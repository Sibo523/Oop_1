import java.sql.SQLOutput;
import java.util.ArrayList;


public class GameLogic implements PlayableLogic {
    private Player def;
    private Player atck;
    private Player temp;
    private boolean atck_turn;
    private boolean game_finished;
    private ArrayList<Move> moves;

    private Piece[][] Board = new Piece[11][11];

    public GameLogic() {
        create_players();
        reset();
    }

    @Override
    public boolean move(Position a, Position b) {

        return false;
    }

    @Override
    public Piece getPieceAtPosition(Position position) {
        return Board[position.getX()][position.getY()];
    }

    @Override
    public Player getFirstPlayer() {
        return null;
    }

    @Override
    public Player getSecondPlayer() {
        return null;
    }

    @Override
    public boolean isGameFinished() {
        return false;
    }

    @Override
    public boolean isSecondPlayerTurn() {
        return false;
    }

    @Override
    public void reset() {
        moves = new ArrayList<>();
        atck_turn = true;
        game_finished = false;
//        for (int i = 0; i < Board.length; i++) {
//            for (int j = 0; j < Board.length; j++)
//                Board[i][j] = new ConcretePiece(temp);
//        }
        // Make all attacker pawns
        // rows first and last attack
        for (int i = 3; i < 8; i++) {
            Board[i][0] = new Pawn(atck);
            Board[i][10] = new Pawn(atck);
        }
        //chopchick
        Board[5][1] = new Pawn(atck);
        Board[5][9] = new Pawn(atck);
        //columns
        for (int i = 3; i < 8; i++) {
            Board[0][i] = new Pawn(atck);
            Board[10][i] = new Pawn(atck);
        }
        //chopchick
        Board[1][5] = new Pawn(atck);
        Board[9][5] = new Pawn(atck);

        // Defenders
        //king
        Board[5][5] = new King(def);
        //chopchick
        Board[3][5] = new Pawn(def);
        Board[7][5] = new Pawn(def);
        //columns next to king
        for (int i = 4; i < 7; i++) {
            Board[4][i] = new Pawn(def);
            Board[6][i] = new Pawn(def);
        }
        //columns 5 pawns  (king column)
        for (int i = 3; i < 8; i++) {
            if (i == 5) continue;
            Board[5][i] = new Pawn(def);
        }
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
//        this.temp = new ConcretePlayer();
    }
}
