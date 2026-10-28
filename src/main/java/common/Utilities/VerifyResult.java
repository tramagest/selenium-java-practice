package common.Utilities;


public class VerifyResult {
    private final boolean result;
    private final String expected;
    private final String actual;

    public VerifyResult(boolean result, String expected, String actual) {
        this.result = result;
        this.expected = expected;
        this.actual = actual;
    }

    public boolean isResult() {
        return result;
    }

    public String getExpected() {
        return expected;
    }

    public String getActual() {
        return actual;
    }
}

