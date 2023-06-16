package com.example.demo;

import com.example.demo.repo.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
	@Autowired
	RoleRepository roleRepository;
	@Test
	void roles() {
		Assertions.assertEquals(roleRepository.findAll().size(), 3);
	}


}
