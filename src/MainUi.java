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

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public MainUi() {
        USUARIOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false); // Oculta a instância atual de UsuarioForm
                Usucamp usu = new Usucamp(); // Passe a instância de UsuarioForm para Usucamp
                usu.setContentPane(usu.getUsucamp());
                usu.setSize(500, 500);
                usu.setLocationRelativeTo(null);
                usu.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        MainUi main = new MainUi();
        main.setContentPane(main.mainPanel);
        main.setSize(500, 500);
        main.setLocationRelativeTo(null);
        main.setVisible(true);

    }
}
