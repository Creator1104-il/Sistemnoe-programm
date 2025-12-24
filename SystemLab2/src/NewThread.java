

public class NewThread implements Runnable {
    //Ссылка на объект потока
    Thread thread;
    int end;
    public NewThread(int i) {
        this.end = i;
        // Создание объекта потока
        thread = new Thread(this, "Новый поток");
        System.out.println(thread);
        // Запуск потока
        thread.start();
    }

    @Override
    public void run() {
        System.out.println("Дочерний поток запущен");
        for (int i = 0; i < this.end; i++) {
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Дочерний поток прерван");
            }
        }
        System.out.println("Дочерний поток завершен");
    }
}
