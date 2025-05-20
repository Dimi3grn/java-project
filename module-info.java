module game {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    
    opens src to javafx.graphics;
    opens src.view to javafx.fxml;
    opens src.controller to javafx.fxml;
} 