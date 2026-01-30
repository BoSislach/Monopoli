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
            numeroGiocatori = Integer.parseInt(scanner.nextLine());
        } while (numeroGiocatori < 1 || numeroGiocatori > 4);

        for (int i = 0; i < numeroGiocatori; i++) {
            System.out.println("Inserisci il nome del giocatore " + (i + 1) + ":");
            String nome = scanner.nextLine();
            System.out.println("inserisci carattere speciale");
            String simbolo = scanner.nextLine();
            Giocatore g = new Giocatore(nome, tabellone.getCasellaPartenza(), 100, new ArrayList<>(), true, simbolo);
            giocatori.add(g);
            System.out.println("Giocatore " + g.getNomeColorato() + " creato con saldo iniziale di " + g.getSaldo());
        }

        Dadi dadi = new Dadi();
        boolean giocoInCorso = true;
        int currentIndex = 0;

        while (giocoInCorso) {
            if (giocatori.isEmpty()) {
                System.out.println("Non ci sono più giocatori. Fine del gioco.");
                break;
            }

            if (currentIndex >= giocatori.size()) {
                currentIndex = 0;
            }

            System.out.print("\n");
            tabellone.stampaTabellone(giocatori);
            Giocatore giocatoreCorrente = giocatori.get(currentIndex);

            if (!giocatoreCorrente.isInGioco()) {
                System.out.println(giocatoreCorrente.getNomeColorato() + " è fuori dal gioco e viene rimosso.");
                giocatoreCorrente.tolgoTerreni();
                giocatori.remove(currentIndex);
                continue;
            }

            System.out.print("\n");
            System.out.println("Turno di " + giocatoreCorrente.getNomeColorato());
            System.out.println("Saldo attuale: " + giocatoreCorrente.getSaldo());

            if (giocatoreCorrente.getStatoPrigione()) {
                if (giocatoreCorrente.getCartaPrigione()) {
                    System.out.println(giocatoreCorrente.getNomeColorato() + " vuoi usare la carta imprevisto? (s/n)");
                    String risposta = scanner.nextLine();
                    if (risposta.equalsIgnoreCase("s")) {
                        giocatoreCorrente.usaCartaPrigione();
                        System.out.println(giocatoreCorrente.getNomeColorato() + " ha usato la carta esci di prigione");
                    } else {
                        System.out.println(giocatoreCorrente.getNomeColorato() + " non ha usato la carta esci di prigione");
                        currentIndex++;
                        continue;
                    }
                } else {
                    giocatoreCorrente.turniInPrigione--;
                    if (giocatoreCorrente.turniInPrigione <= 0) {
                        giocatoreCorrente.setStatoPrigione(false);
                        System.out.println(giocatoreCorrente.getNomeColorato() + " esce dalla prigione");
                    } else {
                        System.out.println(giocatoreCorrente.getNomeColorato() + " rimane in prigione per altri "+ giocatoreCorrente.turniInPrigione + " turni");
                        currentIndex++;
                        continue;
                    }
                }
            }

            dadi.lanciaDadi();
            int somma = dadi.getSomma();
            System.out.print("\n");
            System.out.println(giocatoreCorrente.getNomeColorato() + " ha lanciato i dadi e ottenuto: " + somma);
            System.out.println("premi invio per continuare");
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
            boolean giocatoreRimossoQuestoTurno = false;

            if (casellaCorrente instanceof Terreno terreno) {
                if (terreno.getProprietario() == null) {
                    if (giocatoreCorrente.getSaldo() >= terreno.getCosto()) {
                        if (!giocatoreCorrente.getCompraCase()) {
                            System.out.println(giocatoreCorrente.getNomeColorato() + " non puo comprare le case");
                            giocatoreCorrente.setCompraCase(true);
                        } else {
                            System.out.println("Vuoi acquistare " + terreno.getNome() + " per " + terreno.getCosto() + "? (s/n)");
                            String risposta = scanner.nextLine();
                            if (risposta.equalsIgnoreCase("s")) {
                                if (giocatoreCorrente.getSaldo() - terreno.getCosto() >= 0) {
                                    terreno.compraCasa(giocatoreCorrente, banca);
                                    terreno.setProprietario(giocatoreCorrente);
                                    System.out.println(giocatoreCorrente.getNomeColorato() + " ha acquistato "+ terreno.getNome());
                                }
                            }
                        }
                    } else {
                        System.out.println(giocatoreCorrente.getNomeColorato()+ " non ha abbastanza soldi per acquistare " + terreno.getNome());
                    }
                } else if (!terreno.getProprietario().equals(giocatoreCorrente)) {
                    System.out.print("\n");
                    System.out.println(terreno.getNome() + " è di proprietà di " + terreno.getProprietario().getNomeColorato());
                    int sommaDaPagare = giocatoreCorrente.pagaAffitto(terreno);
                    if (giocatoreCorrente.getSaldo() < sommaDaPagare) {
                        System.out.print(giocatoreCorrente.getNomeColorato() + " deve pagare un affitto di "+ sommaDaPagare + " ma non ha abbastanza soldi (saldo: " + giocatoreCorrente.getSaldo()+ "). ");
                        System.out.println(giocatoreCorrente.getNomeColorato() + " ha perso il gioco!");
                        giocatoreCorrente.tolgoTerreni();
                        giocatori.remove(currentIndex);
                        giocatoreRimossoQuestoTurno = true;
                    } else {
                        giocatoreCorrente.setSaldo(giocatoreCorrente.getSaldo() - sommaDaPagare);
                        terreno.getProprietario().setSaldo(terreno.getProprietario().getSaldo() + sommaDaPagare);
                        System.out.println(giocatoreCorrente.getNomeColorato() + " ha pagato un affitto di "+ sommaDaPagare + " a " + terreno.getProprietario().getNomeColorato());
                        if (giocatoreCorrente.getSaldo() < 0) {
                            giocatoreCorrente.setSaldo(0);
                        }
                    }
                }
            } else if (casellaCorrente instanceof Imprevisto imprevisto) {
                System.out.println(giocatoreCorrente.getNomeColorato() + " pesca una carta imprevisto: ");
                int saldoPrima = giocatoreCorrente.getSaldo();
                imprevisto.esegui(giocatoreCorrente, tabellone, dadi);
                if (giocatoreCorrente.getSaldo() < 0) {
                    System.out.println(giocatoreCorrente.getNomeColorato() + " è andato in bancarotta a causa dell'imprevisto!");
                    giocatoreCorrente.setSaldo(0);
                    giocatoreCorrente.setInGioco(false);
                }

                for (int c = 0; c < giocatori.size();) {
                    if (!giocatori.get(c).isInGioco()) {
                        if (giocatori.get(c) == giocatoreCorrente) {
                            giocatoreRimossoQuestoTurno = true;
                        }
                        System.out.println(giocatori.get(c).getNomeColorato() + " è fuori dal gioco");
                        giocatori.get(c).tolgoTerreni();
                        giocatori.remove(c);
                        if (c <= currentIndex && currentIndex > 0) {
                            currentIndex--;
                        }
                    } else {
                        c++;
                    }
                }
            } else if (casellaCorrente instanceof VaiinPrigione) {
                Prigione prigione = tabellone.getCasellaPrigione();
                giocatoreCorrente.VaiInPrigione(prigione);
                System.out.println(giocatoreCorrente.getNomeColorato() + " è stato mandato in prigione!");
                System.out.println("Vuoi pagare la cauzione per uscire subito? (s/n)");
                String risposta = scanner.nextLine();
                if (risposta.equalsIgnoreCase("s")) {
                    int costoCauzione = 50;
                    if (giocatoreCorrente.getSaldo() < costoCauzione) {
                        System.out.println(giocatoreCorrente.getNomeColorato()+ " non ha abbastanza soldi per pagare la cauzione!");
                    } else {
                        prigione.cauzione(giocatoreCorrente);
                        if (giocatoreCorrente.getSaldo() < 0 || !giocatoreCorrente.isInGioco()) {
                            if (giocatoreCorrente.getSaldo() < 0) {
                                giocatoreCorrente.setSaldo(0);
                            }
                            System.out.println(giocatoreCorrente.getNomeColorato()+ " non ha potuto pagare la cauzione e ha perso!");
                            giocatoreCorrente.tolgoTerreni();
                            giocatori.remove(currentIndex);
                            giocatoreRimossoQuestoTurno = true;
                        }
                    }
                }
            } else if (casellaCorrente instanceof Tassa tassa) {
                System.out.print("\n");
                System.out.println(giocatoreCorrente.getNomeColorato() + " deve pagare una tassa di " + tassa.getImporto());

                if (giocatoreCorrente.getSaldo() < tassa.getImporto()) {
                    System.out.println(giocatoreCorrente.getNomeColorato()+ " non ha abbastanza soldi per pagare la tassa! Ha perso il gioco!");
                    giocatoreCorrente.tolgoTerreni();
                    giocatori.remove(currentIndex);
                    giocatoreRimossoQuestoTurno = true;
                } else {
                    tassa.azione(giocatoreCorrente);
                    if (giocatoreCorrente.getSaldo() < 0) {
                        giocatoreCorrente.setSaldo(0);
                        giocatoreCorrente.setInGioco(false);
                        giocatoreRimossoQuestoTurno = true;
                    }
                }
            }

            if (giocatoreRimossoQuestoTurno) {
                if (giocatori.size() == 1) {
                    System.out.println(giocatori.get(0).getNomeColorato() + " ha vinto il gioco!");
                    giocoInCorso = false;
                    break;
                } else if (giocatori.isEmpty()) {
                    System.out.println("Non ci sono più giocatori. Fine del gioco.");
                    giocoInCorso = false;
                    break;
                }
                continue;
            }

            System.out.println("premi invio per continuare");
            scanner.nextLine();
            currentIndex++;

            if (giocatori.size() == 1) {
                System.out.println(giocatori.get(0).getNomeColorato() + " ha vinto il gioco!");
                giocoInCorso = false;
            }
        }

        scanner.close();
    }
}
