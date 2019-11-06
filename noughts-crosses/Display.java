
import java.io.*;
import java.util.Scanner;

class Display{

    private Scanner input = new Scanner(System.in);

    //boolean check_userinput();
    //void convert_badinput();

    int[] get_userinput(){
        int[] move = new int[2];
        System.out.print("Enter x-coordinate: "); move[0] = input.nextInt();
        System.out.print("Enter y-coordinate: "); move[1] = input.nextInt();
        return move;
    }

    void display_board(int[][] board){
        System.out.println();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(board[i][j] == -1){
                    System.out.print(" 0 ");
                }
                if(board[i][j] == 1){
                    System.out.print(" X ");
                }
                if(board[i][j] == 0){
                    System.out.print(" - ");
                }
                if(j == 0 || j == 1)
                    System.out.print("|");
            }
            System.out.println();
        }
    }
}
