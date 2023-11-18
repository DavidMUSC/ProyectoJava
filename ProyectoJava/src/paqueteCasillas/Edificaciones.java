package paqueteCasillas;

import paquetePartida.Jugador;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Edificaciones {

    //ATRIBUTOS
    private int numCasas;
    private int numHoteles;
    private int numPiscinas;
    private int numPistasDeporte;

    private int maxEdificaciones; //Será el mismo número para todos los tipos de edificación

    private int costeCasas;
    private int costeHoteles;
    private int costePiscinas;
    private int costePistasDeporte;

    private GrupoSolar grupoSolar;

    static private int nIdsCasas = 0;
    static private int nIdsHoteles = 0;
    static private int nIdsPiscinas = 0;
    static private int nIdsPistasDeporte = 0;

    private ArrayList<String> ids;

    //GETTERS
    public int getNumCasas(){return numCasas;}
    public int getNumHoteles(){return numHoteles;}
    public int getNumPiscinas(){return numPiscinas;}
    public int getNumPistasDeporte(){return numPistasDeporte;}

    public int getMaxEdificaciones(){return maxEdificaciones;}

    public int getCosteCasas(){return costeCasas;}
    public int getCosteHoteles(){return costeHoteles;}
    public int getCostePiscinas(){return costePiscinas;}
    public int getCostePistasDeporte(){return costePistasDeporte;}

    public GrupoSolar getGrupoSolar(){return grupoSolar;}

    public int getnIdsCasas(){return nIdsCasas;}
    public int getnIdsHoteles(){return nIdsHoteles;}
    public int getnIdsPiscinas(){return nIdsPiscinas;}
    public int getnIdsPistasDeporte(){return nIdsPistasDeporte;}

    public ArrayList<String> getIds(){return ids;}

    //SETTERS
    public void setNumCasas(int numCasas){this.numCasas = numCasas;}
    public void setNumHoteles(int numHoteles){this.numHoteles = numHoteles;}
    public void setNumPiscinas(int numPiscinas){this.numPiscinas = numPiscinas;}
    public void setNumPistasDeporte(int numPistasDeporte){this.numPistasDeporte = numPistasDeporte;}

    public void setMaxEdificaciones(int maxEdificaciones){this.maxEdificaciones = maxEdificaciones;}

    public void setCosteCasas(int costeCasas){this.costeCasas = costeCasas;}
    public void setCosteHoteles(int costeHoteles){this.costeHoteles = costeHoteles;}
    public void setCostePiscinas(int costePiscinas){this.costePiscinas = costePiscinas;}
    public void setCostePistasDeporte(int costePistasDeporte){this.costePistasDeporte = costePistasDeporte;}

    public void setGrupoSolar(GrupoSolar grupoSolar){this.grupoSolar = grupoSolar;}

    public void setnIdsCasas(int new_nIdsCasas){nIdsCasas = new_nIdsCasas;}
    public void setnIdsHoteles(int new_nIdsHoteles){nIdsHoteles = new_nIdsHoteles;}
    public void setnIdsPiscinas(int new_nIdsPiscinas){nIdsPiscinas = new_nIdsPiscinas;}
    public void setnIdsPistasDeporte(int new_nIdsPistasDeporte){nIdsPistasDeporte = new_nIdsPistasDeporte;}

    //No necesitamos un setter para el ArrayList ids

    //Constructor
    public Edificaciones(GrupoSolar grupoSolar){

        //Vinculamos el grupoSolar
        setGrupoSolar(grupoSolar);

        //Inicializamos a 0 el número de edificios:
        setNumCasas(0);
        setNumHoteles(0);
        setNumPiscinas(0);
        setNumPistasDeporte(0);

        //Calculamos el coste de cada uno:
        calcularCostesConstruccion();

        //Calculamos el número máximo de cada edificación:
        calcularMaxEdificaciones();

        //Inicializamos el Array:
        ids = new ArrayList<>(12);

    }
    
    //Otros Métodos:
    //Método para construir una propiedad:
    //Los chequeos para saber cuando se puede edificar se hacen en el menú, aquí se hacen los del tipo de eficiación:
    public void construirEdificacion(String opcion){

        //Variables
        Jugador propietario = this.getGrupoSolar().getPropietario();
        calcularCostesConstruccion();   //Así se actualizará cuando se actualiza el valor del solar.

        //Usamos funciones auxiliares
        switch (opcion){

            case "Casa":
            case "casa":
                construirCasa(propietario);
                break;
            case "Hotel":
            case "hotel":
                construirHotel(propietario);
                break;
            case "Piscina":
            case "piscina":
                construirPiscina(propietario);
                break;
            case "PistaDeportes":
            case "pistaDeportes":
                construirPistaDeDeportes(propietario);
                break;
        }
    }

    //Método para construir una casa:
    private void construirCasa(Jugador propietario){

        //Chequeamos si no estamos ya en el máximo:
        if (esMaxEdificaciones("Casa") == -1){
            return;
        }

        //Chequeamos requisitos para construir una casa - En teoría se cumplen en el menú
        //Chequeamos que haya suficiente dinero
        if (propietario.getDinero() < getCosteCasas()){
            System.out.println("No puedes permitirte construir una casa aquí: tienes " + propietario.getDinero() + " € y edificar una casa aquí vale " +
                    getCosteCasas() + " €.");
            return;
        }
        else{
            //Restamos dinero al propietario
            propietario.setDinero(propietario.getDinero() - getCosteCasas());
            System.out.println("Has construido una casa por " + getCosteCasas() + " €");

            //Sumamos
            setNumCasas(getNumCasas() + 1);
            //Creamos un nuevo ID:
            crearIds("Casa");
            //No hace falta actualizar el alquiler, se va actualizando dinámicamente al pedirlo.

            //actualizar estadisticas de dineroInvertido
            propietario.setDineroInvertido(propietario.getDineroInvertido() + getCosteCasas());
        }
    }

    //Método para construir un hotel:
    private void construirHotel(Jugador propietario){

        //Chequeamos si no estamos ya en el máximo:
        if (esMaxEdificaciones("Hotel") == -1){
            return;
        }

        //Chequeamos requisitos para construir una casa - En teoría se cumplen en el menú
        //Chequeamos que haya suficiente dinero
        if (propietario.getDinero() < getCosteHoteles()){
            System.out.println("Tienes " + propietario.getDinero() + " € y edificar un hotel aquí vale " + getCosteHoteles() + " €.");
        }
        else{
            //Chequeamos si hay 4 casas construidas previamente:
            if (this.getNumCasas() < 4){
                System.out.println("No puedes construír un hotel: necesitas tener al menos 4 casas edificadas y tienes "
                + getNumCasas() + ".");
                return;
            }

            //Si hay 4 casas, las eliminamos, junto a sus IDS
            setNumCasas(getNumCasas() - 4);

            //Eliminamos 4 IDS de casa:
            int flag = 0;
            int i = 0;

            //While
            while (flag!=4){
                //Si encontramos una id de casa, la eliminamos y no actualizamos i:
                if (getIds().get(i).startsWith("casa-")){
                    getIds().remove(i);
                    flag++;
                }
                else{
                    i++;
                }
            }

            //Sumamos 1 hotel:
            setNumHoteles(getNumHoteles() + 1);
            //Creamos un nuevo ID:
            crearIds("Hotel");

            //Restamos dinero al propietario
            propietario.setDinero(propietario.getDinero() - getCosteHoteles());
            System.out.println("Has construido un hotel por " + getCosteHoteles() + " €");
            //No hace falta actualizar el alquiler, se va actualizando dinámicamente al pedirlo.

            //actualizar estadisticas de dineroInvertido
            propietario.setDineroInvertido(propietario.getDineroInvertido() + getCosteHoteles());
        }
    }

    //Método para construir una piscina:
    private void construirPiscina(Jugador propietario){

        //Chequeamos si no estamos ya en el máximo:
        if (esMaxEdificaciones("Piscina") == -1){
            return;
        }

        //Chequeamos requisitos para construir una casa - En teoría se cumplen en el menú
        //Chequeamos que haya suficiente dinero
        if (propietario.getDinero() < getCostePiscinas()){
            System.out.println("Tienes " + propietario.getDinero() + " € y construir una piscina aquí vale " + getCostePiscinas() + " €.");
        }
        else{
            //Chequeamos si hay 1 hotel y 2 casas construidas previamente:
            if ((this.getNumHoteles() == 0) || ((this.getNumCasas() < 2) && (this.getNumHoteles() == 1)) ){
                System.out.println("No puedes construír una piscina: necesitas tener al menos 1 hotel y 2 casas edificadas y tienes "
                        + getNumCasas() + " casas y " + getNumHoteles() + " hoteles.");
                return;
            }

            //Sumamos 1 piscina:
            setNumPiscinas(getNumPiscinas() + 1);
            //Creamos un nuevo ID:
            crearIds("Piscina");

            //Restamos dinero al propietario
            propietario.setDinero(propietario.getDinero() - getCostePiscinas());
            System.out.println("Has construido una piscina por " + getCostePiscinas() + " €");
            //No hace falta actualizar el alquiler, se va actualizando dinámicamente al pedirlo.

            //actualizar estadisticas de dineroInvertido
            propietario.setDineroInvertido(propietario.getDineroInvertido() + getCostePiscinas());
        }
    }

    //Método para construir una pista de deporte:
    private void construirPistaDeDeportes(Jugador propietario){

        //Chequeamos si no estamos ya en el máximo:
        if (esMaxEdificaciones("PistaDeportes") == -1){
            return;
        }

        //Chequeamos requisitos para construir una casa - En teoría se cumplen en el menú
        //Chequeamos que haya suficiente dinero
        if (propietario.getDinero() < getCostePistasDeporte()){
            System.out.println("Tienes " + propietario.getDinero() + " € y construir una pista de deporte aquí vale " + getCostePistasDeporte() + " €.");
            return;
        }
        else {
            //Chequeamos si hay 2 hoteles construidos previamente:
            if (this.getNumHoteles() < 2) {
                System.out.println("No puedes construír una pista de deportes: necesitas tener al menos 2 hoteles y tienes "
                        + getNumHoteles() + ".");
                return;
            }

            //Sumamos 1 pista de deportes:
            setNumPistasDeporte(getNumPistasDeporte() + 1);
            //Creamos un nuevo ID:
            crearIds("PistaDeporte");

            //Restamos dinero al propietario
            propietario.setDinero(propietario.getDinero() - getCostePistasDeporte());
            System.out.println("Has construido una pista de deportes por " + getCostePistasDeporte() + " €");
            //No hace falta actualizar el alquiler, se va actualizando dinámicamente al pedirlo.

            //actualizar estadisticas de dineroInvertido
            propietario.setDineroInvertido(propietario.getDineroInvertido() + getCostePistasDeporte());
        }
    }

    //Método para calcular número máximo de edificaciones (SOLO LAS CALCULA, NO COMPRUEBA):
    public void calcularMaxEdificaciones(){

        //El número máximo de edificaciones dependerá del color del grupoSolar:
        String color = this.getGrupoSolar().getColorSolar();
        //Para color negro y color azul:
        if (color.equals("Negro") || color.equals("Azul")){
            setMaxEdificaciones(2);
        }
        //Para el resto de colores:
        else{
            setMaxEdificaciones(3);
        }
    }

    //Método para saber si ya tenemos el máximo de edificaciones posibles (SOLO COMPRUEBA):
    public int esMaxEdificaciones(String edificio){

        //Hacemos un switch para cada tipo de edificación:
        switch (edificio){

            //Casas:
            case "Casa":
            case "casa":
                //Primero chequeamos si tenemos ya 4 casas (es un máximo implícito, da igual que el máximo real sea 3 o 2):
                if (getNumCasas() == 4){
                    System.out.println("Ya tienes 4 casas edificadas, edifica un hotel.");
                    return -1;
                }
                //Después chequeamos si tenemos el máximo de hoteles:
                if (getNumHoteles() == getMaxEdificaciones()){
                    //Si tenemos el maxHoteles, no podremos pasarnos del máximo de casas establecido:
                    if(getNumCasas() == getMaxEdificaciones()){
                        System.out.println("Tienes el número máximo de casas y hoteles permitido.");
                        return -1;
                    }
                }
                //Si pasa los dos checks:
                return 0;

            //Hoteles:
            case "Hotel":
            case "hotel":
                //Chequeamos simplemente si tenemos el máximo permitido:
                if (getNumHoteles() == getMaxEdificaciones()){
                    System.out.println("Ya tienes el número máximo permitido de hoteles edificados.");
                    return -1;
                }
                //Si pasa el check retornamos 0:
                return 0;

            //Piscinas:
            case "Piscina":
            case "piscina":
                //Chequeamos simplemente si tenemos el máximo permitido:
                if (getNumPiscinas() == getMaxEdificaciones()){
                    System.out.println("Ya tienes el número máximo permitido de piscinas construidas.");
                    return -1;
                }
                //Si pasa el check retornamos 0:
                return 0;

            //Pistas de deporte:
            case "PistaDeportes":
            case "pistaDeportes":
                //Chequeamos simplemente si tenemos el máximo permitido:
                if (getNumPistasDeporte() == getMaxEdificaciones()){
                    System.out.println("Ya tienes el número máximo permitido de pistas de deportes construidas.");
                    return -1;
                }
                //Si pasa el check retornamos 0:
                return 0;

            //Default (no hace falta, ya que va hardcodeado):
            default:
                System.out.println("No has seleccionado una edificación válida para comprobar.");
                return -1;
        }
    }

    //Método para calcular los costes de cada edificacion:
    public void calcularCostesConstruccion(){

        //Los costes de cada edificación vienen dados por un porcentaje del valor inicial del solar en el que se edifica:

        //CASA - 60% del valor inicial del solar
        setCosteCasas((int)(this.getGrupoSolar().getValor() * 0.6));

        //HOTEL - 60% del valor inicial del solar
        setCosteHoteles((int)(this.getGrupoSolar().getValor() * 0.6));

        //PISCINA - 40% del valor inicial del solar
        setCostePiscinas((int)(this.getGrupoSolar().getValor() * 0.4));

        //PISTA DE DEPORTES - 125% del valor inicial del solar
        setCostePistasDeporte((int)(this.getGrupoSolar().getValor() * 1.25));
    }

    //Método para crear ids:
    public void crearIds(String edificio){
        //Variables:
        int num;
        String stringAux;

        //Dependiendo del edificio que construyamos, crearemos Ids distintos:
        switch (edificio){
            //CASA
            case "Casa":
                //Obtenemos el número del ID:
                num = getnIdsCasas();
                //Creamos un String que sea el ID:
                stringAux = "casa-" + num;
                //Añadimos el nuevo id al ArrayList:
                getIds().add(stringAux);
                //Actualizamos el atributo static:
                setnIdsCasas(getnIdsCasas()+1);
                break;

            //HOTEL
            case "Hotel":
                //Obtenemos el número del ID:
                num = getnIdsHoteles();
                //Creamos un String que sea el ID:
                stringAux = "hotel-" + num;
                //Añadimos el nuevo id al ArrayList:
                getIds().add(stringAux);
                //Actualizamos el atributo static:
                setnIdsHoteles(getnIdsHoteles()+1);
                break;

            //PISCINA
            case "Piscina":
                //Obtenemos el número del ID:
                num = getnIdsPiscinas();
                //Creamos un String que sea el ID:
                stringAux = "piscina-" + num;
                //Añadimos el nuevo id al ArrayList:
                getIds().add(stringAux);
                //Actualizamos el atributo static:
                setnIdsPiscinas(getnIdsPiscinas()+1);
                break;

            //PISTA DE DEPORTE
            case "PistaDeporte":
                //Obtenemos el número del ID:
                num = getnIdsPistasDeporte();
                //Creamos un String que sea el ID:
                stringAux = "deporte-" + num;
                //Añadimos el nuevo id al ArrayList:
                getIds().add(stringAux);
                //Actualizamos el atributo static:
                setnIdsPistasDeporte(getnIdsPistasDeporte()+1);
                break;

        }

    }

    //Método auxiliar para listar todas las edificaciones de una instancia de Edificaciones:
    public void listarEdificaciones1Casilla(){

        //Variables
        int numTotalEdificaciones = getNumCasas() + getNumHoteles() + getNumPiscinas() + getNumPistasDeporte();
        int i = 0;
        String stringAux;
        int opcion = 0; // 1: Casa; 2: Hotel, 3: Piscina, 4: Pista de deportes
        int coste = 0;

        //Si no hay ningún edificio construido, se retorna:
        if (numTotalEdificaciones == 0) return;

        //Iteramos N (número de edificaciones) veces sobre los ids:
        for (i = 0; i < numTotalEdificaciones; i++){

            //Determinamos propiedad dependiendo del ID:
            stringAux = getIds().get(i);

            //Necesitamos saber el coste de cada tipo de edificacion:
            //Casa
            if (stringAux.startsWith("casa-")) opcion = 1;
            //Hotel
            if (stringAux.startsWith("hotel-")) opcion = 2;
            //Piscina
            if (stringAux.startsWith("piscina-")) opcion = 3;
            //Pista de deportes
            if (stringAux.startsWith("deporte-")) opcion = 4;

            //Switch:
            switch (opcion){
                //Casa
                case 1:
                    coste = getCosteCasas();
                    break;
                //Hoteles
                case 2:
                    coste = getCosteHoteles();
                    break;
                //Piscina:
                case 3:
                    coste = getCostePiscinas();
                    break;
                //Pistas deporte:
                case 4:
                    coste = getCostePistasDeporte();
                    break;
                //Error:
                default:
                    System.out.println("Error al elegir opcion en listarEdificaciones1Casilla.\n");
                    break;
            }

            //Imprimimos id, propietario, casilla y grupo:
            System.out.println("{\n\tid: " + stringAux + ",\n\tpropietario: " + getGrupoSolar().getPropietario().getNombre() +
                    ",\n\tcasilla: " + getGrupoSolar().getCasilla().getNombreCasilla() + ",\n\tgrupo: " +
                    getGrupoSolar().getColorSolar() + ",\n\tcoste: " + coste + "\n},\n");
        }
    }

    //Método auxiliar para determinar si hay alguna edificacion construida:
    public boolean esVacioEdificaciones(){
        //Variable
        int contador = 0;
        //Sumamos el número de edificaciones ya construidas:
        contador = getNumCasas() + getNumHoteles() + getNumPiscinas() + getNumPistasDeporte();
        //Si contador = 0, no hay ninguna edificacion:
        if (contador == 0) return true;
        else return false;
    }

}

