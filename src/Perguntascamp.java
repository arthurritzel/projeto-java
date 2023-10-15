import model.dao.DaoFactory;
import model.dao.PerguntasDao;
import model.entities.Perguntas;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Perguntascamp extends JFrame{
    private JTextField nivelField;
    private JButton voltarButton;
    private JTextField cabrcalhoField;
    private JTextField tipoField;
    private JButton inserirButton;
    private JButton deletarButton;
    private JButton atualizarButton;
    private JList<Perguntas> perguntasList;
    private JPanel perguntasPane;

    private DefaultListModel<Perguntas> listModel;

    private int ID_MOD = -1;

    public Perguntascamp(){
        listModel = new DefaultListModel<>();
        perguntasList.setModel(listModel);

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Perguntascamp.this.dispose();
                MainUi main = new MainUi();
                main.setContentPane(main.getMainPanel());
                main.setSize(400, 700);
                main.setLocationRelativeTo(null);
                main.setVisible(true);
            }
        });

        loadPerguntasList();
        inserirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cabecalho = cabrcalhoField.getText();
                String tipo = tipoField.getText();
                String nivel = nivelField.getText();
                if (cabecalho.isEmpty() || tipo.isEmpty() || nivel.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Preencha todos os campos antes de inserir uma empresa","Erro", JOptionPane.ERROR_MESSAGE);
                }else{
                    try{
                        PerguntasDao perguntasDao = DaoFactory.createPerguntasDao();
                        Perguntas perInsert = new Perguntas();
                        perInsert.setCabecalho(cabecalho);
                        perInsert.setTipo_prova(tipo);
                        int nivelInt = Integer.parseInt(nivel);
                        perInsert.setNivel(nivelInt);

                        perguntasDao.insert(perInsert);
                        loadPerguntasList();

                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(null, "Erro ao inserir", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
                cabrcalhoField.setText("");
                tipoField.setText("");
                nivelField.setText("");
            }
        });
        perguntasList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (perguntasList.getSelectedValue() != null) {
                    ID_MOD = perguntasList.getSelectedValue().getId();
                    cabrcalhoField.setText(perguntasList.getSelectedValue().getCabecalho());
                    tipoField.setText(perguntasList.getSelectedValue().getTipo_prova());
                    nivelField.setText(Integer.toString(perguntasList.getSelectedValue().getNivel()));
                }
            }
        });
        deletarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PerguntasDao perguntasDao = DaoFactory.createPerguntasDao();
                if(ID_MOD == -1){
                    JOptionPane.showMessageDialog(null, "Selecione o dado a ser deletado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }else{
                    int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza? Essa ação nao é reversível", "Erro", JOptionPane.OK_CANCEL_OPTION);
                    if(confirm == 0) {
                        perguntasDao.deleteById(ID_MOD);
                        loadPerguntasList();
                    }
                    cabrcalhoField.setText("");
                    tipoField.setText("");
                    nivelField.setText("");
                    ID_MOD = -1;
                }
            }
        });
        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ID_MOD == -1){
                    JOptionPane.showMessageDialog(null, "Selecione o dado a ser deletado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }else{
                    String cabecalho = cabrcalhoField.getText();
                    String tipo = tipoField.getText();
                    String nivel = nivelField.getText();
                    if (cabecalho.isEmpty() || tipo.isEmpty() || nivel.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Preencha todos os campos antes de atualizar um usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }else{
                        try{
                            PerguntasDao perguntasDao = DaoFactory.createPerguntasDao();
                            Perguntas perInsert = new Perguntas();
                            perInsert.setId(ID_MOD);
                            perInsert.setCabecalho(cabecalho);
                            perInsert.setTipo_prova(tipo);
                            int nivelInt = Integer.parseInt(nivel);
                            perInsert.setNivel(nivelInt);

                            perguntasDao.update(perInsert);
                            loadPerguntasList();

                        }catch (Exception ex){
                            JOptionPane.showMessageDialog(null, "Erro ao inserir", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    cabrcalhoField.setText("");
                    tipoField.setText("");
                    nivelField.setText("");
                    ID_MOD = -1;
                }
            }
        });
    }

    private void loadPerguntasList(){
        PerguntasDao perguntasDao = DaoFactory.createPerguntasDao();
        List<Perguntas> perguntas = perguntasDao.findAll();

        listModel.clear();
        for (Perguntas perguntas2 : perguntas){
            listModel.addElement(perguntas2);
        }
    }

    public JPanel getPerguntasPane() {
        return perguntasPane;
    }

    public void setPerguntasPane(JPanel perguntasPane) {
        this.perguntasPane = perguntasPane;
    }
}
