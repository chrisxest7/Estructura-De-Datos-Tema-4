module com.crissystems.grafodinamico {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.crissystems.grafodinamico to javafx.fxml;
    exports com.crissystems.grafodinamico;
}