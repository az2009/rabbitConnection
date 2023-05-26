# rabbitConnection

Add Config to application.properties

...

#QUEUE SAMPLE<br>
sample.rabbit.queue.prefix=sample<br>
sample.rabbit.queue.queueName=sample.queue.name<br>
sample.rabbit.queue.topicExchangeName=sample.topic.exchange.name<br>
sample.rabbit.queue.autoStartup=true<br>
sample.rabbit.queue.maxConcurrentConsumers=5<br>
sample.rabbit.queue.startConsumerMinInterval=3000<br>
sample.rabbit.queue.recoveryInterval=1500<br>
sample.rabbit.queue.prefetchCount=1<br>
sample.rabbit.queue.channelTransacted=true<br>
sample.rabbit.queue.missingQueuesFatal=false<br>

...
