
import java.lang.ProcessBuilder;

public class process{
    static Process startProcess(String name){
        try {
            ProcessBuilder p = new ProcessBuilder();
            p.command(name);
            Process zadaca = p.start();
            System.out.println(name + " создан (PID: " + zadaca.pid() + ")" );  // System.out.println(name + " запущен (PID: " + process.pid() + ")");
            System.out.println(zadaca.info());
            zadaca.info();
            return zadaca;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }
    static void endProcess(Process name){
        try {
            name.destroy();
            if (name.isAlive()) {
                name.destroyForcibly();
            }
            System.out.println("Процесс уничтожен");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}