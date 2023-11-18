package paqueteCasillas;

import java.util.Scanner;
import paquetePartida.Jugador;
import paquetePartida.EstadoPartida;

public class GrupoIrCarcel{

    //ATRIBUTOS
    private Casilla salidaCarcel;

    //GETTER
    public Casilla getSalidaCarcel() {
        return salidaCarcel;
    }

    //SETTER
    public void setSalidaCarcel(Casilla salidaCarcel) {
        this.salidaCarcel = salidaCarcel;
    }

    // Constructor de la clase GrupoIrCarcel
    public GrupoIrCarcel(Casilla salidaCarcel) {
        this.salidaCarcel = salidaCarcel;
    }

    // OTROS MÉTODOS
    // Método para permitir al jugador salir de la Cárcel
    public static void irACarcel(EstadoPartida estadoPartida, Jugador jugador, int cantidadPorVuelta ) {

        Casilla casillaCarcel = estadoPartida.getTablero().getCarcel(); //Movemos el avatar a la casilla de la carcel
        Scanner scanner = new Scanner(System.in);
        int flag = 0;

        //jugador.getAvatar().setCasilla(casillaCarcel);
        int valorDados=0;
        int cantidadDinero = cantidadDinero(cantidadPorVuelta); //Calculamos el precio de la multa
        //Mover jugador de la casilla irCarcel a carcel
        jugador.incrementarIndiceDados(-40); //Estoy en 30, me voy a mover 20, resto 40 para estar en 10
        EstadoPartida.moverJugador(jugador, 20,estadoPartida);

        //estadística cantidad de veces que se ha ido a la cárcel
        jugador.setVecesCarcel(jugador.getVecesCarcel()+1);

        while(flag==0){
            //MENU PARA QUE EL JUGADOR ELIJA QUE QUIERA HACER
            System.out.println("Elige una opción:");
            System.out.println("1. Pagar para salir de la cárcel");
            System.out.println("2. Usar carta de suerte para salir de la cárcel");
            System.out.println("3. Tirar dados para intentar sacar dobles");
            int opcion = scanner.nextInt();

            switch (opcion){
                case 1:
                    System.out.println(jugador.getNombre() + " ha elegido pagar para salir de la cárcel.");
                    if (jugador.getDinero() >= cantidadDinero)
                    {
                        jugador.setDinero(jugador.getDinero() - cantidadDinero);
                        //jugador.pagar(cantidadDinero, null); // Pagar la cantidad para salir de la Cárcel
                        System.out.println(jugador.getNombre() + " ha pagado " + cantidadDinero + " para salir de la Cárcel.");
                        // Mover el avatar fuera de la Cárcel
                        jugador.setTurnosCarcel(0);
                        System.out.println("El avatar " + jugador.getAvatar().getId() + " ha salido de la Cárcel.");
                        flag=1;
                    }
                    else {
                        // El jugador no tiene suficiente dinero para pagar y debe intentar sacar dobles
                        System.out.println(jugador.getNombre() + " no tiene suficiente dinero para salir de la Cárcel.");
                    }
                    break;
                case 2:
                    System.out.println(jugador.getNombre() + " ha elegido usar una carta de suerte para salir de la cárcel.");
                    if (jugador.tieneCartaSuerte()) {
                        jugador.usarCartaSuerte();
                        System.out.println(jugador.getNombre() + " ha usado una carta de suerte para salir de la Cárcel.");
                        // Mover el avatar fuera de la Cárcel
                        jugador.getAvatar().setCasilla(jugador.getAvatar().getCasilla().getGrupoIrCarcel().getSalidaCarcel());
                        System.out.println("El avatar " + jugador.getAvatar().getId() + " ha salido de la Cárcel.");
                        flag=1;
                        jugador.setTurnosCarcel(0);
                    }
                    else{
                        System.out.println(jugador.getNombre() + " no tiene cartas de suerte para usar.");
                    }
                    break;
                case 3:
                    System.out.println(jugador.getNombre() + " ha elegido tirar dados para intentar sacar dobles.");
                    boolean dobles = checkDoblesCarcel(jugador, valorDados);
                    if (dobles) {
                        System.out.println(jugador.getNombre() + " ha sacado dobles y ha salido de la Cárcel.");
                        // Mover el avatar fuera de la Cárcel
                        EstadoPartida.moverJugador(jugador,valorDados,estadoPartida);
                        jugador.setTurnosCarcel(0);
                    }
                    else {
                        jugador.setTurnosCarcel(jugador.getTurnosCarcel()-1);
                        System.out.println(jugador.getNombre() + " no ha sacado dobles. Turnos restantes en en la carcel: " + jugador.getTurnosCarcel() + ".");
                    }
                    flag=1;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }


    }

    // Método para chequear si hay dobles para salir de la cárcel.
    private static boolean checkDoblesCarcel(Jugador jugador, int valorDados) { //EL método lanzar dado puede ser publico?
        int dado1 = EstadoPartida.lanzarDado();
        int dado2 = EstadoPartida.lanzarDado();
        System.out.println(jugador.getNombre() + " ha lanzado los dados y obtuvo " + dado1 + " y " + dado2 + ".");
        valorDados = dado1 + dado2;
        return dado1 == dado2;
    }

    //Método para calcular la cantidad de dinero. Es el 25% de cantidadPorVuelta
    private static int cantidadDinero(int cantidadPorVuelta){
        return cantidadPorVuelta/4;
    }

}
