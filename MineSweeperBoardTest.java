package cs2114.minesweeper;

import student.TestCase;

/**
 * @author Haonan Jiang
 * @version 2018.02.16
 */
public class MineSweeperBoardTest extends TestCase {

    /**
     * empty constructor
     */
    public MineSweeperBoardTest() {
        // empty constructor
    }

    /**
     * This method test Equals.
     */
    public void testEqual() {
        MineSweeperBoard mBoard1 = new MineSweeperBoard(4, 4, 6);
        MineSweeperBoard mBoard2 = new MineSweeperBoard(4, 4, 6);
        mBoard1.loadBoardState("    ", "OOOO", "O++O", "OOOO");
        mBoard2.loadBoardState("    ", "OOOO", "O++O", "OOOO");
        // test the same board same dimensions
        assertTrue(mBoard1.equals(mBoard2));
        // same board testing same board
        assertTrue(mBoard1.equals(mBoard1));
        // testing same dimensions board with different cell
        MineSweeperBoard mBoard3 = new MineSweeperBoard(4, 4, 6);
        mBoard3.loadBoardState("    ", "O+OO", "O++O", "OOOO");
        assertFalse(mBoard1.equals(mBoard3));
        MineSweeperBoard mBoard4 = new MineSweeperBoard(15, 1, 0);
        mBoard4.loadBoardState("OFM+* 123456788");
        assertFalse(mBoard1.toString().equals(mBoard3.toString()));
        // testing two string against a board
        assertFalse(mBoard4.toString().equals(mBoard2.toString()));
        // testing against a string
        assertFalse(mBoard1.equals("abc"));
        assertNotNull(mBoard1);
        // same width but different height
        MineSweeperBoard mBoard6 = new MineSweeperBoard(4, 5, 6);
        mBoard6.loadBoardState("    ", "O+OO", "O++O", "OOOO", "OOOO");
        assertFalse(mBoard6.equals(mBoard1));
        // different width same height
        MineSweeperBoard mBoard5 = new MineSweeperBoard(5, 4, 6);
        mBoard5.loadBoardState("     ", "O+OOO", "O++OO", "OOOOO");
        assertFalse(mBoard5.equals(mBoard1));
    }

    /**
     * test FlagCell
     */
    public void testFlagCell() {
        MineSweeperBoard game1 = new MineSweeperBoard(4, 4, 2);
        game1.loadBoardState("    ", "OOOO", "O++O", "OOOO");

        game1.flagCell(2, 2);
        assertBoard(game1, "    ", "OOOO", "O+MO", "OOOO");

        game1.flagCell(2, 1);
        assertBoard(game1, "    ", "OOFO", "O+MO", "OOOO");

        game1.flagCell(2, 2);
        assertBoard(game1, "    ", "OOFO", "O++O", "OOOO");

        game1.flagCell(2, 1);
        assertBoard(game1, "    ", "OOOO", "O++O", "OOOO");

        game1.flagCell(1, 9);
        assertBoard(game1, "    ", "OOOO", "O++O", "OOOO");

        game1.flagCell(9, 2);
        assertBoard(game1, "    ", "OOOO", "O++O", "OOOO");

        game1.flagCell(0, 0);
        assertBoard(game1, "    ", "OOOO", "O++O", "OOOO");
    }

    /**
     * test GetCell
     */
    public void testGetCell() {
        MineSweeperBoard game1 = new MineSweeperBoard(4, 4, 2);
        game1.loadBoardState("    ", "OOOO", "O++O", "OOOO");

        assertEquals(game1.getCell(19, 3), MineSweeperCell.INVALID_CELL);
        assertEquals(game1.getCell(0, 18), MineSweeperCell.INVALID_CELL);
        assertEquals(game1.getCell(3, 1), MineSweeperCell.COVERED_CELL);
    }

    /**
     * test IsGameLost
     */
    public void testIsGameLost() {
        MineSweeperBoard game1 = new MineSweeperBoard(4, 4, 2);
        game1.loadBoardState("    ", "OOOF", "OM*O", "OO+O");
        assertTrue(game1.isGameLost());

        game1.flagCell(2, 2);
        game1.loadBoardState("    ", "OO3F", "OM+O", "OO+O");
        assertFalse(game1.isGameLost());

        MineSweeperBoard board2 = new MineSweeperBoard(0, 0, 10);
        assertFalse(board2.isGameLost());

    }

    /**
     * test IsGameWon
     */
    public void testIsGameWon() {
        MineSweeperBoard game1 = new MineSweeperBoard(4, 4, 2);
        game1.loadBoardState("    ", "1111", "11M1", "1111");
        assertTrue(game1.isGameWon());

        game1.loadBoardState("    ", "OOOO", "OMOO", "OOOO");
        assertFalse(game1.isGameWon());
        game1.loadBoardState("    ", "12F1", "11M1", "1111");
        assertFalse(game1.isGameWon());
        game1.loadBoardState("    ", "1111", "11+1", "1111");
        assertFalse(game1.isGameWon());
        game1.loadBoardState("    ", "1111", "1**1", "1111");
        assertFalse(game1.isGameWon());
    }

