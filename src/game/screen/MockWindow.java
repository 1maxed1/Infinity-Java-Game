package src.game.screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.math.BigInteger;

public class MockWindow{
    //Returns the dimensions of the current monitor and the realHeight of the window created
    private final int mockHeight = 200;
    private final int mockWidth = 600;

    public double dimensionX;

    public double dimensionY;

    public JFrame frame;

    public float realHeight;
    public float realWidth;

    public int difference;

    public float percentage;

    public MockWindow(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        dimensionY = screenSize.getHeight();
        dimensionX = screenSize.getWidth();
        System.out.println("Resolution height: " +dimensionY);
        System.out.println("Resolution width: " + dimensionX);


       frame = new JFrame("Mock");

       //Creates a frame with the exact same measures as my game
       frame.setPreferredSize(new Dimension(mockWidth, mockHeight));
       frame.setLayout(new BorderLayout());

       frame.pack();

       //Makes this jFrame invisible
       frame.setVisible(false);

       realHeight = calculateRealHeight();
       realWidth = calculateRealWidth();



    }

    public void close(){
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    private float calculateRealHeight(){
       float rH= frame.getContentPane().getHeight();
       System.out.println("RH: " +rH);
       return rH;
    }
    private float calculateRealWidth(){
        float rW= frame.getContentPane().getWidth();
        System.out.println("RW: " +rW);
        return rW;
    }

    public float getDifference(int base){
        //(base/realHeight)
        return (float) Math.abs(realHeight-base);
    }
    public float getPercentage(int base){
        //(base/realHeight)
        return (float) Math.round(base/realHeight*100)/100;
    }





}
