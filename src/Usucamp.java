import model.dao.DaoFactory;
import model.dao.UsuarioDao;
import model.entities.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Usucamp extends JFrame{
    private JPanel usucamp;
    private JButton voltarButton;
    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField empresaField;
    private JButton inserirButton;
    private JLabel empresa;


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
        inserirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();
                Usuario usu = new Usuario();
                usu.setNome(nomeField.getText());
                usu.setCpf(cpfField.getText());
                try {
                    Integer id = Integer.parseInt(empresaField.getText());
                    usu.setId_empresa(id);
                    usuarioDao.insert(usu);
                } catch (NumberFormatException ex) {
                    // Trate o caso em que o texto não é um número válido.
                    JOptionPane.showMessageDialog(null, "O campo Empresa deve ser um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    public JPanel getUsucamp() {
        return usucamp;
    }

    public void setUsucamp(JPanel usucamp) {
        this.usucamp = usucamp;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
