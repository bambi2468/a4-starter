package game;

import histogram.Histogram;
import histogram.SimpleHistogram;

public class Word implements Formable<Word>, Comparable<Word> {

    private String word;
    private Histogram<Character> hist;

    // The only constructor of the class, which takes a String representation
    // of the word. The histogram is generated here from the given word.
    public Word(String word) {
        this.word = word;
        Character[] chars = new Character[word.length()];

        for (int i = 0; i < chars.length; i++) {
            char c = word.charAt(i);
            chars[i] = c;
        }
        this.hist = new SimpleHistogram<>(chars);
    }

    // Returns the String representation of the word.
    public String getWord() {
        return word;
    }

    // Returns a Histogram describing the character distribution of the word.
    public Histogram<Character> getHistogram() {
        return hist;
    }

    // Returns true if the Word represented by other can be formed using some
    // or all of the characters of this word.
    public boolean canForm(Word other) {
        Histogram<Character> otherHist = other.getHistogram();
        for (char c : otherHist) {
            int thisCount = hist.getCount(c);
            int otherCount = otherHist.getCount(c);
            if (thisCount < otherCount) {
                return false;
            }
        }
        return true;

    }

    // Return -1 if this word is shorter than the word represented by o OR
    // when this word and the word represented by o have the same length but
    // this word comes before the word represented by o alphabetically.
    // Return zero if this word and o word are identical.
    // Return +1 otherwise.
    public int compareTo(Word o) {
        if (this.word.length() < o.word.length()|| o.word.compareTo(this.word) == 1){
            return -1;
        }
        if (o.word.compareTo(this.word) == 0){
            return 0;
        }
        return 1;
    }

    public String getText() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getText'");
    }
}
