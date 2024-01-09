public class Move {
    //who moved
    private Position from;
    private Piece to;
    private Position toPos;

    //who go shit on
    private Piece [] died; // if someone got killed I need to bring it back to the to position
    private Position[] where_dead;


    public Move(Position from, Position toPos ,Piece to, Piece[] died,Position[] where_dead){
        this.from = from;
        this.to = to;
        this.died = died;
        this.where_dead = where_dead;
        this.toPos = toPos;
    }

    public Position getToPos() {
        return toPos;
    }

    public Position getFrom() {
        return from;
    }

    public Piece getTo() {
        return to;
    }
    public Piece[] getDied() {
        return died;
    }
    public Position[] getWhere_dead() {
        return where_dead;
    }
}
