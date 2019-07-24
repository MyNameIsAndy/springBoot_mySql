package com.andy.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.listener.concurrent}")
    private Integer concurrent;

    @Value("${rabbitmq.listener.prefetch.count}")
    private Integer count;

    /*@Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setAddresses("192.168.99.100:30000,192.168.99.100:30002,192.168.99.100:30004");
        connectionFactory.setChannelCacheSize(10);
        return connectionFactory;
    }*/

    @Bean
    ConnectionFactory connectionFactory(@Value("${spring.rabbitmq.port}") int port,
                                        @Value("${spring.rabbitmq.host}") String host,
                                        @Value("${spring.rabbitmq.username}") String userName,
                                        @Value("${spring.rabbitmq.password}") String password,
                                        @Value("${spring.rabbitmq.publisher-confirms}") boolean isConfirm,
                                        @Value("${spring.rabbitmq.virtual-host}") String vhost) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setVirtualHost(vhost);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setPublisherConfirms(isConfirm);
        return connectionFactory;
    }


    @Bean("rabbitContainerFactory")
    public SimpleRabbitListenerContainerFactory pointTaskContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setPrefetchCount(count);
        factory.setConcurrentConsumers(concurrent);
        configurer.configure(factory, connectionFactory);
        return factory;
    }
}
