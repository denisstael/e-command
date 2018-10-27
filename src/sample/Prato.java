package sample;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Prato {

    private ConexaoBanco conexaoBanco = new ConexaoBanco(); //objeto para conexão com o banco

    //Atributos da classe que serão utilizados na tabela de pratos
    private SimpleStringProperty nome;
    private SimpleStringProperty descricao;
    private SimpleFloatProperty preco;
    private SimpleIntegerProperty codprato;

    //construtor com 4 parâmetros
    public Prato(String nome, int codprato, String descricao, float preco) {
        this.nome = new SimpleStringProperty(nome);
        this.descricao = new SimpleStringProperty(descricao);
        this.preco = new SimpleFloatProperty(preco);
        this.codprato = new SimpleIntegerProperty(codprato);
    }

    public Prato(){
    }

    //Getters e Setters
    public String getNome() {
        return nome.get();
    }

    public SimpleStringProperty nomeProperty() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getDescricao() {
        return descricao.get();
    }

    public SimpleStringProperty descricaoProperty() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
    }

    public String getPreco() {
        return String.valueOf(preco.get());
    }

    public SimpleFloatProperty precoProperty() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco.set(preco);
    }

    public String getCodprato() {
        return String.valueOf(codprato.get());
    }

    public SimpleIntegerProperty codpratoProperty() {
        return codprato;
    }

    public void setCodprato(int codprato) {
        this.codprato.set(codprato);
    }


    /*método que mostra a tabela com todos os pratos em estoque e
    recebe como parâmetros a tabela que será apresentada, suas colunas e a string sql*/
    public void mostraTabela(TableView tabelaPratos, TableColumn colunaPrato, TableColumn colunaDescricao, TableColumn colunaCod, TableColumn colunaPreco, String sql) {
        ObservableList<Prato> listaPratos = FXCollections.observableArrayList();
        try {
            listaPratos.clear();//limpa a lista
            Statement stmt = conexaoBanco.connection.createStatement();//cria declaração sql
            ResultSet rs = stmt.executeQuery(sql); //executa a declaração e armazena o resultado

            //enquanto há resultados na consulta, registra os pratos na lista
            while (rs.next()) {
                listaPratos.add(new Prato(rs.getString("nome"), rs.getInt("codprato"),
                        rs.getString("descricao"), rs.getFloat("preco")));
            }
            rs.close(); //fecha o resultset

            //atribui às colunas da tabela os valores
            colunaPrato.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colunaCod.setCellValueFactory(new PropertyValueFactory<>("codprato"));
            colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
            colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

            //insere os itens na tabela
            tabelaPratos.setItems(listaPratos);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao apresentar pratos!\n" + e);
        }
    }

    public ObservableList<GridPane> listaPrato(String sql) {
        ObservableList<GridPane> listaPratos = FXCollections.observableArrayList();
        try {
            Statement stmt = conexaoBanco.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                GridPane gridPane = new GridPane();
                gridPane.getColumnConstraints().add(new ColumnConstraints(280));
                gridPane.getColumnConstraints().add(new ColumnConstraints(70));
                gridPane.getStylesheets().add(String.valueOf(getClass().getResource("css/TableStyle.css")));
                gridPane.getStyleClass().add("gridPane");
                Label lblNome = new Label(rs.getString("nome"));
                lblNome.getStyleClass().add("nome");
                Label lblPreco = new Label("R$ "+Float.toString(rs.getFloat("preco")));
                lblPreco.getStyleClass().add("preco");
                Label lblDescricao = new Label(rs.getString("descricao"));
                lblDescricao.setPrefWidth(380);
                lblDescricao.setWrapText(true);
                lblDescricao.getStyleClass().add("descricao");
                gridPane.setMaxWidth(380);
                gridPane.setPadding(new Insets(2,2,2,2));
                gridPane.setVgap(5);
                gridPane.setHgap(5);
                GridPane.setHalignment(lblNome, HPos.LEFT);
                gridPane.add(lblNome,0,0,1,1);
                GridPane.setHalignment(lblPreco,HPos.CENTER);
                gridPane.add(lblPreco,1,0);
                gridPane.add(lblDescricao,0,1,2,2);
                listaPratos.add(gridPane);
            }
            rs.close();

        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao apresentar pratos!\n" + e);
        }
        return listaPratos;
    }
}
