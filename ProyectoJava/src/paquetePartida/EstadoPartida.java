package paquetePartida;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import paqueteCasillas.GrupoImpuesto;
import paqueteCasillas.Tablero;
import paqueteCasillas.Casilla;
import paqueteCasillas.GrupoIrCarcel;

public class EstadoPartida {


    //Atributos
    private int numeroJugadores;
    private ArrayList<Jugador> jugadores;
    private Turno turno;
    private Tablero tablero;
    static private Jugador banca; //Almacenará todas las propiedades.


    //GETTERS
    public int getNumeroJugadores() {
        return numeroJugadores;
    }
    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }
    public Tablero getTablero() {
        return tablero;
    }
    public Turno getTurno() {
        return turno;
    }
    static public Jugador getBanca() {
        return banca;
    }


    //SETTERS
    public void setNumeroJugadores(int numeroJugadores) {
            this.numeroJugadores = numeroJugadores;

    }
    public void setBanca(Jugador banca) {
        this.banca = banca;
    }
    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }
    public void setTurno(Turno turno) {
        this.turno = turno;
    }


    //CONSTRUCTOR
    public EstadoPartida(){
        //Creamos tablero y turno
        this.tablero = new Tablero();
        this.turno = new Turno();

        //Creamos jugador Banca
        Jugador banca = new Jugador("Banca");
        setBanca(banca);

        //Añadimos propiedades al jugador Banca
        anadirPropiedadesBanca(banca, this.tablero);

        //Setteamos numeroJugadores a 0:
        this.numeroJugadores = 0; //MANUALMENTE, no usamos el getter:
        jugadores = new ArrayList<Jugador>(6);
    }



    //OTROS MÉTODOS:
    //MÉTODO CREAR UN NUEVO JUGADOR
    public void nuevoJugador(){

        //Variables
        Jugador jugador;

        //Aquí hacemos lectura de datos
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nIntroduzca el nombre del jugador:");
        String aux_jugador = scanner.nextLine();


        String aux_avatar = Menu.menuEleccionPiezaAvatar();

        //Llamamos al constructor de jugador
        jugador = new Jugador(aux_jugador,aux_avatar, tablero.getCasilla(0));

        //Añadimos ese objeto jugador al array en la última posición disponible (si es vacio será 0, despues 1...)
        jugadores.add(jugador);

        //Aumentamos en 1 el número de jugadores en el atributo numeroJugadores
        setNumeroJugadores(getNumeroJugadores()+1);

        //Indicamos la información del jugador:
        System.out.println(jugador.indicarJugador());
    }

    //MÉTODO PARA AÑADIR TODAS LAS PROPIEDADES POSIBLES A LA BANCA      ---- PODEMOS ELIMINARLA
    public void anadirPropiedadesBanca(Jugador banca, Tablero tablero){

        //Bucle que itera por todas las casillas del tablero:
        for (int i=0;i<40;i++){
            //Chequeamos si es "solar", "estacion" o "servicios":

            //Solar
            if ((tablero.getCasilla(i).getGrupoCasilla()).equals("Solar")){
                //Accede a GrupoSolar
                tablero.getCasilla(i).getGrupoSolar().setPropietario(banca);
                //Añadir a las propiedades de banca
                banca.getPropiedades().add(tablero.getCasilla(i));

            }
            //Transporte
            if ((tablero.getCasilla(i).getGrupoCasilla()).equals("Transporte")){
                //Accede a GrupoTransporte
                tablero.getCasilla(i).getGrupoTransporte().setPropietario(banca);
                //Añadir a las propiedades de banca
                banca.getPropiedades().add(tablero.getCasilla(i));
            }
            //Servicios
            if ((tablero.getCasilla(i).getGrupoCasilla()).equals("Servicios")){
                //Accede a GrupoServicios
                tablero.getCasilla(i).getGrupoServicios().setPropietario(banca);
                //Añadir a las propiedades de banca
                banca.getPropiedades().add(tablero.getCasilla(i));

            }


        }
    }

    //MÉTODO QUE TE PIDE NUMERO DE JUGADORES Y INVOCA A NUEVOJUGADOR N VECES
    public void iniciarPartida(){

        //Objeto nuevo:
        Scanner scanner = new Scanner(System.in);
        System.out.println();

        //CREAR JUGADORES:
        // ¿Número de jugadores?
        System.out.print("Inserta el numero de jugadores:");
        int numJugadoresAux = scanner.nextInt();
        // Creamos n jugadores
        int i = 0;
        for (i = 0; i < numJugadoresAux; i++){
            nuevoJugador();
        }
        iterarTurnos(jugadores,numeroJugadores,turno);
    }

    //METODO PARA ITERAR TURNOS
    public void iterarTurnos(ArrayList<Jugador> jugadores, int numeroJugadores, Turno turno){
        Jugador jugador;
        int contador = 0;
        while(numeroJugadores!=1){
                jugador= jugadores.get(contador);
                if (jugador.getTurnosCarcel()!=0){
                    GrupoIrCarcel.irACarcel(this.getTablero(),jugador,this.getTablero().getCasilla(0).getGrupoSalida().getCantidadPorVuelta());
                }else{
                    Menu.menuPreLanzarDados(this,jugador);
                }
                jugadorSiguiente(jugador);
                contador++;
                if (contador==numeroJugadores){
                    contador=0;
                    turno.actualizarTurno();
                }
        }

    }

    //METODO PARA IMPRIMIR EL JUGADOR SIGUIENTE
    public  void jugadorSiguiente(Jugador jugador){
        int posicion = jugadores.indexOf(jugador);
        if (posicion == numeroJugadores-1){
            System.out.println("El siguiente jugador es " + jugadores.get(0).getNombre());
        }
        else{
            System.out.println("El siguiente jugador es " + jugadores.get(posicion+1).getNombre());
        }
    }

    //MÉTODO DEVOLVER POSICIÓN JUGADOR
    public int devolverPosicionJugador(Jugador jugador ){
        return jugador.getCasillaDeJugador().getNumCasilla();
    }

    //MÉTODO DEVOLVER POSICIONES JUGADORES
    public Map<Character, Integer> devolverPosicionesJugadores(ArrayList<Jugador> jugadores, int numeroJugadores){

        //Variables
        Map<Character, Integer> mapa = new HashMap<Character, Integer>();
        int posicion;
        char IDcaracter;
        ArrayList<Jugador> jugadoresAux =  getJugadores();  //Cuidado con el Aliasing

        //Iteramos en el ArrayList y vamos añadiendo al HashMap:
        for (int i = 0; i < numeroJugadores; i++){

            //Sacamos un jugador:
            IDcaracter = jugadoresAux.get(i).getAvatar().getId();
            posicion = jugadoresAux.get(i).getAvatar().getCasilla().getNumCasilla();

            //Añadimos en el mapa:
            mapa.put(IDcaracter, posicion);
        }

        //Devolvemos el mapa:
        return mapa;
    }

    //METODO AUXILIAR para mover un juegador un numero de casillas determinado
    public static void moverJugador(Jugador jugador, int casillas, Tablero tablero1){

        //Variables
        int posicionActual = jugador.getCasillaDeJugador().getNumCasilla();

        int posicionNueva = (posicionActual + casillas) % 40;

        jugador.setCasillaDeJugador(tablero1.getCasilla(posicionNueva));
        //MOVER AVATAR EN EL TABLERO A LA NUEVA CASILLA
        jugador.cambiarCasillaAvatar(tablero1.getCasilla(posicionNueva));


    }

    //MÉTODO LANZARDADO
    public static int lanzarDado() {
        return (int) (Math.random() * 6) + 1;
    }

    //MÉTODO PARA TIRAR DADOS
    public void tirarDados(Jugador jugador) {
        //Variables
        int dado1;
        int dado2;
        int total;

        //Leer input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pulsa enter para tirar los dados");
        scanner.nextLine();

        //Generar dados
        /*dado1 = 3;
        dado2 = 3;*/

        dado1 = EstadoPartida.lanzarDado();
        dado2 = EstadoPartida.lanzarDado();
        total = dado1 + dado2;
        System.out.println("El jugador " + jugador.getNombre() + " ha sacado " + dado1 + " y " + dado2 + ", avanzando " + total + " posiciones.");
        //Mover avatar
        moverJugador(jugador, total, this.tablero);
        Casilla.realizarAccionesCasilla(this,jugador.getCasillaDeJugador(), jugador);
        //COMPROBACION DE SI SALEN DOBLES VOLVER A TIRAR, REALIZAR ACCIONES CASILLA Y SI SALE TERCERA VEZ IR A LA CARCEL
        if(dado1==dado2){
            //Printear que el jugador saco dobles
            System.out.println("El jugador " + jugador.getNombre() + " ha sacado dobles. Por lo tanto tu turno vuelve comenzar.");
            /*dado1 = 3;
            dado2 = 3;*/

            dado1 = EstadoPartida.lanzarDado();
            dado2 = EstadoPartida.lanzarDado();
            total = dado1 + dado2;
            System.out.println("El jugador " + jugador.getNombre() + " ha sacado " + dado1 + " y " + dado2 + ", avanzando " + total + " posiciones.");
            moverJugador(jugador, total, this.tablero);
            Casilla.realizarAccionesCasilla(this,jugador.getCasillaDeJugador(), jugador);
            if(dado1==dado2){
                //Printear que el jugador saco dobles
                System.out.println("El jugador " + jugador.getNombre() + " ha sacado dobles por segunda vez. Por lo tanto su turno vuelve comenzar.");
                /*dado1 = 3;
                dado2 = 3;*/

                dado1 = EstadoPartida.lanzarDado();
                dado2 = EstadoPartida.lanzarDado();
                if(dado1==dado2){
                    jugador.setTurnosCarcel(3);
                    jugador.setCasillaDeJugador(tablero.getCasilla(10));
                    jugador.cambiarCasillaAvatar(tablero.getCasilla(10));
                    System.out.println("El jugador " + jugador.getNombre() + " ha sacado " + dado1 + " y " + dado2 + ", avanzando " + total + " posiciones.");
                    System.out.println("El jugador " + jugador.getNombre() + " ha sacado dobles 3 veces seguidas, va a la carcel.");
                    jugador.setTurnosCarcel(3);
                    GrupoIrCarcel.irACarcel(tablero,jugador,tablero.getCasilla(0).getGrupoSalida().getCantidadPorVuelta());
                }else{
                    total = dado1 + dado2;
                    System.out.println("El jugador " + jugador.getNombre() + " ha sacado " + dado1 + " y " + dado2 + ", avanzando " + total + " posiciones.");
                    moverJugador(jugador, total, this.tablero);
                    Casilla.realizarAccionesCasilla(this,jugador.getCasillaDeJugador(), jugador);
                }

            }
        }

    }

    //MÉTODO LISTAR PROPIEDADES EN VENTA
    public void listarPropiedadesVenta(){

        //Variables
        int i = 0;
        Casilla casillaAux;

        //Iteramos por todas las casillas "Solar", "Transporte" y "Servicios"
        for (i=0; i<40; i++){
            casillaAux = tablero.getCasilla(i);

            //SOLARES
            if (casillaAux.getGrupoCasilla().equals("Solar")){

                //CHEQUEAMOS PROPIETARIO
                if (casillaAux.getGrupoSolar().getPropietario().getNombre().equals("Banca")){

                    //LISTAMOS:
                    System.out.println("{\n\ttipo: " + casillaAux.getGrupoCasilla() + ",\n\tcolor: " +
                            casillaAux.getGrupoSolar().getColorSolar() + ",\n\tvalor: " + casillaAux.getGrupoSolar().getValor() +
                            ",\n},\n");
                }
            }
            //TRANSPORTES
            if (casillaAux.getGrupoCasilla().equals("Transporte")){

                //CHEQUEAMOS PROPIETARIO
                if (casillaAux.getGrupoTransporte().getPropietario().getNombre().equals("Banca")){

                    //LISTAMOS:
                    System.out.println("{\n\ttipo: " + casillaAux.getGrupoCasilla() + ",\n\tvalor: " +
                            casillaAux.getGrupoTransporte().getValor() +
                            ",\n},\n");
                }
            }
            //SERVICIOS
            if (casillaAux.getGrupoCasilla().equals("Servicios")){

                //CHEQUEAMOS PROPIETARIO
                if (casillaAux.getGrupoServicios().getPropietario().getNombre().equals("Banca")){

                    //LISTAMOS:
                    System.out.println("{\n\ttipo: " + casillaAux.getGrupoCasilla() + ",\n\tvalor: " +
                            casillaAux.getGrupoServicios().getValor() +
                            ",\n},\n");
                }
            }
        }
    }

    //MÉTODO DESCRIBIR UNA CASILLA:
    public void describirCasilla(){


        //Variables
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        Casilla casillaAux = null;
        String nombreCasilla;
        String stringAux = "";   //Auxiliar, para los jugadores que estén en la casilla.

        //Leemos el nombre de la casilla a describir:
        System.out.println("\nIntroduce el nombre de la casilla a describir: ");
        nombreCasilla = scanner.next();

        //Encontramos el numero de casilla:
        for (i = 0; i < 41; i++){

            //Si i == 40, ya habremos chequeado todas las casillas y no estará:
            if (i == 40){
                System.out.println("\nLa casilla introducida no existe.");
                return;
            }

            //Si coincide el nombre de una de las casillas:
            if (tablero.getCasilla(i).getNombreCasilla().equals(nombreCasilla)){
                casillaAux = tablero.getCasilla(i);
                break;
            }

        }

        //Encontramos jugadores en esa casilla
        ArrayList<Jugador> arrayJugadores = new ArrayList<>(6);
        for (i = 0; i < numeroJugadores; i++){

            //Si la casilla coincide con alguno de los jugadores lo añadimos al array:
            if (jugadores.get(i).getCasillaDeJugador().equals(casillaAux)){
                arrayJugadores.add(jugadores.get(i));
            }

        }

        //Si hay jugadores se escribe:
        if (!arrayJugadores.isEmpty()){

            //Ponemos las comas, tabs, lineas del string:
            stringAux = ",\n\tjugadores: [";

            //Hacemos el bucle para añadir los jugadores al string:
            for (i=0; i < arrayJugadores.size()-1; i++){
                stringAux = stringAux + arrayJugadores.get(i).getNombre() + ", ";
                if (casillaAux.getGrupoCasilla().equals("Carcel")){
                    stringAux = stringAux + arrayJugadores.get(i).getTurnosCarcel() + ", ";
                }
            }
            stringAux = stringAux + arrayJugadores.get(i).getNombre();

            //Ponemos el fin del string:
            stringAux = stringAux + "]";
        }


        //Chequeamos para cada tipo de casillas:
        //SOLAR
        if (casillaAux.getGrupoCasilla().equals("Solar")){

            //DESCRIBIMOS:
            System.out.println("{\n\ttipo: " + casillaAux.getGrupoCasilla() + ",\n\tcolor: " +
                    casillaAux.getGrupoSolar().getColorSolar() + ",\n\tpropietario: " +
                    casillaAux.getGrupoSolar().getPropietario().getNombre() + ",\n\tvalor: " + casillaAux.getGrupoSolar().getValor()
                    + ",\n\talquiler: " + casillaAux.getGrupoSolar().getAlquiler() + stringAux + ",\n},\n");
        }

        //SERVICIOS
        if (casillaAux.getGrupoCasilla().equals("Servicios")){

            //DESCRIBIMOS:
            System.out.println("{\n\ttipo: " + casillaAux.getGrupoCasilla() + ",\n\tpropietario: " +
                    casillaAux.getGrupoServicios().getPropietario().getNombre() + ",\n\tvalor: " + casillaAux.getGrupoServicios().getValor()
                    + stringAux + ",\n},\n");

        }


        //TRANSPORTES
        if (casillaAux.getGrupoCasilla().equals("Transporte")){

            //DESCRIBIMOS:
            System.out.println("{\n\ttipo: " + casillaAux.getGrupoCasilla() + ",\n\tpropietario: " +
                    casillaAux.getGrupoTransporte().getPropietario().getNombre() + ",\n\tvalor: " + casillaAux.getGrupoTransporte().getValor()
                    + ",\n\talquiler: " + casillaAux.getGrupoTransporte().getAlquiler() + stringAux  + ",\n},\n");

        }

        //PARKING
        if (casillaAux.getGrupoCasilla().equals("Parking")){

            //DESCRIBIMOS:
            System.out.println("{\n\ttipo: " + casillaAux.getGrupoCasilla() + ",\n\tbote: " + GrupoImpuesto.getMultaAcumulada()
                    + stringAux  + ",\n},\n");

        }

        //SALIDA
        if (casillaAux.getGrupoCasilla().equals("Salida")){

            //DESCRIBIMOS:
            System.out.println("{\n\ttipo: " + casillaAux.getGrupoCasilla() + ",\n\tdinero a recibir: " + casillaAux.getGrupoSalida().getCantidadPorVuelta()
                    + stringAux  + ",\n},\n");

        }

        //IMPUESTOS
        if (casillaAux.getGrupoCasilla().equals("Impuestos")){

            //DESCRIBIMOS:
            System.out.println("{\n\ttipo: " + casillaAux.getGrupoCasilla() + ",\n\ta pagar: " + casillaAux.getGrupoImpuesto().getMulta()
                    + stringAux  + ",\n},\n");

        }

        //CARCEL
        if (casillaAux.getGrupoCasilla().equals("Carcel")){

            //DESCRIBIMOS:      //FALTA PONER LOS JUGADORES QUE ESTAN EN LA CARCEL - ??ATRIBUTO DE JUGADOR??
            System.out.println("{\n\ttipo: " + casillaAux.getGrupoCasilla() + stringAux  + ",\n},\n");
        }















    }

    //MÉTODO DESCRIBIR UN JUGADOR
    public void describirJugador(){

        //Variables
        Scanner scanner = new Scanner(System.in);
        Jugador jugadorAux = null;
        String stringAux;
        int encontrado = 0;

        //Preguntamos si se quiere describir un Jugador
        System.out.println("\nIntroduce el nombre del jugador: ");
        stringAux = scanner.nextLine();

        //Lo buscamos:
        int i = 0;
        for (i = 0; i < getNumeroJugadores(); i++){

            //Chequeamos
            if (jugadores.get(i).getNombre().equals(stringAux)){

                //Nuestro jugadorAux apuntará al jugador a describir:
                jugadorAux = jugadores.get(i);
                //Si lo encontramos ponemos encontrado a 1:
                encontrado = 1;
            }

        }

        //Si no existe imprimimos error
        if (encontrado == 0){
            System.out.println("No se ha encontrado el jugador introducido.");
            return;
        }

        //Si existe llamamos al método toString e imprimimos
        System.out.println(jugadorAux.toStringListar());
    }

    //MISMO MÉTODO PERO SOBRECARGADO
    public void describirJugador(String stringAux){

        //Variables
        Jugador jugadorAux = null;
        int encontrado = 0;
        int i = 0;

        //Lo buscamos:

        for (i = 0; i < getNumeroJugadores(); i++){

            //Chequeamos
            if (jugadores.get(i).getNombre().equals(stringAux)){

                //Nuestro jugadorAux apuntará al jugador a describir:
                jugadorAux = jugadores.get(i);
                //Si lo encontramos ponemos encontrado a 1:
                encontrado = 1;
            }

        }

        //Si no existe imprimimos error
        if (encontrado == 0){
            System.out.println("No se ha encontrado el jugador introducido.");
            return;
        }

        //Si existe llamamos al método toString e imprimimos
        System.out.println(jugadorAux.toStringListar());
    }

    //MÉTODO DESCRIBIR AVATAR:
    public void describirAvatar(){

        //Variables
        Scanner scanner = new Scanner(System.in);
        Avatar avatarAux = null;
        char charAux;
        int encontrado = 0;

        //Preguntamos si se quiere describir un Jugador
        System.out.println("\nIntroduce el id del avatar (sin &): ");
        charAux = scanner.next().charAt(0);

        //Lo buscamos:
        int i = 0;
        for (i = 0; i < getNumeroJugadores(); i++){
            //Chequeamos avatar
            if (jugadores.get(i).getAvatar().getId() == (charAux)){

                //Nuestro jugadorAux apuntará al jugador a describir:
                avatarAux = jugadores.get(i).getAvatar();

                //Si lo encontramos ponemos encontrado a 1:
                encontrado = 1;
            }

        }

        //Si no existe imprimimos error
        if (encontrado == 0){
            System.out.println("No se ha encontrado el avatar introducido.");
            return;
        }

        //Si existe llamamos al método toString e imprimimos
        System.out.println(avatarAux.toStringListar());
    }

    //MÉTODO DESCRIBIR AVATAR SOBRECARGADO:
    public void describirAvatar(char charAux){

        //Variables
        Avatar avatarAux = null;
        int encontrado = 0;

        //Lo buscamos:
        int i = 0;
        for (i = 0; i < getNumeroJugadores(); i++){
            //Chequeamos avatar
            if (jugadores.get(i).getAvatar().getId() == (charAux)){

                //Nuestro jugadorAux apuntará al jugador a describir:
                avatarAux = jugadores.get(i).getAvatar();

                //Si lo encontramos ponemos encontrado a 1:
                encontrado = 1;
            }

        }

        //Si no existe imprimimos error
        if (encontrado == 0){
            System.out.println("No se ha encontrado el avatar introducido.");
            return;
        }

        //Si existe llamamos al método toString e imprimimos
        System.out.println(avatarAux.toStringListar());
    }

    //MÉTODO LISTAR JUGADORES
    public void listarJugadores(){

        //Variables
        int i = 0;
        String stringaux = "";

        //Hacemos un bucle que pase como argumento el nombre de cada jugador:
        for (i = 0; i < getNumeroJugadores(); i++){
            //Accedemos al nombre de cada jugador:
            stringaux = getJugadores().get(i).getNombre();
            //Pasamos el nombre a la función describirJugador:
            describirJugador(stringaux);
        }
    }

    //MÉTODO LISTAR AVATARES
    public void listarAvatares(){

        //Variables
        int i = 0;
        char charAux;

        //Hacemos un bucle que pase como argumento el nombre de cada jugador:
        for (i = 0; i < getNumeroJugadores(); i++){
            //Accedemos al nombre de cada jugador:
            charAux = getJugadores().get(i).getAvatar().getId();
            //Pasamos el nombre a la función describirJugador:
            describirAvatar(charAux);
        }
    }
}
