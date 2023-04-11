package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import controller.*;
import model.*;


public class KingBeenAttack extends  JFrame {
    public JButton b;
    public KingBeenAttack(ChessColor chessColor){
        setLayout(new FlowLayout());
        if(chessColor.equals(ChessColor.BLACK)){
            add(new JLabel("黑王正在被将军"));
        }else{
            add(new JLabel("白王正在被将军"));
        }
        b=new JButton("知道了");
        add(b);
        b.addActionListener(new ClickListener());

        setSize(300,200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setVisible(true);

    }

    public void close(){
        dispose();
    }

    public class ClickListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            close();
        }

    }
}
