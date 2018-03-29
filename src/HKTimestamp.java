/**
 * Class HKTimestamp for the implementation of Secure HiveKey
 * Stores the timestamp, version code, and parent/child ID bits
 */
public class HKTimestamp {

    private long timestamp;
    private String bits;
    private int ver;
    private String parent_id_bits;
    private String child_id_bits;

    /**
     * HKTimestamp Constructor
     */
    public HKTimestamp(long timestamp){
        try {
            this.timestamp = timestamp;
            this.bits = Long.toBinaryString(timestamp);
            this.ver = Integer.parseInt(this.bits.substring(this.bits.length() - 1));
            this.parent_id_bits = this.bits.substring(this.bits.length() - 3);

            //set child ID bits based on version code
            if(this.ver == 0) {
                this.child_id_bits = this.bits.substring(this.bits.length() - 7, this.bits.length() - 4);
            } else if(this.ver == 1) {
                this.child_id_bits = this.bits.substring(this.bits.length() - 9, this.bits.length() - 4);
            }
        } catch (Exception e) {
            System.out.println("Error in HKTimestamp constructor: " + e.toString());
        }
    }

    /**
     * Retrieve the stored timestamp
     */
    public long getTimestamp() {
        return this.timestamp;
    }

    /**
     * Retrieve the bit string for the stored timestamp
     */
    public String getTimestampBits() {
        return this.bits;
    }

    /**
     * Retrieve the version code
     */
    public int getVer() {
        return this.ver;
    }

    /**
     * Retrieve the parent ID bits
     */
    public String getParentIDBits() {
        return this.parent_id_bits;
    }

    /**
     * Retrieve the child ID bits
     */
    public String getChildIDBits() {
        return this.child_id_bits;
    }

    @Override
    public String toString() {
        String timestamp_string = "Timestamp: " + this.timestamp
                + "\nTimestamp bits: " + this.bits
                + "\nVersion: " + this.ver
                + "\nParent ID: " + this.parent_id_bits
                + "\nChild ID: " + this.child_id_bits;
        return timestamp_string;
    }
}
