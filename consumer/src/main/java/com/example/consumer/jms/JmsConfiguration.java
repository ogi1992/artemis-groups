package com.example.consumer.jms;

import javax.jms.ConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisProperties;
import org.springframework.boot.autoconfigure.jms.artemis.PublicArtemisConnectionFactoryFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;

/**
 * @author Flurin Juvalta <flurin.juvalta@avelon.ch>
 */
@Configuration
@EnableConfigurationProperties(ArtemisProperties.class)
public class JmsConfiguration {

    @Bean
    public ConnectionFactory jmsConnectionFactory(ListableBeanFactory beanFactory, ArtemisProperties properties) {
        return new CachingConnectionFactory(
                new PublicArtemisConnectionFactoryFactory(beanFactory, properties)
                        .createConnectionFactory(ActiveMQConnectionFactory.class));
    }
}
