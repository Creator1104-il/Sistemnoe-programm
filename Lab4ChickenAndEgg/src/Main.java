public class Main {
    public static void main(String[] args){
        sporThread egg = new sporThread("яичко");
        sporThread chicken = new sporThread("курица");
        sporObserver observer = new sporObserver();
        observer.addSpeaker(egg);
        observer.addSpeaker(chicken);
        observer.startAll();
    }
}
