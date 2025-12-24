class AnimalThread extends Thread {
    private String animalName;
    private int distance = 0;
    private static final int FINISH = 100;

    public AnimalThread(String name, int priority) {
        this.animalName = name;
        setName(name);
        setPriority(priority);
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public void run() {
        while (distance < FINISH) {
            distance++;
            System.out.println(animalName + " пробежал " + distance + " м. (приоритет: " + getPriority() + ")");
            try {
                Thread.sleep(500); // имитация времени бега
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(animalName + " финишировал!");
    }
}