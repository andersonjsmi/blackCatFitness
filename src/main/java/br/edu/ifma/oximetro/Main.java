/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifma.oximetro;

import br.edu.ifma.oximetro.entity.Telemetry;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.w3c.dom.ranges.Range;

/**
 *
 * @author anderson
 */
public class Main extends JFrame {
    
    private static MainFrame mainFrame;
    
    public Main(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("database");
        EntityManager manager = factory.createEntityManager();
        factory.close();
        start();
    }
    
    public void start(){
        /*
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("database");
        EntityManager manager = factory.createEntityManager();
        List<Telemetry> telemetryList = manager.createQuery("select t from Telemetry t").getResultList();
        
        for(Telemetry telemetry : telemetryList){
            ds.addValue(telemetry.getSpo2(), "Oxigenação por Zona", telemetry.getZone());
        }
        JFreeChart graphic = ChartFactory.createLineChart("Gráfico", "Zona", "Oxigenação", ds, PlotOrientation.VERTICAL, true, true, false);
        
        this.add(new ChartPanel(graphic));
        
        manager.close();
        factory.close();
        */
        ClientMQTT client = new ClientMQTT("tcp://127.0.0.1:1883");
        client.init();
        
        new ListenerMQTT(client, "ifma/dee/oximetro", 0);
    }
    
    public static void logger(String message){
        
        mainFrame.log(message);
    }
    
    public static void chronometer(long time){
        Integer hour = (int) time / 3600;
        Integer minutes = (int) (time % 3600) / 60;
        Integer seconds = (int) (time % 3600) % 60; 
        mainFrame.chronometer(hour.toString() + ":" + minutes.toString() + ":" + seconds.toString());
    }
    
    public static void zones(Telemetry telemetry){
        mainFrame.trainingZone(telemetry);
    }
    
    public static void person(Person person){
        mainFrame.setPerson(person);
    }
    
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
       
    }
}
