package paqueteCasillas;

import paquetePartida.Jugador;

public class GrupoParking {


    //ATRIBUTOS
    private Casilla casilla;
    private int bote; //El jugador recibe toda la cantidad de dinero recaudada en Impuestos

    //GETTERS
    public Casilla getCasilla(){return casilla;}
    public int getBote(){return bote;}

    //SETTERS
    public void setCasilla(Casilla casilla){this.casilla = casilla;}
    public void setBote(int bote) {
        this.bote = bote;
    }

    //CONSTRUCTOR
    public GrupoParking(Casilla casilla){
        setCasilla(casilla);
        setBote(0);

    }

    //MÉTODO PARA HALLAR EL VALOR DEL BOTE. A partir del atributo multaAcumulada de GrupoImpuesto
    public void valorBote(){
        setBote(GrupoImpuesto.getMultaAcumulada());
    }
    //METODO PARA COBRAR EL BOTE-> EL QUE HAY QUE USAR CUANDO SE LLEGUE A LA CASILLA
    public void cobrarBote(Jugador jugador){
        //Halla el valor del bote
        valorBote();
        //Checkeo de si el bote es distinto de 0
        if (getBote()!=0){
            //Imprimir el precio del bote
            System.out.println("Has recibido un bote de: " + getBote()+" €. ENHORABUENA!!!");
            jugador.setDinero(jugador.getDinero() + getBote());
            //estadísticas premios o bote
            jugador.setCobroPremiosOBote(jugador.getCobroPremiosOBote() + getBote());
            ponerACeroMultaAcumulada();
            setBote(0);
        }else{
            System.out.println("No hay bote acumulado. Una pena...");
        }
    }
    //MÉTODO PARA PONER A CERO multaAcumulada
    public void ponerACeroMultaAcumulada(){
        GrupoImpuesto.setMultaAcumulada(0);
    }

}
