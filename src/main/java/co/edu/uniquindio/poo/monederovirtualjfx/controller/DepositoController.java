package co.edu.uniquindio.poo.monederovirtualjfx.controller;

import co.edu.uniquindio.poo.monederovirtualjfx.model.*;

import java.time.LocalDate;
import java.util.Optional;

public class DepositoController {
    private final MonederoVirtual monederoVirtual;

    public DepositoController(MonederoVirtual monederoVirtual) {
        this.monederoVirtual = monederoVirtual;
    }
    public Optional<Monedero> buscarMonedero(String codigo){
        return monederoVirtual.buscarMonederoPorCodigo(codigo);
    }
    public boolean existeMonedero (String codigo){
        return buscarMonedero(codigo).isPresent();
    }
    public String realizarDeposito(Usuario usuario, String codigoMonedero, double monto, LocalDate fecha, boolean programada, Frecuencia frecuencia)
            throws MontoInvalidoException, SaldoInsuficienteException {
        if (!existeMonedero(codigoMonedero)) {
            throw new IllegalArgumentException("No existe un monedero con ese código");
        }
        Monedero monedero = monederoVirtual.buscarMonederoPorCodigo(codigoMonedero).get();
        return monederoVirtual.depositar(usuario, monedero, monto, fecha, programada, frecuencia);
    }

}
