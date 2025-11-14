module com.crissystems.arbolb {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.crissystems.arbolb to javafx.fxml;
    exports com.crissystems.arbolb;
}