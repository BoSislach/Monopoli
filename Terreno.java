
import java.util.Scanner;

class Terreno extends Casella {

    protected int costo;
    protected int affitto;
    protected boolean comprato;
    protected final int Max_Case = 4;
    protected int numeroCaseInCasella = 0;

    Scanner scanner = new Scanner(System.in);

    public Terreno(String nome, int costo, int affitto, int x, int y) {
        super(nome, x, y);
        this.costo = costo;
        this.affitto = affitto;
        this.comprato = false;
    }

    public int getCosto() {
        return costo;
    }

    public int getAffitto() {
        return affitto;
    }

    public void setComprato() {
        this.comprato = true;
    }

    public void stampaDescrizione() {
        System.out.println(toString());
    }

    public void pagaAffitto(Giocatore giocatore) {
        giocatore.setSaldo(giocatore.getSaldo() - affitto);
        System.out.println(giocatore.getNome() + " ha pagato un affitto di " + affitto);
    }

    public boolean compraCasa(Giocatore giocatore) {
        if (numeroCaseInCasella < Max_Case && giocatore.getSaldo() >= costo) {
            giocatore.setSaldo(giocatore.getSaldo() - costo);
            System.out.println("scegli un colore per la casa tra i seguenti:");

            for (int i = 0; i < vettoreColori.length; i++) {
                System.out.println(i + ": " + colori.get(i));
            }

            int posizione = scanner.nextInt();
            setColor(vettoreColori[posizione]);
            numeroCaseInCasella++;
            System.out.println("Casa acquistata con successo");
            colori.remove(posizione);

            return true;
        } else {
            if (numeroCaseInCasella >= Max_Case) {
                System.out.println("Non puoi comprare altre case su questo terreno.");
            } else {
                System.out.println("Saldo insufficiente per comprare una casa.");
            }
            return false;
        }
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nCosto: " + costo
                + "\nAffitto: " + affitto
                + "\nCase costruite: " + numeroCaseInCasella;

    }

}
