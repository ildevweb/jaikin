import java.awt.event.*;
import java.util.HashSet;

public class Input implements KeyListener, MouseListener, MouseMotionListener {
    private static final HashSet<Integer> keysPressed = new HashSet<>();
    private static int mouseX = 0, mouseY = 0;
    private static boolean mousePressed = false;

    public static void update() {
        // You can add per-frame updates here if needed
    }

    public static boolean isKeyPressed(int keyCode) {
        return keysPressed.contains(keyCode);
    }

    public static int getMouseX() { return mouseX; }
    public static int getMouseY() { return mouseY; }
    public static boolean isMousePressed() { return mousePressed; }

    @Override
    public void keyPressed(KeyEvent e) { keysPressed.add(e.getKeyCode()); }
    @Override
    public void keyReleased(KeyEvent e) { keysPressed.remove(e.getKeyCode()); }
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) { mousePressed = true; }
    @Override
    public void mouseReleased(MouseEvent e) { mousePressed = false; }
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) { mouseX = e.getX(); mouseY = e.getY(); }
    @Override
    public void mouseMoved(MouseEvent e) { mouseX = e.getX(); mouseY = e.getY(); }
}
