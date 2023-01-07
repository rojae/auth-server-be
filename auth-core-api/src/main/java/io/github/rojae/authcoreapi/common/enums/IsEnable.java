package io.github.rojae.authcoreapi.common.enums;

public enum IsEnable {
    Y('Y'),
    N('N');

    private final char yn;

    IsEnable(char yn) {
        this.yn = yn;
    }

    public char getYn() {
        return yn;
    }
}
