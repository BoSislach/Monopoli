import java.util.ArrayList;
import java.util.Random;
public class Tabellone {
    protected int dimensione;
    protected char[][] monopoli;
    protected final int DIM = 10;
    protected Casella[][] matrice = new Casella[DIM][DIM];
    
    public Tabellone(){
        dimensione = 10;
        monopoli = new char[dimensione][dimensione];
        inizializzaTabellone();
    }
    
    protected void inizializzaTabellone() {
        int pos = 0;

        for (int j = 0; j < DIM; j++) {
            matrice[DIM - 1][j] = creaCasella(pos, DIM - 1, j);
            pos++;
        }

        for (int i = DIM - 2; i >= 0; i--) {
            matrice[i][DIM - 1] = creaCasella(pos, i, DIM - 1);
            pos++;
        }

        for (int j = DIM - 2; j >= 0; j--) {
            matrice[0][j] = creaCasella(pos, 0, j);
            pos++;
        }

        for (int i = 1; i < DIM - 1; i++) {
            matrice[i][0] = creaCasella(pos, i, 0);
            pos++;
        }
    }

    public Casella [][]getCasellaMatrice(){
        return matrice;
    }

    private Casella creaCasella(int pos, int x, int y) {
        Random rand = new Random();
        Random rand2 = new Random();
        switch (pos) {
            case 0:
                return new Partenza("PARTENZA", pos);
            case 5:
                return new Tassa(25,"TASSA",pos);
            case 10:
                return new Prigione("PRIGIONE",pos);
            case 15:
                return new Imprevisto("IMPREVISTO",pos);
            case 20:
                return new Tassa(50, "TASSA",pos);
            case 25:
                return new Imprevisto("IMPREVISTO",pos);
            case 30:
                return new VaiinPrigione("VAIPRIGIONE",pos);
            case 35: 
                return new Tassa(50, "TASSA  ",pos);
            case 40:
                return new Imprevisto("IMPREVISTO",pos);
            default:
                int costo = rand.nextInt(25, 100);
                int affitto = rand2.nextInt(15, 50);
                return new Terreno("terreno", pos, costo, affitto);
        }
    }

    public int getMatriceLength() {
        return matrice.length;
    }

    public int getMatriceWidth() {
        return matrice[0].length;
    }
    
    public Casella getCasella(int posizione) {
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                Casella casella = matrice[i][j];
                if (casella != null && casella.getPosizione() == posizione) {
                    return casella;
                }
            }
        }
        return null; 
    }

    public Prigione getCasellaPrigione() {
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                Casella casella = matrice[i][j];
                if (casella instanceof Prigione) {
                    return (Prigione) casella;
                }
            }
        }
        return null; 
    }

    public void stampaTabellone(ArrayList<Giocatore> giocatori) {
    int larghezza = 10;
    for (int i = 0; i < DIM; i++) {
        for (int j = 0; j < DIM; j++) {
            if (matrice[i][j] != null) {
                String nomeCasella = centra(matrice[i][j].getNome(), larghezza);
                Colore colore = matrice[i][j].getColore();
                String simboliGiocatori = "";
                for (int c =0 ;c < giocatori.size();c++) {
                    if (giocatori.get(c).getPosizione() == matrice[i][j].getPosizione()) {
                        simboliGiocatori += giocatori.get(c).getSimbolo();
                    }
                }
                if (!simboliGiocatori.equals("")) {
                    System.out.print("[" + centra(simboliGiocatori, larghezza) + "]");
                } else {
                    if (matrice[i][j] instanceof Terreno && colore != null) {
                        System.out.print("[" + colore.ansi + nomeCasella + Colore.RESET + "]");
                    } else if (!(matrice[i][j] instanceof Terreno)) {
                        System.out.print("[" + Colore.BIANCO.ansi + nomeCasella + Colore.RESET + "]");
                    } else {
                        System.out.print("[" + nomeCasella + "]");
                    }
                }
            } else {
                System.out.print("[" + " ".repeat(larghezza) + "]");
            }
        }
        System.out.println();
    }
}



private String centra(String testo, int larghezza) {
    if (testo.length() >= larghezza) {
        return testo.substring(0, larghezza);
    }

    int spaziTotali = larghezza - testo.length();
    int spaziSinistra = spaziTotali / 2;
    int spaziDestra = spaziTotali - spaziSinistra;

    return " ".repeat(spaziSinistra) + testo + " ".repeat(spaziDestra);
}


}
