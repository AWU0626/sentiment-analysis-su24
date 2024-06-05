import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * 
 * Main class for the sentiment analysis program.
 * 
 */
public class Main {

	public static void main(String[] args) {

		/* Implement this in Part 4 */
		if (args.length == 0) {
			System.out.println("no input file");
		} else {
			try {
				Set<Sentence> sentences = Reader.readFile(args[0]);
				Map<String, Double> wordScores = Analyzer.calculateWordScores(sentences);

				Scanner scanner = new Scanner(System.in);
				while (true) {
					System.out.println("Enter a sentence (or 'quit' to exit): ");
					String userSentence = scanner.nextLine();

					if (userSentence.equals("quit")) {
						break;
					}

					double score = Analyzer.calculateSentenceScore(wordScores, userSentence);
					System.out.println("Sentence score: " + score);
				}

				scanner.close();

			} catch (IllegalArgumentException e) {
				System.out.println("bad file input");
				return;
			}
		}
	}

}
