//93行
package view;

import controller.GameController;
import model.ChessColor;
import model.ChessComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体，是一个框架
 */
public class ChessGameFrame extends JFrame implements MouseListener{
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    Container con;
    JToolBar jmain;

    int k=0;
    String title;
    JLabel image,im,ge,ma,ima,age;
    JButton set,undo,undo1,restart,fail,sort,ID1,ID2;
    private Chessboard chessboard;//新加的

    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS102A Project"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;
        this.chessboard=new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);//新加的

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(3); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addChessboard();
        addUndoButton();
        addUndo1Button();
        addRestartButton();
        addFailButton();
        addSortButton();

        ID1();
        ID2();
        addSetButton();

        con = this.getContentPane();
        con.setLayout(null);
        this.setTitle(title);
        jmain = new JToolBar();
        this.setSize(1000,800);


        con.add(ge=new JLabel(Images.backchess));
        ge.setBounds(75,75,600,600);
        con.add(ma=new JLabel(Images.backchess1));
        ma.setBounds(75,75,600,600);
        con.add(ima=new JLabel(Images.backchess3));
        ima.setBounds(75,75,600,600);

        con.add(image=new JLabel(Images.background1));
        image.setBounds(0,0,1000,800);
        con.add(im=new JLabel(Images.background3));
        im.setBounds(0,0,1000,800);
        con.add(age=new JLabel(Images.background5));
        age.setBounds(0,0,1000,800);

        this.setLocation(350,20);
        this.setIconImage(Images.paint.getImage());
        setResizable(false);
        this.setVisible(true);



    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {

        Chessboard chessboard = new Chessboard(601,600);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);


        add(chessboard);
        this.chessboard=chessboard;//新加的

    }


    private void ID1(){
        ID1= new JButton("");
        System.out.println("查看黑方用户属性");;
        ID1.addMouseListener(this);
//    ID1.setEnabled(false);
        ID1.setLocation(0, 0);
        ID1.setSize(70, 70);
        ID1.setIcon(Images.black);
        ID1.setContentAreaFilled(false);
        ID1.setBorder(null);
        add(ID1);
    }
    private void ID2(){
        ID2= new JButton("");
        System.out.println("查看白方用户属性");
        ID2.addMouseListener(this);
//    ID1.setEnabled(false);
        ID2.setLocation(0, HEIGTH/10+600);
        ID2.setSize(70, 70);
        ID2.setIcon(Images.white);
        ID2.setContentAreaFilled(false);
        ID2.setBorder(null);
        add(ID2);
    }
    //添加设置按钮
    private void addSetButton() {
        set= new JButton("");
        set.addMouseListener(this);
        System.out.println("Click 设置");
        set.setIcon(Images.set);
        set.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                set.setIcon(Images.set1);
            }
        });
        set.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                set.setIcon(Images.set);

            }
        });
        set.setLocation(HEIGTH-90, HEIGTH/10);
        set.setSize(360, 60);

        set.setContentAreaFilled(false);
        set.setBorder(null);
        add(set);
    }
    //添加上一步按钮
    private void addUndoButton() {
        undo= new JButton("");
        System.out.println("Click 上一步");
        undo.setIcon(Images.undo);
        undo.addMouseListener(this);

        undo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                undo.setIcon(Images.undo1);
            }
        });
        undo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                undo.setIcon(Images.undo);

            }
        });
        undo.setLocation(HEIGTH-90, HEIGTH / 10+100);
        undo.setSize(360, 60);

        undo.setContentAreaFilled(false);
        undo.setBorder(null);

        undo.addActionListener(new RegretClickListener());
        add(undo);
    }
    public class RegretClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            String oneHistory = chessboard.getHistory().get(chessboard.getHistory().size()-1);
            //拿到最近一次history,例如WP55-N_45
            int x1 = Integer.parseInt(oneHistory.substring(2,3));
            int y1 = Integer.parseInt(oneHistory.substring(3,4));//原来被移动棋子开始的位置
            int x2 = Integer.parseInt(oneHistory.substring(7,8));
            int y2 = Integer.parseInt(oneHistory.substring(8,9));//原来到的空棋子或被吃的棋子位置
            ChessComponent before = chessboard.getChessComponents()[x1][y1];//原来被移动棋子的地方
            ChessComponent after = chessboard.getChessComponents()[x2][y2];//现在被移动棋子的地方
            String beforeChessKind = oneHistory.substring(1,2); //原来被移动棋子的类型
            String afterChessKind = oneHistory.substring(6,7);//到的空棋子或被吃的棋子类型

            //改原来被移动
            if(oneHistory.substring(0,1).equals("W")){//原来被移动的是白棋
                if(beforeChessKind.equals("K")){//原来被移动的是白王
                    chessboard.setKing(before, ChessColor.WHITE);
                }else if(beforeChessKind.equals("Q")){
                    chessboard.setQueen(before,ChessColor.WHITE);//原来被移动的是白皇后
                }else if(beforeChessKind.equals("R")){
                    chessboard.setRook(before,ChessColor.WHITE);//原来被移动的是白车
                }else if(beforeChessKind.equals("B")){
                    chessboard.setBishop(before,ChessColor.WHITE);//原来被移动的是象
                }else if(beforeChessKind.equals("N")){
                    chessboard.setKnight(before,ChessColor.WHITE);
                }else if(beforeChessKind.equals("P")){
                    chessboard.setPawn(before,ChessColor.WHITE);
                }
            } else if (oneHistory.substring(0,1).equals("B")) {//原来被移动的是黑棋子
                if(beforeChessKind.equals("K")){//原来被移动的是白王
                    chessboard.setKing(before, ChessColor.BLACK);
                }else if(beforeChessKind.equals("Q")){
                    chessboard.setQueen(before,ChessColor.BLACK);//原来被移动的是皇后
                }else if(beforeChessKind.equals("R")){
                    chessboard.setRook(before,ChessColor.BLACK);//原来被移动的是车
                }else if(beforeChessKind.equals("B")){
                    chessboard.setBishop(before,ChessColor.BLACK);//原来被移动的是象
                }else if(beforeChessKind.equals("N")){
                    chessboard.setKnight(before,ChessColor.BLACK);//马
                }else if(beforeChessKind.equals("P")){
                    chessboard.setPawn(before,ChessColor.BLACK);//兵
                }
            }

            //改后来到的
            if(oneHistory.substring(5,6).equals("N")){//后来到的是空格子
                chessboard.setEmptyslot(after);
            }else if(oneHistory.substring(5,6).equals("W")){//后来被移动的是白棋
                if(afterChessKind.equals("K")){//后来被移动的是白王
                    chessboard.setKing(after, ChessColor.WHITE);
                }else if(afterChessKind.equals("Q")){
                    chessboard.setQueen(after,ChessColor.WHITE);//后来被移动的是白皇后
                }else if(afterChessKind.equals("R")){
                    chessboard.setRook(after,ChessColor.WHITE);//后来被移动的是白车
                }else if(afterChessKind.equals("B")){
                    chessboard.setBishop(after,ChessColor.WHITE);//后来被移动的是象
                }else if(afterChessKind.equals("N")){
                    chessboard.setKnight(after,ChessColor.WHITE);
                }else if(afterChessKind.equals("P")){
                    chessboard.setPawn(after,ChessColor.WHITE);
                }
            } else if (oneHistory.substring(5,6).equals("B")) {//后来被移动的是黑棋子
                if(afterChessKind.equals("K")){//后来被移动的是白王
                    chessboard.setKing(after, ChessColor.BLACK);
                }else if(afterChessKind.equals("Q")){
                    chessboard.setQueen(after,ChessColor.BLACK);//后被移动的是皇后
                }else if(afterChessKind.equals("R")){
                    chessboard.setRook(after,ChessColor.BLACK);//后来被移动的是车
                }else if(afterChessKind.equals("B")){
                    chessboard.setBishop(after,ChessColor.BLACK);//后来被移动的是象
                }else if(afterChessKind.equals("N")){
                    chessboard.setKnight(after,ChessColor.BLACK);//马
                }else if(afterChessKind.equals("P")){
                    chessboard.setPawn(after,ChessColor.BLACK);//兵
                }
            }

            //以下改当前玩家
            chessboard.swapColor();
            chessboard.deleteOneHistory();//去掉一次历史记录


        }
    }
    //添加存档按钮
    private void addUndo1Button() {
        undo1= new JButton("");
        System.out.println("Click 下载");
        undo1.setIcon(Images.loud);
        undo1.addMouseListener(this);
        undo1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                undo1.setIcon(Images.loud1);
            }
        });
        undo1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                undo1.setIcon(Images.loud);

            }
        });
        undo1.addActionListener(e -> {
            try{
                File writename = new File("C://Users//25739//IdeaProjects//project3//src//test//testUpLoad.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件

                writename.createNewFile(); // 创建新文件

                BufferedWriter out = new BufferedWriter(new FileWriter(writename));

                out.write(chessboard.saveLoad());

                out.flush(); // 把缓存区内容压入文件

                out.close(); // 最后记得关闭文件

            }catch (Exception e1) {

                e1.printStackTrace();

            }
        });
        undo1.setLocation(HEIGTH-90, HEIGTH / 10+300);
        undo1.setSize(360, 60);


        undo1.setContentAreaFilled(false);
        undo1.setBorder(null);
        add(undo1);
    }
    //添加重新开始
    private void addRestartButton() {
        restart= new JButton("");
        System.out.println("Click 重新开始");
        restart.setIcon(Images.restart);
        restart.addMouseListener(this);
        restart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                restart.setIcon(Images.restart1);
            }
        });
        restart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                restart.setIcon(Images.restart);

            }
        });
        restart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ChessGameFrame(1000,760);

                dispose();
            }
        });
        restart.setLocation(HEIGTH-90, HEIGTH / 10+200);
        restart.setSize(360, 60);

        restart.setContentAreaFilled(false);
        restart.setBorder(null);
        add(restart);
    }
    //添加退出按钮
    private void addFailButton() {
        fail= new JButton("");
        System.out.println("Click 退出");
        fail.setIcon(Images.Exit);
        fail.addMouseListener(this);
        fail.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                fail.setIcon(Images.Exit1);
            }
        });
        fail.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                fail.setIcon(Images.Exit);

            }
        });
        fail.setLocation(HEIGTH-90, HEIGTH / 10+500);
        fail.setSize(360, 90);

        fail.setContentAreaFilled(false);
        fail.setBorder(null);
        dispose();
        add(fail);
    }
    //添加动态演示按钮
    private void addSortButton() {
        sort= new JButton("");
        sort.setIcon(Images.sort);
        sort.addMouseListener(this);
        sort.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                sort.setIcon(Images.sort1);
            }
        });
        sort.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                sort.setIcon(Images.sort);

            }
        });
        sort.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this,"Input Path here");
            gameController.loadGameFromFile(path);
        });
        sort.setLocation(HEIGTH-90, HEIGTH / 10+400);
        sort.setSize(360, 90);

