package model;

import controller.ClickController;
import view.ChessboardPoint;
import view.Images;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class QueenChessComponent extends  ChessComponent{
    private static Image QUEEN_WHITE;
    private static Image QUEEN_BLACK;
    private Image queenImage;

    private char chessName ='Q';
    public void loadResource() throws IOException {
        if (QUEEN_WHITE == null) {
            QUEEN_WHITE = ImageIO.read(Images.white_q1);
        }

        if (QUEEN_BLACK == null) {
            QUEEN_BLACK = ImageIO.read(Images.black_q1);
        }
    }
    private void initiateQueenImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                queenImage = QUEEN_WHITE;
            } else if (color == ChessColor.BLACK) {
                queenImage = QUEEN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public QueenChessComponent(ChessboardPoint source, Point location, ChessColor color, ClickController listener, int size) {
        super(source, location, color,listener, size);
        initiateQueenImage(color);
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

        for(int a=0 ; a<7-source.getX(); a++){//only change x, move down
            ChessboardPoint afterMove = source.offset(a+1,0);
            if(chessComponents[afterMove.getX()][afterMove.getY()].getChessColor().equals(chessColor)){
                break;//can't eat teammate
            }
            CanMovePoint.add(afterMove);
            if(chessComponents[afterMove.getX()][afterMove.getY()].getChessName()!='_'){
                break;//eat,reach and stop
            }
        }
        for(int b=0 ; b<source.getX() ; b++){//only change x, move up
            ChessboardPoint afterMove = source.offset(-b-1,0);
            if(chessComponents[afterMove.getX()][afterMove.getY()].getChessColor().equals(chessColor)){
                break;
            }
            CanMovePoint.add(afterMove);
            if(chessComponents[afterMove.getX()][afterMove.getY()].getChessName()!='_'){
                break;
            }
        }
        for(int c=0; c<7-source.getY() ; c++){//only change y,move right
            ChessboardPoint afterMove = source.offset(0,c+1);
            if(chessComponents[afterMove.getX()][afterMove.getY()].getChessColor().equals(chessColor)){
                break;
            }
            CanMovePoint.add(afterMove);
            if(chessComponents[afterMove.getX()][afterMove.getY()].getChessName()!='_'){
                break;
            }
        }
        for(int d=0;d<source.getY();d++){//only change y, move left
            ChessboardPoint afterMove = source.offset(0,-d-1);
            if(chessComponents[afterMove.getX()][afterMove.getY()].getChessColor().equals(chessColor)){
                break;
            }
            CanMovePoint.add(afterMove);
            if(chessComponents[afterMove.getX()][afterMove.getY()].getChessName()!='_'){
                break;
            }
        }
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
        g.drawImage(queenImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }

    @Override
    public char getChessName(){
        return 'Q';
    }

}
