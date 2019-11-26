package start;

import controllers.MainController;
import helper.Encryptor;
import helper.EntityManagerFactoryHelper;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.*;
import services.*;
import services.interfaces.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StartProgram extends Application {

    private MainController mainController;
    private Encryptor encryptor;

    public void start(Stage primaryStage) throws Exception {

        primaryStage.initStyle(StageStyle.UNDECORATED);

        mainController = new MainController();
        encryptor = new Encryptor();
        _EXAMPLE();
        mainController.createScene(primaryStage);

    }

    public void _EXAMPLE() throws Exception {

        List<Student> studentList = new ArrayList<>();
        List<Student> studentList2 = new ArrayList<>();

        Teacher teacher = new Teacher("Kovács János", "kJani", encryptor.encrypting("alma"),
                "tanar@gmail.com", null);


        Parent parent = new Parent("Kisné Julianna", "kis_juli@gmail.com", encryptor.encrypting("alma"), studentList);
        Student student = new Student("Kis István", "k_istvan@gmail.com",
                encryptor.encrypting("alma"), LocalDate.parse("1993-02-01"),
                8, null,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0,0,0,0, parent);


        studentList.add(student);
        parent.setChildren(studentList);

        Parent parent2 = new Parent("Nagyné Erzsébet", "nagy_erzsi@gmail.com", encryptor.encrypting("alma"), studentList);
        Student student2 = new Student("Nagy László", "n_laszlo@gmail.com",
                encryptor.encrypting("alma"), LocalDate.parse("1991-08-18"),
                10, null,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0,0,0,0, parent2);

        studentList.add(student2);
        parent2.setChildren(studentList2);


        ParentService parentService = new ParentServiceImpl();
        parentService.save(parent2);

        parentService.save(parent);

        student.getTeachers().add(teacher);
        student2.getTeachers().add(teacher);
        teacher.getStudentList().add(student);
        teacher.getStudentList().add(student2);


        TeacherService teacherService = new TeacherServiceImpl();
        teacherService.save(teacher);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
