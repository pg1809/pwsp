import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Lukasz on 2015-10-30.
 */
public class Banker {

    // Number of client processes
    private int clientsNumber;

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
    private Lock lock = new ReentrantLock(true);

    private Condition stateSafe = lock.newCondition();

    public Banker(int clientsNumber, int maxAvailable, int[] allocation, int[] maxDemand) {
        this.clientsNumber = clientsNumber;
        this.maxAvailable = maxAvailable;
        this.allocation = allocation;
        this.maxDemand = maxDemand;

        need = new int[clientsNumber];

        for (int i = 0; i < clientsNumber; i++) {
            need[i] = maxDemand[i] - allocation[i];
        }

        System.out.println("Need array: " + Arrays.toString(need));

        for (int i = 0; i < clientsNumber; i++) {
            allocationSum += allocation[i];
        }

        System.out.println("Allocation sum: " + allocationSum);

        available = this.maxAvailable - allocationSum;

        System.out.println("Available: " + available);
    }

    private boolean isStateSafe(int clientNumber, int request) {
        System.out.println("Client " + clientNumber + " requesting " + request);
        System.out.println("Available: " + available);

        if (request > available) {
            System.out.println("Request cannot be granted");
            return false;
        }

        boolean[] canFinish = new boolean[clientsNumber];

        // Working copy of available resources
        int work = available;

        // Simulate granting request, to check if the state will be safe
        work -= request;
        need[clientNumber] -= request;
        allocation[clientNumber] += request;

        deadlockDetected = false;

        for (int i = 0; i < clientsNumber; i++) {
            // Find first thread that can finish
            for (int j = 0; j < clientsNumber; j++) {
                if (!canFinish[j]) {
                    boolean isSafe = true;
                    if (need[j] > work) {
                        isSafe = false;
                    }
                    if (isSafe) {
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
        for (int i = 0; i < clientsNumber; i++) {
            if (!canFinish[i]) {
                isStateSafe = false;
                deadlockDetected = true;
                break;
            }
        }

        return isStateSafe;
    }

    public void requestResources(int clientNumber, int request) {
        try {
            lock.lock();
            while (!isStateSafe(clientNumber, request)) {
                if (deadlockDetected) {
                    System.out.println("Granting request of client " + clientNumber + " would cause deadlock");
                }
                stateSafe.await();
            }

            available -= request;
            allocation[clientNumber] += request;
            need[clientNumber] = maxDemand[clientNumber] - allocation[clientNumber];
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void releaseResources(int clientNumber, int release) {
        try {
            lock.lock();
            System.out.println("Client " + clientNumber + " releasing " + release);

            available += release;
            allocation[clientNumber] -= release;
            need[clientNumber] = maxDemand[clientNumber] - allocation[clientNumber];
            stateSafe.signalAll();
        } finally {
            lock.unlock();
        }
    }

}
