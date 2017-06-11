import java.io.*;
import java.util.Scanner;

public class TextHandler implements Runnable {

    File source;
    File whereToWrite;
    private boolean currentIsOn;
    CommonSwitch commonSwitch;

    TextHandler(File source, File whereToWrite, CommonSwitch commonSwitch) {

        this.source = source;
        this.whereToWrite = whereToWrite;
        this.commonSwitch = commonSwitch;
        currentIsOn = true;

    }

    @Override
    public void run() {

        synchronized (commonSwitch) {

            try (Scanner sc = new Scanner(source);
                 FileWriter fw = new FileWriter(whereToWrite, true);
            ) {

                while (currentIsOn) {
                    if (!sc.hasNext()) {
                        currentIsOn = false;
                        return;
                    }
                    if (commonSwitch.isTurnedOn()) {

                        String next = sc.next();
                        System.out.print(next + " ");
                        fw.write(next + " ");
                        System.out.println(Thread.currentThread().getName() + " passing turn to other threads");
                        try {
                            commonSwitch.notify();
                            Thread.sleep(100);
                            //commonSwitch.wait();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            } catch (IOException ex) {

                System.out.println(ex.getMessage());
            }
        }
    }
}