    /**
     * test loadBoardState
     */
    public void testloadBoardState() {
        MineSweeperBoard a = new MineSweeperBoard(2, 2, 1);
        Exception thrown = null;
        // loadBoardState testing
        // wrong number of rows
        try {
            a.loadBoardState("00");
        } 
        catch (Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        thrown = null;
        // wrong number of columns
        try {
            a.loadBoardState("0000 ", " ");
        } 
        catch (Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        // Wrong symbol in cell
        try {
            a.loadBoardState("00", "$+");
        } 
        catch (Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
    }

    /**
     * test NumberOfAdjacentMines
     */

    public void testNumberOfAdjacentMines() {
        MineSweeperBoard game1 = new MineSweeperBoard(4, 4, 2);
        game1.loadBoardState("    ", "OOOO", "O++O", "OOOO");

        assertEquals(0, game1.numberOfAdjacentMines(0, 0));
        assertEquals(1, game1.numberOfAdjacentMines(3, 3));
        assertEquals(2, game1.numberOfAdjacentMines(2, 1));
        assertEquals(-1, game1.numberOfAdjacentMines(1, 8));
        assertEquals(-1, game1.numberOfAdjacentMines(12, 4));
    }

    /**
     * test AdjacentTo
     */
    public void testAdjacentTo() {
        MineSweeperCell c = MineSweeperCell.ADJACENT_TO_0;
        assertNotNull(c);
        // testing for exception
        Exception thrown = null;
        try {
            c = MineSweeperCell.adjacentTo(10);
        } 
        catch (Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);

        thrown = null;
        try {
            MineSweeperCell.adjacentTo(-1);
        } 
        catch (Exception e) {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertNotNull(MineSweeperCell.values());
        assertNotNull(MineSweeperCell.valueOf(
                MineSweeperCell.ADJACENT_TO_0.toString()));
    }

    /**
     * test RevealBoard
     */
    public void testRevealBoard() {
        MineSweeperBoard game1 = new MineSweeperBoard(4, 4, 2);
        game1.loadBoardState("    ", "OOOO", "O++O", "OOOO");

        game1.revealBoard();
        assertBoard(game1, "    ", "1221", "1**1", "1221");
    }

    /**
     * @param theBoard
     *            set the board equals to theBoard
     * @param expected
     *            ?
     */
    public void assertBoard(MineSweeperBoard theBoard, String... expected) {
        MineSweeperBoard expectedBoard = new MineSweeperBoard(
                expected[0].length(), expected.length, 0);
        expectedBoard.loadBoardState(expected);
        theBoard.equals(expectedBoard);
    }

    // ----------------------------------------------------------
    /**
     * An example test case for the setCell() method.
     */
    public void testSetCell() {
        // board is declared as part of the test fixture, and
        // is initialized to be 4x4
        MineSweeperBoard game1 = new MineSweeperBoard(4, 4, 2);
        game1.loadBoardState("    ", "OOOO", "O++O", "OOOO");

        game1.setCell(1, 3, MineSweeperCell.FLAGGED_MINE);
        assertBoard(game1, "    ", "OOOO", "OO+O", "OMOO");

        game1.setCell(9, 3, MineSweeperCell.FLAGGED_MINE);
        assertBoard(game1, "    ", "OOOO", "OO+O", "OMOO");

        game1.setCell(3, 6, MineSweeperCell.FLAGGED_MINE);
        assertBoard(game1, "    ", "OOOO", "OO+O", "OOMO");
    }

    /**
     * test UncoverCell
     */
    public void testUncoverCell() {
        MineSweeperBoard game1 = new MineSweeperBoard(4, 4, 2);
        game1.loadBoardState("    ", "OOOO", "O+MO", "OOOF");

        game1.uncoverCell(2, 3);
        assertBoard(game1, "    ", "OOOO", "O+MO", "OO3F");
        game1.uncoverCell(2, 3);
        assertBoard(game1, "    ", "OOOO", "O+MO", "OO3F");
        game1.uncoverCell(2, 2);
        assertBoard(game1, "    ", "OOOO", "O+*O", "OOOF");
        game1.uncoverCell(3, 2);
        assertBoard(game1, "    ", "OOOO", "O+*2", "OOOF");
        game1.uncoverCell(3, 10);
        assertBoard(game1, "    ", "OOOO", "O+*2", "OOOF");
        game1.uncoverCell(100, 5);
        assertBoard(game1, "    ", "OOOO", "O+*2", "OOOF");

        game1.uncoverCell(1, 3);
        assertBoard(game1, "    ", "OOOO", "O**2", "OOOF");

        game1.uncoverCell(3, 3);
        assertBoard(game1, "    ", "OOOO", "O**2", "OOOF");
    }
}