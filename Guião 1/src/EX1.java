class Impressora extends Thread {
    int I;
    public Impressora(int I){
        this.I = I;
    }
    public void run() {
        for(int i = 1; i <= I; i++) System.out.println("numero: "+i);
    }
}

class Programa {
    public static void main(String[] args) throws InterruptedException{
        int N = 10;
        int I = 100;
        Thread[] a = new Thread[N];
        for(int i = 1; i <= N; i++)
            a[i] = new Impressora(I);
        for(int i = 1; i <= N; i++)
            a[i].start();
        for(int i = 1; i <= N; i++)
            a[i].join();
        System.out.println("fim");
    }
}