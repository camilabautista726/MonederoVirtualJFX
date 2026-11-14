package co.edu.uniquindio.poo.monederovirtualjfx.viewController;

import co.edu.uniquindio.poo.monederovirtualjfx.app.Aplicacion;
import co.edu.uniquindio.poo.monederovirtualjfx.model.Monedero;
import co.edu.uniquindio.poo.monederovirtualjfx.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MenuViewController {

    private static final String ESTILO_MENSAJE_ERROR = "-fx-text-fill: #EF4444; -fx-font-size: 12px;";
    private static final String ESTILO_MENSAJE_EXITO = "-fx-text-fill: #16A34A; -fx-font-size: 12px;";

    @FXML
    private Label lblBienvenido;

    @FXML
    private Label lblPuntos;

    @FXML
    private Label lblRango;

    @FXML
    private Label lblMonederos;

    @FXML
    private Label lblMensajeMenu;

    private Aplicacion aplicacion;

    public void setApp(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
        cargarDatosUsuario();
    }

    private void cargarDatosUsuario() {
        Usuario usuario = Aplicacion.getUsuarioActual();
        if (usuario == null) {
            lblBienvenido.setText("No hay una sesión activa");
            lblPuntos.setText("0");
            lblRango.setText("SIN RANGO");
            lblMonederos.setText("Vuelva a iniciar sesión.");
            return;
        }

        lblBienvenido.setText("Bienvenido, " + usuario.getNombreCompleto());
        lblPuntos.setText(String.valueOf(usuario.getPuntosAcumulados()));
        lblRango.setText(usuario.getRango().name());

        StringBuilder monederos = new StringBuilder();
        for (Monedero monedero : usuario.getListaMonederos()) {
            monederos.append("Código: ").append(monedero.getCodigo())
                    .append("  |  Saldo: $").append(monedero.getSaldo())
                    .append("  |  Tipo: ").append(monedero.getTipoMonedero())
                    .append("\n");
        }
        lblMonederos.setText(monederos.length() == 0
                ? "No tienes monederos registrados."
                : monederos.toString());
    }

    // Operaciones

    @FXML
    void abrirDeposito(ActionEvent event) {
       aplicacion.openViewDeposito();
    }

    @FXML
    void abrirRetiro(ActionEvent event) {
        aplicacion.openViewRetiro();
    }

    @FXML
    void abrirTransferencia(ActionEvent event) {
        aplicacion.openViewTransferencia();
    }

    @FXML
    void abrirProgramarTransaccion(ActionEvent event) {
        aplicacion.openViewProgramarTransaccion();
    }

    // Consultas

    @FXML
    void abrirHistorial(ActionEvent event) {
        aplicacion.openViewHistorial();
    }

    @FXML
    void abrirPuntosRango(ActionEvent event) {
        aplicacion.openViewPuntosRango();
    }

    @FXML
    void abrirReporteGastos(ActionEvent event) {
        aplicacion.openViewReporteGastos();
    }

    @FXML
    void abrirCanjeBeneficios(ActionEvent event) {
        aplicacion.openViewBeneficios();
    }

    @FXML
    void abrirNotificaciones(ActionEvent event) {
        aplicacion.openViewNotificaciones();
    }

    //

    @FXML
    void ejecutarProgramadas(ActionEvent event) {
        try {
            Aplicacion.monederoVirtual.ejecutarTransaccionesProgramadas();
            cargarDatosUsuario();
            mostrarMensaje("Se ejecutaron las transacciones programadas de hoy.", false);
        } catch (Exception e) {
            mostrarMensaje("No se pudieron ejecutar: " + e.getMessage(), true);
        }
    }

    @FXML
    void cerrarSesion(ActionEvent event) {
        aplicacion.cerrarSesion();
    }

    //

    private void mostrarMensaje(String mensaje, boolean error) {
        lblMensajeMenu.setText(mensaje);
        lblMensajeMenu.setStyle(error ? ESTILO_MENSAJE_ERROR : ESTILO_MENSAJE_EXITO);
    }

    @FXML
    void initialize() {
        assert lblBienvenido != null : "fx:id=\"lblBienvenido\" was not injected: check your FXML file 'Menu.fxml'.";
        assert lblPuntos != null : "fx:id=\"lblPuntos\" was not injected: check your FXML file 'Menu.fxml'.";
        assert lblRango != null : "fx:id=\"lblRango\" was not injected: check your FXML file 'Menu.fxml'.";
        assert lblMonederos != null : "fx:id=\"lblMonederos\" was not injected: check your FXML file 'Menu.fxml'.";
        assert lblMensajeMenu != null : "fx:id=\"lblMensajeMenu\" was not injected: check your FXML file 'Menu.fxml'.";
    }
}