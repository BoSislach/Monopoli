import java.util.ArrayList;

public class Giocatore {
    protected String nome;
    protected Casella posizione;
    protected int saldo;
    protected boolean inGioco;
    protected ArrayList<Terreno> terreniPosseduti;
    protected boolean turno = false;
    protected boolean cartaEsciPrigione = false;
    protected boolean StatoPrigione = false;
    protected boolean compraCase = true;
    protected int counterTurni = 0;
    protected boolean saltaCasellaMalus = false;
    protected boolean saltaTasse = false;
    protected int contatoreSaltaTasse = 3;
    protected boolean VaiInPrigione = false;
    protected String simbolo;
    protected boolean Hacomprato = false;
    protected Colore coloreScelto = null;
    protected int turniInPrigione = 0;

    public Giocatore(String nome, Casella posizione, int saldo, ArrayList<Terreno> terreniPosseduti, boolean inGioco,
            String simbolo) {
        this.nome = nome;
        this.posizione = posizione;
        this.saldo = saldo;
        this.terreniPosseduti = terreniPosseduti;
        this.inGioco = inGioco;
        this.simbolo = simbolo;
    }

    public String getNome() {
        return nome;
    }

    public String getNomeColorato() {
        if (coloreScelto != null) {
            return coloreScelto.ansi + nome + Colore.RESET;
        }
        return nome;
    }

    public Casella getPosizione() {
        return posizione;
    }

    public void setPosizione(Casella posizione) {
        this.posizione = posizione;
    }

    public int getSaldo() {
        return saldo;
    }

    public boolean setIsInGioco(boolean stato) {
        this.inGioco = stato;
        return inGioco;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public boolean isInGioco() {
        return inGioco;
    }

    public void aumementaCounterTurni() {
        this.counterTurni++;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public boolean controlloPerditaTurno() {
        return this.turno;
    }

    public void setPerditaTurno(boolean turno) {
        this.turno = turno;
    }

    public void muovi(int passi, Tabellone t) {
        Casella attuale = posizione;
        for (int i = 0; i < passi; i++) {
            attuale = t.getProssimaCasella(attuale);
        }
        posizione = attuale;
    }

    public void controlloSaltaCasellaMalus() {
        if (this.saltaCasellaMalus) {
            this.saltaCasellaMalus = false;
        }
    }

    public void setHaComprato(boolean stato) {
        this.Hacomprato = stato;
    }

    public boolean getHaComprato() {
        return Hacomprato;
    }

    public ArrayList<Terreno> getTerreniPosseduti() {
        return terreniPosseduti;
    }

    public void getTerreno() {
        for (Terreno t : terreniPosseduti) {
            System.out.println(t.getNome());
        }
    }

    public void setColoreScelto(Colore posizione) {
        coloreScelto = posizione;
    }

    public Colore getColoreScelto() {
        return coloreScelto;
    }

    public int pagaAffitto(Terreno terreno) {
        int affitto = terreno.getAffitto();
        this.saldo -= affitto;
        return affitto;
    }

    public boolean getStatoPrigione() {
        return StatoPrigione;
    }

    public void setStatoPrigione(boolean stato) {
        this.StatoPrigione = stato;
    }

    public void setCartaPrigione(boolean stato) {
        this.cartaEsciPrigione = stato;
    }

    public boolean getCartaPrigione() {
        return cartaEsciPrigione;
    }

    public boolean usaCartaPrigione() {
        if (this.cartaEsciPrigione) {
            this.cartaEsciPrigione = false;
            this.StatoPrigione = false;
            return true;
        } else {
            this.StatoPrigione = true;
            return false;
        }
    }

    public void VaiInPrigione(Prigione prigione) {
        this.posizione = prigione;
        this.StatoPrigione = true;
        this.turniInPrigione = prigione.getTurniPrigione();
    }

    public boolean getSaltaTasse() {
        return saltaTasse;
    }

    public void setCasellaMalus(boolean stato) {
        this.saltaCasellaMalus = stato;
    }

    public void setSaltaTasse(boolean stato) {
        this.saltaTasse = stato;
    }

    public void prestitoBanca(int importo, Banca b) {
        this.saldo += importo;
        b.setSaldoTotale(b.getSaldoTotale() - importo);
    }

    public void setCompraCase(boolean stato) {
        this.compraCase = stato;
    }

    public String toString() {
        return nome;
    }

    public boolean getCompraCase() {
        return compraCase;
    }

    public boolean setInGioco(boolean stato) {
        this.inGioco = stato;
        return inGioco;
    }

    public void tolgoTerreni() {
        for (Terreno t : terreniPosseduti) {
            t.setProprietario(null);
            t.comprato = false;
            t.numeroCaseInCasella = 0;

            if (t.getColore() != null) {
                Casella.colori.add(t.getColore());
                t.setColore(null);
            }
        }
        terreniPosseduti.clear();
    }
}
