class Prigione extends Casella{
    protected final boolean indicatorePrigione = true;
    protected boolean contatorePrigione = false;
    protected final int TurniPrigione = 3;
    protected final int CostoCauzione = 50;
    protected int contatorePosizioneGiocatore=0;
    protected int posizione;

    public Prigione(String nome, int posizione){
        super(nome, posizione);
    }

    public boolean getIndicatorePrigione(){
        return indicatorePrigione;
    }

    public int getTurniPrigione(){
        return TurniPrigione;
    }

    public int getCostoCauzione(){
        return CostoCauzione;
    }

    public boolean getContatorePrigione(){
        return contatorePrigione;
    }

    public void esciPrigione(){
        setContatorePrigione(false);
    }

    public void setContatorePrigione(boolean stato){
        contatorePrigione = stato;
        contatorePosizioneGiocatore=0;
    }

    public void contaVoltePosizioneGiocatore(){
        contatorePosizioneGiocatore++;
        if(contatorePosizioneGiocatore==3){
          contatorePosizioneGiocatore=0;
          setContatorePrigione(false);
        }else{
          setContatorePrigione(true);
        }
    }

    public boolean cauzione(Giocatore g){
        if(g.getSaldo() >= CostoCauzione){
            g.setSaldo(g.getSaldo() - CostoCauzione);
            setContatorePrigione(false);
            return false;
        }else{
            setContatorePrigione(true);
            return true;
        }

    }
}