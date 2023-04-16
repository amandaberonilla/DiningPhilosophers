import common.BaseThread;

/**
 * Class Philosopher.
 * Outlines main subrutines of our virtual philosopher.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Philosopher extends BaseThread {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    /**
     * Max time an action can take (in milliseconds)
     */
    public static final long TIME_TO_WASTE = 1000;

    /**
     * The act of eating.
     * - Print the fact that a given phil (their TID) has started eating.
     * - yield
     * - Then sleep() for a random interval.
     * - yield
     * - The print that they are done eating.
     */
    public void eat() {
        try {
            // PA3: Implementation start
            System.out.println("Philosopher " + this.iTID + " has started eating");
            Thread.yield();
            // PA3: Implementation end

            sleep((long) (Math.random() * TIME_TO_WASTE));

            // PA3: Implementation start
            Thread.yield();
            System.out.println("Philosopher " + this.iTID + " is done eating");
            // PA3: Implementation end
        } catch (InterruptedException e) {
            System.err.println("Philosopher.eat():");
            DiningPhilosophers.reportException(e);
            System.exit(1);
        }
    }

    /**
     * The act of thinking.
     * - Print the fact that a given phil (their TID) has started thinking.
     * - yield
     * - Then sleep() for a random interval.
     * - yield
     * - The print that they are done thinking.
     */
    public void think() {
        // PA3: Implementation start
        try {
            System.out.println("Philosopher " + this.iTID + " has started thinking");
            Thread.yield();
            sleep((long) (Math.random() * TIME_TO_WASTE));
            Thread.yield();
            System.out.println("Philosopher " + this.iTID + " is done thinking");
        } catch (InterruptedException e) {
            System.err.println("Philosopher.think():");
            e.printStackTrace();
            System.exit(1);
        }
        // PA3: Implementation end
    }

    /**
     * The act of talking.
     * - Print the fact that a given phil (their TID) has started talking.
     * - yield
     * - Say something brilliant at random
     * - yield
     * - The print that they are done talking.
     */
    public void talk() {
        // PA3: Implementation start
        System.out.println("Philosopher " + this.iTID + " has started talking");
        Thread.yield();
        // PA3: Implementation end

        saySomething();

        // PA3: Implementation start
        Thread.yield();
        System.out.println("Philosopher "+ this.iTID + " is done talking");
        // PA3: Implementation end
    }

    /**
     * No, this is not the act of running, just the overridden Thread.run()
     */
    public void run() {
        for (int i = 0; i < DiningPhilosophers.DINING_STEPS; i++) {
            DiningPhilosophers.soMonitor.pickUp(getTID());

            eat();

            DiningPhilosophers.soMonitor.putDown(getTID());

            think();

            /*
             * TODO:
             * A decision is made at random whether this particular
             * philosopher is about to say something terribly useful.
             */

            // PA3: Implementation start
            ///// Allowing a philosopher to speak if the number rolled on the die is even.
            int dieRoll = (int)(Math.random() * 6) + 1;
            if(dieRoll % 2 == 0) {
                DiningPhilosophers.soMonitor.requestTalk();
                talk();
                DiningPhilosophers.soMonitor.endTalk();
            }
            // PA3: Implementation end

            Thread.yield();
        }
    } // run()

    /**
     * Prints out a phrase from the array of phrases at random.
     * Feel free to add your own phrases.
     */
    public void saySomething() {
        String[] astrPhrases = {
                "Eh, it's not easy to be a philosopher: eat, think, talk, eat...",
                "You know, true is false and false is true if you think of it",
                "2 + 2 = 5 for extremely large values of 2...",
                "If thee cannot speak, thee must be silent",
                "To be or not to be",
                "We get as close to death as we can to feel alive",
                "My number is " + getTID() + ""
        };

        System.out.println(ANSI_YELLOW + "Philosopher 웃 " + getTID() + " says: " + astrPhrases[(int) (Math.random() * astrPhrases.length)] + ANSI_RESET);
    }
}

// EOF
