package io.github.rojae.authcoreapi.common.exception;

public class LoginAccountInvalidException extends RuntimeException{
  public LoginAccountInvalidException() {
    super("Invalid Account Data");
  }
}
