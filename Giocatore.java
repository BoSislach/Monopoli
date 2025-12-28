import java.util.ArrayList;

class Giocatore {
    protected String nome;
    protected int posizione;
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

    public Giocatore(String nome, int posizione, int saldo,
                     ArrayList<Terreno> terreniPosseduti, boolean inGioco, String simbolo) {
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

    public int getPosizione() {
        return posizione;
    }

    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    public int getSaldo() {
        return saldo;
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

    public String getSimbolo(){
        return simbolo;
    }

    public boolean controlloPerditaTurno() {
        return this.turno;
    }

    public void setPerditaTurno(boolean turno) {
        this.turno = turno;
    }

    public void muovi() {
        Dadi dadi = new Dadi();
        dadi.lanciaDadi();
        int somma = dadi.getSomma();
        this.posizione += somma;
    }

    public void controlloSaltaCasellaMalus() {
        if (this.saltaCasellaMalus) {
            this.posizione++;
            this.saltaCasellaMalus = false;
        }
    }

    public ArrayList<Terreno> getTerreniPosseduti() {
        return terreniPosseduti;
    }

    public void getTerreno() {
        for (Terreno t : terreniPosseduti) {
            System.out.println(t.getNome());
        }
    }

    public void pagaAffitto(Terreno terreno) {
        int affitto = terreno.getAffitto();
        this.saldo -= affitto;
        if (this.saldo < 0) {
            this.inGioco = false;
            System.out.println("Il giocatore " + this.nome + " è fuori");
        }
    }

    public boolean getStatoPrigione() {
        return StatoPrigione;
    }

    public void setStatoPrigione(boolean stato) {
        this.StatoPrigione = stato;
    }

    public void setCartaPrigione() {
        this.cartaEsciPrigione = true;
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

    public void esciPrigione() {
        setCartaPrigione();
    }

    public void VaiInPrigione(Prigione prigione) {
        this.posizione = prigione.getPosizione();
        this.StatoPrigione = true;
    }

    public boolean getSaltaCasellaMalus() {
        return saltaCasellaMalus;
    }

    public void setCasellaMalus(boolean stato) {
        this.saltaCasellaMalus = stato;
    }

    public void setSaltaTasse(boolean stato) {
        this.saltaTasse = stato;
    }

    public void ContatoreSaltaTasse() {
        if (contatoreSaltaTasse > 0) {
            contatoreSaltaTasse--;
        } else {
            contatoreSaltaTasse = 3;
            setSaltaTasse(false);
        }
    }
    

    public void prestitoBanca(int importo, Banca b) {
        this.saldo += importo;
        b.setSaldoTotale(b.getSaldoTotale() - importo);
    }

    public void setCompraCase(boolean stato) {
        this.compraCase = stato;
    }

    public boolean getCompraCase() {
        return compraCase;
    }
}
