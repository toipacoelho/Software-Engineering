/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.message;

/**
 *
 * @author coelho
 */
public class MsgUtil {
    
    public String process(String msg){        
        String[] parts = msg.split(" ");
        return parts[parts.length-1];
    }
}
