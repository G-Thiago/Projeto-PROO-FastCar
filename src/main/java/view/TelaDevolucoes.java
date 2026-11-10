package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TelaDevolucoes implements Tela {

    @Override
    public Node criar() {
        VBox containerPrincipal = new VBox(20);
        containerPrincipal.setPadding(new Insets(20));
        containerPrincipal.setStyle("-fx-background-color: #f5f7fa;");

        // Título da Tela
        Label lblTitulo = new Label("Devoluções");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");
        containerPrincipal.getChildren().add(lblTitulo);

        //  Seção
        VBox painelSecao = new VBox(15);
        painelSecao.setPadding(new Insets(15));
        painelSecao.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-radius: 6px; -fx-background-radius: 6px;");
        VBox.setVgrow(painelSecao, Priority.ALWAYS);


        Label lblSubtitulo = new Label("Locações em andamento");
        lblSubtitulo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        // Barra de Busca
        TextField txtBusca = new TextField();
        txtBusca.setPromptText("🔍 Buscar por nome, placa ou ID da locação...");
        txtBusca.setMaxWidth(400);
        txtBusca.setStyle("-fx-pref-height: 32px;");

        painelSecao.getChildren().addAll(lblSubtitulo, txtBusca, new Separator());

        // Locações Ativas
        TableView<ModelDevolucaoTabela> tabela = new TableView<>();
        tabela.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0;");

        Label lblVazio = new Label("Não há locações ativas para devolução.");
        lblVazio.setStyle("-fx-text-fill: #64748b; -fx-font-size: 13px;");
        tabela.setPlaceholder(lblVazio);

        VBox.setVgrow(tabela, Priority.ALWAYS);

        TableColumn<ModelDevolucaoTabela, String> colIdLocacao = new TableColumn<>("ID Locação");
        colIdLocacao.setCellValueFactory(new PropertyValueFactory<>("idLocacao"));
        colIdLocacao.setPrefWidth(90);

        TableColumn<ModelDevolucaoTabela, String> colCliente = new TableColumn<>("Cliente");
        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colCliente.setPrefWidth(160);

        TableColumn<ModelDevolucaoTabela, String> colVeiculo = new TableColumn<>("Veículo");
        colVeiculo.setCellValueFactory(new PropertyValueFactory<>("veiculo"));
        colVeiculo.setPrefWidth(130);

        TableColumn<ModelDevolucaoTabela, String> colPlaca = new TableColumn<>("Placa");
        colPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colPlaca.setPrefWidth(90);

        TableColumn<ModelDevolucaoTabela, String> colRetirada = new TableColumn<>("Data de retirada");
        colRetirada.setCellValueFactory(new PropertyValueFactory<>("dataRetirada"));
        colRetirada.setPrefWidth(120);

        TableColumn<ModelDevolucaoTabela, String> colPrevisao = new TableColumn<>("Previsão de devolução");
        colPrevisao.setCellValueFactory(new PropertyValueFactory<>("previsaoDevolucao"));
        colPrevisao.setPrefWidth(140);

        TableColumn<ModelDevolucaoTabela, String> colAcoes = new TableColumn<>("Ações");
        colAcoes.setCellValueFactory(new PropertyValueFactory<>("acoes"));
        colAcoes.setPrefWidth(140);

        tabela.getColumns().addAll(colIdLocacao, colCliente, colVeiculo, colPlaca, colRetirada, colPrevisao, colAcoes);

        painelSecao.getChildren().add(tabela);
        containerPrincipal.getChildren().add(painelSecao);

        return containerPrincipal;
    }

   // Modal

    public static void abrirModalDevolucao(String idLocacao, String cliente, String veiculo) {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("🔄 Registrar Devolução");
        modalStage.setResizable(false);

        VBox rootModal = new VBox(15);
        rootModal.setPadding(new Insets(20));
        rootModal.setStyle("-fx-background-color: white;");
        rootModal.setAlignment(Pos.TOP_LEFT);

        Label lblModalTitulo = new Label("🔄 Confirmar Devolução do Veículo");
        lblModalTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        VBox detalhesLocacao = new VBox(8);
        detalhesLocacao.setPadding(new Insets(10));
        detalhesLocacao.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #e2e8f0; -fx-border-radius: 4px;");

        detalhesLocacao.getChildren().addAll(
                new Label("ID da Locação: " + idLocacao),
                new Label("Cliente: " + cliente),
                new Label("Veículo: " + veiculo)
        );

        Label lblChecklist = new Label("Inspeção rápida de devolução:");
        lblChecklist.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #475569;");

        CheckBox chkLimpeza = new CheckBox("Veículo entregue limpo");
        CheckBox chkAvarias = new CheckBox("Sem novas avarias aparentes");
        CheckBox chkTanque = new CheckBox("Tanque de combustível verificado");

        rootModal.getChildren().addAll(
                lblModalTitulo,
                new Separator(),
                detalhesLocacao,
                lblChecklist,
                chkLimpeza,
                chkAvarias,
                chkTanque
        );

        Button btnConfirmar = new Button("Confirmar Devolução");
        btnConfirmar.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        btnConfirmar.setOnAction(e -> {
            // Modal secundário ou alerta de sucesso simulado
            Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Devolução registrada com sucesso! (Sem DAO)");
            alerta.setHeaderText(null);
            alerta.showAndWait();
            modalStage.close();
        });

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #e2e8f0; -fx-text-fill: #1e293b; -fx-cursor: hand;");
        btnCancelar.setOnAction(e -> modalStage.close());

        HBox boxBotoes = new HBox(10, btnConfirmar, btnCancelar);
        boxBotoes.setAlignment(Pos.CENTER_RIGHT);
        rootModal.getChildren().add(boxBotoes);

        Scene scene = new Scene(rootModal, 400, 420);
        modalStage.setScene(scene);
        modalStage.showAndWait();
    }

    // Temporário
    public static class ModelDevolucaoTabela {
        private String idLocacao, cliente, veiculo, placa, dataRetirada, previsaoDevolucao;
        private Button acoes;

        public ModelDevolucaoTabela(String idLocacao, String cliente, String veiculo, String placa, String dataRetirada, String previsaoDevolucao) {
            this.idLocacao = idLocacao;
            this.cliente = cliente;
            this.veiculo = veiculo;
            this.placa = placa;
            this.dataRetirada = dataRetirada;
            this.previsaoDevolucao = previsaoDevolucao;

            // Botão de ação integrado na linha
            this.acoes = new Button("Devolver");
            this.acoes.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 11px;");
            this.acoes.setOnAction(e -> abrirModalDevolucao(idLocacao, cliente, veiculo));
        }

        public String getIdLocacao() { return idLocacao; }
        public String getCliente() { return cliente; }
        public String getVeiculo() { return veiculo; }
        public String getPlaca() { return placa; }
        public String getDataRetirada() { return dataRetirada; }
        public String getPrevisaoDevolucao() { return previsaoDevolucao; }
        public Button getAcoes() { return acoes; }
    }
}