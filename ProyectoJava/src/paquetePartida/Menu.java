package paquetePartida;

import java.util.Scanner;
import paqueteCasillas.Casilla;
public class Menu {

    //MENÚ es una clase solamente de métodos, por lo que no tiene ni getters ni setters.

    //METODO CON EL MENU PARA INICIAR PARTIDA
    public static void iniciarMenuPartida(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nBienvenido a Monopoly");
        System.out.println("1. Iniciar partida");
        System.out.println("2. Salir del juego");
        System.out.println("Selecciona una opción:");
        int opcion = scanner.nextInt();

        switch (opcion){
            case 1:
                System.out.println("\nHas seleccionado iniciar partida");
                //AQUI SE INICIA LA PARTIDA Y SE INICIALIZAN LAS CLASES
                EstadoPartida estadoPartida = new EstadoPartida();
                estadoPartida.iniciarPartida();

                break;
            case 2:
                System.out.println("\nHas seleccionado salir del juego");
                System.exit(0);
                break;
            default:
                System.out.println("\nOpción incorrecta");
                iniciarMenuPartida();
        }
    }

//MENU COMPLETO
static public void menuModularizado(EstadoPartida estadoPartida, Jugador jugador,String identificarMenu, boolean propietarioCheck){
    //Hacer un menu modularizado con las opciones de los otros 3 menus anterirores
    Scanner scanner = new Scanner(System.in);
    System.out.println("\n1. Indicar jugador que tiene el turno");
    System.out.println("2. Ver tablero");
    System.out.println("3. Listar jugadores");
    System.out.println("4. Listar avatares");
    System.out.println("5. Listar propiedades en venta");
    System.out.println("6. Describir una casilla");
    System.out.println("7. Describir un jugador");
    System.out.println("8. Describir un avatar");
    switch (identificarMenu) {
        case "menuPreLanzarDados" -> {
            System.out.println("9. Edificar");
            System.out.println("10. Vender edificios");
            System.out.println("11. Lanzar dados");
            System.out.println("12. Salir de la partida");
            System.out.println("13. Opcion administrador");
        }
        case "menuCasillasComprables" -> {
            System.out.println("9. Edificar");
            System.out.println("10. Vender edificios");
            System.out.println("11. Comprar la propiedad");
            System.out.println("12. Finalizar turno");
            System.out.println("13. Salir de la partida");
        }
        case "menuCasillasNoComprables" -> {
            System.out.println("9. Edificar");
            System.out.println("10. Vender edificios");
            System.out.println("11. Finalizar turno");
            System.out.println("12. Salir de la partida");
        }
    }

    int opcion = scanner.nextInt();

    switch(opcion) {
        case 1:
            System.out.println("\nHas seleccionado indicar jugador que tiene el turno");
            //metodo para saber el turno del jugador
            System.out.println("\nEl jugador que tiene el turno es: " + jugador.getNombre());
            System.out.println(jugador.indicarJugador());
            menuModularizado(estadoPartida, jugador, identificarMenu, propietarioCheck);
            break;
        case 2:
            System.out.println("\nHas seleccionado ver tablero");
            estadoPartida.getTablero().imprimir(estadoPartida.devolverPosicionesJugadores(estadoPartida.getJugadores(), estadoPartida.getNumeroJugadores()));
            menuModularizado(estadoPartida, jugador, identificarMenu, propietarioCheck);
            break;
        case 3:
            System.out.println("\nHas seleccionado listar jugadores");
            estadoPartida.listarJugadores();
            menuModularizado(estadoPartida, jugador, identificarMenu, propietarioCheck);
            break;
        case 4:
            System.out.println("\nHas seleccionado listar avatares");
            estadoPartida.listarAvatares();
            menuModularizado(estadoPartida, jugador, identificarMenu, propietarioCheck);
            break;
        case 5:
            System.out.println("\nHas seleccionado listar propiedades en venta");
            estadoPartida.listarPropiedadesVenta();
            menuModularizado(estadoPartida, jugador, identificarMenu, propietarioCheck);
            break;
        case 6:
            System.out.println("\nHas seleccionado describir una casilla");
            estadoPartida.describirCasilla();
            menuModularizado(estadoPartida, jugador, identificarMenu, propietarioCheck);
            break;
        case 7:
            System.out.println("\nHas seleccionado describir un jugador");
            estadoPartida.describirJugador();
            menuModularizado(estadoPartida, jugador, identificarMenu, propietarioCheck);
            break;
        case 8:
            System.out.println("\nHas seleccionado describir un avatar");
            estadoPartida.describirAvatar();
            menuModularizado(estadoPartida, jugador, identificarMenu, propietarioCheck);
            break;
        case 9:
            System.out.println("\nHas seleccionado edificar");
            //Llamamos a la funcion calcular monopolio para saber si hay o no monopolio
            if(jugador.puedesConstruir(jugador.getCasillaDeJugador())){
                //llamar a la funcion edificar
                String tipoEdificacion = menuEdificaciones();
                jugador.getCasillaDeJugador().getGrupoSolar().getEdificaciones().construirEdificacion(tipoEdificacion);
            }
            else{
                System.out.println("\nNo puedes edificar en esta casilla");
            }

            menuModularizado(estadoPartida, jugador, identificarMenu, propietarioCheck);
            break;
        case 10:
            System.out.println("\nHas seleccionado vender edificios");
            //Llamar a la funcion vender edificios
            menuModularizado(estadoPartida, jugador, identificarMenu, propietarioCheck);
            break;
        case 11:
            if(identificarMenu.equals("menuPreLanzarDados")){
                System.out.println("\nHas seleccionado lanzar dados");
                estadoPartida.tirarDados(jugador);
            }
            else if(identificarMenu.equals("menuCasillasComprables")){
                System.out.println("\nHas seleccionado comprar la propiedad");
                //Comprobamos si tiene propietario
                if(!propietarioCheck){
                    System.out.println("\nHas seleccionado comprar la propiedad");
                    //Check del tipo de casilla
                    //Si es de tipo solar, se llama al método comprar propiedad
                    switch (jugador.getCasillaDeJugador().getGrupoCasilla()) {
                        case "Solar" -> jugador.getCasillaDeJugador().getGrupoSolar().comprarPropiedad(jugador);

                        //Si es de tipo transporte, se llama al método comprar propiedad
                        case "Transporte" ->
                                jugador.getCasillaDeJugador().getGrupoTransporte().comprarTransporte(jugador);

                        //Si es de tipo servicios, se llama al método comprar propiedad
                        case "Servicios" -> jugador.getCasillaDeJugador().getGrupoServicios().comprarServicio(jugador);
                    }

                    menuModularizado(estadoPartida, jugador, identificarMenu, propietarioCheck);
                }else{
                    System.out.println("\nLa casilla ya tiene propietario.");
                }
            }
            else if(identificarMenu.equals("menuCasillasNoComprables")){
                System.out.println("\nHas seleccionado finalizar turno");
            }
            break;
        case 12:
            if(identificarMenu.equals("menuPreLanzarDados")){
                salirPartida(estadoPartida, jugador, identificarMenu,propietarioCheck, scanner);
            }
            else if(identificarMenu.equals("menuCasillasComprables")){
                System.out.println("\nHas seleccionado finalizar turno");
            }
            else if(identificarMenu.equals("menuCasillasNoComprables")){
                salirPartida(estadoPartida, jugador, identificarMenu,propietarioCheck, scanner);
            }
            break;
        case 13:
            if(identificarMenu.equals("menuPreLanzarDados")) {
                System.out.println("\nHas seleccionado opcion administrador");
                //Preguntar por el numero de casillas que te quieres mover
                System.out.println("\nIntroduce el numero de casillas que te quieres mover");
                int numeroCasillas = scanner.nextInt();
                EstadoPartida.moverJugador(jugador, numeroCasillas, estadoPartida.getTablero());
                Casilla.realizarAccionesCasilla(estadoPartida, jugador.getCasillaDeJugador(), jugador);
                if(jugador.getCasillaDeJugador().getGrupoSolar()!=null){
                    jugador.actualizarCasillasPasadas(jugador.getCasillaDeJugador());
                }
            }
            else if (identificarMenu.equals("menuCasillasComprables")){
                salirPartida(estadoPartida, jugador, identificarMenu, propietarioCheck, scanner);
            }
            break;
        default:
            System.out.println("\nOpción incorrecta");
            menuModularizado(estadoPartida, jugador, identificarMenu, propietarioCheck);
    }
}

