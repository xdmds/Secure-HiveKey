import java.util.ArrayList;
import java.util.Arrays;
import java.security.SecureRandom;

public class HiveKey {
    private byte[] actual_seed; //SecureRandom.generateSeed(64 bits)
    private int key; //SecureRandom.next(128 bits);
    private static ArrayList<byte[]> parent_seeds;
    private HKTimestamp timestamp;
    private byte[] left_seed;
    private byte[] right_seed;
    private byte[] child_seed;

    /**
     * Generates a random seed used to randomize the 6 parent seeds for HiveKey
     */
    private static void generateParents() {
        //generate a random seed
        SecureRandom random = new SecureRandom();
        byte[] init_seed = random.generateSeed(64);
        random.setSeed(init_seed);
    
        //generate 6 parent seeds
        for(int i = 0; i < 6; i++) {
            byte[] next_seed = new byte[64];
            random.nextBytes(next_seed);

            //add to parent_seed array
            parent_seeds.add(next_seed);
        }
    }

    /**
     * Randomly choose a parent seed based on the current system time and sets the version code
     * @return the chosen parent seed
     */
    private static void disturb() {
        //get parent seed from timestamp
        String parent_seed = this.timestamp.getParentIDBits();

        //get/set left and right neighbor seeds
        //generate children based on ver code
        //determine/set child_seed
    }

    /**
     * Generates the seed used for the actual key generation, done by disturbing the hive and integrating
     * the left and right neighbors
     */
    private static void computeActualSeed() {
        //60% MSB of left_seed (eq. 4a)
        //40% LSB of right_seed (eq. 4b)
        //compute/store actual_seed (eq. 5)
    }

    /**
     * Generates the encryption key from the actual seed
     */
    private static void generateKey() {
        //generate the encryption key from the actual_seed
    }

    /**
     * Creates a encryption key sequences with random delays. These keys are then evaluated
     * with ISS and STS and compared to the results of HiveSec's implementation using Random()
     * @param args command line arguments, if any
     */
    public static void main(String[] args) {
        //timestampTesting();
        long ts = System.currentTimeMillis();
        this.timestamp = new HKTimestamp(ts);

        //init parent_seeds array
        parent_seeds = new ArrayList<byte[]>(6);

        generateParents();
        disturb();
 
        //generate 10240 key sequence - output to file
        //delay - random from 0-2 seconds, between each key sequence generated
        //sequence evaluation
    }

    private static void timestampTesting() {
        long timestamp = System.currentTimeMillis();
        HKTimestamp hkt = new HKTimestamp(timestamp);

        System.out.println("Timestamp: " + hkt.getTimestamp());
        System.out.println("Timestamp bits: " + hkt.getTimestampBits());
        System.out.println("Version: " + hkt.getVer());
        System.out.println("Parent ID: " + hkt.getParentIDBits());
        System.out.println("Child ID: " + hkt.getChildIDBits());
    }
}
