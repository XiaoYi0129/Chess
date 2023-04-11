package view;

import javax.swing.*;
import java.awt.*;

public class CurrentPlayer1 extends JFrame {
    public CurrentPlayer1(){
        Frame f =new Frame("打开窗口");
        f.setTitle("该局走棋方");
        f.setSize(200,100);
        f.setLocation(1000,300);
        Label l=new Label("WHITE");
        f.setLayout(new FlowLayout());
        f.add(l);
        setDefaultCloseOperation(3);
        f.setVisible(true);

    }
}
class CurrentPlayer2 extends JFrame {
    public CurrentPlayer2(){
        Frame f =new Frame("打开窗口");
        f.setTitle("该局走棋方");
        f.setSize(200,100);
        f.setLocation(1000,300);
        Label l=new Label("BLACK");
        f.setLayout(new FlowLayout());
        setDefaultCloseOperation(3);
        f.add(l);
        f.setVisible(true);

    }
}