package paqueteCasillas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Tablero {
    
    //Atributos
    private static int valorSumaSolares;
    private ArrayList<Casilla> casillasTablero;


    //GETTERS
    public Casilla getCasilla(int numCasilla){
        return casillasTablero.get(numCasilla);
    }
    public ArrayList<Casilla> getCasillasTablero() {
        return casillasTablero;
    }
    public static int getValorSumaSolares(){
        return valorSumaSolares;
    }

    //SETTERS
    public void setValorSumaSolares(int valorSumaSolares) {
        //CHECKEO DE QUE NO PUEDE SER NEGATIVO
        if(valorSumaSolares<0){
            //Imprimir que no puede ser menor que 0
            System.out.println("El valor de la suma de los solares no puede ser menor que 0.");
            this.valorSumaSolares = valorSumaSolares;
        }else {
            this.valorSumaSolares = valorSumaSolares;
        }
    }

    //Constructor
    public Tablero() {
        // Función para inicializar cada casilla del tablero;
        inicializarCasillas();
    }
    //Métodos
    //Declaramos casilla por casilla
    private void inicializarCasillas() {

        //Inicializamos ArrayList:
        this.casillasTablero = new ArrayList<>(40);
        for (int i = 0; i < 40; i++){
            casillasTablero.add(null);
        }

        //Cada casilla se va declarando

        //Lista de índices de las casillas Solar:
        int[] indices = {1,3,6,8,9,11,13,14,16,18,19,21,23,24,26,27,29,31,32,34,37,39};

        //PRIMERO SOLARES:
        casillasTablero.set(1,new Casilla("Badajoz", 1,"Solar", "Negro"));
        casillasTablero.set(3,new Casilla("Cáceres", 3,"Solar", "Negro"));

        casillasTablero.set(6,new Casilla("Huesca", 6,"Solar", "Cian"));
        casillasTablero.set(8,new Casilla("Zaragoza", 8,"Solar", "Cian"));
        casillasTablero.set(9,new Casilla("Teruel", 9,"Solar", "Cian"));

        casillasTablero.set(11, new Casilla("Getafe", 11, "Solar", "Rosa"));
        casillasTablero.set(13,new Casilla("Alcalá", 13,"Solar", "Rosa"));
        casillasTablero.set(14,new Casilla("Madrid", 14,"Solar", "Rosa"));

        casillasTablero.set(16,new Casilla("Castellón", 16, "Solar", "Amarillo"));
        casillasTablero.set(18,new Casilla("Valencia", 18,"Solar", "Amarillo"));
        casillasTablero.set(19,new Casilla("Alicante", 19,"Solar", "Amarillo"));

        casillasTablero.set(21,new Casilla("Sevilla", 21,"Solar", "Rojo"));
        casillasTablero.set(23,new Casilla("Granada", 23,"Solar", "Rojo"));
        casillasTablero.set(24,new Casilla("Málaga", 24,"Solar", "Rojo"));

        casillasTablero.set(26,new Casilla("Murcia", 26,"Solar", "Marron"));
        casillasTablero.set(27,new Casilla("Toledo", 27,"Solar", "Marron"));
        casillasTablero.set(29,new Casilla("Cuenca", 29,"Solar", "Marron"));

        casillasTablero.set(31,new Casilla("Girona", 31,"Solar", "Verde"));
        casillasTablero.set(32,new Casilla("Tarragona", 32,"Solar", "Verde"));
        casillasTablero.set(34,new Casilla("Barcelona", 34,"Solar", "Verde"));

        casillasTablero.set(37,new Casilla("Santiago", 37,"Solar", "Azul"));
        casillasTablero.set(39,new Casilla("Pontevedra", 39,"Solar", "Azul"));

        //Calculamos el valor de todos los solares:
        calcularSumaDeSolares(casillasTablero, indices);

        //Inicializamos Salida:
        casillasTablero.set(0,new Casilla("Salida", 0,"Salida", valorSumaSolares)); //valorSumaSolares

        //Inicializamos Transporte
        casillasTablero.set(5,new Casilla("Monbus", 5,"Transporte", getDineroPasarSalida(casillasTablero.get(0))));
        casillasTablero.set(15,new Casilla("Cercanías", 15,"Transporte", getDineroPasarSalida(casillasTablero.get(0))));
        casillasTablero.set(25,new Casilla("AVE", 25,"Transporte", getDineroPasarSalida(casillasTablero.get(0))));
        casillasTablero.set(35,new Casilla("Avioń", 35,"Transporte", getDineroPasarSalida(casillasTablero.get(0))));

        //Inicializamos Servicios
        casillasTablero.set(12,new Casilla("Fenosa", 12,"Servicios", getDineroPasarSalida(casillasTablero.get(0))));
        casillasTablero.set(28,new Casilla("Endesa", 28,"Servicios", getDineroPasarSalida(casillasTablero.get(0))));

        //Inicializamos Impuestos
        casillasTablero.set(4,new Casilla("IVA", 4,"Impuestos", getDineroPasarSalida(casillasTablero.get(0))));
        casillasTablero.set(38,new Casilla("IRPF", 38,"Impuestos", getDineroPasarSalida(casillasTablero.get(0))));

        //Inicializamos IrCarcel
        casillasTablero.set(30,new Casilla("IrCarcel", 30,"IrCarcel"));

        //Inicializamos Carcel
        casillasTablero.set(10,new Casilla("Carcel", 10,"Carcel"));

        //Inicializamos CajaComunidad
        casillasTablero.set(2,new Casilla("Caja", 2,"CajaComunidad"));
        casillasTablero.set(17,new Casilla("Caja", 17, "CajaComunidad"));
        casillasTablero.set(33,new Casilla("Caja", 33,"CajaComunidad"));

        //Inicializamos Suerte
        casillasTablero.set(7,new Casilla("Suerte", 7,"Suerte"));
        casillasTablero.set(22,new Casilla("Suerte", 22,"Suerte"));
        casillasTablero.set(36,new Casilla("Suerte", 36,"Suerte"));

        //Inicializamos Parking - Para la deuda hacemos un atributo static
        casillasTablero.set(20,new Casilla("Parking", 20,"Parking"));
    }

    public Casilla getCarcel() {
        for (Casilla casilla : casillasTablero) {
            if (casilla.getNombreCasilla().equals("Carcel")) {
                return casilla;
            }
        }
        return null; // Devuelve null si no se encuentra la casilla de la cárcel
    }

    // Método para imprimir el tablero
    public void imprimir(Map<Character, Integer> mapa){


        //MATRIZ
        Casilla[] casillas = {
                casillasTablero.get(20), casillasTablero.get(21), casillasTablero.get(22), casillasTablero.get(23), casillasTablero.get(24), casillasTablero.get(25), casillasTablero.get(26), casillasTablero.get(27), casillasTablero.get(28), casillasTablero.get(29), casillasTablero.get(30),
                casillasTablero.get(19), null, null, null, null, null, null, null, null, null, casillasTablero.get(31),
                casillasTablero.get(18), null, null, null, null, null, null, null, null, null, casillasTablero.get(32),
                casillasTablero.get(17), null, null, null, null, null, null, null, null, null, casillasTablero.get(33),
                casillasTablero.get(16), null, null, null, null, null, null, null, null, null, casillasTablero.get(34),
                casillasTablero.get(15), null, null, null, null, null, null, null, null, null, casillasTablero.get(35),
                casillasTablero.get(14), null, null, null, null, null, null, null, null, null, casillasTablero.get(36),
                casillasTablero.get(13), null, null, null, null, null, null, null, null, null, casillasTablero.get(37),
                casillasTablero.get(12), null, null, null, null, null, null, null, null, null, casillasTablero.get(38),
                casillasTablero.get(11), null, null, null, null, null, null, null, null, null, casillasTablero.get(39),
                casillasTablero.get(10), casillasTablero.get(9), casillasTablero.get(8), casillasTablero.get(7), casillasTablero.get(6), casillasTablero.get(5), casillasTablero.get(4), casillasTablero.get(3), casillasTablero.get(2), casillasTablero.get(1), casillasTablero.get(0),
        };

        //CAMBIAR STRINGS POR CASILLA.NOMBRE
        String[] stringCasillas = {
                casillasTablero.get(20).getNombreCasilla(), casillasTablero.get(21).getNombreCasilla(), casillasTablero.get(22).getNombreCasilla(), casillasTablero.get(23).getNombreCasilla(), casillasTablero.get(24).getNombreCasilla(), casillasTablero.get(25).getNombreCasilla(), casillasTablero.get(26).getNombreCasilla(), casillasTablero.get(27).getNombreCasilla(), casillasTablero.get(28).getNombreCasilla(), casillasTablero.get(29).getNombreCasilla(), casillasTablero.get(30).getNombreCasilla(),
                casillasTablero.get(19).getNombreCasilla(), "", "", "", "", "", "", "", "", "", casillasTablero.get(31).getNombreCasilla(),
                casillasTablero.get(18).getNombreCasilla(), "", "", "", "", "", "", "", "", "", casillasTablero.get(32).getNombreCasilla(),
                casillasTablero.get(17).getNombreCasilla(), "", "", "", "", "", "", "", "", "", casillasTablero.get(33).getNombreCasilla(),
                casillasTablero.get(16).getNombreCasilla(), "", "", "", "", "", "", "", "", "", casillasTablero.get(34).getNombreCasilla(),
                casillasTablero.get(15).getNombreCasilla(), "", "", "", "", "", "", "", "", "", casillasTablero.get(35).getNombreCasilla(),
                casillasTablero.get(14).getNombreCasilla(), "", "", "", "", "", "", "", "", "", casillasTablero.get(36).getNombreCasilla(),
                casillasTablero.get(13).getNombreCasilla(), "", "", "", "", "", "", "", "", "", casillasTablero.get(37).getNombreCasilla(),
                casillasTablero.get(12).getNombreCasilla(), "", "", "", "", "", "", "", "", "", casillasTablero.get(38).getNombreCasilla(),
                casillasTablero.get(11).getNombreCasilla(), "", "", "", "", "", "", "", "", "", casillasTablero.get(39).getNombreCasilla(),
                casillasTablero.get(10).getNombreCasilla(), casillasTablero.get(9).getNombreCasilla(), casillasTablero.get(8).getNombreCasilla(), casillasTablero.get(7).getNombreCasilla(), casillasTablero.get(6).getNombreCasilla(), casillasTablero.get(5).getNombreCasilla(), casillasTablero.get(4).getNombreCasilla(), casillasTablero.get(3).getNombreCasilla(), casillasTablero.get(2).getNombreCasilla(), casillasTablero.get(1).getNombreCasilla(), casillasTablero.get(0).getNombreCasilla()
        };

        int filas = 11;
        int columnas = 11;

        // Verificar que el número total de casillas coincida con filas * columnas
        if (casillas.length != filas * columnas) {
            System.out.println("Error: El número de casillas no coincide con filas * columnas.");
            return;
        }

        // Imprimir el encabezado del tablero
        for (int columna = 0; columna < columnas; columna++) {
            System.out.print("+--------------------");
        }
        System.out.println("+");

        // Imprimir las filas del tablero
        // Imprimir el encabezado del tablero
        for (int columna = 0; columna < columnas; columna++) {
            System.out.print("+--------------------");
        }
        System.out.println("+");

        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                String stringaux = stringCasillas[fila * columnas + columna];
                Casilla casilla = casillas[fila * columnas + columna];
                String color = RESET;

                if (casilla != null && "Solar".equals(casilla.getGrupoCasilla())) {
                    color = getTextColor(casilla);
                }

                System.out.print("| " + color + padRight(stringaux, mapa, casilla) + RESET + " ");
            }
            System.out.println("|");

            // Imprimir los separadores de fila
            for (int columna = 0; columna < columnas; columna++) {
                System.out.print("+--------------------");
            }
            System.out.println("+");
        }
    }

    public static final String RESET = "\033[0m";
    public String getTextColor(Casilla casilla){
        String ColorSolar = casilla.getColorSolar();
        String color = RESET; //Color por defecto

        switch (ColorSolar){
            case "Negro":
                color = "\033[4;30m";
                break;
            case "Cian":
                color = "\033[4;36m";
                break;
            case "Rosa":
                color = "\033[4;35m";
                break;
            case "Amarillo":
                color = "\033[4;93m";
                break;
            case "Rojo":
                color = "\033[4;31m";
                break;
            case "Marron":
                color = "\033[4;33m";
                break;
            case "Verde":
                color = "\033[4;32m";
                break;
            case "Azul":
                color = "\033[4;34m";
                break;
            default:
                color=RESET;
        }

        return color;
    }
    // Método para ajustar el ancho de las casillas. Hay que añadir el metodo mapa como argumento para comprobar si hay jugadores en la casilla
    private static String padRight(String s, Map<Character, Integer> mapa, Casilla casilla) {
        // Comprobamos jugadores en el número de casilla:
        int longitudTotal = 18; // Longitud total deseada

        if (casilla == null) {
            // Imprimir cadena vacía con 18 espacios sin usar argumentos
            return String.format("%-18s", "");
        }

        if (mapa.containsValue(casilla.getNumCasilla())) {
            // Si contiene el valor, hallar el número de jugadores en la casilla:
            int numJugadores = Collections.frequency(mapa.values(), casilla.getNumCasilla());

            // Hallar el id de Avatar de los jugadores en la casilla:
            ArrayList<Character> ids = new ArrayList<>();
            for (Map.Entry<Character, Integer> entry : mapa.entrySet()) {
                if (entry.getValue().equals(casilla.getNumCasilla())) {
                    ids.add(entry.getKey());
                }
            }

            int longitudVariable = 0;
            String stringAux = "";

            // Calcular la longitud de la cadena con los IDs de los jugadores
            for (int i = 0; i < numJugadores; i++) {
                if (i == 0) {
                    if (s.endsWith(" ")) {
                        s = s.substring(0, s.length() - 1);
                    }
                    stringAux += "&";
                }
                stringAux += ids.get(i);
                longitudVariable += 1; // Cada ID adicional tiene 1 carácter
            }

            int espaciosEnBlanco = longitudTotal - longitudVariable - s.length()-1;

            if (espaciosEnBlanco < 0) {
                // Si hay más caracteres que la longitud total, acortar la cadena
                s = s.substring(0, s.length() + espaciosEnBlanco);
            } else {
                // Agregar espacios a la derecha si es necesario
                String spaces = new String(new char[espaciosEnBlanco]).replace('\0', ' ');
                s = s + spaces;
            }

            return s + stringAux;
        }

        // Si no hay jugadores, no se toca:
        return String.format("%-18s", s);
    }

    //TABLERO tiene que aceptar un argumento EstadoPartida para cambiar el estado del tablero.
    //También podemos pasarle un atributo de EstadoPartida o un método auxiliar (que solo guarde las posiciones
    //de cada jugador.


    //Método para calcular el total del valor de la suma de los solares.
    public void calcularSumaDeSolares(ArrayList<Casilla> casillas, int[] indices) {

        //Variable resultado
        int sumaSolares = 0;

        //For-each
        for (int indice : indices) {
            if (casillas.get(indice).getGrupoCasilla().equals("Solar")) {
                sumaSolares = sumaSolares + casillas.get(indice).getGrupoSolar().getValor();
            }
        }
        //Setteamos el atributo valorSumaSolares
        setValorSumaSolares(sumaSolares);
    }

    //Método para obtener el dinero que da pasar por la casilla salida
    public int getDineroPasarSalida(Casilla casillaSalida){
        return casillaSalida.getGrupoSalida().getCantidadPorVuelta();
    }

    //Método para saber cual es la casilla más rentable es decir la que tiene el dineroRecaudado más alto
    public Casilla casillaMasRentable(){
        //Variables
        int dineroRecaudado = 0;
        Casilla casillaMasRentable = null;

        //For-each
        for (Casilla casilla : casillasTablero) {
            if (casilla.getGrupoCasilla().equals("Solar")) {
                if (casilla.getGrupoSolar().getDineroRecaudado() > dineroRecaudado) {
                    dineroRecaudado = casilla.getGrupoSolar().getDineroRecaudado();
                    casillaMasRentable = casilla;
                }
                if(casilla.getGrupoCasilla().equals("Transporte")){
                    dineroRecaudado = casilla.getGrupoSolar().getDineroRecaudado();
                    casillaMasRentable = casilla;
                }
                if(casilla.getGrupoCasilla().equals("Servicios")){
                    dineroRecaudado = casilla.getGrupoSolar().getDineroRecaudado();
                    casillaMasRentable = casilla;
                }
            }
        }
        return casillaMasRentable;
    }

    //Método para saber cual es el grupo más rentable
    public String grupoMasRentable(){
        //Variables
        int dineroRecaudado = 0;
        String grupoMasRentable = null;

        //For-each
        for (Casilla casilla : casillasTablero) {
            if (casilla.getGrupoCasilla().equals("Solar")) {
                if (casilla.getGrupoSolar().getDineroRecaudado() > dineroRecaudado) {
                    dineroRecaudado = casilla.getGrupoSolar().getDineroRecaudado();
                    grupoMasRentable = casilla.getGrupoSolar().getColorSolar();
                }
            }
            if(casilla.getGrupoCasilla().equals("Transporte")){
                if(casilla.getGrupoTransporte().getDineroRecaudado()>dineroRecaudado){
                    dineroRecaudado = casilla.getGrupoTransporte().getDineroRecaudado();
                    grupoMasRentable = "Transporte";
                }
            }
            if (casilla.getGrupoCasilla().equals("Servicios")){
                if(casilla.getGrupoServicios().getDineroRecaudado()>dineroRecaudado){
                    dineroRecaudado = casilla.getGrupoServicios().getDineroRecaudado();
                    grupoMasRentable = "Servicios";
                }
            }
        }
        return grupoMasRentable;
    }

}
