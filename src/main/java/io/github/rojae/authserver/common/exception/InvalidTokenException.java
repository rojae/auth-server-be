package io.github.rojae.authserver.common.exception;

public class InvalidTokenException extends RuntimeException{

  public InvalidTokenException() {
    super("Api Header's Token is Invalid");
  }
}
