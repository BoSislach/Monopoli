class Dadi {
    protected int dado1;
    protected int dado2;

    public void lanciaDadi() {
        dado1 = (int) (Math.random() * 6) + 1;
        dado2 = (int) (Math.random() * 6) + 1;
        
    }

    public int getSomma() {
        return dado1 + dado2;
    }
}
