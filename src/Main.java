import java.io.*;

public class Main {

    public static void main(String[] args) {

        CommonSwitch commonSwitch = new CommonSwitch();


        new Thread(new TextHandler(new File("./src/res/abc.txt"),
                new File("./src/output/common.txt"), commonSwitch)).start();
        new Thread(new TextHandler(new File("./src/res/bca.txt"),
                new File("./src/output/common.txt"), commonSwitch)).start();
        new Thread(new TextHandler(new File("./src/res/cab.txt"),
                new File("./src/output/common.txt"), commonSwitch)).start();

    }
}