package paqueteCasillas;

import java.util.Scanner;
import paquetePartida.Jugador;
import paquetePartida.EstadoPartida;

public class GrupoSolar {

    //ATRIBUTOS
    private Casilla casilla;
    private String color;   //mismo color tiene mismo valor inicial y alquiler
    private Jugador propietario;
    private int valor;
    private int alquiler; //debe ser 10% de precio inicial
    //el resto de atributos no hacen falta en esta entrega


    //GETTERS
    public Casilla getCasilla(){return casilla;}
    public String getColorSolar() {
        return color;
    }
    public Jugador getPropietario() {
        return propietario;
    }
    public int getValor() {
        return valor;
    }
    public int getAlquiler() {
        return alquiler;
    }


    //SETTERS
    public void setCasilla(Casilla casilla) {
        this.casilla = casilla;
    }
    public void setColorSolar(String color) {
        this.color = color;
    }
    public void setPropietario(Jugador propietario) {
        this.propietario = propietario;
    }
    public void setAlquiler(int alquiler) {
        this.alquiler = alquiler;
    }
    public void setValor(int valor) {
        this.valor = valor;
    }



    //CONSTRUCTOR - TENEMOS QUE ASIGNAR COLORES
    public GrupoSolar(Casilla casilla, String color){

        //Asignamos atributos mediante setters - al inicializarlo el propietario será banca o null.
        setCasilla(casilla);
        setColorSolar(color);
        setPropietario(EstadoPartida.getBanca());   //----------- BANCA ------------------------

        //Método para calcular el valor:
        valorPorColor(color);
    }


    //MÉTODOS

    //MÉTODO CALCULAR ALQUILER
    private int calcularAlquiler(int valorInicialSolar){
        return valorInicialSolar/10;
    }

    //MÉTODO PARA ASIGNAR COSTE Y COSTE ALQUILER - VA HARDCODEADO
    private void valorPorColor(String color){

        int valorPrimerGrupo = 1000;
        double factorAumento = 1.3;
        //NEGRO
        if (color.equals("Negro")){
            setValor(valorPrimerGrupo);
            setAlquiler(calcularAlquiler(getValor()));
        }
        //AZUL
        if (color.equals("Cian")){
            setValor((int) (valorPrimerGrupo * factorAumento));
            setAlquiler(calcularAlquiler(getValor()));
        }
        //ROSA
        if (color.equals("Rosa")){
            setValor((int) (valorPrimerGrupo * (Math.pow(factorAumento, 2))));
            setAlquiler(calcularAlquiler(getValor()));
        }
        //AMARILLO
        if (color.equals("Amarillo")){
            setValor((int) (valorPrimerGrupo * (Math.pow(factorAumento, 3))));
            setAlquiler(calcularAlquiler(getValor()));
        }
        //ROJO
        if (color.equals("Rojo")){
            setValor((int) (valorPrimerGrupo * (Math.pow(factorAumento, 4))));
            setAlquiler(calcularAlquiler(getValor()));
        }
        //MARRON
        if (color.equals("Marron")){
            setValor((int) (valorPrimerGrupo * (Math.pow(factorAumento, 5))));
            setAlquiler(calcularAlquiler(getValor()));
        }
        //VERDE
        if (color.equals("Verde")){
            setValor((int) (valorPrimerGrupo * (Math.pow(factorAumento, 6))));
            setAlquiler(calcularAlquiler(getValor()));
        }
        //AZUL
        if (color.equals("Azul")){
            setValor((int) (valorPrimerGrupo * (Math.pow(factorAumento, 7))));
            setAlquiler(calcularAlquiler(getValor()));
        }
    }

    //MÉTODO PARA SETEAR EL VALOR DEL ALQUILER - HAY QUE TENER EN CUENTA QUE SI HAY UN JUGADOR PROPIETARIO DE LAS TRES CASILLAS DEL MISMO COLOR SE PAGA EL DOBLE
    public void setAlquilerColor(EstadoPartida EstadoPartida, Jugador jugador){
        String color = jugador.getCasillaDeJugador().getGrupoSolar().getColorSolar();
        //Obtener casillas del mismo color
        //Obtener propietario de las casillas del mismo color
        //Chequeo de si el jugador es propietario de las tres casillas del mismo color
        //Si es el propietario de las tres casilla se hace un set del valor al doble
    }

    //MÉTODO PARA ACTUALIZAR COSTES - TENEMOS QUE PONER EN OTRA CLASE CUANDO USARLO Y CUANDO NO
    public void actualizarValor (){
        setValor((int) (getValor() * 1.05));
    }

    //MÉTODO COMPRAR PROPIEDAD
    public void comprarPropiedad(Jugador jugador){
        //Imprima el precio de la casilla
        System.out.println("El precio de la casilla es de " + getValor() + " €");
        //Preguntar si quiere comprar la casilla
        System.out.println("¿Quieres comprar la casilla? (S/N)");
        Scanner scanner = new Scanner(System.in);
        String respuesta = scanner.nextLine();
        //Si la respuesta es S se compra la casilla
        if(respuesta.equals("S")){
            //Chequeo
            if(jugador.getDinero() - getValor() < 0){
                jugador.noDinero();
            }
            else {
                //Comprar propiedad
                jugador.setDinero(jugador.getDinero() - getValor());
                jugador.anadirPropiedad(casilla);
                setPropietario(jugador);
                System.out.println("Has comprado la casilla " + getCasilla().getNombreCasilla() + " por " +
                        getValor() + " €\n");
            }

        }
        else{
            System.out.println("No has comprado la casilla " + getCasilla().getNombreCasilla() + "\n");
        }
    }

    //MÉTODO COBRAR ALQUILER
    public void pagarAlquiler(Jugador jugador){

        //Chequeo que se pueda pagar o no:
        if((jugador.getDinero() - getAlquiler()) < 0){
            jugador.noDinero();
            return;
        }

        jugador.setDinero(jugador.getDinero() - getAlquiler());

    }



}
