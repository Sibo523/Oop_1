public class Move {
    //who moved
    private Position from;
    private Position to;

    //who go shit on
    private Piece dead; // if someone got killed I need to bring it back to the to position
    private int x_dir,y_dir;


    public Move(Position from, Position to, Piece died, int direction_x, int direction_y){
        this.from = from;
        this.to = to;
        this.dead = died;
        this.x_dir = direction_x;
        this.y_dir = direction_y;
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }

    public int getX_dir() {
        return x_dir;
    }

    public int getY_dir() {
        return y_dir;
    }

    public Piece getDead() {
        return dead;
    }
}
