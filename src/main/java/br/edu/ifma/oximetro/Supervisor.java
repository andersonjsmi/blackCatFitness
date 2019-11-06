/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifma.oximetro;

import br.edu.ifma.oximetro.entity.Session;
import br.edu.ifma.oximetro.entity.Telemetry;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author anderson
 */
public class Supervisor {
    private final ClientMqttCallback client;
    private final TimerTask tarefa;
    private final Timer tick;
    private static long timer;
    private static Session session;
    private static EntityManagerFactory factory;
    private static EntityManager manager;
    
    private static boolean running;
    
    public static void send(MqttMessage mm){
        String jsonString = mm.toString();
        Date today = new Date();
        Telemetry telemetry;
        JSONObject oximeterObject;
        
        try{
            oximeterObject = new JSONObject(jsonString);

            telemetry  = new Telemetry();

            telemetry.setSession(session);
            telemetry.setDeviceName(oximeterObject.getString("device"));
            telemetry.setBpm(oximeterObject.getFloat("bpm"));
            telemetry.setSpo2(oximeterObject.getInt("spo2"));
            telemetry.setCreated_at(Calendar.getInstance());
            telemetry.setZone(MainFrame.getPerson().getBPMZone(telemetry.getBpm()));

            save(telemetry);

            Main.zones(telemetry);

            String logMessage = "receive data from " + telemetry.getDeviceName() + ", at " + today.toString();

            Main.logger(logMessage);
        }catch(JSONException ex){
            Main.logger("DEVICE MESSAGE IS NOT A JSON OBJECT!");
        }
        System.err.println("message arrive");
    }
    
    public Supervisor(){
        tick = new Timer();
        tarefa = new TimerTask() {
            @Override
            public void run() {
                Supervisor.tick();
            }
        };
        tick.schedule(tarefa, 0, 1000);
        
        client = new ClientMqttCallback();
    }
    
    public static void tick(){
        if(running){
            timer ++;
            Main.chronometer(timer);
        }
    }
    
    public void start(){
        session = new Session();
        session.setCreated_at(Calendar.getInstance());
        save(session);
        
        client.connect();
        
        timer = 0;
        running = true;
    }
    
    public void stop(){
        running = false;
        client.disconnect();
        
        session.setLength(timer);
        update(session);
        session = null;
    }
    
    public static void update(Object object){
        connect();
        
        manager.getTransaction().begin();
        manager.merge(object);
        manager.getTransaction().commit();
        
        close();
    }
    
    public static void save(Object object){
        connect();
        
        manager.getTransaction().begin();
        manager.persist(object);
        manager.getTransaction().commit();
        
        close();
    }
    
    public static void connect(){
        factory = Persistence.createEntityManagerFactory("database");
        manager = factory.createEntityManager();
    }
    
    public static void close(){
        manager.close();
        factory.close();
    }
    
    
    public static List<Person> getAllPersons(){
        connect();
        List<Person> persons = manager.createQuery("select p from Person as p").getResultList();
        close();
        return persons;
    }
    
    
    public static Person getPerson(long id){
        connect();
        Person person = manager.find(Person.class, id);
        close();
        return person;
    }
}
