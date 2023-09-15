package com.company.service;

import java.util.List;

import com.company.Exceptions.NoSuchUserException;
import com.company.Exceptions.UserAlreadyExistsException;
import com.company.dao.UserDao;
import com.company.model.UserDetails;

public class UserServiceImpl implements UserService {
	
	private UserDao dao;

	public UserDao getDao() {
		return dao;
	}

	public void setDao(UserDao dao) {
		this.dao = dao;
	}

	@Override
	public boolean createUser(UserDetails user) throws UserAlreadyExistsException {
		// TODO Auto-generated method stub
		return dao.createUser(user);
	}

	@Override
	public UserDetails readUser(int uniqueId) throws NoSuchUserException {
		// TODO Auto-generated method stub
		return dao.readUser(uniqueId);
	}

	@Override
	public boolean deleteUser(int uniqueId) throws NoSuchUserException {
		// TODO Auto-generated method stub
		return dao.deleteUser(uniqueId);
	}

	@Override
	public UserDetails updateUser(int uniqueId, UserDetails user) {
		// TODO Auto-generated method stub
		return dao.updateUser(uniqueId, user);
	}

	@Override
	public List<UserDetails> readAllUsers() {
		// TODO Auto-generated method stub
		return dao.readAllUsers();
	}
}