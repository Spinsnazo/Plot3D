package com.plot3d.plot3d;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import static java.lang.Math.max;
import static java.lang.Math.abs;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable {
    @FXML
    private VBox Window;
    @FXML
    private Button LoadDataButton;
    @FXML
    private Spinner<Integer>AlphaAngle;
    @FXML
    private Spinner<Integer>BetaAngle;
    @FXML
    private Spinner<Integer>GammaAngle;
    @FXML
    private Spinner<Integer> z0Spinner;
    @FXML
    private Spinner<Integer> dSpinner;
    @FXML
    private StackPane holder;
    @FXML
    private Canvas drawingPanel;
    @FXML
    private Button clearButton;
    @FXML
    private Button castingButton;
    private GraphicsContext gc;
    private int alpha = 0;
    private int beta = 0;
    private int gamma = 0;
    private int z0 = 100;
    private int d = -1;
    private ListOfPoints<Point3D> readPoints;

    @FXML
    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        holder.getChildren().add(drawingPanel);
        holder.setStyle("-fx-background-color: black");
        gc = drawingPanel.getGraphicsContext2D();
        LoadDataButton.setOnAction(this::loadData);
        clearButton.setOnAction(actionEvent -> clearPanel());
        castingButton.setOnAction(this::plotPoints);
        SpinnerValueFactory<Integer> alphaValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 360);
        SpinnerValueFactory<Integer> betaValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 360);
        SpinnerValueFactory<Integer> gammaValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 360);
        SpinnerValueFactory<Integer> z0ValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(-500, 500);
        SpinnerValueFactory<Integer> dValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(-500, 500);
        alphaValueFactory.setValue(0);
        betaValueFactory.setValue(0);
        gammaValueFactory.setValue(0);
        z0ValueFactory.setValue(100);
        dValueFactory.setValue(-1);
        AlphaAngle.setValueFactory(alphaValueFactory);
        BetaAngle.setValueFactory(betaValueFactory);
        GammaAngle.setValueFactory(gammaValueFactory);
        z0Spinner.setValueFactory(z0ValueFactory);
        dSpinner.setValueFactory(dValueFactory);

        alphaValueFactory.valueProperty().addListener((observableValue, integer, t1) -> {
            alpha = alphaValueFactory.getValue();
            System.out.println("alpha = " + alpha + " deg");
        });
        betaValueFactory.valueProperty().addListener((observableValue, integer, t1) -> {
            beta = betaValueFactory.getValue();
            System.out.println("beta = " + beta + " deg");
        });
        gammaValueFactory.valueProperty().addListener((observableValue, integer, t1) -> {
            gamma = gammaValueFactory.getValue();
            System.out.println("gamma = " + gamma + " deg");
        });
        z0ValueFactory.valueProperty().addListener((observableValue, integer, t1) -> {
            z0 = z0ValueFactory.getValue();
            System.out.println("z0 = " + z0);
        });
        dValueFactory.valueProperty().addListener((observableValue, integer, t1) -> {
            d = dValueFactory.getValue();
            System.out.println("d = " + z0);
        });
    }

    private void loadData(ActionEvent e){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Please select the file...");
        File dataFile = fileChooser.showOpenDialog(Window.getScene().getWindow());
        if (dataFile != null) {
            try {
                FileReader fileReader = new FileReader(dataFile);
                readPoints = null;
                readPoints = new ListOfPoints<>();
                Scanner input = new Scanner(fileReader);
                input.useDelimiter("[;\n]");
                while (input.hasNext()) {
                    double x = Double.parseDouble(input.next());
                    double y = Double.parseDouble(input.next());
                    double z = Double.parseDouble(input.next());
                    Point3D P = new Point3D(x, y, z);
                    readPoints.addPoint(P);
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Reading data");
                alert.setContentText("Data has been read successfully.");
                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("An error occurred while reading the data.");
                alert.showAndWait();
            }
        }
    }

    private void plotPoints(ActionEvent e){
        if(readPoints == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("There is no data provided.");
            alert.showAndWait();
            return;
        }
        ListOfPoints<Point2D> castPoints2DLeft = new ListOfPoints<>();
        ListOfPoints<Point2D> castPoints2DRight = new ListOfPoints<>();

        try{
            int xAxis = (int) (drawingPanel.getHeight() / 2);
            int yAxis = (int) (drawingPanel.getWidth() / 2);

            double maxX = 0;
            double maxY = 0;

            for(int i=0; i<readPoints.calculateSize(); i++){
                Point3D rotated = new Point3D(readPoints.getPoint(i));
                rotated.rotateOX(alpha);
                rotated.rotateOY(beta);
                rotated.rotateOZ(gamma);
                Point2D P = rotated.CastTo2D(z0, d);
                Point2D L = rotated.CastTo2D(z0, -d);
                double localMax = max(abs(P.getX()), abs(L.getX()));
                if(localMax > maxX)
                    maxX = localMax;
                localMax = max(abs(P.getY()), abs(L.getY()));
                if(localMax > maxY)
                    maxY = localMax;
                castPoints2DRight.addPoint(P);
                castPoints2DLeft.addPoint(L);
            }

            System.out.println("MAX X = " + maxX + " MAX Y = " + maxY);

            for(int i = 0; i< castPoints2DRight.calculateSize(); i++){
                double x = castPoints2DRight.getPoint(i).getX();
                double y = castPoints2DRight.getPoint(i).getY();
                plotPoint(yAxis, xAxis, x, y, maxX, maxY, Color.RED);
            }
            for(int i = 0; i< castPoints2DLeft.calculateSize(); i++){
                double x = castPoints2DLeft.getPoint(i).getX();
                double y = castPoints2DLeft.getPoint(i).getY();
                plotPoint(yAxis, xAxis, x, y, maxX, maxY, Color.CYAN);
            }

        }
        catch(Exception ex){
            clearPanel();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("There is no data provided.");
            alert.showAndWait();
        }
    }

    private void clearPanel(){
        gc.clearRect(0, 0, drawingPanel.getWidth(), drawingPanel.getHeight());
    }

    private void plotPoint(int yAxis, int xAxis, double x, double y, double maxX, double maxY, Color c){
        int x0 = (int) (x*yAxis/maxX);
        int y0 = (int) (y*xAxis/maxY);
        //System.out.println("x0 = " + x0 + ", y0 = " + y0);
        x0 = (x0 < 0 ? yAxis - abs(x0) : yAxis + x0);
        y0 = (y0 < 0 ? xAxis + abs(y0) : xAxis - y0);
        gc.setStroke(c);
        gc.strokeLine(x0, y0, x0, y0);
        System.out.println("x0 = " + x0 + ", y0 = " + y0);
    }
}
