package view;


/*import model.ChessColor;
import model.ChessComponent;
import model.EmptySlotComponent;
import model.RookChessComponent;*/
import controller.ClickController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {//bao包括摆放棋子
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;

    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller这个棋盘上的所有组件只共享一个模型控制器
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;

    public int getCHESS_SIZE() {
        return CHESS_SIZE;
    }


    public int count;
    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

        initiateEmptyChessboard();

        // FIXME: Initialize chessboard for testing only.
        /*initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
        initKingOnBoard(0,1,ChessColor.BLACK);//测试*/
        //以下为新加
        this.initRookOnBoard(0, 0, ChessColor.BLACK);
        this.initRookOnBoard(0, 7, ChessColor.BLACK);
        this.initRookOnBoard(7, 0, ChessColor.WHITE);
        this.initRookOnBoard(7, 7, ChessColor.WHITE);
        this.initKingOnBoard(0, 4, ChessColor.BLACK);
        this.initKingOnBoard(7, 4, ChessColor.WHITE);
        this.initPawnOnBoard(1, 0, ChessColor.BLACK);
        this.initPawnOnBoard(1, 1, ChessColor.BLACK);
        this.initPawnOnBoard(1, 2, ChessColor.BLACK);
        this.initPawnOnBoard(1, 3, ChessColor.BLACK);
        this.initPawnOnBoard(1, 4, ChessColor.BLACK);
        this.initPawnOnBoard(1, 5, ChessColor.BLACK);
        this.initPawnOnBoard(1, 6, ChessColor.BLACK);
        this.initPawnOnBoard(1, 7, ChessColor.BLACK);
        this.initPawnOnBoard(6, 0, ChessColor.WHITE);
        this.initPawnOnBoard(6, 1, ChessColor.WHITE);
        this.initPawnOnBoard(6, 2, ChessColor.WHITE);
        this.initPawnOnBoard(6, 3, ChessColor.WHITE);
        this.initPawnOnBoard(6, 4, ChessColor.WHITE);
        this.initPawnOnBoard(6, 5, ChessColor.WHITE);
        this.initPawnOnBoard(6, 6, ChessColor.WHITE);
        this.initPawnOnBoard(6, 7, ChessColor.WHITE);
        this.initQueenOnBoard(0, 3, ChessColor.BLACK);
        this.initQueenOnBoard(7, 3, ChessColor.WHITE);
        this.initKnightOnBoard(0, 1, ChessColor.BLACK);
        this.initKnightOnBoard(0, 6, ChessColor.BLACK);
        this.initKnightOnBoard(7, 1, ChessColor.WHITE);
        this.initKnightOnBoard(7, 6, ChessColor.WHITE);
        this.initBishopOnBoard(0, 2, ChessColor.BLACK);
        this.initBishopOnBoard(0, 5, ChessColor.BLACK);
        this.initBishopOnBoard(7, 2, ChessColor.WHITE);
        this.initBishopOnBoard(7, 5, ChessColor.WHITE);
    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists. chess2变成空棋子
        if (!(chess2 instanceof EmptySlotComponent)) {//不为空
            //instanceof是Java语言中的一个二元运算符，他的作用是判断一个引用类型的变量所指向的对象是否是一个类(或接口、抽象类、父类)的实例。
            // 即它左边的对象是否是它右边的类的实例，该运算符返回boolean类型的数据。
            //这里的意思应该是判断chess2上不是空的，可以吃子
            remove(chess2);//移走chess2，把原来的chess2变为空
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);//这里是chess2是空的，直接移动，变换坐标轴，对象是chess1
        //swapLocation是chesscomponent里的方法
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        chess1.repaint();
        chess2.repaint();

    }

    public void setEmptyslot(ChessComponent chess) {//可以用了
        remove(chess);
        add(chess = new EmptySlotComponent(chess.getChessboardPoint(), chess.getLocation(), clickController, CHESS_SIZE));
        int x=chess.getChessboardPoint().getX() , y=chess.getChessboardPoint().getY();
        chessComponents[x][y]=chess;
        chess.repaint();

        /*ChessComponent chessComponent = new EmptySlotComponent(new ChessboardPoint(row, col), this.calculatePoint(row, col),  this.clickController, this.CHESS_SIZE);
        chessComponent.setVisible(true);
        this.putChessOnBoard(chessComponent);*/
    }//新加的

    public void setKing(ChessComponent chess,ChessColor chessColor){
        remove(chess);
        add(chess = new KingChessComponent(chess.getChessboardPoint() , chess.getLocation() , chessColor,clickController , CHESS_SIZE));
        int x=chess.getChessboardPoint().getX() , y=chess.getChessboardPoint().getY();
        chessComponents[x][y]=chess;
        chess.repaint();
    }//新加
    public void setQueen(ChessComponent chess,ChessColor chessColor){
        remove(chess);
        add(chess = new QueenChessComponent(chess.getChessboardPoint() , chess.getLocation() , chessColor,clickController , CHESS_SIZE));
        int x=chess.getChessboardPoint().getX() , y=chess.getChessboardPoint().getY();
        chessComponents[x][y]=chess;
        chess.repaint();
    }//新加
    public void setRook(ChessComponent chess,ChessColor chessColor){
        remove(chess);
        add(chess = new RookChessComponent(chess.getChessboardPoint() , chess.getLocation() , chessColor,clickController , CHESS_SIZE));
        int x=chess.getChessboardPoint().getX() , y=chess.getChessboardPoint().getY();
        chessComponents[x][y]=chess;
        chess.repaint();
    }//新加的
    public void setBishop(ChessComponent chess,ChessColor chessColor){
        remove(chess);
        add(chess = new BishopChessComponent(chess.getChessboardPoint() , chess.getLocation() ,chessColor,clickController , CHESS_SIZE));
        int x=chess.getChessboardPoint().getX() , y=chess.getChessboardPoint().getY();
        chessComponents[x][y]=chess;
        chess.repaint();
    }//新加的
    public void setKnight(ChessComponent chess,ChessColor chessColor){
        remove(chess);
        add(chess = new KnightChessComponent(chess.getChessboardPoint() , chess.getLocation() , chessColor,clickController , CHESS_SIZE));
        int x=chess.getChessboardPoint().getX() , y=chess.getChessboardPoint().getY();
        chessComponents[x][y]=chess;
        chess.repaint();
    }//新加的
    public void setPawn(ChessComponent chess,ChessColor chessColor){
        remove(chess);
        add(chess = new PawnChessComponent(chess.getChessboardPoint() , chess.getLocation() , chessColor,clickController , CHESS_SIZE));
        int x=chess.getChessboardPoint().getX() , y=chess.getChessboardPoint().getY();
        chessComponents[x][y]=chess;
        chess.repaint();
    }//新加的


    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public void swapColor() {
        CurrentPlayer1 a ;
        CurrentPlayer2 b;
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;

        if (currentColor==ChessColor.WHITE){
        a=new CurrentPlayer1();

        }
        if (currentColor==ChessColor.BLACK){
            b=new CurrentPlayer2();
            b.dispose();
        }

    }//每下一次棋子交换当前player

    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        //new一个rook类
        chessComponent.setVisible(true);//这个是啥？visible是可见的
        putChessOnBoard(chessComponent);
    }

    private void initKingOnBoard(int row, int col, ChessColor color) {//测试
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        //new一个rook类
        chessComponent.setVisible(true);//这个是啥？visible是可见的
        putChessOnBoard(chessComponent);
    }
    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), this.calculatePoint(row, col), color, this.clickController, this.CHESS_SIZE);
        chessComponent.setVisible(true);
        this.putChessOnBoard(chessComponent);
    }

    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), this.calculatePoint(row, col), color, this.clickController, this.CHESS_SIZE);
        chessComponent.setVisible(true);
        this.putChessOnBoard(chessComponent);
    }

    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), this.calculatePoint(row, col), color, this.clickController, this.CHESS_SIZE);
        chessComponent.setVisible(true);
        this.putChessOnBoard(chessComponent);
    }

    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), this.calculatePoint(row, col), color, this.clickController, this.CHESS_SIZE);
        chessComponent.setVisible(true);
        this.putChessOnBoard(chessComponent);
    }



