package paqueteCasillas;

import paquetePartida.EstadoPartida;
import paquetePartida.Jugador;
import paquetePartida.Menu;

public class Casilla {

    //Atributos
    private String nombreCasilla;
    private int numCasilla;
    private String grupoCasilla;
    private GrupoSolar grupoSolar;
    private GrupoServicios grupoServicios;
    private GrupoTransporte grupoTransporte;
    private GrupoIrCarcel grupoIrCarcel;
    private GrupoCajaComunidad grupoCajaComunidad;
    private GrupoSalida grupoSalida;
    private GrupoSuerte grupoSuerte;
    private GrupoParking grupoParking;
    private GrupoImpuesto grupoImpuesto;
    private String colorSolar;

    //Getters
    public String getNombreCasilla() {
        return this.nombreCasilla;
    }
    public int getNumCasilla() {
        return this.numCasilla;
    }
    public String getGrupoCasilla() {
        return this.grupoCasilla;
    }
    public GrupoIrCarcel getGrupoIrCarcel() {
        return grupoIrCarcel;
    }
    public GrupoCajaComunidad getGrupoCajaComunidad() {
        return grupoCajaComunidad;
    }
    public GrupoSalida getGrupoSalida() {
        return grupoSalida;
    }
    public GrupoSolar getGrupoSolar() {
        return grupoSolar;
    }
    public GrupoServicios getGrupoServicios() {
        return grupoServicios;
    }
    public GrupoTransporte getGrupoTransporte(){
        return grupoTransporte;
    }
    public GrupoImpuesto getGrupoImpuesto() {
        return grupoImpuesto;
    }
    public GrupoParking getGrupoParking() {
        return grupoParking;
    }
    public GrupoSuerte getGrupoSuerte(){
        return grupoSuerte;
    }
    public String getColorSolar(){
        return colorSolar;
    }

    //Setters
    void setNombreCasilla(String newNombre) {
        this.nombreCasilla = newNombre;
    }
    void setNumCasilla(int newNumCasilla) {
        this.numCasilla = newNumCasilla;
    }
    void setGrupoCasilla(String grupocasilla) {
        this.grupoCasilla = grupocasilla;
    }
    //No habrá Setters de los Grupos de casillas ni en el grupoSolar ya que se hacen en sus respectivos constructores.

    //Constructor
    public Casilla(String nombreCasilla, int numCasilla, String tipocasilla) {
        setNombreCasilla(nombreCasilla);
        setNumCasilla(numCasilla);
        setGrupoCasilla(tipocasilla);

        //GRUPOS (solar tiene su propio constructor)º
        if (tipocasilla.equals("IrCarcel")) {
            this.grupoIrCarcel = new GrupoIrCarcel(this);
        }
        if (tipocasilla.equals("CajaComunidad")) {
            this.grupoCajaComunidad = new GrupoCajaComunidad(this);
        }
        if (tipocasilla.equals("Suerte")) {
            this.grupoSuerte = new GrupoSuerte(this);
        }


        if (tipocasilla.equals("Parking")) {
            this.grupoParking = new GrupoParking(this);
        }
    }
    //Sobrecarga del constructor para solar:
    public Casilla(String nombreCasilla, int numCasilla, String tipocasilla, String colorSolar) {

        setNombreCasilla(nombreCasilla);
        setNumCasilla(numCasilla);
        setGrupoCasilla(tipocasilla);

        //GRUPOS
        if (tipocasilla.equals("Solar")) {
            this.colorSolar = colorSolar;
            this.grupoSolar = new GrupoSolar(this, colorSolar);
        }
    }
    //Sobrecarga del constructor para salida, transporte, servicios e impuestos:
    public Casilla(String nombreCasilla, int numCasilla, String tipocasilla, int valor) {

        setNombreCasilla(nombreCasilla);
        setNumCasilla(numCasilla);
        setGrupoCasilla(tipocasilla);

        //GRUPOS
        //SALIDA
        if (tipocasilla.equals("Salida")) {
            this.grupoSalida = new GrupoSalida(this, valor);
        }

        if (tipocasilla.equals("Transporte")) {
            this.grupoTransporte = new GrupoTransporte(this, valor);
        }

        if (tipocasilla.equals("Servicios")) {
            this.grupoServicios = new GrupoServicios(this, valor);
        }

        if (tipocasilla.equals("Impuestos")) {
            this.grupoImpuesto = new GrupoImpuesto(this, valor);
        }

    }

