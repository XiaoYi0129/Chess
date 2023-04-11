package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrawPanel extends JFrame{

    public JButton b;

    public DrawPanel(){
        setLayout(new FlowLayout());
        add(new JLabel("和棋"));
        b=new JButton("开始新游戏");
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
            //这里写开始新游戏
            new ChessGameFrame(1000,760);
            close();//暂时
        }
    }
}
