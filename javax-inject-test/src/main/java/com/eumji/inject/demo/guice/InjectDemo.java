package com.eumji.inject.demo.guice;

import com.eumji.inject.domain.User;
import com.eumji.inject.service.UserService;
import com.eumji.inject.service.impl.UserServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;

public class InjectDemo {

	static class GuiceModule extends AbstractModule{
		@Override
		protected void configure() {
			//
			User user = new User();
			user.setName("zhangsan");
			user.setAge(18);

			User user2 = new User();
			user2.setName("zhangsan2");
			user2.setAge(182);
			bind(User.class).toInstance(user);
			bind(User.class).annotatedWith(Names.named("user2")).toInstance(user2);

			bind(UserService.class).to(UserServiceImpl.class);
		}
	}

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new GuiceModule());
		UserService instance = injector.getInstance(UserService.class);
		System.out.println(instance.toString());
	}
}
