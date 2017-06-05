/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.consumer;

/**
 *
 * @author coelho
 */
public class Config {
    //kafka IP
    final static public String ip = "172.17.0.5:9092";
    
    //topics to subscrive
    //final static public String[] topics = {"VitalJacket_ECG", "VitalJacket_Others"};
    final static public String topics = "VitalJacket_ECG";
    
    final static public long timeout = Long.MAX_VALUE;
}
