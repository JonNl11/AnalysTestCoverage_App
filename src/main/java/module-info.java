module com.example.analystestcoverage_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires org.jsoup;
    requires jai.imageio.core;
   // requires org.junit.jupiter.api;
   // requires org.junit.platform.commons;
   // requires transitive java.desktop;
    requires javafx.swing;


    opens com.example.analystestcoverage_app to javafx.fxml;
    exports com.example.analystestcoverage_app;
    opens Elements to javafx.base;
   // exports Elements to org.junit.jupiter.api;
   // exports Efficient to org.junit.jupiter.api;
   // exports Connect to org.junit.jupiter.api;

}
