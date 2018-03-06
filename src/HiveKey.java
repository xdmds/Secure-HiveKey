import java.util.ArrayList;

public class HiveKey {
    private byte[] actual_seed; //SecureRandom.generateSeed(64 bits)
    private int key; //SecureRandom.next(128 bits);
    private ArrayList<ArrayList<byte[]>> parent_seeds; //SecureRandom.generateSeed(64 bits)

    /**
     * Generates a random seed used to randomize the 6 parent seeds for HiveKey
     */
    private void generateParents() {
        //generate random seed
        //generate 6 parent seeds -- SecureRandom.nextBytes(byte[64] bytearray)
        //store in parent_seeds array
    }


}
