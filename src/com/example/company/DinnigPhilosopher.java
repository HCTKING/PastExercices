package com.example.company;

public class DinnigPhilosopher {

    private int AmountOfFood;
    private static boolean Guiding;
    private static boolean blocked = false;

    public DinnigPhilosopher(int amountOfFood) {
        AmountOfFood = amountOfFood;
    }

    public int getAmountOfFood() {
        return AmountOfFood;
    }

    public void setAmountOfFood(int amountOfFood) {
        AmountOfFood = amountOfFood;
    }

    public synchronized void CheckTheStatus(int Nodesize, int VisitedSize){
        if(Nodesize == VisitedSize){
            System.out.println(Philosopher.currentThread().getName() + " LOL ");
            Guiding = true;
        }else{
            Guiding = false;
        }
    }

    public synchronized void setThisThreadOnWait(Philosopher current){
        if(!Guiding){
            while(!blocked){
                try{
                    wait();
                }catch(Exception e ){
                    System.out.println("crap");
                }
            }
        }
    }

    public synchronized void AwakentheThread(){

        if(Guiding && !blocked){
            cleantheconditions();
        }
        blocked = true;
        notifyAll();
    }


    public synchronized void cleantheconditions(){

        if(Guiding && Philosopher.getCOUNTINGSHIT().size() == Philosopher.getCOUNTINGSHIT().size()) {
            Philosopher.getCOUNTINGSHIT().clear();
            Guiding = false;
        }
    }

    public synchronized void Cleaner(){
        if(blocked)
            blocked = false;
    }



    public static void main(String[] args) {

        Chopstick Chop1 = new Chopstick();
        Chopstick Chop2 = new Chopstick();
        Chopstick Chop3 = new Chopstick();
        Chopstick Chop4 = new Chopstick();
        Chopstick Chop5 = new Chopstick();


        DinnigPhilosopher DP = new DinnigPhilosopher(150);

        Philosopher aristote = new Philosopher("Aristote", Chop1, Chop2, DP);
        aristote.setName("Aristote");
        Philosopher _50cent = new Philosopher("50Cent", Chop2, Chop3, DP);
        _50cent.setName("50Cent");
        Philosopher Nieztsche = new Philosopher("Nieztsche", Chop3, Chop4, DP);
        Nieztsche.setName("Nieztsche");
        Philosopher Socrate = new Philosopher("Socrate", Chop4, Chop5, DP);
        Socrate.setName("Socrate");
        Philosopher Platon = new Philosopher("Platon", Chop5, Chop1, DP);
        Platon.setName("Platon");

        aristote.start();
        _50cent.start();
        Nieztsche.start();
        Socrate.start();
        Platon.start();
    }
}
