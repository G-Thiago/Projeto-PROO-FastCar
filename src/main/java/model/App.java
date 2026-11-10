package model;

import javafx.application.Application;
import javafx.stage.Stage;
import view.TelaPrincipal;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        TelaPrincipal.iniciarAplicacao(stage);

     }

    public static void main(String[] args) {
        launch();
    }
}