package co.edu.uniquindio.poo.monederovirtualjfx.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.poo.monederovirtualjfx.app.Aplicacion;
import co.edu.uniquindio.poo.monederovirtualjfx.controller.BeneficiosController;
import co.edu.uniquindio.poo.monederovirtualjfx.controller.NotificacionesController;
import co.edu.uniquindio.poo.monederovirtualjfx.model.Monedero;
import co.edu.uniquindio.poo.monederovirtualjfx.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class NotificacionesViewController {
    private static final String ESTILO_MENSAJE_ERROR = "-fx-text-fill: #b51515; -fx-font-size: 12px;";
    private static final String ESTILO_MENSAJE_EXITO = "-fx-text-fill: #16A34A; -fx-font-size: 12px;";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnConsultar;

    @FXML
    private Button btnVolver;

    @FXML
    private ComboBox<String> cmbMonedero;

    @FXML
    private Label lblMensaje;

    @FXML
    private TextArea txtResultado;
    private Aplicacion aplicacion;
    private NotificacionesController notificacionesController;

    @FXML
    void consultar(ActionEvent event) {
        Usuario usuario = Aplicacion.getUsuarioActual();
        if (usuario == null) {
            mostrarMensaje("No hay una sesión activa.", true);
            return;
        }
        String codigoMonedero = cmbMonedero.getValue();

        if ( codigoMonedero == null) {
            mostrarMensaje("Ingrese el monedero para consultar sus notificaciones.", true);
            return;
        }
        String resultado = notificacionesController.consultar(usuario, codigoMonedero);
        txtResultado.setText(resultado);
        mostrarMensaje("", false);
    }

    @FXML
    void volver(ActionEvent event) {
        aplicacion.openViewMenu();
    }
    public void setApp(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
        this.notificacionesController =
                new NotificacionesController(aplicacion.monederoVirtual);
        cargarMonederos();
    }
    private void mostrarMensaje(String mensaje, boolean error) {
        lblMensaje.setText(mensaje);
        lblMensaje.setStyle(error ? ESTILO_MENSAJE_ERROR : ESTILO_MENSAJE_EXITO);
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

    @FXML
    void initialize() {
        assert btnConsultar != null : "fx:id=\"btnConsultar\" was not injected: check your FXML file 'Notificaciones.fxml'.";
        assert btnVolver != null : "fx:id=\"btnVolver\" was not injected: check your FXML file 'Notificaciones.fxml'.";
        assert cmbMonedero != null : "fx:id=\"cmbMonedero\" was not injected: check your FXML file 'Notificaciones.fxml'.";
        assert lblMensaje != null : "fx:id=\"lblMensaje\" was not injected: check your FXML file 'Notificaciones.fxml'.";
        assert txtResultado != null : "fx:id=\"txtResultado\" was not injected: check your FXML file 'Notificaciones.fxml'.";

    }

}

