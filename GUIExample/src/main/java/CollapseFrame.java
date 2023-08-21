import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CollapseFrame implements ActionListener {


    JFrame frame = new JFrame();
    JButton collapseButt = new JButton();
    TextArea surName = new TextArea();
    TextArea name = new TextArea();
    TextArea lastName = new TextArea();

    CollapseFrame(){

        //COMPONENTS

        surName.setFont(new Font("Consolas", Font.BOLD, 25));
        name.setFont(new Font("Consolas", Font.BOLD, 25));
        lastName.setFont(new Font("Consolas", Font.BOLD, 25));


        collapseButt.setBounds(240,515,120,40);
        collapseButt.setText("Collapse");
        collapseButt.addActionListener(this);

        JLabel surNameLabel = new JLabel("Your surname");
        surNameLabel.setBounds(250,80,200,50);

        JLabel nameLabel = new JLabel("Your name");
        nameLabel.setBounds(250,210,200,50);

        JLabel lastNameLabel = new JLabel("Your lastname");
        lastNameLabel.setBounds(250,320,200,50);


        //SIZE PARAMS

        surName.setBounds(100,120,400,50);
        name.setBounds(100,250,400,50);
        lastName.setBounds(100,370,400,50);



        // Main Frame


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(600,600);
        frame.add(collapseButt);
        frame.add(surName);
        frame.add(name);
        frame.add(lastName);
        frame.add(nameLabel);
        frame.add(surNameLabel);
        frame.add(lastNameLabel);
        frame.setVisible(true);
        frame.setResizable(false);
        Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int iCoordX = (objDimension.width - frame.getWidth()) / 2;
        int iCoordY = (objDimension.height - frame.getHeight()) / 2;
        frame.setLocation(iCoordX, iCoordY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(!name.getText().isEmpty() && !surName.getText().isEmpty()){
            if (e.getSource() == collapseButt) {
                frame.dispose();
                new ExpandFrame(surName.getText() + " " + name.getText() + " " + lastName.getText());

            }
        }else {
            JOptionPane.showMessageDialog(null,
                    "You enter uncorect values",
                    "values problem",
                    JOptionPane.PLAIN_MESSAGE);
            frame.dispose();
            new CollapseFrame();
        }



    }
}
