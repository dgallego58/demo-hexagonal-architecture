package co.com.hexagonal.application.adapter.service;

import co.com.hexagonal.application.commons.SelfValidator;
import co.com.hexagonal.domain.Account.AccountId;
import co.com.hexagonal.domain.Money;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;

public interface SendMoneyUseCase {

    boolean sendMoney(SendMoneyCommand command);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class SendMoneyCommand extends SelfValidator<SendMoneyCommand> {

        @NotNull
        AccountId sourceAccountId;
        @NotNull
        AccountId targetAccountId;
        @NotNull Money money;

        public SendMoneyCommand(AccountId sourceAccountId, AccountId targetAccountId, Money money) {
            this.sourceAccountId = sourceAccountId;
            this.targetAccountId = targetAccountId;
            this.money = money;
            this.validateSelf();
        }
    }

}
