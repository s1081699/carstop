package com.example.myapplication.pojo;

public class Member {
    private String name;
    private int level; //0: 使用者, 1:管理者

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
