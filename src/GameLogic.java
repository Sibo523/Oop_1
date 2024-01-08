import java.util.Stack;


public class GameLogic implements PlayableLogic {
    private ConcretePlayer def, atck;
    private King king; // faster to know if the game finished
    private boolean atck_turn;
    private Stack<Move> moves;

    private ConcretePiece[][] Board = new ConcretePiece[11][11];

    public GameLogic() {
        create_players();
        reset();
    }

    @Override
    public boolean move(Position a, Position b) {

        Piece from = getPieceAtPosition(a);
        Piece to = getPieceAtPosition(b);
        if (!inBoard(b)) return false; // if the destination is out of bounds
        if (from == null || to != null) return false; // no Piece to move or there is a Piece in the destination
        if (from.getOwner().isPlayerOne() != atck_turn) return false; // if it's not your turn
        if (a.equals(b)) return false; // if you didn't move

        if (from.getType() != "♚" && ((b.getX() == 0 && (b.getY() == 10 || b.getY() == 0)) || (b.getX() == 10 && (b.getY() == 10 || b.getY() == 0))))
            return false;

        if (a.half_equal(b)) { // if it's a straight move
            //need to check for clear path
            int xDirection = Integer.compare(b.getX(), a.getX());
            int yDirection = Integer.compare(b.getY(), a.getY());

            if (a.getX() == b.getX()) {
                int di = a.getY() - b.getY();
                if (di < 0) {
                    for (int i = a.getY() + 1; i < a.getY() + di; i++) {
                        if (Board[a.getX()][i] != null)
                            return false;
                    }
                } else {
                    for (int i = a.getY() - 1; i > a.getY() + di; i--) {
                        if (Board[a.getX()][i] != null)
                            return false;
                    }
                }
            } else {
                int di = a.getX() - b.getX();
                if (di < 0) {
                    for (int i = a.getX() + 1; i < a.getX() + di; i++) {
                        if (Board[i][a.getY()] != null)
                            return false;
                    }
                } else {
                    for (int i = a.getX() - 1; i > a.getX() + di; i--) {
                        if (Board[i][a.getY()] != null)
                            return false;
                    }
                }
            }
            Position [] killed_Pos = new Position[0];
            Piece [] Killed_Piece = new Piece[0];
            if (from.getType() != "♚") {
                killed_Pos = find_kill(b, from);
                Killed_Piece = Pos_to_Piece(killed_Pos);
                killer(killed_Pos);
            }

            return move(a,b,from,Killed_Piece,killed_Pos);
        }
        return false;
    }

    @Override
    public Piece getPieceAtPosition(Position position) {
        return Board[position.getX()][position.getY()];
    }

    @Override
    public Player getFirstPlayer() {
        return atck;
    }

    @Override
    public Player getSecondPlayer() {
        return def;
    }

    @Override
    public boolean isGameFinished() {
        if( check_for_win_attack() ) {
            atck.increment();
            System.out.println(atck.getWins());
            return true;
        }
        if( check_for_win_defend() ) {
            def.increment();
            System.out.println(def.getWins());
            return true;
        }
        return false;
    }

    @Override
    public boolean isSecondPlayerTurn() {
        return !atck_turn;
    }

    @Override
    public void reset() {
        settings_reset();
        attack_reset();
        defend_reset();
//        print_board();
    }

    @Override
    public void undoLastMove() {
        if (moves.isEmpty()) {
            System.out.println("no moves to undo"); // bro be trolling me
            return;
        }
        Move last = moves.pop();
        Position [] pos = last.getWhere_dead();
        Piece [] pieces = last.getDied();
        set_onBoard(last.getFrom(),getPieceAtPosition(last.getTo()));
        set_onBoard(last.getTo(),null);
        for (int i = 0; i < pos.length; i++) {
            if (pos[i] == null) {
                break;
            }
            set_onBoard(pos[i],pieces[i]);
        }
        atck_turn = !atck_turn;
    }

    @Override
    public int getBoardSize() {
        return Board.length;
    }

    //////////////////////////////////////////////////PRIVATE METHODS//////////////////////////////////////////////////
    private void set_onBoard(Position a,Piece b){
        Board[a.getX()][a.getY()] = (ConcretePiece) b;
    }
    private void create_players() {
        this.atck = new ConcretePlayer(true);
        this.def = new ConcretePlayer(false);
    }

    private void print_board() {
        for (int i = 0; i < Board.length; i++) {
            for (int j = 0; j < Board.length; j++)
                if (Board[j][i] == null) {
                    System.out.print(" ");
                } else {
                    Piece x = (getPieceAtPosition(new Position(j, i)));
                    System.out.print(Board[j][i].getName());
                }
            System.out.println();
        }
    }

    private void settings_reset() {
//        create_players(); // create two players atck and def
        moves = new Stack<>(); // move history for undo
        atck_turn = true; // switch turns every move
        this.king = new King(def, "K7", new Position(5, 5));
    }

