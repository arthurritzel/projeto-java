import model.dao.AgendamentoDao;
import model.dao.DaoFactory;
import model.dao.UsuarioDao;
import model.entities.Agendamento;
import model.entities.Empresa;
import model.entities.Usuario;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class Agendamentocamp extends JFrame{
    private JButton voltarButton;
    private JRadioButton feitoRadioButton;
    private JComboBox<Usuario> usuarioDrop;
    private JButton inserirButton;
    private JButton deletarButton;
    private JButton atualizarButton;
    private JTextField horaField;
    private JList<Agendamento> agendamentoList;

    private JPanel agendamentoPanel;
    private JTextField diaField;

    private int ID_MOD = -1;

    private DefaultListModel<Agendamento> listModel;

    private DefaultComboBoxModel<Usuario> dropModel;

    UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();
    List<Usuario> usuariosDropList = usuarioDao.findAll();

    Empresa emp = new Empresa(-1, "", "");
    private Usuario usuarioNull = new Usuario(-1, "", "", emp);
    Agendamentocamp(){
        listModel = new DefaultListModel<>(); // Inicialize o modelo da lista
        agendamentoList.setModel(listModel);

        dropModel = new DefaultComboBoxModel<>();
        usuarioDrop.setModel(dropModel);
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Agendamentocamp.this.dispose();
//                setVisible(false); // Oculta a instância atual de Usucamp
                MainUi main = new MainUi(); // Crie uma instância de MainUi
                main.setContentPane(main.getMainPanel());
                main.setSize(400, 700);
                main.setLocationRelativeTo(null);
                main.setVisible(true);
            }
        });

        inserirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dia = diaField.getText();
                String hora = horaField.getText();
                boolean feito = feitoRadioButton.isSelected();

                if (dia.isEmpty() || hora.isEmpty() || usuarioDrop.getSelectedItem() == usuarioNull) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos antes de inserir um usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        Usuario selectedUsuario = (Usuario) usuarioDrop.getSelectedItem();

                        AgendamentoDao agendamentoDao = DaoFactory.createAgendaDao();

                        Agendamento agendInsert = new Agendamento();
                        agendInsert.setDia(dia);
                        agendInsert.setHora(hora);
                        agendInsert.setFeito(feito);

                        Usuario usu = new Usuario();
                        usu.setId(selectedUsuario.getId());
                        agendInsert.setUsuario(usu);
                        agendamentoDao.insert(agendInsert);
                        loadAgendList();

                        diaField.setText("");
                        horaField.setText("");
                        feitoRadioButton.setSelected(false);
                        usuarioDrop.setSelectedItem(usuarioNull);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "O campo Empresa deve ser um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

        });

        loadAgendList();
        loadUsuarios();
        agendamentoList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (agendamentoList.getSelectedValue() != null) {
                    ID_MOD = agendamentoList.getSelectedValue().getId();
                    diaField.setText(agendamentoList.getSelectedValue().getDia());
                    horaField.setText(agendamentoList.getSelectedValue().getHora());
                    feitoRadioButton.setSelected(agendamentoList.getSelectedValue().isFeito());

                    for (Usuario usuariosDrop : usuariosDropList) {
                        if(usuariosDrop.getId() == agendamentoList.getSelectedValue().getUsuario().getId()) {
                            usuarioDrop.setSelectedItem(usuariosDrop);
                        }

                    }
                }
            }
        });
        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if (ID_MOD == -1) {
                    JOptionPane.showMessageDialog(null, "Selecione o dado a ser atualizado.", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    String dia = diaField.getText();
                    String hora = horaField.getText();
                    boolean feito = feitoRadioButton.isSelected();

                    if (dia.isEmpty() || hora.isEmpty() || usuarioDrop.getSelectedItem() == usuarioNull) {
                        JOptionPane.showMessageDialog(null, "Preencha todos os campos antes de atualizar um usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            Usuario selectedUsuario = (Usuario) usuarioDrop.getSelectedItem();

                            AgendamentoDao agendamentoDao = DaoFactory.createAgendaDao();

                            Agendamento agendInsert = new Agendamento();
                            agendInsert.setId(ID_MOD);
                            agendInsert.setDia(dia);
                            agendInsert.setHora(hora);
                            agendInsert.setFeito(feito);

                            Usuario usu = new Usuario();
                            usu.setId(selectedUsuario.getId());
                            agendInsert.setUsuario(usu);
                            agendamentoDao.update(agendInsert);
                            loadAgendList();

                            diaField.setText("");
                            horaField.setText("");
                            feitoRadioButton.setSelected(false);
                            usuarioDrop.setSelectedItem(usuarioNull);
                            ID_MOD = -1;

                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "O campo Empresa deve ser um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        deletarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgendamentoDao agendamentoDao = DaoFactory.createAgendaDao();
                if (ID_MOD == -1) {
                    JOptionPane.showMessageDialog(null, "Selecione o dado a ser deletado.", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza? Essa ação não será reversível", "Erro", JOptionPane.OK_CANCEL_OPTION);
                    if (confirm == 0) {
                        agendamentoDao.deleteById(ID_MOD);
                        loadAgendList();
                    }
                    diaField.setText("");
                    horaField.setText("");
                    feitoRadioButton.setSelected(false);
                    usuarioDrop.setSelectedItem(usuarioNull);
                    ID_MOD = -1;
                }
            }
        });
    }

    private void loadAgendList() {
        AgendamentoDao agendamentoDao = DaoFactory.createAgendaDao();
        List<Agendamento> agendamentos = agendamentoDao.findAll();

        listModel.clear();
        for (Agendamento agendamento : agendamentos) {
            listModel.addElement(agendamento);

        }
    }
    private void loadUsuarios() {
        dropModel.addElement(usuarioNull);
        for (Usuario usuarioDrop : usuariosDropList) {
            dropModel.addElement(usuarioDrop);
        }
    }

    public JPanel getAgendamentoPanel() {
        return agendamentoPanel;
    }

    public void setAgendamentoPanel(JPanel agendamentoPanel) {
        this.agendamentoPanel = agendamentoPanel;
    }
}
