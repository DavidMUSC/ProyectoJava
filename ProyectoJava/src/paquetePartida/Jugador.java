package paquetePartida;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import paqueteCasillas.Casilla;
import paqueteCasillas.GrupoSalida;
import paqueteCasillas.Tablero;
import paqueteCasillas.GrupoSolar;
import paqueteCasillas.Edificaciones;

public class Jugador {

    //ATRIBUTOS
    private Casilla casilla;
    private String nombre;
    private Avatar avatar; //El id lo sacaremos desde este avatar.
    private int dinero; //cuando el dinero sea negativo el jugador entra en bancarrota
    private ArrayList<Casilla> propiedades; //ATRIBUTO PROPIEDADES COMPRADAS
    private int TurnosCarcel;
    private ArrayList<String> monopolioColor;
    private ArrayList<Integer> casillasPasadas;
    private HashMap<String, Edificaciones> edificacionesMonopolio;
    private String[] hipotecas; //ATRIBUTO HIPOTECAS
    private String[] edificios; //ATRIBUTO EDIFICIOS
    private boolean tieneCartaSuerte = false;
    private int vueltasCompletadas;
    private int indiceDados;

    //CASILLA ACTUAL NO HACE FALTA PORQ YA ESTÁ EN AVATAR
    //TIPO AVATAR TAMPOCO HACE FALTA PORQ YA ESTÁ EN AVATAR


