import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener, MouseMotionListener {
    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 800;
    static final int UNIT_SIZE = 20;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;

    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int cols =( SCREEN_WIDTH / UNIT_SIZE );
    int rows = (SCREEN_HEIGHT / UNIT_SIZE);
    int grid[][] = new int[cols ][rows];

    int DELAY = 40;
    boolean running = false;
    Timer timer;

    int hueValue = 200;

    GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addMouseMotionListener(this);
        this.addKeyListener(new MyKeyAdapter());
        start();
    }

    public void start() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();

    }

    
    @Override
    public void mouseMoved(MouseEvent e) {
        int xCoord = e.getX();
        int yCoord = e.getY();
        // Calculate grid position
        int gridX = xCoord / UNIT_SIZE;
        int gridY = yCoord / UNIT_SIZE;
        if (gridY < rows - 1 && gridX >= 0 && gridX < cols) {
            int cellValue = grid[gridX][gridY]; // Store clicked cell value
            // If clicked cell is empty and cell below is empty, move down
            if (cellValue == 0 ) {
                grid[gridX][gridY] = ++hueValue; // Fill cell below
            }

            if (hueValue > 360) {
                 hueValue = 1;
            }
         }
    }
    @Override
    public void mouseDragged(MouseEvent e) {
      
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            for (int i = 0; i<SCREEN_HEIGHT/UNIT_SIZE; i++) {
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] > 0) {
                        float hue = (float)grid[i][j]/360;
                        g.setColor(Color.getHSBColor(hue, 1.0f, 1.0f));
                        g.fillRect(i*UNIT_SIZE, j*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                    }
                }
            }
        }
    }

    public void move() {
        int nextGrid[][] = new int[cols][rows]; 

        for (int i = 0; i < cols; i++) {
            for (int j= 0; j < rows; j++) {
                int state = grid[i][j];
                if (state > 0) {
                    int below = (j + 1 < rows) ? grid[i][j + 1] : 1;
                    int belowA = -1;
                    int belowB = -1;
                    int randomDirection = Math.random() < 0.5 ? 1 : -1;
                    
                    if(withinCols(i+randomDirection)) {
                        belowA = (j + 1 < rows) ? grid[i+randomDirection][j + 1] : 1;
                    }
                    if(withinCols(i-randomDirection)) {
                        belowB = (j + 1 < rows) ? grid[i-randomDirection][j + 1] : 1;
                    }
                    if (below == 0) {                        
                        nextGrid[i][j + 1] = state;
                    } else if (belowA == 0) {
                        nextGrid[i+randomDirection][j + 1] = state;
                    } else if (belowB == 0) {
                        nextGrid[i-randomDirection][j + 1] = state;
                    } else {
                        nextGrid[i][j] = state;
                    }
                }
            }
        }

        grid = nextGrid;
    }

    private boolean withinCols(int i) {
        return i >= 0 && i < cols;
    }

    private boolean withinRows(int j) {
        return j >= 0 && j < rows;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ENTER -> grid = new int[cols][rows];
            }

        }
    }
}
