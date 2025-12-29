import java.util.ArrayList;

public class Turni {
    private final ArrayList<Giocatore> giocatori;
    private int turnoCorrente;


    public Turni(ArrayList<Giocatore> giocatori) {
        this.giocatori = new ArrayList<>(giocatori);
        this.turnoCorrente = 0;
    }

    public Giocatore getGiocatoreCorrente() {
        return giocatori.get(turnoCorrente);
    }

    public void passaAlProssimoTurno() {
       if (turnoCorrente < giocatori.size() - 1) {
            turnoCorrente++;
        } else {
            turnoCorrente = 0;
        }
    }

    public int getTurnoCorrente() {
        return turnoCorrente;
    }

    public int getNumeroGiocatori() {
        return giocatori.size();
    }
}
