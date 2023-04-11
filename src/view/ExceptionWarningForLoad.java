package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExceptionWarningForLoad extends JFrame {

    public JButton b;

    public ExceptionWarningForLoad(String exception){
        setLayout(new FlowLayout());
        add(new JLabel(exception));
        b=new JButton("重新输入");
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
