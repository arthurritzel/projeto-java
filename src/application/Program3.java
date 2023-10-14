package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.dao.UsuarioDao;
import model.dao.impl.SellerDaoJDBC;
import model.dao.impl.UsuarioDaoJDBC;
import model.entities.Department;
import model.entities.Empresa;
import model.entities.Seller;
import model.entities.Usuario;

import java.text.SimpleDateFormat;
import java.text.ParseException;
public class Program3{

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        SellerDao sellerDao = DaoFactory.createSellerDao();
        UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();


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
                    Usuario usuario = usuarioDao.findById(usuarioId);
                    System.out.println(usuario);
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
                    List<Usuario> allUsuarios = usuarioDao.findAll();
                    for (Usuario obj : allUsuarios) {
                        System.out.println(obj.toString());
                    }
                    break;

                case 4:
                    System.out.println("=== Insert usuario ===");
                    Usuario usuario2 = new Usuario();
                    System.out.print("-Digite o nome: ");
                    usuario2.setNome(sc.next());
                    System.out.print("-Digite o cpf: ");
                    usuario2.setCpf(sc.next());
                    System.out.print("-Digite o id_empresa: ");

                    Empresa emp = new Empresa();
                    emp.setId(sc.nextInt());
                    usuario2.setEmpresa(emp);

                    usuarioDao.insert(usuario2);
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
