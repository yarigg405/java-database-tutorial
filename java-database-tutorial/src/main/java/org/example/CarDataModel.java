package org.example;

public class CarDataModel {
    private long id;
    private int year;
    private String mark;
    private String model;
    private int horsePower;


    public CarDataModel() {
    }

    public CarDataModel(int year, String mark, String model, int horsePower) {
        this.year = year;
        this.mark = mark;
        this.model = model;
        this.horsePower = horsePower;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    @Override
    public String toString() {
        return "CarDataModel{" +
                "id=" + id +
                ", year=" + year +
                ", mark='" + mark + '\'' +
                ", model='" + model + '\'' +
                ", horsePower=" + horsePower +
                '}';
    }
}
