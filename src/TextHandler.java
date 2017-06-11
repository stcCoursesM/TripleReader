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
                 FileWriter fw = new FileWriter(whereToWrite, true)
            ) {
                while (commonSwitch.isTurnedOn() && currentIsOn) {

                    if (sc.hasNextInt()) {
                        commonSwitch.setIsTurnedOff();
                        System.out.println(Thread.currentThread().getName() +
                                " encountered illegal input, stopping all threads!");
                        fw.close();
                        return;
                    }
                    if (!sc.hasNext()) {
                        currentIsOn = false;
                        System.out.println(Thread.currentThread().getName() + " has no more work to do");
                        fw.close();
                        return;
                    }

                if (commonSwitch.isTurnedOn() && currentIsOn) {

                    String next = sc.next();
                    System.out.print(next + " ");
                    fw.write(next + " ");
                    fw.flush();
                    try {
                        commonSwitch.notifyAll();
                        Thread.sleep(10);
                        System.out.println(Thread.currentThread().getName() + " passing turn to other threads");
                        commonSwitch.wait(100);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
}
}