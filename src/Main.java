import java.io.File;

public class Main {

    public static void main(String[] args) {

        System.out.println("Started");

        CommonSwitch commonSwitch = new CommonSwitch();


        new Thread(new TextChecker(new File("./src/resources/abc.txt"),
                new File("./src/output/common.txt"), commonSwitch)).start();
        new Thread(new TextChecker(new File("./src/resources/bca.txt"),
                new File("./src/output/common.txt"), commonSwitch)).start();
        new Thread(new TextChecker(new File("./src/resources/cab.txt"),
                new File("./src/output/common.txt"), commonSwitch)).start();

    }
}