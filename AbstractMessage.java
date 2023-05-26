package br.com.springmongodb.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@Getter
public abstract class AbstractMessage {

    @Getter(AccessLevel.PRIVATE)
    private CachingConnectionFactory connection = new CachingConnectionFactory();

    @Getter(AccessLevel.PRIVATE)
    private MessageConverter converter = new Jackson2JsonMessageConverter(new ObjectMapper());

    public String queueNamePrefix;

    @Getter(AccessLevel.PRIVATE)
    @Autowired
    ApplicationContext applicationContext;

    @Bean
    protected Queue queue() {
        return new Queue(getProperty("rabbit.queue.queueName"), false);
    }

    @Bean
    protected TopicExchange exchange() {
        return new TopicExchange(getProperty("rabbit.queue.topicExchangeName"));
    }

    @Bean
    protected Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(getProperty("rabbit.queue.queueName"));
    }

    @Bean
    public SimpleRabbitListenerContainerFactory containerFactory() {
        SimpleRabbitListenerContainerFactory listener = new SimpleRabbitListenerContainerFactory();
        listener.setConnectionFactory(getConnection());
        listener.setAutoStartup(Boolean.valueOf(getProperty("rabbit.queue.autoStartup")));
        listener.setMaxConcurrentConsumers(Integer.valueOf(getProperty("rabbit.queue.maxConcurrentConsumers")));
        listener.setConcurrentConsumers(Integer.valueOf(getProperty("rabbit.queue.maxConcurrentConsumers")));
        listener.setMissingQueuesFatal(Boolean.valueOf(getProperty("rabbit.queue.missingQueuesFatal")));
        listener.setStartConsumerMinInterval(Long.valueOf(getProperty("rabbit.queue.startConsumerMinInterval")));
        listener.setRecoveryInterval(Long.valueOf(getProperty("rabbit.queue.recoveryInterval")));
        listener.setChannelTransacted(Boolean.valueOf(getProperty("rabbit.queue.channelTransacted")));
        listener.setPrefetchCount(Integer.valueOf(getProperty("rabbit.queue.prefetchCount")));
        listener.setMessageConverter(getConverter());
        return listener;
    }

    protected RabbitTemplate rabbitTemplate()
    {
        RabbitTemplate template = new RabbitTemplate(getConnection());
        template.setMessageConverter(getConverter());
        template.setExchange(getProperty("rabbit.queue.topicExchangeName"));
        template.setRoutingKey(getProperty("rabbit.queue.queueName"));
        return template;
    }

    private String getProperty(String propertyName)
    {
        return getApplicationContext().getEnvironment().getProperty(getQueueNamePrefix() + "." + propertyName);
    }
}
