import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
    private JFrame frame;
    private boolean running = false;
    private final int width, height;
    private Thread thread;

    public Game(String title, int width, int height) {
        this.width = width;
        this.height = height;

        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);

        this.addKeyListener(new Input());
        this.addMouseListener(new Input());
        this.addMouseMotionListener(new Input());
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this, "Game Thread");
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try { thread.join(); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    @Override
    public void run() {
        while (running) {
            update();
            render();
            try { Thread.sleep(16); } catch (InterruptedException e) {} // ~60 FPS
        }
    }

    private void update() {
        Input.update(); // Update keyboard/mouse state
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        // Example shapes
        Shapes.drawRect(g, 100, 100, 200, 150, Color.RED);
        Shapes.drawCircle(g, 400, 300, 50, Color.GREEN);

        g.dispose();
        bs.show();
    }
}
