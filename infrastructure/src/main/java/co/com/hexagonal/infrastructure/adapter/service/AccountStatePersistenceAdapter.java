package co.com.hexagonal.infrastructure.adapter.service;

import co.com.hexagonal.domain.Account;
import co.com.hexagonal.domain.port.out.LoadAccountPort;
import co.com.hexagonal.domain.port.out.UpdateAccountStatePort;
import co.com.hexagonal.infrastructure.adapter.data.AccountJpaEntity;
import co.com.hexagonal.infrastructure.adapter.data.ActivityJpaEntity;
import co.com.hexagonal.infrastructure.adapter.repo.AccountJpaRepository;
import co.com.hexagonal.infrastructure.adapter.repo.ActivityJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
@RequiredArgsConstructor
public class AccountStatePersistenceAdapter implements LoadAccountPort, UpdateAccountStatePort {

    private final AccountJpaRepository accountJpaRepository;
    private final ActivityJpaRepository activityJpaRepository;
    private final AccountMapper accountMapper;
    private final AccountPersistenceJDBC accountPersistenceJDBC;


    private Long orZero(Long value) {
        return value == null ? 0L : value;
    }

    @Override
    public Account loadAccount(Account.AccountId accountId, LocalDateTime baselineDate) {
        Long idValue = accountId.getValue();
        var untilInstant = baselineDate.toInstant(ZoneOffset.UTC);

        Map<String, Object> params = new HashMap<>();
        params.put("accountId", idValue);
        params.put("date", untilInstant);
        AccountJpaEntity account = accountJpaRepository.findById(idValue).orElseThrow(EntityNotFoundException::new);
        List<ActivityJpaEntity> activities = activityJpaRepository.findByOwnerSince(idValue, untilInstant);
        Long withdrawalBalance = orZero(accountPersistenceJDBC.withDrawalBalanceUntil(idValue, untilInstant));
        Long depositBalance = orZero(accountPersistenceJDBC.getDepositBalanceUntil(idValue, untilInstant));

        return accountMapper.mapToDomainEntity(account, activities, withdrawalBalance, depositBalance);
    }

    @Override
    public void updateActivities(Account account) {
        account.getActivityWindow().getActivities().forEach(activity -> {
            if (activity.getId() == null) {
                activityJpaRepository.save(accountMapper.mapToJpaEntity(activity));
            }
        });
    }
}
