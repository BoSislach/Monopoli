import java.util.Random;
public class Imprevisto extends Casella{

    public Imprevisto(String nomeCasella,int posizione){
        super(nomeCasella, posizione);


        
    }
    public void esegui(Giocatore giocatore, Tabellone t, Dadi dadi) {
    AzioneImprevisto azione = getAzioneCasuale();
    System.out.println(azione.getDescrizioneAzione());
    azione.eseguiAzione(giocatore, t, dadi);
}


    public enum AzioneImprevisto{

    BONUS("hai ricevuto un bonus di 100 $"){
        @Override
        public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi){
            giocatore.setSaldo(giocatore.getSaldo() + 100);
        }
    },

    Paga_tassa_imprevisto50("paga una tassa imprevisto di 50 $"){
        @Override
        public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi){
            giocatore.setSaldo(giocatore.getSaldo() - 50);
        }
    },
    VaiInPrigione("vai in prigione"){
        @Override
        public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi){
        Prigione prigione = t.getCasellaPrigione();
        giocatore.VaiInPrigione(prigione);
        giocatore.setStatoPrigione(true);
    }
    },
    AvanzaDi5Posizioni("avanza di 5 posizioni"){
        @Override
        public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi){
            //aggiunta controllo se sei ai bordi DANIEL SOS!!!!!!?
            if (giocatore.getPosizione() + 5 > 39) {
                giocatore.setPosizione((giocatore.getPosizione() + 5) - 40);
                giocatore.setSaldo(giocatore.getSaldo() + 200);
            }
            giocatore.setPosizione(giocatore.getPosizione() + 5);
        }
    },
    Paga_tassa_imprevisto100("paga una tassa imprevisto di 100 $"){
        @Override
        public void eseguiAzione(Giocatore giocatore, Tabellone t,Dadi dadi){
            giocatore.setSaldo(giocatore.getSaldo() - 100);
        }
    },
    PerdiTurno("perdi un turno"){
        @Override
        public void eseguiAzione(Giocatore giocatore, Tabellone t,Dadi dadi){
            giocatore.setPerditaTurno(true);
        }
    },

    PagaPerOgniTerreno50("paga per ogni terreno posseduto 50 $"){
        @Override
        public void eseguiAzione(Giocatore giocatore, Tabellone t,Dadi dadi){
            int numeroTerreni = giocatore.getTerreniPosseduti().size();
            giocatore.setSaldo(giocatore.getSaldo() - (50 * numeroTerreni));
           giocatore.usaCartaPrigione();
        }
    },

    EsciGratisPrigione("hai ottenuto una carta esci gratis prigione"){
        @Override
        public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi){
            giocatore.esciPrigione();
            giocatore.usaCartaPrigione();
        }
    },

    VaiAllaPartenza("vai alla partenza"){
        @Override
        public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi){
            giocatore.setPosizione(0);
            giocatore.setSaldo(giocatore.getSaldo() + 200);
        }
    },

    TornaIndietro3Posizioni("torna indietro di 3 posizioni"){
        @Override
        public void eseguiAzione(Giocatore giocatore, Tabellone t,Dadi dadi){
            //FUNZIOE DELLO SPOSTAMENTO INDIETRO DANIEL SOS!!!!!!!
            giocatore.setPosizione(giocatore.getPosizione() - 3);
        }
    },
    ManutenzioneCasa25("paga una manutenzione di 25 $ per ogni casa posseduta"){
        @Override
        public void eseguiAzione(Giocatore giocatore, Tabellone t,Dadi dadi){
            int numeroCase = giocatore.terreniPosseduti.size();
            giocatore.setSaldo(giocatore.getSaldo() - (25 * numeroCase));
        }
    },
    GuastoImprovvisoCasa("paga 80 $ per un guasto improvviso alla casa"){
        @Override
        public void eseguiAzione(Giocatore giocatore, Tabellone t,Dadi dadi){
            giocatore.setSaldo(giocatore.getSaldo() - 80);
        }
    },
    SostaCompraCase("non puoi comprare case per 1 turno"){
        @Override
        public void eseguiAzione(Giocatore giocatore, Tabellone t,Dadi dadi){
            giocatore.setCompraCase(false);
        }
    },
    EreditaCasa200("hai ereditato 200 $ da tua zia"){
        @Override
        public void eseguiAzione(Giocatore giocatore, Tabellone t,Dadi dadi){
            giocatore.setSaldo(giocatore.getSaldo() + 200);
        }
    },
    RimborsoTasse20("ricevi un rimborso tasse di 20 $"){
        @Override
        public void eseguiAzione(Giocatore giocatore, Tabellone t,Dadi dadi){
            giocatore.setSaldo(giocatore.getSaldo() + 20);
        }
    },
    Avanza1Posizioni("avanza di 1 posizioni"){
        @Override
        public void eseguiAzione(Giocatore giocatore, Tabellone t,Dadi dadi){
            giocatore.setPosizione(giocatore.getPosizione() + 1);
        }
    },

    TurnoExtra("hai ottenuto un turno extra"){
        public void eseguiAzione(Giocatore giocatore, Tabellone t,Dadi dadi){
           
           
        }
    },

    CostruzioneBonus50("ricevi un bonus di 50 $ per la costruzione di edifici"){
        public void eseguiAzione(Giocatore giocatore, Tabellone t,Dadi dadi){
            giocatore.setSaldo(giocatore.getSaldo() + 50);
        }
    },

    ScudoProtettivo("hai ottenuto uno scudo protettivo, sei immune alle tasse per 3 turni"){
        public void eseguiAzione(Giocatore giocatore, Tabellone t,Dadi dadi){
            giocatore.setSaltaTasse(true);
        }
    },

    SaltaCasellaMalusProssima("salta la prossima casella malus"){
        public void eseguiAzione(Giocatore giocatore, Tabellone t,Dadi dadi){
            giocatore.setCasellaMalus(true);
        }

    };
    
    private final String descrizioneAzione;

    AzioneImprevisto(String descrizioneAzione){
        this.descrizioneAzione = descrizioneAzione;
    }

    public String getDescrizioneAzione(){
        return descrizioneAzione;
    }

    public abstract void eseguiAzione(Giocatore giocatore, Tabellone t,Dadi dadi);

    }
    //DA USARE NEL MAIN
    public AzioneImprevisto getAzioneCasuale(){
        Random rand = new Random();
        AzioneImprevisto[] azioni = AzioneImprevisto.values();
        int indiceCasuale = rand.nextInt(azioni.length);
        return azioni[indiceCasuale];
    }

}