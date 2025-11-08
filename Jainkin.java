
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Jainkin extends JPanel implements MouseListener {

    // Store click points
    private final List<Point> points = new ArrayList<>();

    public Jainkin() {
        addMouseListener(this);
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        // Draw all stored points
        for (Point p : points) {
            g.fillOval(p.x - 2, p.y - 2, 5, 5);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        points.add(e.getPoint());
        repaint(); // Repaint to show the new point
    }

    // Unused MouseListener methods
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Click Drawer");
        Jainkin panel = new Jainkin();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Infinite loop (to satisfy your "infinite loop" request)
        // In practice, Swing event loop already handles GUI updates,
        // but here's a harmless example that keeps the program alive.
        while (true) {
            try {
                Thread.sleep(100); // prevent CPU overuse
            } catch (InterruptedException ignored) {}
        }
    }
}
