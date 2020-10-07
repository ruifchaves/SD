import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class EX2 extends Thread{
    int I;
    Bank b;
    int V;
    public EX2 (int I, Bank b, int V){
        this.I = I;
        this.b = b;
        this.V = V;
    }
    public void run() {
        for(int i = 1; i <= I; i++) {
            b.deposit(V);
        }
    }
}

class Bank {
    Lock l = new ReentrantLock();
    private static class Account {
        private int balance;
        Account(int balance) { this.balance = balance; }
        int balance() { return balance; }
        boolean deposit(int value) {
            balance += value;
            return true;
        }
    }

    // Our single account, for now
    private Account savings = new Account(0);

    // Account balance
    public int balance() {
        l.lock();
        try {
            return savings.balance();
        } finally {
            l.unlock();
        }
    }

    // Deposit
    boolean deposit(int value) {
        l.lock();
        try {
            return savings.deposit(value);
        } finally {
            l.unlock();
        }
    }
}

class Programa2 {
    public static void main(String[] args) throws InterruptedException{
        int N = 10;
        int I = 1000;
        Bank b = new Bank();
        Thread[] a = new Thread[N];
        //[...]
        for(int i = 1; i <= N; i++)
            a[i] = new Impressora(I);
        for(int i = 1; i <= N; i++)
            a[i].start();
        for(int i = 1; i <= N; i++)
            a[i].join();
        System.out.println("fim");
    }
}

//nao podemos ter varias threads a ler e escrever em objetos partilhados.