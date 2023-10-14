import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainUi extends JFrame{
    private JPanel mainPanel;
    private JButton USUARIOButton;
    private JButton button2;

    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JLabel img;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public MainUi() {
        img.setIcon(new javax.swing.ImageIcon("./src/logo_web2 1.png"));
        USUARIOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainUi.this.dispose();
//                setVisible(false); //
                Usucamp usu = new Usucamp();
                usu.setContentPane(usu.getUsucamp());
                usu.setSize(600, 500);
                usu.setLocationRelativeTo(null);
                usu.setVisible(true);
            }
        });
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainUi.this.dispose();

                Empresacamp emp = new Empresacamp();
                emp.setContentPane(emp.getEmpresacamp());
                emp.setSize(600, 500);
                emp.setLocationRelativeTo(null);
                emp.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        MainUi main = new MainUi();
        main.setContentPane(main.mainPanel);
        main.setSize(400, 700);
        main.setLocationRelativeTo(null);
        main.setVisible(true);

    }
}
