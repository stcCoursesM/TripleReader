// Необходимо разработать программу, которая получает на вход список ресурсов, содержащих текст,
// и проверяет уникальность каждого слова. Каждый ресурс должен быть обработан в отдельном потоке,
// текст не должен содержать инностранных символов, только кириллица, знаки препинания и цифры.
// В случае нахождения дубликата, программа должна прекращать выполнение с соответсвующим сообщением.
// Все ошибки должны быть корректно обработаны, все API покрыто модульными тестами

import java.io.*;
import java.util.Scanner;

public class TextChecker implements Runnable {

    File source;
    File whereToWrite;
    private boolean currentIsOn;
    CommonSwitch commonSwitch;

    TextChecker(File source, File whereToWrite, CommonSwitch commonSwitch) {

        this.source = source;
        this.whereToWrite = whereToWrite;
        this.commonSwitch = commonSwitch;
        currentIsOn = true;

    }

    @Override
    public void run() {

        synchronized (commonSwitch) {

            try (Scanner sc = new Scanner(source);
            ) {
                while (commonSwitch.isTurnedOn() && currentIsOn) {

                    if (sc.hasNextInt()) {
                        commonSwitch.setIsTurnedOff();
                        System.out.println(Thread.currentThread().getName() +
                                " encountered illegal input, stopping all threads!");
                        return;
                    }
                    if (!sc.hasNext()) {
                        currentIsOn = false;
                        System.out.println(Thread.currentThread().getName() + " has no more work to do");
                        System.out.println("Current Set size is "+commonSwitch.strSet.size()+ "\n" + "it contains: "
                        +commonSwitch.strSet.toString());
                        return;
                    }

                    if (commonSwitch.isTurnedOn() && currentIsOn) {

                        String next = sc.next();
                        for (int i=0; i<next.length(); i++){
                            if(Character.UnicodeBlock.of(next.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
                                System.out.println("Cyrillic symbols encountered! Stopping!");
                                commonSwitch.setIsTurnedOff();
                                System.out.println("Current Set size is "+commonSwitch.strSet.size()+ "\n" + "it contains: "
                                        +commonSwitch.strSet.toString());
                                return;
                            }
                        }

                        if (!commonSwitch.addToSet(next)) {
                            System.out.println("Duplicate encountered! Stopping!");
                            commonSwitch.setIsTurnedOff();
                            System.out.println("Current Set size is "+commonSwitch.strSet.size()+ "\n" + "it contains: "
                                    +commonSwitch.strSet.toString());
                        };

                        try {
                            commonSwitch.notifyAll();
                            Thread.sleep(100);
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