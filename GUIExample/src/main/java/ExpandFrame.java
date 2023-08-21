import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpandFrame implements ActionListener {

    JButton expandButt = new JButton("Expand");
    JFrame frame = new JFrame();
    ExpandFrame(String res){

        expandButt.setBounds(240,515,120,40);
        expandButt.addActionListener(this);

        JLabel fullNameLabel = new JLabel("Your fullname");
        fullNameLabel.setFont(new Font("Consolas", Font.BOLD, 20));
        fullNameLabel.setBounds(200,80,200,50);
        JLabel fullName = new JLabel(res);
        fullName.setFont(new Font("Consolas", Font.BOLD, 30));
        fullName.setBounds(80,120,440,50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(600,600);
        frame.add(fullName);
        frame.add(fullNameLabel);
        frame.add(expandButt);
        frame.setVisible(true);
        frame.setResizable(false);
        Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int iCoordX = (objDimension.width - frame.getWidth()) / 2;
        int iCoordY = (objDimension.height - frame.getHeight()) / 2;
        frame.setLocation(iCoordX, iCoordY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==expandButt){
            new CollapseFrame();
        }
    }
}
