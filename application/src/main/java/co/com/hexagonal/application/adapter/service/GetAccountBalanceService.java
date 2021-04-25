package co.com.hexagonal.application.adapter.service;

import co.com.hexagonal.domain.Account;
import co.com.hexagonal.domain.Money;
import co.com.hexagonal.domain.port.in.GetAccountBalanceQuery;
import co.com.hexagonal.domain.port.out.LoadAccountPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class GetAccountBalanceService implements GetAccountBalanceQuery {

    private final LoadAccountPort loadAccountPort;

    @Override
    public Money getAccountBalance(Account.AccountId accountId) {
        return loadAccountPort.loadAccount(accountId, LocalDateTime.now()).calculateBalance();
    }
}
