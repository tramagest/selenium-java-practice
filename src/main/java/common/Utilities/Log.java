package common.Utilities;

import com.aventstack.extentreports.ExtentTest;
import org.testng.asserts.SoftAssert;

import java.util.Objects;

public class Log {

    private static final ThreadLocal<ExtentTest> current = new ThreadLocal<>();

    public static void set(ExtentTest test) {
        current.set(test);
    }

    public static ExtentTest get() {
        return current.get();
    }

    // Ghi một step vào report
    public static void step(String message) {
        if (current.get() != null) {
            current.get().info(message);
        }
    }

    public static boolean verify(String what, Object expected, Object actual) {
        boolean ok = Objects.equals(expected, actual);
        if (current.get() != null) {
            String text = "Verify " + what + ": expected [" + expected + "], actual [" + actual + "]";
            if (ok) {
                current.get().pass(text);
            } else {
                current.get().fail(text);
            }
        }
        return ok;
    }


    public static void info(String message) {
        if (current.get() != null) {
            current.get().info(message);
        }
    }



    public static void verify(SoftAssert soft, String what, Object expected, Object actual) {
        verify(what, expected, actual);
        soft.assertEquals(actual, expected, what);
    }
}