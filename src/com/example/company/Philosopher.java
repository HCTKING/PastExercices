package com.example.company;

import com.sun.source.tree.Tree;
import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Philosopher extends Thread  {

    private String name;
    private Chopstick leftchopstick;
    private Chopstick rightchopstick;
    private int minApp;
    private int maxApp;

    private static ArrayList<Philosopher> NODES = new ArrayList<>();
    private static ArrayList<Philosopher> COUNTINGSHIT = new ArrayList<>();
    private DinnigPhilosopher TheRunnable;

    public static ArrayList<Philosopher> getNODES() {
        return NODES;
    }

    public Philosopher(String name, Chopstick leftchopstick, Chopstick rightchopstick, DinnigPhilosopher DP) {
        Thread.currentThread().setName(name);
        this.name = name;
        this.leftchopstick = leftchopstick;
        this.rightchopstick = rightchopstick;
        minApp = 0;
        maxApp = 10;
        TheRunnable = DP;
        NODES.add(this);

    }

/*    public Philosopher(String name, Chopstick leftchopstick, Chopstick rightchopstick, int minapp, int maxapp) {
        Thread.currentThread().setName(name);
        this.name = name;
        this.leftchopstick = leftchopstick;
        this.rightchopstick = rightchopstick;
        this.minApp = minapp;
        this.maxApp = maxapp;
        NODES.add(this);

    }*/

    public static ArrayList<Philosopher> getCOUNTINGSHIT() {
        return COUNTINGSHIT;
    }

    public int ThinkingTime(){
        int Think = (int) (Math.random() * 2000);
        return Think;
    }

    public int EatingTime(){
        int Eat = (int) (Math.random() * 1000);
        return Eat;
    }

    public synchronized int Hungryness(){
        return (int) ((Math.random() * (maxApp - minApp)) + minApp);
    }


    @Override
    public void run()  {

        while (TheRunnable.getAmountOfFood() > 0) {

            TheRunnable.Cleaner();


            int randomhungryness = Hungryness();
            /*
            *This is the thinking time of the philosopher.
            */
            try {
                Thread.sleep(ThinkingTime());
            } catch (Exception E) {
                System.out.println("Interupted");
            }



            /*
             *Now, he wants to eat
             */
            if (TheRunnable.getAmountOfFood() - randomhungryness >= 0) {
            synchronized (leftchopstick) {

                synchronized (rightchopstick) {



                        TheRunnable.setAmountOfFood((TheRunnable.getAmountOfFood() - Hungryness()));
                        /*
                         *Now, he is eating !!!!
                         */
                        try {
                            Thread.sleep(EatingTime());
                            COUNTINGSHIT.add(this);
                            System.out.println(name + " IS EATING " + randomhungryness + " so there is : " + TheRunnable.getAmountOfFood());
                        } catch (Exception e) {
                            System.out.println("Interupted while eating");
                        }


                }
            }
            TheRunnable.CheckTheStatus(NODES.size(), COUNTINGSHIT.size());
            TheRunnable.setThisThreadOnWait(this);
            TheRunnable.AwakentheThread();
            System.out.println(name + " is here ");

            try {
                Thread.sleep(2000);
            } catch (Exception E) {
                System.out.println("Interupted");
            }

        }else{
            break;
        }
        }
        System.out.println("End of it ?");

    }

}






class ThreadColor {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001b[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001b[37m";
}


/*    @Override
    public void run() {

        while(AmountofFood > 0) {
            try {
                Thread.sleep(ThinkingTime());
            } catch (Exception E) {
                System.out.println(name + " COULDN'T THINK ! Help him now !");
            }
            System.out.println(name + "finished think, now i'm Hungry");
            synchronized (leftchopstick){
                System.out.println(name + " i have my left chopstick, waiting for right one");
                synchronized (rightchopstick){
                    System.out.println(name + " i have my right chopstick, waiting for eating");


                    Hungryness();
                    if(AmountofFood <= 0)
                        break;
                    System.out.println("There is still " + AmountofFood + " left");

                    try{
                        Thread.sleep(EatingTime());
                    }catch(Exception e){
                        System.out.println("Hey man, just WTF");
                    }
                }
                System.out.println(name + "no mo' right");
            }
            System.out.println(name + "no mo' left");
        }
    }*/

/*    @Override
    public synchronized void run() {


            while (AmountofFood > 0) {
                try {
                    Thread.sleep(ThinkingTime());
                } catch (Exception E) {
                    System.out.println(name + " COULDN'T THINK ! Help him now !");
                }
                System.out.println(name + "END THINK");
                leftaken();
                synchronized (leftchopstick) {
                    System.out.println(name + "LEFT");
                    synchronized (rightchopstick) {
                        rightTaken();
                        System.out.println(name + "LEFT + RIGHT");
                        Hungryness();
                        if (AmountofFood <= 0)
                            break;
                        System.out.println("There is still " + AmountofFood + " left");

                        try {
                            Thread.sleep(EatingTime());
                        } catch (Exception e) {
                            System.out.println("Hey man, just WTF");
                        }
                    }
                    System.out.println(name + " - RIGHT ");
                }
                System.out.println(name + "- LEFT");

                while (!TEST){
                    try{
                        wait();
                    }catch(Exception e){
                        System.out.println("WTF mdr LOL");
                        e.printStackTrace();
                    }
                }
                TEST= false;
                visited.clear();
            }
        }*/