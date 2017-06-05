/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.consumer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;

/**
 *
 * @author coelho
 */
public class Consumer extends Thread implements interfaceAdapter{
    
    private static Consumer instance = null;
    private KafkaConsumer<String, String> consumer;
    private LinkedList<interfaceObserver> observers;
    
    private Consumer(){
        observers = new LinkedList<>();
        Properties props = new Properties();
        props.put("bootstrap.servers", Config.ip);
        props.put("group.id", "ECG");        
        String id = "ECGClient" + (int) Math.ceil(Math.random()*100);
        props.put("client.id", id);
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(Config.topics));
    }
    
    public static Consumer getInstance(){
        if(instance == null){
            instance = new Consumer();
            instance.start();
        }
        return instance;
    }
    
    @Override
    public void run(){
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Config.timeout);
                for (ConsumerRecord<String, String> record : records) {
                        System.out.println(record);
                    }
                for (interfaceObserver obs : observers) {
                    obs.update(records);
                }
            System.out.println("Thread de pool correu");
            }
        } catch (WakeupException e) {
            System.out.println("Shutting down thread");
        } finally {
            consumer.close();
        }
    }
    
    @Override
    public void register(interfaceObserver obs){
        observers.add(obs);
    }
    @Override
    public void shutdown() {
        consumer.wakeup();
  }
}
