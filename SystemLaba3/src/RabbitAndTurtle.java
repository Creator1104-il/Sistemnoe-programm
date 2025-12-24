public class RabbitAndTurtle {
    public static void main(String[] args) throws InterruptedException {
        AnimalThread rabbit = new AnimalThread("Кролик", Thread.NORM_PRIORITY + 2);
        AnimalThread turtle = new AnimalThread("Черепаха", Thread.NORM_PRIORITY - 2);

        rabbit.start();
        turtle.start();

        while (rabbit.isAlive() && turtle.isAlive()) {
            Thread.sleep(500);
            if (rabbit.getDistance() - turtle.getDistance() > 10) {
                turtle.setPriority(Thread.MAX_PRIORITY);
                rabbit.setPriority(Thread.MIN_PRIORITY);
                System.out.println(">> Изменение приоритетов: Черепаха догоняет!");
            } else if (turtle.getDistance() - rabbit.getDistance() > 10) {
                rabbit.setPriority(Thread.MAX_PRIORITY);
                turtle.setPriority(Thread.MIN_PRIORITY);
                System.out.println(">> Изменение приоритетов: Кролик догоняет!");
            }
        }

        rabbit.join();
        turtle.join();
        System.out.println("Гонка завершена!");
    }
}