package co.com.hexagonal.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * Una actividad de transferencia de dinero entre cuentas {@link Account}
 */
@Getter
@Value
@RequiredArgsConstructor
public class Activity {

    ActivityId id;

    /**
     * La cuenta que posee esta actividad.
     */
    @NonNull Account.AccountId ownerAccountId;

    /**
     * La cuenta debitada
     */
    @NonNull
    Account.AccountId sourceAccountId;

    /**
     * La cuenta acreditada
     */
    @NonNull
    Account.AccountId targetAccountId;

    /**
     * La variable de control de la actividad
     */
    @NonNull
    LocalDateTime timestamp;

    /**
     * El dinero que fue transferido entre cuentas
     */
    @NonNull
    Money money;

    public Activity(@NonNull Account.AccountId ownerAccountId,
                    @NonNull Account.AccountId sourceAccountId,
                    @NonNull Account.AccountId targetAccountId, @NonNull LocalDateTime timestamp,
                    @NonNull Money money) {
        this.id = null;
        this.ownerAccountId = ownerAccountId;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.timestamp = timestamp;
        this.money = money;
    }

    @Value
    public static class ActivityId {
        long value;
    }

}
