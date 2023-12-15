module com.example.readeasy {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                    requires org.kordamp.bootstrapfx.core;
            
    opens br.ufrpe.readeasy to javafx.fxml;
    exports br.ufrpe.readeasy;
}