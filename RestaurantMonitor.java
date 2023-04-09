import java.util.ArrayList;
import java.util.concurrent.locks.*;

public class RestaurantMonitor {
    private OrderSystem orderSystem;
    private Lock lock = new ReentrantLock();
    private Condition canPack = lock.newCondition();

    public RestaurantMonitor(OrderSystem orderSystem){
        this.orderSystem = orderSystem;
    }

    public void workOnOrders(Order order) throws InterruptedException{
       lock.lock();
        try{
            addOrder(order);
            order.setStatus("order taken");
            Thread.sleep(2000);
            order.setStatus("order cooked");
            canPack.signalAll();
        }finally{
            lock.unlock();
        }
    }

    // if not have unpacked orders, wait; if has unpacked orders, pack.
    public void packOrders() throws InterruptedException{
        lock.lock();
        try{
            while(!hasUnpackedOrders()){
                canPack.await();
            }
            for(Order order: getOrders()){
                if(!order.getStatus().equals("order packed")){
                    order.setStatus("order packed");
                }        
            }
        }finally{
            lock.unlock();
        }
    }

    // return true if there is at least one order unpacked
    public boolean hasUnpackedOrders(){
        lock.lock();
        try{
            for(Order order: getOrders()){
                if(!order.getStatus().equals("order packed")){
                    return true;
                }        
            }
            return false;
        }finally{
            lock.unlock();
        }
    }

    public void addOrder(Order order){
        lock.lock();
        try{
            orderSystem.addOrder(order);
        }finally{
            lock.unlock();
        }

    }

    public ArrayList<Order> getOrders(){
        lock.lock();
        try{
            return orderSystem.getOrders();
        }finally{
            lock.unlock();
        }
    }
}
