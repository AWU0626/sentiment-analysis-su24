import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculateSentenceScoreTest {

    Map<String, Double> scoreMap;
    String testSentenceOne,
            testSentenceTwo,
            testSentenceInvalid;

    @Before
    public void setUp() {
        testSentenceOne = "Simple sentence score .";
        testSentenceTwo = "!Complex SenTenCe score against invalid !sentence .";
        testSentenceInvalid = "! ! !";

        scoreMap = new HashMap<>();
        scoreMap.put("simple", 0.0);
        scoreMap.put("sentence", 0.4);
        scoreMap.put("score", -1.0);
        scoreMap.put("invalid", -2.0);
    }

    @Test
    public void testEmptyMapValidSentence() {
        Map<String, Double> emptyMap = new HashMap<>();

        double result = Analyzer.calculateSentenceScore(emptyMap, testSentenceOne);
        Assert.assertEquals(0, result, 3);
    }

    @Test
    public void testNullString() {
        double result = Analyzer.calculateSentenceScore(scoreMap, null);
        Assert.assertEquals(0, result, 3);
    }

    @Test
    public void testEmptyString() {
        double result = Analyzer.calculateSentenceScore(scoreMap, "");
        Assert.assertEquals(0, result, 3);
    }

    @Test
    public void testAllInvalidString() {
        double result = Analyzer.calculateSentenceScore(scoreMap, testSentenceInvalid);
        Assert.assertEquals(0, result, 3);
    }

    @Test
    public void testSimple() {
        double result = Analyzer.calculateSentenceScore(scoreMap, testSentenceOne);
        Assert.assertEquals(-0.2, result, 3);
    }

    @Test
    public void testComplex() {
        double result = Analyzer.calculateSentenceScore(scoreMap, testSentenceTwo);
        Assert.assertEquals(-0.65, result, 3);
    }

}
