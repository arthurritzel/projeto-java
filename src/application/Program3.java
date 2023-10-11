package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.dao.impl.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;
import java.text.SimpleDateFormat;
import java.text.ParseException;
public class Program3{

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        SellerDao sellerDao = DaoFactory.createSellerDao();

        int choice = 0;
        while (choice != 7) {
            System.out.println("=== CRUD Menu ===");
            System.out.println("1. Find Seller by ID");
            System.out.println("2. Find Sellers by Department");
            System.out.println("3. Find All Sellers");
            System.out.println("4. Insert Seller");
            System.out.println("5. Update Seller");
            System.out.println("6. Delete Seller");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("=== Find Seller by ID ===");
                    System.out.print("Enter seller ID: ");
                    int sellerId = sc.nextInt();
                    Seller seller = sellerDao.findById(sellerId);
                    System.out.println(seller);
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
                    System.out.println("=== Find All Sellers ===");
                    List<Seller> allSellers = sellerDao.findAll();
                    for (Seller obj : allSellers) {
                        System.out.println(obj);
                    }
                    break;

                case 4:
                    System.out.println("=== Insert Seller ===");
                    Seller seler = new Seller();
                    System.out.print("-Digite o nome: ");
                    seler.setName(sc.next());
                    System.out.print("-Digite o email: ");
                    seler.setEmail(sc.next());
                    System.out.print("-Digite a data de nascimento: ");
                    String dateStr = sc.next();
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        Date birthDate = formatter.parse(dateStr);
                        seler.setBirthDate(birthDate);
                    } catch (ParseException e) {
                        System.out.println("Erro ao analisar a data. Certifique-se de que está no formato correto (dd/MM/yyyy).");
                        continue; // Volte ao menu principal
                    }
                    System.out.print("-Digite o salario: ");
                    seler.setBaseSalary(sc.nextDouble());
                    System.out.print("-Digite o id do departamento: ");
                    Department dep = new Department(sc.nextInt(), "");
                    seler.setDepartment(dep);
                    sellerDao.insert(seler);
                    break;

                case 5:
                    System.out.println("=== Update Seller ===");
                    Seller seler2 = new Seller();
                    System.out.print("-Digite o id para alterar: ");
                    seler2.setId(sc.nextInt());
                    System.out.print("-Digite o nome: ");
                    seler2.setName(sc.next());
                    System.out.print("-Digite o email: ");
                    seler2.setEmail(sc.next());
                    System.out.print("-Digite a data de nascimento: ");
                    String dateStr2 = sc.next();
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        Date birthDate = formatter.parse(dateStr2);
                        seler2.setBirthDate(birthDate);
                    } catch (ParseException e) {
                        System.out.println("Erro ao analisar a data. Certifique-se de que está no formato correto (dd/MM/yyyy).");
                        continue; // Volte ao menu principal
                    }
                    System.out.print("-Digite o salario: ");
                    seler2.setBaseSalary(sc.nextDouble());
                    System.out.print("-Digite o id do departamento: ");
                    Department dep2 = new Department(sc.nextInt(), "");
                    seler2.setDepartment(dep2);
                    sellerDao.update(seler2);
                    break;

                case 6:
                    System.out.println("=== Delete Seller ===");
                    System.out.print("-Digite o id para excluir: ");
                    int id = sc.nextInt();
                    sellerDao.deleteById(id);
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
