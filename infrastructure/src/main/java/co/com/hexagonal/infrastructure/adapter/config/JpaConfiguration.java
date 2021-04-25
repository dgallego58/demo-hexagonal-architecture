package co.com.hexagonal.infrastructure.adapter.config;

import co.com.hexagonal.domain.port.out.LoadAccountPort;
import co.com.hexagonal.domain.port.out.UpdateAccountStatePort;
import co.com.hexagonal.infrastructure.adapter.repo.AccountJpaRepository;
import co.com.hexagonal.infrastructure.adapter.repo.ActivityJpaRepository;
import co.com.hexagonal.infrastructure.adapter.service.AccountMapper;
import co.com.hexagonal.infrastructure.adapter.service.AccountStatePersistenceAdapter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"co.com.hexagonal.infrastructure.adapter.repo"})
@EntityScan(basePackages = {"co.com.hexagonal.infrastructure.adapter.data"})
@ComponentScan(basePackages = {"co.com.hexagonal.infrastructure.adapter"})
public class JpaConfiguration {

/*    @Bean
    public LoadAccountPort loadAccountPort(final AccountJpaRepository accountJpaRepository,
                                           final ActivityJpaRepository activityJpaRepository,
                                           final AccountMapper accountMapper) {
        return new AccountStatePersistenceAdapter(accountJpaRepository, activityJpaRepository, accountMapper);
    }

    @Bean
    public UpdateAccountStatePort updateAccountStatePort(final AccountJpaRepository accountJpaRepository,
                                                         final ActivityJpaRepository activityJpaRepository,
                                                         final AccountMapper accountMapper) {
        return new AccountStatePersistenceAdapter(accountJpaRepository, activityJpaRepository, accountMapper);
    }*/

}
