package co.com.hexagonal.infrastructure.adapter.repo;

import co.com.hexagonal.infrastructure.adapter.data.ActivityJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface ActivityJpaRepository extends JpaRepository<ActivityJpaEntity, Long> {

    @Query("select a from ActivityJpaEntity a " +
                   "where a.ownerAccountId = :ownerAccountId " +
                   "and a.timestamp >= :since")
    List<ActivityJpaEntity> findByOwnerSince(@Param("ownerAccountId") Long ownerAccountId,
                                             @Param("since") Instant since);

    @Query("select sum(a.amount) from ActivityJpaEntity a " +
                   "where a.targetAccountId = ?1 " +
                   "and a.ownerAccountId = ?1 " +
                   "and a.timestamp <= ?2")
    Long getDepositBalanceUntil(@Param("accountId") Long accountId, Instant until);

    @Query("select sum(a.amount) from ActivityJpaEntity a " +
                   "where a.sourceAccountId = :#{accountId} " +
                   "and a.ownerAccountId = :#{accountId} " +
                   "and a.timestamp <= :#{until}")
    Long withdrawalBalanceUntil(@Param("accountId") Long accountId, @Param("until") Instant until);

    @Query("select sum(a.amount) from ActivityJpaEntity a " +
                   "where a.sourceAccountId = :#{params['accountId']} " +
                   "and a.ownerAccountId = :#{params['accountId']} " +
                   "and a.timestamp <= :#{params['date']}")
    Long withdrawalBalanceUntil(Map<String, Object> params);

}
