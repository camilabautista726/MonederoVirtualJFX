package co.edu.uniquindio.poo.monederovirtualjfx.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.poo.monederovirtualjfx.app.Aplicacion;
import co.edu.uniquindio.poo.monederovirtualjfx.controller.DepositoController;
import co.edu.uniquindio.poo.monederovirtualjfx.controller.RetiroController;
import co.edu.uniquindio.poo.monederovirtualjfx.model.Monedero;
import co.edu.uniquindio.poo.monederovirtualjfx.model.MontoInvalidoException;
import co.edu.uniquindio.poo.monederovirtualjfx.model.SaldoInsuficienteException;
import co.edu.uniquindio.poo.monederovirtualjfx.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RetiroViewController {
    private static final String ESTILO_MENSAJE_ERROR = "-fx-text-fill: #b51515; -fx-font-size: 12px;";
    private static final String ESTILO_MENSAJE_EXITO = "-fx-text-fill: #16A34A; -fx-font-size: 12px;";
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnRetirar;

    @FXML
    private Button btnVolver;

    @FXML
    private ComboBox<String> cmbMonedero;

    @FXML
    private Label lblMensaje;

    @FXML
    private TextField txtMonto;
    private Aplicacion aplicacion;
    private RetiroController retiroController;

    @FXML
    void retirar(ActionEvent event) {
        Usuario usuario = Aplicacion.getUsuarioActual();
        if (usuario == null) {
            mostrarMensaje("No hay una sesión activa.", true);
            return;
        }
        String codigoMonedero= cmbMonedero.getValue();
        String ingresoMonto= txtMonto.getText().trim();
        if (codigoMonedero.isEmpty() ||codigoMonedero == null|| ingresoMonto.isEmpty()) {
            mostrarMensaje("Rellene todos los campos para hacer el retiro.",true);
            return;
        }
        double monto;
        try {
            monto = Double.parseDouble(ingresoMonto);
        } catch (NumberFormatException e) {
            mostrarMensaje("El monto debe ser un número válido.", true);
            return;
        }
        try{
            String resultado=retiroController.retirar(usuario, codigoMonedero, monto);
            mostrarMensaje(resultado, false);
        }catch (MontoInvalidoException | SaldoInsuficienteException e) {
            mostrarMensaje(e.getMessage(), true);
        } catch (IllegalArgumentException e) {
            mostrarMensaje(e.getMessage(), true);
        }
    }

    @FXML
    void volver(ActionEvent event) {
        aplicacion.openViewMenu();

    }
    public void setApp(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
        this.retiroController =
                new RetiroController(aplicacion.monederoVirtual);
        cargarMonederos();
    }
    private void cargarMonederos() {
        cmbMonedero.getItems().clear();
        Usuario usuario = Aplicacion.getUsuarioActual();
        if (usuario == null) return;
        for (Monedero monedero : usuario.getListaMonederos()) {
            cmbMonedero.getItems().add(monedero.getCodigo());
        }
    }
    private void mostrarMensaje(String mensaje, boolean error) {
        lblMensaje.setText(mensaje);
        lblMensaje.setStyle(error ? ESTILO_MENSAJE_ERROR : ESTILO_MENSAJE_EXITO);
    }


    @FXML
    void initialize() {
        assert btnRetirar != null : "fx:id=\"btnRetirar\" was not injected: check your FXML file 'Retiro.fxml'.";
        assert btnVolver != null : "fx:id=\"btnVolver\" was not injected: check your FXML file 'Retiro.fxml'.";
        assert cmbMonedero != null : "fx:id=\"cmbMonedero\" was not injected: check your FXML file 'Retiro.fxml'.";
        assert lblMensaje != null : "fx:id=\"lblMensaje\" was not injected: check your FXML file 'Retiro.fxml'.";
        assert txtMonto != null : "fx:id=\"txtMonto\" was not injected: check your FXML file 'Retiro.fxml'.";

    }

}
