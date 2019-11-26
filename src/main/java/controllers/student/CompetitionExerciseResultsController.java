package controllers.student;

import helper.DifficultyLevel;
import helper.Types;
import helper.WindowMoving;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.CompetitionResults;
import models.Student;
import models.Worksheet;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CompetitionExerciseResultsController implements Initializable {

    private static Stage stage;
    private AnchorPane pane;

    private WindowMoving windowMoving;
    private CompetitionController competitionController;
    private Worksheet worksheet;
    private static List<Double> actualPoints;
    private static List<Integer> actualTimes;

    private static Student student;

    @FXML private ListView<String> exerciseName;
    @FXML private ListView<DifficultyLevel> exerciseDiff;
    @FXML private ListView<String> exerciseCat;
    @FXML private ListView<Types> types;
    @FXML private ListView<Double> exercisePoint;
    @FXML private ListView<Integer> time;
    @FXML private ListView<Double> competitionPoint;
    @FXML private ListView<Integer> competitionTime;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setTable();
    }

    public CompetitionExerciseResultsController() {

        competitionController = new CompetitionController();
        windowMoving = new WindowMoving();
        student = competitionController.getStudent();
        worksheet = competitionController.getWorksheet();
    }

    public void createScene(Stage stage) throws IOException {

        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/student/CompetitionExerciseResultsVIEW.fxml"));
        pane = loader.load();

        windowMoving.moving(stage, pane);

        Scene scene = new Scene(pane);
        scene.getStylesheets().add("css/main_back.css");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void exitButtonEvent(ActionEvent actionEvent) throws IOException {

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    private void setTable() {

        ObservableList<String> names = FXCollections.observableArrayList();
        ObservableList<DifficultyLevel> difficultyLevels = FXCollections.observableArrayList();
        ObservableList<Types> typeslist = FXCollections.observableArrayList();
        ObservableList<String> cats = FXCollections.observableArrayList();
        ObservableList<Double> exPoints = FXCollections.observableArrayList();
        ObservableList<Integer> exTimes = FXCollections.observableArrayList();
        ObservableList<Double> lists = FXCollections.observableArrayList();
        ObservableList<Integer> times = FXCollections.observableArrayList();

        for(int i = 0; i < worksheet.getExercises().size(); i++) {

            names.add(worksheet.getExercises().get(i).getName());
            difficultyLevels.add(worksheet.getExercises().get(i).getDifficultyLevel());
            cats.add(worksheet.getExercises().get(i).getExerciseCat());
            typeslist.add(worksheet.getExercises().get(i).getTypes());
            exPoints.add(worksheet.getExercises().get(i).getPoint());
            exTimes.add(worksheet.getExercises().get(i).getTime());
            lists.add(actualPoints.get(i));
            times.add(actualTimes.get(i));
        }
        exerciseName.setItems(names);
        exerciseDiff.setItems(difficultyLevels);
        exerciseCat.setItems(cats);
        types.setItems(typeslist);
        exercisePoint.setItems(exPoints);
        time.setItems(exTimes);
        competitionPoint.setItems(lists);
        competitionTime.setItems(times);
    }

    public static  List<Double> getActualPoints() {
        return actualPoints;
    }

    public  static void setActualPoints(List<Double> actualPoints) {
        CompetitionExerciseResultsController.actualPoints = actualPoints;
    }

    public static List<Integer> getActualTimes() {
        return actualTimes;
    }

    public static void setActualTimes(List<Integer> actualTimes) {
        CompetitionExerciseResultsController.actualTimes = actualTimes;
    }
}
