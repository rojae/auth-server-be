package io.github.rojae.authserver.common.exception;

public class DatabaseTransactionException extends RuntimeException{
  public DatabaseTransactionException() {
    super("Database Transaction is Failed");
  }
}
