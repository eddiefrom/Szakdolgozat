package models;

import helper.DifficultyLevel;
import helper.Types;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double point;
    private String url;
    private String goodAnswer;
    private DifficultyLevel difficultyLevel;
    private String name;
    private String exerciseCat;
    private String help;
    private Types types;
    private int time;
    @ManyToOne
    private Worksheet worksheet;

    public Exercise() {
    }

    public Exercise(double point, String url, String goodAnswer, DifficultyLevel difficultyLevel, String name, String exerciseType, String help,
                    Types types, int time, Worksheet worksheet) {
        this.point = point;
        this.url = url;
        this.goodAnswer = goodAnswer;
        this.difficultyLevel = difficultyLevel;
        this.name = name;
        this.exerciseCat = exerciseType;
        this.help = help;
        this.types = types;
        this.time = time;
        this.worksheet = worksheet;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGoodAnswer() {
        return goodAnswer;
    }

    public void setGoodAnswer(String goodAnswer) {
        this.goodAnswer = goodAnswer;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExerciseCat() {
        return exerciseCat;
    }

    public void setExerciseCat(String exerciseCat) {
        this.exerciseCat = exerciseCat;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public Types getTypes() {
        return types;
    }

    public void setTypes(Types types) {
        this.types = types;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Worksheet getWorksheet() {
        return worksheet;
    }

    public void setWorksheet(Worksheet worksheet) {
        this.worksheet = worksheet;
    }
}
