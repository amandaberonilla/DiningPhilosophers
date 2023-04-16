package Assignment3;



/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor {
    /*
     * ------------
     * Data members
     * ------------
     */
    private final int NUM_OF_CHOPSTICKS;
    private boolean[] chopstickState;
    private boolean speaker;




    /**
     * Constructor
     */
    public Monitor(int piNumberOfPhilosophers) {
        // TODO: set appropriate number of chopsticks based on the # of philosophers
        NUM_OF_CHOPSTICKS = piNumberOfPhilosophers;
        chopstickState = new boolean[piNumberOfPhilosophers];
        speaker = false;

    }

    /*
     * -------------------------------
     * User-defined monitor procedures
     * -------------------------------
     */

    /**
     * Grants request (returns) to eat when both chopsticks/forks are available.
     * Else forces the philosopher to wait()
     */
    public synchronized void pickUp(final int piTID) {
        //Here we are telling the philosopher to pick up the left chopstick before the right one
        //To do so we must first specify to the philosopher what are the right and left chopsticks for them
        int leftChopstick,rightChopstick;

        leftChopstick = piTID - 1;
        rightChopstick = piTID % NUM_OF_CHOPSTICKS;
        try{
            while(chopstickState[leftChopstick] || chopstickState[rightChopstick]){
                wait();
            }
            chopstickState[leftChopstick] = true;
            //Added to account for any possible interruptions, that would change the state of the right chopstick
            while(chopstickState[rightChopstick]){
                wait();
            }
            chopstickState[rightChopstick] = true;
        } catch (InterruptedException e){
            System.err.println("Monitor.pickup():");
            DiningPhilosophers.reportException(e);
            System.exit(1);
        }
    }

    /**
     * When a given philosopher's done eating, they put the chopstiks/forks down
     * and let others know they are available.
     */
    public synchronized void putDown(final int piTID) {
        chopstickState[piTID - 1] = false;
        chopstickState[piTID % NUM_OF_CHOPSTICKS] = false;

        notifyAll();
    }

    /**
     * Only one philopher at a time is allowed to philosophy
     * (while she is not eating).
     */
    public synchronized void requestTalk() {
        try{
            while(speaker){
                wait();
            }
            speaker = true;
        } catch (InterruptedException e){
            System.err.println("Monitor.requestTalk:");
            DiningPhilosophers.reportException(e);
            System.exit(1);
        }
    }

    /**
     * When one philosopher is done talking stuff, others
     * can feel free to start talking.
     */
    public synchronized void endTalk() {
        speaker = false;
    }
}

// EOF
