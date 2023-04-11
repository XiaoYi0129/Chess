package model;

import controller.ClickController;
import view.ChessboardPoint;
import view.Images;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KnightChessComponent extends ChessComponent{
    private static Image KNIGHT_WHITE;
    private static Image KNIGHT_BLACK;
    private Image knightImage;

    private char chessName ='N';
    public void loadResource() throws IOException {
        if (KNIGHT_WHITE == null) {
            KNIGHT_WHITE = ImageIO.read(Images.white_n1);
        }

        if (KNIGHT_BLACK == null) {
            KNIGHT_BLACK = ImageIO.read(Images.black_n1);
        }
    }
    private void initiateRookImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                knightImage = KNIGHT_WHITE;
            } else if (color == ChessColor.BLACK) {
                knightImage = KNIGHT_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public KnightChessComponent(ChessboardPoint source, Point location, ChessColor color,  ClickController listener, int size) {
        super(source, location, color, listener, size);
        initiateRookImage(color);
    }
    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination,ArrayList<String> history) {
        List<ChessboardPoint> destinationList = new ArrayList<ChessboardPoint>();
        destinationList=canMoveToList(chessComponents,history);
        System.out.println(destinationList);//测试cammovetolist对不对
        String destinationPoint =  "("+destination.getX() + ","+destination.getY()+")" ;
        for(int i=0 ; i<destinationList.size() ; i++){
            if(destinationList.get(i).toString().equals(destinationPoint)){
                return true;
            }
        }
        return false;
    }
    @Override
    public List<ChessboardPoint> canMoveToList(ChessComponent[][] chessComponents,ArrayList<String> history){
        List CanMovePoint = new ArrayList<ChessboardPoint>();
        CanMovePoint.add(canMoveToHelp(chessComponents,-2,-1));
        CanMovePoint.add(canMoveToHelp(chessComponents,-2,1));
        CanMovePoint.add(canMoveToHelp(chessComponents,-1,-2));
        CanMovePoint.add(canMoveToHelp(chessComponents,-1,2));
        CanMovePoint.add(canMoveToHelp(chessComponents,1,-2));
        CanMovePoint.add(canMoveToHelp(chessComponents,1,2));
        CanMovePoint.add(canMoveToHelp(chessComponents,2,-1));
        CanMovePoint.add(canMoveToHelp(chessComponents,2,1));
        List noNullCanMovePoint = new ArrayList<ChessboardPoint>();
        for(int j=0 ; j<CanMovePoint.size();j++){
            if(CanMovePoint.get(j)!=null){
                noNullCanMovePoint.add(CanMovePoint.get(j));
            }
        }
        if(noNullCanMovePoint!=null){
            return noNullCanMovePoint;
        }else{
            return new ArrayList<>();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(knightImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
    @Override
    public char getChessName(){
        return 'N';
    }

}
