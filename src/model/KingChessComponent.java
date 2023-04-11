package model;

import controller.ClickController;
import view.ChessboardPoint;
import view.Images;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class KingChessComponent extends ChessComponent{
    private static Image KING_WHITE;
    private static Image KING_BLACK;
    private Image kingImage;

    private char chessName ='K';
    public void loadResource() throws IOException {
        if (KING_WHITE == null) {
           KING_WHITE = ImageIO.read(Images.white_k1);
        }

        if (KING_BLACK == null) {
            KING_BLACK = ImageIO.read(Images.black_k1);
        }
    }
    private void initiateKingImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                kingImage = KING_WHITE;
            } else if (color == ChessColor.BLACK) {
                kingImage = KING_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public KingChessComponent(ChessboardPoint source, Point location, ChessColor color, ClickController listener, int size) {
        super(source, location, color, listener, size);
        initiateKingImage(color);
    }
    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination,ArrayList<String> history) {
        List<ChessboardPoint> destinationList = new ArrayList<ChessboardPoint>();
        destinationList=canMoveToList(chessComponents,history);
        System.out.println(destinationList);//测试cammovetolist对不对
        String destinationPoint = "("+destination.getX() + ","+destination.getY()+")" ;
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
        CanMovePoint.add(canMoveToHelp(chessComponents,-1,-1));
        CanMovePoint.add(canMoveToHelp(chessComponents,-1,0));
        CanMovePoint.add(canMoveToHelp(chessComponents,-1,1));
        CanMovePoint.add(canMoveToHelp(chessComponents,0,-1));
        CanMovePoint.add(canMoveToHelp(chessComponents,0,1));
        CanMovePoint.add(canMoveToHelp(chessComponents,1,-1));
        CanMovePoint.add(canMoveToHelp(chessComponents,1,0));
        CanMovePoint.add(canMoveToHelp(chessComponents,1,1));

//以下为王车易位
        //以下为黑王易位
        if(chessColor.equals(ChessColor.BLACK) && ifMove(history)==false && ifBeenAttack(chessComponents,history,ChessColor.WHITE)==false){
            //王没被移动过且没有正在被将军
            //以下为短易位
            if(chessComponents[0][7] instanceof RookChessComponent//短易位处有车
                    && chessComponents[0][7].ifMove(history)==false//车没被移动过
                    && chessComponents[0][5] instanceof EmptySlotComponent
                    && chessComponents[0][6] instanceof EmptySlotComponent//中间没有棋子挡着
                    && chessComponents[0][5].ifBeenAttack(chessComponents,history,ChessColor.WHITE)==false
                    && chessComponents[0][6].ifBeenAttack(chessComponents,history,ChessColor.WHITE)==false//王过的两个格子没被攻击
            ){
                CanMovePoint.add(canMoveToHelp(chessComponents,0,2));//这里是变化，dx与dy，不是xy
            }//黑王短易位完成（黑马还没移动）
            //以下为长易位
            else if(chessComponents[0][0] instanceof RookChessComponent//长易位处有车
                    && chessComponents[0][0].ifMove(history)==false//车没被移动过
                    && chessComponents[0][1] instanceof EmptySlotComponent
                    && chessComponents[0][2] instanceof EmptySlotComponent//中间没有棋子挡着
                    && chessComponents[0][3] instanceof EmptySlotComponent
                    && chessComponents[0][3].ifBeenAttack(chessComponents,history,ChessColor.WHITE)==false
                    && chessComponents[0][2].ifBeenAttack(chessComponents,history,ChessColor.WHITE)==false){//王过的两个格子没被攻击
                CanMovePoint.add(canMoveToHelp(chessComponents,0,-2));
            }//黑王长易位完成（黑马还没移动）
        }
        //以下为白王易位
        else if(chessColor.equals(ChessColor.WHITE) && ifMove(history)==false && ifBeenAttack(chessComponents,history,ChessColor.BLACK)==false){
            //王没被移动过且没有正在被将军
            //以下为短易位
            //System.out.print("giao1");//测试,这里没过
            if(chessComponents[7][7] instanceof RookChessComponent//短易位处有车
                    && chessComponents[7][7].ifMove(history)==false//车没被移动过
                    && chessComponents[7][5] instanceof EmptySlotComponent
                    && chessComponents[7][6] instanceof EmptySlotComponent//中间没有棋子挡着
                    && chessComponents[7][5].ifBeenAttack(chessComponents,history,ChessColor.BLACK)==false
                    && chessComponents[7][6].ifBeenAttack(chessComponents,history,ChessColor.BLACK)==false//王过的两个格子没被攻击
            ){
                CanMovePoint.add(canMoveToHelp(chessComponents,0,2));
            }//白王短易位完成（白马还没移动）
            //以下为长易位
            else if(chessComponents[7][0] instanceof RookChessComponent//长易位处有车
                    && chessComponents[7][0].ifMove(history)==false//车没被移动过
                    && chessComponents[7][1] instanceof EmptySlotComponent
                    && chessComponents[7][2] instanceof EmptySlotComponent//中间没有棋子挡着
                    && chessComponents[7][3] instanceof EmptySlotComponent
                    && chessComponents[7][3].ifBeenAttack(chessComponents,history,ChessColor.BLACK)==false
                    && chessComponents[7][2].ifBeenAttack(chessComponents,history,ChessColor.BLACK)==false){//王过的两个格子没被攻击
                CanMovePoint.add(canMoveToHelp(chessComponents,0,-2));
            }//黑王长易位完成（黑马还没移动）
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
//        g.drawImage(kingImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(kingImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }

    @Override
    public char getChessName(){
        return 'K';
    }
}
