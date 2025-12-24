import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        List<Process> processes = new ArrayList<>();
        List<String> processesNames = new ArrayList<>();
        for(int i = 0; i<3; i++) {
            System.out.println("ВВедите название процесса, который хотите запустить, например: notepad.exe, calc.exep или mspaint.exe");
            String name = in.nextLine();
            Process a = process.startProcess(name);
            if (a == null) {
                i--;
                System.out.println("Не удалось создать процесс: " + name + ", попробуйте заново");
            } else {
                processes.add(a);
                processesNames.add(name);
            }
        }
        for(int i = 0; i<3;i++){
            PrintProcessNames(processesNames);
            System.out.println("Если хотите закрыть процесс, введите его номер 1/2/3, введите 4 если нихотите ничего закрывать");
            byte vote = in.nextByte();
            switch (vote){
                case 1, 2, 3:
                    try {
                        process.endProcess(processes.get(vote - 1));
                        processes.remove(vote -1);
                        processesNames.remove(vote -1);
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        i--;
                    }
                    break;
                case 4: i = 3; break;
                default: System.out.println("Попробуйте еще раз!"); i--;
            }
        }

    }

    static void PrintProcessNames(List<String> list){
        System.out.print("Текущие процессы: ");
        for(int i = 0; i< list.size(); i++){
            System.out.print(list.get(i) + " ");
        }
        System.out.println();
    }
}