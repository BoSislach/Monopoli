class Partenza extends Casella {

    public Partenza() { 
        super("Partenza", 0, 0); 
        }

    public void PassaAlVia(Giocatore giocatore) {
        giocatore.setSaldo(giocatore.getSaldo() + 200);

    }

    @Override
    public String toString() {
        return super.toString();   
    }
    
    
}