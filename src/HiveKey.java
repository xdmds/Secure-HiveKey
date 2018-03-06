import java.util.ArrayList;

public class HiveKey {
    private byte[] actual_seed; //SecureRandom.generateSeed(64 bits)
    private int key; //SecureRandom.next(128 bits);
    private ArrayList<ArrayList<byte[]>> parent_seeds; //SecureRandom.generateSeed(64 bits)
    private int ver;

    /**
     * Generates a random seed used to randomize the 6 parent seeds for HiveKey
     */
    private void generateParents() {
        //generate random seed
        //generate 6 parent seeds -- SecureRandom.nextBytes(byte[64] bytearray)
        //store in parent_seeds array
    }

    /**
     * Randomly choose a parent seed based on the current system time and sets the version code
     * @return the chosen parent seed
     */
    private byte[] disturb() {
        //System.currentTimeMillis()
        //set ver = nine LSB of timestamp
        //generate children based on ver code
        //determine child number
    }

    /**
     * Generates the seed used for the actual key generation, done by disturbing the hive and integrating
     * the left and right neighbors
     */
    private void computeActualSeed() {
        //consider two neighbors of chosen seed from disturb()
        //set actual_seed
    }

    /**
     * Generates the encryption key from the actual seed
     */
    private void generateKey() {
        //generate the encryption key from the actual_seed
    }

    /**
     * Creates a encryption key sequences with random delays. These keys are then evaluated
     * with ISS and STS and compared to the results of HiveSec's implementation using Random()
     * @param args command line arguments, if any
     */
    public static void main(String[] args) {
        //generate 10240 key sequences with random delays
        //sequence evaluation
    }
}
