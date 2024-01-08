public class King extends ConcretePiece{
    private Position pos;
    public King(Player ow,String name,Position p){
        super(ow,name);
        this.pos = p;
    }
    @Override
    public String getType() {
        return "♚";
    }
    public Position getPos() {
        return pos;
    }
    public String getSPos() {
        return pos.toString();
    }
    public void setPos(Position pos) {
        this.pos = pos;
    }
}
