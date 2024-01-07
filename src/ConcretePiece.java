public class ConcretePiece implements Piece{
    private Player owner;
//    String color;

    public ConcretePiece(Player p){
        this.owner = p;
    }
    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public String getType() {
        return "";
    }
}
