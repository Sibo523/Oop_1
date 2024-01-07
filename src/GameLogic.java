import java.util.Stack;


public class GameLogic implements PlayableLogic {
    private Player def, atck;

    private boolean atck_turn;
    private boolean game_finished;
    private Stack<Move> moves;

    private Piece[][] Board = new Piece[11][11];

    public GameLogic() {
        moves = new Stack<>(); // move history for undo
        atck_turn = true; // switch turns every move
        game_finished = false; // gg

        create_players(); // create two players atck and def
        reset();
    }

    @Override
    public boolean move(Position a, Position b) {
        Piece from = getPieceAtPosition(a);
        Piece to = getPieceAtPosition(b);
        if (from == null || to != null) return false; // no Piece to move or there is a Piece in the destination
        if (from.getOwner().isPlayerOne() != atck_turn) return false; // if it's not your turn
        if (a.equals(b)) return false; // if you didn't move

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
            return move(a, b, from, to, xDirection, yDirection);
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
        return false;
    }

    @Override
    public boolean isSecondPlayerTurn() {
        return !atck_turn;
    }

    @Override
    public void reset() {
        // Make all attacker pawns
        // rows first and last attack
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
        //chopchick

        // Defenders
        //king
        Board[5][5] = new King(def, "K7");
//        //chopchick
        Board[3][5] = new Pawn(def, "D5");
        Board[7][5] = new Pawn(def, "D9");
        //columns next to king
        index = 2;
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
        print_board();
    }

    @Override
    public void undoLastMove() {

    }

    @Override
    public int getBoardSize() {
        return Board.length;
    }

    //////////////////////////////////////////////PRIVATE METHODS//////////////////////////////////////////////
    private void create_players() {
        this.atck = new ConcretePlayer(true);
        this.def = new ConcretePlayer(false);
    }

    private void print_board() {
        for (int i = 0; i < Board.length; i++) {
            for (int j = 0; j < Board.length; j++)
                if (Board[i][j] == null) {
                    System.out.print(" ");
                } else {
                    Piece x = (getPieceAtPosition(new Position(i, j)));
                    System.out.print(Board[i][j].getType());
                }
            System.out.println();
        }
    }

    private boolean move(Position from, Position to, Piece save, Piece died, int direction_x, int direction_y) {
        moves.push(new Move(from, to, died, direction_x, direction_y));
        Board[from.getX()][from.getY()] = null;
        Board[to.getX()][to.getY()] = save;
        atck_turn = !atck_turn;
        System.out.println("moved");
        return true;
    }

    //TODO: check for kill
    private Position check_for_kill() {
        return null;
    }

    private boolean check_for_win_attack() {
        return false;
    }
}
