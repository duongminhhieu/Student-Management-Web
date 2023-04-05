package Models;

import java.util.Date;

public class Student {
    private String id ;
    private String name;
    private float grade;
    private Date birdthday;
    private String address;
    private String notes;

    // Constructor


    public Student(String id, String name, float grade, Date birdthday, String address, String notes) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.birdthday = birdthday;
        this.address = address;
        this.notes = notes;
    }

    // Getter and Setter
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

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public Date getBirdthday() {
        return birdthday;
    }

    public void setBirdthday(Date birdthday) {
        this.birdthday = birdthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
