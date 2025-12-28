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

class Casella {
    protected String nome;
    protected Casella precedente;
    protected Casella successiva;
    protected int posizione;
    protected Colore colore;

    protected final Colore coloreBase = Colore.BIANCO;

    protected Colore[] vettoreColori = {
            Colore.ROSSO,
            Colore.BLU,
            Colore.VERDE,
            Colore.GIALLO
    };

    protected ArrayList<Colore> colori = new ArrayList<>(Arrays.asList(vettoreColori));

    public Casella(String nome, int posizione) {
        this.nome = nome;
        this.posizione = posizione;
        this.colore = coloreBase;
    }

    public String getNome() {
        return nome;
    }

    public int getPosizione() {
        return posizione;
    }

    public Colore getColore() {
        return colore;
    }
    public void setColore(int posizione) {
        this.colore = colori.get(posizione);
    }
    

    @Override
    public String toString() {
        return colore.ansi + " " + nome + " " + Colore.RESET;
    }
}
