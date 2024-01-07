public class King extends ConcretePiece{
    public King(Player ow){
        super(ow);
    }
    @Override
    public String getType() {
        return "♔";
    }
}
