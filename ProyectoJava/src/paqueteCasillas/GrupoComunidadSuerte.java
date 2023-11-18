package paqueteCasillas;


import paquetePartida.EstadoPartida;
import paquetePartida.Jugador;

import java.util.HashMap;
import java.util.Scanner;

public class GrupoComunidadSuerte {

    //ATRIBUTOS
    private Casilla casilla;
    //HashMap con un int y un string para la baraja de comunidad
    private HashMap<Integer, String> barajaComunidad;
    private HashMap<Integer, String> barajaSuerte;
    //cartas de comunidad
    private String[] cartaComunidad;
    //cartas suerte
    private String[] cartaSuerte;

    //GETTERS
    public Casilla getCasilla() {
        return casilla;
    }

    public HashMap<Integer, String> getBarajaComunidad() {
        return barajaComunidad;
    }

    public HashMap<Integer, String> getBarajaSuerte() {
        return barajaSuerte;
    }

    public String[] getCartaComunidad() {
        return cartaComunidad;
    }

    public String[] getCartaSuerte() {
        return cartaSuerte;
    }

    //SETTERS
    public void setCasilla(Casilla casilla) {
        this.casilla = casilla;
    }

    public void setBarajaComunidad(HashMap<Integer, String> barajaComunidad) {
        this.barajaComunidad = barajaComunidad;
    }

    public void setBarajaSuerte(HashMap<Integer, String> barajaSuerte) {
        this.barajaSuerte = barajaSuerte;
    }

    public void setCartaComunidad(String[] cartaComunidad) {
        this.cartaComunidad = cartaComunidad;
    }

    public void setCartaSuerte(String[] cartaSuerte) {
        this.cartaSuerte = cartaSuerte;
    }

    //CONSTRUCTOR
    public GrupoComunidadSuerte(Casilla casilla) {
        setCasilla(casilla);
        crearCartas();
        crearBarajaComunidad();
        crearBarajaSuerte();
    }

    //METODO PARA CREAR CARTAS DE COMUNIDAD
    public void crearCartas() {
        //creamos 6 cartas de comunidad
        cartaComunidad = new String[6];
        cartaComunidad[0] = "Paga 500€ por un fin de semana en un balneario de 5 estrellas";
        cartaComunidad[1] = "Colócate en la casilla de Salida. Cobra 1000€";
        cartaComunidad[2] = "Tu compañía de Internet obtiene beneficios. Recibe 2000€";
        cartaComunidad[3] = "Paga 500€ por invitar a todos tus amigos a un viaje a Santiago.";
        cartaComunidad[4] = "Retrocede hasta Badajoz para comprar antigüedades exóticas";
        cartaComunidad[5] = "Recibe 1000€ de beneficios por alquilar los servicios de tu jet privado.";

        //Creamos 6 cartas de suerte
        cartaSuerte = new String[6];
        cartaSuerte[0] = "¡Has ganado el bote de la lotería! Recibe 20000€";
        cartaSuerte[1] = "¡Hora punta de tráfico! Retrocede tres casillas.";
        cartaSuerte[2] = "Decides hacer un viaje de placer. Avanza hasta Pontevedra.";
        cartaSuerte[3] = "Te multan por usar el móvil mientras conduces. Paga 300€.";
        cartaSuerte[4] = "Paga 1000€ por la matrícula del colegio privado";
        cartaSuerte[5] = "Beneficio por la venta de tus acciones. Recibe 2000€";
    }

    private HashMap<Integer, String> crearBaraja(String[] cartas) {
        HashMap<Integer, String> baraja = new HashMap<>();

        for (int i = 0; i < cartas.length; i++) {
            baraja.put(i, cartas[i]);
        }

        return baraja;
    }

    public void crearBarajaComunidad() {
        barajaComunidad = crearBaraja(cartaComunidad);
    }

    public void crearBarajaSuerte() {
        barajaSuerte = crearBaraja(cartaSuerte);
    }

    //metodo para barajar las cartas
    public void barajar(HashMap<Integer, String> baraja) {
        //barajamos las cartas de la baraja
        for (int i = 0; i < baraja.size(); i++) {
            //escoge un numero aleatorio entre 0 y el tamaño de la baraja
            int random = (int) (Math.random() * baraja.size());
            //intercambia las cartas de la posicion i y la posicion random y asi sucesivamente entre todas la cartas
            String aux = baraja.get(i);
            baraja.put(i, baraja.get(random));
            baraja.put(random, aux);
        }
    }

