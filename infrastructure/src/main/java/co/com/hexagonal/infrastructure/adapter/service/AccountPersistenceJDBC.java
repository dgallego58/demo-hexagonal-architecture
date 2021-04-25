package co.com.hexagonal.infrastructure.adapter.service;

import co.com.hexagonal.infrastructure.adapter.data.AccountJpaEntity;
import co.com.hexagonal.infrastructure.adapter.data.ActivityJpaEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Repository
public class AccountPersistenceJDBC {

    public static final RowMapper<AccountJpaEntity> accountRowMapper = (rs, rowNum) -> {
        var accountJpaEntity = new AccountJpaEntity();
        accountJpaEntity.setId(rs.getLong("ID"));
        return accountJpaEntity;
    };
    public static final RowMapper<ActivityJpaEntity> activityRowMapper = (rs, rowNum) -> {
        var activityJpaEntity = new ActivityJpaEntity();
        activityJpaEntity.setId(rs.getLong("ID"));
        activityJpaEntity.setOwnerAccountId(rs.getLong("OWNER_ACCOUNT_ID"));
        activityJpaEntity.setTargetAccountId(rs.getLong("TARGET_ACCOUNT_ID"));
        activityJpaEntity.setAmount(rs.getLong("AMOUNT"));
        activityJpaEntity.setTimestamp(rs.getObject("TIME_STAMP", Instant.class));

        return activityJpaEntity;
    };
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    public AccountPersistenceJDBC(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                  JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long withDrawalBalanceUntil(Long accountId, Instant until) {
        var instant = LocalDateTime.ofInstant(until, ZoneOffset.UTC);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("accountId", accountId)
                .addValue("until", instant);

        String query = "select sum(a.AMOUNT) from hexagon.activities a " +
                "where a.OWNER_ACCOUNT_ID = :accountId " +
                "and a.TARGET_ACCOUNT_ID = :accountId " +
                "and a.TIMESTAMP < :until";
        return namedParameterJdbcTemplate.queryForObject(query, sqlParameterSource, Long.class);
    }

    public Long getDepositBalanceUntil(Long accountId, Instant until) {
        var instant = LocalDateTime.ofInstant(until, ZoneOffset.UTC);
        String query = "select sum(a.amount) from hexagon.activities as a " +
                "where a.TARGET_ACCOUNT_ID = ? " +
                "and a.OWNER_ACCOUNT_ID = ? " +
                "and a.TIMESTAMP < ?";
        return jdbcTemplate.queryForObject(query, Long.class, accountId, accountId, instant);

    }

}
