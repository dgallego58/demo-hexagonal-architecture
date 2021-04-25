package co.com.hexagonal.application.api.controller;

import co.com.hexagonal.application.adapter.service.SendMoneyUseCase;
import co.com.hexagonal.application.adapter.service.SendMoneyUseCase.SendMoneyCommand;
import co.com.hexagonal.domain.Account;
import co.com.hexagonal.domain.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/accounts")
public class SendMoneyController {

    private final SendMoneyUseCase sendMoneyUseCase;

    @PostMapping(path = "/send/{sourceAccountId}/{targetAccountId}/{amount}")
    public ResponseEntity<Void> sendMoney(@PathVariable("sourceAccountId") final Long sourceAccountId,
                                          @PathVariable("targetAccountId") final Long targetAccountId,
                                          @PathVariable("amount") final Long amount) {

        var command = new SendMoneyCommand(new Account.AccountId(sourceAccountId),
                                           new Account.AccountId(targetAccountId),
                                           Money.of(amount));

        sendMoneyUseCase.sendMoney(command);
        return ResponseEntity.noContent().build();

    }

}
