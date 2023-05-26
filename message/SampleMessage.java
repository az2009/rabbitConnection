package br.com.springmongodb.queue.message;

import br.com.springmongodb.domain.dto.request.RequestQuoteDTO;
import br.com.springmongodb.queue.AbstractMessage;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class SampleMessage extends AbstractMessage {

    @Getter
    @Value("${sample.rabbit.queue.prefix}")
    public String queueNamePrefix;

    public void publish(RequestQuoteDTO message) {
        rabbitTemplate().convertAndSend(message);
    }
}
