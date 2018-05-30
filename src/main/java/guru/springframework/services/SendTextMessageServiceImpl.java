package guru.springframework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
public class SendTextMessageServiceImpl implements SendTextMessageService {
    private Queue textMessageQueue;
    private JmsTemplate jmsTemplate;

    @Autowired
    public SendTextMessageServiceImpl(Queue textMessageQueue, JmsTemplate jmsTemplate) {
        this.textMessageQueue = textMessageQueue;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void sendTextMessage(String msg) {
        this.jmsTemplate.convertAndSend(this.textMessageQueue, msg);
    }
}
