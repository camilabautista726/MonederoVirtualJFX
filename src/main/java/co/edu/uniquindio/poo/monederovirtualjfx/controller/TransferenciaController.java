package co.edu.uniquindio.poo.monederovirtualjfx.controller;


import co.edu.uniquindio.poo.monederovirtualjfx.model.*;

import java.time.LocalDate;
import java.util.Optional;

public class TransferenciaController {
    private final MonederoVirtual monederoVirtual;

    public TransferenciaController(MonederoVirtual monederoVirtual) {
        this.monederoVirtual = monederoVirtual;
    }
    public Optional<Monedero> buscarMonedero(String codigo){
        return monederoVirtual.buscarMonederoPorCodigo(codigo);
    }
    public boolean existeMonedero (String codigo){
        return buscarMonedero(codigo).isPresent();
    }

    public String transferir(Usuario usuarioActual, String codigoOrigen, double monto, String codigoDestinatario, LocalDate fecha, boolean programada, Frecuencia frecuencia)
            throws MontoInvalidoException, SaldoInsuficienteException {
        if(!existeMonedero(codigoOrigen)) {
           throw new IllegalArgumentException("No existe un monedero con ese código");
        }
        Monedero monederoOrigen = monederoVirtual.buscarMonederoPorCodigo(codigoOrigen).get();
        if(!existeMonedero(codigoDestinatario)){
            throw new IllegalArgumentException("No existe un monedero con ese código para realizar la transferencia");
        }
        Monedero monederoDestinatario= monederoVirtual.buscarMonederoPorCodigo(codigoDestinatario).get();
        if ( monederoDestinatario== monederoOrigen) {
            throw new IllegalArgumentException("No puedes transferir al mismo monedero");
        }
         return monederoVirtual.transferir(usuarioActual, monederoOrigen, monto,
                codigoDestinatario, fecha, programada, frecuencia);
    }
}
