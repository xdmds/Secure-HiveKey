public class HKTimestamp {
    private long timestamp;
    private String timestamp_bits;
    private int ver;
    private String parent_id_bits;
    private String child_id_bits;

    public HKTimestamp(long timestamp){
        try {
            this.timestamp = timestamp;
            this.timestamp_bits = Long.toBinaryString(timestamp);
            this.ver = Integer.parseInt(this.timestamp_bits.substring(this.timestamp_bits.length() - 1));
            this.parent_id_bits = this.timestamp_bits.substring(this.timestamp_bits.length() - 3);

            if(this.ver == 0) {
                this.child_id_bits = this.timestamp_bits.substring(this.timestamp_bits.length() - 7, this.timestamp_bits.length() - 4);
            } else if(this.ver == 1) {
                this.child_id_bits = this.timestamp_bits.substring(this.timestamp_bits.length() - 9, this.timestamp_bits.length() - 4);
            }
        } catch (Exception e) {
            System.out.println("Error in HKTimestamp constructor: " + e.toString());
        }
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public String getTimestampBits() {
        return this.timestamp_bits;
    }

    public int getVer() {
        return this.ver;
    }

    public String getParentIDBits() {
        return this.parent_id_bits;
    }

    public String getChildIDBits() {
        return this.child_id_bits;
    }
}
