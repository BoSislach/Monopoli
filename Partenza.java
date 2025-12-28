class Partenza extends Casella {   
    protected String nome;
    protected String posizione;

    public Partenza(String nome,int posizione){
        super(nome,posizione);
    }

    public void PassaAlVia(Giocatore giocatore) {
        giocatore.setSaldo(giocatore.getSaldo() + 200);

    }

    @Override
    public String toString() {
        return super.toString();   
    }
    
    
}
