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
public interface interfaceAdapter {
    
    public void register(interfaceObserver obs);
    
    public void run();
    
    public void shutdown();
}
