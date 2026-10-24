package model;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // Inicializa a classe Tela que gerencia toda a interface gráfica
        Tela.iniciarAplicacao(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}