module com.crissystems.arbolavl {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.crissystems.arbolavl to javafx.fxml;
    exports com.crissystems.arbolavl;
}