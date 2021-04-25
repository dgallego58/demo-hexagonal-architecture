package co.com.hexagonal.domain.port.out;

import co.com.hexagonal.domain.Account;

import java.time.LocalDateTime;

public interface LoadAccountPort {

    Account loadAccount(Account.AccountId accountId, LocalDateTime baselineDate);

}
