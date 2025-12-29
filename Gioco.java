import java.util.ArrayList;
import java.util.Scanner;

public class Gioco {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
        Tabellone tabellone = new Tabellone();
        ArrayList<Giocatore> giocatori = new ArrayList<>();
        Banca banca = new Banca(10000);

        
        int numeroGiocatori = 0;

        do {
            System.out.print("Inserisci il numero di giocatori (1-4): ");
            numeroGiocatori = scanner.nextInt();
        } while (numeroGiocatori < 1 || numeroGiocatori > 4);

        for (int i = 0; i < numeroGiocatori; i++) {
            System.out.println("Inserisci il nome del giocatore " + (i + 1) + ":");
            String nomeGiocatore = scanner.next();
            System.out.println("inserisci carattere speciale");
            Giocatore giocatore = new Giocatore(nomeGiocatore, 0, 1500, new ArrayList<>(), true,scanner.next());
            giocatori.add(giocatore);
            System.out.println("Giocatore " + giocatore.getNome() + " creato con saldo iniziale di " + giocatore.getSaldo());
        }

        Turni turni = new Turni(giocatori);

        Dadi dadi = new Dadi();
        boolean giocoInCorso = true;
        
        while (giocoInCorso) {
            tabellone.stampaTabellone(giocatori);
            Giocatore giocatoreCorrente = turni.getGiocatoreCorrente();

            if(!giocatoreCorrente.isInGioco()) {
               turni.passaAlProssimoTurno();
                continue;

            }

            System.out.println("Turno di " + giocatoreCorrente.getNome());
            System.out.println("Saldo attuale: " + giocatoreCorrente.getSaldo());

            if(giocatoreCorrente.getStatoPrigione()){
                Prigione prigione = tabellone.getCasellaPrigione();
                if(giocatoreCorrente.cartaEsciPrigione){
                    System.out.println(giocatoreCorrente.getNome() + " vuoi usare la carta imprevisto? (s/n)");
                    String risposta = scanner.next();
                    if(risposta.equals("s")){
                        giocatoreCorrente.esciPrigione();
                        giocatoreCorrente.usaCartaPrigione();
                        System.out.println(giocatoreCorrente.getNome()+ " ha usato la carta esci di prigione");
                    }else{
                        System.out.println(giocatoreCorrente.getNome() + " non ha usato la carta esci di prigione");
                    }
                        
                    
                }else{
                    boolean saltaTurno = prigione.contaVoltePosizioneGiocatore(giocatoreCorrente);
                    if(saltaTurno){
                        System.out.println("il giocatore " + giocatoreCorrente.getNome()+ " salta il turno perche e in prigione");
                        turni.passaAlProssimoTurno();
                        continue;
                    }
                }
            }else{

            
            dadi.lanciaDadi();
            int somma = dadi.getSomma();
            System.out.println(giocatoreCorrente.getNome() + " ha lanciato i dadi e ottenuto: " + somma);
            giocatoreCorrente.setPosizione(giocatoreCorrente.getPosizione() + somma);

            if(giocatoreCorrente.getPosizione() >= 40){
                System.out.println(giocatoreCorrente.getNome() + " ha superato la partenza e riceve 200$");
                giocatoreCorrente.setSaldo(giocatoreCorrente.getSaldo() + 200);
                giocatoreCorrente.setPosizione(giocatoreCorrente.getPosizione() % 40);
            }

            Casella casellaCorrente = tabellone.getCasella(giocatoreCorrente.getPosizione());
            System.out.println(giocatoreCorrente.getNome() + " è atterrato su " + casellaCorrente.getNome());
            if(casellaCorrente instanceof Terreno terreno) {
                if(terreno.getProprietario() == null) {
                    if(giocatoreCorrente.getSaldo() >= terreno.getCosto()) {
                        System.out.println("Vuoi acquistare " + terreno.getNome() + " per " + terreno.getCosto() + "? (s/n)");
                        String risposta = scanner.next();
                        if(risposta.equals("s")) {
                            giocatoreCorrente.setSaldo(giocatoreCorrente.getSaldo() - terreno.getCosto());
                            
                            ((Terreno) casellaCorrente).compraCasa(giocatoreCorrente, banca);

                            terreno.setProprietario(giocatoreCorrente);
                            giocatoreCorrente.getTerreniPosseduti().add(terreno);
                            System.out.println(giocatoreCorrente.getNome() + " ha acquistato " + terreno.getNome());
                        }
                    } else {
                        System.out.println(giocatoreCorrente.getNome() + " non ha abbastanza soldi per acquistare " + terreno.getNome());
                    }
                } else if(!terreno.getProprietario().equals(giocatoreCorrente)){
                    System.out.println(terreno.getNome() + " è di proprietà di " + terreno.getProprietario());
                    giocatoreCorrente.pagaAffitto(terreno);
                    terreno.getProprietario().setSaldo(terreno.getProprietario().getSaldo() + terreno.getAffitto());
                    giocatoreCorrente.setSaldo(giocatoreCorrente.getSaldo() - terreno.getAffitto());
                }
            } else if(casellaCorrente instanceof Imprevisto imprevisto) {
                System.out.println(giocatoreCorrente.getNome() + " pesca una carta imprevisto: ");
                imprevisto.esegui(giocatoreCorrente,tabellone,dadi);

            }else if(casellaCorrente instanceof VaiinPrigione) {
                System.out.println(giocatoreCorrente.getNome() + " e nella casella Vai in Prigione");
                boolean controlloPrigione = ((VaiinPrigione) casellaCorrente).VaiInGalera(giocatoreCorrente);
                if(controlloPrigione){
                    System.out.println("vuoi pagare la cauzione? (s/n)" );
                    String risposta = scanner.next();
                    if(risposta.equals("s")){
                        ((Prigione) casellaCorrente).cauzione(giocatoreCorrente);
                        
                    }else{
                        
                }

                }


                



                
            }else if(casellaCorrente instanceof Tassa) {
                Tassa tassa = (Tassa) casellaCorrente;
                System.out.println(giocatoreCorrente.getNome() + " deve pagare una tassa di " + tassa.getImporto());
                if(giocatoreCorrente.getSaldo()- tassa.getImporto() < 0){
                    System.out.println(giocatoreCorrente.getNome() + " ha perso il gioco!");
                    giocatoreCorrente.setIsInGioco(false);
                }else{
                    giocatoreCorrente.setSaldo(giocatoreCorrente.getSaldo() - tassa.getImporto());
            }
            turni.passaAlProssimoTurno();
            
            }else if(casellaCorrente instanceof Partenza){
                Partenza partenza = (Partenza) casellaCorrente;

                partenza.PassaAlVia(giocatoreCorrente);
                
            }

            
        }
        }
    }
}

}
