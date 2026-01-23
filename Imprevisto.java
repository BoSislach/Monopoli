import java.util.Random;

public class Imprevisto extends Casella {
    public Imprevisto(String nomeCasella) {
        super(nomeCasella);
    }

    public void esegui(Giocatore giocatore, Tabellone t, Dadi dadi) {
        AzioneImprevisto azione = getAzioneCasuale();
        System.out.println(azione.getDescrizioneAzione());
        azione.eseguiAzione(giocatore, t, dadi);
    }

    public enum AzioneImprevisto {
        BONUS("hai ricevuto un bonus di 100 $") {
            @Override
            public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi) {
                giocatore.setSaldo(giocatore.getSaldo() + 100);
            }
        },
        Paga_tassa_imprevisto50("paga una tassa imprevisto di 50 $") {
            @Override
            public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi) {
                giocatore.setSaldo(giocatore.getSaldo() - 50);
            }
        },
        VaiInPrigione("vai in prigione") {
            @Override
            public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi) {
                Prigione prigione = t.getCasellaPrigione();
                giocatore.VaiInPrigione(prigione);
                giocatore.setStatoPrigione(true);
            }
        },
        AvanzaDi5Posizioni("avanza di 5 posizioni") {
            @Override
            public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi) {
                int i = 5;
                giocatore.muovi(i, t);
            }
        },
        Paga_tassa_imprevisto100("hai pagato 100 $ per delle scarpe nike air force super mega iper cool!!!") {
            @Override
            public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi) {
                giocatore.setSaldo(giocatore.getSaldo() - 100);
            }
        },
        PerdiTurno("perdi un turno") {
            @Override
            public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi) {
                giocatore.setPerditaTurno(true);
            }
        },
        PagaPerOgniTerreno50("paga per ogni terreno posseduto 50 $") {
            @Override
            public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi) {
                int numeroTerreni = giocatore.getTerreniPosseduti().size();
                giocatore.setSaldo(giocatore.getSaldo() - (50 * numeroTerreni));
                giocatore.usaCartaPrigione();
            }
        },
        EsciGratisPrigione("hai ottenuto una carta esci gratis prigione") {
            @Override
            public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi) {
                giocatore.setCartaPrigione(true);
            }
        },
        VaiAllaPartenza("vai alla partenza") {
            @Override
            public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi) {
                if (!(giocatore.getPosizione() instanceof Partenza)) {
                    giocatore.setPosizione(t.getCasellaPartenza());
                    giocatore.setSaldo(giocatore.getSaldo() + 200);
                    System.out.println("Ricevi 200 $ per aver passato dalla partenza");
                } else {
                    System.out.println("Sei già alla partenza, non ricevi i 200 $");
                }
            }
        },
        TornaIndietro3Posizioni("torna indietro di 3 posizioni") {
            @Override
            public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi) {
                giocatore.muovi(-3, t);
            }
        },
        ManutenzioneCasa25("paga una manutenzione di 25 $ per ogni casa posseduta") {
            @Override
            public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi) {
                int numeroCase = giocatore.terreniPosseduti.size();
                giocatore.setSaldo(giocatore.getSaldo() - (25 * numeroCase));
            }
        },
        GuastoImprovvisoCasa("paga 80 $ per un guasto improvviso alla casa") {
            @Override
            public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi) {
                giocatore.setSaldo(giocatore.getSaldo() - 80);
            }
        },
        SostaCompraCase("non puoi comprare case per 1 turno") {
            @Override
            public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi) {
                giocatore.setCompraCase(false);
            }
        },
        EreditaCasa200("hai ereditato 200 $ da tua zia") {
            @Override
            public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi) {
                giocatore.setSaldo(giocatore.getSaldo() + 200);
            }
        },
        RimborsoTasse20("ricevi un rimborso tasse di 20 $") {
            @Override
            public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi) {
                giocatore.setSaldo(giocatore.getSaldo() + 20);
            }
        },
        Avanza1Posizioni("avanza di 1 posizioni") {
            @Override
            public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi) {
                giocatore.setPosizione(giocatore.getPosizione().getSuccessiva());
            }
        },
        CostruzioneBonus50("ricevi un bonus di 50 $ per la costruzione di edifici") {
            public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi) {
                giocatore.setSaldo(giocatore.getSaldo() + 50);
            }
        },
        Suicidati("ti sei suicidato, sciocchino") {
            public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi) {
                giocatore.setIsInGioco(false);
            }
        },
        TeletrasportoCasuale("teletrasporto ad una casella casuale") {
            public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi) {
                Random rand = new Random();
                int posizioneCasuale = rand.nextInt(0, t.getDimensione() + 1);
                giocatore.muovi(posizioneCasuale, t);
            }
        },
        CartaSaltaTasse("hai ottenuto una carta salta tasse") {
            public void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi) {
                if(giocatore.getSaltaTasse()){
                    System.out.println("hai già una carta salta tasse");
                }else{
                    giocatore.setSaltaTasse(true);
                }
                
            }
        };

        private final String descrizioneAzione;

        AzioneImprevisto(String descrizioneAzione) {
            this.descrizioneAzione = descrizioneAzione;
        }

        public String getDescrizioneAzione() {
            return descrizioneAzione;
        }

        public abstract void eseguiAzione(Giocatore giocatore, Tabellone t, Dadi dadi);
    }

    public AzioneImprevisto getAzioneCasuale() {
        Random rand = new Random();
        AzioneImprevisto[] azioni = AzioneImprevisto.values();
        int indiceCasuale = rand.nextInt(0, azioni.length);
        return azioni[indiceCasuale];
    }
}