//这个方法和鼠标在棋盘上点击进行操作有关
package controller;


import model.ChessColor;
import model.ChessComponent;
import model.EmptySlotComponent;
import model.KingChessComponent;
import view.*;

import javax.sound.sampled.*;
import java.io.File;
import java.util.ArrayList;

public class ClickController {//吃过路兵标记！，王车易位标记@，#
    private final Chessboard chessboard;
    private ChessComponent first;
    //这个类是一个抽象类，主要表示8*8棋盘上每个格子的棋子情况，当前有两个子类继承它，分别是EmptySlotComponent(空棋子)和RookChessComponent(车)
    private ArrayList<String> history = new ArrayList<>();//历史记录
    StringBuilder oneHistory = new StringBuilder();
    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }
    //可以点击按钮重置游戏棋盘

    public ArrayList<String> getHistory() {
        return history;
    }
    public void onClick(ChessComponent chessComponent) {

        if (first == null) {//这里是第一次选中，还没有对first进行赋值，所以first还是null
            if (handleFirst(chessComponent)) {//目标选取的棋子与棋盘记录的当前行棋方颜色相同，可以对这个棋子进行操作





                chessComponent.setSelected(true);
                first = chessComponent;
                if( !(chessComponent instanceof EmptySlotComponent)){//只有非空的才会记录历史
                    String chess1 =getColorName(chessComponent) + chessComponent.getChessName() + chessComponent.getSource().getX() + chessComponent.getSource().getY();
                    System.out.print(chess1);//测试chess1
                    oneHistory.append(chess1);
                    //颜色，名字，x坐标，y坐标，例：BP10

                    first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint(),history);//显示可以到的地方
//                    for (int i=0;i<first.canMoveToList(chessboard.getChessComponents(),history).size();i++){
//                        g.setColor(Color.RED);
//                        g.drawOval(first.canMoveToList(chessboard.getChessComponents(),history).get(i).getY()*75, first.canMoveToList(chessboard.getChessComponents(),history).get(i).getX()*75,chessComponent.getWidth() , chessComponent.getHeight());
//                    }

                }
                first.CanMoveRed(chessboard,true);
                //可以到的地方亮起来
                first.repaint();//刷新，可以重复用
            }
        } else {//这里是第二次选中，已经对first进行赋值

            if (first == chessComponent || !(handleSecond(chessComponent)) ) {//这里是指点原来的地方，点击两次取消选取
//                g.setColor(null);
                chessComponent.setSelected(false);

                first.CanMoveRed(chessboard,false);//可以到的地方暗下去
                ChessComponent recordFirst = first;

                first = null;

                oneHistory.delete(0,4);//取消历史记录

                recordFirst.repaint();
            } else if (handleSecond(chessComponent)) {//第二次点击另一个棋子，吃子，要先判断这两个棋子不是一个颜色的&& can move to？？？？？？？


                //repaint in swap chess method.
                first.CanMoveRed(chessboard,false);
                //刚刚亮起来的地方暗下去
//                g.setColor(null);
                if(chessComponent instanceof  EmptySlotComponent){
                    oneHistory.append('-');//移到空棋子
                }else{
                    oneHistory.append('X');//吃子
                }
                String chess2 = getColorName(chessComponent) + chessComponent.getChessName() + chessComponent.getSource().getX() + chessComponent.getSource().getY();
                System.out.print(chess2);//测试chess2
                oneHistory.append(chess2);

                chessboard.swapChessComponents(first, chessComponent);//first为第一次点击的棋盘，chessComponent为第二次点击的棋盘
//                chessboard.swapColor();

                //以下为吃过路兵,吃过路兵是点击当前方要到达的地方，不是点击它要吃的过路兵，要吃的兵要被移走
//                if(chessboard.getCurrentColor().equals(ChessColor.BLACK)//黑吃白
//                && history.size()!=0
//                && history.get(history.size()-1).substring(0,3).equals("WP6")
//                && history.get(history.size()-1).substring(5,8).equals("N_4")//上一步为两步白兵
//                && oneHistory.substring(0,3).equals("BP4")//这一步斜走空
//                && oneHistory.substring(5,8).equals("N_5")){//满足吃过路兵，移除被吃掉的白兵
//                    chessboard.setEmptyslot(4,chessComponent.getY());
//                }
//                if(chessboard.getCurrentColor().equals(ChessColor.WHITE)//白吃黑
//                && history.size()!=0
//                && history.get(history.size()-1).substring(0,3).equals("BP1")
//                && history.get(history.size()-1).substring(5,8).equals("N_3")
//                && oneHistory.substring(0,3).equals("WP3")
//                && oneHistory.substring(5,8).equals("N_2")){
//                    chessboard.setEmptyslot(3,chessComponent.getY());
//                }//吃过路兵完
                //以下为吃过路兵中移走被吃掉的棋子,吃过路兵是点击当前方要到达的地方，不是点击它要吃的过路兵，要吃的兵要被移走
                //原来那个棋子移动的的地方，即first，是空的
                if(chessboard.getCurrentColor().equals(ChessColor.BLACK)//黑吃白
                /*&& history.size()!=0
                && history.get(history.size()-1).substring(0,3).equals("WP6")
                && history.get(history.size()-1).substring(5,8).equals("N_4")*///上一步为两步白兵
                        && String.valueOf(oneHistory).substring(0,3).equals("BP4")//这一步斜走空
                        && String.valueOf(oneHistory).substring(5,8).equals("N_5")){//满足吃过路兵，移除被吃掉的白兵
                    //System.out.print("giao");//测试
                    chessboard.setEmptyslot(chessboard.getChessComponents()[4][Integer.parseInt(String.valueOf(oneHistory).substring(8,9))]);
                    //chessboard.setEmptyslot(4,chessComponent.getY()); 还是不对
                }
                if(chessboard.getCurrentColor().equals(ChessColor.WHITE)//白吃黑
                /*&& history.size()!=0
                && history.get(history.size()-1).substring(0,3).equals("BP1")
                && history.get(history.size()-1).substring(5,8).equals("N_3")*/
                        && String.valueOf(oneHistory).substring(0,3).equals("WP3")
                        && String.valueOf(oneHistory).substring(5,8).equals("N_2")){
                    chessboard.setEmptyslot(chessboard.getChessComponents()[3][Integer.parseInt(String.valueOf(oneHistory).substring(8,9))]);
                    //chessboard.swapChessComponents(first,chessboard.getChessComponents()[3][chessComponent.getY()]);
                    //chessboard.setEmptyslot(3,chessComponent.getY()); 还是不对
                }//吃过路兵完
//以下为王车易位中的兵移位
                if(chessboard.getCurrentColor().equals(ChessColor.BLACK)) {//黑王易位
                    //以下为黑王短易位
                    if(String.valueOf(oneHistory).equals("BK04-N_06")){
                        chessboard.swapChessComponents(chessboard.getChessComponents()[0][7],chessboard.getChessComponents()[0][5]);
                    }
                    //以下为黑王长易位
                    else if(String.valueOf(oneHistory).equals("BK04-N_02")){
                        chessboard.swapChessComponents(chessboard.getChessComponents()[0][0],chessboard.getChessComponents()[0][3]);
                    }
                }
                //以下为白王易位
                else if(chessboard.getCurrentColor().equals(ChessColor.WHITE)){
                    //以下为白王短易位
                    if(String.valueOf(oneHistory).equals("WK74-N_76")){
                        chessboard.swapChessComponents(chessboard.getChessComponents()[7][7],chessboard.getChessComponents()[7][5]);
                    }else if(String.valueOf(oneHistory).equals("WK74-N_72")){
                        chessboard.swapChessComponents(chessboard.getChessComponents()[7][0],chessboard.getChessComponents()[7][3]);
                    }
                }
                //王车易位完
                //以下为判断输赢,还是当前局 棋子还没换 要判对方是否输
                //以下为下一局没有王
                if(chessboard.getCurrentColor().equals(ChessColor.BLACK)//当前是黑
                        && ifEnd(ChessColor.WHITE)){//以下为判白方输，黑方赢
                    WinPanel winPanel = new WinPanel(ChessColor.BLACK);
                    chessboard.setCurrentColorNone();//这里可能要改
                    System.out.println("Black WIN!");
                }else if(chessboard.getCurrentColor().equals(ChessColor.WHITE)//当前是白
                        && ifEnd(ChessColor.BLACK)){//以下为判黑方输，白方赢
                    WinPanel winPanel = new WinPanel(ChessColor.WHITE);
                    chessboard.setCurrentColorNone();//这里可能要改
                    System.out.println("White WIN!");
                }
            }
            //以下为兵底线升变

            if(chessboard.getCurrentColor().equals(ChessColor.BLACK)
                    && String.valueOf(oneHistory).substring(0, 3).equals("BP6")){//黑兵底线升变
                //这一步是黑兵从x=6走，下一步只能进到7
                PawnDevelopPanel pawnDevelopPanel = new PawnDevelopPanel(chessboard,first);

                        /*int PawnDevelop = chessComponent.getPawnDevelop();
                        System.out.println(PawnDevelop);//test
                        if(PawnDevelop==1){
                            chessboard.setQueen(chessComponent);
                        }else if(PawnDevelop==2){
                            chessboard.setRook(chessComponent);
                        }else if(PawnDevelop==3){
                            chessboard.setBishop(chessComponent);
                        }else if(PawnDevelop==4){
                            chessboard.setKnight(chessComponent);
                        }
                        chessComponent.setPawnDevelop(0);*/

            }else if(chessboard.getCurrentColor().equals(ChessColor.WHITE)
                    && String.valueOf(oneHistory).substring(0, 3).equals("WP1")){//白兵底线
                PawnDevelopPanel pawnDevelopPanel = new PawnDevelopPanel(chessboard,first);//改

                    /*int PawnDevelop = chessComponent.getPawnDevelop();
                    if(PawnDevelop==1){
                        chessboard.setQueen(chessComponent);
                    }else if(PawnDevelop==2){
                        chessboard.setRook(chessComponent);
                    }else if(PawnDevelop==3){
                        chessboard.setBishop(chessComponent);
                    }else if(PawnDevelop==4){
                        chessboard.setKnight(chessComponent);
                    }
                    chessComponent.setPawnDevelop(0);*/

            }
            //兵底线升变完成
//这里还没有交换玩家，还是在本局
            //以下为给每个王是否被攻击计数同时报警
            //计数规则为当黑王被将军，在那个history里面加上符号#，当白王被将军，在那个history里面加上符号$
            if(chessboard.getCurrentColor().equals(ChessColor.WHITE) ){
                //当前是白棋，刚下完一局，以下要判断白棋下完这一步后黑棋王是否被攻击
                //先找到黑棋的王
                for(int i=0 ; i<8 ; i++){
                    for(int j=0;j<8;j++){
                        if(chessboard.getChessComponents()[i][j] instanceof KingChessComponent
                                && chessboard.getChessComponents()[i][j].getChessColor().equals(ChessColor.BLACK)){//是王且是黑棋
                            ChessComponent BlackKing=chessboard.getChessComponents()[i][j];
                            //System.out.println("currentColorIsWhite");//测试,可以到
                            if(BlackKing.ifBeenAttack(chessboard.getChessComponents(),history,ChessColor.WHITE)){
                                oneHistory.append("#");//黑王被将军次数加一
                                //以下为黑王被将军报警
                                KingBeenAttack kingBeenAttack=new KingBeenAttack(ChessColor.BLACK);

                            }
                        }
                    }
                }

            }else if(chessboard.getCurrentColor().equals(ChessColor.BLACK)){
                //当前是黑棋，刚下完一局，以下要判断黑棋下完这一步后白棋王是否被攻击
                //先找到白棋的王
                for(int i=0 ; i<8 ; i++){
                    for(int j=0;j<8;j++){
                        if(chessboard.getChessComponents()[i][j] instanceof KingChessComponent
                                && chessboard.getChessComponents()[i][j].getChessColor().equals(ChessColor.WHITE)){//是王且是白棋
                            ChessComponent WhiteKing=chessboard.getChessComponents()[i][j];
                            if(WhiteKing.ifBeenAttack(chessboard.getChessComponents(),history,ChessColor.BLACK)){
                                oneHistory.append("$");//白王被将军次数加一
                                //以下为白王被将军报警
                                KingBeenAttack kingBeenAttack=new KingBeenAttack(ChessColor.WHITE);

                            }
                        }
                    }
                }
            }
            //将军报警系统完
            //以下为长将和棋

            if(oneHistory.length()==10 && history.size()>6){//有王被将军
                if(String.valueOf(oneHistory).substring(9,10).equals("#")){//黑王在当前被将军
                    if(history.get(history.size()-2).length()==10
                            && history.get(history.size()-4).length()==10
                            && history.get(history.size()-6).length()==10){//黑王之前已经被连续将军三次
                        DrawPanel drawPanel=new DrawPanel();//跳出和棋框
                    }
                }else if(String.valueOf(oneHistory).substring(9,10).equals("$")){//白王在当前被将军
                    if(history.get(history.size()-2).length()==10
                            && history.get(history.size()-4).length()==10
                            && history.get(history.size()-6).length()==10){//白王之前已经被连续将军三次
                        DrawPanel drawPanel=new DrawPanel();////跳出和棋框
                    }
                }
            }

            //长将和棋成功
            chessboard.swapColor();//先判断再改当前玩家

            history.add(String.valueOf(oneHistory));
                System.out.print(history);//测试history

            oneHistory=new StringBuilder();//初始化每次历史记录工具
            chessboard.setHistory(history);

                first.setSelected(false);

                first = null;//结束一次移动，first设为null
            playMusic();
            //以下为一个局面出现三次,在第四局第一方下即和棋
            if(history.size()>8){
                int size=history.size();
                String s1=history.get(size-1).substring(7,9);
                String s2=history.get(size-1).substring(2,4);
                String s3=history.get(size-2).substring(7,9);
                String s4=history.get(size-2).substring(2,4);
                if(history.get(size-3).substring(2,4).equals(s1)
                        && history.get(size-3).substring(7,9).equals(s2)
                        && history.get(size-4).substring(2,4).equals(s3)
                        && history.get(size-4).substring(7,9).equals(s4)
                        && history.get(size-5).substring(2,4).equals(s2)
                        && history.get(size-5).substring(7,9).equals(s1)
                        && history.get(size-6).substring(2,4).equals(s4)
                        && history.get(size-6).substring(7,9).equals(s3)
                        && history.get(size-7).substring(2,4).equals(s1)
                        && history.get(size-7).substring(7,9).equals(s2)){
                    //三次重复局面，当第四次开头出现时判和棋
                    DrawPanel drawPanel = new DrawPanel();
                }
            }
            //三次重复局面已经完成！

            //以下为某方行棋时发现无子可动时和棋
            if(chessComponent.getAllCanMoveTo(chessboard.getChessComponents(),history,ChessColor.WHITE).size()==0
                    || chessComponent.getAllCanMoveTo(chessboard.getChessComponents(),history,ChessColor.BLACK).size()==0){
                //只要有一方无子可动
                //跳出和棋框，停止游戏
                DrawPanel drawPanel = new DrawPanel();
            }

            }

        }


    private static void playMusic() {// 背景音乐播放

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File("src/Image/落子_1.WAV"));    //绝对路径
            AudioFormat aif = ais.getFormat();
            final SourceDataLine sdl;
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, aif);
            sdl = (SourceDataLine) AudioSystem.getLine(info);
            sdl.open(aif);
            sdl.start();
            FloatControl fc = (FloatControl) sdl.getControl(FloatControl.Type.MASTER_GAIN);
            // value可以用来设置音量，从0-2.0
            double value = 2;
            float dB = (float) (Math.log(value == 0.0 ? 0.0001 : value) / Math.log(10.0) * 20.0);
            fc.setValue(dB);
            int nByte = 0;
            final int SIZE = 1024 * 64;
            byte[] buffer = new byte[SIZE];
            while (nByte != -1) {
                nByte = ais.read(buffer, 0, SIZE);
                sdl.write(buffer, 0, nByte);
            }
            sdl.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(ChessComponent chessComponent) {//一个判断方法
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
        //       目标选中棋子                             当前行棋方颜色   若相同返回正确
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessComponent chessComponent) {
        /*System.out.print(chessComponent.getChessColor());
        System.out.print(chessboard.getCurrentColor());
        System.out.print(first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint()));*/
        //以上为测试
        return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint(),history);//这里已经开始用多态了
        //canMoveto是ChessComponent里面的方法，this棋子对象的移动规则和当前位置(chessboardPoint)能否到达目标位置
        //     * <br>
        //     * 这个方法主要是检查移动的合法性，每个棋子的合法性不同（在ChessComponent里面有定义），如果合法就返回true，反之是false
    }

    private String getColorName(ChessComponent chessComponent){
        if(chessComponent.getChessColor().equals(ChessColor.BLACK)){
            return "B";
        }else if(chessComponent.getChessColor().equals(ChessColor.WHITE)){
            return "W";
        }
        return "N";
    }
    //以下用于判输赢
    private boolean ifEnd(ChessColor oppositePlayer){

        for(int i=0 ; i<8 ; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessboard.getChessComponents()[i][j] instanceof KingChessComponent
                        && chessboard.getChessComponents()[i][j].getChessColor().equals(oppositePlayer)) {//找到对手王
                    //ChessComponent WhiteKing = chessboard.getChessComponents()[i][j];
                    //break;//这里就这样吧
                    //ChessComponent oppositeKing=chessboard.getChessComponents()[i][j];

                    //if(oppositeKing.ifBeenAttack(chessboard.getChessComponents(), chessboard.getHistory(),chessboard.getCurrentColor())){//对手王被将军
                    //if(oppositeKing.canMoveToList(chessboard.getChessComponents(), history).size()==0 ){//对手王没地方躲

                    // }else if(checkCanMoveToListForIfEnd(oppositeKing.canMoveToList(chessboard.getChessComponents(), history),oppositeKing.getAllCanMoveTo(chessboard.getChessComponents(), chessboard.getHistory(),chessboard.getCurrentColor())))

                    //}

                    // }
                    //List<ChessboardPoint> KingCanMoveTo = currentKing.canMoveToList(chessboard.getChessComponents(),history);

                    return false;//对手还有王
                }
            }
        }
        return true;//对手没有王
    }

}
