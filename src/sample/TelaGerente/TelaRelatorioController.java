package sample.TelaGerente;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.ConexaoBanco;
import sample.Main;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ConcurrentModificationException;
import java.util.ResourceBundle;

public class TelaRelatorioController {
    @FXML
    DatePicker txtDataInicio, txtDataFim;
    public void acaoVoltar() throws IOException {
        Main.trocaTela("TelaGerente/telaGerente.fxml");
    }

    /*public void acaoGerarRelatorio() throws IOException {
        Image icone = new Image(getClass().getResourceAsStream("../img/icone.png"));
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../TelaGerente/telaRelatorioGerado.fxml"));
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(Main.stage);
        stage.resizableProperty().setValue(false);
        stage.sizeToScene();
        stage.getIcons().add(icone);
        stage.setTitle("Relatório");
        stage.setScene(scene);
        stage.show();

        LocalDate data = txtDataInicio.getValue();
        LocalDate data2 = txtDataFim.getValue();

        consultaBanco(data, data2);
    }*/

    private void consultaBanco(LocalDate data, LocalDate data2){
       

    }

    public void initialize(URL location, ResourceBundle resources){

    }

    public void acaoGerarRelatorioEstoque(ActionEvent actionEvent) {
    }

    public void acaoGerarRelatorioPedidos(ActionEvent actionEvent) {
    }
}
