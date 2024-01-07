public class Pawn extends ConcretePiece {
    private String name;
    public Pawn(Player p,String name) {
        super(p);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return "♙";
    }
}