    //metodo para seleccionar una carta de la baraja y realizar la accion que indica
    public void accionCarta(EstadoPartida estadoPartida, Jugador jugador, char identificador) {
        switch (identificador) {
            case 'C':
                //Imprimir que has caído en una casilla de comunidad y que escoges una carta
                System.out.println("Has caído en una casilla de comunidad. Escoge una carta");
                //barajamos las cartas de la baraja
                barajar(barajaComunidad);
                //pedir que escoja un numero del 1-6. Si no es un numero del 1-6, volver a pedirlo
                Scanner scanner = new Scanner(System.in);
                int numero;
                // Mientras la entrada no sea un número o esté fuera del rango 1-6, sigue pidiendo input.
                while (true) {
                    System.out.println("Escoge un número del 1 al 6");

                    // Verifica si la entrada es un número entero.
                    if (scanner.hasNextInt()) {
                        numero = scanner.nextInt();

                        // Verifica si el número está en el rango 1-6.
                        if (numero >= 1 && numero <= 6) {
                            break; // Sale del bucle si el número es válido.
                        } else {
                            System.out.println("Número fuera del rango. Por favor, introduce un número del 1 al 6");
                        }
                    } else {
                        System.out.println("Por favor, introduce un número válido del 1 al 6");
                        scanner.next(); // Limpia el buffer del scanner
                    }
                }
                //imprimir la carta que ha salido
                System.out.println(barajaComunidad.get(numero));
                String stringAux = barajaComunidad.get(numero);
                //realizar la accion que indica la carta
                accionCartaComunidad(estadoPartida,jugador,stringAux);
                break;
            case 'S':
                //Imprimir que has caído en una casilla de suerte y que escoges una carta
                System.out.println("Has caído en una casilla de suerte. Escoge una carta");
                //barajamos las cartas de la baraja
                barajar(barajaSuerte);
                //pedir que escoja un numero del 1-6. Si no es un numero del 1-6, volver a pedirlo
                Scanner scanner2 = new Scanner(System.in);
                int numero2;
                // Mientras la entrada no sea un número o esté fuera del rango 1-6, sigue pidiendo input.
                while (true) {
                    System.out.println("Escoge un número del 1 al 6");

                    // Verifica si la entrada es un número entero.
                    if (scanner2.hasNextInt()) {
                        numero2 = scanner2.nextInt();

                        // Verifica si el número está en el rango 1-6.
                        if (numero2 >= 1 && numero2 <= 6) {
                            break; // Sale del bucle si el número es válido.
                        } else {
                            System.out.println("Número fuera del rango. Por favor, introduce un número del 1 al 6");
                        }
                    } else {
                        System.out.println("Por favor, introduce un número válido del 1 al 6");
                        scanner2.next(); // Limpia el buffer del scanner
                    }
                }
                //imprimir la carta que ha salido
                System.out.println(barajaSuerte.get(numero2));
                String stringAux2 = barajaSuerte.get(numero2);
                //realizar la accion que indica la carta
                accionCartaSuerte(estadoPartida,jugador,stringAux2);
                break;
        }
    }

    private void accionCartaSuerte(EstadoPartida estadoPartida, Jugador jugador,String cartaSuerte) {
        switch (cartaSuerte){
            case "¡Has ganado el bote de la lotería! Recibe 20000€":
                //estadísticas premios o bote
                jugador.setCobroPremiosOBote(jugador.getCobroPremiosOBote()+20000);
                //sumar 20000€ al jugador
                jugador.setDinero(jugador.getDinero() + 20000);
                //imprimir que ha ganado el bote de la loteria y que ha recibido 20000€
                System.out.println("Has ganado el bote de la lotería y has recibido 20000€");
                break;
            case "¡Hora punta de tráfico! Retrocede tres casillas.":
                EstadoPartida.moverJugador(jugador,-3,estadoPartida);
                //imprimir que ha retrocedido 3 casillas por la hora punta de trafico
                System.out.println("Has retrocedido 3 casillas por la hora punta de trafico");
                break;
            case "Decides hacer un viaje de placer. Avanza hasta Pontevedra.":
                //Calcular cuantas casillas se tiene que mover desde su casilla hasta Pontevedra
                int numCasillaJugador= jugador.getCasillaDeJugador().getNumCasilla();
                int numCasillaPontevedra=39;
                int numCasillasAMover=numCasillaPontevedra-numCasillaJugador;
                //mover al jugador a la casilla de Pontevedra que es la 39
                EstadoPartida.moverJugador(jugador,numCasillasAMover,estadoPartida);
                //imprimir que se ha movido a la casilla de Pontevedra
                System.out.println("Te has movido a la casilla de Pontevedra");
                break;
            case "Te multan por usar el móvil mientras conduces. Paga 300€.":
                //estadisticas PagoTasasEImpuestos
                jugador.setPagoTasasEImpuestos(jugador.getPagoTasasEImpuestos()+300);
                //restar 300€ al jugador
                jugador.setDinero(jugador.getDinero() - 300);
                //imprimir que ha pagado 150000€ por usar el movil mientras conducia
                System.out.println("Has pagado 300€ por usar el movil mientras conducias");
                break;
            case "Paga 1000€ por la matrícula del colegio privado":
                if(jugador.getDinero()<500){
                    jugador.noDinero(estadoPartida);
                    return;
                }
                //estadisticas PagoTasasEImpuestos
                jugador.setPagoTasasEImpuestos(jugador.getPagoTasasEImpuestos()+1000);
                //restar 1000€ al jugador
                jugador.setDinero(jugador.getDinero()-1000);
                //imprimir que ha pagado 1000€ por la matricula del colegio privado
                System.out.println("Has pagado 1000€ por la matricula del colegio privado");
                break;
            case "Beneficio por la venta de tus acciones. Recibe 2000€":
                //estadísticas premios o bote
                jugador.setCobroPremiosOBote(jugador.getCobroPremiosOBote()+2000);
                //sumar 2000€ al jugador
                jugador.setDinero(jugador.getDinero()+2000);
                //imprimir que ha recibido 2000€ por la venta de sus acciones
                System.out.println("Has recibido 2000€ por la venta de tus acciones");
                break;
        }
    }

