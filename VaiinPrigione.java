class VaiinPrigione extends Casella{

    protected final int MaxPosizione = 3;
    protected int contatoreGiocatoreVaiInPrigione =0;

    public VaiinPrigione(String nome,int posizione){
        super(nome,posizione);
        
    }

    public void VaiInGalera(Giocatore giocatore){
        if(giocatore.getPosizione() == 30){
           contatoreGiocatoreVaiInPrigione++;
           if(contatoreGiocatoreVaiInPrigione == 3){
                giocatore.setPosizione(10);
                giocatore.setStatoPrigione(true);
                System.out.println(giocatore.getNome() + " è andato in prigione");
                contatoreGiocatoreVaiInPrigione = 0;
           }
       
        }
    }
    public String toString(){
        return super.toString();
    }

    

}