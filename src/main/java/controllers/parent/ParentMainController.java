package controllers.parent;

import controllers.MainController;
import helper.WindowMoving;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Parent;
import models.Student;
import services.ParentServiceImpl;
import services.StudentServiceImpl;
import services.interfaces.ParentService;
import services.interfaces.StudentService;
import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ParentMainController implements Initializable {

    static Stage stage;
    private AnchorPane pane;

    private MainController mainController;
    private Parent loggedParent;
    private static Student selectedStudent;
    private ParentService parentService;
    private WindowMoving windowMoving;
    private StudentService studentService;
    private ParentResultController parentResultController;
    private ParentRankController parentRankController;

    @FXML private ImageView profilPic;
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> nameColl;
    @FXML private TableColumn<Student, Integer> classColl;
    @FXML private Label nameLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setLoggedParentInfos(loggedParent);
        setTable();
    }

    public ParentMainController() {

        mainController = new MainController();
        loggedParent= mainController.getLoginParent();
        parentService = new ParentServiceImpl();
        studentService = new StudentServiceImpl();
        windowMoving = new WindowMoving();
    }

    public void createScene(Stage stage) throws IOException {

        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/parent/ParentMainVIEW.fxml"));
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
    public void teachersEvent() throws IOException {
        selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if(selectedStudent != null) {
            StudentsTeachersController studentsTeachersController = new StudentsTeachersController();
            studentsTeachersController.createScene();
        }
    }

    @FXML
    public void pictureChooser(){

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(String.valueOf(file.toURI()));
            profilPic.setImage(image);
            loggedParent.setProfilPic(file.toURI().toString());
            parentService.modify(loggedParent);
        }
    }

    @FXML
    public void rankListButtonEvent() throws IOException {

        selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if(selectedStudent != null)
        {
            parentRankController = new ParentRankController();
            parentRankController.createScene();
        }
    }

    @FXML
    public void resultsEvent() throws IOException {

        selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if(selectedStudent != null)
        {
            parentResultController = new ParentResultController();
            parentResultController.createScene();
        }
    }

    private void setTable()
    {
        nameColl.setCellValueFactory(new PropertyValueFactory<>("name"));
        classColl.setCellValueFactory(new PropertyValueFactory<>("classNumber"));

        ObservableList<Student> studentObservableList = FXCollections.observableArrayList();

        for(Student s : studentService.findAllStudent())
        {
            if(s.getParent().getEmail().equals(loggedParent.getEmail()))
                studentObservableList.add(s);
        }

        studentTable.setItems(studentObservableList);
        studentTable.getColumns().clear();
        studentTable.getColumns().addAll(nameColl, classColl);
    }
    public void setLoggedParentInfos(Parent parent)
    {
        nameLabel.textProperty().setValue(parent.getName()+" !");

        if(parent.getProfilPic() == null)
            profilPic.setImage(new Image("pictures/no_profil_pic.jpg"));
        else
            profilPic.setImage(new Image(parent.getProfilPic()));
    }

    public Student getSelectedStudent() {
        return selectedStudent;
    }

    @FXML
    public void exitButtonEvent() throws IOException {

        mainController.createScene(stage);
    }


}
