package co.com.hexagonal.domain.port.out;

import co.com.hexagonal.domain.Account;

public interface UpdateAccountStatePort {

    void updateActivities(Account account);

}
