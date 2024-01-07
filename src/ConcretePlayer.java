public class ConcretePlayer implements Player{
    private boolean who;
    private int counter;
    public ConcretePlayer(boolean who) {
        this.who = who;
        counter = 0;
    }
    public ConcretePlayer(){}


    public void increment(){
        counter += 1;
    }
    @Override
    public boolean isPlayerOne() {
        return who;
    }

    @Override
    public int getWins() {
        return counter;
    }
}
