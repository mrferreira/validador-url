package com.mferreira.validadorurl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories
@SpringBootTest
@EnableTransactionManagement
class ValidadorUrlApplicationTests {

	@Test
	void contextLoads() {
	}

}
