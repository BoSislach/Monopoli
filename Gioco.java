import java.util.ArrayList;
import java.util.Scanner;

class Gioco {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Tabellone tabellone = new Tabellone();
        ArrayList<Giocatore> giocatori = new ArrayList<>();

        Turni turni = new Turni(giocatori);
        int numeroGiocatori = 0;
        System.out.println("Inserisci il numero di giocatori:");

        do {
            numeroGiocatori = scanner.nextInt();
            if (numeroGiocatori < 1 || numeroGiocatori > 4) {
                System.out.println("Errore: inserisci un numero tra 1 e 4");
            }

        } while (numeroGiocatori < 1 || numeroGiocatori > 4);

        for (int i = 0; i < numeroGiocatori; i++) {
            System.out.println("Inserisci il nome del giocatore " + (i + 1) + ":");
            String nomeGiocatore = scanner.next();

            Giocatore giocatore = new Giocatore(nomeGiocatore, 0, 0, 1500, new ArrayList<Terreno>(), true);
            giocatori.add(giocatore);
            System.out.println("Giocatore " + giocatore.getNome() + " creato con saldo iniziale di " + giocatore.getSaldo());
        }

        tabellone.stampaTabellone();

        while (true) {
            Giocatore corrente = turni.getGiocatoreCorrente();
            System.out.println("Turno di " + corrente.getNome());

            //movimento
            Casella casella = tabellone.getCasella(corrente.getX(), corrente.getY());
            System.out.println("Sei arrivato su: " + casella.getNome());

            turni.passaAlProssimoTurno();
        }

    }
}
