/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifma.oximetro;

import java.util.Arrays;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

/**
 *
 * @author anderson
 */
public class ClientMQTT implements MqttCallback{
    
    private final String serverHost;
    private MqttClient client;
    private final MqttConnectOptions mqttConnectOptions;
    
    public ClientMQTT(String serverHost){
        this.serverHost = serverHost;
        
        mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setMaxInflight(200);
        mqttConnectOptions.setConnectionTimeout(3);
        mqttConnectOptions.setKeepAliveInterval(10);
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        
    }
    
    public IMqttToken subscribe(int qos, IMqttMessageListener messageManagerMQTT, String ... topics){
        if (client == null || topics.length == 0) {
            return null;
        }
        
        int length = topics.length;
        int [] qoss = new int[length];
        IMqttMessageListener[] listeners = new IMqttMessageListener[length];
        
        for(int i = 0; i < length; i++){
            qoss[i] = qos;
            listeners[i] = messageManagerMQTT;
        }
        try{
            return client.subscribeWithResponse(topics, qoss, listeners);
        }catch(MqttException ex){
            System.out.println(String.format("topic not subscribe %s - %s", Arrays.asList(topics), ex));
            return null;
        }
    }
    
    public void init(){
        try{
            System.out.println("Connecting to broker in " + serverHost);
            client = new MqttClient(serverHost, String.format("client_java_%d", System.currentTimeMillis()), new MqttDefaultFilePersistence(System.getProperty("java.io.tmpdir")));
            client.setCallback(this);
            client.connect(mqttConnectOptions);
        }catch(MqttException ex){
            System.err.println("Unable to connect to mqtt broker " + serverHost + " - " + ex);
        }
    }
    
    public void close(){
        if(client == null || !client.isConnected()){
            return;
        }
        try{
            client.disconnect();
            client.close();
        }catch(MqttException ex){
            System.err.println("Unable to disconnect to broker - " + ex);
        }
    }
    
    public void publish(String topic, byte[] payload, int qos){
        publish(topic, payload, qos, false);
    }
    
    public synchronized void publish(String topic, byte[] payload, int qos, boolean retained){
        try{
            if(client.isConnected()){
                client.publish(topic, payload, qos, retained);
                System.out.println(String.format("Topic %s published. %dB", topic, payload.length));
            }else{
                System.out.println("Client disconnect, unable to publish in topic " + topic);
            }
            
        }catch(MqttException ex){
            System.out.println("Error to publish " + topic + " - " + ex);
        }
    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        System.out.println("Connection lost! -" + thrwbl);
    }
    
    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        Supervisor.send(mm);
        
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        
    }
    
}
