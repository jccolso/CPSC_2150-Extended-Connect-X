package cpsc2150.extendedConnectX.tests;
/*
GROUP MEMBER NAMES AND GITHUB USERNAMES SHOULD GO HERE
Jacob Colson (jccolso)
Warren Wasden (wwasden)
Steven Cabezas (scabeza)
*/

import cpsc2150.extendedConnectX.models.BoardPosition;
import cpsc2150.extendedConnectX.models.GameBoard;
import org.junit.Test;
import static org.junit.Assert.*;
import cpsc2150.extendedConnectX.models.IGameBoard;
import cpsc2150.extendedConnectX.models.GameBoardMem;

public class TestGameBoardMem {

    //Calls the appropriate constructor for GameBoard implementation
    private IGameBoard GameBoardFactory(int aRow, int aColumn, int numWin) {
        return new GameBoardMem(aRow, aColumn, numWin);
    }

    //Creates a string from the expectedGameBoard array.
    private String makeExpectedGameBoard(char[][] aArray, int numRows, int numColumns) {
        String expectedBoard = "";
        int DoubleDigit = 10;

        for (int i = 0; i < numColumns; i++) {
            if (i < DoubleDigit) {
                expectedBoard += "| ";
            }
            else {
                expectedBoard += '|';
            }
            expectedBoard += i;
        }

        expectedBoard += "|\n";

        for (int i = numRows - 1; i >= 0; i--) {
            for (int j = 0; j < numColumns; j++) {
                expectedBoard += '|';

                if (!Character.isLetter(aArray[i][j])) {
                    expectedBoard += "  ";
                }
                else {
                    expectedBoard += aArray[i][j];
                    expectedBoard += ' ';
                }
            }
            expectedBoard += "|\n";
        }

        return expectedBoard;
    }

    //Constructor Tests
    //Test constructor to make sure correct GameBoard size was created 5x10 and numToWin is initialized correctly at 5
    @Test
    public void test_5_10_5_GameBoard() {

        int numRows = 5;
        int numColumns = 10;
        int numToWin = 5;

        char[][] expectedBoard = new char[numRows][numColumns];
        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);

