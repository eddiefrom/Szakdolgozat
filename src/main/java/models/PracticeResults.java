package models;

import helper.Types;

import javax.persistence.*;

@Entity
public class PracticeResults {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long worksheetID;
    private Long exerciseID;
    private Types types;
    private Double gotPoint;
    private Integer gotTime;
    @ManyToOne
    private Student studentP;

    public PracticeResults() { }

    public PracticeResults(Double gotPoint, Integer gotTime) {
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

    public Types getTypes() {
        return types;
    }

    public void setTypes(Types types) {
        this.types = types;
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

    public Student getStudentP() {
        return studentP;
    }

    public void setStudentP(Student studentP) {
        this.studentP = studentP;
    }
}


