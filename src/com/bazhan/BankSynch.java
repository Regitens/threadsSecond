package com.bazhan;

import java.util.Arrays;

public class BankSynch {
    private final double[] accounts;

    public BankSynch(int n, double initialBalance) {
        accounts=new double[n];
        Arrays.fill(accounts, initialBalance);
    }

    public synchronized void transfer(int from,int to, double amount) throws InterruptedException{

            while (accounts[from]<amount)
                //вынуждает поток ждать уведомления
                wait();
            System.out.print(Thread.currentThread());
            accounts[from]-=amount;
            System.out.printf("%10.2f from %d to %d", amount,from, to);
            accounts[to]+=amount;
            System.out.printf("Total Balance: %10.2f%n", getTotalBalance());
            //разблокировать потоки исполнения, вызвавшие метод wait
            notifyAll();
    }
    public double getTotalBalance(){
        double sum=0;
        for (double a:accounts) sum+=a;
        return sum;
    }
    public int size(){
        return accounts.length;
    }
}