        String blankBoard = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertEquals(numRows, gb.getNumRows());
        assertEquals(numColumns, gb.getNumColumns());
        assertEquals(gb.toString(), blankBoard);
    }

    //Test constructor to make sure correct GameBoard size was created 75x4 and numToWin is initialized correctly at 75
    @Test
    public void test_75_4_25_GameBoard() {
        int numRows = 75;
        int numColumns = 4;
        int numToWin = 25;

        char[][] expectedBoard = new char[numRows][numColumns];
        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);

        String blankBoard = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertEquals(numRows, gb.getNumRows());
        assertEquals(numColumns, gb.getNumColumns());
        assertEquals(gb.toString(), blankBoard);
    }

    //Test constructor to make sure correct GameBoard size was created 100x100 and numToWin is
    //initialized correctly at 25
    @Test
    public void test_MaxSize_MaxNumToWin_GameBoard() {
        int numRows = 100;
        int numColumns = 100;
        int numToWin = 25;

        char[][] expectedBoard = new char[numRows][numColumns];
        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);

        String blankBoard = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertEquals(numRows, gb.getNumRows());
        assertEquals(numColumns, gb.getNumColumns());
        assertEquals(gb.toString(), blankBoard);
    }

    //CheckIfFree tests
    // Checks to make sure a specific column is not full. In this case column 3
    @Test
    public void test_3_empty_CheckIfFree() {
        int numRows = 4;
        int numColumns = 10;
        int numToWin = 5;

        int ColumnToDrop = 3;
        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];
        String blankBoard = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertTrue(gb.checkIfFree(ColumnToDrop));
        assertEquals(gb.toString(), blankBoard);
    }

    //Checks to make see if half the column is not full in this case column 0
    @Test
    public void test_0_half_CheckIfFree() {
        int numRows = 4;
        int numColumns = 10;
        int numToWin = 5;

        int ColumnToDrop = 0;
        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        for (int i = 0; i < (numRows / 2); i++) {
            if (i % 2 == 0) {
                gb.dropToken('O', ColumnToDrop);
                expectedBoard[i][ColumnToDrop] = 'O';
            }
            else {
                gb.dropToken('X', ColumnToDrop);
                expectedBoard[i][ColumnToDrop] = 'X';
            }
        }

        String expectedString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertTrue(gb.checkIfFree(ColumnToDrop));
        assertEquals(gb.toString(), expectedString);

    }

    // Check to see if column 2 is free which is false because the board is full
    @Test
    public void test_2_fullBoard_CheckIfFree() {
        int numRows = 4;
        int numColums = 10;
        int numToWin = 5;

        int ColumnToCheck = 2;
        IGameBoard gb = GameBoardFactory(numRows, numColums, numToWin);
        char[][] expectedBoard = new char[numRows][numColums];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColums; j++) {
                if (i % 2 == 0) {
                    gb.dropToken('O', j);
                    expectedBoard[i][j] = 'O';
                }
                else {
                    gb.dropToken('X', j);
                    expectedBoard[i][j] = 'X';
                }
            }
        }

        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColums);

        assertTrue(!gb.checkIfFree(ColumnToCheck));
        assertEquals(gb.toString(), expectedBoardString);

    }

    //checkHorizWin tests
    //checkHorizWin to return true from col 0-4 starting from right to left
    @Test
    public void test_0_5_checkHorizWin() {
        int numRows = 25;
        int numColumns = 25;
        int numToWin = 5;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        for (int i = 0; i < numToWin; i++) {
            gb.dropToken('X', i);
            expectedBoard[0][i] = 'X';
        }
        BoardPosition positionToCheck = new BoardPosition(0, 5);
        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);


        assertTrue(gb.checkHorizWin(positionToCheck, 'X'));
        assertEquals(gb.toString(), expectedBoardString);
    }

    //checkHorizWin to return true from col 4-0 starting from left to right
    @Test
    public void test_5_0_checkHorizWin() {
        int numRows = 10;
        int numColumns = 10;
        int numToWin = 5;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        for (int i = numToWin - 1; i >= 0; i--) {
            gb.dropToken('X', i);
            expectedBoard[0][i] = 'X';
        }
        BoardPosition positionToCheck = new BoardPosition(0, 0);
        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertTrue(gb.checkHorizWin(positionToCheck, 'X'));
        assertEquals(gb.toString(), expectedBoardString);
    }

    //checkHorizWin to return true from col 0-4 but starting the checkHorizWin from the middle 1st column
    // with min numToWin
    @Test
    public void test_middle_placement_checkHorizWin() {
        int numRows = 10;
        int numColumns = 10;
        int numToWin = 3;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        for (int i = 0; i < numToWin; i++) {
            gb.dropToken('X', i);
            expectedBoard[0][i] = 'X';
        }

        BoardPosition positionToCheck = new BoardPosition(0, 1);
        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertTrue(gb.checkHorizWin(positionToCheck, 'X'));
        assertEquals(gb.toString(), expectedBoardString);
    }

    //checkHorizWin for a false win, where checkHorizWin should return false because not enough tokens to produce a win
    @Test
    public void test_return_false_checkHorizWin() {
        int numRows = 50;
        int numColumns = 50;
        int numToWin = 25;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        int loopController = 21;
        for (int i = 0; i < loopController; i++) {
            gb.dropToken('X', i);
            expectedBoard[0][i] = 'X';
        }

        BoardPosition positionToCheck = new BoardPosition(0, 1);
        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertTrue(!gb.checkHorizWin(positionToCheck, 'X'));
        assertEquals(gb.toString(), expectedBoardString);
    }

    //CheckVertWin tests
    //checkVertWin to return false because numToWin is not met
    @Test
    public void test_return_false_checkVertWin() {
        int numRows = 20;
        int numColumns = 20;
        int numToWin = 5;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        int inc = 0;
        while (inc < numToWin - 1) {
            gb.dropToken('X', 0);
            expectedBoard[inc][0] = 'X';
            inc++;
        }

        BoardPosition positionToCheck = new BoardPosition(3,0);
        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertTrue(!gb.checkVertWin(positionToCheck, 'X'));
        assertEquals(gb.toString(), expectedBoardString);
    }

    //checkVertWin to return true for number of tokens placed in a column == numToWin
    @Test
    public void test_column_0_checkVertWin() {
        int numRows = 20;
        int numColumns = 20;
        int numToWin = 5;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        int inc = 0;
        while (inc < numToWin) {
            gb.dropToken('X', 0);
            expectedBoard[inc][0] = 'X';
            inc++;
        }

        BoardPosition positionToCheck = new BoardPosition(4,0);
        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertTrue(gb.checkVertWin(positionToCheck, 'X'));
        assertEquals(gb.toString(), expectedBoardString);
    }

    //checks to see if checkVertWin returns false if character's match number to win but are not consecutive.
    @Test
    public void test_col3_alternatingCharacters_checkVertWin() {
        int numRows = 10;
        int numColumns = 10;
        int numToWin = 5;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            if (i % 2 == 0) {
                gb.dropToken('X', 3);
                expectedBoard[i][3] = 'X';
            }
            else {
                gb.dropToken('O', 3);
                expectedBoard[i][3] = 'O';
            }
        }

        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);
        BoardPosition positionToCheck = new BoardPosition(numRows - 1, 3);

        assertTrue(!gb.checkVertWin(positionToCheck, 'X'));
        assertEquals(gb.toString(), expectedBoardString);
    }

    //checks to see if checkVertWin returns true if there are consecutive tokens not starting on the bottom
    @Test
    public void test_verticalWinNotOnBottomRow_Col4_checkVertWin() {
        int numRows = 10;
        int numColumns = 10;
        int numToWin = 5;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];


        for (int i = 0; i < numToWin; i++) {
            if (i % 2 == 0) {
                gb.dropToken('X', 4);
                expectedBoard[i][4] = 'X';
            }
            else {
                gb.dropToken('O', 4);
                expectedBoard[i][4] = 'O';
            }
        }

        for (int i = numToWin; i < numRows; i++) {
            gb.dropToken('X', 4);
            expectedBoard[i][4] = 'X';
        }

        BoardPosition positionToCheck = new BoardPosition(numRows - 1, 4);
        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertEquals(gb.toString(), expectedBoardString);
        assertTrue(gb.checkVertWin(positionToCheck, 'X'));
    }



    //CheckDiagWin tests
    //checkDiagWin to return false because numToWin is not met
    @Test
    public void test_return_false_checkDiagWin() {
        int numRows = 10;
        int numColumns = 10;
        int numToWin = 5;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        BoardPosition colToCheck = new BoardPosition(0, 0);
        for (int i = 0; i < numToWin - 1; i++) {
            for (int j = i+1; j < numToWin - 1; j++) {
                gb.dropToken('O', j);
                expectedBoard[i][j] = 'O';
            }
            gb.dropToken('X', i);
            expectedBoard[i][i] = 'X';
            colToCheck = new BoardPosition(i, i);
        }
        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertTrue(!gb.checkDiagWin(colToCheck, 'X'));
        assertEquals(gb.toString(), expectedBoardString);
    }

    // checkDiagWin to return true for diagonal direction top right to bottom left with last token placed at top right
    @Test
    public void test_topRight_to_bottomLeft_checkDiagWin() {
        int numRows = 10;
        int numColumns = 10;
        int numToWin = 5;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        BoardPosition colToCheck = new BoardPosition(0, 0);
        for (int i = 0; i < numToWin; i++) {
            for (int j = i+1; j < numToWin; j++) {
                gb.dropToken('O', j);
                expectedBoard[i][j] = 'O';
            }
            gb.dropToken('X', i);
            expectedBoard[i][i] = 'X';
            colToCheck = new BoardPosition(i, i);
        }
        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertTrue(gb.checkDiagWin(colToCheck, 'X'));
        assertEquals(gb.toString(), expectedBoardString);
    }

    //checkDiagWin to return true for diagonal direction bottom right to top left with last token placed at bottom right
    @Test
    public void test_bottomRight_to_topLeft_checkDiagWin() {
        int numRows = 10;
        int numColumns = 10;
        int numToWin = 5;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        BoardPosition colToCheck = new BoardPosition(0, 0);
        int xPlace = 4;

        for (int i = numToWin - 1; i >= 0 ; i--) {
            for (int j = numToWin; j < numColumns; j++) {
                int rowToDrop = 0;
                while (rowToDrop < xPlace) {
                    gb.dropToken('O', j);
                    expectedBoard[rowToDrop][j] = 'O';
                    rowToDrop++;
                }
                xPlace--;
            }
            gb.dropToken('X', numColumns - 1 - i);
            expectedBoard[i][numColumns - 1 - i] = 'X';
            colToCheck = new BoardPosition(i, numColumns - 1 - i);
        }

        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertTrue(gb.checkDiagWin(colToCheck, 'X'));
        assertEquals(gb.toString(), expectedBoardString);
    }

    //checkDiagWin to return true for diagonal direction bottom left to top right with last token placed at bottom left
    @Test
    public void test_bottomLeft_to_topRight_checkDiagWin() {
        int numRows = 10;
        int numColumns = 10;
        int numToWin = 5;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        BoardPosition colToCheck = new BoardPosition(0, 0);
        for (int i = numToWin - 1; i >= 0; i--) {
            for (int j = (i - 1); j >= 0; j--) {
                gb.dropToken('O', i);
                expectedBoard[j][i] = 'O';
            }
            gb.dropToken('X', i);
            expectedBoard[i][i] = 'X';
            colToCheck = new BoardPosition(i, i);
        }
        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertTrue(gb.checkDiagWin(colToCheck, 'X'));
        assertEquals(gb.toString(), expectedBoardString);
    }

    //checkDiagWin to return true for diagonal direction top left to bottom right with last token placed at bottom right
    @Test
    public void test_topLeft_to_bottomRight_checkDiagWin() {
        int numRows = 10;
        int numColumns = 10;
        int numToWin = 5;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        BoardPosition colToCheck = new BoardPosition(0, 0);
        for (int i = 0; i < numToWin; i++) {
            gb.dropToken('X', numColumns - 1 - i);
            for (int j = numColumns - 2; j >= numColumns - numToWin; j--) {
                if (i != numToWin - 1) {
                    gb.dropToken('O', j);
                }
                expectedBoard[i][j] = 'O';
            }
            expectedBoard[i][numColumns - 1 - i] = 'X';
            colToCheck = new BoardPosition(i, numColumns - 1 - i);
        }
        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertTrue(gb.checkDiagWin(colToCheck, 'X'));
        assertEquals(gb.toString(), expectedBoardString);
    }

    // checkDiagWin to return true for diagonal direction bottom left to top right, and top right to bottom left
    // with final token being placed in the middle of the 5 token sequence
    @Test
    public void test_bottomLeft_to_topRight_and_topRight_to_bottomLeft_middlePlacement_checkDiagWin() {
        int numRows = 10;
        int numColumns = 10;
        int numToWin = 5;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        int loopController1 = 2;
        for (int i = 0; i < loopController1; i++) {
            for (int j = 1; j < loopController1; j++) {
                if (i != 1) {
                    gb.dropToken('O', j);
                }
                expectedBoard[i][j] = 'O';
            }
            gb.dropToken('X', i);
            expectedBoard[i][i] = 'X';
        }

        int loopController2 = 3;
        for (int i = 0; i < numToWin; i++) {
            for (int j = loopController2; j < numToWin; j++) {
                if (j == numToWin - 1 && i != numToWin - 1) {
                    gb.dropToken('O', j);
                } else if (j == loopController2 && i != loopController2 && i != numToWin - 1) {
                    gb.dropToken('O', j);
                }
                if (j == loopController2 && i != numToWin - 1) {
                    expectedBoard[i][j] = 'O';
                }
                else if (j == numToWin - 1 && i != numToWin - 1) {
                    expectedBoard[i][j] = 'O';
                }
            }
            if (i > loopController1) {
                gb.dropToken('X', i);
                expectedBoard[i][i] = 'X';
            }
        }

        int j = loopController1;
        for (int i = 0; i < loopController2; i++) {
            if (i == 2) {
                gb.dropToken('X', i);
                expectedBoard[i][i] = 'X';
            }
            else {
                gb.dropToken('O', j);
                expectedBoard[i][j] = 'O';
            }
        }
        BoardPosition colToCheck = new BoardPosition(2, 2);
        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertTrue(gb.checkDiagWin(colToCheck, 'X'));
        assertEquals(gb.toString(), expectedBoardString);
    }

    // checkDiagWin to return true for diagonal direction bottom right to top left, and top left to bottom right
    // with final token being placed in the middle of the 5 token sequence
    @Test
    public void test_topLeft_to_bottomRight_and_bottomRight_to_topLeft_middlePlacement_checkDiagWin() {
        int numRows = 10;
        int numColumns = 10;
        int numToWin = 5;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        int loopController1 = 2;
        for (int i = 0; i < loopController1; i++) {
            for (int j = numRows - 1; j > numRows - loopController1; j--) {
                if (i != 1) {
                    gb.dropToken('O', j - 1);
                }
                expectedBoard[i][j - 1] = 'O';
            }
            gb.dropToken('X', numColumns - 1 - i);
            expectedBoard[i][numColumns - 1 - i] = 'X';
        }

        int loopController2 = 7;
        int loopController3 = 3;
        int loopController4 = 6;
        for (int i = 0; i < numToWin; i++) {
            for (int j = numToWin; j < loopController2; j++) {
                if (j == numToWin && i != numToWin - 1) {
                    gb.dropToken('O', j);
                }
                else if (j == loopController4 && i < loopController3) {
                    gb.dropToken('O', j);
                }
                if (i < loopController3 && j == loopController4) {
                    expectedBoard[i][j] = 'O';
                }
                else if ( i < numToWin - 1 && j == numToWin) {
                    expectedBoard[i][j] = 'O';
                }
            }
            if (i > loopController1) {
                gb.dropToken('X', numColumns - 1 - i);
                expectedBoard[i][numColumns - 1 - i] = 'X';
            }
        }

        int j = 7;
        for (int i = 0; i < loopController3; i++) {
            if (i == 2) {
                gb.dropToken('X', j);
                expectedBoard[i][j] = 'X';
            }
            else {
                gb.dropToken('O', j);
                expectedBoard[i][j] = 'O';
            }
        }


        BoardPosition colToCheck = new BoardPosition(2, 7);
        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertTrue(gb.checkDiagWin(colToCheck, 'X'));
        assertEquals(gb.toString(), expectedBoardString);
    }


    //CheckTie tests
    //CheckTie when the board is completely full. Returns true
    @Test
    public void test_when_there_is_tie_checkTie() {
        int numRows = 10;
        int numColumns = 10;
        int numToWin = 5;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i % 2 == 0) {
                    gb.dropToken('X', j);
                    expectedBoard[i][j] = 'X';
                } else {
                    gb.dropToken('O', j);
                    expectedBoard[i][j] = 'O';
                }
            }
        }
        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertTrue(gb.checkTie());
        assertEquals(gb.toString(), expectedBoardString);
    }

    //checkTie when the board is completely empty. Return false
    @Test
    public void test_empty_board_checkTie() {
        int numRows = 10;
        int numColumns = 10;
        int numToWin = 5;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertTrue(!gb.checkTie());
        assertEquals(gb.toString(), expectedBoardString);
    }

    //checkTie when the columns on the board are half full. Return false
    @Test
    public void test_half_full_columns_checkTie() {
        int numRows = 10;
        int numColumns = 10;
        int numToWin = 5;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numToWin; j++) {
                if (i % 2 == 0) {
                    gb.dropToken('X', j);
                    expectedBoard[i][j] = 'X';
                } else {
                    gb.dropToken('O', j);
                    expectedBoard[i][j] = 'O';
                }
            }
        }
        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertTrue(!gb.checkTie());
        assertEquals(gb.toString(), expectedBoardString);
    }

    //checkTie when the rows on the board are half full. Return false
    @Test
    public void test_half_full_rows_checkTie() {
        int numRows = 10;
        int numColumns = 10;
        int numToWin = 5;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        for (int i = 0; i < numToWin; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i % 2 == 0) {
                    gb.dropToken('X', j);
                    expectedBoard[i][j] = 'X';
                } else {
                    gb.dropToken('O', j);
                    expectedBoard[i][j] = 'O';
                }
            }
        }
        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertTrue(!gb.checkTie());
        assertEquals(gb.toString(), expectedBoardString);
    }

    //whatsAtPos tests
    //Checking lower left boundary to see if it returns correct
    // element at that position which is X
    @Test
    public void test_MinRow_MinCol_whatsAtPos() {
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;

        int ColumnToDrop = 0;
        int RowToDrop = 0;
        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        gb.dropToken('X', ColumnToDrop);
        expectedBoard[RowToDrop][ColumnToDrop] = 'X';
        BoardPosition positionToCheck = new BoardPosition(RowToDrop, ColumnToDrop);

        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertEquals('X', gb.whatsAtPos(positionToCheck));
        assertEquals(gb.toString(), expectedBoardString);
    }

    //Checking what is at row 25, column 4 to see if it returns
    // the correct element at that position which is O
    @Test
    public void test_25_4_FULLBOARD_whatsAtPos() {
        int numRows = 30;
        int numColumns = 25;
        int numToWin = 10;

        int RowToCheck = 25;
        int ColumnToCheck = 4;
        BoardPosition positionToCheck = new BoardPosition(RowToCheck, ColumnToCheck);
        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (j % 2 == 0) {
                    gb.dropToken('O', j);
                    expectedBoard[i][j] = 'O';
                }
                else {
                    gb.dropToken('X', j);
                    expectedBoard[i][j] = 'X';
                }
            }
        }

        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertEquals('O', gb.whatsAtPos(positionToCheck));
        assertEquals(gb.toString(), expectedBoardString);
    }

    //Checking what is at row 7, column 9 to see if it returns the
    // correct element which should be nothing because the board is empty
    @Test
    public void test_7_9_BLANK_whatsAtPos() {
        int numRows = 10;
        int numColumns = 10;
        int numToWin = 5;

        int RowToCheck = 7;
        int ColumnToCheck = 9;
        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        BoardPosition positionToCheck = new BoardPosition(RowToCheck, ColumnToCheck);
        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertEquals(' ', gb.whatsAtPos(positionToCheck));
        assertEquals(gb.toString(), expectedBoardString);

    }

    //Checking to see what is at the upper right boundary of a max
    // board to see if it returns the correct element at that position
    // which should be J
    @Test
    public void test_MaxCol_MaxROW_FULLBOARD_whatsAtPos() {
        int numRows = 100;
        int numColumns = 100;
        int numToWin = 25;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (j % 2 == 0) {
                    gb.dropToken('P', j);
                    expectedBoard[i][j] = 'P';
                }
                else {
                    gb.dropToken('J', j);
                    expectedBoard[i][j] = 'J';
                }
            }
        }

        BoardPosition positionToCheck = new BoardPosition(numRows - 1, numColumns - 1);
        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertEquals('J', gb.whatsAtPos(positionToCheck));
        assertEquals(gb.toString(), expectedBoardString);

    }

    //Checking to see what is in the exact center of a max board to see if it
    // returns the correct element at that position which should be P
    @Test
    public void test_ExactCenter_FULLBOARD_whatsAtPos() {
        int numRows = 100;
        int numColumns = 100;
        int numToWin = 25;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (j % 2 == 0) {
                    gb.dropToken('P', j);
                    expectedBoard[i][j] = 'P';
                }
                else {
                    gb.dropToken('J', j);
                    expectedBoard[i][j] = 'J';
                }
            }
        }

        BoardPosition positionToCheck = new BoardPosition(numRows / 2, numColumns / 2);
        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertEquals('P', gb.whatsAtPos(positionToCheck));
        assertEquals(gb.toString(), expectedBoardString);
    }

    //isPlayerAtPos tests
    //Checks to see if the element at the bottom left of a full 5x5 board
    // matches the desired character at that position which is O
    @Test
    public void test_bottomLeft_FULLBOARD_isPlayerAtPos() {
        int numRows = 5;
        int numColumns = 4;
        int numToWin = 5;

        int rowToCheck = 0;
        int colToCheck = 0;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (j % 2 == 0) {
                    gb.dropToken('O', j);
                    expectedBoard[i][j] = 'O';
                }
                else {
                    gb.dropToken('X', j);
                    expectedBoard[i][j] = 'X';
                }
            }
        }


        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);
        BoardPosition positionToCheck = new BoardPosition(rowToCheck, colToCheck);

        assertTrue(gb.isPlayerAtPos(positionToCheck, 'O'));
        assertEquals(gb.toString(), expectedBoardString);
    }

    //Checks to see if the element at the bottom right of a full 5x5 board
    // matches the desired character at that position which is X
    @Test
    public void test_bottomRight_FULLBOARD_isPlayerAtPos() {
        int numRows = 5;
        int numColumns = 4;
        int numToWin = 5;

        int rowToCheck = 4;
        int colToCheck = 3;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (j % 2 == 0) {
                    gb.dropToken('O', j);
                    expectedBoard[i][j] = 'O';
                }
                else {
                    gb.dropToken('X', j);
                    expectedBoard[i][j] = 'X';
                }
            }
        }


        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);
        BoardPosition positionToCheck = new BoardPosition(rowToCheck, colToCheck);

        assertTrue(gb.isPlayerAtPos(positionToCheck, 'X'));
        assertEquals(gb.toString(), expectedBoardString);
    }

    //Checks to see if test returns false in an empty 3x3 board when the expected
    // character X is not there
    @Test
    public void test_3_3_EMPTYBOARD_isPlayerAtPos() {
        int numRows = 7;
        int numColumns = 7;
        int numToWin = 5;

        int rowToCheck = 3;
        int colToCheck = 3;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];


        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);
        BoardPosition positionToCheck = new BoardPosition(rowToCheck, colToCheck);

        assertTrue(!gb.isPlayerAtPos(positionToCheck, 'X'));
        assertEquals(gb.toString(), expectedBoardString);
    }

    //checks to see if the element at row 4, column 0 returns the expected
    // character which should be U
    @Test
    public void test_TopLeft_FULLBOARD_isPlayerAtPos() {
        int numRows = 5;
        int numColumns = 4;
        int numToWin = 5;

        int rowToCheck = 4;
        int colToCheck = 0;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (j % 2 == 0) {
                    gb.dropToken('U', j);
                    expectedBoard[i][j] = 'U';
                }
                else {
                    gb.dropToken('W', j);
                    expectedBoard[i][j] = 'W';
                }
            }
        }


        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);
        BoardPosition positionToCheck = new BoardPosition(rowToCheck, colToCheck);

        assertTrue(gb.isPlayerAtPos(positionToCheck, 'U'));
        assertEquals(gb.toString(), expectedBoardString);
    }

    //Checks to see if the element at row 25, column 2 in a 75 x 4 is returns
    // the expected character which should be O
    @Test
    public void test_25_2_HalfFull_isPlayerAtPos() {
        int numRows = 75;
        int numColumns = 4;
        int numToWin = 5;

        int rowToCheck = 25;
        int colToCheck = 2;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        for (int i = 0; i < numRows / 2; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (j % 2 == 0) {
                    gb.dropToken('O', j);
                    expectedBoard[i][j] = 'O';
                }
                else {
                    gb.dropToken('X', j);
                    expectedBoard[i][j] = 'X';
                }
            }
        }
        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);
        BoardPosition positionToCheck = new BoardPosition(rowToCheck, colToCheck);

        assertTrue(gb.isPlayerAtPos(positionToCheck, 'O'));
        assertEquals(gb.toString(), expectedBoardString);
    }

    //dropToken tests
    //Checks if dropToken correctly fills up a column at the lowest possible
    // available position at column 3 in a 5x5 board
    @Test
    public void test_3_fullColumn_DropToken() {
        int numRows = 5;
        int numColumns = 5;
        int numToWin = 5;

        int columnToDrop = 3;
        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            gb.dropToken('X', columnToDrop);
            expectedBoard[i][columnToDrop] = 'X';
        }

        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertEquals(gb.toString(), expectedBoardString);
    }

    //Checks if dropToken can fill up two different columns to a desired
    // limit which in this case if half filled
    @Test
    public void test_0_5_HalfFUll_DropToken() {
        int numRows = 6;
        int numColumns = 6;
        int numToWin = 5;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        int columnToDrop = 0;
        for (int i = 0; i < numRows / 2; i++) {
            gb.dropToken('X', columnToDrop);
            expectedBoard[i][columnToDrop] = 'X';
        }

        columnToDrop = 5;
        for (int i = 0; i < numRows / 2; i++) {
            gb.dropToken('X', columnToDrop);
            expectedBoard[i][columnToDrop] = 'X';
        }

        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertEquals(gb.toString(), expectedBoardString);
    }

    //Checks to see if dropToken can fill the bottom row one at
    // a time if available
    @Test
    public void test_fullBottomRow_DropToken() {
        int numRows = 25;
        int numColumns = 25;
        int numToWin = 5;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        for (int i = 0; i < numColumns; i++) {
            gb.dropToken('X', i);
            expectedBoard[0][i] = 'X';
        }

        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertEquals(gb.toString(), expectedBoardString);
    }

    //checks to see if dropToken can fill the board column by column
    @Test
    public void test_FullBoard_DropToken() {
        int numRows = 25;
        int numColumns = 25;
        int numToWin = 5;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (j % 2 == 0) {
                    gb.dropToken('X', j);
                    expectedBoard[i][j] = 'X';
                }
                else {
                    gb.dropToken('O', j);
                    expectedBoard[i][j] = 'O';
                }
            }
        }

        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertEquals(gb.toString(), expectedBoardString);
    }

    //checks if dropToken can fill the board going diagonally
    @Test
    public void test_DropDiagonally_DropToken() {
        int numRows = 25;
        int numColumns = 25;
        int numToWin = 5;

        IGameBoard gb = GameBoardFactory(numRows, numColumns, numToWin);
        char[][] expectedBoard = new char[numRows][numColumns];

        int RowCount = numRows;
        int ColumnCount = numColumns;

        for (int i = 0; i < RowCount; i++) {
            for (int j = 0; j < ColumnCount; j++) {
                if (j % 2 == 0) {
                    gb.dropToken('X', j);
                    expectedBoard[i][j] = 'X';
                }
                else {
                    gb.dropToken('O', j);
                    expectedBoard[i][j] = 'O';
                }
            }
            ColumnCount = ColumnCount - 1;
        }

        String expectedBoardString = makeExpectedGameBoard(expectedBoard, numRows, numColumns);

        assertEquals(gb.toString(), expectedBoardString);
    }

}
