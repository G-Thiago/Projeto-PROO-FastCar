package model;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.function.Consumer;

public class ModelClienteTabela {
    private String id, nome, cpf, telefone, cnh;
    private HBox acoes;
    private Button btnEditar;
    private Button btnExcluir;

    public ModelClienteTabela(String id, String nome, String cpf, String telefone, String cnh, String acoesTexto) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.cnh = cnh;

        this.btnEditar = new Button("Editar");
        this.btnEditar.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 11px; -fx-cursor: hand;");

        this.btnExcluir = new Button("Excluir");
        this.btnExcluir.setStyle("-fx-background-color: #dc2626; -fx-text-fill: white; -fx-font-size: 11px; -fx-cursor: hand;");

        this.acoes = new HBox(6, this.btnEditar, this.btnExcluir);
        this.acoes.setAlignment(Pos.CENTER_LEFT);
    }

    public void configurarAcoes(
            Consumer<ModelClienteTabela> aoEditar,
            Consumer<ModelClienteTabela> aoExcluir
    ) {
        this.btnEditar.setOnAction(e -> aoEditar.accept(this));
        this.btnExcluir.setOnAction(e -> aoExcluir.accept(this));
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getTelefone() { return telefone; }
    public String getCnh() { return cnh; }
    public HBox getAcoes() { return acoes; }
}