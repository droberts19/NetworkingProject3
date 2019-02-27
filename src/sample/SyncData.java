package sample;

public class SyncData {
    // This SynchronizedQueue stores 100 Files.
    // It could store any type of same data by changing type of myQueue array.
    private Object[] myQueue;
    // dataAmount is how many pieces of data are currently in the queue
    int dataAmount;
    // putLocation is where the next put will happen
    int putLocation;
    // getLocation is where the next get will get from
    int getLocation;

    SyncData() {
        // Initially, the queue has no data
        myQueue = new Object[100];
        dataAmount = 0;
        putLocation = 0;
        getLocation = 0;
    }

    synchronized boolean put(Object newValue) {
        // check if there is space in the queue
        if (dataAmount < 100) {
            // put newValue into queue
            myQueue[putLocation] = newValue;
            // update where next put will go into queue, making sure to wrap back to beginning
            if (putLocation < 99) {
                putLocation += 1;
            } else {
                putLocation = 0;
            }
            // Increment amount of data now in queue (which gets used to decide if there is data to get)
            dataAmount += 1;
            // tell caller that put succeeded
            System.out.println("PUT " + newValue);
            return true;
        } else {
            // no space in queue, don't put newValue in queue, and tell caller that put failed
            return false;
        }
    }

    synchronized Object get() {

        // Check if there is data to get
        if (dataAmount > 0) {
            // Get the next data
            Object objToReturn = myQueue[getLocation];
            // Update where next data will be gotten from
            if (getLocation < 99) {
                getLocation += 1;
            } else {
                getLocation = 0;
            }
            // Decrement amount of data left in queue (which also gets used to decide if there is space for more data)
            dataAmount -= 1;

            // by returning a file, we're letting the caller know that get succeeded
            System.out.println("GOT " + objToReturn);
            return objToReturn;
        } else {
            // by returning NO file, we're letting the caller know that get failed
            return null;
        }
    }

    /*// Fields
        private Object[] queue;
        private int nextPutLocation;
        private int nextGetLocation;
        private int amountData;

        // Constructor
        SyncData() {
            queue = new Object[100];
            nextPutLocation = 0;
            nextGetLocation = 0;
            amountData = 0;
        }

        // Methods

    synchronized boolean put(Object object) {
        if (amountData == 100) {
            return false;
        }

        queue[nextPutLocation] = object;
        amountData = amountData + 1;
        if (nextPutLocation < 99) {
            nextPutLocation = nextPutLocation + 1;
        } else {
            nextPutLocation = 0;
        }
        System.out.println("PUT " + object);
        return true;
    }

    synchronized Object get() {
            if (amountData == 0) {
                return null;
            }

            int saveNextGetLocation = nextGetLocation;
            if (nextGetLocation < 99) {
                nextGetLocation = nextGetLocation + 1;
            } else {
            }
            amountData = amountData - 1;
            return queue[saveNextGetLocation];

        } */
    }