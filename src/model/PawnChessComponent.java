package model;

import controller.ClickController;
import view.ChessboardPoint;
import view.Images;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PawnChessComponent extends ChessComponent{
    private static Image PAWN_WHITE;
    private static Image PAWN_BLACK;
    private Image pawnImage;

    private char chessName ='P';

    private int PawnDevelop = 0;
    public void loadResource() throws IOException {
        if (PAWN_WHITE == null) {
            PAWN_WHITE = ImageIO.read(Images.white_p1);
        }

        if (PAWN_BLACK == null) {
            PAWN_BLACK = ImageIO.read(Images.black_p1);
        }
    }
    private void initiateRookImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = PAWN_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = PAWN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public PawnChessComponent(ChessboardPoint source, Point location, ChessColor color, ClickController listener, int size) {
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
        if(chessColor.equals(ChessColor.BLACK)){
            if(source.getX()==1){//在开始位置
                CanMovePoint.add(canMoveToHelp(chessComponents,1,0));
                CanMovePoint.add(canMoveToHelp(chessComponents,2,0));
                CanMovePoint.add(eatCanMoveTo(chessComponents,1,-1));
                CanMovePoint.add(eatCanMoveTo(chessComponents,1,1));

            }else{

                //以下为黑棋吃过路兵
                if(eatCanMoveTo(chessComponents,0,-1)!=null && history.size()!=0 &&
                        history.get(history.size()-1).equals("WP6"+String.valueOf(source.getY()-1)+"-"+"N_4"+String.valueOf(source.getY()-1))     ){
                    //左边有白且该白棋刚走两步
                    CanMovePoint.add(canMoveToHelp(chessComponents,1,-1));//吃过路兵，怎么把原来的棋子吃掉
                }else if(eatCanMoveTo(chessComponents,0,1)!=null && history.size()!=0 &&
                        history.get(history.size()-1).equals("WP6"+String.valueOf(source.getY()+1)+"-"+"N_4"+String.valueOf(source.getY()+1))   ){
                    //右边有白且该白棋刚走两步
                    CanMovePoint.add(canMoveToHelp(chessComponents,1,1));
                }
                //吃过路兵黑完


                CanMovePoint.add(canMoveToHelp(chessComponents,1,0));
                CanMovePoint.add(eatCanMoveTo(chessComponents,1,-1));
                CanMovePoint.add(eatCanMoveTo(chessComponents,1,1));
            }
        }else{
            if(source.getX()==6){
                CanMovePoint.add(canMoveToHelp(chessComponents,-1,0));
                CanMovePoint.add(canMoveToHelp(chessComponents,-2,0));
                CanMovePoint.add(eatCanMoveTo(chessComponents,-1,-1));
                CanMovePoint.add(eatCanMoveTo(chessComponents,-1,1));
            }else{
                //以下为白棋吃过路兵
                if(eatCanMoveTo(chessComponents,0,-1)!=null &&   history.size()!=0 &&
                        history.get(history.size()-1).equals("BP1"+String.valueOf(source.getY()-1)+"-"+"N_3"+String.valueOf(source.getY()-1))     ){
                    //左边有黑且刚走两步
                    CanMovePoint.add(canMoveToHelp(chessComponents,-1,-1));//吃过路兵，怎么把原来的棋子吃掉
                }else if(eatCanMoveTo(chessComponents,0,1)!=null && history.size()!=0 &&
                        history.get(history.size()-1).equals("BP1"+String.valueOf(source.getY()+1)+"-"+"N_3"+String.valueOf(source.getY()+1))   ){
                    //右边有黑且刚走两步
                    CanMovePoint.add(canMoveToHelp(chessComponents,-1,1));
                }
                //吃过路兵白完

                CanMovePoint.add(canMoveToHelp(chessComponents,-1,0));
                CanMovePoint.add(eatCanMoveTo(chessComponents,-1,-1));
                CanMovePoint.add(eatCanMoveTo(chessComponents,-1,1));
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
    public ChessboardPoint canMoveToHelp(ChessComponent[][] chessComponents,int dx,int dy){
        ChessboardPoint afterMove = source.offset(dx, dy);
        if(afterMove!=null && chessComponents[afterMove.getX()][afterMove.getY()].getChessColor().equals(ChessColor.NONE)){
            return afterMove;//still in board and can't eat so just to empty boardpoint
        }else{
            return null;
        }
    }
    public ChessboardPoint eatCanMoveTo(ChessComponent[][] chessComponents,int dx,int dy){
        ChessboardPoint afterMove = source.offset(dx, dy);
        if(afterMove!=null && chessColor.equals(ChessColor.BLACK) && chessComponents[afterMove.getX()][afterMove.getY()].getChessColor().equals(ChessColor.WHITE)){
            return afterMove;
        }
        //if(afterMove!=null && chessColor.equals(ChessColor.BLACK) && BlackOrWriteOrNone(chessComponents[afterMove.getX()][afterMove.getY()].getChessName()).equals(ChessColor.WHITE)){return afterMove;}
        else if(afterMove!=null && chessColor.equals(ChessColor.WHITE) && chessComponents[afterMove.getX()][afterMove.getY()].getChessColor().equals(ChessColor.BLACK)){
            return afterMove;
        }
        return null;


        /*if(afterMove!=null && !BlackOrWriteOrNone(chessComponents[afterMove.getX()][afterMove.getY()].getChessName()).equals(ChessColor.NONE)
                && !BlackOrWriteOrNone(chessComponents[afterMove.getX()][afterMove.getY()].getChessName()).equals(chessColor)){
            return afterMove;//still in board and eat its opposite(not none and not its teammate)
        }else{
            return null;
        }*/
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(pawnImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }

    }
    @Override
    public char getChessName(){
        return 'P';
    }

}
