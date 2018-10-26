package sample.TelaVisualizarCardapio;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import sample.*;
import sample.TelaPedido.TelaPedidoController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TelaVisualizarCardapioController implements Initializable {

    private static Pedido pedido = new Pedido();
    private Prato tabelas = new Prato();
    private String sqlPrato = "select nome,codprato,descricao,preco from prato where cardapio = TRUE and tipo = 'Comida' order by codprato;"; //String sql
    private String sqlBebida = "select nome,codprato,descricao,preco from prato where cardapio = TRUE and tipo = 'Bebida' order by codprato;"; //String sql
    @FXML
    private TableView<Prato> tabelaPedido;
    @FXML
    private ListView<GridPane> listaPratos, listaBebidas;

    public static Pedido getPedido() {
        return pedido;
    }

    public static void setPedido(Pedido pedido) {
        TelaVisualizarCardapioController.pedido = pedido;
    }

    public void acaoVoltar() throws IOException {
        tabelaPedido.getItems().clear();
        Main.trocaTela("TelaMesa/telaMesa.fxml");
    }

    public void acaoComanda() throws IOException {
        Main.trocaTela("TelaComanda/telaComanda.fxml");
    }

    @FXML
    private void acaoSelecaoPrato(){
        if(listaBebidas.getSelectionModel().getSelectedItem() != null)
            listaBebidas.getSelectionModel().clearSelection();
    }

    @FXML
    private void acaoSelecaoBebida(){
        if(listaPratos.getSelectionModel().getSelectedItem() != null)
            listaPratos.getSelectionModel().clearSelection();
    }

    public void acaoAddPedido() {
        /*if(listaPratos.getSelectionModel().getSelectedItem() != null){
            pedido.getListaPedido().add(listaPratos.getSelectionModel().getSelectedItem());
            tabelaPedido.setItems(pedido.getListaPedido());
        } else if(listaBebidas.getSelectionModel().getSelectedItem() != null){
            pedido.getListaPedido().add(listaBebidas.getSelectionModel().getSelectedItem());
            tabelaPedido.setItems(pedido.getListaPedido());
        }*/
    }

    public void removerPratoPedido() {
        if(tabelaPedido.getSelectionModel().getSelectedItem() != null){
            pedido.getListaPedido().remove(tabelaPedido.getSelectionModel().getSelectedItem());
        }
    }

    public void acaoProximo() throws IOException {
        TelaPedidoController.setPedido(pedido);
        Main.trocaTela("TelaPedido/telaPedido.fxml");
    }

    public void initialize(URL url, ResourceBundle rb){
        listaPratos.setItems(tabelas.listaPrato(sqlPrato));
        listaBebidas.setItems(tabelas.listaPrato(sqlBebida));
    }
}