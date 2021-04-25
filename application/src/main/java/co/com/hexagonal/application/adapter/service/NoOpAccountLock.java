package co.com.hexagonal.application.adapter.service;

import co.com.hexagonal.domain.Account;
import co.com.hexagonal.domain.port.out.AccountLockPort;
import org.springframework.stereotype.Service;

@Service
public class NoOpAccountLock implements AccountLockPort {

    @Override
    public void lockAccount(Account.AccountId accountId) {
        //do nothing
    }

    @Override
    public void releaseAccount(Account.AccountId accountId) {
        //do nothing
    }
}
