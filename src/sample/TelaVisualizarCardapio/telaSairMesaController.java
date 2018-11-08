package sample.TelaVisualizarCardapio;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import sample.Main;
import sample.TelaPedido.TelaPedidoController;

import java.io.IOException;

public class telaSairMesaController {
    @FXML
    private PasswordField senhaSair;
    @FXML
    private Label labelSair;

    public void acaoConfirmar() throws IOException {
        if(!senhaSair.getText().isEmpty() && senhaSair.getText().equals("sair")){
            Main.trocaTela("TelaMesa/telaMesa.fxml");
            TelaVisualizarCardapioController.stage.close();
            TelaPedidoController.numeroComanda = null;
        }else{
            labelSair.setText("Digite a senha corretamente!");
        }

    }
}
