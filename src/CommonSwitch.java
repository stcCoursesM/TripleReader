public class CommonSwitch {

    static boolean isTurnedOn = true;

    public static boolean isTurnedOn() {
        return isTurnedOn;
    }

    public static void setIsTurnedOff() {
        CommonSwitch.isTurnedOn = false;
    }

}
