/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifma.oximetro;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
        
/**
 *
 * @author anderson
 */
public class ListenerMQTT implements IMqttMessageListener{

    public ListenerMQTT(ClientMQTT client, String topic, int qos){
        client.subscribe(qos, this, topic);
    }
    
    @Override
    public void messageArrived(String topic, MqttMessage mm) throws Exception {
        System.out.println("Message arrived:");
        System.out.println("\ttopic: " + topic);
        System.out.println("\tMessage: " + new String(mm.getPayload()));
        System.out.println("");
        
        Supervisor.send(mm);
        
    }
}
