import java.util.ArrayList;
import java.util.Arrays;

enum Colore {
    ROSSO("\u001B[41m"),
    BLU("\u001B[44m"),
    VERDE("\u001B[42m"),
    GIALLO("\u001B[43m"),
    BIANCO("\u001B[47m");

    public static final String RESET = "\u001B[0m";
    public final String ansi;

    Colore(String ansi) {
        this.ansi = ansi;
    }
}

public class Casella {
    protected String nome;
    protected Casella precedente;
    protected Casella successiva;
    protected Colore colore;

    protected final Colore coloreBase = null;

    public static ArrayList<Colore> colori = new ArrayList<>(Arrays.asList(Colore.ROSSO, Colore.BLU,Colore.VERDE,Colore.GIALLO));

    public Casella(String nome) {
        this.nome = nome;
        this.colore = coloreBase;
    }

    public Casella getSuccessiva(){
        return successiva;
    }

    public Casella getPrecedente(){
        return precedente;
    }

    public String getNome() {
        return nome;
    }

    public Colore getColore() {
        return colore;
    }

    public void setSuccessiva(Casella successiva){
        this.successiva = successiva;
    }

    public void setPrecedente(Casella precedente){
        this.precedente = precedente;
    }

    public void setColore(Colore color) {
        this.colore = color;
    }
    

    @Override
    public String toString() {
        if(colore == null){
            if(this instanceof Terreno){
                return nome; 
            }else{
                return Colore.BIANCO.ansi + nome + Colore.RESET;
            }
        }else{
             return colore.ansi + " " + nome + " " + Colore.RESET;
        }
       
    }
}
