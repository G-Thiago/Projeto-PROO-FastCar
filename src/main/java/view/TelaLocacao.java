package view;

import dao.LocacaoDAO;
import dao.LocacaoDAO.ItemCliente;
import dao.LocacaoDAO.ItemVeiculo;
import controller.MaskUtils;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class TelaLocacao implements Tela {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Locale LOCALE_BR = Locale.forLanguageTag("pt-BR");

    @Override
    public Node criar() {
        LocacaoDAO dao = new LocacaoDAO();

        VBox containerPrincipal = new VBox(20);
        containerPrincipal.setPadding(new Insets(20));
        containerPrincipal.setStyle("-fx-background-color: #f5f7fa;");

        Label lblTitulo = new Label("Nova Locação");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");
        containerPrincipal.getChildren().add(lblTitulo);

        GridPane gridFormulario = new GridPane();
        gridFormulario.setHgap(20);
        gridFormulario.setVgap(15);
        VBox.setVgrow(gridFormulario, Priority.ALWAYS);

        // 1. Cliente
        VBox colunaCliente = new VBox(12);
        colunaCliente.setPadding(new Insets(15));
        colunaCliente.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-radius: 6px; -fx-background-radius: 6px;");

        Label lblEtapa1 = new Label("1. Cliente");
        lblEtapa1.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2563eb;");

        ComboBox<ItemCliente> cbCliente = new ComboBox<>();
        cbCliente.getItems().setAll(dao.listarClientes());
        cbCliente.setPromptText("Selecione um cliente...");
        cbCliente.setMaxWidth(Double.MAX_VALUE);

        TextField txtCpfCliente = new TextField();
        txtCpfCliente.setPromptText("000.000.000-00");
        MaskUtils.aplicarMascaraCpf(txtCpfCliente);

        TextField txtTelefoneCliente = new TextField();
        txtTelefoneCliente.setPromptText("(00) 00000-0000");
        MaskUtils.aplicarMascaraTelefone(txtTelefoneCliente);

        TextField txtCnhCliente = new TextField();
        txtCnhCliente.setPromptText("CNH do cliente");
        MaskUtils.aplicarMascaraCNH(txtCnhCliente);

        cbCliente.setOnAction(e -> {
            ItemCliente selecionado = cbCliente.getValue();
            if (selecionado != null) {
                txtCpfCliente.setText(selecionado.getCpf());
                txtTelefoneCliente.setText(selecionado.getTelefone());
                txtCnhCliente.setText(selecionado.getCnh());
            }
        });

        colunaCliente.getChildren().addAll(
                lblEtapa1,
                new Separator(),
                criarCampoForm("Cliente:", cbCliente),
                criarCampoForm("CPF:", txtCpfCliente),
                criarCampoForm("Telefone:", txtTelefoneCliente),
                criarCampoForm("CNH:", txtCnhCliente)
        );

        // 2. Veículo
        VBox colunaVeiculo = new VBox(12);
        colunaVeiculo.setPadding(new Insets(15));
        colunaVeiculo.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-radius: 6px; -fx-background-radius: 6px;");

        Label lblEtapa2 = new Label("2. Veículo");
        lblEtapa2.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2563eb;");

        ComboBox<ItemVeiculo> cbVeiculo = new ComboBox<>();
        cbVeiculo.getItems().setAll(dao.listarVeiculos());
        cbVeiculo.setPromptText("Selecione um veículo...");
        cbVeiculo.setMaxWidth(Double.MAX_VALUE);

        TextField txtMarca = new TextField();
        txtMarca.setPromptText("Marca");

        TextField txtModelo = new TextField();
        txtModelo.setPromptText("Modelo");

        TextField txtPlaca = new TextField();
        txtPlaca.setPromptText("ABC-1023");
        MaskUtils.aplicarMascaraPlaca(txtPlaca);

        TextField txtDiaria = new TextField();
        txtDiaria.setPromptText("150,00");
        MaskUtils.aplicarMascaraPreco(txtDiaria);

        Label lblDisponibilidade = new Label("Disponibilidade: -");
        lblDisponibilidade.setStyle("-fx-text-fill: #64748b; -fx-font-weight: bold;");

        // 3. Período da locação
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

        Label lblDiasCalculados = new Label("Dias: 0");
        Label lblValorTotal = new Label("R$ 0,00");
        lblValorTotal.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        Label lblDetalheTotal = new Label("(0 dias x R$ 0,00)");
        lblDetalheTotal.setStyle("-fx-text-fill: #64748b; -fx-font-size: 11px;");

        Runnable atualizarCalculo = () -> {
            double diaria = parseValorDiaria(txtDiaria.getText());
            long dias = 0;

            try {
                if (txtRetirada.getText().length() == 10 && txtDevolucao.getText().length() == 10) {
                    LocalDate inicio = LocalDate.parse(txtRetirada.getText(), FORMATO_DATA);
                    LocalDate fim = LocalDate.parse(txtDevolucao.getText(), FORMATO_DATA);
                    long diff = ChronoUnit.DAYS.between(inicio, fim);
                    if (diff >= 0) {
                        dias = Math.max(1, diff);
                    }
                }
            } catch (DateTimeParseException ignored) {
                dias = 0;
            }

            double total = dias * diaria;
            lblDiasCalculados.setText("Dias: " + dias);
            lblValorTotal.setText(String.format(LOCALE_BR, "R$ %.2f", total));
            lblDetalheTotal.setText(String.format(LOCALE_BR, "(%d dias x R$ %.2f)", dias, diaria));
        };

        cbVeiculo.setOnAction(e -> {
            ItemVeiculo selecionado = cbVeiculo.getValue();
            if (selecionado != null) {
                txtMarca.setText(selecionado.getMarca());
                txtModelo.setText(selecionado.getModelo());
                txtPlaca.setText(selecionado.getPlaca());
                txtDiaria.setText(String.format(LOCALE_BR, "%.2f", selecionado.getValorDiaria()));
                lblDisponibilidade.setText("Disponibilidade: " + selecionado.getDisponibilidade());
                if ("Disponível".equalsIgnoreCase(selecionado.getDisponibilidade())) {
                    lblDisponibilidade.setStyle("-fx-text-fill: #16a34a; -fx-font-weight: bold;");
                } else {
                    lblDisponibilidade.setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
                }
                atualizarCalculo.run();
            }
        });

        txtDiaria.textProperty().addListener((obs, oldVal, newVal) -> atualizarCalculo.run());
        txtRetirada.textProperty().addListener((obs, oldVal, newVal) -> atualizarCalculo.run());
        txtDevolucao.textProperty().addListener((obs, oldVal, newVal) -> atualizarCalculo.run());

        VBox boxTotal = new VBox(2, new Label("Valor total da locação:"), lblValorTotal, lblDetalheTotal);
        boxTotal.setPadding(new Insets(10));
        boxTotal.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #e2e8f0; -fx-border-radius: 4px;");

        Button btnRegistrar = new Button("🚗 Registrar Locação");
        btnRegistrar.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-pref-height: 38px;");
        btnRegistrar.setMaxWidth(Double.MAX_VALUE);
        btnRegistrar.setOnAction(e -> {
            ItemCliente cliente = cbCliente.getValue();
            ItemVeiculo veiculo = cbVeiculo.getValue();

            if (cliente == null || veiculo == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING, "Selecione um cliente e um veículo.");
                alerta.setHeaderText(null);
                alerta.showAndWait();
                return;
            }

            try {
                LocalDate dataRetirada = LocalDate.parse(txtRetirada.getText(), FORMATO_DATA);
                LocalDate dataDevolucao = LocalDate.parse(txtDevolucao.getText(), FORMATO_DATA);
                long dias = ChronoUnit.DAYS.between(dataRetirada, dataDevolucao);

                if (dias <= 0) {
                    Alert alerta = new Alert(Alert.AlertType.WARNING, "A data de devolução deve ser posterior à data de retirada.");
                    alerta.setHeaderText(null);
                    alerta.showAndWait();
                    return;
                }

                double diaria = parseValorDiaria(txtDiaria.getText());
                double valorTotal = dias * diaria;

                boolean salvou = dao.registrarLocacao(
                        cliente.getId(),
                        veiculo.getId(),
                        dataRetirada,
                        dataDevolucao,
                        valorTotal
                );

                if (salvou) {
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Locação registrada com sucesso!");
                    alerta.setHeaderText(null);
                    alerta.showAndWait();

                    // Recarrega veículos para refletir o status 'Alugado' e limpa os campos
                    cbVeiculo.getItems().setAll(dao.listarVeiculos());
                    cbVeiculo.setValue(null);
                    txtMarca.clear();
                    txtModelo.clear();
                    txtPlaca.clear();
                    txtDiaria.clear();
                    lblDisponibilidade.setText("Disponibilidade: -");
                    txtRetirada.clear();
                    txtDevolucao.clear();
                    atualizarCalculo.run();
                } else {
                    Alert alerta = new Alert(Alert.AlertType.ERROR, "Erro ao registrar locação. Confira o console.");
                    alerta.setHeaderText(null);
                    alerta.showAndWait();
                }

            } catch (DateTimeParseException ex) {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Confira as datas de retirada e devolução (DD/MM/AAAA).");
                alerta.setHeaderText(null);
                alerta.showAndWait();
            }
        });

        colunaVeiculo.getChildren().addAll(
                lblEtapa2,
                new Separator(),
                criarCampoForm("Veículo:", cbVeiculo),
                criarCampoForm("Marca:", txtMarca),
                criarCampoForm("Modelo:", txtModelo),
                criarCampoForm("Placa:", txtPlaca),
                criarCampoForm("Valor da diária:", txtDiaria),
                lblDisponibilidade
        );

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

    private double parseValorDiaria(String texto) {
        if (texto == null || texto.isBlank()) {
            return 0.0;
        }
        String digitos = texto.replaceAll("\\D", "");
        if (digitos.isEmpty()) {
            return 0.0;
        }
        return Double.parseDouble(digitos) / 100.0;
    }
}