package cpsc2150.extendedConnectX.models;

/*GROUP MEMBER NAMES AND GITHUB USERNAMES SHOULD GO HERE
Jacob Colson (jccolso)
Warren Wasden (wwasden)
Steven Cabezas (scabeza)

*/

import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * This class contains the methods that can be done by the game in order to validate token positions
 * and to advance the game for the memory efficient option.
 *
 * @invariant [GameBoard should be of size ROWS x COLUMNS specified by the user]
 * AND ROWS >= [minimum number of rows] AND ROWS <= [Maximum number of rows] AND COLUMNS >= [minimum number of columns AND
 * Columns <= [Maximum number of columns] AND numToWin >= [Minimum number to win]
 * AND [numToWin <= [Maximum number to win] or numToWin <= [Maximum number to win] iff COLUMNS <= [Max number of columns]
 * or ROWS <= [Max Number of rows] else numToWin <= ROWS or numToWin <= COLUMNS] AND
 *[GameBoard cannot contain blank ' ' between two indicies of player token]
 *
 * @corresponds num_rows = ROWS
 * @corresponds num_columns = COLUMNS
 * @corresponds num_to_win = numToWin
 *
 */
public class GameBoardMem extends AbsGameBoard implements IGameBoard {

    private static HashMap<Character, List<BoardPosition>> gameMap;
    private int ROWS;
    private int COLUMNS;
    private static int numToWin;

    /**
     * Constructs a memory efficient GameBoard that is the size specified by the parameters
     * and contains blank positions for every space on the board
     *
     * @param aRow number of rows for the GameBoard
     * @param aCol number of columns for the GameBoard
     * @param numWin number required to win the game
     *
     * @pre [min number of rows <= aRow <= max number of rows] AND [min number of columns <= aCol <= max number of columns]
     * AND [min number to win <= numWin <= max number to win]
     * @post ROWS = aRow AND COLUMNS = aCol AND numToWin = numWin
     */
    public GameBoardMem(int aRow, int aCol, int numWin) {
        gameMap = new HashMap<>();
        ROWS = aRow;
        COLUMNS = aCol;
        numToWin = numWin;
    }


    /**
     * Check which character is at any position on the GameBoard and returns said character.
     *
     * @param pos Current position on the GameBoard HashMap.
     *
     * @return Player token that is at desired position as a Character
     *
     * @pre None
     *
     * @post whatsAtPos = [whatsAtPos returns which player's character is at the current position on the game board
     * array] AND ROWS = #ROWS AND COLUMNS = #COLUMNS AND numToWin = #numToWin AND self = #self
     */
    @Override
    public char whatsAtPos(BoardPosition pos) {

        //Iterates through each key in gameMap
        for (char c : gameMap.keySet()) {

            //Check each element in the list value in gameMap
            for (BoardPosition p : gameMap.get(c)) {

                //returns element found at pos.
                if (p.equals(pos)) {
                    return c;
                }
            }
        }

        //returns blank if key is never found in gameMap
        return ' ';
    }

    /**
     * Determine which player has placed a token at the position
     * on the GameBoard
     *
     * @param pos Indicates current position on the GameBoard
     *
     * @param player character of the player
     *
     * @return [boolean, true iff correct player token is at position
     * false iff other player is at position]
     *
     * @pre None
     *
     * @post isPlayerAtPos = [isPlayerAtPos returns true iff the player is at the corresponding position on the
     * GameBoard, return true iff the player is at the corresponding position]
     * AND self = #self AND ROWS = #ROWS AND COLUMNS = #COLUMNS AND numToWin = #numToWin
     */
    @Override
    public boolean isPlayerAtPos(BoardPosition pos, char player) {

        //returns true if gameMap contains Key and List contains position
        if (gameMap.containsKey(player)) {
            if (gameMap.get(player).contains(pos)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Drops a token to the bottom of the board in the lowest unoccupied specified
     * column
     *
     * @param p indicates player's character
     *
     * @param c indicates the column number
     *
     * @pre [p is valid player token current in play] AND c >= [min number of columns] AND [c <= getNumColumns - 1]
     *
     * @post [Drops token on GameBoard at column c, contains player character]
     * AND ROWS = #ROWS AND COLUMNS = #COLUMNS AND numToWin = #numToWin AND self = #self
     */
    @Override
    public void dropToken(char p, int c) {

        //Initializes a BoardPosition object and List for key,value pair
        BoardPosition pos = new BoardPosition(0, c);

        //Updates pos until blank space is found.
        if (whatsAtPos(pos) != ' ') {
            while (whatsAtPos(pos) != ' ') {
                pos = new BoardPosition(pos.getRow() + 1, c);
            }
        }

        List<BoardPosition> positList = new ArrayList<BoardPosition>();

        //Makes sure position is not already on the list.
        if (gameMap.get(p) == null) {
            positList.add(pos);
            gameMap.put(p, positList);
        }
        //Simply adds pos to list if key does exist.
        else {
            gameMap.get(p).add(pos);
        }


    }

    /**
     * Returns the value for ROWS
     *
     * @return the value for ROWS as an int
     *
     * @pre None
     *
     * @post getNumRows = ROWS AND ROWS = #ROWS AND COLUMNS = #COLUMNS AND numToWin = #numToWin AND self = #self
     *
     */
    @Override
    public int getNumRows() {
        return ROWS;
    }

    /**
     * Returns the value for COLUMNS
     *
     * @return the value for COLUMNS as an int
     *
     * @pre None
     *
     * @post getNumColumns = COLUMNS AND ROWS = #ROWS AND COLUMNS = #COLUMNS AND numToWin = #numToWin AND self = #self
     *
     */
    @Override
    public int getNumColumns() {
        return COLUMNS;
    }

    /**
     * Returns the value for numToWin
     *
     * @return the value for numToWin as an int
     *
     * @pre None
     *
     * @post getNumColumns = COLUMNS AND ROWS = #ROWS AND COLUMNS = #COLUMNS AND numToWin = #numToWin
     *
     */
    @Override
    public int getNumToWin() {
        return numToWin;
    }
}
