import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private final int n;
    private final int[] board;
    private int blankPos;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException();
        }
        if (tiles.length != tiles[0].length) {
            throw new IllegalArgumentException();
        }
        n = tiles.length;
        board = new int[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i * n + j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    blankPos = i * n + j;
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n);
        s.append("\n");
        for (int i = 0; i < n * n; i++) {
            s.append(board[i] + " ");
            if (i % n == n - 1) {
                s.append("\n");
            }
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int cnt = 0;
        for (int i = 0; i < n * n; i++) {
            if (board[i] != 0 && board[i] != i + 1) {
                cnt++;
            }
        }
        return cnt;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sumManhattan = 0;
        for (int i = 0; i < n * n; i++) {
            if (board[i] == 0) continue;
            sumManhattan += Math.abs(i / n - (board[i] - 1) / n);
            sumManhattan += Math.abs(i % n - (board[i] - 1) % n);
        }
        return sumManhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n * n - 1; i++) {
            if (board[i] != i + 1) {
                return false;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y.getClass() == this.getClass()) {
            if (Arrays.equals(board, ((Board) y).board)) {
                return true;
            }
        }
        return false;
    }

    // convert 1D array to 2D array.
    private int[][] convertTo2Darray(int[] a) {
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = a[i * n + j];
            }
        }
        return tiles;
    }

    private boolean checkBox(int row, int col) {
        return (row >= 0 && row < n && col >= 0 && col < n);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int[] r = {0, 1, 0, -1};
        int[] c = {1, 0, -1, 0};
        List<Board> neighbors = new ArrayList<>();
        int row = blankPos / n;
        int col = blankPos % n;
        for (int k = 0; k < 4; k++) {
            if (checkBox(row + r[k], col + c[k])) {
                int[] newBoard = board.clone();
                int tmp = newBoard[blankPos];
                newBoard[blankPos] = newBoard[blankPos + r[k] * n + c[k]];
                newBoard[blankPos + r[k] * n + c[k]] = tmp;
                neighbors.add(new Board(convertTo2Darray(newBoard)));
            }
        }
        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[] r = {0, 1, 0, -1};
        int[] c = {1, 0, -1, 0};
        boolean twinSuccess = false;
        int k = 0;
        for (int i = 0; i < n * n; i++) {
            if (board[i] != 0 && board[i] != i + 1) {
                k = i;
                break;
            }
        }
        int row = k / n;
        int col = k % n;
        int[] tmpBoard = board.clone();
        int choice = 0;
        while (!twinSuccess) {
            if (checkBox(row + r[choice], col + c[choice])) {
                if (k + r[choice] * n + c[choice] != blankPos) {
                    int tmp = tmpBoard[k];
                    tmpBoard[k] = tmpBoard[k + r[choice] * n + c[choice]];
                    tmpBoard[k + r[choice] * n + c[choice]] = tmp;
                    twinSuccess = true;
                }
            }
            choice++;
        }
        return new Board(convertTo2Darray(tmpBoard));
    }
}