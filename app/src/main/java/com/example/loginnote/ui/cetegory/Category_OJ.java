package com.example.loginnote.ui.cetegory;

public class Category_OJ {
    private int Id;
    private String name;
    private String createDate;

    public Category_OJ(int id, String name, String createDate) {
        Id = id;
        this.name = name;
        this.createDate = createDate;
    }

    public Category_OJ() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Category_OJ{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
