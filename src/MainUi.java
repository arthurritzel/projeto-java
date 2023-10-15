import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainUi extends JFrame{
    private JPanel mainPanel;
    private JButton USUARIOButton;
    private JButton button2;

    private JButton perguntaButton;
    private JButton empresaButton;
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
        empresaButton.addActionListener(new ActionListener() {
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
        perguntaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainUi.this.dispose();

                Perguntascamp perguntas = new Perguntascamp();
                perguntas.setContentPane(perguntas.getPerguntasPane());
                perguntas.setSize(600, 500);
                perguntas.setLocationRelativeTo(null);
                perguntas.setVisible(true);
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
