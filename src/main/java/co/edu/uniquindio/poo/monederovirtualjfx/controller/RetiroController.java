package co.edu.uniquindio.poo.monederovirtualjfx.controller;

import co.edu.uniquindio.poo.monederovirtualjfx.model.*;

import java.util.Optional;

public class RetiroController {
    private final MonederoVirtual monederoVirtual;

    public RetiroController(MonederoVirtual monederoVirtual) {
        this.monederoVirtual = monederoVirtual;
    }
    public Optional<Monedero> buscarMonedero(String codigo){
        return monederoVirtual.buscarMonederoPorCodigo(codigo);
    }
    public boolean existeMonedero (String codigo){
        return buscarMonedero(codigo).isPresent();
    }
    public String retirar(Usuario usuarioActual, String codigoMonedero, double monto)
            throws MontoInvalidoException, SaldoInsuficienteException {
        if(!existeMonedero(codigoMonedero)) {
            throw new IllegalArgumentException("No existe un monedero con ese código");
        }
        Monedero monedero = monederoVirtual.buscarMonederoPorCodigo(codigoMonedero).get();

        return monederoVirtual.retirar(usuarioActual, monedero, monto);
    }
}
