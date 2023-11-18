package paquetePartida;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import paqueteCasillas.*;

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
        int numJugadoresAux;
        System.out.print("Inserta el numero de jugadores:");
        while (true){

            if (scanner.hasNextInt()){
                numJugadoresAux = scanner.nextInt();
                break;
            }
            else {
                System.out.print("Por favor, introduce un número válido.");
                scanner.next();
            }
        }
        // Creamos n jugadores
        int i = 0;
        for (i = 0; i < numJugadoresAux; i++){
            nuevoJugador();
        }
        iterarTurnos(jugadores,turno);
    }

    //METODO PARA ITERAR TURNOS
    public void iterarTurnos(ArrayList<Jugador> jugadores, Turno turno){
        Jugador jugador;
        int contador = 0, comprobacionEliminados=0;
        ArrayList<Jugador> jugadoresAux =  new ArrayList<>(getNumeroJugadores());
        jugadoresAux.addAll(jugadores);
        while(true){
                jugador= jugadores.get(contador);
                if (jugador.getTurnosCarcel()!=0){
                    GrupoIrCarcel.irACarcel(this,jugador,this.getTablero().getCasilla(0).getGrupoSalida().getCantidadPorVuelta());
                }else{
                    Menu.menuModularizado(this,jugador,"menuPreLanzarDados", false);
                }
                comprobacionEliminados=jugadorSiguiente(jugador,jugadoresAux);
                if(comprobacionEliminados!=1){
                    contador++;
                }

                if (contador==getNumeroJugadores()){
                    contador=0;
                    turno.actualizarTurno();
                }
        }

    }

    //METODO PARA IMPRIMIR EL JUGADOR SIGUIENTE
    public  int jugadorSiguiente(Jugador jugador, ArrayList<Jugador> jugadoresAux){
        int posicion = getJugadores().indexOf(jugador);
        if(posicion==-1){
            //comprobar si se encuentra en el arrayAux
            posicion = jugadoresAux.indexOf(jugador);
            //Imprimir el jugador de esa posición del arrayOriginal
            System.out.println("El siguiente jugador es " + jugadores.get(posicion).getNombre());
            return 1;

        }else if(posicion == getNumeroJugadores()-1){
            System.out.println("El siguiente jugador es " + jugadores.get(0).getNombre());
            return 0;
        }
        else{
            System.out.println("El siguiente jugador es " + jugadores.get(posicion+1).getNombre());
            return 0;
        }

    }

    //MÉTODO PARA COMPROBAR SI UN JUAGADOR HA GANADO LA PARTIDA
    public void ganarPartida(){
        if (numeroJugadores==1){
            System.out.println("El jugador " + jugadores.get(0).getNombre() + " ha ganado la partida. ¡¡¡ENHORABUENA POR LA VICTORIA!!!");
            //salir de la ejecucion
            System.exit(10);
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
    public static void moverJugador(Jugador jugador, int casillas, EstadoPartida estadoPartida){

        //Variables
        int posicionActual = jugador.getCasillaDeJugador().getNumCasilla();
        int posicionNueva = (posicionActual + casillas) % 40;
        boolean cuatroVueltas = false;

        jugador.setCasillaDeJugador(estadoPartida.getTablero().getCasilla(posicionNueva));
        //MOVER AVATAR EN EL TABLERO A LA NUEVA CASILLA
        jugador.cambiarCasillaAvatar(estadoPartida.getTablero().getCasilla(posicionNueva));
        if(jugador.getCasillaDeJugador().getGrupoSolar()!=null){
            jugador.actualizarCasillasPasadas(jugador.getCasillaDeJugador());
        }
        //ACTUALIZO EL INDICE DE LOS DADOS
        int indiceDados = jugador.incrementarIndiceDados(casillas);
        if (indiceDados>=40){
            estadoPartida.getTablero().getCasilla(0).getGrupoSalida().completarVuelta(jugador);
            cuatroVueltas = comprobarVueltasCompletadas(estadoPartida);
            if(cuatroVueltas){
                estadoPartida.getTablero().getCasilla(0).getGrupoSalida().aumentarPreciosCompraSolares(jugador,estadoPartida.getTablero());
                cuatroVueltas = false;
            }
        }

    }

    //MÉTODO PARRA CHECKEAR SI TODOS LOS JUGADORES TIENES CUATRO VUELTAS COMPLETADAS. EN EL CASO DE QUE LAS TENGA DEVUELVE TRUE
    public  static boolean comprobarVueltasCompletadas(EstadoPartida estadoPartida){

        //Variables
        int contador = 0;

        //Iteramos por todos los jugadores
        for (int i = 0; i < estadoPartida.numeroJugadores; i++){

            //Si el jugador tiene 4 vueltas completadas aumentamos el contador
            if (estadoPartida.jugadores.get(i).getVueltasCompletadas() == 4){
                contador++;
            }
        }

        //Si el contador es igual al numero de jugadores devolvemos true
        if (contador == estadoPartida.numeroJugadores){
            //imprimir que todos los jugadores han completado 4 vueltas
            System.out.println("Todos los jugadores han completado 4 vueltas al tablero.");
            //poner todos los jugadores a 0 vueltas completadas
            for (int i = 0; i < estadoPartida.numeroJugadores; i++){
                estadoPartida.jugadores.get(i).setVueltasCompletadas(0);
            }
            return true;
        }
        else{
            return false;
        }
    }

    //METODO PARA FILTRAR EL TIPO DE MOVIMIENTO
    public void filtrarTipoMovimiento(Jugador jugador,String identificadorMovimiento,String identificadorTipoAvatar,String identificadorAdministrador){
        if(identificadorMovimiento.equals("basico")){
            if(identificadorAdministrador.equals("dados"))
                tirarDadosBasico(jugador);
            else if(identificadorAdministrador.equals("admin"))
                tirarDadosBasicoAdmin(jugador);
        }
        else if(identificadorMovimiento.equals("avanzado")){
            if(identificadorTipoAvatar.equals("pelota")){
                //Movimiento avanzado pelota
                if(identificadorAdministrador.equals("dados"))
                    tirarDadosPelota(jugador);
                else if(identificadorAdministrador.equals("admin")){
                    tirarDadosPelotaAdmin(jugador);
                }
            }
            else if(identificadorTipoAvatar.equals("coche")){
                //Movimiento avanzado coche
                if(identificadorAdministrador.equals("dados"))
                    tirarDadosCoche(jugador);
                else if(identificadorAdministrador.equals("admin")){
                    tirarDadosCocheAdmin(jugador);
                }
            }
        }else
            System.out.println("No se ha introducido un tipo de movimiento valido");
    }

    private void tirarDadosBasicoAdmin(Jugador jugador) {
        //pedir el numero de posiciones para moverse
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el numero de posiciones que quieres moverte:");
        int numeroPosiciones = scanner.nextInt();
        //Actualiza veces dados
        jugador.actualizarVecesDados();
        //Mover avatar
        moverJugador(jugador, numeroPosiciones, this);
        Casilla.realizarAccionesCasilla(this,jugador.getCasillaDeJugador(), jugador);

    }

    //MÉTODO LANZARDADO
    public static int lanzarDado() {
        return (int) (Math.random() * 6) + 1;
    }

    //MÉTODO TIRAR DADOS
    public int[] tirarDados(){
        //Variables
        int total;
        int dado1,dado2;
        int[] arrayDados = new int[3];

        //Leer input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pulsa enter para tirar los dados");
        scanner.nextLine();

        dado1 = EstadoPartida.lanzarDado();
        arrayDados[0] = dado1;
        dado2 = EstadoPartida.lanzarDado();
        arrayDados[1] = dado2;
        total = dado1 + dado2;
        arrayDados[2] = total;
        return arrayDados;
    }

    //método para comprobar si son dobles
    public boolean comprobarDobles(int dado1, int dado2){
        return dado1 == dado2;
    }

    //MÉTODO TIRAR DADOS PELOTA ADMIN: EN VEZ DE TIRAR DADOS SE PUEDE ELEGIR EL NUMERO QUE SE QUIERA
    public void tirarDadosPelotaAdmin(Jugador jugador){
        int total;
        int vecesCarcel=jugador.getVecesCarcel();

        //Leer input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el numero de dado que quieres sacar:");
        total = scanner.nextInt();
        //Actualiza veces dados
        jugador.actualizarVecesDados();

        //si total es >4 se avanza en el tablero las posiciones que indique total parando en las impares
        if(total>4) {
            System.out.println("El jugador " + jugador.getNombre() + " ha sacado " + total + ", avanzando " + total + " posiciones.");
            System.out.println("Como posee el avatar de tipo Pelota avanzará 4 posiciones y luego parará en las posiciones impares hasta " + total + " posiciones.");

            moverJugador(jugador, 4, this);
            //Comprobar los numeros impares que hay ebtre 4 y total
            for (int i = 5; i <= total; i++) {
                //si es impar se para
                moverJugador(jugador, 1, this);
                if (i % 2 != 0) {
                    Casilla.realizarAccionesCasilla(this, jugador.getCasillaDeJugador(), jugador);
                    if(jugador.getVecesCarcel()!=vecesCarcel){
                        total=0;
                        jugador.setComproCasillaTurno(0);
                        return;
                    }
                }
            }
        }
    }

    public void tirarDadosCocheAdmin(Jugador jugador){
        int total=5;
        int numeroVeces= 0;
        int vecesCarcel=jugador.getVecesCarcel();

        //si el total es >4 puede volver a tirar
        while (total > 4) {
            //Leer input
            Scanner scanner = new Scanner(System.in);
            System.out.println("Introduce el numero de dado que quieres sacar:");
            total = scanner.nextInt();
            System.out.println("El jugador " + jugador.getNombre() + " ha sacado " + total + ", avanzando " + total + " posiciones.");
            numeroVeces++;
            //Actualiza veces dados
            jugador.actualizarVecesDados();
            //Mover avatar
            moverJugador(jugador, total, this);
            Casilla.realizarAccionesCasilla(this, jugador.getCasillaDeJugador(), jugador);

            //Comprobar si el jugador ha caído en el casilla 30. Si ha caido romper el bucle
            if(jugador.getVecesCarcel()!=vecesCarcel){
                total=0;
                jugador.setComproCasillaTurno(0);
                return;
            }

            if (total > 4 && numeroVeces < 3) {
                System.out.println("El jugador " + jugador.getNombre() + " ha sacado mas de un 4 por lo que puede seguir moviéndose gracias al movimiento del avatar tipo Coche.");
            }else if (total < 4){
                System.out.println("Se ha detenido el movimiento del avatar tipo Coche porque el jugador ha sacado un 4 o menos.");
                total=0;
                jugador.setComproCasillaTurno(0);
            }else{
                System.out.println("Se ha detenido el movimiento del avatar tipo Coche porque el jugador ha sacado mas de un 4 3 veces seguidas");
                total=0;
                jugador.setComproCasillaTurno(0);
            }
        }
    }

    //MÉTODO TIRAR DADOS PELOTA
    public void tirarDadosPelota(Jugador jugador){
        int total;
        int dado1;
        int dado2;
        int[] arrayDados= new int[3];
        int vecesCarcel=jugador.getVecesCarcel();

        //Tirar dados en la primera tirada
        arrayDados= tirarDados();
        dado1 = arrayDados[0];
        dado2 = arrayDados[1];
        total = arrayDados[2];
        //Actualiza veces dados
        jugador.actualizarVecesDados();

        //si total es >4 se avanza en el tablero las posiciones que indique total parando en las impares
        if(total>4){
            System.out.println("El jugador " + jugador.getNombre() + " ha sacado " + dado1 + " y " + dado2 + ", avanzando " + total + " posiciones.");
            System.out.println("Como posee el avatar de tipo Pelota avanzará 4 posiciones y luego parará en las posiciones impares hasta " + total + " posiciones.");

            moverJugador(jugador, 4, this);
            //Comprobar los numeros impares que hay ebtre 4 y total
            for(int i=5;i<=total;i++){
                //si es impar se para
                moverJugador(jugador, 1, this);
                if(i%2!=0){
                    Casilla.realizarAccionesCasilla(this,jugador.getCasillaDeJugador(), jugador);
                    if(jugador.getVecesCarcel()!=vecesCarcel){
                        total=0;
                        jugador.setComproCasillaTurno(0);
                        return;
                    }
                }
            }


        }else{
            //si total es <4 se retrocede en el tablero las posiciones que indique total parando en las impares
            System.out.println("El jugador " + jugador.getNombre() + " ha sacado " + dado1 + " y " + dado2 + ", retrocediendo " + total + " posiciones.");
            System.out.println("Como posee el avatar de tipo Pelota retrocederá " + total + " posiciones parando en las posiciones impares.");

            int i=total;
            //Comprobar los numeros impares que hay ebtre 4 y total
            while(i!=0){
                //si es impar se para
                moverJugador(jugador, -1, this);
                if(i==3 || i==1){
                    Casilla.realizarAccionesCasilla(this,jugador.getCasillaDeJugador(), jugador);
                    if(jugador.getVecesCarcel()!=vecesCarcel){
                        total=0;
                        jugador.setComproCasillaTurno(0);
                        return;
                    }
                }
                i--;
            }
        }
    }

    //MÉTODO TIRAR DADOS COCHE
    public void tirarDadosCoche(Jugador jugador) {
        int total=5;
        int dado1;
        int dado2;
        int[] arrayDados= new int[3];
        int numeroVeces= 0, vecesCarcel=jugador.getVecesCarcel();



        //si el total es >4 puede volver a tirar
        while (total > 4) {
            //Tirar dados en la primera tirada
            arrayDados = tirarDados();
            dado1 = arrayDados[0];
            dado2 = arrayDados[1];
            total = arrayDados[2];
            //Actualiza veces dados
            jugador.actualizarVecesDados();
            System.out.println("El jugador " + jugador.getNombre() + " ha sacado " + dado1 + " y " + dado2 + ", avanzando " + total + " posiciones.");
            //Auemntamos el numero de veces que ha tirado dados
            numeroVeces++;

            //Mover avatar
            moverJugador(jugador, total, this);
            Casilla.realizarAccionesCasilla(this, jugador.getCasillaDeJugador(), jugador);

            if(jugador.getVecesCarcel()!=vecesCarcel){
                total=0;
                jugador.setComproCasillaTurno(0);
                return;
            }
            if (total > 4 && numeroVeces < 3) {
                System.out.println("El jugador " + jugador.getNombre() + " ha sacado mas de un 4 por lo que puede seguir moviéndose gracias al movimiento del avatar tipo Coche.");
            }else if (total < 4){
                System.out.println("Se ha detenido el movimiento del avatar tipo Coche porque el jugador ha sacado un 4 o menos.");
                total=0;
                jugador.setComproCasillaTurno(0);
            }else{
                System.out.println("Se ha detenido el movimiento del avatar tipo Coche porque el jugador ha sacado mas de un 4 3 veces seguidas");
                total=0;
                jugador.setComproCasillaTurno(0);
            }
        }


    }
    //MÉTODO PARA TIRAR DADOS
    public void tirarDadosBasico(Jugador jugador) {
        int total;
        int dado1;
        int dado2;
        int[] arrayDados= new int[3];

        //Tirar dados en la primera tirada
        arrayDados= tirarDados();
        dado1 = arrayDados[0];
        dado2 = arrayDados[1];
        total = arrayDados[2];
        //Actualiza veces dados
        jugador.actualizarVecesDados();
        System.out.println("El jugador " + jugador.getNombre() + " ha sacado " + dado1 + " y " + dado2 + ", avanzando " + total + " posiciones.");
        //Mover avatar
        moverJugador(jugador, total, this);

        Casilla.realizarAccionesCasilla(this,jugador.getCasillaDeJugador(), jugador);

        //COMPROBACION DE SI SALEN DOBLES VOLVER A TIRAR, REALIZAR ACCIONES CASILLA Y SI SALE TERCERA VEZ IR A LA CARCEL
        if(comprobarDobles(dado1,dado2)){
            //Printear que el jugador saco dobles
            System.out.println("El jugador " + jugador.getNombre() + " ha sacado dobles. Por lo tanto su turno vuelve comenzar.");

            //Tirar dados en la segunda tirada
            arrayDados = tirarDados();
            dado1 = arrayDados[0];
            dado2 = arrayDados[1];
            total = arrayDados[2];
            //Actualiza veces dados
            jugador.actualizarVecesDados();

            System.out.println("El jugador " + jugador.getNombre() + " ha sacado " + dado1 + " y " + dado2 + ", avanzando " + total + " posiciones.");
            moverJugador(jugador, total, this);
            Casilla.realizarAccionesCasilla(this,jugador.getCasillaDeJugador(), jugador);
            if(comprobarDobles(dado1,dado2)){
                //Printear que el jugador saco dobles
                System.out.println("El jugador " + jugador.getNombre() + " ha sacado dobles por segunda vez. Por lo tanto su turno vuelve comenzar.");

                //tira dados en la tercera tirada
                arrayDados = tirarDados();
                dado1 = arrayDados[0];
                dado2 = arrayDados[1];
                total = arrayDados[2];
                //Actualiza veces dados
                jugador.actualizarVecesDados();

                if(comprobarDobles(dado1,dado2)){
                    jugador.setTurnosCarcel(3);
                    jugador.setCasillaDeJugador(tablero.getCasilla(10));
                    jugador.cambiarCasillaAvatar(tablero.getCasilla(10));
                    System.out.println("El jugador " + jugador.getNombre() + " ha sacado " + dado1 + " y " + dado2 + ", avanzando " + total + " posiciones.");
                    System.out.println("El jugador " + jugador.getNombre() + " ha sacado dobles 3 veces seguidas, va a la carcel.");
                    jugador.setTurnosCarcel(3);
                    GrupoIrCarcel.irACarcel(this,jugador,tablero.getCasilla(0).getGrupoSalida().getCantidadPorVuelta());
                }else{
                    System.out.println("El jugador " + jugador.getNombre() + " ha sacado " + dado1 + " y " + dado2 + ", avanzando " + total + " posiciones.");
                    moverJugador(jugador, total, this);
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
                    + ",\n\talquiler: " + casillaAux.getGrupoSolar().getAlquilerActualizado() + ",\n\tedificios: " +
                    casillaAux.getGrupoSolar().getEdificaciones().getIds() + stringAux + ",\n},\n");
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

    //Método para listar los edificios construidos
    public void listarEdificiosConstruidos(){

        //Variables
        int i = 0;

        //Vamos a iterar por todas las casillas GrupoSolar llamando a la función aux: listarEdificios1Casilla
        for (i=0; i<40; i++){
            //Accedemos solo a las casillas que sean GrupoSolar
            if (tablero.getCasilla(i).getGrupoCasilla().equals("Solar")){
                //Accedemos a sus edificaciones e invocamos al método auxiliar:
                tablero.getCasilla(i).getGrupoSolar().getEdificaciones().listarEdificaciones1Casilla();
            }

        }

    }

    //Método para listar los edificios construidos en un grupo pasado el color:
    public void listarEdificiosGrupoAux(String color){

        //Variables
        int i = 0;
        int j = 0;

        //Variable para almacenar casas construidas ( - si no hay):
        ArrayList<String> casas = new ArrayList<>(4);

        //Variable para almacenar hoteles construidos ( - si no hay):
        ArrayList<String> hoteles = new ArrayList<>(4);

        //Variable para almacenar piscinas construidas ( - si no hay)
        ArrayList<String> piscinas = new ArrayList<>(4);

        //Variable para almacenar pistas de deportes construidas ( - si no hay)
        ArrayList<String> pistasDeporte = new ArrayList<>(4);

        //Vamos a iterar por todas las casillas GrupoSolar llamando a la función aux: listarEdificios1Casilla
        for (i=0; i<40; i++){
            //Accedemos solo a las casillas que sean GrupoSolar
            if (tablero.getCasilla(i).getGrupoCasilla().equals("Solar")){

                //Chequeamos el color del GrupoSolar:
                if (tablero.getCasilla(i).getGrupoSolar().getColorSolar().equals(color)){

                    //Variable auxiliar para mejorar legibilidad:
                    GrupoSolar solarAux = tablero.getCasilla(i).getGrupoSolar();

                    //Almacenamos las casas, hoteles, piscinas y pistas:
                    for (j=0; j<solarAux.getEdificaciones().getIds().size(); j++){

                        //Casa
                        if (solarAux.getEdificaciones().getIds().get(j).startsWith("casa-")){
                            casas.add(solarAux.getEdificaciones().getIds().get(j));
                        }

                        //Casa
                        if (solarAux.getEdificaciones().getIds().get(j).startsWith("hotel-")){
                            hoteles.add(solarAux.getEdificaciones().getIds().get(j));
                        }

                        //Casa
                        if (solarAux.getEdificaciones().getIds().get(j).startsWith("piscina-")){
                            piscinas.add(solarAux.getEdificaciones().getIds().get(j));
                        }

                        //Casa
                        if (solarAux.getEdificaciones().getIds().get(j).startsWith("deporte-")){
                            pistasDeporte.add(solarAux.getEdificaciones().getIds().get(j));
                        }
                    }

                    //Imprimimos propiedades de esa casilla:
                    //Nombre, casas, hoteles, piscinas, pistas de deporte, alquiler.
                    System.out.println("{\n\tpropiedad: " + solarAux.getCasilla().getNombreCasilla() +
                            ",\n\tcasas: " + casas + ",\n\thoteles: " + hoteles + ",\n\tpiscinas: " +
                            piscinas + ",\n\tpistas de deporte: " + pistasDeporte + ",\n\talquiler: " +
                            solarAux.getAlquilerActualizado() + "\n}\n");

                    //Imprimir propiedades que todavía se pueden construir:
                    solarAux.getEdificaciones().esMaxEdificacionesAmpliado("casa");
                    solarAux.getEdificaciones().esMaxEdificacionesAmpliado("hotel");
                    solarAux.getEdificaciones().esMaxEdificacionesAmpliado("piscina");
                    solarAux.getEdificaciones().esMaxEdificacionesAmpliado("pistaDeportes");

                }
            }
            //"Vaciamos" los ArrayList usados:
            casas.clear();
            hoteles.clear();
            piscinas.clear();
            pistasDeporte.clear();
        }

    }

    //Método para listar los edificios de un grupo, menú para elegir:
    public void listarEdificiosGrupo(){

        //Variables
        int opcion;

        //Preguntamos el color al usuario:
        System.out.println("Selecciona el color a listar: ");
        System.out.println("1. Negro");
        System.out.println("2. Cian");
        System.out.println("3. Rosa");
        System.out.println("4. Amarillo");
        System.out.println("5. Rojo");
        System.out.println("6. Marron");
        System.out.println("7. Verde");
        System.out.println("8. Azul");
        Scanner scanner = new Scanner(System.in);
        opcion = scanner.nextInt();

        switch (opcion){
            case 1:
                listarEdificiosGrupoAux("Negro");
                break;
            case 2:
                listarEdificiosGrupoAux("Cian");
                break;
            case 3:
                listarEdificiosGrupoAux("Rosa");
                break;
            case 4:
                listarEdificiosGrupoAux("Amarillo");
                break;
            case 5:
                listarEdificiosGrupoAux("Rojo");
                break;
            case 6:
                listarEdificiosGrupoAux("Marron");
                break;
            case 7:
                listarEdificiosGrupoAux("Verde");
                break;
            case 8:
                listarEdificiosGrupoAux("Azul");
                break;
        }

    }

    //Método para saber cual es la casilla en la que más veces se ha caído
    public String casillaMasFrecuentada(){
        int max=0;
        String nombreCasilla="-";
        int indice;
        //Comprobar con la funcion vecesCasillaMasFrecuentada cual es la casilla
        for(int i=0; i< getNumeroJugadores();i++){
            if(max<jugadores.get(i).vecesCasillaMasPasada()){
                max=jugadores.get(i).vecesCasillaMasPasada();
                indice=jugadores .get(i).indiceCasillaMasPasada();
                nombreCasilla=getTablero().getCasilla(indice).getNombreCasilla();
            }
        }
        return nombreCasilla;
    }

    //Método para saber cual es el jugador con m´sa vueltas
    public String jugadorMasVueltas(){
        int max=0;
        String nombreJugador="-";
        //Comprobar con la funcion vecesCasillaMasFrecuentada cual es la casilla
        for(int i=0; i< getNumeroJugadores();i++){
            if(max<jugadores.get(i).getVueltasCompletadas()){
                max=jugadores.get(i).getVueltasCompletadas();
                nombreJugador=jugadores.get(i).getNombre();
            }
        }
        return nombreJugador;
    }

    //Método para saber qu ejugador ha sacado más veces los dados
    public String jugadorMasVecesDados(){
        int max=0;
        String nombreJugador="-";
        //Comprobar con la funcion vecesCasillaMasFrecuentada cual es la casilla
        for(int i=0; i< getNumeroJugadores();i++){
            if(max<jugadores.get(i).getVecesDados()){
                max=jugadores.get(i).getVecesDados();
                nombreJugador=jugadores.get(i).getNombre();
            }
        }
        return nombreJugador;
    }

    //Método para saber el jugador en cabeza
    public String jugadorEnCabeza(){
        //Comprobar quien es el jugador que tiene mayor fortunaTotal
        int max=0;
        String nombreJugador="-";
        for(int i=0; i< getNumeroJugadores();i++){
            jugadores.get(i).calcularFortunaTotal();
        }
        for(int i=0; i< getNumeroJugadores();i++){
            if(max<jugadores.get(i).getFortunaTotal()){
                max=jugadores.get(i).getFortunaTotal();
                nombreJugador=jugadores.get(i).getNombre();
            }
        }
        return nombreJugador;
    }

    //Método para elegir jugador para las estadísticas
    public void estadisticasJugador(EstadoPartida estadoPartida){
        //preguntar jugador del que se quieren saber las estadísticas
        System.out.println("Introduce el nombre del jugador del que quieres saber las estadisticas:");
        Scanner scanner = new Scanner(System.in);
        String nombreJugador = scanner.nextLine();
        //Comprobar que el jugador existe
        int i = 0;
        for(i=0;i<numeroJugadores;i++){
            if(jugadores.get(i).getNombre().equals(nombreJugador)){
                estadisticasJugadorAux(jugadores.get(i));
                return;
            }
        }
        System.out.println("El jugador introducido no existe");
    }


    //Método para hallar las estadísticas de un jugador
    void estadisticasJugadorAux(Jugador jugador){
        //imprimir dinero invertido
        System.out.println("Dinero invertido: " + jugador.getDineroInvertido());
        //imprimir pago de tasas e impuestos
        System.out.println("Pago de tasas e impuestos: " + jugador.getPagoTasasEImpuestos());
        //imprimir pagoAlquileres
        System.out.println("Pago de alquileres: " + jugador.getPagoAlquileres());
        //imprimir cobroAlquileres
        System.out.println("Cobro de alquileres: " + jugador.getCobroAlquileres());
        //imprimir pasarPorCasillaDeSalida
        System.out.println("Dinero recibido por pasar por la casilla de salida: " + jugador.getPasarPorCasillaSalida());
        //imprimir PremiosInversionesOBote
        System.out.println("Premios, inversiones y bote: " + jugador.getCobroPremiosOBote());
        //imprimir vecesEnLaCarcel
        System.out.println("Veces en la carcel: " + jugador.getVecesCarcel());
    }

    //ESTADISTICAS PARTIDA
    void estadisticasPartida(){
        //Llamamos a la función casilla más rentable
        Casilla casillaAux = tablero.casillaMasRentable();
        if(casillaAux!=null){
            System.out.println("casillaMasRentable: " + casillaAux.getNombreCasilla());
        }else{
            System.out.println("casillaMasRentable: -");
        }

        //Llamamos al función grupo más rentable
        String stringAux = tablero.grupoMasRentable();
        if(stringAux!=null){
            System.out.println("grupoMasRentable: " + stringAux);
        }else{
            System.out.println("grupoMasRentable: -");
        }
        //Llamamos a la función casillaMasFrecuentada
        String nombreCasilla = casillaMasFrecuentada();
        System.out.println("casillaMasFrecuentada: " + nombreCasilla);
        //Llamamos a la función jugadorMasVueltas
        String nombreJugador = jugadorMasVueltas();
        System.out.println("jugadorMasVueltas: " + nombreJugador);
        //Llamamos a la función jugadorMasVecesDados
        String nombreJugador2 = jugadorMasVecesDados();
        System.out.println("jugadorMasVecesDados: " + nombreJugador2);
        //Llamamos a la funcion jugador en cabeza
        String nombreJugador3 = jugadorEnCabeza();
        System.out.println("jugadorEnCabeza: " + nombreJugador3);
    }




}
