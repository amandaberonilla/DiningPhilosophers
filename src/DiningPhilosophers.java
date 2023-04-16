/**
 * Class DiningPhilosophers
 * The main starter.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class DiningPhilosophers {
    /*
     * ------------
     * Data members
     * ------------
     */

    /**
     * This default may be overridden from the command line
     */
    public static final int DEFAULT_NUMBER_OF_PHILOSOPHERS = 4;

    /**
     * Dining "iterations" per philosopher thread
     * while they are socializing there
     */
    public static final int DINING_STEPS = 10;

    /**
     * Our shared monitor for the philosphers to consult
     */
    public static Monitor soMonitor = null;

    /*
     * -------
     * Methods
     * -------
     */

    /**
     * Main system starts up right here
     */
    public static void main(String[] args) {
        try {
            /*
             * TODO:
             * Should be settable from the command line
             * or the default if no arguments supplied.
             */
            int iPhilosophers = DEFAULT_NUMBER_OF_PHILOSOPHERS;

            // PA3 Implementation start
            if (args.length > 0) {
                try {
                    iPhilosophers = Integer.parseInt(args[0]);
                    if (iPhilosophers <= 0) {
                        throw new NumberFormatException();
                    }
                }
                catch (NumberFormatException e) {
                    System.out.println("\"" + args[0] + "\" is not a positive decimal integer");
                }
            }
            // PA3 Implementation end

            // Make the monitor aware of how many philosophers there are
            soMonitor = new Monitor(iPhilosophers);

            // Space for all the philosophers
            Philosopher aoPhilosophers[] = new Philosopher[iPhilosophers];

            // Let 'em sit down
            for (int j = 0; j < iPhilosophers; j++) {
                aoPhilosophers[j] = new Philosopher();
                aoPhilosophers[j].start();
            }

            System.out.println(ANSI_CYAN + iPhilosophers + " philosopher(s) came in for a dinner.");
            for (int i = 0; i < iPhilosophers; i++)
            {
                System.out.print("웃 ");
            }
            System.out.println("" + ANSI_RESET);

            // Main waits for all its children to die...
            // I mean, philosophers to finish their dinner.
            for (int j = 0; j < iPhilosophers; j++)
                aoPhilosophers[j].join();

            System.out.println("All philosophers have left. System terminates normally.");
        } catch (InterruptedException e) {
            System.err.println("main():");
            reportException(e);
            System.exit(1);
        }
    } // main()

    /**
     * Outputs exception information to STDERR
     *
     * @param poException Exception object to dump to STDERR
     */
    public static void reportException(Exception poException) {
        System.err.println("Caught exception : " + poException.getClass().getName());
        System.err.println("Message          : " + poException.getMessage());
        System.err.println("Stack Trace      : ");
        poException.printStackTrace(System.err);
    }

    public static void displayMessage() {
        System.out.println(ANSI_YELLOW + "----------------------------------------");
        System.out.println("         COMP 346 Assignment 3");
        System.out.println("     Suha Abubakr & Amanda Beronilla :)");
        System.out.println("----------------------------------------" + ANSI_RESET);
    }
}


// EOF
