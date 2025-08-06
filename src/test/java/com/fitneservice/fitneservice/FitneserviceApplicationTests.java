package com.fitneservice.fitneservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class FitneserviceApplicationTests {

	@Test
	void contextLoads() {
	}

}
