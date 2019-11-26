package controllers.teacher;

import helper.WindowMoving;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Student;
import models.Worksheet;
import services.WorksheetServiceImpl;
import services.interfaces.WorksheetService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentInfoController implements Initializable {

    private Stage stage;
    private AnchorPane pane;

    private TeacherMainController teacherMainController;
    private static Student student;
    private WindowMoving windowMoving;

    @FXML private Label nameLabel;
    @FXML private Label emailLabel;
    @FXML private Label bornLabel;
    @FXML private Label ageLabel;
    @FXML private Label classNumberLabel;
    @FXML private Label motherNameLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setStudentsInfos();
    }

    public StudentInfoController() {

        teacherMainController = new TeacherMainController();
        windowMoving = new WindowMoving();
        student = teacherMainController.getSelectedStudent();
    }

    public void createScene() throws IOException {

        this.stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/teacher/StudentInfoVIEW.fxml"));
        pane = loader.load();

        windowMoving.moving(stage,pane);

        Scene scene = new Scene(pane);
        scene.getStylesheets().add("css/teacher_back.css");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void rankButtonEvent() throws IOException {
        StudentRankController studentRankController = new StudentRankController();
        studentRankController.createScene();
    }

    @FXML
    public void resultsButtonEvent() throws IOException {
        StudentResultsController studentResultsController = new StudentResultsController();
        studentResultsController.createScene();
    }

    private void setStudentsInfos()
    {
        nameLabel.textProperty().setValue(student.getName());
        emailLabel.textProperty().setValue(student.getEmail());
        bornLabel.textProperty().setValue(String.valueOf(student.getBornDate()));
        ageLabel.textProperty().setValue(String.valueOf(student.getAge()));
        classNumberLabel.textProperty().setValue(String.valueOf(student.getClassNumber()));
        motherNameLabel.textProperty().setValue(student.getParent().getName());
    }

    @FXML
    public void exitButtonEvent(ActionEvent e)  {

        stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        stage.close();
    }

    public  Student getStudent() {
        return student;
    }

    public  void setStudent(Student student) {
        StudentInfoController.student = student;
    }
}
