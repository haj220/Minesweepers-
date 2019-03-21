package cs2114.minesweeper;
import java.util.Random;

/**
 * @author Haonan Jiang
 * @version 2018.02.16
 */
public class MineSweeperBoard extends MineSweeperBoardBase {
    private int width;
    private int height;
    private int mNumber;
    private MineSweeperCell [][] game;
    /**
     * i is just a variable
     */
    private int i = 0;
    
    //constructor
    /**
     * @param width the width of the game
     * @param height the height of the game
     * @param mNumber the number of the mines
     */
    public MineSweeperBoard(int width, int height, int mNumber) {
        Random random = new Random();
        this.width = width;
        this.height = height;
        if (mNumber > width * height) {
            this.mNumber = width * height;
        }
        else {
            this.mNumber = mNumber;
        }
        game = new MineSweeperCell[width][height];
        for (int m = 0; m < width; m++) {
            for (int n = 0; n < height; n++) {
                game[m][n] = MineSweeperCell.COVERED_CELL;
            }
        }
        while (i < this.mNumber) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            i++;
            if (game[x][y] == MineSweeperCell.MINE ) {
                i--;
            }
            game[x][y] = MineSweeperCell.MINE;
        }
    }
    //method
    @Override
    
    public void flagCell(int x, int y) {
        if (x < width && y < height && (
                game[x][y] == MineSweeperCell.COVERED_CELL || 
                game[x][y] == MineSweeperCell.MINE
                || game[x][y] == MineSweeperCell.FLAGGED_MINE || 
                game[x][y] == MineSweeperCell.FLAG)) {
            if (game[x][y] == MineSweeperCell.COVERED_CELL) {
                game[x][y] = MineSweeperCell.FLAG;
            } 
            else if (game[x][y] == MineSweeperCell.MINE) {
                game[x][y] = MineSweeperCell.FLAGGED_MINE;
            }

            else if (game[x][y] == MineSweeperCell.FLAG) {
                game[x][y] = MineSweeperCell.COVERED_CELL;
            } 
            else {
                game[x][y] = MineSweeperCell.MINE;
            }
        }

    }
    @Override
    public MineSweeperCell getCell(int x, int y) {
        if (x < width && y < height) {
            return game[x][y];
        }
        else {
            return MineSweeperCell.INVALID_CELL;
        }
    }
    @Override
    public int height() {
        return height;
    }
    @Override
    public boolean isGameLost() {
        for (int m = 0; m < width; m++) {
            for (int n = 0; n < height; n++) {
                if (game[m][n] == MineSweeperCell.UNCOVERED_MINE) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public boolean isGameWon() {
        for (int m = 0; m < width; m++) {
            for (int n = 0; n < height; n++) {
                if (game[m][n] == MineSweeperCell.UNCOVERED_MINE || 
                        game[m][n] == MineSweeperCell.MINE || 
                        game[m][n] == MineSweeperCell.COVERED_CELL ||
                        game[m][n] == MineSweeperCell.FLAG) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public int numberOfAdjacentMines(int x, int y) {
        int num = 0;
        if (x < width && y < height) {
            for (int m = -1; m <= 1; m++) {
                for (int n = -1; n <= 1; n++) {
                    if (x + m < width && 
                            y + n < height &&
                            x + m >= 0 &&
                            y + n >= 0) {
                        if (game[x + m][y + n] == 
                                MineSweeperCell.UNCOVERED_MINE ||
                                game[x + m][y + n]
                                        == MineSweeperCell.MINE ||
                                game[x + m][y + n] 
                                        == MineSweeperCell.FLAGGED_MINE) {
                            num++;
                        }
                    }
                }
            }
            return num;
        }
        else {
            return -1;
        }
    }
    @Override
    public void revealBoard() {
        for (int m = 0; m < width; m++) {
            for (int n = 0; n < height; n++) {
                this.uncoverCell(m, n);
            }
        }
    }
    @Override
    public void uncoverCell(int x, int y) {
        if (x < width && y < height) {

            if (game[x][y] == MineSweeperCell.MINE) {
                game[x][y] = MineSweeperCell.UNCOVERED_MINE;
            } 
            else if (game[x][y] == MineSweeperCell.COVERED_CELL) {
                game[x][y] = MineSweeperCell.adjacentTo(
                        this.numberOfAdjacentMines(x, y));
            }
        }

    }
    @Override
    public int width() {
        return width;
    }
   
    
    
   
   
   
    
    
    @Override
    protected void setCell(int x, int y, MineSweeperCell contain) {
        if (x < width && y < height) {
            game[x][y] = contain;
        }
        
    }
}
