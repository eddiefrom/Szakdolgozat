package controllers.student;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DrawingController implements Initializable {

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
    @FXML private ImageView imageView;

    private TextCreatorStudentController textCreatorStudentController;
    private GraphicsContext mainGC, effectGC;
    private boolean lineDrawing, ovalDrawing, rectDrawing, drawByHand, rubbering, circleDrawing,
            squareDrawing, pointDrawing, textWriting;
    private static double startX, startY, endX, endY, oldX, oldY;
    private File file;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainGC = mainCanvas.getGraphicsContext2D();
        effectGC = effectCanvas.getGraphicsContext2D();
        canvas = mainCanvas;
        imageView.setDisable(true);

        penSize.setText("10");
        fillPainting.setSelected(true);
    }

    public DrawingController() {

        imageView = new ImageView();

    }

    public void createScene() throws IOException {

        this.stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/student/DrawingVIEW.fxml"));
        pane = loader.load();

        Scene scene = new Scene(pane);
        scene.getStylesheets().add("css/main_back.css");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();

    }

    @FXML
    public void createExercise(ActionEvent actionEvent) throws IOException {

        if(setImageDialog() != null)
            pictureSave();
    }

    private File setImageDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        return file = fileChooser.showSaveDialog(stage);
    }

    private void pictureSave() throws IOException {
        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(null, writableImage);
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
        ImageIO.write(renderedImage, "png", file);
    }

    @FXML
    public void exitButtonEvent(ActionEvent actionEvent) {

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void deleteCanvas(ActionEvent e)
    {
        imageView.setImage(null);
        imageView.setDisable(true);
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
        if(textWriting) {
            writeText();
        }
    }

    @FXML
    private void mouseRelease(MouseEvent mouseEvent){
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

        textCreatorStudentController = new TextCreatorStudentController();
        textCreatorStudentController.createScene();

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

    public Canvas getMainCanvas() {
        return canvas;
    }
}


