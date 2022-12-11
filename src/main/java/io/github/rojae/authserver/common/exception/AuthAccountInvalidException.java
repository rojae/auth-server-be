package io.github.rojae.authserver.common.exception;

public class AuthAccountInvalidException extends RuntimeException{
  public AuthAccountInvalidException() {
    super("Authenticate Account's Data Invalid");
  }

}
