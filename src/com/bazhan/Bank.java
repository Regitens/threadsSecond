package com.bazhan;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.Arrays.fill;

public class Bank {
    private final double[] accounts;
    private  Lock bankLock;
    private Condition sufficientFunds;

    public Bank(int n, double initialBalance){
        accounts=new double[n];
        Arrays.fill(accounts, initialBalance);
        bankLock=new ReentrantLock();
        //возвращает бъект условия, связанный с данной блокировкой
        sufficientFunds=bankLock.newCondition();
    }
    public void transfer(int from,int to, double amount) throws InterruptedException{
        bankLock.lock();
        try {
            while (accounts[from]<amount)
                //вводит поток исполнения в набор ожидания по данному условию
                sufficientFunds.await();
            System.out.print(Thread.currentThread());
            accounts[from]-=amount;
            System.out.printf("%10.2f from %d to %d", amount,from, to);
            accounts[to]+=amount;
            System.out.printf("Total Balance: %10.2f%n", getTotalBalance());
            //разблокировать все потоки исполнения в наборе ожидания
            sufficientFunds.signalAll();
        }
        finally {
            bankLock.unlock();
        }
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
