package paqueteCasillas;

import paquetePartida.Jugador;

import java.util.ArrayList;

public class GrupoSalida {

    //Atributos
    private int valorSumaSolares;
    private int cantidadPorVuelta;
    private boolean aumentoPreciosAplicado;

    //Constructor (sin argumentos)
    public GrupoSalida(Casilla casilla,int valorSumaSolares) {
        setValorSumaSolares(valorSumaSolares);
        calcularSettearCantidadVuelta();
    }

    // Setter valorSumaSolares
    public void setValorSumaSolares(int valorSumaSolares) {
        this.valorSumaSolares = valorSumaSolares;
    }

    // Setter cantidadPorVuelta
    public void setCantidadPorVuelta(int cantidadPorVuelta){ this.cantidadPorVuelta = cantidadPorVuelta;}

    // Método calcular y settear valor por vuelta
    public void calcularSettearCantidadVuelta(){
        setCantidadPorVuelta(getValorSumaSolares() / 22);
    }

    // Método para calcular la cantidad de dinero que recibe un avatar al completar una vuelta al tablero
    public int getCantidadPorVuelta() {
        return cantidadPorVuelta;
    }

    // Getter
    public int getValorSumaSolares() {
        return valorSumaSolares;
    }

    public boolean getAumentoPreciosAplicado(){
        return aumentoPreciosAplicado;
    }

    // Método para completar una vuelta al tablero
    public void completarVuelta(Jugador jugador) {

        int cantidadPorVuelta = getCantidadPorVuelta();
        jugador.setDinero(jugador.getDinero() + cantidadPorVuelta);
        System.out.println(jugador.getNombre() + " ha completado una vuelta al tablero y ha recibido " + cantidadPorVuelta + " de dinero.");
        jugador.incrementarVueltasCompletadas();
        //estadistica Pasar por casilla salida
        jugador.setPasarPorCasillaSalida(jugador.getPasarPorCasillaSalida()+cantidadPorVuelta);
    }

    //METODO PARA ACTUALIZAR PRECIO SOLARES
    public void aumentarPreciosCompraSolares(Jugador jugador, Tablero tablero) {

        for (int i = 0; i < 40; i++) {
            Casilla casilla = tablero.getCasilla(i);
            if (casilla.getGrupoCasilla().equals("Solar")) {
                tablero.getCasilla(i).getGrupoSolar().actualizarValor();
            }
        }
        jugador.setVueltasCompletadas(0);
        System.out.println("Todos los precios de compra de solares han aumentado en un 5%.");

    }

}