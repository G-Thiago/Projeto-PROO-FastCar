package view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

public class TelaInicio {
    public static VBox criar() {
        VBox painel = new VBox(15);
        VBox card = new VBox(15);

        painel.setPadding(new Insets(25));
        card.setPadding(new Insets(25));

        Label lblTitulo = new Label("Início - Visão Geral do Sistema");

        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");
        card.setStyle("-fx-background-color: white; -fx-background-radius: 8px; -fx-border-color: #e2e8f0; -fx-border-radius: 8px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 6, 0, 0, 2);");

        Label lblNomeProjeto = new Label("FastCar 🚗");
        lblNomeProjeto.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        Label lblDescricao = createLblDescricao();

        // Tecnologia usada
        VBox boxTecnologias = new VBox(5);
        Label lblTecnologiasTitulo = new Label("Tecnologias utilizadas:");
        lblTecnologiasTitulo.setStyle("-fx-font-weight: bold; -fx-text-fill: #1e293b; -fx-font-size: 14px;");
        Label lblTecnologiasLista = new Label("• Java\n• JavaFX\n• MySQL\n• Maven");
        lblTecnologiasLista.setStyle("-fx-text-fill: #475569; -fx-font-size: 13px;");
        boxTecnologias.getChildren().addAll(lblTecnologiasTitulo, lblTecnologiasLista);

        //  Funcionalidade
        VBox boxFuncionalidades = new VBox(5);
        Label lblFuncionalidadesTitulo = new Label("Funcionalidades do sistema:");
        lblFuncionalidadesTitulo.setStyle("-fx-font-weight: bold; -fx-text-fill: #1e293b; -fx-font-size: 14px;");
        Label lblFuncionalidadesLista = new Label("• Cadastro e gerenciamento de veículos\n• Cadastro e gerenciamento de clientes\n• Registro de locações\n• Controle de devoluções");
        lblFuncionalidadesLista.setStyle("-fx-text-fill: #475569; -fx-font-size: 13px;");
        boxFuncionalidades.getChildren().addAll(lblFuncionalidadesTitulo, lblFuncionalidadesLista);

        // Status
        VBox boxStatus = new VBox(3);
        Label lblStatusTitulo = new Label("Status:");
        lblStatusTitulo.setStyle("-fx-font-weight: bold; -fx-text-fill: #1e293b; -fx-font-size: 14px;");
        Label lblStatusValor = new Label("Em desenvolvimento.");
        lblStatusValor.setStyle("-fx-text-fill: #0284c7; -fx-font-size: 13px; -fx-font-weight: bold;");
        boxStatus.getChildren().addAll(lblStatusTitulo, lblStatusValor);

        // Adiciona todos os elementos dentro do cartão
        card.getChildren().addAll(
                lblNomeProjeto,
                lblDescricao,
                new Separator(),
                boxTecnologias,
                new Separator(),
                boxFuncionalidades,
                new Separator(),
                boxStatus
        );

        painel.getChildren().addAll(lblTitulo, card);
        return painel;
    }

    private static Label createLblDescricao() {
        Label lblDescricao = new Label(
                "O FastCar é um sistema de gerenciamento de locação de veículos desenvolvido como " +
                        "projeto acadêmico para a disciplina de Programação Orientada a Objetos.\n" +
                        "Nosso objetivo é desenvolver uma aplicação desktop para auxiliar no gerenciamento de uma " +
                        "locadora de veículos, facilitando o controle de carros, clientes e aluguéis."
        );
        lblDescricao.setWrapText(true);
        lblDescricao.setStyle("-fx-font-size: 14px; -fx-text-fill: #475569;");
        return lblDescricao;
    }
}