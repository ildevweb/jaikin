import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import tools.*;
import java.awt.geom.Line2D;

public class Jaikin extends JPanel implements MouseListener, KeyListener {
    List<Circle> circles = new ArrayList<>();
    List<Circle> originalCircles = new ArrayList<>();
    List<Circle> ogCircles = new ArrayList<>();
    List<Line2D.Float> lines = new ArrayList<>();

    boolean stepMode = false;
    boolean started = false;
    int currentStep = 0;
    final int maxSteps = 7;

    long lastStepTime = 0;

    public Jaikin() {
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocusInWindow();
        addMouseListener(this);
        addKeyListener(this);

        // Timer to mimic game loop (~60 FPS)
        Timer timer = new Timer(16, e -> update());
        timer.start();
    }

    void update() {
        // Step through Chaikin algorithm every 600ms
        if (stepMode) {
            long now = System.currentTimeMillis();
            if (now - lastStepTime >= 600) {
                if (currentStep == 0) {
                    circles = cloneList(originalCircles);
                    buildLines(circles);
                } else {
                    circles = chaikinStep(circles);
                    buildLines(circles);
                }
                currentStep++;
                if (currentStep > maxSteps) currentStep = 0;
                lastStepTime = now;
            }
        }
        repaint();
    }

    List<Circle> cloneList(List<Circle> list) {
        List<Circle> copy = new ArrayList<>();
        for (Circle c : list) copy.add(c.cloneCircle());
        return copy;
    }

    void buildLines(List<Circle> circles) {
        lines.clear();
        for (int i = 0; i < circles.size() - 1; i++) {
            Circle a = circles.get(i);
            Circle b = circles.get(i + 1);
            lines.add(new Line2D.Float(a.x, a.y, b.x, b.y));
        }
    }

    List<Circle> chaikinStep(List<Circle> circles) {
        List<Circle> newCircles = new ArrayList<>();

        for (int i = 0; i < circles.size(); i++) {
            Circle c = circles.get(i);

            // Always keep last point
            if (i == circles.size() - 1) {
                newCircles.add(c.cloneCircle());
                break;
            }

            // Calculate new points between c and next
            Circle q = Circle.interpolate(c, circles.get(i + 1), 0.25f);
            Circle r = Circle.interpolate(c, circles.get(i + 1), 0.75f);

            // Keep first point
            if (i == 0) {
                newCircles.add(c.cloneCircle());
            }

            newCircles.add(q);
            newCircles.add(r);
        }

        return newCircles;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Draw lines
        g2.setColor(Color.WHITE);
        for (Line2D.Float line : lines) {
            g2.draw(line);
        }

        // Draw original circles
        g2.setColor(Color.WHITE);
        for (Circle c : ogCircles) {
            g2.fillOval((int) (c.x - 2), (int) (c.y - 2), 4, 4);
        }

        // Draw current circles
        g2.setColor(Color.RED);
        for (Circle c : circles) {
            g2.fillOval((int) (c.x - c.r / 2), (int) (c.y - c.r / 2), (int) c.r, (int) c.r);
        }
    }

    // MouseListener
    @Override
    public void mouseClicked(MouseEvent e) {
        if (!started) {
            circles.add(new Circle(e.getX(), e.getY(), 4f));
        }
    }
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    // KeyListener
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (!started && code == KeyEvent.VK_ENTER && circles.size() > 2) {
            ogCircles = cloneList(circles);
            originalCircles = cloneList(circles);
            buildLines(circles);
            stepMode = true;
            started = true;
            currentStep = 0;
            lastStepTime = System.currentTimeMillis();
        } else if (!started && code == KeyEvent.VK_ENTER && circles.size() == 2) {
            buildLines(circles);
        } else if (code == KeyEvent.VK_SPACE) {
            stepMode = false;
            started = false;
            currentStep = 0;
            circles.clear();
            ogCircles.clear();
            originalCircles.clear();
            lines.clear();
        } else if (code == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }
    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Jaikin");
        Jaikin panel = new Jaikin();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
