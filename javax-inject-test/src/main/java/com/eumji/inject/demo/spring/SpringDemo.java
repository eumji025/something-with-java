package com.eumji.inject.demo.spring;

import com.eumji.inject.domain.User;
import com.eumji.inject.service.UserService;
import com.eumji.inject.service.impl.UserServiceImpl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class SpringDemo {

	@Configuration
	static class SpringConfiguration {

		@Bean
		public User user1(){
			User user = new User();
			user.setAge(19);
			user.setName("lisi");
			return user;
		}

		@Bean
		public User user2(){
			User user = new User();
			user.setAge(192);
			user.setName("lisi2");
			return user;
		}

		@Bean
		public UserService userService(){
			return new UserServiceImpl();
		}

	}


	public static void main(String[] args) {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		UserService bean = applicationContext.getBean(UserService.class);
		System.out.println(bean.toString());
	}
}
