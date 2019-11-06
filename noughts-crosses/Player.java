

class Player {

    private Symbol choice;

    void update_choice(int x){
        if(x == 1){
            choice = Symbol.Nought;
            assert(choice == Symbol.Nought);
        }
        else if(x == 2){
            choice = Symbol.Cross;
            assert(choice == Symbol.Cross);
        }
        else {
            System.out.println("That is not an option\nPlease pick again\n");
        }
    }

    Symbol get_choice(){
        if(choice == Symbol.Nought){
            return Symbol.Nought;
        }
        else if(choice == Symbol.Cross){
            return Symbol.Cross;
        }
        else {
            return Symbol.UnexpectedInput;
        }
    }

    void test_updatechoice(){

        update_choice(1);
        assert(choice == Symbol.Nought);

        update_choice(2);
        assert(choice == Symbol.Cross);
    }

    void test_getchoice(){

        update_choice(1);
        assert(get_choice() == Symbol.Nought);

        update_choice(2);
        assert(get_choice() == Symbol.Cross);
    }

    public static void main(String[] args){
        Player test = new Player();
        test.run();

    }

    private void run() {
        boolean testing = false;
        assert(testing = true);
        test_updatechoice();
        test_getchoice();
        System.out.println("Player Class - all tests pass");

    }

}
