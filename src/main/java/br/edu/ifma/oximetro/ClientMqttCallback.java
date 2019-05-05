/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifma.oximetro;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author anderson
 */
public class ClientMqttCallback implements MqttCallback {

    MqttClient client;
    EntityManagerFactory factory;
    EntityManager manager;

    public boolean connect() {
        try {
            client = new MqttClient("tcp://test.mosquitto.org:1883", "supervisor");
            client.connect();
            client.setCallback(this);
            client.subscribe("ifma/dee/oxi");
            
            factory = Persistence.createEntityManagerFactory("database");
            manager = factory.createEntityManager();
            
            return true;
            
        } catch (MqttException ex) {
            Logger.getLogger(ClientMqttCallback.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void disconnect(){
        try {
            client.disconnect();
            client.close();
            Main.logger("Desconectado em" + Calendar.getInstance().toString());
        } catch (MqttException ex) {
            Main.logger("O cliente está desconectado");
        }
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        System.out.println(mm.toString());
        Supervisor.send(mm);
        
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        System.out.println("desconectado");
        thrwbl.printStackTrace();
    }
    
}
