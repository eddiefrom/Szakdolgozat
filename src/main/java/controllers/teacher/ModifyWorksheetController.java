package controllers.teacher;

import helper.DifficultyLevel;
import helper.EntityManagerFactoryHelper;
import helper.WindowMoving;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Exercise;
import models.Student;
import models.Worksheet;
import services.ExerciseServiceImpl;
import services.StudentServiceImpl;
import services.WorksheetServiceImpl;
import services.interfaces.ExerciseService;
import services.interfaces.StudentService;
import services.interfaces.WorksheetService;
import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ModifyWorksheetController implements Initializable {

    private static Stage stage;
    private AnchorPane pane;

    private WindowMoving windowMoving;
    private ModifyCreateExerciseController modifyCreateExerciseController;
    private TeacherMainController teacherMainController;
    private static Worksheet worksheet;
    private ObservableList<Exercise> exerciseObservableList;
    private static Exercise selectedExercise;
    private  ExerciseService exerciseService;
    private List<Exercise> exerciseList;
    private  WorksheetService worksheetService;
    private  StudentService studentService;
    private boolean isEmpty;
    private static String method;

    @FXML private TableView<Exercise> tableView;
    @FXML private TableColumn<Exercise, String> nameCell;
    @FXML private TableColumn<Exercise, Double> pointCell;
    @FXML private TableColumn<Exercise, DifficultyLevel> diffCell;
    @FXML private TableColumn<Exercise,String> categoryCell;
    @FXML private TableColumn<Exercise,String> typeCell;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button modifyButton;
    @FXML private TextField worksheetName;
    @FXML private TextField classNumber;
    @FXML Label errorLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setButtons();
        setTable();
        worksheetName.textProperty().setValue(worksheet.getName());
        classNumber.textProperty().setValue(String.valueOf(worksheet.getClassNumber()));
    }

    public ModifyWorksheetController() {

        teacherMainController = new TeacherMainController();
        windowMoving = new WindowMoving();
        worksheet = teacherMainController.getSelectedWorksheet();
        exerciseService = new ExerciseServiceImpl();
        exerciseList = worksheet.getExercises();
        worksheetService = new WorksheetServiceImpl();
        studentService = new StudentServiceImpl();

        tableView = new TableView<>();
        nameCell = new TableColumn<>();
        pointCell = new TableColumn<>();
        diffCell = new TableColumn<>();
        categoryCell = new TableColumn<>();
        isEmpty = true;
    }

    public void createScene(Stage stage) throws IOException {

        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/teacher/ModifyWorksheetVIEW.fxml"));
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
    public void addExerciseButtonEvent(ActionEvent actionEvent) throws IOException {

        method = "add";
        modifyCreateExerciseController = new ModifyCreateExerciseController();
        modifyCreateExerciseController.createScene();

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void deleteExerciseButtonEvent() throws IOException {

        selectedExercise = tableView.getSelectionModel().getSelectedItem();
        File file;
        if(selectedExercise != null)
        {
            file = new File(selectedExercise.getUrl());
            if(file.delete())

            worksheet.setminusPoints(selectedExercise.getPoint());
            worksheet.getExercises().remove(selectedExercise);

            for(Student s : studentService.findAllStudent())
            {
                for(int j = 0; j < s.getCompetitionResults().size(); j++)
                {
                    if(s.getCompetitionResults().get(j).getWorksheetID().equals(worksheet.getId())
                        && s.getCompetitionResults().get(j).getExerciseID().equals(selectedExercise.getId())) {
                        s.getCompetitionResults().remove(j);
                        studentService.modify(s);
                    }
                }
            }
            for(Student s : studentService.findAllStudent())
            {
                for(int j = 0; j < s.getPracticeResults().size(); j++)
                {
                    if(s.getPracticeResults().get(j).getWorksheetID().equals(worksheet.getId())
                            && s.getPracticeResults().get(j).getExerciseID().equals(selectedExercise.getId())) {
                        s.getPracticeResults().remove(j);
                        studentService.modify(s);
                    }
                }
            }
            exerciseService.remove(selectedExercise);
            tableView.getItems().remove(selectedExercise);

            setCats();
            setDiffs();

            worksheetService.modify(worksheet);

            teacherMainController.createScene(stage);
        }
    }

    @FXML
    public void modifyExerciseButtonEvent(ActionEvent actionEvent) throws IOException {
        selectedExercise = tableView.getSelectionModel().getSelectedItem();
        if(selectedExercise != null)
        {
            modifyCreateExerciseController = new ModifyCreateExerciseController();
            method = "modify";
            modifyCreateExerciseController.createScene();
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    private void setCats(){

        List<String> cats = new ArrayList<>();
        for (Exercise exercise : worksheet.getExercises())
            cats.add(exercise.getExerciseCat());

        if(cats.stream().distinct().collect(Collectors.toList()).size() > 1)
            worksheet.setCategories("Vegyes");
        else if(cats.stream().distinct().collect(Collectors.toList()).size() == 1)
            worksheet.setCategories(cats.get(0));
    }

    private void setDiffs(){

        List<DifficultyLevel> diffs = new ArrayList<>();
        for (Exercise exercise : worksheet.getExercises())
            diffs.add(exercise.getDifficultyLevel());

        if(diffs.stream().distinct().collect(Collectors.toList()).size() > 1){
            int e,m,h;
            e = 0; m = 0; h = 0;
            for(DifficultyLevel d : diffs)
                switch (d){
                    case KÖNNYŰ: e++;break;
                    case KÖZEPES: m++; break;
                    case NEHÉZ: h++;break;
                    default:break;
                }
            if(e > m && e > h)
                worksheet.setDifficultyLevel(DifficultyLevel.KÖNNYŰ);
            if(m > e && m > h)
                worksheet.setDifficultyLevel(DifficultyLevel.KÖZEPES);
            if(h > e && h > m)
                worksheet.setDifficultyLevel(DifficultyLevel.NEHÉZ);
        }
        else if(diffs.stream().distinct().collect(Collectors.toList()).size() == 1)
            worksheet.setDifficultyLevel(diffs.get(0));
    }


    private void setButtons()
    {
        addButton.setGraphic(new ImageView(new Image("pictures/plus.png")));
        deleteButton.setGraphic(new ImageView(new Image("pictures/delete.png")));
        modifyButton.setGraphic(new ImageView(new Image("pictures/modify.png")));
    }

    private void setTable(){
        nameCell.setCellValueFactory(new PropertyValueFactory<>("name"));
        pointCell.setCellValueFactory(new PropertyValueFactory<>("point"));
        diffCell.setCellValueFactory(new PropertyValueFactory<>("difficultyLevel"));
        categoryCell.setCellValueFactory(new PropertyValueFactory<>("exerciseCat"));
        typeCell.setCellValueFactory(new PropertyValueFactory<>("types"));

        exerciseObservableList = FXCollections.observableArrayList();
        exerciseObservableList.addAll(exerciseList);

        tableView.setItems(exerciseObservableList);
        tableView.getColumns().clear();
        tableView.getColumns().addAll(nameCell, pointCell, diffCell, categoryCell, typeCell);
    }

    private String fieldCheck()
    {
        String error = "";
        textfieldsBorderSetup();

        worksheetName.setStyle("-fx-border-color: none ; -fx-border-width: 0px ;");
        classNumber.setStyle("-fx-border-color: none ; -fx-border-width: 0px ;");

        if(worksheetName.getText().equals("") && classNumber.getText().equals(""))
            return error;
        else
            {
            for (Worksheet w : worksheetService.findAllWorksheet())
            {
                if(!w.getName().equals(worksheet.getName()))
                {
                    if (w.getName().equals(worksheetName.getText()))
                    {
                        error += "Van már ilyen nevű feladatsor!\n";
                        worksheetName.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                    }
                }
            }

            try {
                Number n = Integer.parseInt(classNumber.getText());

            } catch (NumberFormatException e) {
                error += "Hibás évfolyam érték!\n";
                classNumber.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            }
        }
        isEmpty = false;
        return  error;
    }
    private void textfieldsBorderSetup()
    {
        worksheetName.setStyle("-fx-border-color: white ; -fx-border-width: 0px ;");
        classNumber.setStyle("-fx-border-color: white ; -fx-border-width: 0px ;");
    }


    @FXML
    public void exitButtonEvent() throws IOException {

        teacherMainController.createScene(stage);
    }

    @FXML
    public void saveModif() throws IOException {
        String error = fieldCheck();

        if(error.equals(""))
        {
            if(isEmpty)
                teacherMainController.createScene(stage);
            else {
                worksheet.setName(worksheetName.getText());
                worksheet.setClassNumber(Integer.parseInt(classNumber.getText()));
                worksheetService.modify(worksheet);
                teacherMainController.createScene(stage);
            }
        }
        else
            errorLabel.textProperty().setValue(error);
    }

    public Worksheet getWorksheet() {
        return worksheet;
    }

    public Exercise getSelectedExercise() {
        return selectedExercise;
    }

    public  String getMethod() {
        return method;
    }
}
