public class VaiinPrigione extends Casella{

    protected final int MaxPosizione = 3;
    protected int contatoreGiocatoreVaiInPrigione =0;

    public VaiinPrigione(String nome,int posizione){
        super(nome,posizione);
        
    }

    public boolean VaiInGalera(Giocatore giocatore){
           contatoreGiocatoreVaiInPrigione++;
           if(contatoreGiocatoreVaiInPrigione == MaxPosizione){
                giocatore.setPosizione(10);
                giocatore.setStatoPrigione(true);
                System.out.println(giocatore.getNome() + " è andato in prigione");
                contatoreGiocatoreVaiInPrigione = 0;
                return true;
           }else{
            return false;
           }
       
        
    }

    public String toString(){
        return super.toString();
    }

    

}
