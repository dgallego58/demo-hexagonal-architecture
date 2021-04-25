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
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class AccountStatePersistenceAdapter implements LoadAccountPort, UpdateAccountStatePort {

    private final AccountJpaRepository accountJpaRepository;
    private final ActivityJpaRepository activityJpaRepository;
    private final AccountMapper accountMapper;

    private Long orZero(Long value) {
        return value == null ? 0L : value;
    }

    @Override
    public Account loadAccount(Account.AccountId accountId, LocalDateTime baselineDate) {
        Long idValue = accountId.getValue();
        var untilInstant = baselineDate.toInstant(ZoneOffset.UTC);
        AccountJpaEntity account = accountJpaRepository.findById(idValue).orElseThrow(EntityNotFoundException::new);
        List<ActivityJpaEntity> activities = activityJpaRepository.findByOwnerSince(idValue, baselineDate);
        Long withdrawalBalance = orZero(activityJpaRepository.getWithdrawalBalanceUntil(idValue, untilInstant));
        Long depositBalance = orZero(activityJpaRepository.getDepositBalanceUntil(idValue, untilInstant));

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
