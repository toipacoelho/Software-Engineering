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
    
    public Object process(String msg){        
        String[] parts = msg.split(" ");
        if (parts.length == 1){
            MsgECG parsed = new MsgECG();
            parsed.setValue(Double.parseDouble(parts[0]));
            return parsed;
        }
        else if (parts.length == 6){
            MsgOthers parsed = new MsgOthers();
            return parsed;
        }
        else
            return null;
    }
}
