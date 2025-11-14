module com.crissystems.arbolconrecorridoinorden {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.crissystems.arbolconrecorridoinorden to javafx.fxml;
    exports com.crissystems.arbolconrecorridoinorden;
}