public class King extends ConcretePiece{
    Position pos;
    public King(Player ow){
        super(ow);
//        this.pos = pos;
    }
    @Override
    public String getType() {
        return "♔";
    }
}
