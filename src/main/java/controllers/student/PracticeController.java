package controllers.student;

import helper.Encryptor;
import helper.Types;
import helper.WindowMoving;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import models.*;
import services.*;
import services.interfaces.*;
import org.apache.commons.lang.time.StopWatch;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class PracticeController implements Initializable {

    private static Stage stage;
    private AnchorPane pane;

    private StudentMainController studentMainController;
    private WindowMoving windowMoving;
    private static Worksheet worksheet;
    private int counter;
    private String find;
    private Encryptor encryptor;
    private boolean isNext;
    private static Student student;
    private StudentService studentService;
    private Integer startTime;
    private Timeline timeline;
    private IntegerProperty timeSeconds ;
    private StopWatch stopWatch;
    private boolean timeOut;
    private Double point;
    private Integer time;
    private PracticeExerciseResultsController practiceExerciseResultsController;
    private String trueFalseValue;
    private static List<Double> actualPoints;
    private static List<Integer> actualTimes;

    @FXML private ImageView imageView;
    @FXML private Label points;
    @FXML private Label name;
    @FXML private TextField textField;
    @FXML private Label exerciseSize;
    @FXML private Label counterLabel;
    @FXML private Button trueButton;
    @FXML private Button falseButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setElements();
        timer();
    }

    public PracticeController() {

        studentMainController = new StudentMainController();
        student = studentMainController.getLoggedStudent();
        studentService = new StudentServiceImpl();

        windowMoving = new WindowMoving();
        worksheet = studentMainController.getSelectedWorksheet();
        encryptor = new Encryptor();
        counter = 0;
        trueFalseValue = "Hamis";
        actualTimes = new ArrayList<>();
        actualPoints = new ArrayList<>();
    }
    public void createScene() throws IOException {

        this.stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/student/PracticeVIEW.fxml"));
        pane = loader.load();

        windowMoving.moving(stage,pane);

        Scene scene = new Scene(pane);
        scene.getStylesheets().add("css/teacher_back.css");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void helpButtonEvent()  {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(null);

        if(worksheet.getExercises().get(counter).getHelp().equals("")) {
            alert.setContentText("Nincs segítség megadva!");
            alert.showAndWait();
        }else{
            if(point > 0) {
                points.textProperty().setValue(Double.toString(worksheet.getExercises().get(counter).getPoint() * 0.8));
                point = worksheet.getExercises().get(counter).getPoint() * 0.8;
            }
            alert.setContentText(worksheet.getExercises().get(counter).getHelp());
            alert.showAndWait();
        }
    }

    @FXML
    public void checkIsSuccess() throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(null);

        if (worksheet.getExercises().get(counter).getTypes().equals(Types.Igaz_Hamis)) {

            if (trueFalseValue.equals(encryptor.decrypting(worksheet.getExercises().get(counter).getGoodAnswer()))) {
                alert.setContentText("A válasz jó!");
                alert.showAndWait();
            } else {
                alert.setContentText("A válasz rossz!");
                alert.showAndWait();
            }
        } else if (textField.getText().equals(encryptor.decrypting(
                worksheet.getExercises().get(counter).getGoodAnswer()))) {
            alert.setContentText("A válasz jó!");
            alert.showAndWait();
        } else {
            alert.setContentText("A válasz rossz!");
            alert.showAndWait();
        }
    }

    @FXML
    public void nextExerciseButtonEvent() throws Exception {

        stopWatch.stop();
        time = timeSeconds.get();
        if(timeSeconds.get() != 0) {
            if(isSuccess()) {
                actualPointsSave(point, time);
            }
            else
                actualPointsSave(0.0, 0);
        }
        counter++;
        if(counter < worksheet.getExercises().size())
            setupNextExercise();
        else
        {
            savePointsToStudent();
            PracticeExerciseResultsController.setActualPoints(actualPoints);
            PracticeExerciseResultsController.setActualTimes(actualTimes);
            practiceExerciseResultsController = new PracticeExerciseResultsController();
            practiceExerciseResultsController.createScene(stage);
        }
    }

    @FXML
    public void trueButtonEvent()
    {
        trueFalseValue = "Igaz";
        trueButton.setTextFill(Color.RED);
        falseButton.setTextFill(Color.BLACK);
    }

    @FXML
    public void falseButtonEvent() {

        trueFalseValue = "Hamis";
        trueButton.setTextFill(Color.BLACK);
        falseButton.setTextFill(Color.RED);
    }


    private void trueFalseButtonShow(boolean value)
    {
        trueButton.setVisible(value);
        falseButton.setVisible(value);
    }

    private boolean isSuccess() throws Exception {

        if(worksheet.getExercises().get(counter).getTypes().equals(Types.Igaz_Hamis)) {

            if(trueFalseValue.equals(encryptor.decrypting(worksheet.getExercises().get(counter).getGoodAnswer())))
                return true;
        }
        if(textField.getText().equals(encryptor.decrypting(
                worksheet.getExercises().get(counter).getGoodAnswer())))
            return true;
        return false;
    }

    private void actualPointsSave(Double point, int time)
    {
        Exercise exercise = worksheet.getExercises().get(counter);
        Double gettingPoints = point + time;
        student.addPractTime(exercise.getTime() - this.time);

        actualPoints.add(gettingPoints);
        actualTimes.add(time);
    }

    private void savePointsToStudent()
    {
        List<PracticeResults> practiceResults = new ArrayList<>();
        boolean isIn = false;

        for(int i = 0 ; i < student.getPracticeResults().size(); i++ )
        {
            if (student.getPracticeResults().get(i).getWorksheetID().equals(worksheet.getId()))
            {
                updatePracticeList(practiceResults,false);
                checkChanges(practiceResults);
                isIn = true;
            }
        }
        if(!isIn)
        {
            updatePracticeList(practiceResults,true);
            student.addWorksheet(worksheet);
        }

        studentService.modify(student);
    }

    private  void updatePracticeList(List<PracticeResults> practiceResults, boolean isEmpty)
    {
        int db = 0;

        for(PracticeResults c : student.getPracticeResults())
        {
            if(c.getWorksheetID().equals(worksheet.getId()))
            {
                db++;
            }
        }
        for(int i = 0;  i < actualPoints.size(); i++)
        {
            practiceResults.add(new PracticeResults(0.0,0));
            practiceResults.get(i).setGotPoint(actualPoints.get(i));
            practiceResults.get(i).setGotTime(actualTimes.get(i));
            practiceResults.get(i).setWorksheetID(worksheet.getId());
            practiceResults.get(i).setExerciseID(worksheet.getExercises().get(i).getId());
            practiceResults.get(i).setTypes(worksheet.getExercises().get(i).getTypes());

            if(isEmpty || i >= db) {
                student.addPractResult(practiceResults.get(i));
            }

        }
    }


    private void checkChanges(List<PracticeResults> practiceResults)
    {
        List<PracticeResults> actResults = new ArrayList<>();

        for(PracticeResults p : student.getPracticeResults())
        {
            if(p.getWorksheetID() == worksheet.getId()) {
                actResults.add(p);
            }
        }
        for (int i = 0; i < actResults.size(); i++) {
            if (actResults.get(i).getGotTime() > practiceResults.get(i).getGotTime()) {
                practiceResults.get(i).setGotTime(actResults.get(i).getGotTime());
            }
            if (actResults.get(i).getGotPoint() > practiceResults.get(i).getGotPoint())
            {
                practiceResults.get(i).setGotPoint(actResults.get(i).getGotPoint());
            }
            practiceResults.get(i).setId(actResults.get(i).getExerciseID());
            student.removePractResult(actResults.get(i));
            student.addPractResult(practiceResults.get(i));
        }
    }

    private void setupNextExercise()
    {
        isNext = true;
        point = worksheet.getExercises().get(counter).getPoint();
        find = worksheet.getExercises().get(counter).getUrl();
        try {
            URL file = new File(find).toURI().toURL();
            imageView.setImage(new Image(String.valueOf(file)));
            exerciseSize.textProperty().setValue(
                    (counter+1) +"/"+ worksheet.getExercises().size());
            startTime = worksheet.getExercises().get(counter).getTime();
            isNext = false;
            timeOut = false;
            stopWatch.reset();
            if(worksheet.getExercises().get(counter).getTypes().equals(Types.Igaz_Hamis)) {
                trueFalseButtonShow(true);
                textField.setDisable(true);
            }
            else {
                trueFalseButtonShow(false);
                textField.setDisable(false);
            }
            timer();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void timer() {
        stopWatch.start();
        timeSeconds = new SimpleIntegerProperty(startTime);
        counterLabel.setVisible(true);
        counterLabel.textProperty().bind(timeSeconds.asString());

        if (timeline != null || isNext) {
            timeline.stop();
        }
        timeSeconds.set(startTime);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(
                        Duration.seconds(startTime + 1),
                        new KeyValue(timeSeconds, 0)));
        timeline.playFromStart();
        if (timeSeconds.get() == 0) {
            textField.setEditable(false);
            timeOut = true;
        }
    }


    private void setElements()
    {
        if(worksheet.getExercises().size() > 0) {
            find = worksheet.getExercises().get(counter).getUrl();

            try {
                URL file = new File(find).toURI().toURL();
                imageView.setImage(new Image(String.valueOf(file)));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            if(worksheet.getExercises().get(0).getTypes().equals(Types.Igaz_Hamis))
                textField.setDisable(true);
            else
                textField.setDisable(false);

            points.textProperty().setValue(Double.toString(worksheet.getExercises().get(0).getPoint()));
            point = worksheet.getExercises().get(0).getPoint();
            textField.textProperty().setValue("");
            name.textProperty().setValue(worksheet.getName());
            exerciseSize.textProperty().setValue(
                    (1) +"/"+ worksheet.getExercises().size());
            counterLabel.textProperty()
                    .setValue(String.valueOf(worksheet.getExercises().get(counter).getTime()));
            startTime = worksheet.getExercises().get(0).getTime();
            stopWatch = new StopWatch();
            timeOut = false;
            isNext = false;
            if(worksheet.getExercises().get(0).getTypes().equals(Types.Igaz_Hamis)){
                trueFalseButtonShow(true);
                textField.setDisable(true);
            }
            else {
                trueFalseButtonShow(false);
                textField.setDisable(false);
            }
        }
        else {

        }
    }

    public Worksheet getWorksheet() {
        return worksheet;
    }

    public Student getStudent() {
        return student;
    }

    @FXML
    public void exitButtonEvent(ActionEvent actionEvent) throws IOException {

        studentService.close();
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

}
