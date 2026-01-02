class Partenza extends Casella {   
    protected String nome;

    public Partenza(String nome){
        super(nome);
    }

    public void PassaAlVia(Giocatore giocatore) {
        giocatore.setSaldo(giocatore.getSaldo() + 200);

    }

    @Override
    public String toString() {
        return super.toString();   
    }
    
    
}
