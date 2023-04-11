package view;

import controller.GameController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {
    //设置窗体的基本属性	大小
    //2.1创建开始按钮 帮助按钮 离开按钮 组件
    JButton one,bot,exit;
    private GameController gameController;
    public MainFrame() {//无参构造，创建对象。并在main函数中调用
        setBounds(0,0,900,800);
        setResizable(false);
        setLayout(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        one();
        exit();
        /**1.实现背景图片及窗体属性*/

        JLabel background = new JLabel(Images.background);
        background.setSize(900,800);
        background.setVisible(true);
        add(background);
//        add(new JLabel(new ImageIcon("C://Users//25739//Desktop//Java作业//开始界面.jpg")));
        //设置窗体基本属性大小 居中 边框隐藏 默认关闭按钮 logo图标
//        this.setSize(900,800);//大小
        this.setLocationRelativeTo(null);//居中
//        this.setUndecorated(true);//边框隐藏
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//默认关闭
        this.setIconImage(Images.paint.getImage());//logo
        this.setVisible(true);
//        setVisible(true);

//        //2.2
//        start = new JLabel(new ImageIcon("C://Users//25739//Desktop//Java作业//双人对战.png"));//ImageIcon:图标
//        start.setBounds(350,320,240,70);
//        start.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                start.setIcon(new ImageIcon("C://Users//25739//Desktop//Java作业//双人对战_副本.png"));
//            }
//        });
//        start.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseExited(MouseEvent e) {
//                start.setIcon(new ImageIcon("C://Users//25739//Desktop//Java作业//双人对战.png"));
//
//            }
//        });
//        start.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                dispose();
//                new ChessGameFrame(1000,760);
//            }
//        });
//        this.add(start);



//        help = new JLabel(new ImageIcon("C://Users//25739//Desktop//Java作业//人机对战.png"));
//        help.setBounds(350,420,240,70);
//        help.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                help.setIcon(new ImageIcon("C://Users//25739//Desktop//Java作业//人机对战_副本.png"));
//            }
//        });
//        help.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseExited(MouseEvent e) {
//                help.setIcon(new ImageIcon("C://Users//25739//Desktop//Java作业//人机对战.png"));
//
//            }
//        });
//        help.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                dispose();
//                new ChessGameFrame(1000,760);
//            }
//        });
//        this.add(help);



//        exit = new JLabel(new ImageIcon("C://Users//25739//Desktop//Java作业//退出游戏.png"));
//        exit.setBounds(350, 520, 240, 70);
//        exit.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                exit.setIcon(new ImageIcon("C://Users//25739//Desktop//Java作业//退出游戏_副本.png"));
//            }
//        });
//        exit.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseExited(MouseEvent e) {
//                exit.setIcon(new ImageIcon("C://Users//25739//Desktop//Java作业//退出游戏.png"));
//
//            }
//        });
//        exit.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                dispose();
//                System.exit(0);
//            }
//        });
//        this.add(exit);
//        File file = new File("saves");
//        String [] items=file.list();
//        JComboBox<String> comboBox=new JComboBox<>(items);
//        comboBox.setBounds(350,295,200,30);
//        add(comboBox);


    }
    private void one(){
        one=new JButton();
//        one.setIcon(new ImageIcon("C://Users//25739//Desktop//Java作业//双人对战.png"));
        one.setIcon(Images.two);
        one.setBorder(null);
        one.setBounds(350,320,240,70);
        one.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                one.setIcon(Images.two2);
            }
        });
        one.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                one.setIcon(Images.two);

            }
        });
        one.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ChessGameFrame(1000,760);
                CurrentPlayer1 a=new CurrentPlayer1();
                dispose();
            }
        });
        add(one);

    }


    private void exit(){
        exit=new JButton();
        exit.setIcon(Images.exit);
        exit.setBorder(null);
        exit.setBounds(350,420,240,70);
        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exit.setIcon(Images.exit2);
            }
        });
        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                exit.setIcon(Images.exit);

            }
        });
        exit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                System.exit(0);
            }
        });
        add(exit);

    }
}
