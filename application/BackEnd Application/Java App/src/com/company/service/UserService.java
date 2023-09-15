package com.company.service;

import java.util.List;

import com.company.Exceptions.NoSuchUserException;
import com.company.Exceptions.UserAlreadyExistsException;
import com.company.model.UserDetails;

public interface UserService {
	
	public boolean createUser(UserDetails user) throws UserAlreadyExistsException;
	public UserDetails readUser(int uniqueId) throws NoSuchUserException;
	public boolean deleteUser(int uniqueId) throws NoSuchUserException ;
	public UserDetails updateUser(int uniqueId, UserDetails user) ;
	public List<UserDetails> readAllUsers();
}
