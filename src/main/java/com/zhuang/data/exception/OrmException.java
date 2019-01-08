package com.zhuang.data.exception;

public class OrmException extends RuntimeException {

	private static final long serialVersionUID = 5100335351714923332L;

	public OrmException() {
		super();
	}

	public OrmException(String s) {
		super(s);
	}

	public OrmException(String s,Throwable cause) {
		super(s,cause);
	}
}
