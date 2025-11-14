module com.crissystems.arbolheapinsertar {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.crissystems.arbolheapinsertar to javafx.fxml;
    exports com.crissystems.arbolheapinsertar;
}