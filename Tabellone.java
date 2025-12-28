class Tabellone {
    protected int dimensione;
    protected char[][] monopoli;
    protected final int DIM = 11;
    protected Casella[][] matrice = new Casella[DIM][DIM];

    public Tabellone(){
        dimensione = 11;
        monopoli = new char[dimensione][dimensione];
        inizializzaTabellone();
    }
    
    private void inizializzaTabellone() {
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

    private Casella creaCasella(int pos, int x, int y) {
        switch (pos) {
            case 0:
                return new Partenza();
            case 10:
                return new Prigione(x, y, "Prigione");
            default:
                return new Terreno(null, pos, pos, x, y);
        }
    }
    
    public Casella getCasella(int x, int y) {
        return matrice[x][y];
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

    public void stampaTabellone() {
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                if (matrice[i][j] != null) {
                    String nome = matrice[i][j].getNome();
                    System.out.print(String.format("[%8s] ", nome));
                } else {
                    System.out.print("[          ] ");
                }
            }
            System.out.println();
        }
    }

}
