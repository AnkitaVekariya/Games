import java.util.Random;
import java.util.Scanner;

class Node {
    int row, col;
    Node next;

    public Node(int row, int col) {
        this.row = row;
        this.col = col;
        this.next = null;
    }
}

public class SnakeGame {

    private static final int ROWS = 10;
    private static final int COLS = 20;

    private static int[][] grid = new int[ROWS][COLS];
    private static Node snakeHead;
    private static Node food;

    private static int directionRow = 0;
    private static int directionCol = 1;

    private static boolean gameOver = false;

    // Initialize the snake at the specified row and column
    public static void initSnake(int row, int col) {
        snakeHead = new Node(row, col);
        grid[row][col] = 1; // Mark the head of the snake on the grid
    }

    // Generate food at a random empty location on the grid
    public static void generateFood() {
        Random rand = new Random();
        int row, col;

        do {
            row = rand.nextInt(ROWS);
            col = rand.nextInt(COLS);
        } while (grid[row][col] != 0);

        food = new Node(row, col);
        grid[row][col] = 2; // Mark food on the grid
    }

    // Display the current state of the grid
    public static void printGrid() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (grid[i][j] == 1) {
                    System.out.print("X "); // Display the snake body as 'X'
                } else if (grid[i][j] == 2) {
                    System.out.print("O "); // Display food as 'O'
                } else {
                    System.out.print(". "); // Empty cell
                }
            }
            System.out.println();
        }
    }

    public static void updateSnakePosition() {
        int newRow = snakeHead.row + directionRow;
        int newCol = snakeHead.col + directionCol;

        if (newRow < 0 || newRow >= ROWS || newCol < 0 || newCol >= COLS || grid[newRow][newCol] == 1) {
            gameOver = true; // Snake hits the wall or itself, game over
            return;
        }

        if (grid[newRow][newCol] == 2) { // Snake eats the food
            Node newHead = new Node(newRow, newCol);
            newHead.next = snakeHead;
            snakeHead = newHead;
            grid[newRow][newCol] = 1; // Mark the new head on the grid

            generateFood(); // Generate new food since the snake has eaten
        } else { // Snake moves into an empty cell
            Node newHead = new Node(newRow, newCol);
            newHead.next = snakeHead;
            snakeHead = newHead;

            // Update the snake's head position
            grid[newRow][newCol] = 1;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        initSnake(0, 0);
        generateFood(); // Initialize food

        while (!gameOver) {
            printGrid();
            System.out.println("Enter direction (U/L/D/R): ");
            String input = sc.next();

            switch (input.toUpperCase()) {
                case "U":
                    directionRow = -1;
                    directionCol = 0;
                    break;
                case "D":
                    directionRow = 1;
                    directionCol = 0;
                    break;
                case "L":
                    directionRow = 0;
                    directionCol = -1;
                    break;
                case "R":
                    directionRow = 0;
                    directionCol = 1;
                    break;
                default:
                    System.out.println("Invalid input. Please enter U, L, D, or R.");
                    continue;
            }

            updateSnakePosition();
            System.out.println();

            try {
                Thread.sleep(500); // Delay for better visualization
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Game Over!");
    }
}
