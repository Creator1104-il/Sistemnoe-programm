public class Main {
    public static void main(String[] args) {
        System.out.println("Главный поток запущен");
        new NewThread(5); //1
        new NewThread(5);//2
        new NewThread(5);//3
        new NewThread(5);//4
        new NewThread(10);//5
        new NewThread(10);//6
        new NewThread(10);//7
        new NewThread(10);//8
        new NewThread(14);//9
        new NewThread(14);//10
    }
}