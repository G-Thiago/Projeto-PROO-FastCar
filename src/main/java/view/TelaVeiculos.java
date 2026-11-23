package view;

import controller.MaskUtils;
import dao.VeiculoDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ModelVeiculoTabela;

public class TelaVeiculos implements Tela {

    @Override
    public Node criar() {
        VBox containerPrincipal = new VBox(20);
        containerPrincipal.setPadding(new Insets(20));
        containerPrincipal.setStyle("-fx-background-color: #f5f7fa;");

        HBox cabecalho = new HBox();
        cabecalho.setAlignment(Pos.CENTER_LEFT);

        Label lblTitulo = new Label("Veículos");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        Region espacador = new Region();
        HBox.setHgrow(espacador, Priority.ALWAYS);

        Button btnNovoVeiculo = new Button("+ Novo Veículo");
        btnNovoVeiculo.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8px 15px;");

        cabecalho.getChildren().addAll(lblTitulo, espacador, btnNovoVeiculo);
        containerPrincipal.getChildren().add(cabecalho);

        TableView<ModelVeiculoTabela> tabela = new TableView<>();
        btnNovoVeiculo.setOnAction(e -> abrirModalNovoVeiculo(tabela));
        tabela.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0;");
        VBox.setVgrow(tabela, Priority.ALWAYS);

        Label lblVazio = new Label("Nenhum veículo cadastrado no momento.");
        lblVazio.setStyle("-fx-text-fill: #64748b; -fx-font-size: 13px;");
        tabela.setPlaceholder(lblVazio);

        TableColumn<ModelVeiculoTabela, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<ModelVeiculoTabela, String> colMarca = new TableColumn<>("Marca");
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colMarca.setPrefWidth(100);

        TableColumn<ModelVeiculoTabela, String> colModelo = new TableColumn<>("Modelo");
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colModelo.setPrefWidth(120);

        TableColumn<ModelVeiculoTabela, String> colAno = new TableColumn<>("Ano");
        colAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        colAno.setPrefWidth(70);

        TableColumn<ModelVeiculoTabela, String> colPlaca = new TableColumn<>("Placa");
        colPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colPlaca.setPrefWidth(90);

        TableColumn<ModelVeiculoTabela, String> colDiaria = new TableColumn<>("Valor da diária");
        colDiaria.setCellValueFactory(new PropertyValueFactory<>("valorDiaria"));
        colDiaria.setPrefWidth(110);

        TableColumn<ModelVeiculoTabela, String> colDisponibilidade = new TableColumn<>("Disponibilidade");
        colDisponibilidade.setCellValueFactory(new PropertyValueFactory<>("disponibilidade"));
        colDisponibilidade.setPrefWidth(110);

        TableColumn<ModelVeiculoTabela, HBox> colAcoes = new TableColumn<>("Ações");
        colAcoes.setCellValueFactory(new PropertyValueFactory<>("acoes"));
        colAcoes.setPrefWidth(170);

        tabela.getColumns().addAll(colId, colMarca, colModelo, colAno, colPlaca, colDiaria, colDisponibilidade, colAcoes);
        carregarTabela(tabela);

        VBox painelTabela = new VBox(tabela);
        painelTabela.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-radius: 6px; -fx-background-radius: 6px;");
        containerPrincipal.getChildren().add(painelTabela);

        return containerPrincipal;
    }

    private void carregarTabela(TableView<ModelVeiculoTabela> tabela) {
        VeiculoDAO dao = new VeiculoDAO();
        var lista = dao.listar();
        lista.forEach(item -> item.configurarAcoes(
                veiculo -> abrirModalAlterarStatus(veiculo, () -> carregarTabela(tabela)),
                veiculo -> confirmarExclusao(veiculo, () -> carregarTabela(tabela))
        ));
        tabela.getItems().setAll(lista);
    }

