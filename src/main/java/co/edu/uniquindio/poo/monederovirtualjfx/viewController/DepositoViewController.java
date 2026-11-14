package co.edu.uniquindio.poo.monederovirtualjfx.viewController;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import co.edu.uniquindio.poo.monederovirtualjfx.app.Aplicacion;
import co.edu.uniquindio.poo.monederovirtualjfx.controller.DepositoController;
import co.edu.uniquindio.poo.monederovirtualjfx.controller.InicioSesionRegistroController;
import co.edu.uniquindio.poo.monederovirtualjfx.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepositoViewController {
    private static final String ESTILO_MENSAJE_ERROR = "-fx-text-fill: #b51515; -fx-font-size: 12px;";
    private static final String ESTILO_MENSAJE_EXITO = "-fx-text-fill: #16A34A; -fx-font-size: 12px;";
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnDepositar;

    @FXML
    private Button btnVolver;

    @FXML
    private CheckBox chkProgramada;

    @FXML
    private ComboBox<Frecuencia> cmbFrecuencia;

    @FXML
    private ComboBox<String> cmbMonedero;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private Label lblMensaje;

    @FXML
    private TextField txtMonto;
    private Aplicacion aplicacion;
    private DepositoController depositoController;

    @FXML
    void depositar(ActionEvent event) {
        Usuario usuario= aplicacion.getUsuarioActual();
        String codigoMonedero = cmbMonedero.getSelectionModel().getSelectedItem();
        String textoMonto = txtMonto.getText();
        boolean programada = chkProgramada.isSelected();
        if (usuario == null) {
            mostrarMensaje("No hay una sesión activa.", true);
            return;
        }
        if (textoMonto == null || textoMonto.isBlank() || codigoMonedero==null||codigoMonedero.isBlank()) {
            mostrarMensaje("Por favor ingrese todos los campos para depositar", true);
            return;
        }
        // Si no es programada, la transacción se hace hoy
        LocalDate fecha = programada ? dpFecha.getValue() : LocalDate.now();
        Frecuencia frecuencia = programada ? cmbFrecuencia.getValue() : null;
        if (programada && fecha == null) { //si es programada y la frecuencia es null se programa pero solo se ejecuta una vez por eso no se valida
            mostrarMensaje("Seleccione la fecha de el deposito programado.", true);
            return;
        }
        if (programada && fecha.isBefore(LocalDate.now())) {
            mostrarMensaje("La fecha debe ser hoy o posterior.", true);
            return;
        }
        double monto;
        try {
            monto = Double.parseDouble(textoMonto.trim());
        } catch (NumberFormatException e) {
            mostrarMensaje("El monto debe ser un número válido", true);
            return;
        }
        try{
            String resultado = depositoController.realizarDeposito(usuario, codigoMonedero, monto, fecha, programada, frecuencia);
            mostrarMensaje(resultado, false);
        } catch (MontoInvalidoException | SaldoInsuficienteException e) {
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
        this.depositoController =
                new DepositoController(aplicacion.monederoVirtual);
        cargarMonederos();
        cargarFrecuencias();
    }
    private void mostrarMensaje(String mensaje, boolean error) {
        lblMensaje.setText(mensaje);
        lblMensaje.setStyle(error ? ESTILO_MENSAJE_ERROR : ESTILO_MENSAJE_EXITO);
    }
    private void cargarMonederos() {
        cmbMonedero.getItems().clear();
        Usuario usuario = Aplicacion.getUsuarioActual();
        if (usuario == null) return;
        for (Monedero monedero : usuario.getListaMonederos()) {
            cmbMonedero.getItems().add(monedero.getCodigo());
        }
    }
    private void cargarFrecuencias() {
        cmbFrecuencia.getItems().setAll(Frecuencia.values());
    }

    @FXML
    void initialize() {
        assert btnDepositar != null : "fx:id=\"btnDepositar\" was not injected: check your FXML file 'Deposito.fxml'.";
        assert btnVolver != null : "fx:id=\"btnVolver\" was not injected: check your FXML file 'Deposito.fxml'.";
        assert chkProgramada != null : "fx:id=\"chkProgramada\" was not injected: check your FXML file 'Deposito.fxml'.";
        assert cmbFrecuencia != null : "fx:id=\"cmbFrecuencia\" was not injected: check your FXML file 'Deposito.fxml'.";
        assert cmbMonedero != null : "fx:id=\"cmbMonedero\" was not injected: check your FXML file 'Deposito.fxml'.";
        assert dpFecha != null : "fx:id=\"dpFecha\" was not injected: check your FXML file 'Deposito.fxml'.";
        assert lblMensaje != null : "fx:id=\"lblMensaje\" was not injected: check your FXML file 'Deposito.fxml'.";
        assert txtMonto != null : "fx:id=\"txtMonto\" was not injected: check your FXML file 'Deposito.fxml'.";

    }

}
