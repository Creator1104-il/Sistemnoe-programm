public class Main {
    public static void main(String[] args) {
// Создаем и запускаем 10 потоков
        for (int i = 0; i < 10; i++) {
            final int threadNumber = i; // Финальная переменная для номера потока
            new Thread(() -> {
                // Код, выполняемый в потоке
                System.out.println("Поток " + threadNumber + " запущен");

                try {
                    // Имитация работы потока (задержка 1-3 секунды)
                    Thread.sleep((long) (Math.random() * 2000 + 1000));
                } catch (InterruptedException e) {
                    System.err.println("Поток прерван: " + e.getMessage());
                }

                System.out.println("Поток " + threadNumber + " завершен");
            }).start();
        }

    }
}