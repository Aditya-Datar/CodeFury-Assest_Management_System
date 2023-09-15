package com.company.Exceptions;

public class NoSuchUserException extends Exception {
	public NoSuchUserException() {
		super("No such user");
	}
}
