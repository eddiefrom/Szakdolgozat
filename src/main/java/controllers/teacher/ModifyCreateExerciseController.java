package controllers.teacher;

import helper.DifficultyLevel;
import helper.Encryptor;
import helper.Types;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;
import models.Exercise;
import models.Worksheet;
import services.ExerciseServiceImpl;
import services.WorksheetServiceImpl;
import services.interfaces.ExerciseService;
import services.interfaces.WorksheetService;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ModifyCreateExerciseController implements Initializable {

    private static Stage stage;
    private AnchorPane pane;

    private static Canvas canvas;
    @FXML private Canvas mainCanvas;
    @FXML private Canvas effectCanvas;
    @FXML private TextField penSize;
    @FXML private ColorPicker fillColor;
    @FXML private CheckBox fillPainting;
    @FXML private CheckBox strokePainting;
    @FXML private ColorPicker strokColor;
    @FXML private Label errorMessage;
    @FXML private TextField nameField;
    @FXML private TextField pointField;
    @FXML private ChoiceBox<DifficultyLevel> difficultyLevelChoiceBox;
    @FXML private TextField goodAnswerField;
    @FXML private TextField exerciseField;
    @FXML private ChoiceBox<Types> exerciseTypes;
    @FXML private TextField timeField;


    private ModifyWorksheetController modifyWorksheetController;
    private TeacherMainController teacherMainController;
    private static Worksheet worksheet;
    private ModifyTextCreateController modifyTextCreateController;
    private GraphicsContext mainGC, effectGC;
    private boolean lineDrawing, ovalDrawing, rectDrawing, drawByHand, rubbering, circleDrawing,
            squareDrawing, pointDrawing, textWriting;
    private static double startX, startY, endX, endY, oldX, oldY;
    private static Exercise exercise;
    private String goodAnswer, exerciseName, exerciseType;
    private DifficultyLevel difficultyLevel;
    private double point;
    private ObservableList<DifficultyLevel> difficultyLevelObservableList;
    private ObservableList<Types> strings;
    private  ExerciseService exerciseService;
    private  WorksheetService worksheetService;
    private File file;
    private Encryptor encryptor;
    private static String help;
    private Image image;
    private static String method;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if(method.equals("modify"))
            exercise = modifyWorksheetController.getSelectedExercise();
        try {
            setFields();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setChoiceBox();

    }

    public ModifyCreateExerciseController() {

        modifyWorksheetController = new ModifyWorksheetController();
        worksheet = modifyWorksheetController.getWorksheet();
        encryptor = new Encryptor();
        exerciseService = new ExerciseServiceImpl();
        worksheetService = new WorksheetServiceImpl();
        encryptor = new Encryptor();
        help = "";
        method = modifyWorksheetController.getMethod();

    }

    public void createScene() throws IOException {

        this.stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/teacher/ModifyCreateExerciseVIEW.fxml"));
        pane = loader.load();

        Scene scene = new Scene(pane);
        scene.getStylesheets().add("css/main_back.css");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();

    }

    @FXML
    public void updateExercise()
    {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        File file = fileChooser.showOpenDialog(null);
        if(file != null)
        {
            String url = "file:"+file.getPath().replaceAll("\\\\","/");
            image = new Image(url);
            mainGC.drawImage(image,0,0);
        }
    }

    @FXML
    public void createButtonEvent(ActionEvent e) throws IOException {

        exerciseName = nameField.getText();
        goodAnswer = goodAnswerField.getText();
        difficultyLevel = difficultyLevelChoiceBox.getValue();
        exerciseType = exerciseField.getText();
        String error = checkFields();
        teacherMainController = new TeacherMainController();

        if (error.equals("")) {
            point = Double.parseDouble(pointField.getText());
            try {
               // if (setImageDialog() != null) {
                    pictureSave();
                    exerciseModify();
               // }
                teacherMainController.createScene(stage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        else{
                errorMessage.textProperty().setValue(error);
            }
    }

    private void setFields() throws Exception
    {
        if(method.equals("modify"))
        {
            errorMessage.textProperty().setValue("");
            pointField.textProperty().setValue(String.valueOf(exercise.getPoint()));
            nameField.textProperty().setValue(exercise.getName());
            timeField.textProperty().setValue(String.valueOf(exercise.getTime()));
            exerciseField.textProperty().setValue(exercise.getExerciseCat());
            goodAnswerField.textProperty().setValue(encryptor.decrypting(exercise.getGoodAnswer()));

            setPic();
        }
        if(method.equals("add"))
        {
            mainGC = mainCanvas.getGraphicsContext2D();
            effectGC = effectCanvas.getGraphicsContext2D();
        }

        canvas = mainCanvas;
        penSize.setText("10");
        fillPainting.setSelected(true);
    }

    private void setPic()
    {
        String url = "file:"+exercise.getUrl();
        image = new Image(url);
        mainGC = mainCanvas.getGraphicsContext2D();
        effectGC = effectCanvas.getGraphicsContext2D();

        mainGC.drawImage(image,0,0);

    }

    private void setChoiceBox(){


        difficultyLevelObservableList = FXCollections.observableArrayList();
        difficultyLevelObservableList.add(DifficultyLevel.KÖNNYŰ);
        difficultyLevelObservableList.add(DifficultyLevel.KÖZEPES);
        difficultyLevelObservableList.add(DifficultyLevel.NEHÉZ);
        difficultyLevelChoiceBox.setItems(difficultyLevelObservableList);
        if(method.equals("modify"))
            difficultyLevelChoiceBox.getSelectionModel().select(exercise.getDifficultyLevel());
        if(method.equals("add"))
            difficultyLevelChoiceBox.getSelectionModel().selectFirst();

        strings = FXCollections.observableArrayList();
        strings.addAll(Types.Igaz_Hamis, Types.Általános);
        exerciseTypes.setItems(strings);
        if(method.equals("modify"))
             exerciseTypes.getSelectionModel().select(exercise.getTypes());
        if(method.equals("add"))
            exerciseTypes.getSelectionModel().selectFirst();
    }

    @FXML
    public void helpButtonEvent() throws IOException {
        ModifyHelpCreatorController modifyHelpCreatorController= new ModifyHelpCreatorController();
        modifyHelpCreatorController.createScene();

    }

    private File setImageDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        fileChooser.setTitle(exerciseName);
        return file = fileChooser.showSaveDialog(stage);
    }

    private void pictureSave() throws IOException {
        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(null, writableImage);
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
        //TODO: csak teszt!
        file = new File("./examples/"+nameField.getText()+".png");
        ImageIO.write(renderedImage, "png", file);
    }

    private void exerciseModify() throws Exception {

        if(method.equals("add"))
        {
            exercise = new Exercise(point, file.getPath(), encryptor.encrypting(goodAnswer),
                    difficultyLevel, exerciseName, exerciseType,
                    help, exerciseTypes.getSelectionModel().getSelectedItem(),
                    Integer.valueOf(timeField.getText()), worksheet);

            worksheet.setExercise(exercise);
            worksheet.setPlusPoints(point);
            exerciseService.save(exercise);
        }
        if(method.equals("modify"))
        {
            exercise = modifyWorksheetController.getSelectedExercise();
            worksheet.setminusPoints(exercise.getPoint());
            exercise.setName(nameField.getText());
            exercise.setPoint(Double.parseDouble(pointField.getText()));
            exercise.setTime(Integer.valueOf(timeField.getText()));
            exercise.setGoodAnswer(encryptor.encrypting(goodAnswerField.getText()));
            exercise.setDifficultyLevel(difficultyLevelChoiceBox.getSelectionModel().getSelectedItem());
            exercise.setTypes(exerciseTypes.getSelectionModel().getSelectedItem());
            exercise.setHelp(help);
            exerciseService.modify(exercise);
            worksheet.setPoints(exercise.getPoint());
        }
        setCats();
        setDiffs();
        setTypes();
        worksheetService.modify(worksheet);
    }


    private String checkFields() {
        String error = "";
        textfieldsSetup();

        if (goodAnswer.equals("") || exerciseName.equals("") || exerciseType.equals(""))
            error += "Minden mezőt ki kell tölteni!\n";
        try {
            Number number = Double.valueOf(pointField.getText());
            if (Double.valueOf(pointField.getText()) < 0) {
                error += "A pont nem lehet negatív!\n";
                pointField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            }
            if (Double.valueOf(pointField.getText()) == 0) {
                error += "A pont nem lehet nulla!\n";
                pointField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            }
        } catch (NumberFormatException e) {
            error += "Nem jó szám érték!\n";
            pointField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
        }

        try {
            Number number = Integer.parseInt(timeField.getText());
            if (Integer.parseInt(timeField.getText()) < 0) {
                error += "Az idő nem lehet negatív!\n";
                timeField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            }
            if (Integer.parseInt(timeField.getText()) == 0) {
                error += "Az idő nem lehet nulla!\n";
                timeField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            }
        } catch (NumberFormatException e) {
            error += "Nem jó idő érték!\n";
            timeField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
        }
        for (Exercise e : exerciseService.findAllExercises()) {
            if (!e.getName().equals(exercise.getName()))
                if (e.getName().equals(exerciseName)) {
                        error += "Ilyen nevű feladat már van!\n";
                        nameField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                   }
        }

        if(exerciseTypes.getSelectionModel().getSelectedItem().equals(Types.Igaz_Hamis))
        {
            if(!goodAnswer.equals("Igaz"))
                if(!goodAnswer.equals("Hamis")) {
                    error += "Rossz válasz lehetőség!";
                    goodAnswerField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                }
        }
        else
        {
            System.out.println("Nem érzékelte");
        }

        return error;
    }

    private void textfieldsSetup()
    {
        goodAnswerField.setStyle("-fx-border-color: none ; -fx-border-width: 0px ;");
        nameField.setStyle("-fx-border-color: none ; -fx-border-width: 0px ;");
        timeField.setStyle("-fx-border-color: none ; -fx-border-width: 0px ;");
        pointField.setStyle("-fx-border-color: none ; -fx-border-width: 0px ;");
    }

    private void setCats(){

        List<String> cats = new ArrayList<>();
        for (Exercise exercise : worksheet.getExercises())
            cats.add(exercise.getExerciseCat());

        if(cats.stream().distinct().collect(Collectors.toList()).size() > 1)
            worksheet.setCategories("Vegyes");
        else if(cats.stream().distinct().collect(Collectors.toList()).size() == 1)
            worksheet.setCategories(exerciseType);
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
            worksheet.setDifficultyLevel(difficultyLevel);
    }

    private void setTypes()
    {
        List<String> types = new ArrayList<>();
        for (Exercise exercise : worksheet.getExercises())
            types.add(String.valueOf(exercise.getTypes()));

        if(types.stream().distinct().collect(Collectors.toList()).size() > 1)
            worksheet.setTypes("Vegyes");
        else if(types.stream().distinct().collect(Collectors.toList()).size() == 1)
            worksheet.setTypes(String.valueOf(exerciseTypes.getSelectionModel().getSelectedItem()));
    }

    public static void setHelp(String help) {
        ModifyCreateExerciseController.help = help;
    }

    @FXML
    public void exitButtonEvent(ActionEvent actionEvent) throws IOException {

        teacherMainController = new TeacherMainController();
        teacherMainController.createScene(stage);
    }

    @FXML
    private void deleteCanvas(ActionEvent e)
    {
        mainGC.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
        effectGC.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
    }

    @FXML
    private void rubberButtonEvent(ActionEvent e)
    {
        lineDrawing = false;
        ovalDrawing = false;
        rectDrawing = false;
        drawByHand = false;
        squareDrawing = false;
        circleDrawing = false;
        pointDrawing = false;
        rubbering = true;
        textWriting = false;
    }

    @FXML
    private void ovalButtonEvent(ActionEvent e)
    {
        lineDrawing = false;
        ovalDrawing = true;
        rectDrawing = false;
        drawByHand = false;
        rubbering = false;
        circleDrawing = false;
        squareDrawing = false;
        pointDrawing = false;
        textWriting = false;
    }

    @FXML
    private void lineButtonEvent(ActionEvent e)
    {
        lineDrawing = true;
        ovalDrawing = false;
        rectDrawing = false;
        drawByHand = false;
        rubbering = false;
        circleDrawing = false;
        squareDrawing = false;
        pointDrawing = false;
        textWriting = false;
    }

    @FXML
    private void rectButtonEvent(ActionEvent e)
    {
        lineDrawing = false;
        ovalDrawing = false;
        drawByHand = false;
        rectDrawing = true;
        rubbering = false;
        circleDrawing = false;
        squareDrawing = false;
        pointDrawing = false;
        textWriting = false;
    }

    @FXML
    private void squareButtonEvent(ActionEvent e)
    {
        lineDrawing = false;
        ovalDrawing = false;
        drawByHand = false;
        rectDrawing = false;
        rubbering = false;
        circleDrawing = false;
        squareDrawing = true;
        pointDrawing = false;
        textWriting = false;
    }

    @FXML
    private void drawByHandButtonEvent(ActionEvent e)
    {
        lineDrawing = false;
        ovalDrawing = false;
        rectDrawing = false;
        drawByHand = true;
        rubbering = false;
        circleDrawing = false;
        squareDrawing = false;
        pointDrawing = false;
        textWriting = false;
    }

    @FXML
    private void drawCircleButtonEvent(ActionEvent e)
    {
        lineDrawing = false;
        ovalDrawing = false;
        rectDrawing = false;
        drawByHand = false;
        rubbering = false;
        circleDrawing = true;
        squareDrawing = false;
        pointDrawing = false;
        textWriting = false;
    }

    @FXML
    private void drawPointButtonEvent(ActionEvent e)
    {
        lineDrawing = false;
        ovalDrawing = false;
        rectDrawing = false;
        drawByHand = false;
        rubbering = false;
        circleDrawing = false;
        squareDrawing = false;
        pointDrawing = true;
        textWriting = false;
    }

    @FXML
    private void textWritingButtonEvent(ActionEvent e)
    {
        lineDrawing = false;
        ovalDrawing = false;
        rectDrawing = false;
        drawByHand = false;
        rubbering = false;
        circleDrawing = false;
        squareDrawing = false;
        pointDrawing = false;
        textWriting = true;
    }

    @FXML
    private void mousePress(MouseEvent mouseEvent){
        this.startX = mouseEvent.getX();
        this.startY = mouseEvent.getY();
        this.oldX = mouseEvent.getX();
        this.oldY = mouseEvent.getY();
    }

    @FXML
    private void mouseDragged(MouseEvent mouseEvent) throws IOException {
        this.endX = mouseEvent.getX();
        this.endY = mouseEvent.getY();

        if(rectDrawing)
            drawRectEffect();
        if(ovalDrawing)
            drawOvalEffect();
        if(lineDrawing)
            drawLineEffect();
        if(drawByHand)
            drawByHand();
        if(circleDrawing)
            drawCircleEffect();
        if(squareDrawing)
            drawSquareEffect();
        if(rubbering)
            rubberEvent();

    }

    @FXML
    private void mouseRelease(MouseEvent mouseEvent) throws IOException {
        if(rectDrawing)
            drawRect();
        if(ovalDrawing)
            drawOval();
        if(lineDrawing)
            drawLine();
        if(circleDrawing)
            drawCircle();
        if(squareDrawing)
            drawSquare();
        if(pointDrawing)
            drawPoint();
        if(textWriting) {
            writeText();
        }
    }

    private void drawOval()
    {
        double wh = endX - startX;
        double hg = endY - startY;

        if(fillPainting.isSelected() && !strokePainting.isSelected()){
            mainGC.setFill(fillColor.getValue());
            mainGC.fillOval(startX, startY, wh, hg);
        }
        if(strokePainting.isSelected() && !fillPainting.isSelected()) {
            mainGC.setStroke(strokColor.getValue());
            mainGC.strokeOval(startX, startY, wh, hg);
        }
        if(strokePainting.isSelected() && fillPainting.isSelected()) {
            mainGC.setFill(fillColor.getValue());
            mainGC.fillOval(startX, startY, wh, hg);
            mainGC.setStroke(strokColor.getValue());
            mainGC.strokeOval(startX, startY, wh, hg);
        }
    }

    private void drawCircle()
    {
        double r = endX - startX;

        if(fillPainting.isSelected() && !strokePainting.isSelected()){
            mainGC.setFill(fillColor.getValue());
            mainGC.fillOval(startX, startY, r, r);
        }
        if(strokePainting.isSelected() && !fillPainting.isSelected()){
            mainGC.setStroke(strokColor.getValue());
            mainGC.strokeOval(startX, startY, r, r);
        }
        if(strokePainting.isSelected() && fillPainting.isSelected()){
            mainGC.setFill(fillColor.getValue());
            mainGC.fillOval(startX, startY, r, r);
            mainGC.setStroke(strokColor.getValue());
            mainGC.strokeOval(startX, startY, r, r);
        }
    }

    private void drawRect()
    {
        double wh = endX - startX;
        double hg = endY - startY;

        if(fillPainting.isSelected() && !strokePainting.isSelected()){
            mainGC.setFill(fillColor.getValue());
            mainGC.fillRect(startX, startY, wh, hg);
        }
        if(strokePainting.isSelected() && !fillPainting.isSelected()) {
            mainGC.setStroke(strokColor.getValue());
            mainGC.strokeRect(startX, startY, wh, hg);
        }
        if(strokePainting.isSelected() && fillPainting.isSelected()) {
            mainGC.setFill(fillColor.getValue());
            mainGC.fillRect(startX, startY, wh, hg);
            mainGC.setStroke(strokColor.getValue());
            mainGC.strokeRect(startX, startY, wh, hg);
        }
    }

    private void drawSquare()
    {
        double r = endX - startX;

        if(fillPainting.isSelected() && !strokePainting.isSelected()){
            mainGC.setFill(fillColor.getValue());
            mainGC.fillRect(startX, startY, r, r);
        }
        if(strokePainting.isSelected() && !fillPainting.isSelected()) {
            mainGC.setStroke(strokColor.getValue());
            mainGC.strokeRect(startX, startY, r, r);
        }
        if(strokePainting.isSelected() && fillPainting.isSelected()) {
            mainGC.setFill(fillColor.getValue());
            mainGC.fillRect(startX, startY, r, r);
            mainGC.setStroke(strokColor.getValue());
            mainGC.strokeRect(startX, startY, r, r);
        }
    }
    private void drawPoint() {
        mainGC.setStroke(fillColor.getValue());
        mainGC.setFill(fillColor.getValue());
        mainGC.fillOval(startX, startY, 5, 5);


    }

    private void drawLine()
    {
        mainGC.setLineWidth(Double.parseDouble(penSize.getText()));
        mainGC.setStroke(fillColor.getValue());
        mainGC.strokeLine(startX, startY, endX, endY);
    }

    private void drawByHand()
    {
        mainGC.setLineWidth(Double.parseDouble(penSize.getText()));
        mainGC.setStroke(fillColor.getValue());
        mainGC.strokeLine(oldX, oldY, endX, endY);
        oldX = endX;
        oldY = endY;
    }

    private void writeText() throws IOException {

        modifyTextCreateController = new ModifyTextCreateController();
        modifyTextCreateController.createScene();
    }

    private void rubberEvent()
    {
        mainGC.setLineWidth(Double.parseDouble(penSize.getText()));
        mainGC.clearRect(oldX, oldY, endX, endY);
        effectGC.clearRect(oldX, oldY, endX, endY);
        oldX = endX;
        oldY = endY;
    }

    private void drawOvalEffect()
    {
        double wh = endX - startX;
        double hg = endY - startY;
        if(fillPainting.isSelected() && !strokePainting.isSelected()){
            effectGC.clearRect(0,0,effectCanvas.getWidth(),effectCanvas.getHeight());
            effectGC.setFill(fillColor.getValue());
            effectGC.fillOval(startX, startY, wh, hg);
        }
        if(strokePainting.isSelected() && !fillPainting.isSelected()) {
            effectGC.clearRect(0,0,effectCanvas.getWidth(),effectCanvas.getHeight());
            effectGC.setStroke(strokColor.getValue());
            effectGC.strokeOval(startX, startY, wh, hg);
        }
        if(strokePainting.isSelected() && fillPainting.isSelected()) {
            effectGC.clearRect(0,0,effectCanvas.getWidth(),effectCanvas.getHeight());
            effectGC.setFill(fillColor.getValue());
            effectGC.fillOval(startX, startY, wh, hg);
            effectGC.setStroke(strokColor.getValue());
            effectGC.strokeOval(startX, startY, wh, hg);
        }
    }

    private void drawCircleEffect()
    {
        double r = endX - startX;
        mainGC.setLineWidth(Double.parseDouble(penSize.getText()));

        if(fillPainting.isSelected() && !strokePainting.isSelected()){
            effectGC.clearRect(0,0,effectCanvas.getWidth(),effectCanvas.getHeight());
            effectGC.setFill(fillColor.getValue());
            effectGC.fillOval(startX, startY, r, r);
        }
        if(strokePainting.isSelected() && !fillPainting.isSelected()){
            effectGC.clearRect(0,0,effectCanvas.getWidth(),effectCanvas.getHeight());
            effectGC.setStroke(strokColor.getValue());
            effectGC.strokeOval(startX, startY, r, r);
        }
        if(strokePainting.isSelected() && fillPainting.isSelected()){
            effectGC.clearRect(0,0,effectCanvas.getWidth(),effectCanvas.getHeight());
            effectGC.setFill(fillColor.getValue());
            effectGC.fillOval(startX, startY, r, r);
            effectGC.setStroke(strokColor.getValue());
            effectGC.strokeOval(startX, startY, r, r);
        }
    }

    private void drawRectEffect()
    {
        double wh = endX - startX;
        double hg = endY - startY;

        if(fillPainting.isSelected() && !strokePainting.isSelected()){
            effectGC.clearRect(0,0,effectCanvas.getWidth(),effectCanvas.getHeight());
            effectGC.setFill(fillColor.getValue());
            effectGC.fillRect(startX, startY, wh, hg);
        }
        if(strokePainting.isSelected() && !fillPainting.isSelected()) {
            effectGC.clearRect(0,0,effectCanvas.getWidth(),effectCanvas.getHeight());
            effectGC.setStroke(strokColor.getValue());
            effectGC.strokeRect(startX, startY, wh, hg);
        }
        if(strokePainting.isSelected() && fillPainting.isSelected()) {
            effectGC.clearRect(0,0,effectCanvas.getWidth(),effectCanvas.getHeight());
            effectGC.setFill(fillColor.getValue());
            effectGC.fillRect(startX, startY, wh, hg);
            effectGC.setStroke(strokColor.getValue());
            effectGC.strokeRect(startX, startY, wh, hg);
        }
    }

    private void drawSquareEffect()
    {
        double r = endX - startX;

        if(fillPainting.isSelected() && !strokePainting.isSelected()){
            effectGC.clearRect(0,0,effectCanvas.getWidth(),effectCanvas.getHeight());
            effectGC.setFill(fillColor.getValue());
            effectGC.fillRect(startX, startY, r, r);
        }
        if(strokePainting.isSelected() && !fillPainting.isSelected()) {
            effectGC.clearRect(0,0,effectCanvas.getWidth(),effectCanvas.getHeight());
            effectGC.setStroke(strokColor.getValue());
            effectGC.strokeRect(startX, startY, r, r);
        }
        if(strokePainting.isSelected() && fillPainting.isSelected()) {
            effectGC.clearRect(0,0,effectCanvas.getWidth(),effectCanvas.getHeight());
            effectGC.setFill(fillColor.getValue());
            effectGC.fillRect(startX, startY, r, r);
            effectGC.setStroke(strokColor.getValue());
            effectGC.strokeRect(startX, startY, r, r);
        }
    }

    private void drawLineEffect()
    {
        effectGC.setLineWidth(Double.parseDouble(penSize.getText()));
        effectGC.setStroke(fillColor.getValue());
        effectGC.clearRect(0, 0, effectCanvas.getWidth() , effectCanvas.getHeight());
        effectGC.strokeLine(startX, startY, endX, endY);
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public Worksheet getWorksheet() {
        return worksheet;
    }

    public Canvas getMainCanvas() {
        return canvas;
    }

    public String getMethod() {
        return method;
    }

    public static Exercise getExercise() {
        return exercise;
    }
}

