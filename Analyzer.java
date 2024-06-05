
import java.util.*;

public class Analyzer {
	public static Map<String, Double> calculateWordScores(Set<Sentence> sentences) {
		/*
		 * Implement this method in Part 2
		 */

		if (sentences == null || sentences.isEmpty()) {
			return new HashMap<String, Double>();
		}

		Map<String, Double> wordScoreMap = new HashMap<>(),
				wordOccurrences = new HashMap<>();

		for (Sentence sentence : sentences) {
			if (sentence == null ||
					sentence.getText() == null ||
					sentence.getScore() < -2 ||
					sentence.getScore() > 2) {
				continue;
			} else {
				String text = sentence.getText();
				int score = sentence.getScore();

				ArrayList<String> words = wordProcessing(text);

				for (String word : words) {
					if (!wordScoreMap.containsKey(word)) {
						wordScoreMap.put(word, (double) score);
						wordOccurrences.put(word, 1.0);
					} else {
						wordScoreMap.put(word, (double) score + wordScoreMap.get(word));
						wordOccurrences.put(word, wordOccurrences.get(word) + 1.0);
					}
				}
			}
		}

		// get weighted sentiment score of each word
		Set<String> sentimentWords = wordScoreMap.keySet();
		for (String sentimentWord : sentimentWords) {
			double numerator = wordScoreMap.get(sentimentWord),
					denominator = wordOccurrences.get(sentimentWord);

			wordScoreMap.put(sentimentWord, numerator / denominator);
		}

		return wordScoreMap;
	}

	// lowercase all words, ignore words that do not begin with a letter
	static ArrayList<String> wordProcessing(String currSentence) {
		StringTokenizer tokenizer = new StringTokenizer(currSentence, " ");

		ArrayList<String> validTokensList = new ArrayList<>();
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken().toLowerCase();
			if (token.charAt(0) >= 'a' && token.charAt(0) <= 'z') {
				validTokensList.add(token);
			}
		}

		return validTokensList;
	}

	public static double calculateSentenceScore(Map<String, Double> wordScores, String sentence) {
		/*
		 * Implement this method in Part 3
		 */
		if (wordScores.isEmpty() ||
				sentence == null ||
				sentence.isEmpty()) {
			return 0;
		}

		ArrayList<String> words = wordProcessing(sentence);
		double score = 0, len = 0;

		for (String word : words) {
			if (wordScores.containsKey(word)) {
				score += wordScores.get(word);
			}

			len += 1;
		}

		if (len == 0) {
			return 0;
		}

		return score / len;
	}
}
