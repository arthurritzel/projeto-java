import model.dao.DaoFactory;
import model.entities.Empresa;
import model.dao.EmpresaDao;

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
    private DefaultListModel<Empresa> listModel;
    
    public Empresacamp(){
        listModel = new DefaultListModel<>();
        empresaList.setModel(listModel);

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                MainUi main = new MainUi();
                main.setContentPane(main.getMainPanel());
                main.setSize(500, 500);
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

    }

    private void loadEmpresaList(){
        EmpresaDao empresaDao = DaoFactory.createEmpresaDao();
        List<Empresa> empresas = empresaDao.findAll();

        listModel.clear();
        for (Empresa empresa : empresas){
            listModel.addElement(empresa);
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
