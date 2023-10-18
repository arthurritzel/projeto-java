package application;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import model.dao.AgendamentoDao;
import model.dao.DaoFactory;
import model.dao.RespostasDao;
import model.dao.UsuarioDao;
import model.entities.Agendamento;
import model.entities.Empresa;
import model.entities.Respostas;
import model.entities.Usuario;

public class Program3{

    public static void main(String[] args) throws SQLException {

        Scanner sc = new Scanner(System.in);


        UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();
        AgendamentoDao agendaDao = DaoFactory.createAgendaDao();
        RespostasDao respostasDao = DaoFactory.createRespostasDao();

        int choice = 0;
        while (choice != 7) {
            System.out.println("=== CRUD Menu ===");
            System.out.println("1. Find Seller by ID");
            System.out.println("2. Find Sellers by Department");
            System.out.println("3. Find All Usuarios");
            System.out.println("4. Insert Seller");
            System.out.println("5. Update Seller");
            System.out.println("6. Delete Seller");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("=== Find usuario by ID ===");
                    System.out.print("Enter seller ID: ");
                    int usuarioId = sc.nextInt();
                    Agendamento agend1 = agendaDao.findById(usuarioId);
                    System.out.println(agend1);
                    break;

                case 2:
                    System.out.println("=== Find Sellers by Department ===");
                    System.out.print("Enter department ID: ");
                    int departmentId = sc.nextInt();

                    break;

                case 3:
                    System.out.println("=== Find All Usuarios ===");
                    List<Respostas> allAgendamento = respostasDao.findAll();
                    for (Respostas obj : allAgendamento) {
                        System.out.println(obj.toString());
                    }
                    break;

                case 4:
                    System.out.println("=== Insert usuario ===");
                    Agendamento agend = new Agendamento();
                    System.out.print("-Digite o nome: ");
                    agend.setDia(sc.next());
                    System.out.print("-Digite o cpf: ");
                    agend.setHora(sc.next());
                    agend.setFeito(true);
                    System.out.print("-Digite o id_usuario: ");

                    Usuario usu = new Usuario();
                    usu.setId(sc.nextInt());
                    agend.setUsuario(usu);

                    agendaDao.insert(agend);
                    break;

                case 5:
                    System.out.println("=== Update Usuario ===");
                    Agendamento agend2 = new Agendamento();
                    System.out.println("Digite o id para alterar");
                    agend2.setId(sc.nextInt());
                    System.out.print("-Digite o nome: ");
                    agend2.setDia(sc.next());
                    System.out.print("-Digite o cpf: ");
                    agend2.setHora(sc.next());
                    agend2.setFeito(true);
                    System.out.print("-Digite o id_usuario: ");

                    Usuario usu2 = new Usuario();
                    usu2.setId(sc.nextInt());
                    agend2.setUsuario(usu2);

                    agendaDao.update(agend2);
                    break;

                case 6:
                    System.out.println("=== Delete Usuario ===");
                    System.out.print("-Digite o id para excluir: ");
                    int id = sc.nextInt();
                    agendaDao.deleteById(id);
                    break;

                case 7:
                    System.out.println("Exiting the program.");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }

        sc.close();
    }


}
