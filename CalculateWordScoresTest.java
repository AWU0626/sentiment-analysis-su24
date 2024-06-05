import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculateWordScoresTest {

    Analyzer analyzer = new Analyzer();
    static Set<Sentence> setOne = new HashSet<>();
    static Set<Sentence> setTwo = new HashSet<>();

    @Before
    public void setUp() {
        setOne.add(new Sentence(-2, "!this !is Bad ! {"));

        setTwo.add(new Sentence(-2, "!this !is Bad !"));
        setTwo.add(new Sentence(1, "I like it even if it's bAd ."));
        setTwo.add(new Sentence(2, "this. only contributes on If and iF but not if. "));
    }

    @Test
    public void testParamNullSet() {
        Assert.assertEquals(0, Analyzer.calculateWordScores(null).size());
    }

    @Test
    public void testParamEmptySet() {
        Assert.assertEquals(0, Analyzer.calculateWordScores(new HashSet<>()).size());
    }

    @Test
    public void testNullSentenceWithoutScoreInSet() {
        Set<Sentence> emptySentence = new HashSet<>();
        emptySentence.add(null);

        Map<String, Double> emptyMap = Analyzer.calculateWordScores(emptySentence);

        // Size of map should be 0
        // Any input should get null
        Assert.assertEquals(0, emptyMap.size());
        Assert.assertNull(emptyMap.get("no"));
        Assert.assertNull(emptyMap.get("hi !"));

    }

    @Test
    public void testNullSentenceWithScoreInSet() {
        Set<Sentence> emptySentence = new HashSet<>();
        emptySentence.add(new Sentence(0, null));

        Map<String, Double> emptyMap = Analyzer.calculateWordScores(emptySentence);

        // Size of map should be 0
        // Any input should get null
        Assert.assertEquals(0, emptyMap.size());
        Assert.assertNull(emptyMap.get("not"));
        Assert.assertNull(emptyMap.get("hi"));

    }

    @Test
    public void testSentenceScoreUnderbound() {
        Set<Sentence> underBoundSet = new HashSet<>();
        underBoundSet.add(new Sentence(-3, "hi, this does not work ."));

        Map<String, Double> map = Analyzer.calculateWordScores(underBoundSet);

        // Size of map should be 0
        // Any input should get null
        Assert.assertEquals(0, map.size());
        Assert.assertNull(map.get("not"));
        Assert.assertNull(map.get("hi"));
    }

    @Test
    public void testSentenceScoreOverbound() {
        Set<Sentence> overBoundSet = new HashSet<>();
        overBoundSet.add(new Sentence(3, "hi, this does not work ."));

        Map<String, Double> map = Analyzer.calculateWordScores(overBoundSet);

        // Size of map should be 0
        // Any input should get null
        Assert.assertEquals(0, map.size());
        Assert.assertNull(map.get("not"));
        Assert.assertNull(map.get("hi"));
    }

    @Test
    public void testSimpleWordScore() {
        Map<String, Double> map = Analyzer.calculateWordScores(setOne);

        // encodes only 'Bad' and stores as 'bad' with -2
        Assert.assertEquals(1, map.size());
        Assert.assertEquals(-2, map.get("bad"), 3);
        Assert.assertNull(map.get("Bad"));
        Assert.assertNull(map.get("not"));
    }

    @Test
    public void testComplexWordScore() {
        Map<String, Double> map = Analyzer.calculateWordScores(setTwo);

        String[] listOfScoreOne = new String[] { "i", "like", "it", "even", "it's" };
        String[] listOfScoreTwo = new String[] { "this.", "only", "contributes", "on", "and", "but", "not", "if." };

        Assert.assertEquals(15, map.size());
        Assert.assertEquals(-0.5, map.get("bad"), 3);
        Assert.assertEquals(1.667, map.get("if"), 3);

        for (String str : listOfScoreOne) {
            Assert.assertEquals(1, map.get(str), 3);
        }

        for (String str : listOfScoreTwo) {
            Assert.assertEquals(2, map.get(str), 3);
        }
    }

}
