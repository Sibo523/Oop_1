public class ConcretePiece implements Piece{
    private Player owner;
    private String name;

    public ConcretePiece(Player p){
        this.owner = p;
    }
    public ConcretePiece(Player p, String name){
        this.owner = p;
        this.name = name;
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
    public String getName(){
        return name;
    }
}
