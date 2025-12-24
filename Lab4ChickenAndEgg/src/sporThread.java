import java.util.Random;
public class sporThread extends Thread{
    private Thread thread;
    private String name;
    private sporObserver observer;
    sporThread(String name){
        this.name = name;
        this.thread = new Thread();
    }
    public void addObserver(sporObserver n){
        this.observer = n;
    }
    public void startIt(){
        this.start();
    }
    public void joinTo(sporThread e)throws InterruptedException {
            e.join();
    }
    public void win(){
        System.out.println("Спор окончен, победитель: " + this.name);
    }
    @Override
    public void run(){
        System.out.println(this.name);
        Random rn = new Random();
        try {
            for(int i = 0;i<4;i++){
                int time = rn.nextInt(1000, 1600);
                sleep(time);
                System.out.println(this.name);
            }
            if(observer!=null) {
                observer.notifyAboutEnd(this);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
