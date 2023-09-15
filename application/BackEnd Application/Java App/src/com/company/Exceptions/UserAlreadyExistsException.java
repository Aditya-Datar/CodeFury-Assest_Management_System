package com.company.Exceptions;

public class UserAlreadyExistsException extends Exception {
	
	public UserAlreadyExistsException()
	{
		super("User with this mail already exists...use another email ID");
	}
}
