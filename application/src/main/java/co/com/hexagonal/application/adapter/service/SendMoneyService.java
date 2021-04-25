package co.com.hexagonal.application.adapter.service;

import co.com.hexagonal.domain.MoneyTransferProperties;
import co.com.hexagonal.domain.port.out.AccountLockPort;
import co.com.hexagonal.domain.port.out.LoadAccountPort;
import co.com.hexagonal.domain.port.out.UpdateAccountStatePort;
import co.com.hexagonal.domain.rules.ThresholdExceededException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SendMoneyService implements SendMoneyUseCase {

    private final LoadAccountPort loadAccountPort;
    private final AccountLockPort accountLockPort;
    private final UpdateAccountStatePort updateAccountStatePort;

    @Override
    public boolean sendMoney(SendMoneyCommand command) {
        checkThreshold(command);
        LocalDateTime baselineDate = LocalDateTime.now().minusDays(10);

        var sourceAccount = loadAccountPort.loadAccount(command.getSourceAccountId(), baselineDate);
        var targetAccount = loadAccountPort.loadAccount(command.getTargetAccountId(), baselineDate);

        var sourceAccountId = sourceAccount.getId()
                .orElseThrow(() -> new IllegalStateException("No hay ID en la cuenta de origen"));
        var targetAccountId = targetAccount.getId()
                .orElseThrow(() -> new IllegalStateException("No hay ID de la cuenta objetivo"));

        accountLockPort.lockAccount(sourceAccountId);
        if (!sourceAccount.withdraw(command.getMoney(), targetAccountId)) {
            accountLockPort.releaseAccount(sourceAccountId);
            return false;
        }
        accountLockPort.lockAccount(sourceAccountId);
        if (!targetAccount.deposit(command.getMoney(), sourceAccountId)) {
            accountLockPort.releaseAccount(sourceAccountId);
            accountLockPort.releaseAccount(targetAccountId);
            return false;
        }

        updateAccountStatePort.updateActivities(sourceAccount);
        updateAccountStatePort.updateActivities(targetAccount);

        accountLockPort.releaseAccount(sourceAccountId);
        accountLockPort.releaseAccount(targetAccountId);
        return true;
    }

    private void checkThreshold(SendMoneyCommand command) {
        if (command.getMoney().isGreaterThan(MoneyTransferProperties.maximumTransferThreshold)) {
            throw new ThresholdExceededException(MoneyTransferProperties.maximumTransferThreshold, command.getMoney());
        }
    }
}
