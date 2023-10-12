import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Usucamp extends JFrame{
    private JPanel usucamp;
    private JButton voltarButton;


    public Usucamp() {
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false); // Oculta a instância atual de UsuarioForm
                MainUi usu = new MainUi(); // Passe a instância de UsuarioForm para Usucamp
                usu.setContentPane(usu.getMainPanel());
                usu.setSize(1000, 1000);
                usu.setVisible(true);
            }
        });
    }
    public JPanel getUsucamp() {
        return usucamp;
    }

    public void setUsucamp(JPanel usucamp) {
        this.usucamp = usucamp;
    }
}
