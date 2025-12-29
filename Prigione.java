public class Prigione extends Casella{
    protected boolean contatorePrigione = false;
    protected final int TurniPrigione = 3;
    protected final int CostoCauzione = 50;
    protected int contatorePosizioneGiocatore=0;
    protected int posizione;

    public Prigione(String nome, int posizione){
        super(nome, posizione);
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


    public boolean contaVoltePosizioneGiocatore(Giocatore g){
        contatorePosizioneGiocatore++;
        if(contatorePosizioneGiocatore >= TurniPrigione){
          contatorePosizioneGiocatore=0;
          g.setStatoPrigione(false);
          return false;
        }else{
          g.setStatoPrigione(true);
          return true;
        }
    }

    public boolean cauzione(Giocatore g){
        if(g.getSaldo() >= CostoCauzione){
            g.setSaldo(g.getSaldo() - CostoCauzione);
            g.setStatoPrigione(false);
            System.out.println("il giocatore ha pagato la cauzione");
            return false;
        }else{
            g.setStatoPrigione(true);
            System.out.println("il giocatore non ha soldi per pagarsi la cauzione");
            return true;
        }

    }
}
