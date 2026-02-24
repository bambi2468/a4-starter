package game;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// HINT(s):
//   To read from src/resources/<filename>
//   InputStream is = getClass().getClassLoader().getResourceAsStream(filename);

public class WordDatabase implements IDatabase {
    private List<String> wordList;

    public WordDatabase(String filename) throws FileNotFoundException{
        this.wordList = new ArrayList<>();
        
        InputStream is = getClass().getClassLoader().getResourceAsStream(filename);
        
        // check file
        if (is == null) {
            System.out.println("Can't find the file in resources: " + filename);
            throw new FileNotFoundException();
        }
        
        // read file
        try {
            Scanner scanner = new Scanner(is);
            while (scanner.hasNextLine()) {
                wordList.add(scanner.nextLine().trim());
            }
            scanner.close();
        } catch(Exception e) {
            System.out.println("An error occurred while reading the file.");
        }
    }

    @Override
    public List<Word> getWordWithLength(int l) {
        List<Word> all = new ArrayList<>();
        for (String text : wordList) {
            if (text.length() == l) {
                all.add(new Word(text));
            }
        }
        return all;
    }

    @Override
    public List<Word> getAllSubWords(Word w, int minLen) {
        List<Word> all = new ArrayList<>();
        for (String text : wordList) {
            Word dictWord = new Word(text);
            if (w.canForm(dictWord) && text.length() >= minLen) {
                all.add(dictWord);
            }
        }
        return all;
    }

    @Override
    public boolean contains(Word o) {
        return wordList.contains(o.getWord());
    }
    
    @Override
    public void add(Word w) {
        wordList.add(w.getWord());
    }

    @Override
    public void remove(Word w) {
        wordList.remove(w.getWord());
    }
}