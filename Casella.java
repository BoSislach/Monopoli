import java.util.ArrayList;
import java.util.Arrays;
class Casella {
    protected String nome;
    protected Casella precedente;
    protected Casella successiva;
    protected int x;
    protected int y;

    public Casella(String nome,int x,int y) {
        this.nome = nome;
        this.x = x;
        this.y = y;
    }

    public String getNome() {
        return nome;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
