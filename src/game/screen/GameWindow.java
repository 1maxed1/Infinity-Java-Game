package game.screen;
import javax.swing.JFrame;
import java.awt.*;

/**
 *File: GameWindow.java
 *@version : 1.0
 *@author  1maxed1 (Max)
 * The GameWindow class represents the game window and sets up the game screen.
 */
public class GameWindow extends JFrame {
    public static final int SCREEN_WIDTH = 600;
    public static final int ORIGIN_SCREEN_HEIGHT = 200;

    private final GameScreen gameScreen;

    public static float realHeight;
    public static float difference;
    public static int windowHeight;
    public static int windowWidth;
    public static float realWidth;

    /**
     * Constructs a GameWindow object.
     */
    public GameWindow() {
        super("Infinity and Beyond");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a MockWindow to get the screen dimensions
        MockWindow mW = new MockWindow();
        realHeight = mW.realHeight;
        realWidth = mW.realWidth;

        difference = mW.getDifference(161);

        windowHeight = (int) (ORIGIN_SCREEN_HEIGHT + difference);
        windowWidth = (int) (SCREEN_WIDTH + difference);

        setPreferredSize(new Dimension(SCREEN_WIDTH, windowHeight));
        setLayout(new BorderLayout());

        gameScreen = new GameScreen();
        addKeyListener(gameScreen);
        add(gameScreen, BorderLayout.CENTER);

        pack();
        setResizable(true);
        setLocationRelativeTo(null);

        // Close the MockWindow
        mW.close();
    }

    /**
     * Starts the game by making the window visible and starting the game screen.
     */
    public void startGame() {
        setVisible(true);
        gameScreen.startGame();
    }
}
