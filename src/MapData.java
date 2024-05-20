public class MapData {
    public static final int SIZE = 11;
    public static final char WALL = '#';
    public static final char ROAD = '.';
    public static final char START = 'S';
    public static final char GOAL = 'G';

    private char[][] map = {
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
            {'#', 'S', '.', '.', '.', '.', '#', '.', '.', '.', '#'},
            {'#', '#', '#', '.', '#', '.', '#', '.', '#', '.', '#'},
            {'#', '.', '.', '.', '#', '.', '.', '.', '#', '.', '#'},
            {'#', '.', '#', '#', '#', '#', '#', '#', '#', '.', '#'},
            {'#', '.', '.', '.', '.', '.', '.', '#', '.', '.', '#'},
            {'#', '#', '#', '#', '#', '#', '.', '#', '.', '#', '#'},
            {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', 'G', '#'},
            {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}
    };

    public char[][] getMap() {
        return map;
    }

    public void printMap() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
