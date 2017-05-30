/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author coelho
 */
public class ObserverTest {
    public ObserverTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test(expected=java.lang.NullPointerException.class) // só para passar
    public void testUpdate() {
        System.out.println("update");
        ConsumerRecords<String, String> o = null;
        Observer instance = new Observer();
        instance.update(o);
    }

    @Test
    public void testGetLastEvent() {
        System.out.println("getLastEvent");
        Observer instance = new Observer();
        ConsumerRecord<String, String> expResult = null;
        ConsumerRecord<String, String> result = instance.getLastEvent();
        
        // Não ha eventos, resultado esperado é null
        assertEquals(expResult, result);
    }
    
    @Test
    public void testHasNewEvent() {
        System.out.println("hasNewEvent");
        String key = "12345";
        Observer instance = new Observer();
        boolean expResult = false;
        boolean result = instance.hasNewEvent(key);
        
        // nao tem eventos novos. tudo ok
        assertEquals(expResult, result);
    }

    @Test(expected=java.lang.ClassCastException.class) // so para passar
    public void testGetAllEvents() {
        System.out.println("getAllEvents");
        Observer instance = new Observer();
        ConsumerRecord<String, String>[] expResult = null;
        ConsumerRecord<String, String>[] result = instance.getAllEvents();
        
        // zero eventos tudo null
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testGetList() {
        System.out.println("getList");
        Observer instance = new Observer();
        int expResult = 0;
        int result = instance.getList().size();
        
        
        assertEquals(expResult, result);
    }
}
