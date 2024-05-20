import java.util.*;

public class ShortestPath {
    private static final int[][] DIRECTIONS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private char[][] map;
    private int rows, cols;

    public ShortestPath(char[][] map) {
        this.map = map;
        this.rows = map.length;
        this.cols = map[0].length;
    }

    private static class Node {
        int row, col, distance;

        Node(int row, int col, int distance) {
            this.row = row;
            this.col = col;
            this.distance = distance;
        }
    }

    public List<int[]> findShortestPathBFS(int startRow, int startCol, int goalRow, int goalCol) {
        Queue<Node> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];
        int[][] distances = new int[rows][cols];
        Node[][] parents = new Node[rows][cols];

        queue.offer(new Node(startRow, startCol, 0));
        visited[startRow][startCol] = true;

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int row = current.row;
            int col = current.col;
            int distance = current.distance;

            if (row == goalRow && col == goalCol) {
                return reconstructPath(parents, startRow, startCol, goalRow, goalCol);
            }

            for (int[] dir : DIRECTIONS) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                if (isValid(newRow, newCol) && map[newRow][newCol] != MapData.WALL && !visited[newRow][newCol]) {
                    visited[newRow][newCol] = true;
                    distances[newRow][newCol] = distance + 1;
                    parents[newRow][newCol] = new Node(row, col, distance);
                    queue.offer(new Node(newRow, newCol, distance + 1));
                }
            }
        }

        return Collections.emptyList();
    }

    public List<int[]> findShortestPathDFS(int startRow, int startCol, int goalRow, int goalCol) {
        Stack<Node> stack = new Stack<>();
        boolean[][] visited = new boolean[rows][cols];
        int[][] distances = new int[rows][cols];
        Node[][] parents = new Node[rows][cols];

        stack.push(new Node(startRow, startCol, 0));

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            int row = current.row;
            int col = current.col;
            int distance = current.distance;

            if (visited[row][col]) continue;

            visited[row][col] = true;
            distances[row][col] = distance;

            if (row == goalRow && col == goalCol) {
                return reconstructPath(parents, startRow, startCol, goalRow, goalCol);
            }

            for (int[] dir : DIRECTIONS) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                if (isValid(newRow, newCol) && map[newRow][newCol] != MapData.WALL && !visited[newRow][newCol]) {
                    parents[newRow][newCol] = new Node(row, col, distance);
                    stack.push(new Node(newRow, newCol, distance + 1));
                }
            }
        }

        return Collections.emptyList();
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    private List<int[]> reconstructPath(Node[][] parents, int startRow, int startCol, int goalRow, int goalCol) {
        List<int[]> path = new ArrayList<>();
        int row = goalRow;
        int col = goalCol;

        while (!(row == startRow && col == startCol)) {
            path.add(new int[]{row, col});
            Node parent = parents[row][col];
            row = parent.row;
            col = parent.col;
        }
        path.add(new int[]{startRow, startCol});
        Collections.reverse(path);

        return path;
    }

    public static void main(String[] args) {
        MapData mapData = new MapData();
        char[][] map = mapData.getMap();

        ShortestPath shortestPath = new ShortestPath(map);

        int startRow = 1;
        int startCol = 1;
        int goalRow = 8;
        int goalCol = 9;

        List<int[]> pathBFS = shortestPath.findShortestPathBFS(startRow, startCol, goalRow, goalCol);
        List<int[]> pathDFS = shortestPath.findShortestPathDFS(startRow, startCol, goalRow, goalCol);

        System.out.println("Shortest Path using BFS:");
        System        .out.println("Path: " + pathBFS);
        System.out.println("Distance: " + (pathBFS.size() - 1));

        System.out.println("Shortest Path using DFS:");
        System.out.println("Path: " + pathDFS);
        System.out.println("Distance: " + (pathDFS.size() - 1));
    }
}

