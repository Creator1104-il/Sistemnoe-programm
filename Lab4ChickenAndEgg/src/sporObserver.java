import java.util.ArrayList;
public class sporObserver{
    ArrayList<sporThread> note;
    sporObserver(){
        this.note = new ArrayList<>();
    }
    public void addSpeaker(sporThread newSpeaker){
        note.add(newSpeaker);
        newSpeaker.addObserver(this);
    }
    public void notifyAboutEnd(sporThread e){
        if(note.size()==1){
            e.win();
        }
        if (e.isAlive()){
            this.removeSubscriber(e);
        }
    }
    public void removeSubscriber(sporThread Subscriber){
        int i = note.indexOf(Subscriber);
        if(i>=0){
            note.remove(i);
        }
    }
    public void startAll() {
        for (int i = 0; i < note.size(); i++) {
            note.get(i).startIt();
        }
    }
}
