public class Pawn extends ConcretePiece {

    public Pawn(Player p) {
        super(p);
    }

    @Override
    public String getType() {
        return "♙";
    }
}
