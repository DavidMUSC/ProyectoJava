package paquetePartida;

import java.util.ArrayList;
import java.util.Scanner;
import paqueteCasillas.Casilla;
import paqueteCasillas.Tablero;

public class Jugador {

    //ATRIBUTOS
    private Casilla casilla;
    private String nombre;
    private Avatar avatar; //El id lo sacaremos desde este avatar.
    private int dinero; //cuando el dinero sea negativo el jugador entra en bancarrota
    private ArrayList<Casilla> propiedades; //ATRIBUTO PROPIEDADES COMPRADAS
    private int TurnosCarcel;
    private String[] hipotecas; //ATRIBUTO HIPOTECAS
    private String[] edificios; //ATRIBUTO EDIFICIOS
    private boolean tieneCartaSuerte = false;

    //CASILLA ACTUAL NO HACE FALTA PORQ YA ESTÁ EN AVATAR
    //TIPO AVATAR TAMPOCO HACE FALTA PORQ YA ESTÁ EN AVATAR


    //GETTERS
    public Casilla getCasillaDeJugador(){
        return this.casilla;
    }
    public String getNombre(){
        return this.nombre;
    }
    public int getDinero(){
        return this.dinero;
    }
    public String[] getHipotecas() {
        return hipotecas;
    }
    public String[] getEdificios() {return edificios;
    }
    public Avatar getAvatar(){
        return this.avatar;
    }
    public boolean getTieneCartaSuerte() {
        return tieneCartaSuerte;
    }

    public int getTurnosCarcel() {
        return TurnosCarcel;
    }

    //Getter compuesto para obtener el tipo de avatar.
    public String getAvatarTipo(){
        return getAvatar().getTipoAvatar();
    }

    public ArrayList<Casilla> getPropiedades() {
        return propiedades;
    }

    //SETTERS

    public void setCasillaDeJugador(Casilla casilla) {
        this.casilla = casilla;
    }

    public void setNombre(String newNombre){
        this.nombre= newNombre;
    }

    public void setDinero(int newDinero) {
        this.dinero = newDinero;
    }

    public void setTurnosCarcel(int turnosCarcel) {
        TurnosCarcel = turnosCarcel;
    }

    //La creación del objeto avatar se hace en el main, aquí solo hace el set:
    public void setAvatar(Avatar newAvatar){
        avatar = newAvatar;
    }

    public void setPropiedades(ArrayList<Casilla> propiedades) {
        this.propiedades = propiedades;
    }

    //CONSTRUCTOR
    //Cada vez que creemos un jugador se creará también un avatar.
    //Al crear un jugador, le pasamos la casilla inicial y se la asignará
    //a su avatar.
    public Jugador(String nombre, String piezaAvatar, Casilla casilla) {
        setCasillaDeJugador(casilla);
        setNombre(nombre);
        setAvatar(new Avatar(this, casilla, piezaAvatar));
        setDinero((int)((Tablero.getValorSumaSolares())/3));
        propiedades = new ArrayList<Casilla>(22);
    }

    //PARA CREAR EL JUGADOR ESPECIAL BANCA PODEMOS HACER UNA SOBRECARGA DE CONSTRUCTORES
    //ALTERNATIVA: TAMBIÉN PODEMOS CREARLO CON UN MÉTODO ESPECIAL EN ESTADO PARTIDA
    public Jugador(String nombre){
        if (nombre.equals("Banca")){

            setNombre("Banca");
            setDinero(9999999);
            propiedades = new ArrayList<Casilla>(22);


        }
        else{
            System.out.println("Formato de constructor incorrecto.");
        }
    }

    // Método para pagar a otro jugador o entidad
    public void pagar(int monto, Jugador destinatario) {
        if (dinero >= monto) {
            // El jugador tiene suficiente dinero para realizar el pago
            dinero -= monto;
            destinatario.recibirPago(monto);
            System.out.println(nombre + " ha pagado " + monto + " a " + destinatario.getNombre());
        } else {
            // El jugador no tiene suficiente dinero y se declara en bancarrota
            System.out.println(nombre + " no tiene suficiente dinero para realizar el pago a " + destinatario.getNombre() + " y se declara en bancarrota.");
            // Implementa la lógica para declarar en bancarrota si es necesario
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

    public void noDinero(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(nombre + ", no tienes dinero suficiente");

        System.out.println("Elige una opción:");
        System.out.println("1. Hipotecar propiedades");
        System.out.println("2. Declararte en bancarrota");
        //Volver al menu de casillaCamprables
        System.out.println("3. Volver al menú");
        int opcion = scanner.nextInt();

        switch (opcion){
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
    public void anadirPropiedad(Casilla casilla){

        propiedades.add(casilla);
    }

    //CAMBIAR LA CASILLA DEL AVATAR
    public void cambiarCasillaAvatar(Casilla casilla){
        getAvatar().setCasilla(casilla);
    }

    //?ESPROPIETARIO? - Chequea si una determinada casilla es tuya
    public int getNumPropiedades(){
        return propiedades.size();
    }

    //OTROS MÉTODOS - //Este método servira cuando nos pidan listar jugadores
    public String toStringListar(){

        //Declaramos variable:
        String stringAux = "";

        //String auxiliar para guardar las propiedades:
        if (!getPropiedades().isEmpty()){

            //Ponemos las comas, tabs, lineas del string:
            stringAux = ",\n\tpropiedades: [";

            //Hacemos el bucle para añadir propiedades al string:
            int i = 0;
            for (i=0; i < getPropiedades().size()-1; i++){
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

    public String indicarJugador(){     //Este método sirve cuando necesitamos indicar un jugador, solo devuelve nombre y avatar-
        return ("{\n\tnombre: " + getNombre() + ",\n\tavatar: " + getAvatar().getId() + "\n}");
    }
}
