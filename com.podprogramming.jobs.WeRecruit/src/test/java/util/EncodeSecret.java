package util;

import java.nio.charset.Charset;

public class EncodeSecret {

    private static final Charset utf8 = Charset.forName("utf8");
    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        String content = Resources.loadXMLProperties("/labels.xml").getProperty("secret");
        int count = 0;
        for(int b : content.getBytes(utf8)) {
            System.out.print(b);
            System.out.print(", ");
            if(++count % 10 == 0)
                System.out.println();
        }
    }

}
