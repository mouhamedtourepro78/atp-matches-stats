package com.tennis.atp_matches_stats.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.tennis.atp_matches_stats.domain")
@EnableJpaRepositories("com.tennis.atp_matches_stats.repos")
@EnableTransactionManagement
public class DomainConfig {
}
