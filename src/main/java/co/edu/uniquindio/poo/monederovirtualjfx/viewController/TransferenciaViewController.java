package co.edu.uniquindio.poo.monederovirtualjfx.viewController;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import co.edu.uniquindio.poo.monederovirtualjfx.app.Aplicacion;
import co.edu.uniquindio.poo.monederovirtualjfx.controller.DepositoController;
import co.edu.uniquindio.poo.monederovirtualjfx.controller.TransferenciaController;
import co.edu.uniquindio.poo.monederovirtualjfx.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TransferenciaViewController {
    private static final String ESTILO_MENSAJE_ERROR = "-fx-text-fill: #b51515; -fx-font-size: 12px;";
    private static final String ESTILO_MENSAJE_EXITO = "-fx-text-fill: #16A34A; -fx-font-size: 12px;";
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnTransferir;

    @FXML
    private Button btnVolver;

    @FXML
    private CheckBox chkProgramada;

    @FXML
    private ComboBox<Frecuencia> cmbFrecuencia;

    @FXML
    private ComboBox<String> cmbMonederoOrigen;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private Label lblMensaje;

    @FXML
    private TextField txtCodigoDestino;

    @FXML
    private TextField txtMonto;
    private Aplicacion aplicacion;
    private TransferenciaController transferenciaController;

    @FXML
    void transferir(ActionEvent event) {
        Usuario usuario = Aplicacion.getUsuarioActual();
        if (usuario == null) {
            mostrarMensaje("No hay una sesión activa.", true);
            return;
        }
        String codigoOrigen= cmbMonederoOrigen.getValue();
        String ingresoMonto= txtMonto.getText().trim();
        String codigoDestino= txtCodigoDestino.getText().trim();
        boolean programada = chkProgramada.isSelected();
        if (codigoOrigen == null || codigoDestino.isEmpty() || ingresoMonto.isEmpty()) {
            mostrarMensaje("Rellene todos los campos para hacer la transferencia.", true);
            return;
        }
        // Si no es programada, la transacción se hace hoy
        LocalDate fecha = programada ? dpFecha.getValue() : LocalDate.now();
        Frecuencia frecuencia = programada ? cmbFrecuencia.getValue() : null;
        if (programada && fecha == null) { //si es programada y la frecuencia es null se programa pero solo se ejecuta una vez por eso no se valida
            mostrarMensaje("Seleccione la fecha de la transferencia programada.", true);
            return;
        }
        if (programada && fecha.isBefore(LocalDate.now())) {
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
           String resultado=transferenciaController.transferir(usuario, codigoOrigen, monto, codigoDestino, fecha, programada, frecuencia);
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
        this.transferenciaController =
                new TransferenciaController(aplicacion.monederoVirtual);
        cargarMonederos();
        cargarFrecuencias();
    }
    private void mostrarMensaje(String mensaje, boolean error) {
        lblMensaje.setText(mensaje);
        lblMensaje.setStyle(error ? ESTILO_MENSAJE_ERROR : ESTILO_MENSAJE_EXITO);
    }
    private void cargarMonederos() {
        cmbMonederoOrigen.getItems().clear();
        Usuario usuario = Aplicacion.getUsuarioActual();
        if (usuario == null) return;
        for (Monedero monedero : usuario.getListaMonederos()) {
            cmbMonederoOrigen.getItems().add(monedero.getCodigo());
        }
    }
    private void cargarFrecuencias() {
        cmbFrecuencia.getItems().setAll(Frecuencia.values());
        }

    @FXML
    void initialize() {
        assert btnTransferir != null : "fx:id=\"btnTransferir\" was not injected: check your FXML file 'Transferencia.fxml'.";
        assert btnVolver != null : "fx:id=\"btnVolver\" was not injected: check your FXML file 'Transferencia.fxml'.";
        assert chkProgramada != null : "fx:id=\"chkProgramada\" was not injected: check your FXML file 'Transferencia.fxml'.";
        assert cmbFrecuencia != null : "fx:id=\"cmbFrecuencia\" was not injected: check your FXML file 'Transferencia.fxml'.";
        assert cmbMonederoOrigen != null : "fx:id=\"cmbMonederoOrigen\" was not injected: check your FXML file 'Transferencia.fxml'.";
        assert dpFecha != null : "fx:id=\"dpFecha\" was not injected: check your FXML file 'Transferencia.fxml'.";
        assert lblMensaje != null : "fx:id=\"lblMensaje\" was not injected: check your FXML file 'Transferencia.fxml'.";
        assert txtCodigoDestino != null : "fx:id=\"txtCodigoDestino\" was not injected: check your FXML file 'Transferencia.fxml'.";
        assert txtMonto != null : "fx:id=\"txtMonto\" was not injected: check your FXML file 'Transferencia.fxml'.";

    }

}
