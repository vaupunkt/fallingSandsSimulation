import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class HoverDetector extends JLabel {
    public  static int mouseX;
    public static int mouseY;

    public HoverDetector() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Mouse entered the component
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Mouse exited the component
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                // Update your application with the mouse coordinates
            }
        });
    }
}