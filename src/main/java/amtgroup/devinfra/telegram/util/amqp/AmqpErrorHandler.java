package amtgroup.devinfra.telegram.util.amqp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.util.ErrorHandler;
import org.springframework.util.ReflectionUtils;

import java.util.Objects;

/**
 * Обработчик ошибок от {@link org.springframework.amqp.rabbit.annotation.RabbitListener}.
 * Основано на реализации {@link org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler}.
 *
 * @author Vitaly Ogoltsov
 */
@Slf4j
public class AmqpErrorHandler implements ErrorHandler {

    private final FatalExceptionStrategy exceptionStrategy;


    public AmqpErrorHandler() {
        this(null);
    }

    @SuppressWarnings("WeakerAccess")
    public AmqpErrorHandler(FatalExceptionStrategy exceptionStrategy) {
        this.exceptionStrategy = exceptionStrategy;
    }

    @Override
    public void handleError(Throwable t) {
        Objects.requireNonNull(t);
        // найти нижнее исключение типа ListenerExecutionFailedException в стеке
        ListenerExecutionFailedException lefe = getException(t, ListenerExecutionFailedException.class);
        // если исключение типа ListenerExecutionFailedException присутствует в стеке, необходимо изучить его причину
        if (lefe != null) {
            while (true) {
                ListenerExecutionFailedException e = getException(lefe.getCause(), ListenerExecutionFailedException.class);
                if (e == null) {
                    break;
                }
                lefe = e;
            }
            Throwable cause = lefe.getCause();
            if (cause instanceof MessageConversionException
                    || cause instanceof org.springframework.messaging.converter.MessageConversionException) {

                reject("Ошибка при обработке входящего сообщения: некорректный формат данных", t);
            } else if (cause instanceof MethodArgumentNotValidException) {
                reject("Ошибка при обработке входящего сообщения: валидация данных не пройдена", t);
            } else if (cause instanceof MethodArgumentTypeMismatchException
                    || cause instanceof NoSuchMethodException
                    || cause instanceof ClassCastException) {

                reject("Ошибка при обработке входящего сообщения: ошибка вызова метода обработки", t);
            }
        }
        // оптимистическая блокировка означает, что требуется переповторить транзакцию
        if (getException(t, OptimisticLockingFailureException.class) != null) {
            rethrow("Ошибка при обработке входящего сообщения: ошибка оптимистической блокировки данных", t);
        }
        // блокировка на уровне БД означает, что требуется повторить транзакцию
        if (getException(t, CannotAcquireLockException.class) != null) {
            rethrow("Ошибка при обработке входящего сообщения: ошибка блокировки данных на уровне БД", t);
        }
        // если установлена специальная стратегия, дать ей возможность пометить исключение как нефатальное
        if (exceptionStrategy != null && !exceptionStrategy.isFatal(t)) {
            rethrow("Ошибка при обработке входящего сообщения", t);
        }
        // если в стеке исклчений есть AmqpRejectAndDontRequeueException, то перебросить его дальше
        if (getException(t, AmqpRejectAndDontRequeueException.class) != null) {
            rethrow("Ошибка при обработке входящего сообщения", t);
        } else {
            reject("Ошибка при обработке входящего сообщения", t);
        }
    }

    private <T extends Throwable> T getException(Throwable t, Class<T> exceptionClass) {
        while (t != null) {
            if (exceptionClass.isInstance(t)) {
                return exceptionClass.cast(t);
            }
            t = t.getCause();
        }
        return null;
    }

    private void rethrow(String message, Throwable t) {
        log.error(message, t);
        ReflectionUtils.rethrowRuntimeException(t);
    }

    private void reject(String message, Throwable t) {
        log.error(message + " (сообщение будет удалено из очереди)", t);
        throw new AmqpRejectAndDontRequeueException(message, t);
    }

}
