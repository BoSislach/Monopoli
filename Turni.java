import java.util.ArrayList;

public class Turni {
    private final ArrayList<Giocatore> giocatori;
    private int turnoCorrente;


    public Turni(ArrayList<Giocatore> giocatori) {
        this.giocatori = giocatori;
        this.turnoCorrente = 0;
    }

    public Giocatore getGiocatoreCorrente() {
    if (turnoCorrente >= giocatori.size()) {
        turnoCorrente = 0;
    }
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
