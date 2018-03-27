import java.util.ArrayList;
import java.util.Arrays;
import java.security.SecureRandom;

public class HiveKey {
    private static byte[] actual_seed; //SecureRandom.generateSeed(64 bits)
    private static int key; //SecureRandom.next(128 bits);
    private static ArrayList<byte[]> parent_seeds;
    private static HKTimestamp timestamp;
    private static byte[] left_seed;
    private static byte[] right_seed;
    private static byte[] child_seed;

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
     * Randomly choose a parent seed and set the left/right neighbor seeds
     * @return the chosen parent seed
     */
    private static byte[] disturb() {
        //get parent seed from timestamp
        String p_id = timestamp.getParentIDBits();
        int p_index = (Integer.parseInt(p_id, 2) % 6);
        byte[] p = parent_seeds.get(p_index);
        
        //get/set left and right neighbor seeds
        left_seed = parent_seeds.get((p_index - 1) % 6);
        right_seed = parent_seeds.get((p_index + 1) % 6);

        return p;
    }

    /**
     * Generate the child seed
     */
    private static void generateChild(byte[] p) {
        //generate a random number using parent_seed as seed
        SecureRandom random = new SecureRandom();
        random.setSeed(p);

        //get child id from timestamp and num children
        String c_id = timestamp.getChildIDBits();
        int num_children = 6;
        if(timestamp.getVer() == 1) {
            num_children = 18;
        }

        //calculate index
        int c_index = (Integer.parseInt(c_id, 2) % num_children);
        ArrayList<byte[]> child_seeds = new ArrayList<byte[]>(num_children);
        for(int i = 0; i < num_children; i++) {
            byte[] next_seed = new byte[64];
            random.nextBytes(next_seed);

            //add to child_seeds array
            child_seeds.add(next_seed);
        }        

        child_seed = child_seeds.get(c_index);
    }

    /**
     * TODO: THIS FUNCTION
     * Generates the seed used for the actual key generation, done by disturbing the hive and integrating
     * the left and right neighbors
     */
    private static void computeActualSeed() {
        //60% MSB of left_seed (eq. 4a)
        //40% LSB of right_seed (eq. 4b)
        //compute/store actual_seed (eq. 5)
    }

    /**
     * TODO: THIS FUNCTION
     * Generates the encryption key from the actual seed
     */
    private static void generateKey() {
        //generate the encryption key from the actual_seed
    }

    /**
     * FIXME: COMPLETE THIS FUNCTION
     * Creates a encryption key sequences with random delays. These keys are then evaluated
     * with ISS and STS and compared to the results of HiveSec's implementation using Random()
     * @param args command line arguments, if any
     */
    public static void main(String[] args) {
        long ts = System.currentTimeMillis();
        timestamp = new HKTimestamp(ts);
        //timestampInfo();

        //init parent_seeds array
        parent_seeds = new ArrayList<byte[]>(6);
        left_seed = new byte[64];
        right_seed = new byte[64];

        generateParents();
        byte[] p = disturb();
        generateChild(p);
 
        //generate 10240 key sequence - output to file
        //delay - random from 0-2 seconds, between each key sequence generated
        //sequence evaluation
    }

    private static void timestampInfo() {
        System.out.println("Timestamp: " + timestamp.getTimestamp());
        System.out.println("Timestamp bits: " + timestamp.getTimestampBits());
        System.out.println("Version: " + timestamp.getVer());
        System.out.println("Parent ID: " + timestamp.getParentIDBits());
        System.out.println("Child ID: " + timestamp.getChildIDBits());
    }
}
