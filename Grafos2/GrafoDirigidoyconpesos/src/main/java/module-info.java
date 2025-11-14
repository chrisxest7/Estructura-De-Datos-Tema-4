module com.crissystems.grafodirigidoyconpesos {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.crissystems.grafodirigidoyconpesos to javafx.fxml;
    exports com.crissystems.grafodirigidoyconpesos;
}