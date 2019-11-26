package controllers.student;

import helper.DifficultyLevel;
import helper.WindowMoving;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Worksheet;
import services.WorksheetServiceImpl;
import services.interfaces.WorksheetService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FilterController implements Initializable {

    private static Stage stage;
    private AnchorPane pane;

    private WindowMoving windowMoving;
    private StudentMainController studentMainController;
    private WorksheetService worksheetService;
    private List<String> allCategories, exerciseCategories;

    @FXML private ChoiceBox<String> categoryChoice;
    @FXML private ChoiceBox<String> difficultyLevelChoiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setCategoryElements();
        setDiffLevelElements();
    }

    public FilterController() {

        windowMoving = new WindowMoving();
        studentMainController = new StudentMainController();
        worksheetService  =new WorksheetServiceImpl();
        categoryChoice = new ChoiceBox<>();
        difficultyLevelChoiceBox = new ChoiceBox<>();
        allCategories = new ArrayList<>();
        exerciseCategories = new ArrayList<>();
    }

    @FXML
    public void createScene(Stage stage) throws IOException {

        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/student/FilterVIEW.fxml"));
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
    public void filterButtonEvent(javafx.event.ActionEvent actionEvent) throws IOException {

        StudentMainController.setCatFilter(categoryChoice.getSelectionModel().getSelectedItem());
        StudentMainController.setDifficultyLevelFilter(difficultyLevelChoiceBox.getSelectionModel().getSelectedItem());
        studentMainController.createScene(stage);
    }

    @FXML
    public void exitButtonEvent(ActionEvent actionEvent) throws IOException {

        worksheetService.close();
        studentMainController.createScene(stage);
    }

    private void setCategoryElements()
    {
        List<String> cats = new ArrayList<>();

        for(Worksheet w : worksheetService.findAllWorksheet()) {
            if(!w.getCategories().equals(""))
                cats.add(w.getCategories());
        }
        cats.add("Mind");
        categoryChoice.setItems(FXCollections.observableArrayList(cats));
        categoryChoice.getSelectionModel().select("Mind");
    }

    private void setDiffLevelElements()
    {
        difficultyLevelChoiceBox.setItems(FXCollections.observableArrayList("Mind",
                DifficultyLevel.KÖNNYŰ.toString(), DifficultyLevel.KÖZEPES.toString(), DifficultyLevel.NEHÉZ.toString()));

        difficultyLevelChoiceBox.getSelectionModel().select("Mind");
    }
}
