module com.crissystems.grafoanimado {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.crissystems.grafoanimado to javafx.fxml;
    exports com.crissystems.grafoanimado;
}