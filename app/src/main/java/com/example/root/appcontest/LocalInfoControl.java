package com.example.root.appcontest;

public class LocalInfoControl {
    /**
     *  로컬 페이지 boolean 변수
     */
    private boolean isLocalInfo = false;

    public LocalInfoControl () {

    }

    /**
     * 로컬 페이지 존재 판단
     *
     * @return isLocalInfo ? true : false
     */
    public boolean isLocalInfoExist() {
        if (isLocalInfo)
            return true;
        else
            return false;
    }
}
