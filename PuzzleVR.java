package puzzlevr;

import java.awt.event.*;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JButton;
import java.util.*;
import javax.swing.Icon;

public class PuzzleVR implements ActionListener {

    private JFrame frame;
    private JLabel[] labels;
    private final int rows = 7; 
    private final int cols = 6;
    private final int chunks = rows * cols;
    private JButton puzzlePieces[][] = new JButton[rows][cols];
    private int coord1[] = new int[3];
    private int coord2[] = new int[3];
    private int turn = 0;
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new PuzzleVR().createGUI();
            }
        });
    }

    private void createGUI() {
        frame = new JFrame("Puzzle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        split();
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
       
    }

    private void split() {

        BufferedImage[] imgs = getImages();
        int counter= 0;
        //setting the contentpane layout (size, etc) for grid layout 
        frame.getContentPane().setLayout(new GridLayout(rows, cols));

         for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ImageIcon pic = new ImageIcon(Toolkit.getDefaultToolkit().createImage(imgs[counter].getSource()));
                puzzlePieces[i][j] = new JButton(pic);
                puzzlePieces[i][j].setActionCommand(Integer.toString(i)+Integer.toString(j)+Integer.toString(counter));
                
                counter++;
              }  
               
            }shuffle();
        }
    

    private BufferedImage[] getImages() {
       
        BufferedImage originalImage = null;
        URL url1 = null;
       try
        {
            url1 = new URL("https://kids.nationalgeographic.com/explore/monuments/eiffel-tower/_jcr_content/content/textimage_6.img.jpg/1581608715365.jpg");
        } 
        catch (MalformedURLException e1) 
        {
            e1.printStackTrace();
        }
       try {
        originalImage = ImageIO.read(url1);
} catch (IOException e) {
}
        int chunkWidth = originalImage.getWidth() / cols; // determines the chunk width and height
        int chunkHeight = originalImage.getHeight() / rows;
        int count = 0;
        BufferedImage imgs[] = new BufferedImage[chunks]; //Image array to hold image chunks
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                //Initialize the image array with image chunks
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, originalImage.getType());

                // draws the image chunk
                Graphics2D gr = imgs[count++].createGraphics();
                gr.drawImage(originalImage, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                gr.dispose();
            }
        }
        return imgs;
    }
   

private JButton puzzlePieces2[][];

private ArrayList<JButton> puzzlePieceShuffle = new ArrayList<JButton>();
public void shuffle()
{
 int index=0;
    for ( int i = 0; i < puzzlePieces.length; i++ )
    {
        // tiny change 1: proper dimensions
        for ( int j = 0; j < puzzlePieces[i].length; j++ )
        {
            // tiny change 2: actually store the values
            puzzlePieceShuffle.add( puzzlePieces[i][j] );
        }
    }
    Collections.shuffle( puzzlePieceShuffle );
    // now you need to find a mode in the list.

    puzzlePieces2 = new JButton[rows][cols];
    
    for ( int i = 0; i < rows; i++ )
    {
        for ( int j = 0; j < cols; j++)
        {
            puzzlePieces2[i][j] = puzzlePieceShuffle.get(index);
            frame.getContentPane().add(puzzlePieces2[i][j]);
            puzzlePieces[i][j].addActionListener(this);
            index++;
        }
    }
}

 public void changeImage(int x1, int y1, int t1, int x2, int y2, int t2) {
       
            puzzlePieces2[x1][y1].setActionCommand(Integer.toString(x2)+Integer.toString(y2)+Integer.toString(t2));
            puzzlePieces2[x2][y2].setActionCommand(Integer.toString(x1) +Integer.toString(y1) + Integer.toString(t1));

            Icon temp = puzzlePieces2[x1][y1].getIcon();
            puzzlePieces2[x1][y1].setIcon(puzzlePieces[x2][y2].getIcon());
            puzzlePieces2[x2][y2].setIcon(temp);
        }
    
 
 public void actionPerformed(ActionEvent e) {
        if (turn == 0) {
            coord1 = getXY(e.getActionCommand());
            turn++;
        } else {
            coord2 = getXY(e.getActionCommand());
            changeImage(coord1[0], coord1[1], coord1[2], coord2[0], coord2[1], coord2[2]);
            turn--;
        }
}
 
  public static int[] getXY(String tag) {
        int xy[] = new int[3];

        xy[0] = Integer.parseInt(tag.substring(0, 1));
        xy[1] = Integer.parseInt(tag.substring(1, 2));
        xy[2] = Integer.parseInt(tag.substring(2));
        System.out.println(xy[0]);
        return xy;
    }
}
