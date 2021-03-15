import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

class Bank {

  private Lock l = new ReentrantLock();

  private static class Account {
    private int balance;

    void lock(){ l.lock; }
    void unlock(){ l.unlock; }

    Account(int balance) {
      this.balance = balance;
    }
    int balance() { return balance; }
    boolean deposit(int value) {
      l.lock();
      try{
        balance += value;
      } finally {
        l.unlock();
      }
      return true;
    }
    boolean withdraw(int value) {
      l.lock();
      try{
        if (value > balance)
          return false;
        balance -= value;
      } finally {
        l.unlock();
      }
      return true;
    }
  }

  // Bank slots and vector of accounts
  private int slots;
  private Account[] av; 

  public Bank(int n)
  {
    slots=n;
    av=new Account[slots];
    for (int i=0; i<slots; i++) av[i]=new Account(0);
  }

  // Account balance
  public int balance(int id) {
    if (id < 0 || id >= slots)
      return 0;
    return av[id].balance();
  }

  // Deposit
  boolean deposit(int id, int value) {
    if (id < 0 || id >= slots)
      return false;
    return av[id].deposit(value);
  }

  // Withdraw; fails if no such account or insufficient balance
  public boolean withdraw(int id, int value) {
    if (id < 0 || id >= slots)
      return false;
    return av[id].withdraw(value);
  }

  public boolean trasfer(int from, int to, int value){
    //é necessario dar lock também neste metodo porque nao basta cada metodo withdrawl e deposit ser de exclusao mutua.
    l.lock();
    try {
      if (!withdraw(from, value))
        return false;
      else withdraw(from, value) && deposit(to, value);
    } finally {
      l.unlock();
    } //não é eficiente porque qlq coisa e bloqueia o banco todo
  }

  //nova abordagem: cada conta proteger-se a si própria.

  public boolean tranfer(int from, int to, int value){
    if()
      return false;
    Account actfrom = av[from];
    Account actto   = av[to];
    actfrom.l.lock();
    actto.l.lock();
    try{
      if(!actfrom.withdraw(value)) return false;
      else actto.deposit(value);
    } finally {
      actfrom.l.unlock();
      actto.l.unlock();
    }
  }

  //ainda melhor, para nao dar problemas
  if(from < to){
    actfrom.t.lock();
    actto.t.lock();
  } else {
    actto.t.lock();
    actfrom.t.lock();
  }
  actfrom.with
  actto.dep

  int totalBalance(){
    return Arrays.stream(av).mapToInt(Account::balance).sum();
  }
}