import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите имя пса: ");
        String name = in.nextLine();
        try {
            pes.checkName(name);
            System.out.println("Все ок");
        } catch (name e){
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
