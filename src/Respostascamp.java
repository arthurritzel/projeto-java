import model.dao.*;
import model.entities.Perguntas;
import model.entities.Respostas;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Respostascamp extends JFrame{
    private JTextField textoField;
    private JButton voltarButton;
    private JRadioButton corretaRadioButton;
    private JComboBox<Perguntas> perguntasDrop;
    private JButton inserirButton;
    private JButton deletarButton;
    private JButton atualizarButton1;
    private JList<Respostas> respotasList;
    private JPanel respostasPanel;

    private DefaultComboBoxModel<Perguntas> dropModel;
    private DefaultListModel<Respostas> listModel;
    private int ID_MOD = -1;

    PerguntasDao perguntasDao = DaoFactory.createPerguntasDao();
    List<Perguntas> perguntasDropList = perguntasDao.findAll();

    private Perguntas perguntaNull = new Perguntas(-1, "", "", 0);

    public Respostascamp(){
        listModel = new DefaultListModel<>(); // Inicialize o modelo da lista
        respotasList.setModel(listModel);

        dropModel = new DefaultComboBoxModel<>();
        perguntasDrop.setModel(dropModel);
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Respostascamp.this.dispose();

                MainUi main = new MainUi();
                main.setContentPane(main.getMainPanel());
                main.setSize(400, 700);
                main.setLocationRelativeTo(null);
                main.setVisible(true);
            }
        });

        loadRespostasList();
        inserirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String texto = textoField.getText();
                Boolean correta = corretaRadioButton.isSelected();


                if (texto.isEmpty() || perguntasDrop.getSelectedItem() == perguntaNull) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos antes de inserir um usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        Perguntas selectedPergunta = (Perguntas) perguntasDrop.getSelectedItem();

                        RespostasDao respostasDao2 = DaoFactory.createRespostasDao();
                        Respostas res = new Respostas();
                        res.setTexto(texto);
                        res.setCorreta(correta);
                        Perguntas newper = new Perguntas();
                        newper.setId(selectedPergunta.getId());
                        res.setPergunta(newper);
                        respostasDao2.insert(res);

                        loadRespostasList();

                        textoField.setText("");
                        corretaRadioButton.setSelected(false);
                        perguntasDrop.setSelectedItem(perguntaNull);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "O campo Empresa deve ser um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        loadPerguntas();
        respotasList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (respotasList.getSelectedValue() != null) {
                    ID_MOD = respotasList.getSelectedValue().getId();
                    textoField.setText(respotasList.getSelectedValue().getTexto());
                    corretaRadioButton.setSelected(respotasList.getSelectedValue().getCorreta());


                    for (Perguntas perguntaDrop : perguntasDropList) {
                        if(perguntaDrop.getId() == respotasList.getSelectedValue().getPergunta().getId()) {
                            perguntasDrop.setSelectedItem(perguntaDrop);
                        }

                    }
                }
            }
        });
        atualizarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = textoField.getText();
                Boolean correta = corretaRadioButton.isSelected();


                if (texto.isEmpty() || perguntasDrop.getSelectedItem() == perguntaNull) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos antes de inserir um usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        Perguntas selectedPergunta = (Perguntas) perguntasDrop.getSelectedItem();

                        RespostasDao respostasDao2 = DaoFactory.createRespostasDao();
                        Respostas res = new Respostas();
                        res.setId(ID_MOD);
                        res.setTexto(texto);
                        res.setCorreta(correta);
                        Perguntas newper = new Perguntas();
                        newper.setId(selectedPergunta.getId());
                        res.setPergunta(newper);
                        respostasDao2.update(res);

                        loadRespostasList();

                        textoField.setText("");
                        corretaRadioButton.setSelected(false);
                        perguntasDrop.setSelectedItem(perguntaNull);
                        ID_MOD = -1;

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "O campo Empresa deve ser um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        deletarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RespostasDao respostasDao = DaoFactory.createRespostasDao();
                if (ID_MOD == -1) {
                    JOptionPane.showMessageDialog(null, "Selecione o dado a ser deletado.", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza? Essa ação não será reversível", "Erro", JOptionPane.OK_CANCEL_OPTION);
                    if (confirm == 0) {
                        respostasDao.deleteById(ID_MOD);
                        loadRespostasList();
                    }
                    textoField.setText("");
                    corretaRadioButton.setSelected(false);
                    perguntasDrop.setSelectedItem(perguntaNull);
                    ID_MOD = -1;
                }
            }
        });
    }

    private void loadRespostasList() {
        RespostasDao respostasDao = DaoFactory.createRespostasDao();
        List<Respostas> respostas = respostasDao.findAll();

        listModel.clear();
        for (Respostas resposta : respostas) {
            listModel.addElement(resposta);

        }
    }

    private void loadPerguntas() {
        dropModel.addElement(perguntaNull);
        for (Perguntas perguntasDrop : perguntasDropList) {
            dropModel.addElement(perguntasDrop);
        }
    }

    public JPanel getRespostasPanel() {
        return respostasPanel;
    }

    public void setRespostasPanel(JPanel respostasPanel) {
        this.respostasPanel = respostasPanel;
    }
}
