/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.kafkaproducerconsumer;

import java.util.Properties;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author coelho
 */
public class FileSender {

    public static void main(String[] args) throws Exception {

        File f = new File("../VitalJacket_ECG.tsv");
        List<String> lines = FileUtils.readLines(f, "UTF-8");

        //Assign topicName to string variable
        String topicName = "test";

        // create instance for properties to access producer configs   
        Properties props = new Properties();
        //Assign localhost id
        props.put("bootstrap.servers", "localhost:9092");
        //Set acknowledgements for producer requests.      
        props.put("acks", "all");
        //If the request fails, the producer can automatically retry,
        props.put("retries", 0);
        //Specify buffer size in config
        props.put("batch.size", 16384);
        //Reduce the no of requests less than 0   
        props.put("linger.ms", 1);
        //The buffer.memory controls the total amount of memory available to the producer for buffering.   
        props.put("buffer.memory", 33554432);
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<String, String>(props);

        for (String line : lines) {
            producer.send(new ProducerRecord<String, String>(topicName, line));            
            System.out.println(line);
            TimeUnit.MILLISECONDS.sleep(800);
        }
        
        System.out.println("Message sent successfully");
        producer.close();
    }
}
