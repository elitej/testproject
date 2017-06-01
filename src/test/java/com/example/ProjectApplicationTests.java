package com.example;

import com.example.web.AdminController;
import com.example.web.MainController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectApplicationTests {

	@Autowired
	private AdminController adminController;
	@Autowired
	private MainController mainController;
//	@Autowired
//	private RegularUserController regularUserController;


	@Test
	public void contextLoads() {
		assertThat(adminController).isNotNull();
		assertThat(mainController).isNotNull();
//		assertThat(regularUserController).isNotNull();
	}

}
