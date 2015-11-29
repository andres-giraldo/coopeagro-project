/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coopeagro.ejb.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageFormatException;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

@MessageDriven(mappedName = "jms/CoopeagroQueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "connectionFactoryJndiName", propertyValue = "jms/CoopeagroQueueConnectionFactory"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class PagosMDB implements MessageListener{
    
    @Resource
    private MessageDrivenContext mdc;

    public PagosMDB() {
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage tm = (TextMessage) message;
            try {
                System.out.println("Mensaje: " + tm.getText());
            } catch (JMSException ex) {
                Logger.getLogger(PagosMDB.class.getName()).log(Level.SEVERE, "Error leyendo mensaje", ex);
                mdc.setRollbackOnly();
            }
        } else if (message instanceof ObjectMessage) {
            ObjectMessage om = (ObjectMessage) message;
            try {
                System.out.println("Mensaje: " + om.getObject());
            } catch (MessageFormatException ex) {
                Logger.getLogger(PagosMDB.class.getName()).log(Level.SEVERE, null, ex);
                mdc.setRollbackOnly();
            } catch (JMSException ex) {
                Logger.getLogger(PagosMDB.class.getName()).log(Level.SEVERE, null, ex);
                mdc.setRollbackOnly();
            }
        } else {
            Logger.getLogger(PagosMDB.class.getName()).log(Level.SEVERE, "Tipo de mensaje no soportado");
            mdc.setRollbackOnly();
        }
    }
}
