package controllers.student;

import controllers.MainController;
import helper.DifficultyLevel;
import helper.WindowMoving;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Student;
import models.Worksheet;
import services.StudentServiceImpl;
import services.WorksheetServiceImpl;
import services.interfaces.StudentService;
import services.interfaces.WorksheetService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StudentMainController implements Initializable {

    static Stage stage;
    private AnchorPane pane;

    private MainController mainController;
    private static Student loggedStudent;
    private ResultsController resultsController;
    private WindowMoving windowMoving;
    private WorksheetService worksheetService;
    private ObservableList<Worksheet> worksheetObservableList;
    private FilterController filterController;
    private List<Worksheet> worksheetList;
    private static String catFilter;
    private static String difficultyLevelFilter;
    private StudentService studentService;
    private PracticeController practiceController;
    private CompetitionController competitionController;
    private static Worksheet selectedWorksheet;
    private DrawingController drawingController;

    @FXML private Label nameLabel;
    @FXML private ImageView profilPic;
    @FXML private TableView<Worksheet> worksheetTable;
    @FXML private TableColumn<Worksheet, String> nameColl;
    @FXML private TableColumn<Worksheet, DifficultyLevel> diffColl;
    @FXML private TableColumn<Worksheet, Double> pointsColl;
    @FXML private TableColumn<Worksheet, String> catColl;
    @FXML private TableColumn<Worksheet, String> typeColl;
    @FXML private Button rankB;
    @FXML private Button resultB;
    @FXML private Button filterB;
    @FXML private Button drawB;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setLoggedStudentInfos(loggedStudent);
        setButtons();
        setTable();
    }

    public StudentMainController() {

        mainController = new MainController();
        loggedStudent = mainController.getLoginStudent();
        worksheetService = new WorksheetServiceImpl();
        windowMoving = new WindowMoving();
        studentService = new StudentServiceImpl();
        if(catFilter == null)
            catFilter = "Mind";
        if(difficultyLevelFilter == null)
            difficultyLevelFilter = "Mind";
    }

    public void createScene(Stage stage) throws IOException {

        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/student/StudentMainVIEW.fxml"));
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
    public void exitButtonEvent() throws IOException {

        worksheetService.close();
        studentService.close();
        mainController.createScene(stage);
    }

    @FXML
    public void filterButtonEvent() throws IOException {

        filterController = new FilterController();
        filterController.createScene(stage);
    }

    @FXML
    public void rankListButtonEvent() throws IOException {
        RankController rankController = new RankController();
        rankController.createScene();
    }

    @FXML
    public void practiceModeButtonEvent() throws IOException {

        selectedWorksheet = worksheetTable.getSelectionModel().getSelectedItem();
        if(selectedWorksheet != null && selectedWorksheet.getExercises().size() > 0)
        {
            practiceController = new PracticeController();
            practiceController.createScene();
        }
    }

    @FXML
    public void competitionModeButtonEvent() throws IOException {
        selectedWorksheet = worksheetTable.getSelectionModel().getSelectedItem();
        if(selectedWorksheet != null && selectedWorksheet.getExercises().size() > 0)
        {
            competitionController = new CompetitionController();
            competitionController.createScene();
        }
    }

    public void setLoggedStudentInfos(Student student)
    {
        nameLabel.textProperty().setValue(student.getName()+" !");

        if(student.getProfilPicURL() == null)
            profilPic.setImage(new Image("pictures/no_profil_pic.jpg"));
        else
            profilPic.setImage(new Image(student.getProfilPicURL()));
    }

    private void setButtons()
    {
        rankB.setGraphic(new ImageView(new Image("pictures/rank.png")));
        resultB.setGraphic(new ImageView(new Image("pictures/result.png")));
        filterB.setGraphic(new ImageView(new Image("pictures/filter.png")));
        drawB.setGraphic(new ImageView(new Image("pictures/modify.png")));
    }

    @FXML
    public void resultsEvent() throws IOException {

        resultsController = new ResultsController();
        resultsController.createScene();
    }


    private void setTable()
    {
        nameColl.setCellValueFactory(new PropertyValueFactory<>("name"));
        diffColl.setCellValueFactory(new PropertyValueFactory<>("difficultyLevel"));
        pointsColl.setCellValueFactory(new PropertyValueFactory<>("points"));
        catColl.setCellValueFactory(new PropertyValueFactory<>("categories"));
        typeColl.setCellValueFactory(new PropertyValueFactory<>("types"));
        worksheetList = new ArrayList<>();

        for(Worksheet w : worksheetService.findAllWorksheet())
        {
            if(w.getClassNumber() == loggedStudent.getClassNumber())
            {
                if (w.getExercises().size() > 0)
                {
                    if (catFilter.equals("Mind") && difficultyLevelFilter.equals("Mind")) {
                        worksheetList.add(w);
                    }
                    if (!catFilter.equals("Mind") && difficultyLevelFilter.equals("Mind")) {
                        if (w.getCategories().equals(catFilter))
                            worksheetList.add(w);
                    }
                    if (catFilter.equals("Mind") && !difficultyLevelFilter.equals("Mind")) {
                        if (w.getDifficultyLevel().toString().equals(difficultyLevelFilter))
                            worksheetList.add(w);
                    }
                    if (!catFilter.equals("Mind")  && !difficultyLevelFilter.equals("Mind")) {
                        if (w.getDifficultyLevel().toString().equals(difficultyLevelFilter)
                                && w.getCategories().equals(catFilter))
                            worksheetList.add(w);
                    }
                }
            }
        }
        worksheetObservableList = FXCollections.observableArrayList();
        worksheetObservableList.addAll(worksheetList);

        worksheetTable.setItems(worksheetObservableList);
        worksheetTable.getColumns().clear();
        worksheetTable.getColumns().addAll(nameColl,diffColl,pointsColl,catColl,typeColl);

    }

    @FXML
    public void drawButtonEvent() throws IOException {
        drawingController = new DrawingController();
        drawingController.createScene();
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
            loggedStudent.setProfilPicURL(file.toURI().toString());
            studentService.modify(loggedStudent);
        }
    }

    public  Student getLoggedStudent() {
        return loggedStudent;
    }

    public static Worksheet getSelectedWorksheet() {
        return selectedWorksheet;
    }

    public static void setCatFilter(String catFilter) {
        StudentMainController.catFilter = catFilter;
    }

    public static void setDifficultyLevelFilter(String difficultyLevelFilter) {
        StudentMainController.difficultyLevelFilter = difficultyLevelFilter;
    }


}
