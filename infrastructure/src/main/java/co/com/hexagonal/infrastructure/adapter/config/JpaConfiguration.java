package co.com.hexagonal.infrastructure.adapter.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"co.com.hexagonal.infrastructure.adapter.repo"})
@EntityScan(basePackages = {"co.com.hexagonal.infrastructure.adapter.data"})
@ComponentScan(basePackages = {"co.com.hexagonal.infrastructure.adapter"})
public class JpaConfiguration {

}
