import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // 7th article
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter number of rows : ");
        int row = input.nextInt();
        System.out.println("Please enter number of columns : ");
        int column = input.nextInt();

        MineSweeper mine = new MineSweeper(row, column);
        mine.run();
    }
}