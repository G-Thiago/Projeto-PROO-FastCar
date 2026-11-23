package model;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.function.Consumer;

public class ModelVeiculoTabela {
    private String id, foto, marca, modelo, ano, placa, valorDiaria, disponibilidade;
    private HBox acoes;

    public ModelVeiculoTabela(String id, String foto, String marca, String modelo, String ano, String placa, String valorDiaria, String disponibilidade, String acoesTexto) {
        this.id = id;
        this.foto = foto;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.placa = placa;
        this.valorDiaria = valorDiaria;
        this.disponibilidade = disponibilidade;

        Button btnStatus = new Button("Status");
        btnStatus.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 11px; -fx-cursor: hand;");

        Button btnExcluir = new Button("Excluir");
        btnExcluir.setStyle("-fx-background-color: #dc2626; -fx-text-fill: white; -fx-font-size: 11px; -fx-cursor: hand;");

        this.acoes = new HBox(6, btnStatus, btnExcluir);
        this.acoes.setAlignment(Pos.CENTER_LEFT);
    }

    public void configurarAcoes(
            Consumer<ModelVeiculoTabela> aoAlterarStatus,
            Consumer<ModelVeiculoTabela> aoExcluir
    ) {
        Button btnStatus = (Button) this.acoes.getChildren().get(0);
        Button btnExcluir = (Button) this.acoes.getChildren().get(1);

        btnStatus.setOnAction(e -> aoAlterarStatus.accept(this));
        btnExcluir.setOnAction(e -> aoExcluir.accept(this));
    }

    public String getId() { return id; }
    public String getFoto() { return foto; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public String getAno() { return ano; }
    public String getPlaca() { return placa; }
    public String getValorDiaria() { return valorDiaria; }
    public String getDisponibilidade() { return disponibilidade; }
    public HBox getAcoes() { return acoes; }
}