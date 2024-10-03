/*GROUP MEMBER NAMES AND GITHUB USERNAMES SHOULD GO HERE
Jacob Colson (jccolso)
Warren Wasden (wwasden)
Steven Cabezas (scabeza)

*/

package cpsc2150.extendedConnectX.models;

/**
 * This AbsGameBoard class implements the interface IGameBoard.
 * @invariant [GameBoard should be valid board of size specified by the user.] AND
 * [Rows and columns must be greater than 3 and less than 100] AND [Player token must be a letter]
 *
 * @corresponds num_rows = ROWS
 * @corresponds num_columns = COLUMNS
 * @corresponds num_to_win = numToWin
 *
 */
public abstract class AbsGameBoard implements IGameBoard
{
    public static final int Doubledigit = 10;
    /**
     * Creates a string for the GameBoard containing each value within the GameBoard.
     *
     * @return the GameBoard array as a String.
     *
     * @pre None
     *
     * @post [toString returns each value contained within the GameBoard
     * as a string formatted as the GameBoard] AND ROWS = #ROWS AND COLUMNS = #COLUMNS AND numToWin = #numToWin
     * AND self = #self
     *
     */
    @Override
    public String toString() {

        //Begins string with header for the output GameBoard
        String boardString = "";
        for (int i = 0; i < getNumColumns(); i++) {
            if (i < Doubledigit) {
                boardString += "| ";
            }
            else {
                boardString += '|';
            }
            boardString += i;
        }

        boardString += "|\n";

        for (int i = getNumRows() - 1; i >= 0; i--) {
            for (int j = 0; j < getNumColumns(); j++) {
                //Creates a new BoardPosition for current spot on GameBoard
                BoardPosition currentPos = new BoardPosition(i, j);
                boardString += ('|');

                //Appends value at currentPos to the String
                if (!Character.isLetter(whatsAtPos(currentPos))) {
                    boardString += "  ";
                }
                else {
                    boardString += (whatsAtPos(currentPos));
                    boardString += ' ';
                }
            }
            boardString += ('|');
            boardString += '\n';
        }

        //Returns the generated String
        return boardString;
    }
}
