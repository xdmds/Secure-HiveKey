import java.util.ArrayList;
import java.util.Arrays;
import java.security.SecureRandom;

public class HiveKey {
    private static long actual_seed; //SecureRandom.generateSeed(64 bits)
    private static byte[] key; //SecureRandom.next(128 bits);
    private static ArrayList<byte[]> parent_seeds;
    private static HKTimestamp timestamp;
    private static byte[] left_seed;
    private static long left_seed_prime;
    private static long right_seed_prime;
    private static byte[] right_seed;
    private static byte[] child_seed;
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * Generates a random seed used to randomize the 6 parent seeds for HiveKey
     */
    private static void generateParents() {
        //generate a random seed
        SecureRandom random = new SecureRandom();
        byte[] init_seed = random.generateSeed(8);
        random.setSeed(init_seed);
    
        //generate 6 parent seeds
        for(int i = 0; i < 6; i++) {
            byte[] next_seed = new byte[8];
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
        
        //get/set left and right neighbor seedsp
        int left_index = (p_index + 5) % 6;
        left_seed = parent_seeds.get(left_index);
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
            byte[] next_seed = new byte[8];
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
        left_seed_prime = ByteUtils.bytesToLong(left_seed) & 0xfffffffffc000000L; //most significant 38 bits are 1. 60% is 38.4

        //40% LSB of right_seed (eq. 4b)
        right_seed_prime = ByteUtils.bytesToLong(right_seed) & 0x1ffffffL; //least significant 25 bits are 1. 40% is 25.6

        //compute/store actual_seed (eq. 5)
        actual_seed = ByteUtils.bytesToLong(child_seed)
                ^ (left_seed_prime | right_seed_prime)
                ^ timestamp.getTimestamp(); //isn't the timestamp 32 bits?
    }

    /**
     * TODO: THIS FUNCTION
     * Generates the encryption key from the actual seed
     */
    private static void generateKey() {
        SecureRandom random = new SecureRandom();
        random.setSeed(actual_seed);

        //retrieve 4 lsbs from timestamp and used as state s
        int state = Integer.parseInt(timestamp.getTimestampBits().substring(timestamp.getTimestampBits().length() - 4), 2);

        //get the sth sequence generated by the PRNG to be used as the key
        byte[] temp_key = new byte[16];
        System.out.println(state);
        for(int i = 0; i < state; i++) {
            random.nextBytes(temp_key);
            System.out.println(temp_key);
        }
        key = temp_key;
    }

    public static String bytesToHex(byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for(byte b : in) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
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
        left_seed = new byte[8];
        right_seed = new byte[8];

        generateParents();
        byte[] p = disturb();
        generateChild(p);
        computeActualSeed();
        key = new byte[8];
        generateKey();

        System.out.println(bytesToHex(key));
        //generate 10240 key sequence - output to file
        //delay - random from 0-2 seconds, between each key sequence generated
        //sequence evaluation
    }
}
