package models;

import helper.DifficultyLevel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Worksheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private DifficultyLevel difficultyLevel;
    private double points;
    private String creator;
    private String categories;
    private String types;
    private int classNumber;
    @OneToMany(mappedBy = "worksheet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exercise> exercises = new ArrayList<>();
    @ManyToMany()
    private List<Student> studentList = new ArrayList<>();

    public Worksheet() {
    }

    public Worksheet(String name, int classNumber, DifficultyLevel difficultyLevel,
                     double points, String creator, String categories, String types,
                     List<Exercise> exercises) {
        this.name = name;
        this.difficultyLevel = difficultyLevel;
        this.points = points;
        this.creator = creator;
        this.categories = categories;
        this.types = types;
        this.exercises = exercises;
        this.classNumber = classNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public void setExercise(Exercise e) {
        this.getExercises().add(e);
    }

    public void setPlusPoints(Double points) {
        this.points += points;
    }

    public void setminusPoints(Double points)
    {
        this.points -= points;
    }

    public int getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

}
