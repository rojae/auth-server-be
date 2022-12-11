package io.github.rojae.authserver.common.exception;

public class LoginAccountInvalidException extends RuntimeException{
  public LoginAccountInvalidException() {
    super("Invalid Account Data");
  }
}
