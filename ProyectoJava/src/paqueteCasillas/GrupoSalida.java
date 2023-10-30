package paqueteCasillas;

import paquetePartida.Jugador;

public class GrupoSalida {

    //Atributos
    private int valorSumaSolares;
    private int cantidadPorVuelta;

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

    // Método para completar una vuelta al tablero
    public void completarVuelta(GrupoSalida grupoSalida, Jugador jugador) {

        int cantidadPorVuelta = grupoSalida.getCantidadPorVuelta();

        jugador.setDinero(jugador.getDinero() + cantidadPorVuelta);
        System.out.println(jugador.getNombre() + " ha completado una vuelta al tablero y ha recibido " + cantidadPorVuelta + " de dinero.");
    }

    //METODO PARA ACTUALIZAR CANTIDAD POR VUELTA

}