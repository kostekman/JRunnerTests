import static java.lang.Math.PI;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;

import org.junit.Test;

public class FormatNumberTutor {

    /**
     * Should print the number in format "111.12" or "3.14"
     * (2 decimal places, separated by the dot)
     */
    public String fomatNumber(String s, double n) {
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb, Locale.FRANCE);
        f.format(Locale.CANADA, s, n);
        return sb.toString();
    }

    public void formatNumber() {
        long n = 461012;
        System.out.format("%d%n", n);      //  -->  "461012"
        System.out.format("%04d%n", n);    //  -->  "00461012"
        System.out.format("%+8d%n", n);    //  -->  " +461012"
        System.out.format("%,8d%n", n);    // -->  " 461,012"
        System.out.format("%+,8d%n%n", n); //  -->  "+461,012"

        double pi = PI;

        System.out.format("%f%n", pi);       // -->  "3.141593"
        System.out.format("%.3f%n", pi);     // -->  "3.142"
        System.out.format("%10.3f%n", pi);   // -->  "     3.142"
        System.out.format("%-10.3f%n", pi);  // -->  "3.142     "
        System.out.format(Locale.FRANCE,
                "%-10.4f%n%n", pi); // -->  "3,1416"

        Calendar c = Calendar.getInstance();
        System.out.format("%tB %te, %tY%n", c, c, c); // -->  "May 29, 2006"
        System.out.format("%tl:%tM %tp%n", c, c, c);  // -->  "2:34 am"
        System.out.format("%tD%n", c);    // -->  "05/29/06"
    }

    @Test
    public void testFormatNumber() {
        formatNumber();
        String number = fomatNumber("%.2f", PI);
        System.out.printf(Locale.CANADA,"%.2f", PI);
        assertEquals("3.14", number);

    }
}
