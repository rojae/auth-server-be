package io.github.rojae.authserver.common.exception;

public class InvalidKakaoTokenException extends RuntimeException{

  public InvalidKakaoTokenException() {
    super("User's Kakao Token is Invalid");
  }

}
