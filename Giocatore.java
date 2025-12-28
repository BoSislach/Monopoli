import java.util.ArrayList;

class Giocatore {
    protected String nome;
    protected int x;
    protected int y;
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
    
    
    public Giocatore(String nome, int x, int y, int saldo, ArrayList<Terreno> terreniPosseduti, boolean inGioco) {
        this.nome = nome;
        this.x = x;
        this.y = y;
        this.saldo = saldo;
        this.terreniPosseduti = terreniPosseduti;
        this.inGioco = true;
    }

    public void aumementaCounterTurni(){
        this.counterTurni++;
    }

    public void getTerreno(){
        for(Terreno t : terreniPosseduti){
            System.out.println(t.getNome());
        }
    }

    public String getNome() {
        return nome;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }   

    public boolean getStatoPrigione() {
        return this.StatoPrigione;
    }

    public void setCartaPrigione(){
        this.cartaEsciPrigione = true;
    }

    public boolean getCartaPrigione(){
        return this.cartaEsciPrigione;
    }

    public ArrayList<Terreno> getTerreniPosseduti() {
        return terreniPosseduti;
    }

    public void VaiInPrigione(Prigione prigione) {
        this.x = prigione.getX();
        this.y = prigione.getY();
    }
    
    public void setCompraCase(boolean stato){
        this.compraCase = stato;
    }

    public boolean getCompraCase(){
        return this.compraCase;
    }

    public void esciPrigione() {
       setCartaPrigione();
    }

    public boolean getSaltaCasellaMalus(){
        return this.saltaCasellaMalus;
    }

    public void setStatoPrigione(boolean stato){
        this.StatoPrigione = stato;
    }

    public void setCasellaMalus(boolean stato){
        this.saltaCasellaMalus = stato;
    }

    public boolean usaCartaPrigione() {
        if(this.cartaEsciPrigione) {
            this.cartaEsciPrigione = false;
            this.StatoPrigione = false;
            return true;
        }else{
            this.StatoPrigione = true;
            return false;
        }
    }

    public void controlloSaltaCasellaMalus(){
        if(this.saltaCasellaMalus){
            //DA FARE SOS DANIEL SOS!!!!!
            setPosizione(x+1, y);
            this.saltaCasellaMalus = false;
        }
    }

    public void prestitoBanca(int importo, Banca b) {
        this.saldo += importo;
        b.setSaldoTotale(b.getSaldoTotale() - importo);
    }

    public void setSaltaTasse(boolean stato){
        this.saltaTasse = stato;
    }

    public void ContatoreSaltaTasse(){
        if(contatoreSaltaTasse > 0){
            contatoreSaltaTasse--;
        }else{
            contatoreSaltaTasse = 3;
            setSaltaTasse(false);
        }
    }


    public void pagaAffitto(Terreno terreno) {
        int affitto = terreno.getAffitto();
        this.saldo -= affitto;

        if(this.saldo - affitto < 0){
            this.inGioco = false;
            System.out.println("Il giocatore " + this.nome + " è fuori");
        }
    }

    public boolean isInGioco() {
        return inGioco;
    }


    public boolean controlloPerditaTurno() {
        if(!this.turno) {
            return false;
        }else{
            return true;
        }
    }

    public void setPerditaTurno(boolean turno) {
        this.turno = turno;
    }

    public void setPosizione(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getPosizione() {
        return this.x + this.y;
    }

    

    public void acquistaTerreno(Terreno terreno, int costo) {
        this.terreniPosseduti.add(terreno);
        this.saldo -= costo;
    }

    public void muovi(){
        Dadi dadi = new Dadi();
        dadi.lanciaDadi();
        int somma = dadi.getSomma();
        this.x += somma;
    }
}
