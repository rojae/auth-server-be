package io.github.rojae.authcoreapi.common.enums;

public enum IsAuth {
    Y('Y'),
    N('N');

    private final char yn;

    IsAuth(char yn) {
        this.yn = yn;
    }

    public char getYn() {
        return yn;
    }
}
