import model.dao.DaoFactory;
import model.dao.UsuarioDao;
import model.entities.Empresa;
import model.dao.EmpresaDao;
import model.entities.Usuario;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Usucamp extends JFrame {
    private int ID_MOD = -1;
    private JPanel usucamp;
    private JButton voltarButton;
    private JTextField nomeField;
    private JTextField cpfField;
    private JButton inserirButton;
    private JLabel empresa;
    private JList<Usuario> list1;
    private JButton atualizarButton;
    private JButton deletarButton;
    private JComboBox<Empresa> empresasDrop;

    private DefaultComboBoxModel<Empresa> dropModel;
    private DefaultListModel<Usuario> listModel;

    EmpresaDao empresaDao = DaoFactory.createEmpresaDao();
    List<Empresa> empresasDropList = empresaDao.findAll();

    private Empresa empresaNull = new Empresa(-1, "", "");

    public Usucamp() {
        listModel = new DefaultListModel<>(); // Inicialize o modelo da lista
        list1.setModel(listModel);

        dropModel = new DefaultComboBoxModel<>();
        empresasDrop.setModel(dropModel);

        // Botão "Voltar" - Ação para voltar à tela anterior
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usucamp.this.dispose();
//                setVisible(false); // Oculta a instância atual de Usucamp
                MainUi main = new MainUi(); // Crie uma instância de MainUi
                main.setContentPane(main.getMainPanel());
                main.setSize(400, 700);
                main.setLocationRelativeTo(null);
                main.setVisible(true);
            }
        });

        // Botão "Inserir" - Ação para inserir um novo usuário
        inserirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtenha os dados do formulário


                String nome = nomeField.getText();
                String cpf = cpfField.getText();

                if (nome.isEmpty() || cpf.isEmpty() || empresasDrop.getSelectedItem() == empresaNull) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos antes de inserir um usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        Empresa selectedEmpresa = (Empresa) empresasDrop.getSelectedItem();

                        UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();
                        Usuario usu = new Usuario();
                        usu.setNome(nome);
                        usu.setCpf(cpf);
                        Empresa newemp = new Empresa();
                        newemp.setId(selectedEmpresa.getId());
                        usu.setEmpresa(newemp);
                        usuarioDao.insert(usu);
                        loadUserList();

                        nomeField.setText("");
                        cpfField.setText("");
                        empresasDrop.setSelectedItem(empresaNull);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "O campo Empresa deve ser um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Carregar a lista de usuários
        loadUserList();

        // Quando um item da lista é selecionado
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (list1.getSelectedValue() != null) {
                    ID_MOD = list1.getSelectedValue().getId();
                    nomeField.setText(list1.getSelectedValue().getNome());
                    cpfField.setText(list1.getSelectedValue().getCpf());
                    System.out.println(list1.getSelectedValue().getEmpresa());

                    for (Empresa empresaDrop : empresasDropList) {
                        if(empresaDrop.getId() == list1.getSelectedValue().getEmpresa().getId()) {
                            empresasDrop.setSelectedItem(empresaDrop);
                        }

                    }
                }
            }
        });

        // Botão "Deletar" - Ação para deletar um usuário selecionado
        deletarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();
                if (ID_MOD == -1) {
                    JOptionPane.showMessageDialog(null, "Selecione o dado a ser deletado.", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza? Essa ação não será reversível", "Erro", JOptionPane.OK_CANCEL_OPTION);
                    if (confirm == 0) {
                        usuarioDao.deleteById(ID_MOD);
                        loadUserList();
                    }
                    nomeField.setText("");
                    cpfField.setText("");
                    empresasDrop.setSelectedItem(empresaNull);
                    ID_MOD = -1;
                }
            }
        });

        // Botão "Atualizar" - Ação para atualizar um usuário selecionado
        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ID_MOD == -1) {
                    JOptionPane.showMessageDialog(null, "Selecione o dado a ser atualizado.", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Obtenha os dados do formulário
                    String nome = nomeField.getText();
                    String cpf = cpfField.getText();

                    if (nome.isEmpty() || cpf.isEmpty() || empresasDrop.getSelectedItem() == empresaNull) {
                        JOptionPane.showMessageDialog(null, "Preencha todos os campos antes de atualizar um usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
                    } else {
                        Empresa selectedEmpresa = (Empresa) empresasDrop.getSelectedItem();
                        System.out.println(selectedEmpresa.getId());
                        UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();
                        Usuario usuAtt = new Usuario();
                        usuAtt.setId(ID_MOD);
                        usuAtt.setNome(nome);
                        usuAtt.setCpf(cpf);
                        Empresa newemp = new Empresa();
                        newemp.setId(selectedEmpresa.getId());
                        usuAtt.setEmpresa(newemp);
                        usuarioDao.update(usuAtt);
                        loadUserList();
                        nomeField.setText("");
                        cpfField.setText("");
                        empresasDrop.setSelectedItem(empresaNull);
                        ID_MOD = -1;
                    }
                }
            }
        });
        loadEmpresas();
    }

    // Método para carregar a lista de usuários
    private void loadUserList() {
        UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();
        List<Usuario> usuarios = usuarioDao.findAll();

        listModel.clear();
        for (Usuario usuario : usuarios) {
            listModel.addElement(usuario);

        }
    }

    private void loadEmpresas() {
        dropModel.addElement(empresaNull);
        for (Empresa empresaDrop : empresasDropList) {
            dropModel.addElement(empresaDrop);
        }
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
