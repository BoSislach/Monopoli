class Prigione extends Casella{
    protected final boolean indicatorePrigione = true;
    protected boolean contatorePrigione = false;
    protected final int TurniPrigione = 3;
    protected final int CostoCauzione = 50;
    protected int contatorePosizioneGiocatore=0;
    protected int x;
    protected int y;

    public Prigione(int x, int y, String nome){
        super(nome, x, y);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
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