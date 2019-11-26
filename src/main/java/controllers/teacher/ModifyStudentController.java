package controllers.teacher;

import helper.Encryptor;
import helper.WindowMoving;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Parent;
import models.Student;
import services.ParentServiceImpl;
import services.StudentServiceImpl;
import services.interfaces.ParentService;
import services.interfaces.StudentService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class ModifyStudentController implements Initializable {

    private static Stage stage;
    private AnchorPane pane;
    private Student student;
    private Parent parent;
    private ParentService parentService;
    private StudentService studentService;

    private Encryptor encryptor;
    private WindowMoving windowMoving;
    private TeacherMainController teacherMainController;

    @FXML private TextField name;
    @FXML private TextField email;
    @FXML private TextField bornDate;
    @FXML private TextField classNumber;
    @FXML private TextField parentName;
    @FXML private TextField parentEmail;
    @FXML private PasswordField password;
    @FXML private PasswordField passwordAgain;
    @FXML private Label errorLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        name.textProperty().setValue(student.getName());
        email.textProperty().setValue(student.getEmail());
        bornDate.textProperty().setValue(String.valueOf(student.getBornDate()));
        parentName.textProperty().setValue(student.getParent().getName());
        parentEmail.textProperty().setValue(student.getParent().getEmail());
        classNumber.textProperty().setValue(String.valueOf(student.getClassNumber()));
        password.textProperty().setValue("");
        passwordAgain.textProperty().setValue("");
        errorLabel.textProperty().setValue("");
    }

    public ModifyStudentController() {

        teacherMainController = new TeacherMainController();
        student = teacherMainController.getSelectedStudent();
        windowMoving = new WindowMoving();
        encryptor = new Encryptor();
        parentService = new ParentServiceImpl();
        studentService = new StudentServiceImpl();
    }

    public void createScene(Stage stage) throws IOException {

        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/teacher/ModifyStudentVIEW.fxml"));
        pane = loader.load();

        windowMoving.moving(stage,pane);

        Scene scene = new Scene(pane);
        scene.getStylesheets().add("css/main_back.css");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void createStudent(ActionEvent e) throws Exception {

        String error = fieldCheck();

        if(error.equals("")) {

            setParentModify();
            setStudentModify();
            parentService.modify(parent);
            studentService.modify(student);

            parentService.close();
            teacherMainController.createScene(stage);
        }
        else
        {
            errorLabel.textProperty().setValue(error);
        }
    }

    @FXML
    public void exitButtonEvent(ActionEvent e) throws IOException {

        parentService.close();
        teacherMainController.createScene(stage);
    }

    private void setParentModify()
    {
        Parent parent = parentService.findByEmail(parentEmail.getText());
        this.parent = parent;
        this.parent.setName(parentName.getText());
        this.parent.setEmail(parentEmail.getText());
    }

    private void setStudentModify() throws Exception {

        student.setName(name.getText());
        student.setEmail(email.getText());
        student.setPassword(encryptor.encrypting(password.getText()));
        student.setBornDate(LocalDate.parse(bornDate.getText()));
        student.setClassNumber(Integer.parseInt(classNumber.getText()));
    }

    private String fieldCheck()
    {
        String error = "";
        textfieldsBorderSetup();

        if(name.getText().equals("")|| email.getText().isEmpty() || bornDate.getText().isEmpty() || classNumber.getText().isEmpty()
                || parentName.getText().isEmpty() || parentEmail.getText().isEmpty()
                || password.getText().isEmpty() || passwordAgain.getText().isEmpty()) {
            error += "Minden mező kitöltése kötelező!\n";
        }

        if(!password.getText().equals(passwordAgain.getText())) {
            error += "Jelszavak nem egyeznek!\n";
            password.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            passwordAgain.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
        }

        for (Student s : studentService.findAllStudent()) {
            if(s.getEmail().equals(email.getText()) && !s.getEmail().equals(student.getEmail())) {
                error += "Ilyen email már van!";
                email.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            }
        }

        try{

            LocalDate localDate;
            if(!bornDate.getText().isEmpty())
                localDate = LocalDate.parse(bornDate.getText());
        }
        catch (DateTimeParseException e)
        {
            error+= "Hibás dátum formátum!\n";
            bornDate.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            e.getMessage();

        }
        try{
            Number n = Integer.parseInt(classNumber.getText());

        }catch (NumberFormatException e)
        {
            error += "Hibás évfolyam érték!";
            classNumber.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
        }

        return  error;
    }

    private void textfieldsBorderSetup()
    {
        bornDate.setStyle("-fx-border-color: white ; -fx-border-width: 0px ;");
        email.setStyle("-fx-border-color: white ; -fx-border-width: 0px ;");
        classNumber.setStyle("-fx-border-color: white ; -fx-border-width: 0px ;");
        password.setStyle("-fx-border-color: white ; -fx-border-width: 0px ;");
        passwordAgain.setStyle("-fx-border-color: white ; -fx-border-width: 0px ;");
    }

}
