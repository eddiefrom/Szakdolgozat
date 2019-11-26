package controllers;

import controllers.parent.ParentMainController;
import controllers.student.StudentMainController;
import controllers.teacher.TeacherMainController;
import helper.Encryptor;
import helper.EntityManagerFactoryHelper;
import helper.WindowMoving;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Parent;
import models.Student;
import models.Teacher;
import services.ParentServiceImpl;
import services.StudentServiceImpl;
import services.TeacherServiceImpl;
import services.interfaces.ParentService;
import services.interfaces.StudentService;
import services.interfaces.TeacherService;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    static Stage stage;
    private AnchorPane pane;
    private Encryptor encryptor;
    private WindowMoving windowMoving;

    private StudentService studentService;
    private TeacherService teacherService;
    private ParentService parentService;

    private static StudentMainController studentMainController;
    private static TeacherMainController teacherMainController;
    private static ParentMainController parentMainController;

    private static Student loginStudent;
    private static Teacher loginTeacher;
    private static Parent loginParent;

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorMessage;
    @FXML private CheckBox studentCheck;
    @FXML private CheckBox teacherCheck;
    @FXML private CheckBox parentCheck;

    public MainController() {

        windowMoving = new WindowMoving();

        studentService = new StudentServiceImpl();
        parentService = new ParentServiceImpl();
        teacherService = new TeacherServiceImpl();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        emailField.setText("");
        passwordField.setText("");
        errorMessage.setText("");

        encryptor = new Encryptor();

    }

    public void createScene(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainVIEW.fxml"));
        pane = loader.load();


        windowMoving.moving(stage,pane);

        Scene scene = new Scene(pane);
        scene.getStylesheets().add("css/main_back.css");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("LearningRoom");
        stage.setResizable(false);
        stage.show();
    }



    @FXML
    public void loginUser(ActionEvent actionEvent) {
        String email = emailField.getText();
        String password = passwordField.getText();

        emailField.setStyle("-fx-border-color: none ; -fx-border-width: 0px ;");
        passwordField.setStyle("-fx-border-color: none ; -fx-border-width: 0px ;");

        try {

            if (parentCheck.isSelected() && parentService.findByEmail(email) != null) {

               if(passwordCheck(password, parentService.findByEmail(email).getPassword()))
                {
                    parentMainController = new ParentMainController();
                    loginParent = parentService.findByEmail(email);
                    parentMainController.createScene(stage);
                }
                else
                {
                    errorMessage.setText("Rossz jelszó!");
                    passwordField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                }

            }
            if (studentCheck.isSelected() && studentService.findByEmail(email) != null) {

                if(passwordCheck(password, studentService.findByEmail(email).getPassword()))
                {
                    studentMainController = new StudentMainController();
                    loginStudent = studentService.findByEmail(email);
                    studentMainController.createScene(stage);
                }
                else
                {
                    errorMessage.setText("Rossz jelszó!");
                    passwordField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                }

            }
            if (teacherCheck.isSelected() && teacherService.findByEmail(email) != null) {

                if(passwordCheck(password, teacherService.findByEmail(email).getPassword()))
                {
                    teacherMainController = new TeacherMainController();
                    loginTeacher = teacherService.findByEmail(email);
                    teacherMainController.createScene(stage);
                }
                else
                {
                    errorMessage.setText("Rossz jelszó!");
                    passwordField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                }

            }

        }
        catch (NoResultException e)
        {
            errorMessage.setText("Rossz email!");
            emailField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void checkBoxesState()
    {

        if(teacherCheck.isSelected()){
            studentCheck.setDisable(true);
            parentCheck.setDisable(true);
        }

        if(!teacherCheck.isSelected()){
            studentCheck.setDisable(false);
            parentCheck.setDisable(false);
        }

        if(studentCheck.isSelected()){
            teacherCheck.setDisable(true);
            parentCheck.setDisable(true);
        }

        if(!studentCheck.isSelected() && !teacherCheck.isSelected() && !parentCheck.isSelected()){
            teacherCheck.setDisable(false);
            parentCheck.setDisable(false);
        }

        if(parentCheck.isSelected()){
            teacherCheck.setDisable(true);
            studentCheck.setDisable(true);
        }

        if(!parentCheck.isSelected() && !studentCheck.isSelected() && !teacherCheck.isSelected()){
            teacherCheck.setDisable(false);
            studentCheck.setDisable(false);
        }


    }

    @FXML
    public void exitButton()
    {
        EntityManagerFactoryHelper.getInstance().close();
        stage.close();
    }


    private boolean passwordCheck(String password1, String password2) throws Exception {

        if(password1.equals(getDecryptedPassword(password2)))
            return true;
        return false;
    }

    private String getDecryptedPassword(String password) throws Exception {

        return encryptor.decrypting(password);
    }


    public Student getLoginStudent() {
        return loginStudent;
    }

    public Teacher getLoginTeacher() {
        return loginTeacher;
    }

    public Parent getLoginParent() {
        return loginParent;
    }

}
