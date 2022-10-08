import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import java.util.Scanner;

public class Solver {
    private int n;
    private Board initial;
    private SearchNode goal;

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private int priority;
        private SearchNode prevNode;

        public SearchNode(Board board, int moves, SearchNode prevNode) {
            this.board = board;
            this.moves = moves;
            this.prevNode = prevNode;
            this.priority = moves + board.manhattan();
        }

        public int compareTo(SearchNode that) {
            return (this.priority - that.priority);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        this.initial = initial;
        n = initial.dimension();
        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> pqTwin = new MinPQ<>();
        pq.insert(new SearchNode(initial, 0, null));
        pqTwin.insert(new SearchNode(initial.twin(), 0, null));
        int cnt = 0;
        while (!pq.isEmpty() && !pqTwin.isEmpty()) {
            SearchNode minPQ = pq.min();
            SearchNode minPQTwin = pq.min();
            cnt += 10;
            if(cnt > 999990) {
                goal = null;
                break;
            }
            if (minPQ.board.isGoal()) {
                goal = minPQ;
                break;
            } else if (minPQTwin.board.isGoal()) {
                goal = null;
                break;
            }
            pq.delMin();
            pqTwin.delMin();
            for (Board neighbor : minPQ.board.neighbors()) {
                if (minPQ.prevNode == null || !minPQ.prevNode.board.equals(neighbor)) {
                    pq.insert(new SearchNode(neighbor, minPQ.moves + 1, minPQ));
                    cnt++;
                }
            }
            for (Board neighbor : minPQTwin.board.neighbors()) {
                if (minPQTwin.prevNode == null || !minPQTwin.prevNode.board.equals(neighbor)) {
                    pqTwin.insert(new SearchNode(neighbor, minPQTwin.moves + 1, minPQTwin));
                    cnt++;
                }
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        if (goal == null) {
            return false;
        }
        return true;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) {
            return goal.moves;
        }
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        Stack<Board> stackSolution = new Stack<>();
        SearchNode current = goal;
        while (current.prevNode != null) {
            stackSolution.push(current.board);
            current = current.prevNode;
        }
        stackSolution.push(current.board);
        return stackSolution;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = scanner.nextInt();
            }
        }
        Board board = new Board(tiles);
        System.out.println(board.twin().toString());
        Solver solver = new Solver(board);
        System.out.println(solver.isSolvable());
        System.out.println(solver.solution());
    }

}

