package com.server.empathy.entity;

public enum WeekDaysKor {
    Sunday(0,"일요일의"),
    Monday(1,"월요일의"),
    Tuesday(2,"화요일의"),
    Wednesday(3,"수요일의"),
    Thursday(4,"목요일의"),
    Friday(5,"금요일의"),
    Saturday(6,"토요일의");

    private int code;
    private String value;

//    WeekDaysKor(){ }
    WeekDaysKor(int code , String value) {
        this.code = code;
        this.value = value;
    }

    public static String getText(int check){
        String result = "";
        switch (check) {
            case 0:
                result = Sunday.getValue();
                break;
            case 1:
                result = Monday.getValue();
                break;
            case 2:
                result = Tuesday.getValue();
                break;
            case 3:
                result = Wednesday.getValue();
                break;
            case 4:
                result = Thursday.getValue();
                break;
            case 5:
                result = Friday.getValue();
                break;
            case 6:
                result = Saturday.getValue();
                break;
            default:
                result = Saturday.getValue();
                break;

        }
        return result;
    }

    public int getCode(){
        return code;
    }

    public String getValue(){
        return value;
    }
}
