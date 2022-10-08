import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class Outcast {
    private WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        if (nouns == null) {
            throw new IllegalArgumentException();
        }
        int max = Integer.MIN_VALUE;
        int index = 0;
        for (int i = 0; i < nouns.length; i++) {
            int sumDistance = 0;
            for (int j = 0; j < nouns.length; j++) {
                sumDistance += wordNet.distance(nouns[i], nouns[j]);
            }
            if (max < sumDistance) {
                max = sumDistance;
                index = i;
            }
        }
        return nouns[index];
    }
}