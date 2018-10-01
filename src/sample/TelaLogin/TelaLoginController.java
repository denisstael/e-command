package sample.TelaLogin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.ConexaoBanco;
import sample.Main;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class TelaLoginController implements Initializable {

    ConexaoBanco conexaoBanco = new ConexaoBanco(); //Objeto de conexão ao banco

    //Atributos da tela
    @FXML
    private Label label;
    @FXML
    private PasswordField txtSenha;
    @FXML
    private TextField txtUsuario;
    @FXML
    private Label labelErroLogin;
    private static String tipo; //Tipo de usuário atribuido pela tela inicial

    //Getter
    public static String getTipo() {
        return tipo;
    }
    //Setter
    public static void setTipo(String tipo) {
        TelaLoginController.tipo = tipo;
    }

    //Método chamado ao clicar no botão para logar
    public void acaoLogar() throws IOException {

        //Verifica se todos os campos foram preenchidos
        if(!txtSenha.getText().isEmpty() && !txtUsuario.getText().isEmpty()){

            if(login()){
                if (tipo.equals("Gerente"))
                    Main.trocaTela("TelaGerente/telaGerente.fxml");
                else if (tipo.equals("Cozinheiro"))
                    Main.trocaTela("TelaCozinheiro/telaCozinheiro.fxml");
                else if (tipo.equals("Garçom"))
                    Main.trocaTela("TelaGarçom/telaGarçom.fxml");
            } else usuarioIncorreto();

            //Mensagem para preencher todos os campos
        } else JOptionPane.showMessageDialog(null,"Preencha todos os campos!");
    }

    private boolean login(){
        boolean existe = false;
        try {
            //Cria declaração para consulta no banco
            PreparedStatement ps = conexaoBanco.connection.prepareStatement("select nome,senha from usuario where tipo = ? and nome = ? and senha = ?;");
            ps.setString(1,tipo);
            ps.setString(2,txtUsuario.getText());
            ps.setString(3,txtSenha.getText());
            ResultSet rs = ps.executeQuery();
            //Verifica se há resultados encontrados
            if(rs.next()){
                existe = true;
            } else existe = false; //insere falso no booleano
            rs.close(); //Fecha ResultSet

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro:\n"+e); //Mensagem de erro
        }
        return existe;
    }

    //Método de usuário incorreto
    private void usuarioIncorreto(){
        labelErroLogin.setText("Usuário ou senha inválidos, tente novamente");//'Seta' texto no label
        txtUsuario.clear(); //limpa campo de texto
        txtSenha.clear(); //limpa campo de texto
    }

    public void acaoRecuperarSenha() {
        //método não implementado ainda
    }

    //método de voltar à tela anterior
    public void acaoVoltar() throws IOException {
        Main.trocaTela("TelaInicial/telaInicial.fxml"); //troca a tela
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        label.setText(tipo); //insere o tipo de usuário no label
    }
}
