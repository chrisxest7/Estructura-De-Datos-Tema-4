module com.crissystems.grafoconbfs {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.crissystems.grafoconbfs to javafx.fxml;
    exports com.crissystems.grafoconbfs;
}