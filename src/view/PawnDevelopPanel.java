package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import controller.*;
import model.*;


public class PawnDevelopPanel extends JFrame {
    public JButton b1;
    public JButton b2;
    public JButton b3;
    private JButton b4;

   // private int returnChessComponent=0;//1后，2车，3象，4马
    public PawnDevelopPanel(Chessboard chessboard,ChessComponent chessComponent){
        setLayout((new GridLayout(2,2)));
        add(new JLabel("请选择兵升变对象"));
        b1=new JButton("后");
        b2=new JButton("车");
        b3=new JButton("象");
        b4=new JButton("马");
        add(b1);
        b1.addActionListener(new QueenClickListener(chessboard,chessComponent));

        add(b2);
        b2.addActionListener(new RookClickListener(chessboard,chessComponent));

        add(b3);
        b3.addActionListener(new BishopClickListener(chessboard,chessComponent));

        add(b4);
        b4.addActionListener(new KnightClickListener(chessboard,chessComponent));
        //setContentPane(panel);
        setSize(300,200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setVisible(true);

    }

    public void close(){
        dispose();
    }




    public class QueenClickListener implements ActionListener{

        ChessComponent chessComponent;
        Chessboard chessboard;
        public QueenClickListener(Chessboard chessboard,ChessComponent chessComponent){
            this.chessComponent=chessComponent;
            this.chessboard=chessboard;
        }

        @Override
        public void actionPerformed(ActionEvent e){
           chessboard.setQueen(chessComponent,chessComponent.getChessColor());
           close();
            //chessComponent.setPawnDevelop(1);
        //System.out.println("giao");//测试
            //setVisible(false);
            //dispose();
            //setDefaultCloseOperation(PawnDevelopPanel.DISPOSE_ON_CLOSE);


        }
    }

    public class RookClickListener implements ActionListener{
        ChessComponent chessComponent;
        Chessboard chessboard;
        public RookClickListener(Chessboard chessboard,ChessComponent chessComponent){
            this.chessComponent=chessComponent;
            this.chessboard=chessboard;
        }

        @Override
        public void actionPerformed(ActionEvent e){
           chessboard.setRook(chessComponent,chessComponent.getChessColor());
            close();
            // returnChessComponent=2;
            //chessComponent.setPawnDevelop(2);
            //setVisible(false);
           // dispose();
            //setDefaultCloseOperation(PawnDevelopPanel.DISPOSE_ON_CLOSE);//这个后面应该要去掉
        }
    }

    public class BishopClickListener implements ActionListener{
        ChessComponent chessComponent;
        Chessboard chessboard;
        public BishopClickListener(Chessboard chessboard,ChessComponent chessComponent){
            this.chessComponent=chessComponent;
            this.chessboard=chessboard;
        }


        @Override
        public void actionPerformed(ActionEvent e){
            chessboard.setBishop(chessComponent,chessComponent.getChessColor());
            close();
            //chessComponent.setPawnDevelop(3);
            // returnChessComponent=3;
            //setVisible(false);
            //dispose();//怎么关闭当前窗口
            //setDefaultCloseOperation(PawnDevelopPanel.DISPOSE_ON_CLOSE);//这个后面应该要去掉
        }
    }
    public class KnightClickListener implements ActionListener{
        ChessComponent chessComponent;
        Chessboard chessboard;
        public KnightClickListener(Chessboard chessboard,ChessComponent chessComponent){
            this.chessboard=chessboard;
            this.chessComponent=chessComponent;
        }
        @Override
        public void actionPerformed(ActionEvent e){
            chessboard.setKnight(chessComponent,chessComponent.getChessColor());
            close();
            ///chessComponent.setPawnDevelop(4);
            //returnChessComponent=4;
            //setVisible(false);
            //dispose();
            //setDefaultCloseOperation(PawnDevelopPanel.DISPOSE_ON_CLOSE);//这个后面应该要去掉
        }
    }

    /*public int getReturnChessComponent(){
        System.out.println("giao");
        return returnChessComponent;
    }*/



}
