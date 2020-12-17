package com.company;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.filechooser.FileSystemView;

public class Window extends JFrame{
    private JButton open;
    private JButton save;
    private JButton close;
    public JScrollPane pane;
    private Panel panel;
    private JRadioButton red;
    private JRadioButton green;
    private JRadioButton blue;
    private JPanel southPanel;
    private Color color;
    private JFileChooser fileChooser;
    private File file;
    private int xCoordOld;
    private int yCoordOld;
    private int xCoordNew;
    private int yCoordNew;

    Window(){
        super("Paint");
        open = new JButton("Open");
        save = new JButton("Save");


        panel = new Panel();
        pane = new JScrollPane(panel);
        int width = 600;
        int height = 400;
       pane.setPreferredSize(new Dimension(width, height));

        red = new JRadioButton("Red");
        green = new JRadioButton("Green");
        blue = new JRadioButton("Blue");
        ButtonGroup colorGroup = new ButtonGroup();
        color = Color.RED;
        colorGroup.add(red);
        colorGroup.add(green);
        colorGroup.add(blue);
        red.setSelected(true);
        southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(1, 3));
        southPanel.add(red);
        southPanel.add(green);
        southPanel.add(blue);
        southPanel.add(save);
        southPanel.add(open);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pane, BorderLayout.CENTER);
        getContentPane().add(southPanel, BorderLayout.SOUTH);

        fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        panel.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){

                xCoordOld = e.getX();
                yCoordOld = e.getY();
            }
        });
        panel.addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseDragged(MouseEvent e){
                xCoordNew = e.getX();
                yCoordNew = e.getY();

                Graphics g = panel.getGraphics();
                Graphics buf = panel.getBuffer().getGraphics();
                chooseColor();
                buf.setColor(color);
                g.setColor(color);

                buf.drawLine(xCoordOld, yCoordOld, xCoordNew, yCoordNew);
                g.drawLine(xCoordOld, yCoordOld, xCoordNew, yCoordNew);

                xCoordOld = xCoordNew;
                yCoordOld = yCoordNew;
            }
        });
        panel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                Graphics g = panel.getGraphics();
                Graphics buf = panel.getBuffer().getGraphics();
                chooseColor();
                buf.setColor(color);
                g.setColor(color);

                g.fillRect(e.getX(), e.getY(), 1, 1);
                buf.fillRect(e.getX(), e.getY(), 1, 1);
            }
        });

        open.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                fileChooser.setDialogTitle("Choose image: ");
                int returnValue = fileChooser.showOpenDialog(Window.this);
                if (returnValue == JFileChooser.APPROVE_OPTION){
                    file = fileChooser.getSelectedFile();
                    try{
                        BufferedImage buf = ImageIO.read(file);
                        panel.loadImage(buf);
                    } catch (IOException ex){
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });

        save.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                fileChooser.setDialogTitle("Save image: ");
                int ret = fileChooser.showSaveDialog(null);
                file = fileChooser.getSelectedFile();
                if (ret == fileChooser.APPROVE_OPTION){
                    try{
                        ImageIO.write(panel.getBuffer(), "png", file);

                    } catch (IOException ex){
                        ex.getMessage();
                    }
                }
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
       addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                dispose();
            }
        });
      }



    private void chooseColor(){
        if (red.isSelected()){
            color = Color.RED;
        } else if (green.isSelected()){
            color = Color.GREEN;
        } else if (blue.isSelected()){
            color = Color.BLUE;
        }
    }

}
