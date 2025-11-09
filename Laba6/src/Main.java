import java.util.concurrent.atomic.AtomicInteger;
public class Main {
    private static final int MAX_MESSAGES = 10;
    private static final AtomicInteger messageCount = new AtomicInteger(0);
    private static String lastMessage = "";

    public static void main(String[] args) throws InterruptedException {
        // Создаем потоки
        Thread chickenThread = new Thread(new ChickenRunnable());
        Thread eggThread = new Thread(new EggRunnable());

        System.out.println("Начинаем спор: Что появилось сначала?");
        System.out.println("======================================");

        // Запускаем потоки
        chickenThread.start();
        eggThread.start();

        // Ждем завершения потоков
        chickenThread.join();
        eggThread.join();

        // Определяем победителя
        System.out.println("======================================");
        System.out.println("Результат спора:");
        if ("Курица".equals(lastMessage)) {
            System.out.println("ПОБЕДИЛА КУРИЦА! ✓");
        } else if ("Яйцо".equals(lastMessage)) {
            System.out.println("ПОБЕДИЛО ЯЙЦО! ✓");
        }
    }
        static class ChickenRunnable implements Runnable {
            @Override
            public void run() {
                while (messageCount.get() < MAX_MESSAGES) {
                    synchronized (messageCount) {
                        if (messageCount.get() < MAX_MESSAGES) {
                            lastMessage = "Курица";
                            System.out.println("Курица");
                            messageCount.incrementAndGet();
                        }
                    }
                    try {
                        Thread.sleep(100); // Небольшая задержка для чередования
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }

static class EggRunnable implements Runnable {
    @Override
    public void run() {
        while (messageCount.get() < MAX_MESSAGES) {
            synchronized (messageCount) {
                if (messageCount.get() < MAX_MESSAGES) {
                    lastMessage = "Яйцо";
                    System.out.println("Яйцо");
                    messageCount.incrementAndGet();
                }
            }
            try {
                Thread.sleep(100); // Небольшая задержка для чередования
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
}
