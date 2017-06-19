import java.util.HashSet;
import java.util.Set;

public class CommonSwitch {

    static boolean switcher;

    public boolean isTurnedOn() {
        return switcher;
    }

    public void setIsTurnedOff() {
        CommonSwitch.switcher = false;
    }

    public boolean addToSet(String s){
        if(strSet.contains(s)) return false;

        strSet.add(s);
        return true;

    }

    volatile Set strSet;

    CommonSwitch(){

        strSet = new HashSet<String>();
        switcher = true;

    }


}
