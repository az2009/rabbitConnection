package br.com.springmongodb.queue.consumers;

import br.com.springmongodb.domain.dto.request.RequestQuoteDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    @RabbitListener(containerFactory = "containerFactory", queues = {"${sample.rabbit.queue.queueName}"})
    public void processMessage(@Payload RequestQuoteDTO message) {
        System.out.println(message.toString());
    }
}