    // Otros Métodos
    // Método para realizar acciones específicas en casillas de propiedad
    public static void realizarAccionesCasilla(EstadoPartida estadoPartida,Casilla casilla, Jugador jugador) {

        boolean propietarioCheck = false;


        //COMPROBAR SI LA CASILLA ES DEL TIPO SOLAR
        if(casilla.getGrupoCasilla().equals("Solar")) {

            //printeamos el nombre de la casilla
            System.out.println("Has caído en la casilla " + casilla.getNombreCasilla() + ".");
            Jugador propietario = casilla.getGrupoSolar().getPropietario();

            //COMPROBAR SI LA CASILLA TIENE PROPIETARIO SI LO TIENE SE PAGA EL ALQUILER
            if ((!propietario.getNombre().equals("Banca")) && (!propietario.equals(jugador))) {
                casilla.getGrupoSolar().pagarAlquiler(jugador);
                //Printear la cantidad que se ha pagado y a quien se ha pagado
                System.out.println("Has pagado " + casilla.getGrupoSolar().getAlquiler() + " a " + propietario.getNombre() + ".");
                propietarioCheck= true;
            }
            else if(propietario == jugador){
                propietarioCheck= true;
                System.out.println("Eres el propietario de esta casilla.");
            }
            //IMPRIMIR MENU CASILLAS COMPRABLES
            Menu.menuCasillasComprables(estadoPartida, jugador, propietarioCheck);
        }
        //COMPROBAR SI LA CASILLA ES DEL TIPO TRANSPORTE
        if(casilla.getGrupoCasilla().equals("Transporte")) {

            //printeamos el nombre de la casilla
            System.out.println("Has caído en la casilla " + casilla.getNombreCasilla() + ".");
            Jugador propietario = casilla.getGrupoTransporte().getPropietario();

            //COMPROBAR SI LA CASILLA TIENE PROPIETARIO SI LO TIENE SE PAGA EL ALQUILER
            if ((!propietario.getNombre().equals("Banca")) && (!propietario.equals(jugador))) {
                //Actualizamos el valor del alquiler:
                casilla.getGrupoTransporte().setAlquiler(casilla.getGrupoTransporte().calcularAlquiler(estadoPartida.getTablero().getCasilla(0).getGrupoSalida().getValorSumaSolares()));
                casilla.getGrupoTransporte().cobrarAlquiler(jugador);
                //Printear la cantidad que se ha pagado y a quien se ha pagado
                System.out.println("Has pagado " + casilla.getGrupoTransporte().getAlquiler() + " a " + propietario.getNombre() + ".");
                propietarioCheck= true;
            }
            else if(propietario == jugador){
                propietarioCheck= true;
                System.out.println("Eres el propietario de esta casilla.");
            }
            //IMPRIMIR MENU CASILLAS COMPRABLES
            Menu.menuCasillasComprables(estadoPartida, jugador, propietarioCheck);
        }
        //COMPROBAR SI LA CASILLA ES DEL TIPO SERVICIOS
        if(casilla.getGrupoCasilla().equals("Servicios")) {

            //printeamos el nombre de la casilla
            System.out.println("Has caído en la casilla " + casilla.getNombreCasilla() + ".");
            Jugador propietario = casilla.getGrupoServicios().getPropietario();

            //COMPROBAR SI LA CASILLA TIENE PROPIETARIO SI LO TIENE SE PAGA EL ALQUILER
            if ((!propietario.getNombre().equals("Banca")) && (!propietario.equals(jugador))) {
                casilla.getGrupoServicios().cobrarAlquiler(jugador);
                //Printear la cantidad que se ha pagado y a quien se ha pagado
                System.out.println("Has pagado " + casilla.getGrupoServicios().getAlquiler() + " a " + propietario.getNombre() + ".");
                propietarioCheck= true;
            }
            else if(propietario == jugador){
                propietarioCheck= true;
                System.out.println("Eres el propietario de esta casilla.");
            }
            //IMPRIMIR MENU CASILLAS COMPRABLES
            Menu.menuCasillasComprables(estadoPartida, jugador, propietarioCheck);
        }
        //COMPROBAR SI LA CASILLA ES DEL TIPO IMPUESTO
        if(casilla.getGrupoCasilla().equals("Impuestos")){
            //PRINTEAMOS EL NOMBRE DE LA CASILLA
            System.out.println("Has caído en la casilla " + casilla.getNombreCasilla() + ".");
            //PAGAR MULTA
            casilla.getGrupoImpuesto().cobrarMulta(casilla.getGrupoImpuesto().getMulta(), jugador);
            Menu.menuCasillasNoComprables(estadoPartida, jugador);
        }

        //COMPROBAR SI LA CASILLA ES DEL TIPO PARKING
        if(casilla.getGrupoCasilla().equals("Parking")){
            //PRINTEAMOS EL NOMBRE DE LA CASILLA
            System.out.println("Has caído en la casilla " + casilla.getNombreCasilla() + ".");
            //COBRAR BOTE
            casilla.getGrupoParking().cobrarBote(jugador);
            Menu.menuCasillasNoComprables(estadoPartida, jugador);
        }

        //COMPROBAR SI LA CASILLA ES DEL TIPO SALIDA, CAJA COMUNIDAD O SUERTE
        if(casilla.getGrupoCasilla().equals("Salida") || casilla.getGrupoCasilla().equals("CajaComunidad") || casilla.getGrupoCasilla().equals("Suerte")|| casilla.getNumCasilla()==10){
            //PRINTEAMOS EL NOMBRE DE LA CASILLA
            System.out.println("Has caído en la casilla " + casilla.getNombreCasilla() + ".");
            //IMPRIMIR MENU CASILLAS NO COMPRABLES
            Menu.menuCasillasNoComprables(estadoPartida, jugador);
        }

        //COMPROBAR SI LA CASILLA ES DEL TIPO IR A LA CARCEL
        if(casilla.getGrupoCasilla().equals("IrCarcel")){
            //PRINTEAMOS EL NOMBRE DE LA CASILLA
            System.out.println("Has caído en la casilla " + casilla.getNombreCasilla() + ".");
            jugador.setTurnosCarcel(3);
            //IR A LA CARCEL
            GrupoIrCarcel.irACarcel(estadoPartida.getTablero(),jugador,estadoPartida.getTablero().getCasilla(0).getGrupoSalida().getCantidadPorVuelta());
        }
    }
}






