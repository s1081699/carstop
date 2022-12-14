package com.example.myapplication.pojo;

public class ParkingLot {
    private String id;
    private String name;
    private String address;
    private int price;
    private int status; // -1: 不可用, 0: 可租, 1: 已租出

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public int getStatus() {
        return status;
    }

    /**
     *
     * @param status 0: 可租, 1: 已租出, -1: 不可用
     */
    public void setStatus(int status) {
        this.status = status;
    }
}


