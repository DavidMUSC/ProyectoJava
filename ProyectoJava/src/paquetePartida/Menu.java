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

    public static void menuPreLanzarDados(EstadoPartida estadoPartida, Jugador jugador){
        //Hacer un menu con las siguientes opciones:Indicar jugador que tiene el turno,lanzar dados, listar jugadores, listar avatares, describir una casilla, describir un jugador, describir un avatar, comprar la propiedad, listar propiedades en venta y ver tablero, finalizar turno
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n1. Indicar jugador que tiene el turno");
        System.out.println("2. Ver tablero");
        System.out.println("3. Lanzar dados");
        System.out.println("4. Listar jugadores");
        System.out.println("5. Listar avatares");
        System.out.println("6. Listar propiedades en venta");
        System.out.println("7. Describir una casilla");
        System.out.println("8. Describir un jugador");
        System.out.println("9. Describir un avatar");
        System.out.println("10. Salir de la partida");
        System.out.println("11. Opcion administrador:");
        System.out.println("Selecciona una opción:");
        int opcion = scanner.nextInt();

        switch (opcion){
            case 1:
                System.out.println("\nHas seleccionado indicar jugador que tiene el turno");
                //metodo para saber el turno del jugador
                System.out.println("\nEl jugador que tiene el turno es: " + jugador.getNombre());
                System.out.println(jugador.indicarJugador());
                menuPreLanzarDados(estadoPartida,jugador);
                break;
            case 2:
                System.out.println("\nHas seleccionado ver tablero");
                estadoPartida.getTablero().imprimir(estadoPartida.devolverPosicionesJugadores(estadoPartida.getJugadores(),estadoPartida.getNumeroJugadores()));
                menuPreLanzarDados(estadoPartida,jugador);
                break;
            case 3:
                System.out.println("\nHas seleccionado lanzar dados");
                estadoPartida.tirarDados(jugador);
                break;
            case 4:
                System.out.println("\nHas seleccionado listar jugadores");
                estadoPartida.listarJugadores();
                menuPreLanzarDados(estadoPartida,jugador);
                break;
            case 5:
                System.out.println("\nHas seleccionado listar avatares");
                estadoPartida.listarAvatares();
                menuPreLanzarDados(estadoPartida,jugador);
                break;
            case 6:
                System.out.println("\nHas seleccionado listar propiedades en venta");
                estadoPartida.listarPropiedadesVenta(); //ANTES O DESPUES DE MENUPRELANZARDADOS
                menuPreLanzarDados(estadoPartida,jugador);
                break;
            case 7:
                System.out.println("\nHas seleccionado describir una casilla");
                estadoPartida.describirCasilla();
                menuPreLanzarDados(estadoPartida,jugador);
                break;
            case 8:
                System.out.println("\nHas seleccionado describir un jugador");
                estadoPartida.describirJugador();
                menuPreLanzarDados(estadoPartida,jugador);
                break;
            case 9:
                System.out.println("\nHas seleccionado describir un avatar");
                estadoPartida.describirAvatar();
                menuPreLanzarDados(estadoPartida,jugador);
                break;
            case 10:
                System.out.println("\nEstas seguro que quieres salir de la partida? (S/N)");
                String opcionSalir = scanner.nextLine();
                if(opcionSalir.equals("S")) {
                    System.out.println("Has seleccionado salir de la partida");
                    System.exit(0);
                }
                else if(opcionSalir.equals("N")){
                    System.out.println("\nHas seleccionado no salir de la partida");
                    menuPreLanzarDados(estadoPartida,jugador);
                }
                else{
                    System.out.println("\nOpción incorrecta");
                    menuPreLanzarDados(estadoPartida,jugador);
                }
                break;
            case 11:
                System.out.println("\nHas seleccionado opcion administrador");
                //Preguntar por el numero de casillas que te quieres mover
                System.out.println("\nIntroduce el numero de casillas que te quieres mover");
                int numeroCasillas = scanner.nextInt();
                EstadoPartida.moverJugador(jugador,numeroCasillas, estadoPartida.getTablero());
                Casilla.realizarAccionesCasilla(estadoPartida,jugador.getCasillaDeJugador(), jugador);
                break;
            default:
                System.out.println("Opción incorrecta");
                menuPreLanzarDados(estadoPartida,jugador);
        }
        }


    public static  void menuCasillasComprables( EstadoPartida estadoPartida, Jugador jugador, boolean propietarioCheck){
        //Igual que el menu anterior pero con la opcion de comprar la propiedad y finalizar turno y sin la opcion de lanzar dados
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n1. Indicar jugador que tiene el turno");
        System.out.println("2. Ver tablero");
        System.out.println("3. Listar jugadores");
        System.out.println("4. Listar avatares");
        System.out.println("5. Listar propiedades en venta");
        System.out.println("6. Describir una casilla");
        System.out.println("7. Describir un jugador");
        System.out.println("8. Describir un avatar");
        System.out.println("9. Comprar la propiedad");
        System.out.println("10. Finalizar turno");
        System.out.println("11. Salir de la partida");
        System.out.println("Selecciona una opción:");
        int opcion = scanner.nextInt();

        switch (opcion){
            case 1:
                System.out.println("\nHas seleccionado indicar jugador que tiene el turno");
                System.out.println("\nEl jugador que tiene el turno es: " + jugador.getNombre());
                System.out.println(jugador.indicarJugador());
                menuCasillasComprables(estadoPartida,jugador,propietarioCheck);
                break;
            case 2:
                System.out.println("\nHas seleccionado ver tablero");
                estadoPartida.getTablero().imprimir(estadoPartida.devolverPosicionesJugadores(estadoPartida.getJugadores(),estadoPartida.getNumeroJugadores()));
                menuCasillasComprables(estadoPartida,jugador,propietarioCheck);
                break;
            case 3:
                System.out.println("\nHas seleccionado listar jugadores");
                estadoPartida.listarJugadores();
                menuCasillasComprables(estadoPartida,jugador,propietarioCheck);
                break;
            case 4:
                System.out.println("\nHas seleccionado listar avatares");
                estadoPartida.listarAvatares();
                menuCasillasComprables(estadoPartida,jugador,propietarioCheck);
                break;
            case 5:
                System.out.println("\nHas seleccionado listar propiedades en venta");
                estadoPartida.listarPropiedadesVenta();
                menuCasillasComprables(estadoPartida,jugador,propietarioCheck);
                break;
            case 6:
                System.out.println("\nHas seleccionado describir una casilla");
                estadoPartida.describirCasilla();
                menuCasillasComprables(estadoPartida,jugador,propietarioCheck);
                break;
            case 7:
                System.out.println("\nHas seleccionado describir un jugador");
                estadoPartida.describirJugador();
                menuCasillasComprables(estadoPartida,jugador,propietarioCheck);
                break;
            case 8:
                System.out.println("\nHas seleccionado describir un avatar");
                estadoPartida.describirAvatar();
                menuCasillasComprables(estadoPartida,jugador,propietarioCheck);
                break;
            case 9:
                System.out.println("\nHas seleccionado comprar la propiedad");
                if(!propietarioCheck){
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

                    menuCasillasComprables(estadoPartida,jugador,propietarioCheck);
                }
                else{
                    //Printear el nombre del propietario y que no se puede comprar
                    System.out.println("\nLa casilla ya tiene propietario.");
                    menuCasillasComprables(estadoPartida,jugador,propietarioCheck);
                }
                break;
            case 10:
                System.out.println("\nHas seleccionado finalizar turno");
                break;
            case 11:
                System.out.println("\nEstas seguro que quieres salir de la partida? (S/N)");
                String opcionSalir = scanner.nextLine();
                if(opcionSalir.equals("S")) {
                    System.out.println("\nHas seleccionado salir de la partida");
                    System.exit(0);
                }
                else if(opcionSalir.equals("N")){
                    System.out.println("\nHas seleccionado no salir de la partida");
                    menuCasillasComprables(estadoPartida,jugador,propietarioCheck);
                }
                else{
                    System.out.println("\nOpción incorrecta");
                    menuCasillasComprables(estadoPartida,jugador,propietarioCheck);
                }
                break;
            default:
                System.out.println("\nOpción incorrecta");
                menuCasillasComprables(estadoPartida,jugador,propietarioCheck);
        }
    }

    //MENU IGUAL QUE EL ANTERIOR PARA CASILLA NO COMPRABLES
    static public void menuCasillasNoComprables(EstadoPartida estadoPartida, Jugador jugador){
        //Igual que el menu anterior pero con la opcion de finalizar turno y sin la opcion de lanzar dados
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n1. Indicar jugador que tiene el turno");
        System.out.println("2. Ver tablero");
        System.out.println("3. Listar jugadores");
        System.out.println("4. Listar avatares");
        System.out.println("5. Listar propiedades en venta");
        System.out.println("6. Describir una casilla");
        System.out.println("7. Describir un jugador");
        System.out.println("8. Describir un avatar");
        System.out.println("9. Finalizar turno");
        System.out.println("10. Salir de la partida");
        System.out.println("Selecciona una opción:");
        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                System.out.println("\nHas seleccionado indicar jugador que tiene el turno");
                //metodo para saber el turno del jugador
                System.out.println("\nEl jugador que tiene el turno es: " + jugador.getNombre());
                System.out.println(jugador.indicarJugador());
                menuPreLanzarDados(estadoPartida,jugador);
                menuCasillasNoComprables(estadoPartida, jugador);
                break;
            case 2:
                System.out.println("\nHas seleccionado ver tablero");
                estadoPartida.getTablero().imprimir(estadoPartida.devolverPosicionesJugadores(estadoPartida.getJugadores(),estadoPartida.getNumeroJugadores()));
                menuCasillasNoComprables(estadoPartida, jugador);
                break;
            case 3:
                System.out.println("\nHas seleccionado listar jugadores");
                estadoPartida.listarJugadores();
                menuCasillasNoComprables(estadoPartida, jugador);
                break;
            case 4:
                System.out.println("\nHas seleccionado listar avatares");
                estadoPartida.listarAvatares();
                menuCasillasNoComprables(estadoPartida, jugador);
                break;
            case 5:
                System.out.println("\nHas seleccionado listar propiedades en venta");
                estadoPartida.listarPropiedadesVenta();
                menuCasillasNoComprables(estadoPartida, jugador);
                break;
            case 6:
                System.out.println("\nHas seleccionado describir una casilla");
                estadoPartida.describirCasilla();
                menuCasillasNoComprables(estadoPartida, jugador);
                break;
            case 7:
                System.out.println("\nHas seleccionado describir un jugador");
                estadoPartida.describirJugador();
                menuCasillasNoComprables(estadoPartida, jugador);
                break;
            case 8:
                System.out.println("\nHas seleccionado describir un avatar");
                estadoPartida.describirAvatar();
                menuCasillasNoComprables(estadoPartida, jugador);
                break;
            case 9:
                System.out.println("\nHas seleccionado finalizar turno");
                break;
            case 10:
                System.out.println("\nEstas seguro que quieres salir de la partida? (S/N)");
                String opcionSalir = scanner.nextLine();
                if (opcionSalir.equals("S")) {
                    System.out.println("\nHas seleccionado salir de la partida");
                    System.exit(0);
                } else if (opcionSalir.equals("N")) {
                    System.out.println("\nHas seleccionado no salir de la partida");
                    menuCasillasNoComprables(estadoPartida, jugador);
                } else {
                    System.out.println("\nOpción incorrecta");
                    menuCasillasNoComprables(estadoPartida, jugador);
                }
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

    }




