
import java.util.Scanner;

class Game {

    private Board main_board = new Board();
    private Display display = new Display();
    private Player player_one = new Player();
    private Player player_two = new Player();
    private Scanner input = new Scanner(System.in);
    private int player_turn = 0;
    private int turns = 0;

    void setup_game(){
        System.out.println("Let the games begin...\n");
        main_board.set_board();
    }

    void choose_sides(){

        int option = 0;

        System.out.print("You are player 1 - please pick 0's (1) or X's (2): ");
        option = input.nextInt();

        if(option == 1){
            player_one.update_choice(1);
            player_two.update_choice(2);
        }
        else if (option == 2){
            player_one.update_choice(2);
            player_two.update_choice(1);
        }
        else {
            System.out.print("\nThat is not a valid choice, please pick again!\n");
            choose_sides();
        }
    }

    void play_game(){

        int[] move = new int[2];
        Symbol choice;

        if(main_board.get_player() == 1){
            System.out.print("\nPlayer 1 - please make your move\n");
            move = display.get_userinput();
            if(main_board.can_move(move)){
                choice = player_one.get_choice();
                main_board.make_move(move, choice);
            }
            else {
                System.out.print("\nInvalid move!\nTry again...\n");
                play_game();
            }
            main_board.update_playergo(player_turn);
        }
        else {
            System.out.print("\nPlayer 2 - please make your move\n");
            move = display.get_userinput();
            if(main_board.can_move(move)){
                choice = player_two.get_choice();
                main_board.make_move(move, choice);
            }
            else {
                System.out.print("\nInvalid move!\nTry again...\n");
                play_game();
            }
            main_board.update_playergo(player_turn);
        }

        display.display_board(main_board.get_board());
        player_turn++;

        draw_or_win();
    }

    void draw_or_win(){
        if(main_board.is_winner() == Symbol.NoWinner){
            if(main_board.is_full()){
                System.out.println("\nIt's a draw!\n");
                System.exit(0);
            }
            else {
                play_game();
            }
        }
        else {
            show_winner();
        }
    }

    void show_winner(){
        if(main_board.is_winner() == Symbol.Cross){
            System.out.println("\nCrosses has won!\n");
            System.exit(0);
        }
        else {
            System.out.println("\nNoughts has won!\n");
            System.exit(0);
        }
    }

}
