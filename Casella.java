import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
class Casella {
    protected String nome;
    protected Casella precedente;
    protected Casella successiva;
    protected int x;
    protected int y;
    protected Color color;

    protected final Color coloreBase = Color.LIGHT_GRAY;

    public Casella(String nome,int x,int y) {
        this.nome = nome;
        this.x = x;
        this.y = y;
        this.color = coloreBase;

    }

    protected Color[] vettoreColori = {
            Color.RED,
            Color.BLUE,
            Color.GREEN,
            Color.YELLOW
    };
    protected ArrayList<Color> colori = new ArrayList<>(Arrays.asList(vettoreColori));
    

    Color white = Color.WHITE; //colore base

    public String getNome() {
        return nome;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    @Override
    public String toString() {
        String coloreANSI = "";
        String reset = "\u001B[0m";

        if(getColor().equals(Color.RED)){
            coloreANSI = "\u001B[41m";

        } else if(getColor().equals(Color.BLUE)){
            coloreANSI = "\u001B[44m";

        } else if(getColor().equals(Color.GREEN)){
            coloreANSI = "\u001B[42m";

        } else if(getColor().equals(Color.YELLOW)){
            coloreANSI = "\u001B[43m";

        } else{
            coloreANSI = "\u001B[47m";
        }
        return coloreANSI + " " + nome + " " + reset;
    }
}
