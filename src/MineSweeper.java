import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class MineSweeper {  // class name -5th article
    //variables-1st article
    String[][] mine_sweeper;
    String[][] mine_sweeper_player;
    int row;
    int column;
    int row_index;
    int column_index;
    Scanner input = new Scanner(System.in);

    MineSweeper(int row, int column) {
        this.row = row;
        this.column = column;
    }

    //this board is to be seen by the player in order to cover mines
    public void setPlayerBoard(int row, int column) {
        mine_sweeper_player = new String[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                mine_sweeper_player[i][j] = "- ";
            }
            System.out.println();
        }
    }

    //this method prints PlayerBoard
    public void callPlayerBoard(int row, int column) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                System.out.print(mine_sweeper_player[i][j]);
            }
            System.out.println();
        }
    }

    //this board is the one running behind the scenes where the mines are going to be attached.
    public void setActualBoard(int row, int column) {
        mine_sweeper = new String[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                mine_sweeper[i][j] = "- ";
            }
            System.out.println();
        }

    }

    //this method prints our actual board.
    public void callActualBoard(int row, int column) {
        System.out.println("Locations of mines");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                System.out.print(mine_sweeper[i][j]);
            }
            System.out.println();
        }

    }

    //8th article
    //here we set our mines into our actual board.
    public void setMinesActualBoard(int row, int column) {

        Random random = new Random();
        int number_of_mines = (row * column) / 4;

        while (number_of_mines > 0) {
            int randomRow = random.nextInt(row);
            int randomColumn = random.nextInt(column);

            //if a randomly chosen coordinate is not equals "*"(mine) we can place mine into that cell.

            if (!mine_sweeper[randomRow][randomColumn].equals("* ")) {
                mine_sweeper[randomRow][randomColumn] = "* ";
                number_of_mines--;
            }
        }
    }


    //Here is the maximum possibilities of touch point a cell can. If a cell touch less than maximum number algorithm continues with help of "&&" statements.
    //Here is a counter to count number of mines around a cell. Bomb counter value reset for each turn.
    public void checkMinesAround(int row_index, int column_index) {
        int bomb_counter = 0;
        int temp_bomb_counter = 0;

        //bottom
        if (row_index + 1 < row && mine_sweeper[row_index + 1][column_index] == "* ") {
            bomb_counter++;
        }
        //bottom right
        if (row_index + 1 < row && column_index + 1 < column && mine_sweeper[row_index + 1][column_index + 1] == "* ") {
            bomb_counter++;
        }
        //top
        if (column_index + 1 < column && mine_sweeper[row_index][column_index + 1] == "* ") {
            bomb_counter++;
        }
        //left
        if (row_index - 1 >= 0 && mine_sweeper[row_index - 1][column_index] == "* ") {
            bomb_counter++;
        }
        //top left
        if (row_index - 1 >= 0 && column_index + 1 < column && mine_sweeper[row_index - 1][column_index + 1] == "* ") {
            bomb_counter++;
        }
        //top left
        if (row_index - 1 >= 0 && column_index - 1 >= 0 && mine_sweeper[row_index - 1][column_index - 1] == "* ") {
            bomb_counter++;
        }
        //right
        if (column_index - 1 >= 0 && mine_sweeper[row_index][column_index - 1] == "* ") {
            bomb_counter++;
        }
        //bottom left
        if (row_index + 1 < row && column_index - 1 >= 0 && mine_sweeper[row_index + 1][column_index - 1] == "* ") {
            bomb_counter++;
        }
        //12th article
        //here we attach our bomb_counter count to the cell we pick
        mine_sweeper_player[row_index][column_index] = bomb_counter + " ";
        //11th article
        //print our new board for player
        callPlayerBoard(row, column);
        System.out.println("===============");
        //reset the counter
        bomb_counter = temp_bomb_counter;
    }

    //Number of trial means that f.e if we have 3x3 matrix which has 9 cells and 9/4 = 2 mines because it's integer so 7 cells empty, 2 with mines
    //if we complete our tour without stepping on a mine, in maximum we have 7 trials in this case. So, if it turns out to be equals to 0 then we win the game.
    public void startTrial(int row, int column) {

        int mines = (row * column) / 4;
        int number_of_trial = (row * column) - mines;
        int temp_number_of_trial = number_of_trial;

        while (number_of_trial > 0) {

            //9th article
            System.out.print("Please enter row index :");
            int row_index = input.nextInt();
            System.out.print("Please enter column index :");
            int column_index = input.nextInt();

            //10th article
            //these line of codes provides continuity of the code until a value entered within the range,
            //and it doesn't decrement count factor
            if (row_index > row || row_index < 0 || column_index > column || column_index < 0) {

                while (row_index > row || row_index < 0 || column_index > column || column_index < 0) {

                    System.out.println("Please enter a point within the range !!\n ============ ");
                    System.out.print("Please enter row index :");
                    row_index = input.nextInt();

                    System.out.print("Please enter column index :");
                    column_index = input.nextInt();
                }
            }
            //this line deters our game to be ended by recursively selecting the same indexes.
            if (number_of_trial < (temp_number_of_trial)) {
                if (!Objects.equals(mine_sweeper_player[row_index][column_index], "- ")) {
                    System.out.println("Please select different row and column indexes !");
                    number_of_trial++;
                }
            }
            //13th and 15th article
            //if you step on a mine you die !!!
            if (Objects.equals(mine_sweeper[row_index][column_index], "* ")) {
                System.out.print("Step on a mine ! Boom ! You lose !");
                break;
            } else {
                checkMinesAround(row_index, column_index);
                number_of_trial--;
            }
            //14th and 15th article
            if (number_of_trial == 0) {
                System.out.println("You have found all the mines ! You win !");
            }
        }

    }

    public void run() {
        setActualBoard(row, column);
        setMinesActualBoard(row, column);
        callActualBoard(row, column);
        setPlayerBoard(row, column);
        System.out.println("===============");
        System.out.println("Welcome to MineSweeper ! ");
        callPlayerBoard(row, column);
        System.out.println("===============");
        startTrial(row, column);
    }
}
