package sample;

public class SyncData {
        // Fields
        Object[] queue;
        int nextPutLocation;
        int nextGetLocation;
        int amountData;

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
            System.out.println("put() failed.  Please get() some");
            return false;
        }

        queue[nextPutLocation] = object;
        amountData = amountData + 1;
        if (nextPutLocation < 99) {
            nextPutLocation = nextPutLocation + 1;
        } else {
            nextPutLocation = 0;
        }
        System.out.println("put " + object);
        return true;
    }

    synchronized Object get() {
            if (amountData == 0) {
                return null;
            }

            int savenextGetLocation = nextGetLocation;
            if (nextGetLocation < 99) {
                nextGetLocation = nextGetLocation + 1;
            } else {
            }
            amountData = amountData - 1;
        System.out.println("get " + queue[savenextGetLocation]);
            return queue[savenextGetLocation];

        }
    }