package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Panel extends JPanel{
    private BufferedImage buffer;
    Panel(){
        int width = 1000;
        int height = 800;
        setPreferredSize(new Dimension(width, height));
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    protected void paintComponent(Graphics g){

        g.drawImage(buffer, 0, 0, null);
    }

    public BufferedImage getBuffer(){
        return buffer;
    }

    public void loadImage(BufferedImage buf){
        buffer.createGraphics().setColor(Color.WHITE);
        buffer.createGraphics().fillRect(0, 0, getWidth(), getHeight());
        buffer.createGraphics().drawImage(buf,0,0,null);
        repaint();
    }

}
