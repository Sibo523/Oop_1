public class ConcretePiece implements Piece{
    private Player owner;

    public ConcretePiece(Player p){
        this.owner = p;
    }
    public ConcretePiece(){}
    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public String getType() {
        return "";
    }
}
