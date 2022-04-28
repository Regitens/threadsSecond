package com.bazhan;

import static java.lang.Thread.sleep;

public class Main {
    public static final int DELAY=10;
    public static final int STEPS=100;
    public static final double MAX_AMOUNT=1000;
    public static final double INITIAL_BALANCE=1000;
    public static final int NACCOUNTS=100;


    public static void main(String[] args) {
	/**var bank=new Bank(4,100000);
    Runnable task1=() -> {
        try {
            for (int i=0; i<STEPS;i++){
                double amount=MAX_AMOUNT*Math.random();
                bank.transfer(0,1,amount);
                sleep((int) (DELAY*Math.random()));
            }
        }
        catch (InterruptedException e){}
    };

        Runnable task2=() -> {
            try {
                for (int i=0; i<STEPS;i++){
                    double amount=MAX_AMOUNT*Math.random();
                    bank.transfer(2,3,amount);
                    sleep((int) (DELAY*Math.random()));
                }
            }
            catch (InterruptedException e){}
        };
        new Thread(task1).start();
        new Thread(task2).start();*/


        var synchBank=new Bank(NACCOUNTS,INITIAL_BALANCE);
        for (int i=0; i<NACCOUNTS; i++){
            int fromAccount=i;
            Runnable r=()->{
                try {
                    while (true){
                        int toAccount=(int) (synchBank.size()*Math.random());
                        double amount=MAX_AMOUNT*Math.random();
                        synchBank.transfer(fromAccount, toAccount, amount);
                        Thread.sleep((int) (DELAY*Math.random()));
                    }
                }
                catch (InterruptedException e){}
            };
            var t=new Thread(r);
            t.start();
        }

    }
}
