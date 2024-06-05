
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

public class Reader {
	/**
	 * This method reads sentences from the input file, creates a Sentence object
	 * for each, and returns a Set of the Sentences.
	 * 
	 * @param filename Name of the input file to be read
	 * @return Set containing one Sentence object per sentence in the input file
	 * @throws IllegalArgumentException if filename is null
	 */
	public static Set<Sentence> readFile(String filename) {
		/*
		 * Implement this method in Part 1
		 */
		if (filename == null) {
			throw new IllegalArgumentException("filename is null");
		}

		Set<Sentence> sentences = new HashSet<>();

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String currSentence;
			while ((currSentence = br.readLine()) != null) {
				if (isValidSentence(currSentence)) {
					sentences.add(parseSentence(currSentence));
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("filename cannot be found");
		}

		return sentences;
	}

	// Returns true if all error checks are passed
	public static boolean isValidSentence(String currSentence) {
		// empty string
		if (currSentence.isEmpty()) {
			return false;
		}

		// check entire string length is more than 1 thing
		String[] split = currSentence.split(" ", 2);
		if (split.length < 2) {
			return false;
		}

		// check for valid score format
		try {
			int rating = Integer.parseInt(split[0]);

			// check rating range [-2, 2]
			if (rating <= -2 && rating >= 2) {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	// Takes in valid string sentence and parse into integer rating, string content
	public static Sentence parseSentence(String currSentence) {
		String[] sentenceSplit = currSentence.split(" ", 2);
		return new Sentence(Integer.parseInt(sentenceSplit[0]), sentenceSplit[1]);
	}
}
