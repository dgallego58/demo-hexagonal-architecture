package co.com.hexagonal.infrastructure.adapter.repo;

import co.com.hexagonal.infrastructure.adapter.data.AccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountJpaRepository extends JpaRepository<AccountJpaEntity, Long> {
}