    //GETTERS
    public Casilla getCasillaDeJugador() {
        return this.casilla;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getDinero() {
        return this.dinero;
    }

    public String[] getHipotecas() {
        return hipotecas;
    }

    public String[] getEdificios() {
        return edificios;
    }

    public Avatar getAvatar() {
        return this.avatar;
    }

    public boolean getTieneCartaSuerte() {
        return tieneCartaSuerte;
    }

    public int getTurnosCarcel() {
        return TurnosCarcel;
    }

    public int getVueltasCompletadas(){
        return vueltasCompletadas;
    }
    
    public int getIndiceDados(){
        return indiceDados;
    }

    public ArrayList<String> getMonopolioColor(){
        return monopolioColor;
    }

    public ArrayList<Integer> getCasillasPasadas(){
        return casillasPasadas;
    }

    //Getter compuesto para obtener el tipo de avatar.
    public String getAvatarTipo() {
        return getAvatar().getTipoAvatar();
    }

    public ArrayList<Casilla> getPropiedades() {
        return propiedades;
    }


    //SETTERS

    public void setCasillaDeJugador(Casilla casilla) {
        this.casilla = casilla;
    }

    public void setNombre(String newNombre) {
        this.nombre = newNombre;
    }

    public void setDinero(int newDinero) {
        this.dinero = newDinero;
    }

    public void setTurnosCarcel(int turnosCarcel) {
        TurnosCarcel = turnosCarcel;
    }
    
    public void setVueltasCompletadas(int vueltasCompletadas){this.vueltasCompletadas = vueltasCompletadas;}

    //La creación del objeto avatar se hace en el main, aquí solo hace el set:
    public void setAvatar(Avatar newAvatar) {
        avatar = newAvatar;
    }

    public void setPropiedades(ArrayList<Casilla> propiedades) {
        this.propiedades = propiedades;
    }

    public void setMonopolioColor(ArrayList<String> monopolioColor){
        this.monopolioColor = monopolioColor;
    }
    public void setIndiceDados(int indiceDados){
        this.indiceDados = indiceDados;
    }


    //CONSTRUCTOR
    //Cada vez que creemos un jugador se creará también un avatar.
    //Al crear un jugador, le pasamos la casilla inicial y se la asignará
    //a su avatar.
    public Jugador(String nombre, String piezaAvatar, Casilla casilla) {
        setCasillaDeJugador(casilla);
        setNombre(nombre);
        setAvatar(new Avatar(this, casilla, piezaAvatar));
        setDinero((int) ((Tablero.getValorSumaSolares()) / 3));
        propiedades = new ArrayList<Casilla>(22);
        monopolioColor = new ArrayList<String>(8);
        casillasPasadas = new ArrayList<Integer>(40);
        inicializarCasillasPasadas();
        setIndiceDados(0);
        setVueltasCompletadas(0);
    }

    //PARA CREAR EL JUGADOR ESPECIAL BANCA PODEMOS HACER UNA SOBRECARGA DE CONSTRUCTORES
    //ALTERNATIVA: TAMBIÉN PODEMOS CREARLO CON UN MÉTODO ESPECIAL EN ESTADO PARTIDA
    public Jugador(String nombre) {
        if (nombre.equals("Banca")) {

            setNombre("Banca");
            setDinero(9999999);
            propiedades = new ArrayList<Casilla>(22);


        } else {
            System.out.println("Formato de constructor incorrecto.");
        }
    }

    // Método para recibir un pago
    public void recibirPago(int monto) {
        dinero += monto;
    }



    // Método para verificar si el jugador tiene una carta de suerte
    public boolean tieneCartaSuerte() {
        return tieneCartaSuerte;
    }

    // Método para usar una carta de suerte
    public void usarCartaSuerte() {
        if (tieneCartaSuerte) {
            tieneCartaSuerte = false;
            System.out.println(nombre + " ha usado una carta de suerte.");
            // Puedes implementar la lógica específica de la carta de suerte aquí
        } else {
            System.out.println(nombre + " no tiene cartas de suerte para usar.");
        }
    }

    public void noDinero() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(nombre + ", no tienes dinero suficiente");

        System.out.println("Elige una opción:");
        System.out.println("1. Hipotecar propiedades");
        System.out.println("2. Declararte en bancarrota");
        //Volver al menu de casillaCamprables
        System.out.println("3. Volver al menú");
        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                System.out.println(nombre + "ha elegido hipotecar sus propiedades.");
                System.out.println("Este método no está implementado.");
                System.exit(0);
                // añadir cosas
                break;
            case 2:
                System.out.println(nombre + "se ha declarado en bancarrota.");
                //IMprimir que esta opcion no esta implementada y salir del programa
                System.exit(0);
                break;
            case 3:
                System.out.println(nombre + "ha vuelto al menú.");
                return;
            default:
                System.out.println("Opción no válida.");

        }
    }

    //AÑADIR PROPIEDAD
    public void anadirPropiedad(Casilla casilla) {

        propiedades.add(casilla);
    }

    //CAMBIAR LA CASILLA DEL AVATAR
    public void cambiarCasillaAvatar(Casilla casilla) {
        getAvatar().setCasilla(casilla);
    }

    //?ESPROPIETARIO? - Chequea si una determinada casilla es tuya
    public int getNumPropiedades() {
        return propiedades.size();
    }

    //OTROS MÉTODOS - //Este método servira cuando nos pidan listar jugadores
    public String toStringListar() {

        //Declaramos variable:
        String stringAux = "";

        //String auxiliar para guardar las propiedades:
        if (!getPropiedades().isEmpty()) {

            //Ponemos las comas, tabs, lineas del string:
            stringAux = ",\n\tpropiedades: [";

            //Hacemos el bucle para añadir propiedades al string:
            int i = 0;
            for (i = 0; i < getPropiedades().size() - 1; i++) {
                stringAux = stringAux + getPropiedades().get(i).getNombreCasilla() + ", ";
            }
            stringAux = stringAux + getPropiedades().get(i).getNombreCasilla();

            //Ponemos el fin del string:
            stringAux = stringAux + "]";
        }

        String string = ("{\n\tnombre: " + getNombre() + ",\n\tavatar: " + getAvatar().getId() + ",\n\tfortuna :" +
                getDinero() + stringAux + ",\n}\n");


        return string;
    }

    public String indicarJugador() {     //Este método sirve cuando necesitamos indicar un jugador, solo devuelve nombre y avatar-
        return ("{\n\tnombre: " + getNombre() + ",\n\tavatar: " + getAvatar().getId() + "\n}");
    }

    //FUNCION PARA SABER UN JUGADOR TIENE UN MONOPOLIO
    public int calcularMonopolio(){

        //Variable hayMonopolio:
        int hayMonopolio = 0;

        //Inicializamos contadores para cada grupo color:
        int negro = 0; //MAX 2
        int cian = 0; //MAX 3
        int rosa = 0; //MAX 3
        int amarillo = 0; //MAX 3
        int rojo = 0; //MAX 3
        int marron = 0; //MAX 3
        int verde = 0; //MAX 3
        int azul = 0; //MAX 2


        //Iteramos en las propiedades del jugador
        int i = 0;
        for (i = 0; i < propiedades.size(); i++){
            //Comprobar que en las propiedades del jugador hay un grupo solar
            if(propiedades.get(i).getGrupoCasilla().equals("Solar")){

                //Tomamos el color del solar:
                String color = propiedades.get(i).getGrupoSolar().getColorSolar();

                //Switch para aumentar el contador del color correspondiente:
                switch (color){

                    case "Negro":
                        negro++;
                        break;
                    case "Cian":
                        cian++;
                        break;
                    case "Rosa":
                        rosa++;
                        break;
                    case "Amarillo":
                        amarillo++;
                        break;
                    case "Rojo":
                        rojo++;
                        break;
                    case "Marron":
                        marron++;
                        break;
                    case "Verde":
                        verde++;
                        break;
                    case "Azul":
                        azul++;
                        break;
                }
            }
        }

        //Una vez tenemos todos los contadores actualizados, iteramos para ver si existe el monopolio:

        //NEGRO
        if (negro == 2){
            if (!monopolioColor.contains("Negro")){
                monopolioColor.add("Negro");
                hayMonopolio = 1;
            }
        }

        //CIAN
        if (cian == 3){
            if(!monopolioColor.contains("Cian")){
                monopolioColor.add("Cian");
                hayMonopolio = 1;
            }

        }

        //ROSA
        if (rosa == 3){
            if (!monopolioColor.contains("Rosa")){
                monopolioColor.add("Rosa");
                hayMonopolio = 1;
            }
        }

        //AMARILLO
        if (amarillo == 3){
            if (!monopolioColor.contains("Amarillo")){
                monopolioColor.add("Amarillo");
                hayMonopolio = 1;
            }
        }

        //ROJO
        if (rojo == 3){
            if (!monopolioColor.contains("Rojo")){
                monopolioColor.add("Rojo");
                hayMonopolio = 1;
            }
        }

        //MARRON
        if (marron == 3){
            if (!monopolioColor.contains("Marron")){
                monopolioColor.add("Marron");
                hayMonopolio = 1;
            }
        }

        //VERDE
        if (verde == 3){
            if (!monopolioColor.contains("Verde")){
                monopolioColor.add("Verde");
                hayMonopolio = 1;
            }
        }

        //AZUL
        if (azul == 2){
            if (!monopolioColor.contains("Azul")){
                monopolioColor.add("Azul");
                hayMonopolio = 1;
            }
        }
        //Devolvemos 1 si se actualiza el monopolio
        return hayMonopolio;
    }

    public void incrementarVueltasCompletadas(){
        setVueltasCompletadas(getVueltasCompletadas()+1);
    }

    public int incrementarIndiceDados(int valor){
        setIndiceDados(getIndiceDados()+valor);
        return getIndiceDados();
    }

    //MÉTODO PARA INICIALIZAR LAS CASILLAS PASADAS A CERO
    public void inicializarCasillasPasadas(){
        for (int i = 0; i < 40; i++){
            casillasPasadas.add(0);
        }
    }

    //MÉTODO PARA ACTUALIZAR LAS CASILLAS PASADAS
    public void actualizarCasillasPasadas(Casilla casilla){
        int indiceCasilla = casilla.getNumCasilla();
        //ArrayList de int con las casillas pasadas

        int valor = casillasPasadas.get(indiceCasilla);
        casillasPasadas.set(indiceCasilla, valor+1);
    }

    //MÉTODO PARA COMPROBAR SI UNA CASILLA PASADA ESTÁ CON EL VALOR DOS (ES DECIR, SI SE HA PASADO DOS VECES POR ELLA)
    public boolean comprobarCasillaPasada(Casilla casilla){
        int indiceCasilla = casilla.getNumCasilla();
        int valor = casillasPasadas.get(indiceCasilla);
        if (valor == 2){
            return true;
        }
        else{
            return false;
        }
    }

    //MÉTODO PARA VER SI PUEDES CONTRUIR
    public boolean puedesConstruir(Casilla casilla){
        if(casilla.getGrupoSolar()!=null){
            //Comprobar si el jugador tiene el monopolio del grupoSolar
            if (monopolioColor.contains(casilla.getGrupoSolar().getColorSolar())){
                System.out.println("Puedes construir en esta casilla porque tienes el monopolio del grupo solar de color " + casilla.getGrupoSolar().getColorSolar());
                return true;
            }
            //comprobar casilla pasada y si es propietario de la casilla
            else if(comprobarCasillaPasada(casilla) && casilla.getGrupoSolar().getPropietario().equals(this)){
                System.out.println("Puedes construir en esta casilla porque eres el propietario de la casilla y has pasado dos veces por ella");
                return true;
            }
            else{
                System.out.println("No puedes construir en esta casilla");
                return false;
            }
        }else{
            return false;
        }
    }
}