//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
////        g.drawImage(image,0,0,CHESS_SIZE*8,CHESS_SIZE*8,this);
//
//    }

    //    public void paintComponents(Graphics g) {
//        super.paintComponent(g);
////        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
////        g.drawImage(Images.back1,0,0,CHESS_SIZE*8,CHESS_SIZE*8,this);
//        g.drawImage(Images.back1,0,0,CHESS_SIZE*8,CHESS_SIZE*8,this);
//    }
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }//？？？？？


    public  List<ChessboardPoint> getAllCanMoveTo(ArrayList<String> history){
        //返回当前棋子可以到的所有点，用于判断王是否被将军
        List<ChessboardPoint> allCanMoveTo = new ArrayList<ChessboardPoint>();
        List<ChessboardPoint> helper =  new ArrayList<ChessboardPoint>();
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(!(chessComponents[i][j] instanceof EmptySlotComponent)){
                    helper=chessComponents[i][j].canMoveToList(chessComponents,history);
                    if(helper.size()!=0){
                        for(int k=0;k<helper.size();k++){
                            allCanMoveTo.add(helper.get(k));
                        }
                    }
                }
            }
        }
        return allCanMoveTo;
    }
    public void setCurrentColorNone(){
        this.currentColor= ChessColor.NONE;
    }

    private ArrayList<String> history = new ArrayList<>();//新加的，历史记录
    public void setHistory(ArrayList<String> history){
        this.history=history;
    }
    public ArrayList<String> getHistory(){
        return history;
    }
    public void deleteOneHistory(){
        history.remove(history.size()-1);
    }

    //以下用于读取本地数据
    public void loadGame(List<String> chessData) {
        char chess='R';

        initiateEmptyChessboard();//先加载一个8*8全是空的棋盘

        //以下为按chessData放置棋子，用initiate
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                //System.out.println("chessboard的load方法可以用");
                chess=chessData.get(i).charAt(j);
                if(chess=='R' || chess=='r'){
                    setRook(chessComponents[i][j],BlackOrWriteOrNone(chess));
                }else if(chess=='N'||chess=='n'){
                    setKnight(chessComponents[i][j],BlackOrWriteOrNone(chess));
                }else if(chess=='B'||chess=='b'){
                    setBishop(chessComponents[i][j],BlackOrWriteOrNone(chess));
                }else if(chess == 'Q' || chess == 'q'){
                    setQueen(chessComponents[i][j],BlackOrWriteOrNone(chess));
                }else if(chess== 'K'||chess=='k'){
                    setKing(chessComponents[i][j],BlackOrWriteOrNone(chess));
                }else if(chess=='P'||chess=='p'){
                    setPawn(chessComponents[i][j],BlackOrWriteOrNone(chess));
                }else if(chess=='_'){
                    setEmptyslot(chessComponents[i][j]);
                }
            }
        }

        String currentChessPlayer;
        currentChessPlayer=chessData.get(8);
        if(currentChessPlayer.equals("W")){
            currentColor = ChessColor.WHITE;
        }else if(currentChessPlayer.equals("B")){
            currentColor = ChessColor.BLACK;
        }

        /*if(chessData.size()>9){//有历史记录
            String historyList = chessData.get(9);


        }*/



        //chessData.forEach(System.out::println);
    }

    //以下用于把当前棋盘信息转为string类，并且存到txt里面
    public String saveLoad(){
        StringBuilder Graph=new StringBuilder();
        for(int i=0 ; i<8 ; i++){
            for(int j=0 ; j<8 ; j++){
                ChessComponent chessComponent=chessComponents[i][j];
                if(chessComponent.getChessColor().equals(ChessColor.NONE)){
                    Graph.append("_");
                }else if(chessComponent.getChessColor().equals(ChessColor.BLACK)){//黑棋大写
                    Graph.append(chessComponent.getChessName());
                }else if(chessComponent.getChessColor().equals(ChessColor.WHITE)){//白棋小写
                    Graph.append((char)(chessComponent.getChessName()+32));
                }
                if(j==7){
                    Graph.append("\n");
                }
            }

        }
        if(currentColor.equals(ChessColor.WHITE)){
            Graph.append('W');
        }else if(currentColor.equals(ChessColor.BLACK)){
            Graph.append('B');
        }
        Graph.append("\n");
        Graph.append(history);


        return String.valueOf(Graph);
    }

    public ChessColor BlackOrWriteOrNone(char chess){
        if(chess=='R' || chess=='N' || chess=='B' || chess=='Q' || chess=='K' || chess=='P'){
            return ChessColor.BLACK;
        }else if(chess=='_'){
            return ChessColor.NONE;
        }
        return ChessColor.WHITE;
    }
}
