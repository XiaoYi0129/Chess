package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import controller.*;
import model.*;

public class WinPanel extends JFrame {
    public JButton b;


    public WinPanel(ChessColor WinPalyer){
        setLayout(new FlowLayout());
        if(WinPalyer.equals(ChessColor.WHITE)){
            add(new JLabel("白方赢"));
        }else{
            add(new JLabel("黑方赢"));
        }
        b=new JButton("开始新游戏");
        add(b);
        b.addActionListener(new ClickListener());
        setSize(300,200);
        setLocation(500,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setVisible(true);
    }
    public void close(){
        dispose();
    }
    public class ClickListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            dispose();
            //这里写开始新游戏
            new ChessGameFrame(1000,760);
            close();//暂时
        }
    }

}
