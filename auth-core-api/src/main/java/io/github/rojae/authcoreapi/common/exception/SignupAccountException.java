package io.github.rojae.authcoreapi.common.exception;

public class SignupAccountException extends RuntimeException{
    public SignupAccountException() {
        super("Account signup save exception");
    }
}
