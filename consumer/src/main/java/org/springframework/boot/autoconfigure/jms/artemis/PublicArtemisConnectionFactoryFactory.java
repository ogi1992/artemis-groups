package org.springframework.boot.autoconfigure.jms.artemis;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisConnectionFactoryFactory;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisProperties;

/**
 * This class exposes the otherwise package private {@link ArtemisConnectionFactoryFactory}
 *
 * @author Flurin Juvalta <flurin.juvalta@avelon.ch>
 */
public class PublicArtemisConnectionFactoryFactory extends ArtemisConnectionFactoryFactory {

    public PublicArtemisConnectionFactoryFactory(ListableBeanFactory beanFactory, ArtemisProperties properties) {
        super(beanFactory, properties);
    }

    @Override
    public <T extends ActiveMQConnectionFactory> T createConnectionFactory(Class<T> factoryClass) {
        return super.createConnectionFactory(factoryClass);
    }
}
