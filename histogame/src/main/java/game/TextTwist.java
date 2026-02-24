package game;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TextTwist {
    private static final int MIN_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 6;
    private static final int WORDS_PER_ROW = 5;

    private WordDatabase db;
    private Random random;
    private Word currentWord;
    private String currentWordToDisplay;
    private List<Word> subWords;
    private boolean[] guessed;
    private long startTime;
    private int score;
    private int maxScore;

    public TextTwist(String filename) throws FileNotFoundException {
        this.db = new WordDatabase(filename);
        this.random = new Random();
    }

    // Simplified shuffling using Java's built-in Collections.shuffle
    private String shuffle(String input) {
        List<Character> characters = new ArrayList<>();
        for (char c : input.toCharArray()) {
            characters.add(c);
        }

        Collections.shuffle(characters, this.random);

        StringBuilder output = new StringBuilder(input.length());
        for (char c : characters) {
            output.append(c);
        }
        return output.toString();
    }

    public void newGame() {
        List<Word> words = this.db.getWordWithLength(MAX_WORD_LENGTH);
        int idx = this.random.nextInt(words.size());

        this.currentWord = words.get(idx);
        this.currentWordToDisplay = this.shuffle(this.currentWord.getText());
        this.subWords = this.db.getAllSubWords(this.currentWord, MIN_WORD_LENGTH);

        Collections.sort(this.subWords);
        this.guessed = new boolean[this.subWords.size()];
        this.score = 0;
        this.maxScore = this.subWords.size();
        this.startTime = System.currentTimeMillis();
    }

    public void guess(String word) {
        for (int i = 0; i < this.subWords.size(); i++) {
            Word w = this.subWords.get(i);

            // If the word matches AND hasn't been guessed yet
            if (w.getText().equals(word) && !this.guessed[i]) {
                this.guessed[i] = true;
                this.score++;
                return; // Exit the loop early since we found the match
            }
        }
    }

    public void printStatus() {
        System.out.println("\n--- Current Progress ---");
        int counter = 0;

        for (int i = 0; i < this.subWords.size(); i++) {
            String wordStr = this.subWords.get(i).getText();

            if (this.guessed[i]) {
                System.out.print(wordStr + " ");
            } else {
                // Print question marks for hidden words
                for (int j = 0; j < wordStr.length(); j++) {
                    System.out.print("?");
                }
                System.out.print(" ");
            }

            counter++;
            // Line break every WORDS_PER_ROW words
            if (counter % WORDS_PER_ROW == 0) {
                System.out.println();
            }
        }
        System.out.println("\n");
    }

    public void printPrompt() {
        long millis = System.currentTimeMillis() - this.startTime;
        double seconds = millis / 1000.0;

        System.out.printf("Elapsed Time: %.2f seconds, Score: %d/%d\n",
                seconds, this.score, this.maxScore);
        System.out.println("Commands: q - quit, ! - give up, ? - info");
        System.out.print("[" + this.currentWordToDisplay + "] > ");
    }

    public void reveal() {
        for (int i = 0; i < this.subWords.size(); i++) {
            this.guessed[i] = true;
        }
    }

    public void runGame() {
        this.newGame();
        Scanner sc = new Scanner(System.in);

        // Flattened the double while(true) loop
        while (true) {
            this.printPrompt();
            String command = sc.next().toLowerCase();

            if (command.startsWith("!")) {
                this.reveal();
                this.printStatus();
                System.out.println("Starting a new game...\n");
                this.newGame();
            } else if (command.startsWith("?")) {
                this.printStatus();
            } else if (command.equals("q")) {
                System.out.println("Bye!");
                break; // Break the loop instead of System.exit(0)
            } else {
                this.guess(command);
            }
        }
        sc.close();
    }

    public static void main(String[] args) {
        try {
            TextTwist tt = new TextTwist("linuxwords.txt");
            tt.runGame();
        } catch (FileNotFoundException e) {
            System.out.println("Dictionary file 'linuxwords.txt' not found.");
        }
    }
}