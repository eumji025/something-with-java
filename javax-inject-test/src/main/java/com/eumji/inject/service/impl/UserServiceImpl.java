package com.eumji.inject.service.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Qualifier;

import com.eumji.inject.domain.User;
import com.eumji.inject.service.UserService;

public class UserServiceImpl implements UserService {

	@Inject
	//@Named(value = "user2")
	private User user;

	@Override
	public String toString() {
		return "UserServiceImpl{" +
				"user=" + user +
				'}';
	}
}
