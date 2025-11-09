public class pes {
    //нельза называть собаку именем Трутень
    public static final String incorrectName = "Трутень";
    public static void checkName(String name) throws name {
        if(name.equals(incorrectName)){
            throw new name("Недопустимое имя");
        }
    }
}
