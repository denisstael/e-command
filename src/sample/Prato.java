package sample;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import sun.plugin.perf.PluginRollup;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    //método pra mostrar tabelas com os pratos na tela do cardapio
    public void mostraTabelaPrato(TableView tabelaPratoCardapio, TableColumn colunaNomePrato, TableColumn colunaPrecoPrato, String sql) {
        ObservableList<Prato> listaPratos = FXCollections.observableArrayList();
        try {
            Statement stmt = conexaoBanco.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                listaPratos.add(new Prato(rs.getString("nome"), rs.getInt("codprato"),
                        rs.getString("descricao"), rs.getFloat("preco")));
            }
            rs.close();

            colunaNomePrato.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colunaPrecoPrato.setCellValueFactory(new PropertyValueFactory<>("preco"));

            tabelaPratoCardapio.setItems(listaPratos);

        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao apresentar pratos!\n" + e);
        }
    }

    public ObservableList<GridPane> listaPrato(){
        ObservableList<GridPane> lista = FXCollections.observableArrayList();
        try {
            Statement stmt = conexaoBanco.connection.createStatement();
            ResultSet rs = stmt.executeQuery("select nome,codprato,descricao,preco from prato where cardapio = TRUE" +
                    " and tipo = 'Comida' order by nome;");

            while(rs.next()){
                GridPane gridPane = new GridPane();
                gridPane.setMinSize(50,50);
                gridPane.setPadding(new Insets(10,10,10,10));
                gridPane.setHgap(10);
                gridPane.setVgap(10);
                gridPane.setAlignment(Pos.CENTER);
                gridPane.add(new Text(rs.getString("nome")),0,0);
                gridPane.add(new Text(rs.getString("descricao")),0,1);
                gridPane.add(new Text(Float.toString(rs.getFloat("preco"))),2,0);
                lista.addAll(gridPane);
            }
            rs.close();

        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao apresentar pratos!\n" + e);
        }

        return lista;
    }

}
