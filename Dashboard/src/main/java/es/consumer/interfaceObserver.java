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
public interface interfaceObserver<T> {
    
    public void update(T obj);
    
}
