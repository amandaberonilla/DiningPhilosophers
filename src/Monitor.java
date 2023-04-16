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
    private final int numberOfChopsticks;
    private boolean[] chopstickStates;
    private boolean currentSpeaker;     // Mutex to determine whether someone is currently speaking.




    /**
     * Constructor
     */
    public Monitor(int piNumberOfPhilosophers) {
        // PA3: Implementation
        // TODO: set appropriate number of chopsticks based on the # of philosophers
        numberOfChopsticks = piNumberOfPhilosophers;
        chopstickStates = new boolean[piNumberOfPhilosophers]; // initially false
        currentSpeaker = false;
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
        // PA3: Implementation
        ///// Defining the left and right chopsticks.
        int leftChopstick, rightChopstick;
        leftChopstick = (piTID + numberOfChopsticks - 1) % numberOfChopsticks; // circular array
        rightChopstick = piTID % numberOfChopsticks;

        ///// Telling the philosopher to pick up the left chopstick before the right one.
        try {
            // Attempting to take the left chopstick.
            while(chopstickStates[leftChopstick] || chopstickStates[rightChopstick]) {
                wait();
            }
            chopstickStates[leftChopstick] = true;
            // Attempting to ake right chopstick.
            while(chopstickStates[rightChopstick]) {
                wait();
            }
            chopstickStates[rightChopstick] = true;
        } catch (InterruptedException e) {
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
        // PA3: Implementation
        ///// Resetting the states of the chopsticks.
        chopstickStates[(piTID + numberOfChopsticks - 1) % numberOfChopsticks] = false;
        chopstickStates[piTID % numberOfChopsticks] = false;

        notifyAll();
    }

    /**
     * Only one philopher at a time is allowed to philosophy
     * (while she is not eating).
     */
    public synchronized void requestTalk() {
        // PA3: Implementation
        try {
            // Attempting to talk.
            while(currentSpeaker) {
                wait();
            }
            currentSpeaker = true;
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

        // PA3: Implementation
        currentSpeaker = false;
    }
}

// EOF
