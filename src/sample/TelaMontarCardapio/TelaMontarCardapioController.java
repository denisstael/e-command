package sample.TelaMontarCardapio;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sample.*;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class TelaMontarCardapioController implements Initializable {

    private ConexaoBanco conexaoBanco = new ConexaoBanco();
    private TabelaLista tabelaPrato = new TabelaLista();
    private String sql = "select nome,codprato,descricao,preco from prato where cardapio = FALSE order by codprato;";//String sql
    private String sql_2 = "select nome,codprato,descricao,preco from prato where cardapio = TRUE and tipo = 'Comida' order by codprato;";
    private String sql_3 = "select nome,codprato,descricao,preco from prato where cardapio = TRUE and tipo = 'Bebida' order by codprato;";
    @FXML
    private TableView<Prato> tabelaPratos,tabelaPratos2,tabelaBebida;
    @FXML
    private TableColumn colunaPrato, colunaDescPrato, colunaCodPrato, colunaPreco, colunaNomePrato2, colunaDescPrato2,
            colunaCodPrato2, colunaPreco2, colunaNomeBebida, colunaCodBebida, colunaDescBebida, colunaPrecoBebida;
    @FXML
    private TextField txtNomePrato,txtCodPrato;

    @FXML //volta para a tela anterior
    public void acaoVoltar() throws IOException {
        Main.trocaTela("TelaGerente/telaGerente.fxml");
    }
    @FXML //pesquisa por nome e mostra na tabela apenas pratos correspondentes à pesquisa
    private void acaoPesquisarPrato(){
        String sqlPesquisa = "select * from prato where nome ilike '%"+txtNomePrato.getText()+"%';";
        tabelaPrato.mostraTabelaPratos(tabelaPratos,colunaPrato,colunaDescPrato,colunaCodPrato,colunaPreco,sqlPesquisa);
    }
    @FXML //preenche o campo de texto com o código do prato selecionado ao clicar numa linha da tabela
    private void clicarTabelaPrato(){
        txtCodPrato.setText(tabelaPratos.getSelectionModel().getSelectedItem().getCodprato());
    }

    @FXML
    private void acaoSelecaoPrato(){
        if(tabelaBebida.getSelectionModel().getSelectedItem() != null)
            tabelaBebida.getSelectionModel().clearSelection();
    }

    @FXML
    private void acaoSelecaoBebida(){
        if(tabelaPratos2.getSelectionModel().getSelectedItem() != null)
            tabelaPratos2.getSelectionModel().clearSelection();
    }

    @FXML
    private void acaoRemoverDoCardapio(){
        int cod = 0;
        boolean selecao = false;
        if(tabelaPratos2.getSelectionModel().getSelectedItem() != null){
            cod = Integer.parseInt(tabelaPratos2.getSelectionModel().getSelectedItem().getCodprato());
            selecao = true;
        } else if(tabelaBebida.getSelectionModel().getSelectedItem() != null){
            cod = Integer.parseInt(tabelaBebida.getSelectionModel().getSelectedItem().getCodprato());
            selecao = true;
        }
        if(selecao){
            try {
                PreparedStatement ps = conexaoBanco.connection.prepareStatement
                        ("update prato set cardapio = FALSE where codprato = ?;");
                ps.setInt(1, cod);
                ps.executeUpdate();

                mostrarTabelas();

                JOptionPane.showMessageDialog(null,"Removido do Cardápio!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"Erro ao remover do Cardápio!");
            }
        }
    }

    public void acaoAdicionarPrato() {
       if(!txtCodPrato.getText().isEmpty()){
            try {
                //Declaração SQL pra inserção no banco
                PreparedStatement ps = conexaoBanco.connection.prepareStatement
                        ("UPDATE Prato SET cardapio = TRUE WHERE codprato = ?;");

                //Atribui os parâmetros e os valores à declaração SQL criada anteriormente
                ps.setInt(1, Integer.parseInt(txtCodPrato.getText()));

                ps.executeUpdate();//Executa a declaração SQL

                limpar(); //limpa os campos de texto

                //chama as informações da tabela
                mostrarTabelas();

                //Mensagem de Sucesso
                JOptionPane.showMessageDialog(null, "Prato Cadastrado!");

            } catch (Exception e) {
                //Mensagem de erro
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar prato!\nErro: " + e);
            }
        } else JOptionPane.showMessageDialog(null,"Digite o código do prato que deseja adicionar ao Cardápio!");
    }

    public void limpar(){
        txtCodPrato.clear();
    }

    private void mostrarTabelas(){
        tabelaPrato.mostraTabelaPratos(tabelaPratos,colunaPrato,colunaDescPrato,colunaCodPrato,colunaPreco,sql);
        tabelaPrato.mostraTabelaPratos(tabelaPratos2,colunaNomePrato2,colunaDescPrato2,colunaCodPrato2,colunaPreco2,sql_2);
        tabelaPrato.mostraTabelaPratos(tabelaBebida,colunaNomeBebida,colunaDescBebida,colunaCodBebida,colunaPrecoBebida,sql_3);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mostrarTabelas();
    }

}
