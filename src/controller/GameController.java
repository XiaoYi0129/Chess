//这个方法用于初始化开启新一轮游戏
package controller;

import view.Chessboard;
import view.ExceptionWarningForLoad;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GameController {
    private Chessboard chessboard;

//    private ClickController clickController;
    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }//初始化棋盘
//    public void getLastChess(){
//        clickController.getHistory().remove(clickController.getHistory().size()-1);
//
//
//    }
//    public void loadGameFromFile(String path) {//备份，下次打开还可以继续上一次游戏？？？？？？？？？？？？？？？
//        try {
//            List<String> chessData = Files.readAllLines(Path.of(path));
//            chessboard.loadGame(chessData);
//        } catch (IOException e) {//？？？？？？？？？？？？？？/
//            e.printStackTrace();
//        }
//    }
    public List<String> loadGameFromFile(String path) {
    try {
        //以下为判断path格式是否为txt
        if (!path.endsWith(".txt")) {
            System.out.println("It's not a correct chessboard");//检查文件格式错误104
                /*JFrame jFrame = new JFrame();
                jFrame.setSize(50, 100);
                jFrame.setLocation(760, 76);
                JLabel jLabel1 = new JLabel("104");
                jFrame.add(jLabel1);
                jFrame.setVisible(true);
                MainFrame.this.add(jFrame);*/
            ExceptionWarningForLoad exceptionWarningForLoad = new ExceptionWarningForLoad("104 文件格式错误");
            return null;//这里return null有必要，这样下面就不会运行了
        }

        List<String> chessData = Files.readAllLines(Path.of(path));

        //以下为棋盘错误101
        if(chessData.size()<8 || chessData.get(0).length()!=8
                || chessData.get(1).length()!=8 || chessData.get(2).length()!=8
                || chessData.get(3).length()!=8 || chessData.get(4).length()!=8
                || chessData.get(5).length()!=8 || chessData.get(6).length()!=8
                || chessData.get(7).length()!=8 )  {
            ExceptionWarningForLoad exceptionWarningForLoad = new ExceptionWarningForLoad("101 棋盘并非 8*8");
            return null;
        }
        //经过以上已经保证是8*8的棋盘

        //导入数据只有棋盘，没有下一步行棋的方的提示103
        if(chessData.size()<9 ){
            ExceptionWarningForLoad exceptionWarningForLoad = new ExceptionWarningForLoad("103 没有下一步行棋的方的提示");
            return null;
        }


        //以下为棋盘错误102,棋子并非六种之一，棋子并非黑白棋子
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                String chess = chessData.get(i).substring(j,j+1);
                if(  !chess.equals("_") && !chess.equals("K") && !chess.equals("k") && !chess.equals("Q") && !chess.equals("q")
                        && !chess.equals("R") && !chess.equals("r") && !chess.equals("B") && !chess.equals("b") && !chess.equals("N")
                        && !chess.equals("n") && !chess.equals("P") && !chess.equals("p")){
                    ExceptionWarningForLoad exceptionWarningForLoad = new ExceptionWarningForLoad("102 棋子并非六种之一，棋子并非黑白棋子");
                    return null;
                }
            }
        }
        //以上保证棋子正确

        //所有保证了才可以进入游戏界面
        chessboard.loadGame(chessData);
        return chessData;
    } catch (IOException e) {//？？？？？？？？？？？？？？/
        e.printStackTrace();
    }
    return null;
}
}
