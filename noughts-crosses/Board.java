

class Board {

    private int board[][] = new int [3][3];
    private int player = 1;

    public static void main(String[] args){
        Board test = new Board();
        test.run();
    }

    private void run() {
        boolean testing = false;
        assert(testing = true);
        test_set_board();
        test_update_playergo();
        test_check_rows();
        test_check_cols();
        test_check_diag();
        test_checkcell();
        test_make_move();
        test_can_move();
        test_is_movevalid();
        test_ismovepossible();
        System.out.println("Board Class - all tests pass");
    }

    void set_board(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                board[i][j] = 0;
            }
        }
    }

    void update_playergo(int turn){
        if(turn % 2 == 0){
            player++;
        }
        else {
            player--;
        }
    }

    int[][] get_board(){
        return board;
    }

    int get_player(){
        return player;
    }

    /* Winning / Losing Functions */

    Symbol is_winner(){
        if(check_rows() == Symbol.Cross||check_cols() == Symbol.Cross||check_diag() == Symbol.Cross){
            return Symbol.Cross;
        }
        if(check_rows() == Symbol.Nought||check_cols() == Symbol.Nought||check_diag() == Symbol.Nought){
            return Symbol.Nought;
        }
        return Symbol.NoWinner;
    }

    Symbol check_rows(){
        for(int i = 0; i < 3; i++){
            if(board[i][0] + board[i][1] + board[i][2] == -3){
                return Symbol.Nought;
            }
            if(board[i][0] + board[i][1] + board[i][2] == 3){
                return Symbol.Cross;
            }
        }
        return Symbol.NoWinner;
    }

    Symbol check_cols(){
        for(int i = 0; i < 3; i++){
            if(board[0][i] + board[1][i] + board[2][i] == -3){
                return Symbol.Nought;
            }
            if(board[0][i] + board[1][i] + board[2][i] == 3){
                return Symbol.Cross;
            }
        }
        return Symbol.NoWinner;
    }

    Symbol check_diag(){
        if(board[0][0] + board[1][1] + board[2][2] == -3){
            return Symbol.Nought;
        }
        if(board[0][0] + board[1][1] + board[2][2] == 3){
            return Symbol.Cross;
        }
        if(board[2][0] + board[1][1] + board[0][2] == -3){
            return Symbol.Nought;
        }
        if(board[2][0] + board[1][1] + board[0][2] == 3){
            return Symbol.Cross;
        }
        return Symbol.NoWinner;
    }

    boolean is_full(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(board[i][j] == 0){
                    return false;
                }
            }
        }
        return true;
    }

    /* Is move valid / possible functions */

    Symbol check_cell(int[] current_move){
        if(board[current_move[0]][current_move[1]] == 0){
            return Symbol.Empty;
        }
        else if(board[current_move[0]][current_move[1]] == -1){
            return Symbol.Nought;
        }
        else if(board[current_move[0]][current_move[1]] == 1){
            return Symbol.Cross;
        }
        else {
            return Symbol.UnexpectedInput;
        }
    }

    boolean can_move(int[] current_move){
        if(is_movevalid(current_move) && is_movepossible(current_move)){
            return true;
        }
        return false;
    }

    boolean is_movevalid(int[] current_move){
        if(current_move[0] < 3 && current_move[1] < 3){
            return true;
        }
        return false;
    }

    boolean is_movepossible(int[] current_move){
        if(is_movevalid(current_move)){
            if(check_cell(current_move) == Symbol.Empty){
                return true;
            }
        }
        return false;
    }

    void make_move(int[] current_move, Symbol player){
        if(player == Symbol.Nought){
            board[current_move[0]][current_move[1]] = -1;
        }
        else if(player == Symbol.Cross){
            board[current_move[0]][current_move[1]] = 1;
        }
    }

    /* Test Functions */

    void test_set_board(){
        assert(board[0][1] == 0);
        assert(board[1][1] == 0);
        assert(board[2][0] == 0);
    }

    void test_update_playergo(){
        update_playergo(1);
        assert(player == 2);
        update_playergo(2);
        assert(player == 1);
    }

    void test_check_rows(){

        board[0][0] = -1;
        board[0][1] = -1;
        board[0][2] = -1;

        assert(check_rows() == Symbol.Nought);

        board[1][0] = 1;
        board[1][1] = 1;
        board[1][2] = 1;

        assert(check_rows() == Symbol.Cross);
    }

    void test_check_cols(){

        board[0][0] = -1;
        board[1][0] = -1;
        board[2][0] = -1;

        assert(check_cols() == Symbol.Nought);

        board[0][1] = 1;
        board[1][1] = 1;
        board[2][1] = 1;

        assert(check_cols() == Symbol.Cross);
    }

    void test_check_diag(){

        board[0][0] = -1;
        board[1][1] = -1;
        board[2][2] = -1;

        assert(check_diag() == Symbol.Nought);

        board[2][0] = 1;
        board[1][1] = 1;
        board[0][2] = 1;

        assert(check_diag() == Symbol.Cross);
    }


    void test_checkcell(){

        int[] test = new int[2];

        test[0] = 0;
        test[1] = 1;

        board[0][1] = 0;

        assert(check_cell(test) == Symbol.Empty);

        board[0][1] = -1;
        assert(check_cell(test) == Symbol.Nought);

        board[0][1] = 1;
        assert(check_cell(test) == Symbol.Cross);
    }

    void test_can_move(){

        int[] test = new int[2];

        test[0] = 0;
        test[1] = 1;

        board[0][1] = 0;

        assert(can_move(test) == true);

    }

    void test_is_movevalid(){

        int[] test = new int[2];

        test[0] = 3;
        test[1] = 3;

        assert(is_movevalid(test) == false);

        test[0] = 2;
        test[1] = 2;

        assert(is_movevalid(test) == true);
    }

    void test_ismovepossible(){
        int[] test = new int[2];

        test[0] = 1;
        test[1] = 2;

        board[0][1] = 0;

        assert(is_movepossible(test) == true);
    }

    void test_make_move(){

        int[] test = new int[2];

        test[0] = 0;
        test[1] = 1;

        make_move(test, Symbol.Nought);
        assert(board[0][1] == -1);

        make_move(test, Symbol.Cross);
        assert(board[0][1] == 1);
    }
}
