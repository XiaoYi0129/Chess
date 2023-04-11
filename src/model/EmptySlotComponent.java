//path
package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类表示棋盘上的空位置
 */
public class EmptySlotComponent extends ChessComponent {

    private char chessName ='_';

    protected ChessColor chessColor=ChessColor.NONE;

    private static Image emptyImage;
    public EmptySlotComponent(ChessboardPoint source, Point location, ClickController listener, int size) {
        super(source, location, ChessColor.NONE,listener, size);
       // initiateEmptyImage();
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination,ArrayList<String> history) {
        return false;
    }

    @Override
    public void loadResource() throws IOException {
        //No resource!
    }
    @Override
    public List<ChessboardPoint> canMoveToList(ChessComponent[][] chessComponents,ArrayList<String> history){
        return new ArrayList<>();
    }
   // private void initiateEmptyImage(){
//        try{
//            if (emptyImage == null) {
//                emptyImage= ImageIO.read(new File("/Users/xiaoyi/Desktop/计算机/project/ChessDemo/images/empty.png"));
//            }
//
//        } catch (IOException e){
//            e.printStackTrace();
//        }

   // }

    //用于显示可以到的地方
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(emptyImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }

    @Override
    public char getChessName(){
        return '_';
    }
}