//        sort.addActionListener(new recoverClickListener());
        sort.setContentAreaFilled(false);
        sort.setBorder(null);
        add(sort);
    }
//    //以下为保存当前棋盘信息
//    private void addUploadChessBoard(){
//        JButton button = new JButton("保存棋盘");
//        button.setLocation(HEIGTH-90, HEIGTH / 10+650);
//        button.setSize(100, 30);
//        add(button);
//        button.addActionListener(e -> {
//            try{
//                File writename = new File("C://Users//25739//IdeaProjects//project3//src//test//testUpLoad.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件
//
//                writename.createNewFile(); // 创建新文件
//
//                BufferedWriter out = new BufferedWriter(new FileWriter(writename));
//
//                out.write(chessboard.saveLoad());
//
//                out.flush(); // 把缓存区内容压入文件
//
//                out.close(); // 最后记得关闭文件
//
//            }catch (Exception e1) {
//
//                e1.printStackTrace();
//
//            }
//        });
//    }
    //保存棋盘信息成功



    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource().equals(set)){
            System.out.println("Click 设置");
            k++;

            if (k==1){
                con.remove(image);
                con.add(image=new JLabel(Images.backchess));
                image.setLocation(0,0);
                image.setBounds(0,0,1000,800);
                con.remove(ge);
                con.add(ge=new JLabel(Images.backchess1));
                ge.setLocation(0,0);
                ge.setBounds(0,0,1000,800);
                con.setVisible(true);
            }else if (k==2){
                con.remove(ma);
                con.add(ma=new JLabel(Images.backchess));
                ma.setLocation(0,0);
                ma.setBounds(0,0,1000,800);
                con.remove(im);
                con.add(im=new JLabel(Images.backchess1));
                im.setLocation(0,0);
                im.setBounds(0,0,1000,800);
                con.setVisible(true);
            }
        }else if(e.getSource().equals(undo)){
            System.out.println("Click 上一步");

        }else if(e.getSource().equals(undo1)){
            System.out.println("Click 下一步");
        }else if(e.getSource().equals(restart)){
            System.out.println("Click 重新开始");
        }else if(e.getSource().equals(fail)){
            System.out.println("Click 退出");
            //跳转到下一界面
            new MainFrame();

            //关闭当前界面
            dispose();
        }else if(e.getSource().equals(sort)){

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    //以下为得到本地棋盘信息并放到棋盘上
//    private void addLoadButton() {
//        JButton button = new JButton("Load");
//        button.setLocation(HEIGTH-90, HEIGTH / 10+600);
//        button.setSize(100, 30);
//        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(button);
//
//        button.addActionListener(e -> {
//            System.out.println("Click load");
//            String path = JOptionPane.showInputDialog(this,"Input Path here");
//            gameController.loadGameFromFile(path);
//        });
//    }



}
