import java.util.ArrayList;
import java.util.Scanner;

public class Gioco {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
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
            Giocatore g = new Giocatore(nome, tabellone.getCasellaPartenza(), 500, new ArrayList<>(), true, simbolo);
            giocatori.add(g);
            System.out.println("Giocatore " + g.getNomeColorato() + " creato con saldo iniziale di " + g.getSaldo());
        }
        Turni turni = new Turni(giocatori);
        Dadi dadi = new Dadi();
        boolean giocoInCorso = true;
        while (giocoInCorso) {
            System.out.print("\n");
            tabellone.stampaTabellone(giocatori);
            Giocatore giocatoreCorrente = turni.getGiocatoreCorrente();
            if (!giocatoreCorrente.isInGioco()) {
                turni.passaAlProssimoTurno();
                continue;
            }
            System.out.print("\n");
            System.out.println("Turno di " + giocatoreCorrente.getNomeColorato());
            System.out.println("Saldo attuale: " + giocatoreCorrente.getSaldo());
            if (giocatoreCorrente.getStatoPrigione()) {
                if (giocatoreCorrente.getCartaPrigione()) {
                    System.out.println(giocatoreCorrente.getNomeColorato() + " vuoi usare la carta imprevisto? (s/n)");
                    String risposta = scanner.next();
                    if (risposta.equals("s")) {
                        giocatoreCorrente.usaCartaPrigione();
                        System.out.println(giocatoreCorrente.getNomeColorato() + " ha usato la carta esci di prigione");
                    } else {
                        System.out.println(
                                giocatoreCorrente.getNomeColorato() + " non ha usato la carta esci di prigione");
                        turni.passaAlProssimoTurno();
                        continue;
                    }
                } else {
                    giocatoreCorrente.turniInPrigione--;
                    if (giocatoreCorrente.turniInPrigione <= 0) {
                        giocatoreCorrente.setStatoPrigione(false);
                        System.out.println(giocatoreCorrente.getNomeColorato() + " esce dalla prigione");
                    } else {
                        System.out.println(giocatoreCorrente.getNomeColorato() + " rimane in prigione per altri "
                                + giocatoreCorrente.turniInPrigione + " turni");
                        turni.passaAlProssimoTurno();
                        continue;
                    }
                }
            }
            dadi.lanciaDadi();
            int somma = dadi.getSomma();
            System.out.print("\n");
            System.out.println(giocatoreCorrente.getNomeColorato() + " ha lanciato i dadi e ottenuto: " + somma);
            System.out.println("premi un tasto per continuare");
            scanner.nextLine();
            Casella attuale = giocatoreCorrente.getPosizione();
            for (int i = 0; i < somma; i++) {
                attuale = tabellone.getProssimaCasella(attuale);
            }
            giocatoreCorrente.setPosizione(attuale);
            tabellone.stampaTabellone(giocatori);
            Casella casellaCorrente = giocatoreCorrente.getPosizione();
            System.out.print("\n");
            System.out.println(giocatoreCorrente.getNomeColorato() + " è atterrato su " + casellaCorrente.getNome());
            if (casellaCorrente instanceof Terreno terreno) {
                if (terreno.getProprietario() == null) {
                    if (giocatoreCorrente.getSaldo() >= terreno.getCosto()) {
                        if (!giocatoreCorrente.getCompraCase()) {
                            System.out.println(giocatoreCorrente.getNomeColorato() + " non puo comprare le case");
                            giocatoreCorrente.setCompraCase(true);
                        } else {
                            System.out.println(
                                    "Vuoi acquistare " + terreno.getNome() + " per " + terreno.getCosto() + "? (s/n)");
                            String risposta = scanner.next();
                            if (risposta.equals("s")) {
                                terreno.compraCasa(giocatoreCorrente, banca);
                                terreno.setProprietario(giocatoreCorrente);
                                System.out.println(
                                        giocatoreCorrente.getNomeColorato() + " ha acquistato " + terreno.getNome());
                            }
                        }
                    } else {
                        System.out.println(giocatoreCorrente.getNomeColorato()+ " non ha abbastanza soldi per acquistare " + terreno.getNome());
                    }
                } else if (!terreno.getProprietario().equals(giocatoreCorrente)) {
                    System.out.print("\n");
                    System.out.println(
                            terreno.getNome() + " è di proprietà di " + terreno.getProprietario().getNomeColorato());
                    int sommaDaPagare = giocatoreCorrente.pagaAffitto(terreno);
                    if (giocatoreCorrente.getSaldo() - sommaDaPagare < 0) {
                        System.out.print(giocatoreCorrente.getNomeColorato() + " deve pagare un affitto di "+ sommaDaPagare + " ma non ha abbastanza soldi. ");
                        System.out.println(giocatoreCorrente.getNomeColorato() + " ha perso il gioco!");
                        giocatoreCorrente.tolgoTerreni();  
                        giocatori.remove(giocatoreCorrente);
                        continue;
                    }
                    terreno.getProprietario().setSaldo(terreno.getProprietario().getSaldo() + sommaDaPagare);
                    System.out.println(giocatoreCorrente.getNomeColorato() + " ha pagato un affitto di "
                            + terreno.getAffitto() + " a " + terreno.getProprietario().getNomeColorato());
                }
            } else if (casellaCorrente instanceof Imprevisto imprevisto) {
                System.out.println(giocatoreCorrente.getNomeColorato() + " pesca una carta imprevisto: ");
                imprevisto.esegui(giocatoreCorrente, tabellone, dadi);
                for (int c = 0; c < giocatori.size(); c++) {
                    if (giocatori.get(c).isInGioco() == false) {
                        System.out.println(giocatori.get(c).getNomeColorato() + " e fuori dal gioco");
                        giocatori.remove(c);
                    }
                }
            } else if (casellaCorrente instanceof VaiinPrigione vai) {
                Prigione prigione = tabellone.getCasellaPrigione();
                giocatoreCorrente.VaiInPrigione(prigione);
                System.out.println(giocatoreCorrente.getNomeColorato() + " è stato mandato in prigione!");

                System.out.println("Vuoi pagare la cauzione per uscire subito? (s/n)");
                String risposta = scanner.next();
                if (risposta.equals("s")) {
                    prigione.cauzione(giocatoreCorrente);
                }
            } else if (casellaCorrente instanceof Tassa tassa) {
                System.out.print("\n");
                System.out.println(
                giocatoreCorrente.getNomeColorato() + " deve pagare una tassa di " + tassa.getImporto());
                if (giocatoreCorrente.getSaldo() - tassa.getImporto() < 0) {
                    System.out.println(giocatoreCorrente.getNomeColorato() + " ha perso il gioco!");
                    giocatoreCorrente.tolgoTerreni(); 
                    giocatori.remove(giocatoreCorrente);
                    continue;
                } else {
                    tassa.azione(giocatoreCorrente);
                }
            }
            System.out.println("premi un tasto per continuare");
            scanner.nextLine();
            turni.passaAlProssimoTurno();
            if (giocatori.size() == 1) {
                System.out.println(giocatori.get(0).getNomeColorato() + " ha vinto il gioco! XDXDXDXDXDXDXDXDXDXDX");
                giocoInCorso = false;
            }
        }
    }
}