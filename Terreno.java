import java.util.Scanner;

public class Terreno extends Casella {
    protected int costo;
    protected int affitto;
    protected boolean comprato;
    protected Giocatore proprietario;
    protected final int Max_Case = 4;
    protected int numeroCaseInCasella = 0;

    Scanner scanner = new Scanner(System.in);

    public Terreno(String nome, int costo, int affitto) {
        super(nome);
        this.costo = costo;
        this.affitto = affitto;
        this.comprato = false;
    }

    public int getCosto() {
        return costo;
    }

    public Giocatore getProprietario() {
        return proprietario;
    }

    public void setProprietario(Giocatore g) {
        this.proprietario = g;
    }

    public int getAffitto() {
        return affitto;
    }

    public void setComprato() {
        this.comprato = true;
    }

    public void setNumeroCaseInCasella() {
        this.numeroCaseInCasella += 1;
    }

    public void stampaDescrizione() {
        System.out.println(toString());
    }

    public void pagaAffitto(Giocatore giocatore) {
        giocatore.setSaldo(giocatore.getSaldo() - affitto);
        System.out.println(giocatore.getNome() + " ha pagato un affitto di " + affitto);
    }


    public boolean compraCasa(Giocatore giocatore, Banca banca) {
        int posizione;
    if (numeroCaseInCasella <= Max_Case) {
        banca.vendiTerreno(this, giocatore);
        giocatore.setSaldo(giocatore.getSaldo() - costo);
        if(!giocatore.getHaComprato()){
        System.out.println("scegli un colore per la casa tra i seguenti:");

        for (int i = 0; i < colori.size(); i++) {
            System.out.println(i + ": " + colori.get(i));
        }

        do{
        System.out.print("Scegli un colore: ");
        posizione = scanner.nextInt();
        }while(posizione < 0 || posizione > colori.size()-1);
        giocatore.setColoreScelto(colori.get(posizione));
        setColore(colori.get(posizione)); 
        numeroCaseInCasella++;
        giocatore.setHaComprato(true);
        giocatore.terreniPosseduti.add(this);
        colori.remove(posizione);
        return true;
    }else{
        setColore(giocatore.getColoreScelto());
        numeroCaseInCasella++;
        giocatore.terreniPosseduti.add(this);
        return true;

    }
    } else {
        System.out.println("Non puoi comprare altre case su questo terreno.");
        return false;
        
        
    }
}

    

    @Override
    public String toString() {
        return  colore.ansi + nome + Colore.RESET;

    }

}
