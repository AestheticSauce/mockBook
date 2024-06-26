package com.group3.mockbook;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.UseMainMethod;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(useMainMethod = UseMainMethod.ALWAYS)
@ActiveProfiles("test")
class MockBookTests {

	@Test
	void contextLoads() {
		
	}

}
