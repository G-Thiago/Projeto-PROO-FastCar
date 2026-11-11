package view;

import controller.MaskUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaLocacao implements Tela {

    @Override
    public Node criar() {

        VBox containerPrincipal = new VBox(20);
        containerPrincipal.setPadding(new Insets(20));
        containerPrincipal.setStyle("-fx-background-color: #f5f7fa;");

        Label lblTitulo = new Label("Nova Locação");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");
        containerPrincipal.getChildren().add(lblTitulo);

        // Grid principal
        GridPane gridFormulario = new GridPane();
        gridFormulario.setHgap(20);
        gridFormulario.setVgap(15);
        VBox.setVgrow(gridFormulario, Priority.ALWAYS);

        // Cliente
        VBox colunaCliente = new VBox(12);
        colunaCliente.setPadding(new Insets(15));
        colunaCliente.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-radius: 6px; -fx-background-radius: 6px;");

        Label lblEtapa1 = new Label("1. Cliente");
        lblEtapa1.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2563eb;");

        TextField txtBuscaCliente = new TextField();
        txtBuscaCliente.setPromptText("🔍 Buscar cliente por nome ou CPF...");

        TextField txtCpfCliente = new TextField();
        txtCpfCliente.setPromptText("000.000.000-00");
        MaskUtils.aplicarMascaraCpf(txtCpfCliente);

        TextField txtTelefoneCliente = new TextField();
        txtTelefoneCliente.setPromptText("(00) 00000-0000");
        MaskUtils.aplicarMascaraTelefone(txtTelefoneCliente);

        TextField txtCnhCliente = new TextField();
        txtCnhCliente.setPromptText("CNH do cliente");

        colunaCliente.getChildren().addAll(
                lblEtapa1,
                new Separator(),
                criarCampoForm("Cliente:", txtBuscaCliente),
                criarCampoForm("CPF:", txtCpfCliente),
                criarCampoForm("Telefone:", txtTelefoneCliente),
                criarCampoForm("CNH:", txtCnhCliente)
        );

        // Veículo
        VBox colunaVeiculo = new VBox(12);
        colunaVeiculo.setPadding(new Insets(15));
        colunaVeiculo.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-radius: 6px; -fx-background-radius: 6px;");

        Label lblEtapa2 = new Label("2. Veículo");
        lblEtapa2.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2563eb;");

        TextField txtBuscaVeiculo = new TextField();
        txtBuscaVeiculo.setPromptText("🔍 Buscar veículo por modelo ou placa...");

        TextField txtMarca = new TextField();
        txtMarca.setPromptText("Marca");

        TextField txtModelo = new TextField();
        txtModelo.setPromptText("Modelo");

        TextField txtPlaca = new TextField();
        txtPlaca.setPromptText("ABC-1023");
        MaskUtils.aplicarMascaraPlaca(txtPlaca);

        TextField txtDiaria = new TextField();
        txtDiaria.setPromptText("R$ 0,00");

        Label lblDisponibilidade = new Label("Disponibilidade: Disponível");
        lblDisponibilidade.setStyle("-fx-text-fill: #16a34a; -fx-font-weight: bold;");

        colunaVeiculo.getChildren().addAll(
                lblEtapa2,
                new Separator(),
                criarCampoForm("Veículo:", txtBuscaVeiculo),
                criarCampoForm("Marca:", txtMarca),
                criarCampoForm("Modelo:", txtModelo),
                criarCampoForm("Placa:", txtPlaca),
                criarCampoForm("Valor da diária:", txtDiaria),
                lblDisponibilidade
        );

        // Período
        VBox colunaPeriodo = new VBox(12);
        colunaPeriodo.setPadding(new Insets(15));
        colunaPeriodo.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-radius: 6px; -fx-background-radius: 6px;");

        Label lblEtapa3 = new Label("3. Período da locação");
        lblEtapa3.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2563eb;");

        TextField txtRetirada = new TextField();
        txtRetirada.setPromptText("DD/MM/AAAA");
        MaskUtils.aplicarMascaraData(txtRetirada);

        TextField txtDevolucao = new TextField();
        txtDevolucao.setPromptText("DD/MM/AAAA");
        MaskUtils.aplicarMascaraData(txtDevolucao);

        Label lblDiasCalculados = new Label("Dias: X");
        Label lblValorTotal = new Label("R$ 0,00");
        lblValorTotal.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        Label lblDetalheTotal = new Label("(X dias x R$ 0,00)");
        lblDetalheTotal.setStyle("-fx-text-fill: #64748b; -fx-font-size: 11px;");

        VBox boxTotal = new VBox(2, new Label("Valor total da locação:"), lblValorTotal, lblDetalheTotal);
        boxTotal.setPadding(new Insets(10));
        boxTotal.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #e2e8f0; -fx-border-radius: 4px;");

        Button btnRegistrar = new Button("🚗 Registrar Locação");
        btnRegistrar.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-pref-height: 38px;");
        btnRegistrar.setMaxWidth(Double.MAX_VALUE);
        btnRegistrar.setOnAction(e -> {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Locação registrada com sucesso!");
            alerta.setHeaderText(null);
            alerta.showAndWait();
        });

        colunaPeriodo.getChildren().addAll(
                lblEtapa3,
                new Separator(),
                criarCampoForm("Data de retirada:", txtRetirada),
                criarCampoForm("Data de devolução:", txtDevolucao),
                lblDiasCalculados,
                boxTotal,
                new Separator(),
                btnRegistrar
        );

        ColumnConstraints col1 = new ColumnConstraints(); col1.setPercentWidth(33.3);
        ColumnConstraints col2 = new ColumnConstraints(); col2.setPercentWidth(33.3);
        ColumnConstraints col3 = new ColumnConstraints(); col3.setPercentWidth(33.3);
        gridFormulario.getColumnConstraints().addAll(col1, col2, col3);

        gridFormulario.add(colunaCliente, 0, 0);
        gridFormulario.add(colunaVeiculo, 1, 0);
        gridFormulario.add(colunaPeriodo, 2, 0);

        containerPrincipal.getChildren().add(gridFormulario);
        return containerPrincipal;
    }
}