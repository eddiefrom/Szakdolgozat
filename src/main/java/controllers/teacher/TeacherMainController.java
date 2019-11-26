package controllers.teacher;

import controllers.MainController;
import helper.DifficultyLevel;
import helper.WindowMoving;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.*;
import services.*;
import services.interfaces.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TeacherMainController implements Initializable {

    private static Stage stage;
    private AnchorPane pane;

    private MainController mainController;
    private Teacher loggedTeacher;
    private static Student selectedStudent;
    private static Worksheet selectedWorksheet;
    private WindowMoving windowMoving;
    private AddStudentController addStudentController;
    private AddWorksheetController addWorksheetController;
    private static WorksheetService worksheetService;
    private static StudentService studentService;
    private ObservableList<Worksheet> worksheetObservableList;
    private ObservableList<Student> studentObservableList;
    private List<Worksheet> worksheetList;
    private ModifyStudentController modifyStudentController;
    private ModifyWorksheetController modifyWorksheetController;
    private static TeacherService teacherService;
    private static List<Student> studentProperties;
    private List<Student> studentList;

    @FXML private Label nameLabel;
    @FXML private ImageView imageView;
    @FXML private TableView<Worksheet> table;
    @FXML private TableColumn<Worksheet, String> nameCell;
    @FXML private TableColumn<Worksheet, DifficultyLevel> diffCell;
    @FXML private TableColumn<Worksheet, Double> pointsCell;
    @FXML private TableColumn<Worksheet, String> categoryCell;
    @FXML private TableColumn<Worksheet, String> typesCell;
    @FXML private TableView<Student> studentsTable;
    @FXML private TableColumn<Student, Integer> studentClassColl;
    @FXML private TableColumn<Student, String> studentsNameColl;
    @FXML private Button addStudentButton;
    @FXML private Button deleteStudentButton;
    @FXML private Button modifyStudentButton;
    @FXML private Button infoStudentButton;
    @FXML private Button addWorksheetButton;
    @FXML private Button deleteWorksheetButton;
    @FXML private Button modifyWorksheetButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setLoggedTeacherInfos(loggedTeacher);
        setWorksheetsTable();
        setStudentsTable();
        setButtonsPic();
        studentsTable.setEditable(true);
    }

    public TeacherMainController() {

        mainController = new MainController();
        loggedTeacher = mainController.getLoginTeacher();
        windowMoving = new WindowMoving();
        worksheetService = new WorksheetServiceImpl();
        studentService = new StudentServiceImpl();
        teacherService = new TeacherServiceImpl();
        studentProperties = new ArrayList<>();
    }

    public void createScene(Stage stage) throws IOException {

        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/teacher/TeacherMainVIEW.fxml"));
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

    private void setLoggedTeacherInfos(Teacher loggedTeacher) {

        this.loggedTeacher = loggedTeacher;

        nameLabel.textProperty().setValue(loggedTeacher.getName()+" !");

        if(loggedTeacher.getProfilPicURL() == null)
            imageView.setImage(new Image("pictures/no_profil_pic.jpg"));
        else
            imageView.setImage(new Image(loggedTeacher.getProfilPicURL()));
    }

    @FXML
    public void addStudentScene() throws IOException {

        addStudentController = new AddStudentController();
        addStudentController.createScene(stage);
    }

    @FXML
    public void addWorksheetScene(ActionEvent actionEvent) throws IOException {

        addWorksheetController = new AddWorksheetController();
        addWorksheetController.createScene();
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void modifyStudentScene() throws IOException {

        selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
        if(selectedStudent != null)
        {
            modifyStudentController = new ModifyStudentController();
            modifyStudentController.createScene(stage);
        }

    }

    @FXML
    public void infoStudentScene() throws IOException {

        selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
        if(selectedStudent != null)
        {
            StudentInfoController studentInfoController = new StudentInfoController();
            studentInfoController.createScene();
        }
    }

    @FXML
    public void modifyWorksheetScene() throws IOException {

        selectedWorksheet = table.getSelectionModel().getSelectedItem();
        if(selectedWorksheet != null)
        {
            modifyWorksheetController = new ModifyWorksheetController();
            modifyWorksheetController.createScene(stage);
        }
    }

    @FXML
    public void deleteStudent()
    {
        selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
        if(selectedStudent != null)
        {
            for(Teacher t : teacherService.findAllTeacher())
            {
                for(int i = 0; i < t.getStudentList().size(); i++)
                {
                    if (t.getStudentList().get(i).getEmail().equals(selectedStudent.getEmail())) {

                        t.getStudentList().remove(i);
                        for(int j = 0; j < selectedStudent.getWorksheetList().size(); j++)
                        {
                            for(int k = 0; k < worksheetService.findAllWorksheet().size(); k++)
                            {
                                worksheetService.findAllWorksheet().get(k).getStudentList().remove(j);
                                worksheetService.modify(worksheetService.findAllWorksheet().get(k));
                            }
                            selectedStudent.getWorksheetList().remove(j);
                        }
                        studentService.remove(selectedStudent);
                        teacherService.modify(t);
                    }
                }
            }
            studentsTable.getItems().remove(selectedStudent);
        }
    }

    @FXML
    public void deleteWorksheet()
    {
        selectedWorksheet = table.getSelectionModel().getSelectedItem();
        if(selectedWorksheet != null)
        {
            for(Student s : studentService.findAllStudent())
            {
                for(int j = 0; j < s.getCompetitionResults().size(); j++)
                {
                    if(s.getCompetitionResults().get(j).getWorksheetID().equals(selectedWorksheet.getId())) {
                        s.getCompetitionResults().remove(j);
                        studentService.modify(s);
                    }
                }
            }
            for(Student s : studentService.findAllStudent())
            {
                for(int j = 0; j < s.getPracticeResults().size(); j++)
                {
                    if(s.getPracticeResults().get(j).getWorksheetID().equals(selectedWorksheet.getId())) {
                        s.getPracticeResults().remove(j);
                        studentService.modify(s);
                    }
                }
            }
            worksheetService.remove(selectedWorksheet);
            table.getItems().removeAll(selectedWorksheet);
        }
    }

    private void setStudentsTable()
    {
        studentsNameColl.setCellValueFactory(new PropertyValueFactory<>("name"));
        studentsNameColl.getStyleClass().add("css/table_view.css");
        studentClassColl.setCellValueFactory(new PropertyValueFactory<>("classNumber"));

        studentList = studentService.findAllStudent();
        for(Student s : studentList)
        {
            for(int i = 0; i < s.getTeachers().size(); i++)
            {
                if(s.getTeachers().get(i).getEmail().equals(loggedTeacher.getEmail()))
                    studentProperties.add(s);
            }

        }
        studentObservableList = FXCollections.observableArrayList();
        studentObservableList.addAll(studentProperties);

        studentsTable.setItems(studentObservableList);
        studentsTable.setEditable(true);
        studentsTable.getColumns().clear();
        studentsTable.getColumns().addAll(studentsNameColl, studentClassColl);
    }

    private void setWorksheetsTable()
    {
        nameCell.setCellValueFactory(new PropertyValueFactory<>("name"));
        diffCell.setCellValueFactory(new PropertyValueFactory<>("difficultyLevel"));
        categoryCell.setCellValueFactory(new PropertyValueFactory<>("categories"));
        typesCell.setCellValueFactory(new PropertyValueFactory<>("types"));
        pointsCell.setCellValueFactory(new PropertyValueFactory<>("points"));

        worksheetList = worksheetService.findAllWorksheetByCreator(
                loggedTeacher.getName());

        worksheetObservableList = FXCollections.observableArrayList();
        worksheetObservableList.addAll(worksheetList);

        table.setItems(worksheetObservableList);
        table.getColumns().clear();
        table.getColumns().addAll(nameCell, diffCell, categoryCell, typesCell, pointsCell);
    }

    private void setButtonsPic()
    {
        addStudentButton.setGraphic(new ImageView(new Image("pictures/plus.png")));
        deleteStudentButton.setGraphic(new ImageView(new Image("pictures/delete.png")));
        modifyStudentButton.setGraphic(new ImageView(new Image("pictures/modify.png")));
        infoStudentButton.setGraphic(new ImageView(new Image("pictures/info.png")));
        addWorksheetButton.setGraphic(new ImageView(new Image("pictures/plus.png")));
        deleteWorksheetButton.setGraphic(new ImageView(new Image("pictures/delete.png")));
        modifyWorksheetButton.setGraphic(new ImageView(new Image("pictures/modify.png")));
    }

    @FXML
    public void pictureChooser() {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(String.valueOf(file.toURI()));
            imageView.setImage(image);
            loggedTeacher.setProfilPicURL(file.toURI().toString());
            teacherService.modify(loggedTeacher);
        }
    }

    @FXML
    public void exitButtonEvent() throws IOException {

        mainController.createScene(stage);
    }


    public Teacher getLoggedTeacher() {
        return loggedTeacher;
    }

    public Student getSelectedStudent() {
        return selectedStudent;
    }

    public Worksheet getSelectedWorksheet() {
        return selectedWorksheet;
    }

}
