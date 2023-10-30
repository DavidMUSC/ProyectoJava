package paqueteCasillas;

public class GrupoSuerte {

    //Atributos
    private Casilla casilla;

    //Getters
    public Casilla getCasilla(){
        return this.casilla;
    }

    //Setters
    public void setCasilla(Casilla newCasilla){
        this.casilla = newCasilla;
    }


    //Constructor
    GrupoSuerte(Casilla casilla){
        setCasilla(casilla);
    }







    //METODO AUXILIAR
    public void auxGrupoSuerte(){
        System.out.print("Estas en la casilla suerte. No hay opciones implementadas");
    }
}
//IGUAL DEBEMOS MOVER LAS FUNCIONES DE GRUPO IRCARCEL AQUI
