import java.util.ArrayList;
import java.util.Scanner;

public class Gioco {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            Tabellone tabellone = new Tabellone();
            ArrayList<Giocatore> giocatori = new ArrayList<>();
            Banca banca = new Banca(10000);

            int numeroGiocatori;
            do {
                System.out.print("Inserisci il numero di giocatori (1-4): ");
                numeroGiocatori = scanner.nextInt();
            } while (numeroGiocatori < 1 || numeroGiocatori > 4);

            

            for (int i = 0; i < numeroGiocatori; i++) {
                System.out.println("Inserisci il nome del giocatore " + (i + 1) + ":");
                String nome = scanner.next();
                System.out.println("inserisci carattere speciale");
                String simbolo = scanner.next();

                Giocatore g = new Giocatore(nome,tabellone.getCasellaPartenza(),1500,new ArrayList<>(),true,simbolo);

                giocatori.add(g);
                System.out.println("Giocatore " + g.getNome() + " creato con saldo iniziale di " + g.getSaldo());
            }

            Turni turni = new Turni(giocatori);
            Dadi dadi = new Dadi();
            boolean giocoInCorso = true;

            while (giocoInCorso) {

                tabellone.stampaTabellone(giocatori);
                Giocatore giocatoreCorrente = turni.getGiocatoreCorrente();

                if (!giocatoreCorrente.isInGioco()) {
                    turni.passaAlProssimoTurno();
                    continue;
                }

                System.out.println("Turno di " + giocatoreCorrente.getNome());
                System.out.println("Saldo attuale: " + giocatoreCorrente.getSaldo());

                if (giocatoreCorrente.getStatoPrigione()) {
                    if (giocatoreCorrente.getCartaPrigione()) {
                        System.out.println(giocatoreCorrente.getNome() + " vuoi usare la carta imprevisto? (s/n)");
                        String risposta = scanner.next();
                        if (risposta.equals("s")) {
                            giocatoreCorrente.usaCartaPrigione();
                            System.out.println(giocatoreCorrente.getNome() + " ha usato la carta esci di prigione");
                        } else {
                            System.out.println(giocatoreCorrente.getNome() + " non ha usato la carta esci di prigione");
                            turni.passaAlProssimoTurno();
                            continue;
                        }
                    } else {
                        giocatoreCorrente.turniInPrigione--;
                        if (giocatoreCorrente.turniInPrigione <= 0) {
                            giocatoreCorrente.setStatoPrigione(false);
                            System.out.println(giocatoreCorrente.getNome() + " esce dalla prigione");
                        } else {
                            System.out.println(giocatoreCorrente.getNome() + " rimane in prigione per altri " + giocatoreCorrente.turniInPrigione + " turni");
                            turni.passaAlProssimoTurno();
                            continue;
                        }
                    }
                }

                dadi.lanciaDadi();
                int somma = dadi.getSomma();
                System.out.println(giocatoreCorrente.getNome() + " ha lanciato i dadi e ottenuto: " + somma);
                scanner.nextLine();
                System.out.println("premi un tasto per continuare");
                scanner.nextLine();

                Casella attuale = giocatoreCorrente.getPosizione();
                for(int i=0; i<somma; i++){
                    attuale = tabellone.getProssimaCasella(attuale);
                }

                giocatoreCorrente.setPosizione(attuale);

                tabellone.stampaTabellone(giocatori);

                Casella casellaCorrente = giocatoreCorrente.getPosizione();
                System.out.println(giocatoreCorrente.getNome() + " è atterrato su " + casellaCorrente.getNome());

                if (casellaCorrente instanceof Terreno terreno) {

                    if (terreno.getProprietario() == null) {

                        if (giocatoreCorrente.getSaldo() >= terreno.getCosto()) {
                            System.out.println("Vuoi acquistare " + terreno.getNome() + " per " + terreno.getCosto() + "? (s/n)");
                            String risposta = scanner.next();

                            if (risposta.equals("s")) {
                                terreno.compraCasa(giocatoreCorrente, banca);
                                terreno.setProprietario(giocatoreCorrente);
                                System.out.println(giocatoreCorrente.getNome() + " ha acquistato " + terreno.getNome());
                            }
                        } else {
                            System.out.println(giocatoreCorrente.getNome() + " non ha abbastanza soldi per acquistare " + terreno.getNome());
                        }

                    } else if (!terreno.getProprietario().equals(giocatoreCorrente)) {
                        System.out.println(terreno.getNome() + " è di proprietà di " + terreno.getProprietario());
                        giocatoreCorrente.pagaAffitto(terreno);
                        terreno.getProprietario().setSaldo(terreno.getProprietario().getSaldo() + terreno.getAffitto());
                    }

                } else if (casellaCorrente instanceof Imprevisto imprevisto) {
                    System.out.println(giocatoreCorrente.getNome() + " pesca una carta imprevisto: ");
                    imprevisto.esegui(giocatoreCorrente, tabellone, dadi);

                } else if (casellaCorrente instanceof VaiinPrigione vai) {
                    boolean controlloPrigione = vai.VaiInGalera(giocatoreCorrente);
                    if (controlloPrigione) {
                        System.out.println("vuoi pagare la cauzione? (s/n)");
                        String risposta = scanner.next();
                        if (risposta.equals("s")) {
                            tabellone.getCasellaPrigione().cauzione(giocatoreCorrente);
                        }
                    }

                } else if (casellaCorrente instanceof Tassa tassa) {
                    System.out.println(giocatoreCorrente.getNome() + " deve pagare una tassa di " + tassa.getImporto());
                    if (giocatoreCorrente.getSaldo() - tassa.getImporto() < 0) {
                        System.out.println(giocatoreCorrente.getNome() + " ha perso il gioco!");
                        giocatoreCorrente.setIsInGioco(false);
                    } else {
                        tassa.azione(giocatoreCorrente);
                    }
                }

                System.out.println("premi un tasto per continuare");
                scanner.nextLine();
                scanner.nextLine();

                turni.passaAlProssimoTurno();
            }
        }
    }
}
