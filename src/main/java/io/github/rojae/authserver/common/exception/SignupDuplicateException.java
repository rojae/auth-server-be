package io.github.rojae.authserver.common.exception;

public class SignupDuplicateException extends RuntimeException{
  public SignupDuplicateException() {
    super("Already Enrolled User Information");
  }

}
