
import javax.swing.JFrame;

public class GameFrame extends JFrame {
    GameFrame() {
        GamePanel GamePanel = new GamePanel();
        this.add(GamePanel);
        this.setTitle("Falling Sand");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
