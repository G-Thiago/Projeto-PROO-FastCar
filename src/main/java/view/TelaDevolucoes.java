package view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class TelaDevolucoes {

    public static VBox criar() {
        VBox painel = new VBox(15);
        painel.setPadding(new Insets(25));
        painel.setStyle("-fx-background-color: #f8fafc;");

        // --- TOPO: Título e Botão de Ação Opcional ---
        HBox topo = new HBox();
        topo.setAlignment(Pos.CENTER_LEFT);

        Label lblTitulo = new Label("Devoluções");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        Region espaco = new Region();
        HBox.setHgrow(espaco, Priority.ALWAYS);

        // Botão secundário ou informativo para o topo (opcional)
        Button btnAtualizar = new Button("🔄 Atualizar Lista");
        btnAtualizar.setStyle("-fx-background-color: #e2e8f0; -fx-text-fill: #1e293b; -fx-font-weight: bold; -fx-background-radius: 6px; -fx-padding: 8px 14px; -fx-cursor: hand;");

        topo.getChildren().addAll(lblTitulo, espaco, btnAtualizar);

        // --- BARRA DE PESQUISA ---
        HBox barraPesquisa = new HBox(10);
        barraPesquisa.setAlignment(Pos.CENTER_LEFT);

        TextField txtPesquisa = new TextField();
        txtPesquisa.setPromptText("🔍   Buscar por nome, placa ou ID da locação...");
        txtPesquisa.setPrefWidth(380);
        txtPesquisa.setStyle("-fx-background-radius: 6px; -fx-border-color: #cbd5e1; -fx-border-radius: 6px; -fx-padding: 7px;");

        barraPesquisa.getChildren().add(txtPesquisa);

        // --- TABELA DE DEVOLUÇÕES (Locações em andamento) ---
        TableView<String[]> tabela = new TableView<>();
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<String[], String> colIdLocacao = new TableColumn<>("ID Locação");
        colIdLocacao.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[0]));

        TableColumn<String[], String> colCliente = new TableColumn<>("Cliente");
        colCliente.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[1]));

        TableColumn<String[], String> colVeiculo = new TableColumn<>("Veículo");
        colVeiculo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[2]));

        TableColumn<String[], String> colPlaca = new TableColumn<>("Placa");
        colPlaca.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[3]));

        TableColumn<String[], String> colDataRetirada = new TableColumn<>("Data de Retirada");
        colDataRetirada.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[4]));

        TableColumn<String[], String> colPrevisaoDevolucao = new TableColumn<>("Previsão de Devolução");
        colPrevisaoDevolucao.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[5]));

        tabela.getColumns().addAll(colIdLocacao, colCliente, colVeiculo, colPlaca, colDataRetirada, colPrevisaoDevolucao);

        VBox.setVgrow(tabela, Priority.ALWAYS);
        painel.getChildren().addAll(topo, barraPesquisa, tabela);
        return painel;
    }
}