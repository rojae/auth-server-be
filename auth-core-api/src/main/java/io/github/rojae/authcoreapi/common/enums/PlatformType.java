package io.github.rojae.authcoreapi.common.enums;

public enum PlatformType {
    NONSOCIAL("소셜 아님"),
    KAKAO("소셜-카카오"),
    UNKNOWN("알수없음");

    private String desc;

    PlatformType(String desc) {
        this.desc = desc;
    }

    public static PlatformType valueOfName(String name){
        for(var e : PlatformType.values()){
            if(e.name().equals(name)){
                return e;
            }
        }
        return UNKNOWN;
    }

}
