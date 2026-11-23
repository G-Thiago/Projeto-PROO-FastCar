module org.example {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql.rowset;
    requires java.sql;

    exports app;
    exports view;
    exports controller;
    exports dao;
    exports model;

    opens view to javafx.base;
    opens model to javafx.base;
}