    private void accionCartaComunidad(EstadoPartida estadoPartida,Jugador jugador,String cartaComunidad) {
        switch (cartaComunidad) {
            case "Paga 500€ por un fin de semana en un balneario de 5 estrellas":
                //COMPROBAR SI EL JUGADOR TIENE DINERO
                if(jugador.getDinero()<500){
                    jugador.noDinero(estadoPartida);
                    return;
                }
                //estadisticas PagoTasasEImpuestos
                jugador.setPagoTasasEImpuestos(jugador.getPagoTasasEImpuestos()+500);
                //restar 500000€ al jugador
                jugador.setDinero(jugador.getDinero() - 500);
                //imprimir que se ha pagado 500€ por un fin de semana en un balneario de 5 estrellas
                System.out.println("Has pagado 500€ por un fin de semana en un balneario de 5 estrellas");
                break;
            case "Colócate en la casilla de Salida. Cobra 1000€":
                //estadísticas premios o bote
                jugador.setCobroPremiosOBote(jugador.getCobroPremiosOBote()+1000);
                //mover al jugador a la casilla de salida. Antes hay que ajustar indiceDados a 0
                jugador.setIndiceDados(0);
                EstadoPartida.moverJugador(jugador,0,estadoPartida);
                //sumar 1000€ al jugador
                jugador.setDinero(jugador.getDinero() + 1000);
                //imprimir que se ha colocado en la casilla de salida y que ha cobrado 1000€
                System.out.println("Te has colocado en la casilla de salida y has cobrado 1000€");
                break;
            case "Tu compañía de Internet obtiene beneficios. Recibe 2000€":
                //estadísticas premios o bote
                jugador.setCobroPremiosOBote(jugador.getCobroPremiosOBote()+2000);
                //sumar 2000000€ al jugador
                jugador.setDinero(jugador.getDinero() + 2000);
                //imprimir que la compañia de internet ha obtenido beneficios y que ha recibido 2000000€
                System.out.println("Tu compañía de Internet ha obtenido beneficios y has recibido 2000€");
                break;
            case "Paga 500€ por invitar a todos tus amigos a un viaje a Santiago.":
                //estadisticas PagoTasasEImpuestos
                jugador.setPagoTasasEImpuestos(jugador.getPagoTasasEImpuestos()+500);
                //restar 500€ al jugador
                jugador.setDinero(jugador.getDinero() - 500);
                //imprimir que ha pagado 500€ por invitar a todos sus amigos a un viaje a Santiago
                System.out.println("Has pagado 500€ por invitar a todos tus amigos a un viaje a Santiago");
                break;
            case "Retrocede hasta Badajoz para comprar antigüedades exóticas":
                //Comprobar la diferencia entre la casilla actual del jugador y la casilla de Badajoz
                int numCasillaJugador= jugador.getCasillaDeJugador().getNumCasilla();
                int numCasillaBadajoz=1;
                //mover al jugador a la casilla de Caceres
                EstadoPartida.moverJugador(jugador,numCasillaBadajoz-numCasillaJugador,estadoPartida);
                //imprimir que se ha retrocedido hasta Caceres para comprar antigüedades exóticas
                System.out.println("Te has retrocedido hasta Caceres para comprar antigüedades exóticas");
                break;
            case "Recibe 1000€ de beneficios por alquilar los servicios de tu jet privado.":
                //estadísticas premios o bote
                jugador.setCobroPremiosOBote(jugador.getCobroPremiosOBote()+1000);
                //Sumar 1000 al dinero del jugador
                jugador.setDinero(jugador.getDinero()+1000);
                //Imprimir que ha recibido 1000€ de beneficios por alquilar los servicios de su jet privado
                System.out.println("Has recibido 1000€ de beneficios por alquilar los servicios de tu jet privado");
                break;
        }

    }
}