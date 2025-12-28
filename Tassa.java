class Tassa extends Casella {
    private int importo;

    public Tassa(int importo, String nome, int x, int y) {
        super(nome, x, y);
        this.importo = importo;
    }

    public int getImporto() {
        return importo;
    }

    public void azione(Giocatore g) {
        if (g.saltaTasse) {
            System.out.println(g.getNome() + " salta il pagamento della tassa di " + importo + "$ grazie al turno extra!");
            return;
        } else {
            g.setSaldo(g.getSaldo() - importo);
        }

        Banca b = new Banca(10000);// Supponendo che la banca abbia un saldo iniziale di 10000

        // devo aggiungere aggiungi soldi alla banca
        System.out.println(g.getNome() + " paga una tassa di " + importo + "$");
    }
}

}
