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
public class Empresacamp extends JFrame{
    private int ID_MOD = -1;
    private JPanel empresacamp;
    private JButton voltarButton;
    private JTextField nomeField;
    private JTextField cnpjField;
    private JLabel empresaLabel;
    private JButton inserirButton;
    private JButton atualizarButton;
    private JButton deletarButton;
    private JList<Empresa> empresaList;
    private JLabel nomeLabel;
    private JLabel cnpjLabel;
    private JButton visualizarUsuariosDaEmpresaButton;
    private JList<Usuario> usuarioList;
    private DefaultListModel<Empresa> listModel;
    private DefaultListModel<Usuario> listModelUsu;
    
    public Empresacamp(){
        listModel = new DefaultListModel<>();
        empresaList.setModel(listModel);

        listModelUsu = new DefaultListModel<>();
        usuarioList.setModel(listModelUsu);

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Empresacamp.this.dispose();
                MainUi main = new MainUi();
                main.setContentPane(main.getMainPanel());
                main.setSize(400, 700);
                main.setLocationRelativeTo(null);
                main.setVisible(true);
            }
        });

        inserirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String cnpj = cnpjField.getText();
                if (nome.isEmpty() || cnpj.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Preencha todos os campos antes de inserir uma empresa","Erro", JOptionPane.ERROR_MESSAGE);
                }else{
                    try{
                        EmpresaDao empresaDao = DaoFactory.createEmpresaDao();
                        Empresa emp = new Empresa();
                        emp.setNome(nome);
                        emp.setCnpj(cnpj);
                        empresaDao.insert(emp);
                        loadEmpresaList();

                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(null, "Erro ao inserir", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        loadEmpresaList();

        empresaList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println(e);
                if (empresaList.getSelectedValue() != null) {
                    ID_MOD = empresaList.getSelectedValue().getId();
                    nomeField.setText(empresaList.getSelectedValue().getNome());
                    cnpjField.setText(empresaList.getSelectedValue().getCnpj());

                }

            }
        });

        deletarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmpresaDao empresaDao = DaoFactory.createEmpresaDao();
                if(ID_MOD == -1){
                    JOptionPane.showMessageDialog(null, "Selecione o dado a ser deletado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }else{
                    int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza? Essa ação nao é reversível", "Erro", JOptionPane.OK_CANCEL_OPTION);
                    if(confirm == 0) {
                        empresaDao.deleteById(ID_MOD);
                        loadEmpresaList();
                    }
                    nomeField.setText("");
                    cnpjField.setText("");
                    ID_MOD = -1;
                }
            }
        });

        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ID_MOD == -1){
                    ID_MOD = -1;
                    JOptionPane.showMessageDialog(null, "Selecione o dado a ser deletado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }else{
                   String nome = nomeField.getText();
                   String cnpj = cnpjField.getText();
                    if (nome.isEmpty() || cnpj.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Preencha todos os campos antes de atualizar um usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }else{
                        EmpresaDao empresaDao = DaoFactory.createEmpresaDao();
                        Empresa empAtt = new Empresa();
                        empAtt.setId(ID_MOD);
                        empAtt.setNome(nome);
                        empAtt.setCnpj(cnpj);
                        empresaDao.update(empAtt);
                        loadEmpresaList();
                        nomeField.setText("");
                        cnpjField.setText("");
                        ID_MOD  = -1;
                    }
                }
            }
        });

        visualizarUsuariosDaEmpresaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();
                if(ID_MOD == -1){
                    JOptionPane.showMessageDialog(null, "Selecione a empresa.", "Erro", JOptionPane.ERROR_MESSAGE);
                }else{
                        List<Usuario> obj = usuarioDao.findByEmp(ID_MOD);
                    if(obj.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Empresa sem usuarios.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }else {
                        loadUsuarioEmpList(obj);
                    }

                    nomeField.setText("");
                    cnpjField.setText("");
                    ID_MOD = -1;
                }
            }

        });
    }

    private void loadEmpresaList(){
        EmpresaDao empresaDao = DaoFactory.createEmpresaDao();
        List<Empresa> empresas = empresaDao.findAll();

        listModel.clear();
        for (Empresa empresa : empresas){
            listModel.addElement(empresa);
        }
    }

    private void loadUsuarioEmpList(List<Usuario> obj) {
        listModelUsu.clear();
        for (Usuario usuario : obj){
            listModelUsu.addElement(usuario);
        }
    }

    public JPanel getEmpresacamp(){
        return empresacamp;
    }

    public void setEmpresacamp(JPanel empresacamp){
        this.empresacamp = empresacamp;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
