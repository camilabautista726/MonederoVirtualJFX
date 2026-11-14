package co.edu.uniquindio.poo.monederovirtualjfx.viewController;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import co.edu.uniquindio.poo.monederovirtualjfx.app.Aplicacion;
import co.edu.uniquindio.poo.monederovirtualjfx.controller.DepositoController;
import co.edu.uniquindio.poo.monederovirtualjfx.controller.ProgramarTransaccionController;
import co.edu.uniquindio.poo.monederovirtualjfx.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ProgramarTransaccionViewController {
    private static final String ESTILO_MENSAJE_ERROR = "-fx-text-fill: #b51515; -fx-font-size: 12px;";
    private static final String ESTILO_MENSAJE_EXITO = "-fx-text-fill: #16A34A; -fx-font-size: 12px;";
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnProgramar;

    @FXML
    private Button btnVolver;

    @FXML
    private ComboBox<Frecuencia> cmbFrecuencia;

    @FXML
    private ComboBox<String> cmbMonedero;

    @FXML
    private ComboBox<String> cmbTipo;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private Label lblMensaje;

    @FXML
    private TextField txtCodigoDestino;

    @FXML
    private TextField txtMonto;
    private Aplicacion aplicacion;
    private ProgramarTransaccionController programarTransaccionController;

    @FXML
    void programar(ActionEvent event) {
        Usuario usuario = Aplicacion.getUsuarioActual();
        if (usuario == null) {
            mostrarMensaje("No hay una sesión activa.", true);
            return;
        }
        String tipo = cmbTipo.getValue();
        String codigoMonedero = cmbMonedero.getValue();
        String codigoDestino = txtCodigoDestino.getText().trim();
        String ingresoMonto = txtMonto.getText().trim();
        LocalDate fecha = dpFecha.getValue();
        Frecuencia frecuencia = cmbFrecuencia.getValue();

        if (tipo== null || codigoMonedero == null  || ingresoMonto.isEmpty()|| fecha == null) {
            mostrarMensaje("Rellene todos los campos para hacer la transaccion programada.", true);
            return;
        }
        if ("Transferencia".equals(tipo) && codigoDestino.isEmpty()) {
            mostrarMensaje("Ingrese el código del monedero destino.", true);
            return;
        }
        if(fecha.isBefore(LocalDate.now())){
            mostrarMensaje("La fecha debe ser hoy o posterior.", true);
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
            String resultado=programarTransaccionController.programar(usuario, tipo, codigoMonedero,  codigoDestino, monto, fecha, frecuencia);
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
        this.programarTransaccionController =
                new ProgramarTransaccionController(aplicacion.monederoVirtual);
        cargarTipos();
        cargarMonederos();
        cargarFrecuencias();
    }
    private void cargarTipos() {
        cmbTipo.getItems().setAll("Deposito", "Transferencia");
        txtCodigoDestino.disableProperty()
                .bind(cmbTipo.valueProperty().isNotEqualTo("Transferencia"));
    }

    private void cargarMonederos() {
        cmbMonedero.getItems().clear();
        Usuario usuario = Aplicacion.getUsuarioActual();
        if (usuario == null) {
            return;
        }
        for (Monedero monedero : usuario.getListaMonederos()) {
            cmbMonedero.getItems().add(monedero.getCodigo());
        }
    }

    private void cargarFrecuencias() {
        cmbFrecuencia.getItems().setAll(Frecuencia.values());   // si no elige, queda única
    }
    private void mostrarMensaje(String mensaje, boolean error) {
        lblMensaje.setText(mensaje);
        lblMensaje.setStyle(error ? ESTILO_MENSAJE_ERROR : ESTILO_MENSAJE_EXITO);
    }

    @FXML
    void initialize() {
        assert btnProgramar != null : "fx:id=\"btnProgramar\" was not injected: check your FXML file 'ProgramarTransaccion.fxml'.";
        assert btnVolver != null : "fx:id=\"btnVolver\" was not injected: check your FXML file 'ProgramarTransaccion.fxml'.";
        assert cmbFrecuencia != null : "fx:id=\"cmbFrecuencia\" was not injected: check your FXML file 'ProgramarTransaccion.fxml'.";
        assert cmbMonedero != null : "fx:id=\"cmbMonedero\" was not injected: check your FXML file 'ProgramarTransaccion.fxml'.";
        assert cmbTipo != null : "fx:id=\"cmbTipo\" was not injected: check your FXML file 'ProgramarTransaccion.fxml'.";
        assert dpFecha != null : "fx:id=\"dpFecha\" was not injected: check your FXML file 'ProgramarTransaccion.fxml'.";
        assert lblMensaje != null : "fx:id=\"lblMensaje\" was not injected: check your FXML file 'ProgramarTransaccion.fxml'.";
        assert txtCodigoDestino != null : "fx:id=\"txtCodigoDestino\" was not injected: check your FXML file 'ProgramarTransaccion.fxml'.";
        assert txtMonto != null : "fx:id=\"txtMonto\" was not injected: check your FXML file 'ProgramarTransaccion.fxml'.";

    }

}

