package co.com.hexagonal.infrastructure.adapter.service;

import co.com.hexagonal.domain.Account;
import co.com.hexagonal.domain.Account.AccountId;
import co.com.hexagonal.domain.Activity;
import co.com.hexagonal.domain.Activity.ActivityId;
import co.com.hexagonal.domain.ActivityWindow;
import co.com.hexagonal.domain.Money;
import co.com.hexagonal.infrastructure.adapter.data.AccountJpaEntity;
import co.com.hexagonal.infrastructure.adapter.data.ActivityJpaEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Component
public class AccountMapper {

    Account mapToDomainEntity(AccountJpaEntity accountJpaEntity,
                              List<ActivityJpaEntity> activityJpaEntities,
                              Long withdrawBalance,
                              Long depositBalance) {

        var baselineBalance = Money.subtract(Money.of(depositBalance), Money.of(withdrawBalance));

        return Account.withId(new AccountId(accountJpaEntity.getId()),
                              baselineBalance,
                              mapToActivityWindow(activityJpaEntities));

    }

    ActivityWindow mapToActivityWindow(List<ActivityJpaEntity> activityJpaEntities) {
        List<Activity> mappedActivities = new ArrayList<>();
        activityJpaEntities.forEach(a -> mappedActivities.add(new Activity(
                new ActivityId(a.getId()),
                new AccountId(a.getOwnerAccountId()),
                new AccountId(a.getSourceAccountId()),
                new AccountId(a.getTargetAccountId()),
                LocalDateTime.ofInstant(a.getTimestamp(), ZoneOffset.UTC),
                Money.of(a.getAmount())
        )));
        return new ActivityWindow(mappedActivities);
    }

    ActivityJpaEntity mapToJpaEntity(Activity activity) {
        return new ActivityJpaEntity(activity.getId() == null ? null : activity.getId().getValue(),
                                     activity.getTimestamp().toInstant(ZoneOffset.UTC),
                                     activity.getOwnerAccountId().getValue(),
                                     activity.getSourceAccountId().getValue(),
                                     activity.getTargetAccountId().getValue(),
                                     activity.getMoney().getAmount().longValue()
        );
    }

}
