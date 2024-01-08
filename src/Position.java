public class Position {
    private int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }
    public boolean equals(Position p){
        return this.x == p.getX() && this.y == p.getY();
    }
    public boolean half_equal(Position p){
        return this.x == p.getX() || this.y == p.getY();
    }

    public int getY() {
        return this.y;
    }
    public String toString(){
        return "("+this.x+","+this.y+")";
    }
}