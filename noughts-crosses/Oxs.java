
class Oxs {

    void run(){
        Game game = new Game();
        game.setup_game();
        game.choose_sides();
        game.play_game();
    }
    public static void main(String[] args){
        Oxs program = new Oxs();
        program.run();
    }

}
