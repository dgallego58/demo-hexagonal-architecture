package co.com.hexagonal.domain;

import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Una ventana de las actividades de una cuenta
 */
public class ActivityWindow {

    /**
     * La lista de actividades de la cuenta en esta ventana
     */
    private final List<Activity> activities;

    public ActivityWindow(@NonNull List<Activity> activities) {
        this.activities = new ArrayList<>(activities);
    }

    public ActivityWindow(@NonNull Activity... activities) {
        this.activities = new ArrayList<>(Arrays.asList(activities));
    }

    /**
     * La fecha y hora de la primera actividad de esta ventanas o
     * arroja una {@link IllegalStateException} en caso de que no sea calculable
     *
     * @return la primera fecha de las actividades
     */
    public LocalDateTime getStartTimestamp() {

        return activities.stream()
                .min(Comparator.comparing(Activity::getTimestamp))
                .orElseThrow(IllegalStateException::new)
                .getTimestamp();
    }

    /**
     * La fecha y hora de la última actividad de esta ventanas o
     * arroja una {@link IllegalStateException} en caso de que no sea calculable
     *
     * @return la primera fecha de las actividades
     */
    public LocalDateTime getEndTimestamp() {
        return activities.stream()
                .max(Comparator.comparing(Activity::getTimestamp))
                .orElseThrow(IllegalStateException::new)
                .getTimestamp();
    }

    /**
     * Calcula el balance al sumar los valores de todas las actividades en esta ventana
     *
     * @param accountId el id de la cuenta en la actividad
     * @return el balance total de las actividades
     */
    public Money calculateBalance(Account.AccountId accountId) {
        Money depositBalance = activities.stream()
                .filter(a -> a.getTargetAccountId().equals(accountId))
                .map(Activity::getMoney)
                .reduce(Money.ZERO, Money::add);
        Money withdrawalBalance = activities.stream()
                .filter(a -> a.getSourceAccountId().equals(accountId))
                .map(Activity::getMoney)
                .reduce(Money.ZERO, Money::add);
        return Money.add(depositBalance, withdrawalBalance.negate());
    }

    public List<Activity> getActivities() {
        return List.copyOf(this.activities);
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }
}
