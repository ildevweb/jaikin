
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chaikin Canvas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CanvasPanel canvas = new CanvasPanel();
        frame.add(canvas);

        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); // center window
        frame.setVisible(true);
    }
}
