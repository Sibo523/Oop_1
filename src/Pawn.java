public class Pawn  implements Piece{
    boolean owner;
    String color;

    public Pawn(boolean ow){
        this.owner = ow;
    }
    @Override
    public Player getOwner() {
        return new ConcretePlayer(owner);
    }

    @Override
    public String getType() {
        return "♙";
    }
}
