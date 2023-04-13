public class DeliverOrder implements Runnable{
    RestaurantMonitor monitor;
    
    DeliverOrder(RestaurantMonitor monitor){
        this.monitor = monitor;
    }

    @Override
    public void run(){
        try{
            Thread.sleep(5000);
            monitor.packOrders();
            System.out.println("Finished packing");
        }catch(InterruptedException e){
            System.err.println(e);
            System.exit(1);
        }
    }
}
