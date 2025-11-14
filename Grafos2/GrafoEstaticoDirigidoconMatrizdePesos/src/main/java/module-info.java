module com.crissystems.grafoestaticodirigidoconmatrizdepesos {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.crissystems.grafoestaticodirigidoconmatrizdepesos to javafx.fxml;
    exports com.crissystems.grafoestaticodirigidoconmatrizdepesos;
}