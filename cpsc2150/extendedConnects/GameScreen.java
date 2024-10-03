package cpsc2150.extendedConnects;
import cpsc2150.extendedConnectX.models.BoardPosition;
import cpsc2150.extendedConnectX.models.GameBoard;
import cpsc2150.extendedConnectX.models.GameBoardMem;
import cpsc2150.extendedConnectX.models.IGameBoard;

import java.util.Scanner;

/*GROUP MEMBER NAMES AND GITHUB USERNAMES SHOULD GO HERE
Jacob Colson (jccolso)
Warren Wasden (wwasden)
Steven Cabezas (scabeza)
*/

public class GameScreen {

    //Useful constants
    private static IGameBoard playerBoard;
    private static char winningChar = 'a';
    public static final int maxPlayers = 10;
    public static final int minPlayers = 2;
    public static final int maxRowsCol = 100;
    public static final int minRowsCol = 3;

    public static final Scanner scanner = new Scanner(System.in);


    public static void printBoard() {
        System.out.println(playerBoard.toString());
    }

    public static void printWinner() {
        System.out.println("Player " + winningChar +" Won!");
    }

    public static int askPlayerForColumn() {
        int column = 0;
        //Gets column number from player.
        column = scanner.nextInt();

        return column;

    }

    public static void main(String[] args) {


        // Input validation for the amount of players
        char playerInput = 'y';

        //Loop iterates until valid number of players is entered
        do {
            boolean valid = true;
            int numOfPlayers = 0;
            while (valid) {
                System.out.println("How many players?");
                numOfPlayers = scanner.nextInt();
                if (numOfPlayers > maxPlayers) {
                    System.out.println("Must be 10 players or fewer");
                } else if (numOfPlayers < minPlayers) {
                    System.out.println("Must be at least 2 players");
                } else {
                    valid = false;
                }
            }

            // Assigning character values to each player
            char[] playerCharacters = new char[numOfPlayers];

            //Gets player's token from each player.
            for (int i = 0; i < numOfPlayers; i++) {
                char playerChar;
                System.out.println("Enter the character to represent player " + (i + 1));
                playerChar = scanner.next().charAt(0);
                playerChar = Character.toUpperCase(playerChar);

                //Checks to make sure entered token does not already exist.
                for (int j = 0; j < numOfPlayers; j++) {

                    //Loop iterates until player enters different character.
                    while (playerCharacters[j] == playerChar) {
                        System.out.println(playerChar + " is already taken as a player token!");
                        System.out.println("Enter the character to represent player " + (i + 1));
                        playerChar = scanner.next().charAt(0);
                        playerChar = Character.toUpperCase(playerChar);
                    }
                }

                //Adds player character to player array.
                playerCharacters[i] = playerChar;

            }

            // Determine number of rows, columns, and number of tokens in a row to win for the game
            int numOfRows = 0;
            int numOfColumns = 0;
            int numberToWin = 0;

            System.out.println("How many rows should be on the board?");
            numOfRows = scanner.nextInt();

            //Validates user input for Rows
            while (numOfRows < minRowsCol) {
                System.out.println("Amount of rows must be between 3 - 100");
                numOfRows = scanner.nextInt();
            }

            while (numOfRows > maxRowsCol) {
                System.out.println("Amount of rows must be between 3 - 100");
                numOfRows = scanner.nextInt();
            }

            System.out.println("How many columns should be on the board?");
            numOfColumns = scanner.nextInt();

            //Validates user input for number of columns
            while (numOfColumns < minRowsCol) {
                System.out.println("Amount of columns must be between 3 - 100");
                numOfColumns = scanner.nextInt();
            }

            while (numOfColumns > maxRowsCol) {
                System.out.println("Amount of columns must be between 3 - 100");
                numOfColumns = scanner.nextInt();
            }

            System.out.println("How many in a row to win?");
            numberToWin = scanner.nextInt();

            //Validates user input for number to win
            if (numOfColumns > numOfRows) {
                while (numberToWin > numOfColumns) {
                    System.out.println("Number of tokens to win must be between 3 and " + numOfColumns);
                    numberToWin = scanner.nextInt();
                }

                while (numberToWin < minRowsCol) {
                    System.out.println("Number of tokens to win must be between 3 and " + numOfColumns);
                    numberToWin = scanner.nextInt();
                }
            }
            if (numOfColumns <= numOfRows) {
                while (numberToWin > numOfRows) {
                    System.out.println("Number of tokens to win must be between 3 and " + numOfRows);
                    numberToWin = scanner.nextInt();
                }

                while (numberToWin < minRowsCol) {
                    System.out.println("Number of tokens to win must be between 3 and " + numOfColumns);
                    numberToWin = scanner.nextInt();
                }
            }


            char efficiency = 'a';

            //Gets user input for choice of gameType, loops until they enter valid choice.
            while (efficiency != 'M' && efficiency != 'F') {
                System.out.println("Would you like a Fast Game (F/f) or a Memory Efficient Game (M/m)?");
                efficiency = scanner.next().charAt(0);
                efficiency = Character.toUpperCase(efficiency);

                if (efficiency != 'M' && efficiency != 'F') {
                    System.out.println("Please enter F or M");
                }
            }

            //Creates GameBoard fast implementation for choice F.
            if (efficiency == 'F') {
                playerBoard = new GameBoard(numOfRows, numOfColumns, numberToWin);
            }

            //Creates GameBoardMem implementation for choice M.
            else {
                playerBoard = new GameBoardMem(numOfRows, numOfColumns, numberToWin);
            }

            int BOARD_MAX_COLUMN = playerBoard.getNumColumns() - 1;

            boolean gameNotOver = true;

            //Loops until game is over.
            while (gameNotOver) {

                //Loops for each player in the game.
                for (int i = 0; i < numOfPlayers; i++) {

                    boolean validCol = false;
                    printBoard();
                    System.out.println("Player " + playerCharacters[i] + ", what column do you want to place your marker in?");
                    int col = askPlayerForColumn();

                    //Determines if column entered is valid.
                    if (col > 0 && col < BOARD_MAX_COLUMN) {
                        validCol = true;
                    }

                    //Loops until valid column number is entered.
                    while (!validCol) {
                        if (col < 0) {
                            System.out.println("Column cannot be less than 0");
                            System.out.println("Player " + playerCharacters[i] + ", what column do you want to place your marker in?");
                            col = askPlayerForColumn();
                        } else if (col > BOARD_MAX_COLUMN) {
                            System.out.println("Column cannot be greater than " + (BOARD_MAX_COLUMN));
                            System.out.println("Player " + playerCharacters[i] + ", what column do you want to place your marker in?");
                            col = askPlayerForColumn();
                        }
                        //Validates that column is empty.
                        else if (!playerBoard.checkIfFree(col)) {
                            System.out.println("Column is full");
                            System.out.println("Player " + playerCharacters[i] + ", what column do you want to place your marker in?");
                            col = askPlayerForColumn();
                        } else {
                            validCol = true;
                        }
                    }

                    if (!playerBoard.checkIfFree(col)) {
                        validCol = false;
                    }

                    while (!validCol) {
                        if (col < 0) {
                            System.out.println("Column cannot be less than 0");
                            System.out.println("Player " + playerCharacters[i] + ", what column do you want to place your marker in?");
                            col = askPlayerForColumn();
                        } else if (col > BOARD_MAX_COLUMN) {
                            System.out.println("Column cannot be greater than " + (BOARD_MAX_COLUMN));
                            System.out.println("Player " + playerCharacters[i] + ", what column do you want to place your marker in?");
                            col = askPlayerForColumn();
                        }
                        //Validates that column is empty.
                        else if (!playerBoard.checkIfFree(col)) {
                            System.out.println("Column is full");
                            System.out.println("Player " + playerCharacters[i] + ", what column do you want to place your marker in?");
                            col = askPlayerForColumn();
                        } else {
                            validCol = true;
                        }
                    }

                    playerBoard.dropToken(playerCharacters[i], col);

                    //Checks if a tie has occured
                    if (playerBoard.checkTie()) {
                        printBoard();

                        //validates the user's choice
                        do {
                            System.out.println("Tie!");
                            System.out.println("Would you like to play again? Y/N");
                            playerInput = scanner.next().charAt(0);
                            playerInput = Character.toUpperCase(playerInput);
                        } while (playerInput != 'Y' && playerInput != 'N');
                        gameNotOver = false;
                        break;
                    }

                    //Checks if a win state has occured.
                    if (playerBoard.checkForWin(col)) {
                        printBoard();
                        winningChar = playerCharacters[i];
                        printWinner();

                        //Validates user's input, loops until valid choice is entered.
                        do {
                            System.out.println("Would you like to play again? Y/N");
                            playerInput = scanner.next().charAt(0);
                            playerInput = Character.toUpperCase(playerInput);
                        } while (playerInput != 'Y' && playerInput != 'N');
                        gameNotOver = false;
                        break;
                    }

                }
            }
        } while (playerInput == 'Y');
    }
}
