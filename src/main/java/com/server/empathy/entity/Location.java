package com.server.empathy.entity;

public enum Location {
    // 서울 인천 대전 대구 광주 부산 울산 세종시
    // 경기도 강원도 충청북도 충청남도 경상북도 경상남도 전라북도 전라남도 제주도
    Seoul(0,"Seoul"),
    Incheon(1,"Incheon"),
    Daejeon(2,"Daejeon"),
    Daegu(3,"Daegue"),
    Gwangju(4,"Gwangju"),
    Busan(5,"Busan"),
    Ulsan(6,"Ulsan"),
    Sejong(7,"Sejong"),
    GyeonggiDo(8,"GyeonggiDo"),
    GangwonDo(9,"GangwonDo"),
    ChungcheongbukDo(10,"ChungcheongbukDo"),
    ChungcheongnamDo(11,"ChungcheongnamDo"),
    GyeongsangbukDo(12,"GyeongsangbukDo"),
    GyeongsangnamDo(13,"GyeongsangnamDo"),
    Jeollabukdo(14,"Jeollabukdo"),
    JeollanamDo(15,"JeollanamDo"),
    Jejudo(16,"Jejudo");

    private int code;
    private String value;

    Location(){ }
    Location(int code , String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode(){
        return code;
    }

    public String getValue(){
        return value;
    }

}
