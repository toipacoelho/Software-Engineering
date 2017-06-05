/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.consumer;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedDeque;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

/**
 *
 * @author coelho
 */
public class Observer implements interfaceObserver<ConsumerRecords<String, String>>{
    
    private ConcurrentLinkedDeque<ConsumerRecord<String, String>> last_event = null;  
    
    public Observer(){
        last_event = new ConcurrentLinkedDeque<>();
    }
    
    @Override
    public synchronized void update(ConsumerRecords<String, String> o) {
        for (ConsumerRecord<String, String> record : o) {
            if (!last_event.contains(record)) {
                last_event.offerFirst(record);
            }
        }
    }
    
    public synchronized ConsumerRecord<String, String> getLastEvent(){
        try {
            return last_event.getLast();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
    
    public synchronized boolean hasNewEvent(String key){
        try {
            return !last_event.getLast().key().equals(key);
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    
    public synchronized ConsumerRecord<String, String>[] getAllEvents(){
        return (ConsumerRecord<String, String>[])last_event.toArray();
    }
    
    public synchronized ConcurrentLinkedDeque<ConsumerRecord<String, String>> getList(){
        return last_event;
    }
    
}
