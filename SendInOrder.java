public class SendInOrder implements Runnable{
    
    RestaurantMonitor monitor;
    Order order;
    
    SendInOrder(Order order, RestaurantMonitor monitor){
        this.monitor = monitor;
        this.order = order;
    }

    @Override
    public void run(){
        try{
            System.out.println("Finished Sending in order");
            monitor.workOnOrders(order);
        }catch(InterruptedException e){
            System.err.println(e);
            System.exit(1);
        }
    }
}
