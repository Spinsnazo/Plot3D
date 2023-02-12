module com.plot3d.plot3d {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.plot3d.plot3d to javafx.fxml;
    exports com.plot3d.plot3d;
}