package model;

import controller.ClickController;
import view.ChessboardPoint;
import view.Images;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BishopChessComponent extends ChessComponent{//象
    private static Image BISHOP_WHITE;
    private static Image BISHOP_BLACK;
    private Image bishopImage;

    private char chessName ='B';
    public void loadResource() throws IOException {
        if (BISHOP_WHITE == null) {
            BISHOP_WHITE = ImageIO.read(Images.white_b1);
        }

        if (BISHOP_BLACK == null) {
            BISHOP_BLACK = ImageIO.read(Images.black_b1);
        }

    }
    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                bishopImage = BISHOP_WHITE;
            } else if (color == ChessColor.BLACK) {
                bishopImage = BISHOP_BLACK;}
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);

        initiateBishopImage(color);
    }
    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination,ArrayList<String> history) {
        //判断是否能移动
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

        for(int e=0 ; e<Math.min(7-source.getX(),7-source.getY()); e++){//slope ,right down
            ChessboardPoint afterMove = source.offset(e+1,e+1);
            if(chessComponents[afterMove.getX()][afterMove.getY()].getChessColor().equals(chessColor)){
                break;
            }
            CanMovePoint.add(afterMove);
            if(chessComponents[afterMove.getX()][afterMove.getY()].getChessName()!='_'){
                break;
            }
        }
        for(int j=0 ; j<Math.min(7-source.getX(),source.getY());j++){//slope,left down
            ChessboardPoint afterMove = source.offset(j+1,-j-1);
            if(chessComponents[afterMove.getX()][afterMove.getY()].getChessColor().equals(chessColor)){
                break;
            }
            CanMovePoint.add(afterMove);
            if(chessComponents[afterMove.getX()][afterMove.getY()].getChessName()!='_'){
                break;
            }
        }

        for(int f=0 ; f<Math.min(source.getX(),source.getY());f++){//slope, left up
            ChessboardPoint afterMove = source.offset(-f-1,-f-1);
            if(chessComponents[afterMove.getX()][afterMove.getY()].getChessColor().equals(chessColor)){
                break;
            }
            CanMovePoint.add(afterMove);
            if(chessComponents[afterMove.getX()][afterMove.getY()].getChessName()!='_'){
                break;
            }
        }
        for(int i=0 ; i<Math.min(source.getX(),7-source.getY());i++){//slop,right up
            ChessboardPoint afterMove = source.offset(-i-1,i+1);
            if(chessComponents[afterMove.getX()][afterMove.getY()].getChessColor().equals(chessColor)){
                break;
            }
            CanMovePoint.add(afterMove);
            if(chessComponents[afterMove.getX()][afterMove.getY()].getChessName()!='_'){
                break;
            }
        }

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
        g.drawImage(bishopImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) {// Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
            
        }

    }
    @Override
    public char getChessName(){
        return 'B';
    }

}
