package Models;

import java.time.LocalDate;

public class Course {
    private String id;
    private String name;
    private String lecture;
    private LocalDate year;
    private String notes;


    // constructor


    public Course(String id, String name, String lecture, LocalDate year, String notes) {
        this.id = id;
        this.name = name;
        this.lecture = lecture;
        this.year = year;
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

    public String getLecture() {
        return lecture;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
    }

    public LocalDate getYear() {
        return year;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
