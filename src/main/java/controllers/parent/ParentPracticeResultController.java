package controllers.parent;

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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Student;
import models.Worksheet;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ParentPracticeResultController implements Initializable {

    private Stage stage;
    private AnchorPane pane;

    private ParentResultController resultsController;
    private WindowMoving windowMoving;
    private static Worksheet worksheet;
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

    public ParentPracticeResultController() {

        stage = new Stage();
        resultsController = new ParentResultController();
        windowMoving = new WindowMoving();
        worksheet = resultsController.getSelectedWorksheet();
        student = resultsController.getStudent();
    }

    public void createScene() throws IOException {

        stage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/parent/ParentPracResultVIEW.fxml"));
        pane = loader.load();

        windowMoving.moving(stage,pane);

        Scene scene = new Scene(pane);
        scene.getStylesheets().add("css/main_back.css");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("LearningRoom");
        stage.setResizable(false);
        stage.show();
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
            for(int j = 0 ; j < student.getPracticeResults().size(); j++)
            {
                if(worksheet.getExercises().get(i).getId().equals(student.getPracticeResults().get(j).getExerciseID())
                        && worksheet.getId().equals(student.getPracticeResults().get(j).getWorksheetID()))
                {
                    names.add(worksheet.getExercises().get(i).getName());
                    difficultyLevels.add(worksheet.getExercises().get(i).getDifficultyLevel());
                    cats.add(worksheet.getExercises().get(i).getExerciseCat());
                    typeslist.add(worksheet.getExercises().get(i).getTypes());
                    exPoints.add(worksheet.getExercises().get(i).getPoint());
                    exTimes.add(worksheet.getExercises().get(i).getTime());
                    lists.add(student.getPracticeResults().get(j).getGotPoint());
                    times.add(student.getPracticeResults().get(j).getGotTime());
                }
            }
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


    @FXML
    public void exitButtonEvent(ActionEvent actionEvent)  {

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

}
