package com.secbow.work.book.vo;

public class HomeVo {
    private int draw;
    private String name;
    private int type;


    public HomeVo(int draw, String name, int type) {
        this.draw = draw;
        this.name = name;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
