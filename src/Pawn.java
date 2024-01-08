public class Pawn extends ConcretePiece {

    public Pawn(Player p,String name) {
        super(p,   name);
    }


    @Override
    public String getType() {
        return "♙";
    }
}
