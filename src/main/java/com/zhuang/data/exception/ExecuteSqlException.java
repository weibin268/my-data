package com.zhuang.data.exception;

public class ExecuteSqlException extends RuntimeException {

	private static final long serialVersionUID = 5100335351714923332L;

	public ExecuteSqlException() {
		super();
	}

	public ExecuteSqlException(String s) {
		super(s);
	}

	public ExecuteSqlException(String s,Throwable cause) {
		super(s,cause);
	}
}
