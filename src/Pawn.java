public class Pawn extends ConcretePiece {
    String color;

    public Pawn(Player p) {
        super(p);
    }

    @Override
    public String getType() {
        return "♙";
    }
}
