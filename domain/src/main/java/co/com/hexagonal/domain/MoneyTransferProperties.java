package co.com.hexagonal.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Configuration properties for money transfer use case.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MoneyTransferProperties {

    public static final Money maximumTransferThreshold = Money.of(1_000_000L);

}
