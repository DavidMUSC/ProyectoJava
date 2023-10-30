package paqueteCasillas;

public class GrupoCajaComunidad {

    //ATRIBUTOS
    private Casilla casilla;

    //GETTERS
    public Casilla getCasilla() {
        return casilla;
    }

    //SETTERS
    public void setCasilla(Casilla casilla) {
        this.casilla = casilla;
    }

    //CONSTRUCTOR
    public GrupoCajaComunidad(Casilla casilla){
        setCasilla(casilla);
    }

    //METODO AUXILIAR
    public void auxCajaComunidad(){
        System.out.print("Estas en la casilla CAJA DE COMUNIDAD. No hay opciones implementadas\n");
    }

}
