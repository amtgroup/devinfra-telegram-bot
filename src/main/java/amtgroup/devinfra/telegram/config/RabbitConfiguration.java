package amtgroup.devinfra.telegram.config;

import amtgroup.devinfra.telegram.util.amqp.AmqpErrorHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.DirectRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import org.springframework.util.ErrorHandler;
import org.springframework.validation.Validator;

/**
 * @author Vitaly Ogoltsov
 */
@Configuration
public class RabbitConfiguration implements RabbitListenerConfigurer {

    private final Validator defaultValidator;

    @Value("${spring.rabbitmq.template.exchange}")
    private String defaultExchangeName;


    @Autowired
    public RabbitConfiguration(Validator defaultValidator) {
        this.defaultValidator = defaultValidator;
    }


    /**
     * Передать собственный экземпляр {@link MessageHandlerMethodFactory}
     * с поддержкой валидации входящих сообщений.
     */
    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    /**
     * Создает и возвращает экземпляр {@link MessageHandlerMethodFactory} с поддержкой валидации входящих сообщений.
     */
    @Bean
    public MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setValidator(defaultValidator);
        return factory;
    }

    /**
     * Обработчик ошибок.
     */
    @Bean
    public ErrorHandler amqpErrorHandler() {
        return new AmqpErrorHandler();
    }


    /**
     * Механизм определения типа содержимого сообщения.
     */
    @Bean
    public ClassMapper classMapper() {
        return new DefaultClassMapper();
    }

    /**
     * Зарегистрировать механизм преобразования сообщений в/из очереди с помощью jackson.
     */
    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper,
                                             ClassMapper classMapper) {

        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter(objectMapper);
        messageConverter.setClassMapper(classMapper);
        return messageConverter;
    }

    /**
     * Настроить {@link org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory}
     * в случае использования {@link SimpleRabbitListenerContainerFactory}.
     */
    @Bean(name = "rabbitListenerContainerFactory")
    @ConditionalOnProperty(prefix = "spring.rabbitmq.listener", name = "type", havingValue = "simple", matchIfMissing = true)
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer,
                                                                                     ConnectionFactory connectionFactory,
                                                                                     ErrorHandler amqpErrorHandler) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setErrorHandler(amqpErrorHandler);
        return factory;
    }

    /**
     * Настроить {@link org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory}
     * в случае использования {@link DirectRabbitListenerContainerFactory}.
     */
    @Bean(name = "rabbitListenerContainerFactory")
    @ConditionalOnProperty(prefix = "spring.rabbitmq.listener", name = "type", havingValue = "direct")
    public DirectRabbitListenerContainerFactory directRabbitListenerContainerFactory(DirectRabbitListenerContainerFactoryConfigurer configurer,
                                                                                     ConnectionFactory connectionFactory,
                                                                                     ErrorHandler amqpErrorHandler) {

        DirectRabbitListenerContainerFactory factory = new DirectRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setErrorHandler(amqpErrorHandler);
        return factory;
    }


    /**
     * Зарегистрировать exchange по умолчанию.
     */
    @Bean
    @ConditionalOnBean(AmqpAdmin.class)
    public Exchange defaultExchange() {
        return new TopicExchange(defaultExchangeName, true, false);
    }

}
