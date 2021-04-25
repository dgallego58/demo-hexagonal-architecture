package co.com.hexagonal.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Una cuenta que mantiene una cierta cantidad de dinero. Un objeto de tipo {@link Account} únicamente
 * contiene una ventana de las ultimas actividades de la cuenta. El balance total de la cuenta es
 * la suma de un balance inicial que fue valido antes de la primera actividad en la ventana y la suma de los
 * valores de la actividad.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {

    /**
     * El id único de la cuenta
     */
    private final AccountId id;
    /**
     * El balance base de la cuenta. Este fue el balance de la cuenta antes de la
     * primer actividad en la ventana de actividades "ActivityWindow"
     */
    private final Money baseLineBalance;
    /**
     * La ventana de las últimas actividades en esta cuenta
     */
    private final ActivityWindow activityWindow;

    /**
     * crea una {@link Account} (Cuenta) sin un ID. Es usado para crear una nueva entidad que no está
     * todavía
     *
     * @param baseLineBalance el balance inicial de la cuenta
     * @param activityWindow  el listado de actividades (generalmente ninguna)
     * @return una **nueva** cuenta
     */
    public static Account withoutId(Money baseLineBalance, ActivityWindow activityWindow) {
        return new Account(null, baseLineBalance, activityWindow);
    }

    /**
     * Crea una {@link Account}(Cuenta) con un ID, usada para reconstituir una entidad ya persistida.
     *
     * @param accountId       el id de la cuenta
     * @param baseLineBalance el balance base actual de la cuenta
     * @param activityWindow  las actividades registradas de la cuenta
     * @return la cuenta que ya está en BD
     */
    public static Account withId(AccountId accountId, Money baseLineBalance, ActivityWindow activityWindow) {
        return new Account(accountId, baseLineBalance, activityWindow);
    }

    public Optional<AccountId> getId() {
        return Optional.ofNullable(this.id);
    }

    /**
     * Calcula el balance total de la cuenta al añadir los valores de la actividad a la linea base
     * del balance
     *
     * @return el balance total de la cuenta
     */
    public Money calculateBalance() {
        return Money.add(this.baseLineBalance, this.activityWindow.calculateBalance(this.id));
    }

    /**
     * Intenta retirar una cierta cantidad de dinero desde esta cuenta.
     * Si es exitoso, crea una nueva actividad con un valor negativo.
     *
     * @return true si el retiro fue exitoso, false si no.
     */
    public boolean withdraw(Money money, AccountId targetAccountId) {
        if (!mayWithdraw(money)) {
            return false;
        }
        var withdrawal = new Activity(this.id, this.id, targetAccountId, LocalDateTime.now(), money);
        this.activityWindow.addActivity(withdrawal);
        return true;
    }

    public boolean deposit(Money money, AccountId sourceAccountId) {
        var deposit = new Activity(this.id, sourceAccountId, this.id, LocalDateTime.now(), money);
        this.activityWindow.addActivity(deposit);
        return true;
    }

    public boolean mayWithdraw(Money money) {
        return Money.add(this.calculateBalance(), money.negate()).isPositiveOrZero();
    }

    @Value
    public static class AccountId {
        long value;
    }

}
