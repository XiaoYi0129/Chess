package model;

import controller.ClickController;
import view.Chessboard;
import view.ChessboardPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类是一个抽象类，主要表示8*8棋盘上每个格子的棋子情况，当前有两个子类继承它，分别是EmptySlotComponent(空棋子)和RookChessComponent(车)。
 */
public abstract class ChessComponent extends JComponent {//click从这里开始被调用，
    // 只要有click事件就会自动启动

    /**
     * CHESSGRID_SIZE: 主要用于确定每个棋子在页面中显示的大小。
     * <br>
     * 在这个设计中，每个棋子的图案是用图片画出来的（由于国际象棋那个棋子手动画太难了）
     * <br>
     * 因此每个棋子占用的形状是一个正方形，大小是50*50
     */

//    private static final Dimension CHESSGRID_SIZE = new Dimension(1080 / 4 * 3 / 8, 1080 / 4 * 3 / 8);   ？？？也许是新建了个图像类，数字表示图像的像素
    protected static final Color[] BACKGROUND_COLORS = {Color.WHITE, Color.BLACK};//棋盘图像背景
    /**
     * handle click event
     */
    protected ClickController clickController;//一个抽象成员变量，表示点击棋盘后的操作，子类里面有具体说明

    /**
     * chessboardPoint: 表示8*8棋盘中，当前棋子在棋格对应的位置，如(0, 0), (1, 0), (0, 7),(7, 7)等等
     * <br>
     * chessColor: 表示这个棋子的颜色，有白色，黑色，无色三种
     * <br>
     * selected: 表示这个棋子是否被选中
     */
    protected ChessboardPoint source=new ChessboardPoint(0,0);

    //protected final ChessColor chessColor;
    protected ChessColor chessColor=ChessColor.WHITE;
    protected boolean selected;
    protected char chessName;
    //protected ChessComponent[][] chessComponents;

    protected ChessComponent(ChessboardPoint source, Point location, ChessColor chessColor,ClickController clickController, int size) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);//----------？
        setLocation(location);
        setSize(size, size);
        this.source = source;
        this.chessColor = chessColor;
        this.selected = false;
        this.clickController = clickController;
    }

    public ChessboardPoint getChessboardPoint() {
        return source;
    }

    public void setChessboardPoint(ChessboardPoint chessboardPoint) {
        this.source = chessboardPoint;
    }

    public ChessColor getChessColor() {
        return chessColor;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * @param another 主要用于和另外一个棋子交换位置
     *                <br>
     *                调用时机是在移动棋子的时候，将操控的棋子和对应的空位置棋子(EmptySlotComponent)做交换
     */
    public void swapLocation(ChessComponent another) {//用于和another交换位置，对象是chess1
        ChessboardPoint chessboardPoint1 = getChessboardPoint(), chessboardPoint2 = another.getChessboardPoint();
        Point point1 = getLocation(), point2 = another.getLocation();
        setChessboardPoint(chessboardPoint2);
        setLocation(point2);
        another.setChessboardPoint(chessboardPoint1);
        another.setLocation(point1);
    }

    /**
     * @param e 响应鼠标监听事件
     *          <br>
     *          当接收到鼠标动作的时候，这个方法就会自动被调用，调用所有监听者的onClick方法，处理棋子的选中，移动等等行为。
     */
    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            System.out.printf("Click [%d,%d]\n", source.getX(), source.getY());
            clickController.onClick(this);//这里指的是选中的chesscomponent吗
            //每onClick一下，这个唯一？？？的clickController会被改变一下，history是否可以写到这里
        }
    }

    /**
     * @param chessboard  棋盘
     * @param destination 目标位置，如(0, 0), (0, 7)等等
     * @return this棋子对象的移动规则和当前位置(chessboardPoint)能否到达目标位置
     * <br>
     * 这个方法主要是检查移动的合法性，如果合法就返回true，反之是false
     */

    public abstract  boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination,ArrayList<String> history);
    public abstract List<ChessboardPoint> canMoveToList(ChessComponent[][] chessComponents,ArrayList<String> history);
    //连用，canMoveToList返回可以到的点的集合，再放入canMoveTo里面判断
    public void canMove(Chessboard chessboard, ArrayList<String> history, ChessComponent first, ChessComponent chessComponent,int k){
        Graphics g= chessboard.getGraphics();
        for (int i=0;i<first.canMoveToList(chessboard.getChessComponents(),history).size();i++){
            g.setColor(Color.RED);
            g.drawOval(first.canMoveToList(chessboard.getChessComponents(),history).get(i).getY()*75, first.canMoveToList(chessboard.getChessComponents(),history).get(i).getX()*75,chessComponent.getWidth() , chessComponent.getHeight());
        }
        if (k==0){

        }else if (k==1){
            g.dispose();
        }

    }


    /**
     * 这个方法主要用于加载一些特定资源，如棋子图片等等。
     *
     * @throws IOException 如果一些资源找不到(如棋子图片路径错误)，就会抛出异常
     */
    public abstract void loadResource() throws IOException;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        System.out.printf("repaint chess [%d,%d]\n", source.getX(), source.getY());
