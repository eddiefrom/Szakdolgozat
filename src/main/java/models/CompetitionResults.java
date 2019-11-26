package models;


import helper.Types;

import javax.persistence.*;

@Entity
public class CompetitionResults {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long worksheetID;
    private Long exerciseID;
    private Types types;
    private Double gotPoint;
    private Integer gotTime;
    @ManyToOne
    private Student student;

    public CompetitionResults() {
    }

    public CompetitionResults(Double gotPoint, Integer gotTime) {
        this.gotPoint = gotPoint;
        this.gotTime = gotTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorksheetID() {
        return worksheetID;
    }

    public void setWorksheetID(Long worksheetID) {
        this.worksheetID = worksheetID;
    }

    public Long getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(Long exerciseID) {
        this.exerciseID = exerciseID;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Double getGotPoint() {
        return gotPoint;
    }

    public void setGotPoint(Double gotPoint) {
        this.gotPoint = gotPoint;
    }

    public Integer getGotTime() {
        return gotTime;
    }

    public void setGotTime(Integer gotTime) {
        this.gotTime = gotTime;
    }

    public Types getTypes() {
        return types;
    }

    public void setTypes(Types types) {
        this.types = types;
    }

}



