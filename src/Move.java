public class Move {
    //who moved
    private Position from;
    private Position to;
    private Piece piece;
    private Player player;

    //who go shit on
    private Piece dead; // if someone got killed I need to bring it back to the to position

    public Move(Position from, Position to, Piece piece, Player player, Piece died){
        this.from = from;
        this.to = to;
        this.piece = piece;
        this.player = player;
        this.dead = died;
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }

    public Piece getPiece() {
        return piece;
    }

    public Player getPlayer() {
        return player;
    }
}
