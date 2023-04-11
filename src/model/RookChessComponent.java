package model;

import controller.ClickController;
import view.ChessboardPoint;
import view.Images;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类表示国际象棋里面的车，横直
 */
public class RookChessComponent extends ChessComponent {
    /**
     * 黑车和白车的图片，static使得其可以被所有车对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image ROOK_WHITE;
    private static Image ROOK_BLACK;

    /**
     * 车棋子对象自身的图片，是上面两种中的一种
     */
    private Image rookImage;

    private char chessName ='R';

    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (ROOK_WHITE == null) {
            ROOK_WHITE = ImageIO.read(Images.white_r1);//"/Users/xiaoyi/Desktop/计算机/project/ChessDemo/images/rook-white.png"));
        }

        if (ROOK_BLACK == null) {
            ROOK_BLACK = ImageIO.read(Images.black_r1);
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定rookImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateRookImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                rookImage = ROOK_WHITE;
            } else if (color == ChessColor.BLACK) {
                rookImage = ROOK_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RookChessComponent(ChessboardPoint source, Point location, ChessColor color, ClickController listener, int size) {
        super(source, location, color,listener, size);
        initiateRookImage(color);
    }

   /* /**
     * 车棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置，如(0, 0), (0, 7)等等
     * @return 车棋子移动的合法性
     */

   // @Override
    /*public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (source.getX() == destination.getX()) {
            int row = source.getX();
            for (int col = Math.min(source.getY(), destination.getY()) + 1;
                 col < Math.max(source.getY(), destination.getY()); col++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else if (source.getY() == destination.getY()) {
            int col = source.getY();
            for (int row = Math.min(source.getX(), destination.getX()) + 1;
                 row < Math.max(source.getX(), destination.getX()); row++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else { // Not on the same row or the same column.
            return false;
        }
        return true;
    }*/
   @Override
   public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination,ArrayList<String> history) {
       List<ChessboardPoint> destinationList = new ArrayList<ChessboardPoint>();
       destinationList=canMoveToList(chessComponents,history);
       System.out.println(destinationList);//测试cammovetolist对不对
       String destinationPoint =  "("+destination.getX() + ","+destination.getY()+")" ;//+ "on the chessboard is clicked!";
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
    /**
     * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
     *
     * @param g 可以类比于画笔
     */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(rookImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
    @Override
    public char getChessName(){
        return 'R';
    }

}