//        Color squareColor = BACKGROUND_COLORS[(source.getX() + source.getY()) % 2];
//        g.setColor(squareColor);
//        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
//    public void paintComponents(Graphics g) {
//        super.paintComponents(g);
//        System.out.printf("repaint chess [%d,%d]\n", source.getX(), source.getY());
////        Color squareColor = BACKGROUND_COLORS[(source.getX() + source.getY()) % 2];
////        g.setColor(squareColor);
////        g.fillRect(0, 0, this.getWidth(), this.getHeight());
//    }
    /*public ChessColor BlackOrWriteOrNone(char chess){
        if(chess=='R' || chess=='N' || chess=='B' || chess=='Q' || chess=='K' || chess=='P'){
            return ChessColor.BLACK;
        }else if(chess=='_'){
            return ChessColor.NONE;
        }
        return ChessColor.WHITE;
    }*/
    public ChessboardPoint canMoveToHelp(ChessComponent[][] chessComponents,int dx,int dy){
        ChessboardPoint afterMove = source.offset(dx, dy);
        if(afterMove!=null && !chessComponents[afterMove.getX()][afterMove.getY()].getChessColor().equals(chessColor)){
            return afterMove;//still in board and can't eat teammate
        }
        return null;//和作业不同，作业表示黑白用大小写，这里不是

    }
    public char getChessName(){
        return chessName;
    }
    public void setSource(int x , int y){
        ChessboardPoint middle = new ChessboardPoint(x,y);
        source=middle;
    }

   public ChessboardPoint getSource(){
        return source;
   }

    public boolean ifMove(ArrayList<String> history){
        String move = getColorName() + getChessName()  + String.valueOf(source.getX() ) + String.valueOf(source.getY());
        //System.out.println(history.size());//test
        //int counter=0;//test
        if(history!=null){
            for(int i=0;i<history.size();i++){
                //counter+=1;//test
                //System.out.println(counter);//test
                //System.out.println(history.get(i));//test
                if(history.get(i).substring(0,4).equals(move)){
                    return true;//移动过
                }
            }
        }
        //System.out.println("giao2");//测试，发现这里不停地打giao
        return false;//没移动过
    }
    public boolean ifBeenAttack(ChessComponent[][] chessComponents,ArrayList<String> history,ChessColor oppositeSide){//判断对手是否正在被攻击（被将军）
        //System.out.println("giao3");//测试,可以到这里
        List<ChessboardPoint> getAllCanMoveList = getAllCanMoveTo(chessComponents,history,oppositeSide);
        if(getAllCanMoveList.size()!=0){
            for(int i=0 ; i<getAllCanMoveList.size();i++){
                if(getAllCanMoveList.get(i).toString().equals(source.toString())){//不确定这里对不对
                    //System.out.println("IfBeenAttackTrue");//测试
                    return true;//被攻击
                }
            }
        }
        return false;//没被攻击
    }
    public  List<ChessboardPoint> getAllCanMoveTo(ChessComponent[][] chessComponents,ArrayList<String> history,ChessColor oppositeSide){
        //改了一下，这个只适用于王车易位，返回当前 对手 棋子可以到的所有点，用于判断王是否被将军
        //System.out.println("giao4");//测试，可以到，，但是到这里又开始调用ifmove，无限循环
        List<ChessboardPoint> allCanMoveTo = new ArrayList<ChessboardPoint>();
        List<ChessboardPoint> helper =  new ArrayList<>();
        for(int i=0;i<8;i++){
            //System.out.println(i);
            for(int j=0;j<8;j++){
                //System.out.println(j);
                if(!(chessComponents[i][j] instanceof EmptySlotComponent)  && !(chessComponents[i][j] instanceof KingChessComponent)
                        && chessComponents[i][j].getChessColor().equals(oppositeSide)){//拿到的是对手棋子可以到的地方

                    helper=chessComponents[i][j].canMoveToList(chessComponents,history);
                    if(helper.size()!=0){
                        //System.out.println("giao5");//c测试，可以到，循环了四遍
                        //System.out.print(helper);
                        for(int k=0;k<helper.size();k++){
                            allCanMoveTo.add(helper.get(k));
                        }
                    }
                }
            }
        }
        //System.out.println("giao6");
        if(allCanMoveTo!=null){
            //System.out.println("allCanMoveTo"+String.valueOf(oppositeSide) + String.valueOf(allCanMoveTo));//测试,可以到
            return allCanMoveTo;
        }
        else{

            return new ArrayList<>();//不知道这里对不对

        }
    }
    private String getColorName(){
        if(getChessColor().equals(ChessColor.BLACK)){
            return "B";
        }else if(getChessColor().equals(ChessColor.WHITE)){
            return "W";
        }
        return "N";
    }
    public void CanMoveRed(Chessboard chessboard,boolean TrueOrFalse){
        List<ChessboardPoint> canMoveToList = canMoveToList(chessboard.getChessComponents(),chessboard.getHistory());
        if(canMoveToList.size()!=0){
            for(int i=0;i<canMoveToList.size();i++){
                ChessboardPoint canMoveToPoint = canMoveToList.get(i);
                ChessComponent canMoveToComponent = chessboard.getChessComponents()[canMoveToPoint.getX()][canMoveToPoint.getY()];
                /*if(canMoveToComponent instanceof  EmptySlotComponent){
                    chessboard.setEmptyslot(canMoveToComponent,true);
                }else{*/
                canMoveToComponent.setSelected(TrueOrFalse);
                canMoveToComponent.repaint();
            }
        }
    }

}
