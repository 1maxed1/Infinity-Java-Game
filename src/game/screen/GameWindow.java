package src.game.screen;

import javax.swing.JFrame;
import java.awt.*;


public class GameWindow extends JFrame {
    public static final int SCREEN_WIDTH = 600;

    //Should be responsive to the devices window -->
    //Screenheight +
    public static  final int ORIGIN_SCREEN_HEIGHT = 200;

    private final GameScreen gameScreen;

    public static float realHeight;

    public static float difference;

    public static int windowHeight;

    public static int windowWidth;

    public static float realWidth;


    public GameWindow() {
        super("Infinity and Beyond");


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MockWindow mW = new MockWindow();

        realHeight = mW.realHeight;
        realWidth = mW.realWidth;

        //161 works on my Screen
        difference = mW.getDifference(161);
        System.out.println("Percentage: "+mW.getPercentage(161));

        System.out.println(mW.realHeight);

        windowHeight = (int) (ORIGIN_SCREEN_HEIGHT+(difference));

        windowWidth = (int)(SCREEN_WIDTH+(difference));

        setPreferredSize(new Dimension(SCREEN_WIDTH, windowHeight));

        setLayout(new BorderLayout());

        gameScreen = new GameScreen();
        //Adds a keyListener on the game screen where the entire logic is processed
        addKeyListener(gameScreen);
        add(gameScreen, BorderLayout.CENTER);


        pack();


        setResizable(true);

        // Center the window on the screen
        setLocationRelativeTo(null);

        mW.close();

    }
    public void startGame() {
        setVisible(true);
        gameScreen.startGame();
    }
}
