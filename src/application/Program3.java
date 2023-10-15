package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.PerguntasDao;
import model.dao.SellerDao;
import model.dao.UsuarioDao;
import model.dao.impl.SellerDaoJDBC;
import model.dao.impl.UsuarioDaoJDBC;
import model.entities.*;

import java.text.SimpleDateFormat;
import java.text.ParseException;
public class Program3{

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        SellerDao sellerDao = DaoFactory.createSellerDao();
        UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();
        PerguntasDao perguntasDao = DaoFactory.createPerguntasDao();



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
                    int perguntasId = sc.nextInt();
                    Perguntas perguntas = perguntasDao.findById(perguntasId);
                    System.out.println(perguntas);
                    break;

                case 2:
                    System.out.println("=== Find Sellers by Department ===");
                    System.out.print("Enter department ID: ");
                    int departmentId = sc.nextInt();
                    Department department = new Department(departmentId, null);
                    List<Seller> sellersByDepartment = sellerDao.findByDepartment(department);
                    for (Seller obj : sellersByDepartment) {
                        System.out.println(obj);
                    }
                    break;

                case 3:
                    System.out.println("=== Find All Usuarios ===");
                    List<Perguntas> allPerguntas = perguntasDao.findAll();
                    for (Perguntas obj : allPerguntas) {
                        System.out.println(obj.toString());
                    }
                    break;

                case 4:
                    System.out.println("=== Insert pergunta ===");
                    Perguntas perguntas2 = new Perguntas();
                    System.out.print("-Digite a pergunta: ");
                    perguntas2.setCabecalho(sc.next());
                    System.out.print("-Digite o tipo de prova : ");
                    perguntas2.setTipo_prova(sc.next());
                    System.out.println("Digite o nivel da pergunta : ");
                    perguntas2.setNivel(sc.nextInt());
                    perguntasDao.insert(perguntas2);
                    break;

                case 5:
                    System.out.println("=== Update Usuario ===");
                    Usuario usuarioUP = new Usuario();
                    System.out.print("-Digite o id para alterar: ");
                    usuarioUP.setId(sc.nextInt());
                    System.out.print("-Digite o nome: ");
                    usuarioUP.setNome(sc.next());
                    System.out.print("-Digite o cpf: ");
                    usuarioUP.setCpf(sc.next());
                    System.out.print("-Digite o id_empresa: ");
                    Empresa empUP = new Empresa();
                    empUP.setId(sc.nextInt());
                    usuarioUP.setEmpresa(empUP);
                    usuarioDao.update(usuarioUP);
                    break;

                case 6:
                    System.out.println("=== Delete Usuario ===");
                    System.out.print("-Digite o id para excluir: ");
                    int id = sc.nextInt();
                    usuarioDao.deleteById(id);
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
