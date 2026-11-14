package co.edu.uniquindio.poo.monederovirtualjfx.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.poo.monederovirtualjfx.app.Aplicacion;
import co.edu.uniquindio.poo.monederovirtualjfx.controller.ReporteGastosController;
import co.edu.uniquindio.poo.monederovirtualjfx.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ReporteGastosViewController {
    private static final String ESTILO_MENSAJE_ERROR = "-fx-text-fill: #b51515; -fx-font-size: 12px;";
    private static final String ESTILO_MENSAJE_EXITO = "-fx-text-fill: #16A34A; -fx-font-size: 12px;";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnGenerar;

    @FXML
    private Button btnVolver;

    @FXML
    private Label lblMensaje;

    @FXML
    private TextArea txtResultado;
    private Aplicacion aplicacion;
    private ReporteGastosController reporteGastosController;

    @FXML
    void generar(ActionEvent event) {
        Usuario usuario = Aplicacion.getUsuarioActual();
        if (usuario == null) {
            mostrarMensaje("No hay una sesión activa.", true);
            return;
        }
        String resultado = reporteGastosController.generar(usuario);
        txtResultado.setText(resultado);
        mostrarMensaje("", false);
    }

    @FXML
    void volver(ActionEvent event) {
        aplicacion.openViewMenu();
    }
    public void setApp(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
        this.reporteGastosController =
                new ReporteGastosController(aplicacion.monederoVirtual);
    }
    private void mostrarMensaje(String mensaje, boolean error) {
        lblMensaje.setText(mensaje);
        lblMensaje.setStyle(error ? ESTILO_MENSAJE_ERROR : ESTILO_MENSAJE_EXITO);
    }


    @FXML
    void initialize() {
        assert btnGenerar != null : "fx:id=\"btnGenerar\" was not injected: check your FXML file 'ReporteGastos.fxml'.";
        assert btnVolver != null : "fx:id=\"btnVolver\" was not injected: check your FXML file 'ReporteGastos.fxml'.";
        assert lblMensaje != null : "fx:id=\"lblMensaje\" was not injected: check your FXML file 'ReporteGastos.fxml'.";
        assert txtResultado != null : "fx:id=\"txtResultado\" was not injected: check your FXML file 'ReporteGastos.fxml'.";

    }

}
