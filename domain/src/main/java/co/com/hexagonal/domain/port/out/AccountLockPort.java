package co.com.hexagonal.domain.port.out;

import co.com.hexagonal.domain.Account;

public interface AccountLockPort {

    void lockAccount(Account.AccountId accountId);

    void releaseAccount(Account.AccountId accountId);

}