    private void attack_reset() {
        for (int i = 0; i < Board.length; i++) {
            for (int j = 0; j < Board.length; j++) {
                Board[i][j] = null;
            }
        }
        String a = "A";
        for (int i = 3; i < 8; i++) {
            Board[i][0] = new Pawn(atck, a + Integer.toString(i - 2));
            Board[i][10] = new Pawn(atck, a + Integer.toString(i + 17));
        }
        //chopchick
        Board[5][1] = new Pawn(atck, "A6");
        Board[5][9] = new Pawn(atck, "A19");
        //column
        int index = 7;
        for (int i = 3; i < 8; i++) {
            Board[0][i] = new Pawn(atck, a + Integer.toString(index));
            index += 1;
            if (index == 12) {
                Board[1][5] = new Pawn(atck, a + Integer.toString(index));
                index += 1;
                Board[9][5] = new Pawn(atck, a + Integer.toString(index));
                index += 1;
            }
            Board[10][i] = new Pawn(atck, a + Integer.toString(index));
            index += 1;
        }
    }

    private void defend_reset() {
        // Defenders
        //king
        Board[5][5] = king;
        //chopchick
        Board[3][5] = new Pawn(def, "D5");
        Board[7][5] = new Pawn(def, "D9");
        //columns next to king
        int index = 2;
        for (int i = 4; i < 7; i++) {
            Board[4][i] = new Pawn(def, "D" + Integer.toString(index));
            index += 2;
            Board[6][i] = new Pawn(def, "D" + Integer.toString(index));
            index += 2;
        }
        Board[5][3] = new Pawn(def, "D1");
        Board[5][4] = new Pawn(def, "D3");
        Board[5][6] = new Pawn(def, "D11");
        Board[5][7] = new Pawn(def, "D13");
    }

    private boolean move(Position from, Position to, Piece save, Piece [] died,Position[] where_dead){
        moves.push(new Move(from, to, died, where_dead));
        if (save.getType() == "♚") {
            king.setPos(to);
        }
        Board[from.getX()][from.getY()] = null;
        Board[to.getX()][to.getY()] = (ConcretePiece) save;
        atck_turn = !atck_turn;
        System.out.println("moved");

        System.out.println(moves.size());
        return true;
    }

    private void killer(Position[] killed) {
        for (int i = 0; i < killed.length; i++) {
            if (killed[i] != null) {
                Board[killed[i].getX()][killed[i].getY()] = null;
            }
        }
    }

    //TODO: check for kill
    private Position[] find_kill(Position to, Piece from) { // we already checked if it's pawn or king
        int[] x = {0, -1, 0, 1};
        int[] y = {-1, 0, 1, 0};
        Position[] killed = new Position[3];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            Position temp = new Position(to.getX() + x[i], to.getY() + y[i]); // all the alter positions
            if (inBoard(temp)) {
                if (getPieceAtPosition(temp) != null && getPieceAtPosition(temp).getOwner() != from.getOwner()) {
                    Position wall = new Position(temp.getX() + x[i], temp.getY() + y[i]); // the wall
                    if (inBoard(wall)) {
                        if (getPieceAtPosition(wall) != null && getPieceAtPosition(wall).getOwner() == from.getOwner()) {
                            killed[index] = temp;
                            index++;
                        }
                        continue; // either null or enemy
                    }// legit a wall
                    killed[index] = temp;
                    index++;
                }
            }
        }
        return killed;
    }
    public Piece[] Pos_to_Piece(Position[] pos){
        Piece[] pieces = new Piece[3];
        for (int i = 0; i < pos.length; i++) {
            if (pos[i] != null) {
                pieces[i] = getPieceAtPosition(pos[i]);
            }
        }
        return pieces;
    }

    private boolean A_way_out(Position p) {
        int[] x = {0, -1, 0, 1};
        int[] y = {-1, 0, 1, 0};
        for (int i = 0; i < 4; i++) {
            Position temp = new Position(p.getX() + x[i], p.getY() + y[i]); // all the alter positions
            if (inBoard(temp)) {
                if (getPieceAtPosition(temp) == null || getPieceAtPosition(temp).getOwner() == getPieceAtPosition(p).getOwner()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean check_for_win_defend() {
        return ((king.getPos().getX() == 0 && (king.getPos().getY() == 10 || king.getPos().getY() == 0)) ||
                (king.getPos().getX() == 10 && (king.getPos().getY() == 10 || king.getPos().getY() == 0)));
    }

    private boolean check_for_win_attack() {
        return !A_way_out(king.getPos()); // if king doesn't have a way out then attack wins
    }

    private boolean inBoard(Position p) {
        if (p == null) {
            System.out.println("lil bro gave me a null position");
            return false;
        }
        return p.getX() >= 0 && p.getX() < 11 && p.getY() >= 0 && p.getY() < 11;
    }

}
