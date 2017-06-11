import java.io.*;

public class Main {

    public static void main(String[] args) {

        CommonSwitch commonSwitch = new CommonSwitch();


        new Thread(new TextHandler(new File("./src/resources/abc.txt"),
                new File("./src/output/common.txt"), commonSwitch)).start();
        new Thread(new TextHandler(new File("./src/resources/bca.txt"),
                new File("./src/output/common.txt"), commonSwitch)).start();
        new Thread(new TextHandler(new File("./src/resources/cab.txt"),
                new File("./src/output/common.txt"), commonSwitch)).start();

    }
}