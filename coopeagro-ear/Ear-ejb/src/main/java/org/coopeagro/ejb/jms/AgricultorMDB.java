/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coopeagro.ejb.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author YEISSON
 */
//@MessageDriven(mappedName = "jms/CoopeagroQueue", activationConfig = {
//    @ActivationConfigProperty(propertyName = "connectionFactoryJndiName",propertyValue = "jms/CoopeagroQueue"),
//    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
//})
public class AgricultorMDB implements MessageListener{

    public void onMessage(Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
