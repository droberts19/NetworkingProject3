package sample;

public class SyncData {

        // This SynchronizedQueue stores 100 Files.
        // It could store any type of same data by changing type of myQueue array.
        private Object[] queue;
        // dataAmount is how many pieces of data are currently in the queue
        int amountData;
        // putLocation is where the next put will happen
        int nextPutLocation;
        // getLocation is where the next get will get from
        int nextGetLocation;

        SyncData() {
            // Initially, the queue has no data
            queue = new Object[100];
            amountData = 0;
            nextPutLocation = 0;
            nextGetLocation = 0;
        }

        synchronized boolean put(Object newValue) {
            // check if there is space in the queue
            if (amountData < 100) {
                // put newValue into queue
                queue[nextPutLocation] = newValue;
                // update where next put will go into queue, making sure to wrap back to beginning
                if (nextPutLocation < 99) {
                    nextPutLocation += 1;
                } else {
                    nextPutLocation = 0;
                }
                // Increment amount of data now in queue (which gets used to decide if there is data to get)
                amountData += 1;
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
            if (amountData > 0) {
                // Get the next data
                Object objToReturn = queue[nextGetLocation];
                // Update where next data will be gotten from
                if (nextGetLocation < 99) {
                    nextGetLocation += 1;
                } else {
                    nextGetLocation = 0;
                }
                // Decrement amount of data left in queue (which also gets used to decide if there is space for more data)
                amountData -= 1;

                // by returning a file, we're letting the caller know that get succeeded
                System.out.println("GOT " + objToReturn);
                return objToReturn;
            } else {
                // by returning NO file, we're letting the caller know that get failed
                return null;
            }
        }
    }



    }