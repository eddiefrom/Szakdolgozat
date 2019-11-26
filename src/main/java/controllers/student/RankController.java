package controllers.student;

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
import services.StudentServiceImpl;
import services.interfaces.StudentService;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class RankController implements Initializable {

    private Stage stage;
    private AnchorPane pane;

    private WindowMoving windowMoving;
    private static Student student;
    private StudentMainController studentMainController;
    private StudentService studentService;


    @FXML private ListView<Integer> rankNumber;
    @FXML private ListView<String> name;
    @FXML private ListView<Double> allPoints;

    @FXML private ListView<Integer> rankNumberEasy;
    @FXML private ListView<String> nameEasy;
    @FXML private ListView<Double> allPointsEasy;

    @FXML private ListView<Integer> rankNumberMedium;
    @FXML private ListView<String> nameMedium;
    @FXML private ListView<Double> allPointsMedium;

    @FXML private ListView<Integer> rankNumberHard;
    @FXML private ListView<String> nameHard;
    @FXML private ListView<Double> allPointsHard;

    @FXML private ListView<Integer> rankNumberMain;
    @FXML private ListView<String> nameMain;
    @FXML private ListView<Double> allPointsMain;

    @FXML private ListView<Integer> rankNumberTrueFalse;
    @FXML private ListView<String> nameTrueFalse;
    @FXML private ListView<Double> allPointsTrueFalse;

    @FXML private ListView<Integer> rankNumberClass;
    @FXML private ListView<String> nameClass;
    @FXML private ListView<Double> allPointsClass;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setAbsolute();
        setEasy();
        setMedium();
        setHard();
        setMain();
        setTrueFalse();
        setClassNumberRank();
    }

    public RankController() {

        windowMoving = new WindowMoving();
        studentMainController = new StudentMainController();
        student = studentMainController.getLoggedStudent();
        studentService = new StudentServiceImpl();
    }

    @FXML
    public void createScene() throws IOException {

        this.stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/student/RankVIEW.fxml"));
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

    private void setAbsolute()
    {
        List<Double> pointsList = new ArrayList<>();
        List<String> studentList = new ArrayList<>();
        List<Integer> rankList = new ArrayList<>();

        ObservableList<Integer> integerObservableList = FXCollections.observableArrayList();
        ObservableList<Double> doubleObservableList = FXCollections.observableArrayList();
        ObservableList<String> studentObservableList = FXCollections.observableArrayList();

        sorting(pointsList,studentList,rankList,"all");

        integerObservableList.addAll(rankList);
        doubleObservableList.addAll(pointsList);
        studentObservableList.addAll(studentList);

        rankNumber.setItems(integerObservableList);
        name.setItems(studentObservableList);
        allPoints.setItems(doubleObservableList);
    }

    private void setClassNumberRank()
    {
        List<Double> pointsList = new ArrayList<>();
        List<String> studentList = new ArrayList<>();
        List<Integer> rankList = new ArrayList<>();

        ObservableList<Integer> integerObservableList = FXCollections.observableArrayList();
        ObservableList<Double> doubleObservableList = FXCollections.observableArrayList();
        ObservableList<String> studentObservableList = FXCollections.observableArrayList();

        sorting(pointsList,studentList,rankList,"class");

        integerObservableList.addAll(rankList);
        doubleObservableList.addAll(pointsList);
        studentObservableList.addAll(studentList);

        rankNumberClass.setItems(integerObservableList);
        nameClass.setItems(studentObservableList);
        allPointsClass.setItems(doubleObservableList);
    }

    private void setEasy()
    {
        List<Double> pointsList = new ArrayList<>();
        List<String> studentList = new ArrayList<>();
        List<Integer> rankList = new ArrayList<>();

        ObservableList<Integer> integerObservableList = FXCollections.observableArrayList();
        ObservableList<Double> doubleObservableList = FXCollections.observableArrayList();
        ObservableList<String> studentObservableList = FXCollections.observableArrayList();

        sorting(pointsList,studentList,rankList,"easy");

        integerObservableList.addAll(rankList);
        doubleObservableList.addAll(pointsList);
        studentObservableList.addAll(studentList);

        rankNumberEasy.setItems(integerObservableList);
        nameEasy.setItems(studentObservableList);
        allPointsEasy.setItems(doubleObservableList);
    }

    private void setMedium()
    {
        List<Double> pointsList = new ArrayList<>();
        List<String> studentList = new ArrayList<>();
        List<Integer> rankList = new ArrayList<>();

        ObservableList<Integer> integerObservableList = FXCollections.observableArrayList();
        ObservableList<Double> doubleObservableList = FXCollections.observableArrayList();
        ObservableList<String> studentObservableList = FXCollections.observableArrayList();

        sorting(pointsList,studentList,rankList,"medium");

        integerObservableList.addAll(rankList);
        doubleObservableList.addAll(pointsList);
        studentObservableList.addAll(studentList);

        rankNumberMedium.setItems(integerObservableList);
        nameMedium.setItems(studentObservableList);
        allPointsMedium.setItems(doubleObservableList);
    }

    private void setHard()
    {
        List<Double> pointsList = new ArrayList<>();
        List<String> studentList = new ArrayList<>();
        List<Integer> rankList = new ArrayList<>();

        ObservableList<Integer> integerObservableList = FXCollections.observableArrayList();
        ObservableList<Double> doubleObservableList = FXCollections.observableArrayList();
        ObservableList<String> studentObservableList = FXCollections.observableArrayList();

        sorting(pointsList,studentList,rankList,"hard");

        integerObservableList.addAll(rankList);
        doubleObservableList.addAll(pointsList);
        studentObservableList.addAll(studentList);

        rankNumberHard.setItems(integerObservableList);
        nameHard.setItems(studentObservableList);
        allPointsHard.setItems(doubleObservableList);
    }

    private void setMain()
    {
        List<Double> pointsList = new ArrayList<>();
        List<String> studentList = new ArrayList<>();
        List<Integer> rankList = new ArrayList<>();

        ObservableList<Integer> integerObservableList = FXCollections.observableArrayList();
        ObservableList<Double> doubleObservableList = FXCollections.observableArrayList();
        ObservableList<String> studentObservableList = FXCollections.observableArrayList();

        sorting(pointsList,studentList,rankList,"main");

        integerObservableList.addAll(rankList);
        doubleObservableList.addAll(pointsList);
        studentObservableList.addAll(studentList);

        rankNumberMain.setItems(integerObservableList);
        nameMain.setItems(studentObservableList);
        allPointsMain.setItems(doubleObservableList);
    }

    private void setTrueFalse()
    {
        List<Double> pointsList = new ArrayList<>();
        List<String> studentList = new ArrayList<>();
        List<Integer> rankList = new ArrayList<>();

        ObservableList<Integer> integerObservableList = FXCollections.observableArrayList();
        ObservableList<Double> doubleObservableList = FXCollections.observableArrayList();
        ObservableList<String> studentObservableList = FXCollections.observableArrayList();

        sorting(pointsList,studentList,rankList,"truefalse");

        integerObservableList.addAll(rankList);
        doubleObservableList.addAll(pointsList);
        studentObservableList.addAll(studentList);

        rankNumberTrueFalse.setItems(integerObservableList);
        nameTrueFalse.setItems(studentObservableList);
        allPointsTrueFalse.setItems(doubleObservableList);
    }


    private void sorting(List<Double> pointsList,List<String> studentList,List<Integer> rankList, String string)
    {
        Map<String,Double> namesAndPoints = new HashMap<>();
        Map<String, Double> result = new LinkedHashMap<>();

        if(string.equals("all"))
            for(int i = 0; i < studentService.findAllStudent().size(); i++) {
                namesAndPoints.put(studentService.findAllStudent().get(i).getName(),
                        studentService.findAllStudent().get(i).getAllPoints());
                rankList.add(i + 1);
            }
        else
        for(int i = 0; i < studentService.findByClass(student.getClassNumber()).size(); i++)
        {
            switch (string){

                case "class" :  namesAndPoints.put(studentService.findByClass(student.getClassNumber()).get(i).getName(),
                        studentService.findByClass(student.getClassNumber()).get(i).getAllPoints() );break;
                case "easy" :   namesAndPoints.put(studentService.findByClass(student.getClassNumber()).get(i).getName(),
                        studentService.findByClass(student.getClassNumber()).get(i).getEasyPoints() );break;
                case "medium" :   namesAndPoints.put(studentService.findByClass(student.getClassNumber()).get(i).getName(),
                        studentService.findByClass(student.getClassNumber()).get(i).getMediumPoints() );break;
                case "hard" :   namesAndPoints.put(studentService.findByClass(student.getClassNumber()).get(i).getName(),
                        studentService.findByClass(student.getClassNumber()).get(i).getHardPoints() );break;
                case "main" :   namesAndPoints.put(studentService.findByClass(student.getClassNumber()).get(i).getName(),
                        studentService.findByClass(student.getClassNumber()).get(i).getMainExercise() );break;
                case "truefalse" :   namesAndPoints.put(studentService.findByClass(student.getClassNumber()).get(i).getName(),
                        studentService.findByClass(student.getClassNumber()).get(i).getTrueFalseExercise() );break;
                        default:break;
            }

            rankList.add(i + 1);
        }
        namesAndPoints.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));

        for(Map.Entry<String, Double> r : result.entrySet())
        {
            studentList.add(r.getKey());
            pointsList.add(r.getValue());
        }
    }

    @FXML
    public void exitButtonEvent(ActionEvent actionEvent) throws IOException {

        studentService.close();
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
