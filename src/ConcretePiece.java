public class ConcretePiece implements Piece{
    private Player owner;
    private String name;
    private int counter = 0;
    public ConcretePiece(Player p){
        this.owner = p;
    }
    public ConcretePiece(Player p, String name){
        this.owner = p;
        this.name = name;
    }
    public void increment(){
        counter += 1;
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
