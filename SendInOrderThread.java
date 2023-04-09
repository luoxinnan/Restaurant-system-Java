public class SendInOrderThread implements Runnable{
    
    RestaurantMonitor monitor;
    Order order;
    
    private SendInOrderThread(Order order, RestaurantMonitor monitor){
        this.monitor = monitor;
        this.order = order;
    }

    @Override
    public void run(){
        try{
            monitor.workOnOrders(order);
            System.out.println("Finished Sending");
        }catch(InterruptedException e){
            System.err.println(e);
            System.exit(1);
        }
    }
}