    private void abrirModalNovoVeiculo(TableView<ModelVeiculoTabela> tabela) {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Novo Veículo");
        modalStage.setResizable(false);

        VBox rootModal = new VBox(15);
        rootModal.setPadding(new Insets(20));
        rootModal.setStyle("-fx-background-color: white;");
        rootModal.setAlignment(Pos.TOP_LEFT);

        Label lblModalTitulo = new Label("Adicionar Novo Veículo");
        lblModalTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        TextField txtMarca = new TextField();
        txtMarca.setPromptText("Ex: Toyota");

        TextField txtModelo = new TextField();
        txtModelo.setPromptText("Ex: Corolla");

        TextField txtAno = new TextField();
        txtAno.setPromptText("Ex: 2022");
        MaskUtils.aplicarMascaraAno(txtAno);

        TextField txtPlaca = new TextField();
        txtPlaca.setPromptText("Ex: ABC-1D23");
        MaskUtils.aplicarMascaraPlaca(txtPlaca);

        TextField txtValorDiaria = new TextField();
        txtValorDiaria.setPromptText("Ex: 150,00");
        MaskUtils.aplicarMascaraPreco(txtValorDiaria);

        ComboBox<String> cbDisponibilidade = new ComboBox<>();
        cbDisponibilidade.getItems().addAll("Disponível", "Alugado", "Em manutenção");
        cbDisponibilidade.setValue("Disponível");
        cbDisponibilidade.setMaxWidth(Double.MAX_VALUE);

        rootModal.getChildren().addAll(
                lblModalTitulo,
                new Separator(),
                criarCampoForm("Marca:", txtMarca),
                criarCampoForm("Modelo:", txtModelo),
                criarCampoForm("Ano:", txtAno),
                criarCampoForm("Placa:", txtPlaca),
                criarCampoForm("Valor da Diária (R$):", txtValorDiaria),
                criarCampoForm("Disponibilidade:", cbDisponibilidade)
        );

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        btnSalvar.setOnAction(e -> {
            try {
                VeiculoDAO dao = new VeiculoDAO();

                boolean salvou = dao.cadastrar(
                        txtMarca.getText(),
                        txtModelo.getText(),
                        Integer.parseInt(txtAno.getText()),
                        txtPlaca.getText(),
                        Double.parseDouble(
                                txtValorDiaria.getText().replaceAll("\\D", "")
                        ) / 100.0,
                        cbDisponibilidade.getValue()
                );

                if (salvou) {
                    Alert alerta = new Alert(
                            Alert.AlertType.INFORMATION,
                            "Veículo cadastrado com sucesso!"
                    );
                    alerta.setHeaderText(null);
                    alerta.showAndWait();

                    carregarTabela(tabela);
                    modalStage.close();
                } else {
                    Alert alerta = new Alert(
                            Alert.AlertType.ERROR,
                            "Erro ao cadastrar veículo. Confira o console."
                    );
                    alerta.setHeaderText(null);
                    alerta.showAndWait();
                }

            } catch (NumberFormatException ex) {
                Alert alerta = new Alert(
                        Alert.AlertType.ERROR,
                        "Confira o ano e o valor da diária."
                );
                alerta.setHeaderText(null);
                alerta.showAndWait();
            }
        });

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #e2e8f0; -fx-text-fill: #1e293b; -fx-cursor: hand;");
        btnCancelar.setOnAction(e -> modalStage.close());

        HBox boxBotoes = new HBox(10, btnSalvar, btnCancelar);
        boxBotoes.setAlignment(Pos.CENTER_RIGHT);
        rootModal.getChildren().add(boxBotoes);

        Scene scene = new Scene(rootModal, 420, 520);
        modalStage.setScene(scene);
        modalStage.showAndWait();
    }

    private void abrirModalAlterarStatus(ModelVeiculoTabela veiculo, Runnable aoConcluir) {
        VeiculoDAO dao = new VeiculoDAO();

        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Alterar Status");
        modalStage.setResizable(false);

        VBox rootModal = new VBox(15);
        rootModal.setPadding(new Insets(20));
        rootModal.setStyle("-fx-background-color: white;");

        Label lblTitulo = new Label(
                "Alterar disponibilidade — " + veiculo.getMarca() + " " + veiculo.getModelo() + " (" + veiculo.getPlaca() + ")"
        );
        lblTitulo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");
        lblTitulo.setWrapText(true);

        ComboBox<String> cbStatus = new ComboBox<>();
        cbStatus.getItems().addAll("Disponível", "Alugado", "Em manutenção");
        cbStatus.setValue(veiculo.getDisponibilidade());
        cbStatus.setMaxWidth(Double.MAX_VALUE);

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        btnSalvar.setOnAction(e -> {
            boolean ok = dao.atualizarDisponibilidade(
                    Integer.parseInt(veiculo.getId()),
                    cbStatus.getValue()
            );
            if (ok) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Status atualizado com sucesso!");
                alerta.setHeaderText(null);
                alerta.showAndWait();

                if (aoConcluir != null) {
                    aoConcluir.run();
                }
                modalStage.close();
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Erro ao atualizar status. Confira o console.");
                alerta.setHeaderText(null);
                alerta.showAndWait();
            }
        });

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #e2e8f0; -fx-text-fill: #1e293b; -fx-cursor: hand;");
        btnCancelar.setOnAction(e -> modalStage.close());

        HBox botoes = new HBox(10, btnSalvar, btnCancelar);
        botoes.setAlignment(Pos.CENTER_RIGHT);

        rootModal.getChildren().addAll(lblTitulo, cbStatus, botoes);
        modalStage.setScene(new Scene(rootModal, 360, 190));
        modalStage.showAndWait();
    }

    private void confirmarExclusao(ModelVeiculoTabela veiculo, Runnable aoConcluir) {
        VeiculoDAO dao = new VeiculoDAO();

        if ("Alugado".equalsIgnoreCase(veiculo.getDisponibilidade())) {
            Alert aviso = new Alert(
                    Alert.AlertType.WARNING,
                    "Não é possível excluir um veículo que está alugado. Conclua ou cancele a locação ativa primeiro."
            );
            aviso.setHeaderText(null);
            aviso.showAndWait();
            return;
        }

        Alert confirmacao = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Deseja realmente excluir o veículo " + veiculo.getMarca() + " " + veiculo.getModelo() + " (" + veiculo.getPlaca() + ")?",
                ButtonType.YES,
                ButtonType.NO
        );
        confirmacao.setHeaderText(null);

        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.YES) {
                boolean excluiu = dao.excluir(Integer.parseInt(veiculo.getId()));
                if (excluiu) {
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Veículo excluído com sucesso!");
                    alerta.setHeaderText(null);
                    alerta.showAndWait();

                    if (aoConcluir != null) {
                        aoConcluir.run();
                    }
                } else {
                    Alert alerta = new Alert(
                            Alert.AlertType.ERROR,
                            "Não foi possível excluir o veículo. Verifique se ele possui locações vinculadas."
                    );
                    alerta.setHeaderText(null);
                    alerta.showAndWait();
                }
            }
        });
    }
}