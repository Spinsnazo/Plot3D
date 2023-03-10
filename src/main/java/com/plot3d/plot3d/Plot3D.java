package com.plot3d.plot3d;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Plot3D extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Plot3D.class.getResource("Plot3D.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1018, 516);
        stage.setTitle("Plot3D");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}