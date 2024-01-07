public class ConcretePlayer implements Player{
    private boolean whos;
    private int counter;
    public ConcretePlayer(boolean who){
        this.whos = who;
        counter = 0;
    }

    public void increment(){
        counter += 1;
    }
    @Override
    public boolean isPlayerOne() {
        return whos;
    }

    @Override
    public int getWins() {
        return counter;
    }
}
