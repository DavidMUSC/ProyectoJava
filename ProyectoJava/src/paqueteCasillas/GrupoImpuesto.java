package paqueteCasillas;

import paquetePartida.EstadoPartida;
import paquetePartida.Jugador;

public class GrupoImpuesto {

    //ATRIBUTOS
    private Casilla casilla;
    private int multa; //Debe ser igual a cantidadPorVuelta en una y otra la mitad
    static private int multaAcumulada;

    //GETTERS
    public Casilla getCasilla(){return casilla;}
    public int getMulta(){return multa;}
    public static int getMultaAcumulada() {
        return multaAcumulada;
    }

    //SETTERS
    public void setCasilla(Casilla casilla){this.casilla = casilla;}
    public void setMulta(int multa){
        if (multa < 0){
            System.out.println("Error, la multa no puede ser negativa.");
        }
        this.multa = multa;
    }
    public static void setMultaAcumulada(int multaAcumuladaNew){
        multaAcumulada = multaAcumuladaNew;
    }

    //CONSTRUCTOR
    public GrupoImpuesto(Casilla casilla,int cantidadPorVuelta){
        setCasilla(casilla);
        multaAcumulada=0;
        int multa_aux = calcularMulta(cantidadPorVuelta);
        if (casilla.getNombreCasilla().equals("Imp1")){
            setMulta(multa_aux/2);
        }
        else if (casilla.getNombreCasilla().equals("Imp2")){
            setMulta(multa_aux);
        }
    }

    //OTROS MÉTODOS
    //MÉTODO PARA CALCULAR MULTA Debe ser igual a cantidadPorVuelta en una y otra la mitad
    public int calcularMulta(int cantidadPorVuelta){
        setMulta(cantidadPorVuelta);
        return getMulta();
    }

    //MÉTODO PARA COBRAR LA MULTA
    public void cobrarMulta(EstadoPartida estadoPartida,int multa, Jugador jugador){
        //Imprimir el precio de la multa
        System.out.println("El precio de la multa es: " + multa);
        //checkeo de si puede pagar o no
        if(jugador.getDinero() - multa < 0){
            jugador.noDinero();
        }else{
            //estadisticas PagoTasasEImpuestos
            jugador.setPagoTasasEImpuestos(jugador.getPagoTasasEImpuestos() + multa);
            jugador.setDinero(jugador.getDinero() - multa);
            contabilizarMulta(multa);
            //Imprimir que ha pagado la multa y el dinero que le queda
            System.out.println("Has pagado la multa y te quedan " + jugador.getDinero() + " €");
        }
    }

    //MÉTODO PARA CONTABILIZAR LA MULTA CADA VEZ QUE SE USA cobrarMulta
    public void contabilizarMulta(int multa){
        multaAcumulada= (multaAcumulada + multa);
    }

}
