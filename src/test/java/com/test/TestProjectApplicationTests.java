package com.test;

import com.test.web.AdminController;
import com.test.web.MainController;
import com.test.web.RegularUserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestProjectApplicationTests {

	@Autowired
	private AdminController adminController;
	@Autowired
	private MainController mainController;
	@Autowired
	private RegularUserController regularUserController;


	@Test
	public void contextLoads() {
		assertThat(adminController).isNotNull();
		assertThat(mainController).isNotNull();
		assertThat(regularUserController).isNotNull();
	}

}
