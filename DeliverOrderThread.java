public class DeliverOrderThread implements Runnable{
    RestaurantMonitor monitor;
    
    private DeliverOrderThread(RestaurantMonitor monitor){
        this.monitor = monitor;
    }

    @Override
    public void run(){
        try{
            monitor.packOrders();
            System.out.println("Finished packing");
        }catch(InterruptedException e){
            System.err.println(e);
            System.exit(1);
        }
    }
}
