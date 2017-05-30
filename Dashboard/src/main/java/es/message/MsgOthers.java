/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.message;

import java.util.Date;

/**
 *
 * @author coelho
 */
public class MsgOthers{
    
    public Date timestampUTC;
    public long timestampMS;
    public double HR;
    public double RR;
    public double bodyTemp;
    public double battery;

    public Date getTimestampUTC() {
        return timestampUTC;
    }

    public void setTimestampUTC(Date timestampUTC) {
        this.timestampUTC = timestampUTC;
    }

    public void setTimestampMS(long timestampMS) {
        this.timestampMS = timestampMS;
    }

    public void setHR(double HR) {
        this.HR = HR;
    }

    public void setRR(double RR) {
        this.RR = RR;
    }

    public void setBodyTemp(double bodyTemp) {
        this.bodyTemp = bodyTemp;
    }

    public void setBattery(double battery) {
        this.battery = battery;
    }

    public long getTimestampMS() {
        return timestampMS;
    }

    public double getHR() {
        return HR;
    }

    public double getRR() {
        return RR;
    }

    public double getBodyTemp() {
        return bodyTemp;
    }

    public double getBattery() {
        return battery;
    }
    
    
    
}
