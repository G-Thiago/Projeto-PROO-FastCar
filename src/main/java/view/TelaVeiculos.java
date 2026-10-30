package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class TelaVeiculos {

    public static VBox criar() {
        VBox painel = new VBox(15);
        painel.setPadding(new Insets(25));

        HBox topo = new HBox();
        topo.setAlignment(Pos.CENTER_LEFT);
        Label lblTitulo = new Label("Gerenciamento de Veículos");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        Region espaco = new Region();
        HBox.setHgrow(espaco, Priority.ALWAYS);

        Button btnNovoVeiculo = new Button("+ Novo Veículo");
        btnNovoVeiculo.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6px; -fx-padding: 8px 14px; -fx-cursor: hand;");
        topo.getChildren().addAll(lblTitulo, espaco, btnNovoVeiculo);

        TableView<String[]> tabela = new TableView<>();
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<String[], String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[0]));

        TableColumn<String[], String> colMarca = new TableColumn<>("Marca");
        colMarca.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[1]));

        TableColumn<String[], String> colModelo = new TableColumn<>("Modelo");
        colModelo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[2]));

        TableColumn<String[], String> colAno = new TableColumn<>("Ano");
        colAno.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[3]));

        TableColumn<String[], String> colPlaca = new TableColumn<>("Placa");
        colPlaca.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[4]));

        TableColumn<String[], String> colDiaria = new TableColumn<>("Valor da Diária");
        colDiaria.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[5]));

        TableColumn<String[], String> colDisponibilidade = new TableColumn<>("Disponibilidade");
        colDisponibilidade.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[6]));

        tabela.getColumns().addAll(colId, colMarca, colModelo, colAno, colPlaca, colDiaria, colDisponibilidade);

        VBox.setVgrow(tabela, Priority.ALWAYS);
        painel.getChildren().addAll(topo, tabela);
        return painel;
    }
}