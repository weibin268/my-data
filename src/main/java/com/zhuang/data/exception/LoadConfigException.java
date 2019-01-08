package com.zhuang.data.exception;

public class LoadConfigException extends RuntimeException {

	private static final long serialVersionUID = -7832976381186001950L;

	public LoadConfigException() {
		super();
	}

	public LoadConfigException(String s) {
		super(s);
	}

	public LoadConfigException(String s,Throwable cause) {
		super(s,cause);
	}
}
