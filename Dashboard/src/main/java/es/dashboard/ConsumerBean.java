/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.dashboard;

import es.consumer.Observer;
import es.message.MsgUtil;
import es.message.MsgECG;
import es.consumer.Consumer;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 *
 * @author coelho
 */
@Named(value = "consumerBean")
@ManagedBean
@SessionScoped
public class ConsumerBean implements Serializable {

    private LinkedList<ConsumerRecord<String, String>> events = null;
    private static final long serialVersionUID = 1L;
    private Observer obs;
        
    public ConsumerBean() {
    }
    
    @PostConstruct
    private void init(){
        obs = new Observer();
        Consumer.getInstance().register(obs);
        events = obs.getList();
    }
    
    public List<ConsumerRecord<String, String>> getEvents(){
        return events;
    }
    
    public void updateEvents(){
        events = obs.getList();
    }
    
    public List<MsgECG> getEventsProcessed(){
        ArrayList<MsgECG> array = new ArrayList<>();
        
        events.forEach(event ->{
            MsgUtil util = new MsgUtil();
            MsgECG message = (MsgECG) util.process(event.value());
            array.add(message);
        });
        return array;
    }
    
    
}
