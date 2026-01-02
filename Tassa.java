public class Tassa extends Casella {
    private int importo;

    public Tassa(int importo, String nome) {
        super(nome);
        this.importo = importo;
    }

    public int getImporto() {
        return importo;
    }

    public void azione(Giocatore g) {
        if (g.saltaTasse) {
            System.out.println(g.getNome() + " salta il pagamento della tassa di " + importo + "$ grazie al turno extra!");
            return;
        } 
        g.setSaldo(g.getSaldo() - importo);


        // devo aggiungere aggiungi soldi alla banca
        System.out.println(g.getNome() + " paga una tassa di " + importo + "$");
    }

    public String toString() {
        return super.toString() + " [Tassa: " + importo + "$]";
    }
}
