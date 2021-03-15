import java.util.*;
import java.util.concurrent.locks.*;

class Warehouse {
  private Map<String, Product> m =  new HashMap<String, Product>();
  Lock l = new ReentrantLock(); 

  private class Product {
    int q = 0;
    Condition cond = l.newCondition();

    //Product(Condition cond) { this.cond = cond; }
  }

  private Product get(String s) {
    Product p = m.get(s);
    if (p != null) return p;
    //p = new Product(l.newCondition());
    p = new Product();
    m.put(s, p);
    return p;
  }

  public void supply(String s, int q) {
    l.lock();
    try{
      Product p = get(s);
      p.q += q;
			p.cond.signalAll();
    } finally {
      l.unlock();
    } 
  }

  private Product missing(String[] a){
    for (String s : a){
      Product p = get(s);
      if(p.q == 0)
        return p;
      }
  }


  // Errado se faltar algum produto...
  public void consume(String[] a) throws InterruptedException{
    l.lock();
    try{
      Product[] ap = new Product[a.length];  //1-converter array de strings em array de prdutos
      for (int i = 0; i < a.length; i++)
          ap[i] = get(a[i]);

      Product p;
      while ((p = missing(ap)) != null)
          p.cond.await();
          

      //ou
      while(true) {
        Product p = missing(ap);
        if (p == null) //se houver algum em falta
                  break;
        p.cond.await(); //espero e tento outra vez
      }
    
    } finally {
      l.unlock();
    }
  }
}