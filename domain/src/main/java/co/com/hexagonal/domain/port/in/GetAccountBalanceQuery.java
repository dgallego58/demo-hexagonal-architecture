package co.com.hexagonal.domain.port.in;

import co.com.hexagonal.domain.Account.AccountId;
import co.com.hexagonal.domain.Money;

public interface GetAccountBalanceQuery {

    Money getAccountBalance(AccountId accountId);

}
