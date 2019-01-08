package com.zhuang.data.exception;

public class GetConnectionException extends RuntimeException {

	private static final long serialVersionUID = -2631261586237684157L;

	public GetConnectionException() {
		super();
	}

	public GetConnectionException(String s) {
		super(s);
	}

	public GetConnectionException(String s,Throwable cause) {
		super(s,cause);
	}
}
