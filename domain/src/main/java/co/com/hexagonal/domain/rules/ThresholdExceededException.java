package co.com.hexagonal.domain.rules;

import co.com.hexagonal.domain.Money;

public class ThresholdExceededException extends RuntimeException {

    public ThresholdExceededException(Money threshold, Money actual) {
        super(String.format(
                "Tope de dinero en la transferencia excedido: intento de transferir %s, el máximo permitido es %s",
                actual, threshold));
    }
}
