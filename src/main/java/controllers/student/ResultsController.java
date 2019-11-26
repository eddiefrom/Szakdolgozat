package controllers.student;

import helper.DifficultyLevel;
import helper.WindowMoving;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.CompetitionResults;
import models.PracticeResults;
import models.Student;
import models.Worksheet;
import org.eclipse.collections.impl.block.factory.HashingStrategies;
import org.eclipse.collections.impl.utility.ListIterate;
import services.WorksheetServiceImpl;
import services.interfaces.WorksheetService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ResultsController implements Initializable {

    private Stage stage;
    private AnchorPane pane;

    private StudentMainController studentMainController;
    private static Student student;
    private WindowMoving windowMoving;
    private static Worksheet selectedWorksheet;
    private WorksheetService worksheetService;

    @FXML private TableView<Worksheet> worksheetTable;
    @FXML private TableColumn<Worksheet, String> nameColl;
    @FXML private TableColumn<Worksheet, DifficultyLevel> diffColl;
    @FXML private TableColumn<Worksheet, Double> pointsColl;
    @FXML private TableColumn<Worksheet, String> catColl;
    @FXML private TableColumn<Worksheet, String> typeColl;

    @FXML private TableView<Worksheet> worksheetTablePractice;
    @FXML private TableColumn<Worksheet, String> nameCollPractice;
    @FXML private TableColumn<Worksheet, DifficultyLevel> diffCollPractice;
    @FXML private TableColumn<Worksheet, Double> pointsCollPractice;
    @FXML private TableColumn<Worksheet, String> catCollPractice;
    @FXML private TableColumn<Worksheet, String> typeCollPractice;

    @FXML private Label easyPoints;
    @FXML private Label mediumPoints;
    @FXML private Label hardPoints;
    @FXML private Label trueFalseExercise;
    @FXML private Label mainExercise;
    @FXML private Label allPoints;

    @FXML private Label allTimePracticeDay;
    @FXML private Label allTimePracticeHour;
    @FXML private Label allTimePracticeMinute;
    @FXML private Label allTimePracticeSecond;
    @FXML private Label allTimeCompetitionDay;
    @FXML private Label allTimeCompetitionHour;
    @FXML private Label allTimeCompetitionMinute;
    @FXML private Label allTimeCompetitionSecond;

    private int day, dayModulo, hour, hourModulo, minute, second;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setLoggedStudentCompetitionInfos(student);
        setPracticeTable();
        setCompetitionTable();
        setStaticTable();
    }

    public ResultsController() {

        stage = new Stage();
        studentMainController = new StudentMainController();
        student = studentMainController.getLoggedStudent();
        windowMoving = new WindowMoving();
        worksheetService = new WorksheetServiceImpl();
        day = 0;
        dayModulo =0;
        hour = 0;
        hourModulo = 0;
        minute = 0;
        second = 0;
    }

    public void createScene() throws IOException {

        stage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/student/ResultsVIEW.fxml"));
        pane = loader.load();

        windowMoving.moving(stage,pane);

        Scene scene = new Scene(pane);
        scene.getStylesheets().add("css/teacher_back.css");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("LearningRoom");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void openCompetition() throws IOException {

        selectedWorksheet = worksheetTable.getSelectionModel().getSelectedItem();
        if(selectedWorksheet != null) {
            CompetitionResultsController competitionResultsController = new CompetitionResultsController();
            competitionResultsController.createScene();
        }
    }

    @FXML
    public void openPractice() throws IOException {

        selectedWorksheet = worksheetTablePractice.getSelectionModel().getSelectedItem();
        if(selectedWorksheet != null) {
            PracticeResultsController practiceResultsController = new PracticeResultsController();
            practiceResultsController.createScene();

        }
    }

    public void setLoggedStudentCompetitionInfos(Student student)
    {

        easyPoints.textProperty().setValue(String.valueOf(student.getEasyPoints()));
        mediumPoints.textProperty().setValue(String.valueOf(student.getMediumPoints()));
        hardPoints.textProperty().setValue(String.valueOf(student.getHardPoints()));
        trueFalseExercise.textProperty().setValue(String.valueOf(student.getTrueFalseExercise()));
        mainExercise.textProperty().setValue(String.valueOf(student.getMainExercise()));
        allPoints.textProperty().setValue(String.valueOf(student.getAllPoints()));
    }

    private void setCompetitionTable()
    {
        nameColl.setCellValueFactory(new PropertyValueFactory<>("name"));
        diffColl.setCellValueFactory(new PropertyValueFactory<>("difficultyLevel"));
        pointsColl.setCellValueFactory(new PropertyValueFactory<>("points"));
        catColl.setCellValueFactory(new PropertyValueFactory<>("categories"));
        typeColl.setCellValueFactory(new PropertyValueFactory<>("types"));

        ObservableList<Worksheet> worksheetObservableList = FXCollections.observableArrayList();
        List<CompetitionResults> compList  = ListIterate.distinct(student.getCompetitionResults(), HashingStrategies.fromLongFunction(CompetitionResults::getWorksheetID));
        List<Worksheet> worksheets = new ArrayList<>();

        for(int i = 0 ; i < worksheetService.findAllWorksheet().size(); i++)
        {
            for(int j = 0; j < compList.size(); j++)
                if(worksheetService.findAllWorksheet().get(i).getId().equals(compList.get(j).getWorksheetID()))
                    worksheets.add(worksheetService.findAllWorksheet().get(i));
        }

        worksheetObservableList.addAll(worksheets);

        worksheetTable.setItems(worksheetObservableList);
        worksheetTable.getColumns().clear();
        worksheetTable.getColumns().addAll(nameColl,diffColl,pointsColl,catColl,typeColl);
    }

    private void setPracticeTable()
    {
        nameCollPractice.setCellValueFactory(new PropertyValueFactory<>("name"));
        diffCollPractice.setCellValueFactory(new PropertyValueFactory<>("difficultyLevel"));
        pointsCollPractice.setCellValueFactory(new PropertyValueFactory<>("points"));
        catCollPractice.setCellValueFactory(new PropertyValueFactory<>("categories"));
        typeCollPractice.setCellValueFactory(new PropertyValueFactory<>("types"));

        ObservableList<Worksheet> worksheetObservableList = FXCollections.observableArrayList();
        List<PracticeResults> compList  = ListIterate.distinct(student.getPracticeResults(), HashingStrategies.fromLongFunction(PracticeResults::getWorksheetID));
        List<Worksheet> worksheets = new ArrayList<>();

        for(int i = 0 ; i < worksheetService.findAllWorksheet().size(); i++)
        {
            for(int j = 0; j < compList.size(); j++)
                if(worksheetService.findAllWorksheet().get(i).getId().equals(compList.get(j).getWorksheetID()))
                    worksheets.add(worksheetService.findAllWorksheet().get(i));
        }

        worksheetObservableList.addAll(worksheets);

        worksheetTablePractice.setItems(worksheetObservableList);
        worksheetTablePractice.getColumns().clear();
        worksheetTablePractice.getColumns().addAll(nameCollPractice,diffCollPractice,pointsCollPractice,catCollPractice,typeCollPractice);
    }

    public void setStaticTable()
    {
        int allPractice = student.getPracticeTime();
        int allCompetition = student.getCompetitionTime();

        day = allPractice / 86400;
        dayModulo = allPractice % 86400;
        hour = dayModulo / 3600;
        hourModulo = allPractice % 3600;
        minute = hourModulo / 60;
        second = hourModulo % 60;

        allTimePracticeDay.textProperty().setValue(String.valueOf(day));
        allTimePracticeHour.textProperty().setValue(String.valueOf(hour));
        allTimePracticeMinute.textProperty().setValue(String.valueOf(minute));
        allTimePracticeSecond.textProperty().setValue(String.valueOf(second));

        day = allCompetition / 86400;
        dayModulo = allCompetition % 86400;
        hour = dayModulo / 3600;
        hourModulo = allCompetition % 3600;
        minute = hourModulo / 60;
        second = hourModulo % 60;

        allTimeCompetitionDay.textProperty().setValue(String.valueOf(day));
        allTimeCompetitionHour.textProperty().setValue(String.valueOf(hour));
        allTimeCompetitionMinute.textProperty().setValue(String.valueOf(minute));
        allTimeCompetitionSecond.textProperty().setValue(String.valueOf(second));
    }


    @FXML
    public void exitButtonEvent(ActionEvent actionEvent)  {

        worksheetService.close();
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public  Student getStudent() {
        return student;
    }

    public Worksheet getSelectedWorksheet() {
        return selectedWorksheet;
    }
}
