import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Lukasz on 2015-10-30.
 */
public class Banker {

    // Number of client processes
    private int clientsCount;

    // Bank's capital (maximum available resources)
    private int maxAvailable;

    // Bank's money (current available resources = maxAvailable - allocationSum)
    private int available;

    // Starting clients' allocated resources
    private int[] allocation;

    // Maximum clients' needs
    private int[] maxDemand;

    // Current clients' needs (maxDemand - allocation)
    private int[] need;

    // Sum of all clients' allocation
    private int allocationSum;

    // Helper variable to print additional message if process requested possible amount of resources, but it would cause deadlock
    private boolean deadlockDetected;

    // Lock to make Banker a monitor
    private Lock lock = new ReentrantLock();

    public Banker(int clientsCount, int maxAvailable, int[] allocation, int[] maxDemand) {
        this.clientsCount = clientsCount;
        this.maxAvailable = maxAvailable;
        this.allocation = allocation;
        this.maxDemand = maxDemand;

        need = new int[clientsCount];

        for(int i=0; i<clientsCount; i++){
            need[i] = maxDemand[i] - allocation[i];
        }

        System.out.println("Need array: " + Arrays.toString(need));

        for(int i=0; i<clientsCount; i++){
            allocationSum += allocation[i];
        }

        System.out.println("Allocation sum: " + allocationSum);

        available = this.maxAvailable - allocationSum;

        System.out.println("Available: " + available);
    }

    private boolean isStateSafe(int clientNumber, int request){
        System.out.println("Client " + clientNumber + " requesting " + request);
        System.out.println("Available: " + available);

        if(request > available){
            System.out.println("Request cannot be granted");
            return false;
        }

        boolean[] canFinish = new boolean[clientsCount];

        // Working copy of available resources
        int work = available;

        // Simulate granting request, to check if the state will be safe
        work -= request;
        need[clientNumber] -= request;
        allocation[clientNumber] += request;

        deadlockDetected = false;

        for(int i=0; i<clientsCount; i++){
            // Find first thread that can finish
            for(int j=0; j<clientsCount; j++){
                if(!canFinish[j]){
                    boolean isSafe = true;
                    if(need[j] > work){
                        isSafe = false;
                    }
                    if(isSafe){
                        canFinish[j] = true;
                        work += allocation[j];
                    }
                }
            }
        }

        // Revert the changes made for simulation
        need[clientNumber] += request;
        allocation[clientNumber] -= request;

        boolean isStateSafe = true;
        for(int i=0; i<clientsCount; i++){
            if(!canFinish[i]){
                isStateSafe = false;
                deadlockDetected = true;
                break;
            }
        }

        return isStateSafe;
    }

    public boolean requestResources(int clientNumber, int request){
        try{
            lock.lock();
            if(!isStateSafe(clientNumber, request)){
                if(deadlockDetected){
                    System.out.println("Granting request of client " + clientNumber + " would cause deadlock");
                }
                return false;
            }

            available -= request;
            allocation[clientNumber] += request;
            need[clientNumber] = maxDemand[clientNumber] - allocation[clientNumber];

            return true;
        } finally {
            lock.unlock();
        }
    }

    public void releaseResources(int clientNumber, int release){
        try{
            lock.lock();
            System.out.println("Client " + clientNumber + " releasing " + release);

            available += release;
            allocation[clientNumber] -= release;
            need[clientNumber] = maxDemand[clientNumber] - allocation[clientNumber];
        } finally {
            lock.unlock();
        }

    }

}
