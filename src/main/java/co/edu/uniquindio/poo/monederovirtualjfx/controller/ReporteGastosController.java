package co.edu.uniquindio.poo.monederovirtualjfx.controller;

import co.edu.uniquindio.poo.monederovirtualjfx.model.MonederoVirtual;
import co.edu.uniquindio.poo.monederovirtualjfx.model.Usuario;

public class ReporteGastosController {
    private final MonederoVirtual monederoVirtual;

    public ReporteGastosController(MonederoVirtual monederoVirtual) {
        this.monederoVirtual = monederoVirtual;
    }
    public String generar(Usuario usuarioActual){
        return usuarioActual.consultarReportesGastos();
    }
}
