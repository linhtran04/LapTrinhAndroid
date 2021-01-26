package com.example.loginnote.ui.priority;

import androidx.annotation.NonNull;

public class PriorityOJ {
    private int Id;
    private String name;
    private String createDate;

    public PriorityOJ(int id, String name, String createDate) {
        Id = id;
        this.name = name;
        this.createDate = createDate;
    }

    public PriorityOJ() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PriorityOJ{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
