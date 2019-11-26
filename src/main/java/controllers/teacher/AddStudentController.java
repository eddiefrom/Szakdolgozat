package controllers.teacher;

import helper.Encryptor;
import helper.WindowMoving;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Parent;
import models.Student;
import services.ParentServiceImpl;
import services.StudentServiceImpl;
import services.TeacherServiceImpl;
import services.interfaces.ParentService;
import services.interfaces.StudentService;
import services.interfaces.TeacherService;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddStudentController implements Initializable {

    private static Stage stage;
    private AnchorPane pane;
    private Parent parent;
    private Student student;
    private ParentService parentService;
    private StudentService studentService;
    private TeacherMainController teacherMainController;
    private Encryptor encryptor;
    private WindowMoving windowMoving;
    private TeacherService teacherService;

    @FXML private TextField name;
    @FXML private TextField email;
    @FXML private TextField bornDate;
    @FXML private TextField classNumber;
    @FXML private TextField parentName;
    @FXML private PasswordField password;
    @FXML private PasswordField passwordAgain;
    @FXML private TextField parentEmail;
    @FXML private Label errorLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        name.textProperty().setValue("");
        email.textProperty().setValue("");
        bornDate.textProperty().setValue("");
        parentName.textProperty().setValue("");
        password.textProperty().setValue("");
        passwordAgain.textProperty().setValue("");
        parentEmail.textProperty().setValue("");
        errorLabel.textProperty().setValue("");
    }

    public AddStudentController() {

        windowMoving = new WindowMoving();
        teacherMainController = new TeacherMainController();
        encryptor = new Encryptor();
        parentService = new ParentServiceImpl();
        studentService = new StudentServiceImpl();
        teacherService = new TeacherServiceImpl();
    }

    public void createScene(Stage stage) throws IOException {

        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/teacher/AddStudentVIEW.fxml"));
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

            boolean existParent = false;

            try {
                if(parentService.findByEmail(parentEmail.getText()) != null) {
                    parent = parentService.findByEmail(parentEmail.getText());
                    existParent = true;
                }
            }
            catch (NoResultException ex)
            {
                List<Student> studentList = new ArrayList<>();
                parent = new Parent(parentName.getText(), parentEmail.getText(), encryptor.encrypting(password.getText()), studentList);
            }


            student = new Student(name.getText(), email.getText(), encryptor.encrypting(password.getText()), LocalDate.parse(bornDate.getText()),
                    Integer.parseInt(classNumber.getText()), null,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0,0,0,0, parent);

            parent.setChild(student);

            if(existParent)
                parentService.modify(parent);
            else
                parentService.save(parent);

            student.getTeachers().add(teacherMainController.getLoggedTeacher());
            teacherMainController.getLoggedTeacher().getStudentList().add(student);
            teacherService.modify(teacherMainController.getLoggedTeacher());

            parentService.close();
            studentService.close();
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
        studentService.close();
        teacherService.close();
        teacherMainController.createScene(stage);
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
            if(s.getEmail().equals(email.getText())) {
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
            if(n.intValue() < 0) {
                error += "Évfolyam nem lehet negatív!\n";
                classNumber.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            }
            if(n.intValue() == 0) {
                error += "Evfolyam nem lehet nulla!\n";
                classNumber.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            }

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
