class Banca {
    protected int saldoTotale;
    
    public Banca(int saldoIniziale) {
        this.saldoTotale = saldoIniziale;
    }
    
    public int getSaldoTotale() {
        return saldoTotale;
    }

    public void aggiungiSaldoBanca(int saldo){
        saldoTotale = saldoTotale + saldo;
    }

    public void togliSaldoBanca(int saldo){
        saldoTotale = saldoTotale - saldo; 
    }

    public void setSaldoTotale(int saldoTotale) {
        this.saldoTotale = saldoTotale;
    }

    public void stampaSaldoBanca() {
        System.out.println("Saldo totale della banca: " + saldoTotale);
    }

    public boolean vendiTerreno(Terreno terreno, Giocatore giocatore) {
        if (terreno.comprato) {
            System.out.println("Terreno già acquistato.");
            return false;
        }

        // ho comprato il terreno
        terreno.setComprato();

        System.out.println(giocatore.getNome() + " ha acquistato il terreno " + terreno.getNome());
        return true;
    }

    public void pagaGiocatore(Giocatore giocatore, int importo) {
        if (saldoTotale >= importo) {
            togliSaldoBanca(importo);
            giocatore.setSaldo(giocatore.getSaldo() + importo);
        }
    }
}

