import java.io.*;

public class SSI {
    public static int keypair_comparison(String key_a, String key_b) {
        String[] a = key_a.split("");
        String[] b = key_b.split("");
        int total = 0;
        for(int i = 0; i < a.length; i++) {
            if(a[i].equals(b[i]))
                total++;
        }
        return total;
    }
    public static void usage() {
        System.out.println("usage: java SSI <filename>");
        System.out.println("<filename>: file containing list of keys");
        System.exit(1);
    }

    public static void main(String[] args) throws Exception {
        if(args.length != 1) {
            usage();
        }

        String filename = args[0];
        File file = new File("../" + filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String key_a;
        String key_b = "";
        double total_ssi = 0;
        int comparison = 0;

        while((key_a = br.readLine()) != null) {
            if(key_b.isEmpty()) {
                key_b = key_a;
                continue;
            }
            comparison++;
            int numerator = keypair_comparison(key_a, key_b);
            int denominator = key_a.length() + key_b.length();
            double ssi = (2 * numerator) / (double)denominator;
            total_ssi += ssi;
            key_b = key_a;
        }
        double ssi_avg = total_ssi / comparison;
        System.out.println("ssi avg: " + ssi_avg);
    }
}