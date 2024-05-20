import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;

public class PathFindingGUI extends JFrame {
    private static final int CELL_SIZE = 50;
    private MapData mapData;
    private JPanel cards;
    private List<int[]> pathBFS;
    private List<int[]> pathDFS;
    private MapPanel bfsPanel;
    private MapPanel dfsPanel;
    private String selectedAlgorithm;

    public PathFindingGUI(MapData mapData) {
        this.mapData = mapData;
        setTitle("Path Finding Visualization");
        setSize(MapData.SIZE * CELL_SIZE + 200, MapData.SIZE * CELL_SIZE + 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] algorithmNames = {"Select Algorithm", "BFS Algorithm", "DFS Algorithm"};
        JComboBox<String> algorithmSelector = new JComboBox<>(algorithmNames);
        algorithmSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedAlgorithm = (String) algorithmSelector.getSelectedItem();
                CardLayout cl = (CardLayout) (cards.getLayout());
                if ("BFS Algorithm".equals(selectedAlgorithm)) {
                    cl.show(cards, "BFS");
                } else if ("DFS Algorithm".equals(selectedAlgorithm)) {
                    cl.show(cards, "DFS");
                }
            }
        });

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedAlgorithm != null) {
                    if ("BFS Algorithm".equals(selectedAlgorithm)) {
                        ShortestPath shortestPath = new ShortestPath(mapData.getMap());
                        pathBFS = shortestPath.findShortestPathBFS(1, 1, 8, 9);
                        bfsPanel.setPath(pathBFS);
                        bfsPanel.repaint();
                    } else if ("DFS Algorithm".equals(selectedAlgorithm)) {
                        ShortestPath shortestPath = new ShortestPath(mapData.getMap());
                        pathDFS = shortestPath.findShortestPathDFS(1, 1, 8, 9);
                        dfsPanel.setPath(pathDFS);
                        dfsPanel.repaint();
                    }
                }
            }
        });

        cards = new JPanel(new CardLayout());
        bfsPanel = new MapPanel(mapData);
        dfsPanel = new MapPanel(mapData);
        cards.add(new JLabel("Please select an algorithm from the dropdown above."), "Select Algorithm");
        cards.add(bfsPanel, "BFS");
        cards.add(dfsPanel, "DFS");

        JPanel controlPanel = new JPanel();
        controlPanel.add(algorithmSelector);
        controlPanel.add(startButton);

        add(cards, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private class MapPanel extends JPanel {
        private char[][] mapData;
        private List<int[]> path;

        public MapPanel(MapData mapData) {
            this.mapData = mapData.getMap();
            setPreferredSize(new Dimension(MapData.SIZE * CELL_SIZE, MapData.SIZE * CELL_SIZE));
        }

        public void setPath(List<int[]> path) {
            this.path = path;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (mapData != null) {
                for (int i = 0; i < MapData.SIZE; i++) {
                    for (int j = 0; j < MapData.SIZE; j++) {
                        if (mapData[i][j] == MapData.WALL) {
                            g.setColor(Color.BLACK);
                        } else if (mapData[i][j] == MapData.START) {
                            g.setColor(Color.GREEN);
                        } else if (mapData[i][j] == MapData.GOAL) {
                            g.setColor(Color.RED);
                        } else {
                            g.setColor(Color.WHITE);
                        }
                        g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        g.setColor(Color.GRAY);
                        g.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    }
                }
            }

            if (path != null) {
                g.setColor(Color.BLUE);
                for (int[] cell : path) {
                    g.fillRect(cell[1] * CELL_SIZE, cell[0] * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    public static void main(String[] args) {
        MapData mapData = new MapData();
        PathFindingGUI pathFindingGUI = new PathFindingGUI(mapData);
        pathFindingGUI.setVisible(true);
    }
}
