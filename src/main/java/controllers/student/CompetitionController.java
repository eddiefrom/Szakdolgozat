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
import services.CompetitionResultsServiceImpl;
import services.ExerciseServiceImpl;
import services.StudentServiceImpl;
import services.WorksheetServiceImpl;
import services.interfaces.CompetitionResultsService;
import services.interfaces.ExerciseService;
import services.interfaces.StudentService;
import org.apache.commons.lang.time.StopWatch;
import services.interfaces.WorksheetService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class CompetitionController implements Initializable {

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
    private CompetitionExerciseResultsController competitionExerciseResultsController;
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

    public CompetitionController() {

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/student/CompetitionVIEW.fxml"));
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
            CompetitionExerciseResultsController.setActualPoints(actualPoints);
            CompetitionExerciseResultsController.setActualTimes(actualTimes);
            competitionExerciseResultsController = new CompetitionExerciseResultsController();
            competitionExerciseResultsController.createScene(stage);
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
        student.addCompTime(exercise.getTime() - this.time);

        actualPoints.add(gettingPoints);
        actualTimes.add(time);
    }

    private void savePointsToStudent()
    {
        List<CompetitionResults> competitionResults = new ArrayList<>();
        boolean isIn = false;

        for(int i = 0 ; i < student.getCompetitionResults().size(); i++ )
        {
            if (student.getCompetitionResults().get(i).getWorksheetID().equals(worksheet.getId()))
            {
                updateCompetitionList(competitionResults,false);
                checkChanges(competitionResults);
                isIn = true;
            }
        }
        if(!isIn)
        {
            updateCompetitionList(competitionResults,true);
            student.addWorksheet(worksheet);
        }
        student.setAllPoints(student.getEasyPoints() + student.getMediumPoints() + student.getHardPoints()
                                + student.getTrueFalseExercise() + student.getMainExercise());

        studentService.modify(student);
    }

    private  void updateCompetitionList(List<CompetitionResults> competitionResults, boolean isEmpty)
    {
        int db = 0;

        for(CompetitionResults c : student.getCompetitionResults())
        {
            if(c.getWorksheetID().equals(worksheet.getId()))
            {
                db++;
            }
        }
        for(int i = 0;  i < actualPoints.size(); i++)
        {
            competitionResults.add(new CompetitionResults(0.0,0));
            competitionResults.get(i).setGotPoint(actualPoints.get(i));
            competitionResults.get(i).setGotTime(actualTimes.get(i));
            competitionResults.get(i).setWorksheetID(worksheet.getId());
            competitionResults.get(i).setExerciseID(worksheet.getExercises().get(i).getId());
            competitionResults.get(i).setTypes(worksheet.getExercises().get(i).getTypes());

            if(isEmpty || i >= db) {
                student.addCompResult(competitionResults.get(i));

                switch (worksheet.getExercises().get(i).getDifficultyLevel())
                {
                    case KÖNNYŰ: student.addEasyPoints(actualPoints.get(i));break;
                    case KÖZEPES: student.addMediumPoints(actualPoints.get(i));break;
                    case NEHÉZ: student.addHardPoints(actualPoints.get(i));break;
                    default:break;
                }
                switch (worksheet.getExercises().get(i).getTypes())
                {
                    case Általános: student.addMainExercise(actualPoints.get(i));break;
                    case Igaz_Hamis: student.addTrueFalseExercise(actualPoints.get(i));break;
                    default:break;
                }
            }
        }
    }


    private void checkChanges(List<CompetitionResults> competitionResults)
    {
        Double actPoints = 0.0;
        Double compPoints = 0.0;
        List<CompetitionResults> actResults = new ArrayList<>();

        for(CompetitionResults c : student.getCompetitionResults())
        {
            if(c.getWorksheetID() == worksheet.getId()) {
                actResults.add(c);
                actPoints += c.getGotPoint();
            }
        }
        for (int i = 0; i < actResults.size(); i++) {
            if (actResults.get(i).getGotTime() > competitionResults.get(i).getGotTime()) {
                competitionResults.get(i).setGotTime(actResults.get(i).getGotTime());
            }
            if (actResults.get(i).getGotPoint() > competitionResults.get(i).getGotPoint())
            {
                competitionResults.get(i).setGotPoint(actResults.get(i).getGotPoint());

            }

            compPoints += competitionResults.get(i).getGotPoint();

            switch (worksheet.getExercises().get(i).getDifficultyLevel())
            {
                case KÖNNYŰ: student.minusEasyPoints(actResults.get(i).getGotPoint());
                                student.addEasyPoints(competitionResults.get(i).getGotPoint());break;
                case KÖZEPES: student.minusMediumPoints(actResults.get(i).getGotPoint());
                                 student.addMediumPoints(competitionResults.get(i).getGotPoint());break;
                case NEHÉZ: student.minusHardPoints(actResults.get(i).getGotPoint());
                                student.addHardPoints(competitionResults.get(i).getGotPoint());break;
                default:break;
            }
            switch (worksheet.getExercises().get(i).getTypes())
            {
                case Általános: student.minusMainExercise(actResults.get(i).getGotPoint());
                                    student.addMainExercise(competitionResults.get(i).getGotPoint());break;
                case Igaz_Hamis: student.minusTrueFalseExercise(actResults.get(i).getGotPoint());
                                    student.addTrueFalseExercise(competitionResults.get(i).getGotPoint());break;
                default:break;
            }
            competitionResults.get(i).setId(actResults.get(i).getExerciseID());
            student.removeCompResult(actResults.get(i));
            student.addCompResult(competitionResults.get(i));
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