    private static void salirPartida(EstadoPartida estadoPartida, Jugador jugador, String identificarMenu,boolean propietarioCheck, Scanner scanner) {
        System.out.println("\nEstas seguro que quieres salir de la partida? (S/N)");
        String opcionSalir = scanner.nextLine();
        if(opcionSalir.equals("S")) {
            System.out.println("\nHas seleccionado salir de la partida");
            System.exit(0);
        }
        else if(opcionSalir.equals("N")){
            System.out.println("\nHas seleccionado no salir de la partida");
            menuModularizado(estadoPartida, jugador, identificarMenu, propietarioCheck);
        }
        else{
            System.out.println("\nOpción incorrecta");
            menuModularizado(estadoPartida, jugador, identificarMenu, propietarioCheck);
        }
    }


    //MENÚ PARA ELECCIÓN DE AVATAR:
    static public String menuEleccionPiezaAvatar(){
        //MENU QUE ESCOJA ENTRE Coche o Esfinge o Sombrero o Pelota
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nElige una pieza para tu avatar:");
        System.out.println("1. Coche");
        System.out.println("2. Esfinge");
        System.out.println("3. Sombrero");
        System.out.println("4. Pelota");
        System.out.println("Selecciona una opción: ");
        int opcion = scanner.nextInt();
        switch (opcion){
            case 1:
            System.out.println("Has seleccionado Coche");
            return "coche";
            case 2:
                System.out.println("Has seleccionado Esfinge");
                return "esfinge";
            case 3:
                System.out.println("Has seleccionado Sombrero");
                return "sombrero";
            case 4:
                System.out.println("Has seleccionado Pelota");
                return "pelota";
            default:
                System.out.println("Opción incorrecta");
                return menuEleccionPiezaAvatar();
        }
    }

    //menu para escoger la edificación que quieres contruir: Casa, Hotel, Piscina o Pista de Deporte
    static public String menuEdificaciones(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nElige una edificación para construir:");
        System.out.println("1. Casa");
        System.out.println("2. Hotel");
        System.out.println("3. Piscina");
        System.out.println("4. Pista de Deporte");
        System.out.println("Selecciona una opción: ");
        int opcion = scanner.nextInt();
        switch (opcion){
            case 1:
                System.out.println("Has seleccionado Casa");
                return "Casa";
            case 2:
                System.out.println("Has seleccionado Hotel");
                return "Hotel";
            case 3:
                System.out.println("Has seleccionado Piscina");
                return "Piscina";
            case 4:
                System.out.println("Has seleccionado Pista de Deporte");
                return "PistaDeportes";
            default:
                System.out.println("Opción incorrecta");
                menuEdificaciones();
        }
        return null;
    }


}




