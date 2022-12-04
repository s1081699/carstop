package com.example.myapplication.pojo;

public class ParkingLot {

    private String name;
    private String address;
    private int price;
    private int status; // -1: 不可用, 0: 可租, 1: 已租出

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        switch (status) {
            case 0:
                return "可租";
            case 1:
                return "已租出";
            default:
                return "不可用";
        }
    }

    public void setStatus(int status) {
        this.status = status;
    }
}


