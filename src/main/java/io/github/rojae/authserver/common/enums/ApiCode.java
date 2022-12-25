package io.github.rojae.authserver.common.enums;

public enum ApiCode {

    OK("A0000", "성공"),

    INVALID_HEADER("A1000", "헤더에 데이터가 존재하지 않습니다"),
    INVALID_BODY("A1001", "바디 데이터가 존재하지 않습니다"),
    INVALID_QUERYSTRING("A1002", "쿼리스트링 데이터가 존재하지 않습니다"),
    INVALID_QUERYSTRING_HEADER("A1003", "쿼리스트링 혹은 헤더가 존재하지 않습니다"),

    UNKNOWN("UNKNOWN", "알 수 없음");

    private final String code;
    private final String reason;

    ApiCode(String code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public String getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

    public static ApiCode ofCode(String code){
        for(var e : ApiCode.values()){
            if(e.getCode().equals(code)){
                return e;
            }
        }
        return UNKNOWN;
    }
}
