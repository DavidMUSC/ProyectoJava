package paquetePartida;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Scanner;
import paqueteCasillas.Casilla;
import paqueteCasillas.Tablero;

public class Avatar {

    //ATRIBUTOS
    private char id; //Será un char aleatorio
    private String tipoAvatar; //El tipo de pieza
    private Casilla casilla;
    private Jugador jugador; //El jugador que controla el avatar.
    private static Set<Character> assignedIds = new HashSet<>();

    //GETTERS:
    public char getId() {
        return id;
    }
    public String getTipoAvatar() {
        return tipoAvatar;
    }
    public Casilla getCasilla() { //Este getter no deberia ser como el de tablero?
        return casilla;
    }
    public Jugador getJugador() {
        return jugador;
    }
    //Getter de assignedIds no es necesario ya que es atributo estático.


    //SETTERS
    //setId NO LO PONDREMOS: tenemos que asegurarnos de que sea aleatorio, por lo que
    // lo hacemos con un método auxiliar.
    //setTipoAvatar tiene que checkear que sea uno de las piezas existentes:
    public void setTipoAvatar(String tipoAvatar) {
        if ("coche".equals(tipoAvatar) | "sombrero".equals(tipoAvatar)
                || "esfinge".equals(tipoAvatar) || "pelota".equals(tipoAvatar)) {
            this.tipoAvatar = tipoAvatar;}
        else{
            System.out.println("Tipo de avatar no válido.");
        }
    }
    //setCasilla no hace falta chequearlo porque se le pasa un objeto casilla.
    public void setCasilla(Casilla casilla) {
        this.casilla = casilla;
    }
    //setJugador no hace falta chequearlo porque se le pasa un objeto jugador.
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }


    //CONSTRUCTOR
    //Sólo se usará el constructor de Avatar al inicio de la partida:
    public Avatar(Jugador jugador, Casilla casilla, String tipoAvatar){
        assignRandomId();
        setJugador(jugador);
        setTipoAvatar(tipoAvatar);
        setCasilla(casilla);
    }


    //OTROS MÉTODOS
    //Método assignRandomId para asignar una ID random y no repetida:
    private void assignRandomId() {
        Random random = new Random();
        char randomId;
        do {
            randomId = (char) (random.nextInt(26) + 'A'); // Genera una letra mayúscula aleatoria
        } while (assignedIds.contains(randomId));

        assignedIds.add(randomId);
        this.id = randomId;
    }
    //Método toStringListar():
    public String toStringListar(){
        String avatarString;
        avatarString = ("{\n\tid: " + getId() + ",\n "
                + "\ttipo: " + getTipoAvatar() + ",\n"
                + "\tcasilla: " + getCasilla().getNombreCasilla() + ",\n"
                + "\tjugador: " + getJugador().getNombre() + ",\n"
                + "}");

        return avatarString;
    }

    //Método para preguntar tipo de movimiento si el tipo del avatar es coche o pelota
    public String preguntarTipoMovimiento(){
        if(getTipoAvatar().equals("coche")||getTipoAvatar().equals("pelota")){
            System.out.println("¿Qué tipo de movimiento quieres hacer?");
            System.out.println("1. Movimiento basico");
            System.out.println("2. Movimiento avanzado");
            Scanner scanner = new Scanner(System.in);
            int opcion = scanner.nextInt();
            if(opcion == 1){
                //Movimiento basico
                return "basico";
            }
            else if(opcion == 2){
                //Movimiento avanzado
                return "avanzado";
            }
            else{
                System.out.println("Opción no válida");
            }
        }
        return "basico";
    }
}
