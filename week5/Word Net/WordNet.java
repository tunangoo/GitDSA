import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RedBlackBST;

import java.util.ArrayList;
import java.util.List;

public class WordNet {
    private RedBlackBST<String, List<Synonym>> symbolTable = new RedBlackBST<>();
    private SAP sap;
    private final Synonym[] synonyms;

    private class Synonym {
        int id;
        String[] synset;

        Synonym(int id, String[] synset) {
            this.id = id;
            this.synset = synset;
        }
    }

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }
        In synsetsIn = new In(synsets);
        int maxId = -1;
        while (!synsetsIn.isEmpty()) {
            String line = synsetsIn.readLine();
            String[] value = line.split(",");
            String[] words = value[1].split(" ");
            int id = Integer.parseInt(value[0]);
            maxId = Math.max(maxId, id);
            Synonym synonym = new Synonym(id, words);
            for (String word : words) {
                List<Synonym> lookUp = symbolTable.get(word);
                if (lookUp == null) {
                    lookUp = new ArrayList<>();
                }
                lookUp.add(synonym);
                symbolTable.put(word, lookUp);
            }
        }
        synonyms = new Synonym[maxId + 1];
        for (String key : symbolTable.keys()) {
            List<Synonym> symbols = symbolTable.get(key);
            for (Synonym s : symbols) {
                synonyms[s.id] = s;
            }
        }
        Digraph digraph = new Digraph(maxId + 1);
        In hypernymsIn = new In(hypernyms);
        while (!hypernymsIn.isEmpty()) {
            String line = hypernymsIn.readLine();
            String[] value = line.split(",");
            int u = Integer.parseInt(value[0]);
            for (int i = 1; i < value.length; i++) {
                int v = Integer.parseInt(value[i]);
                digraph.addEdge(u, v);
            }
        }
        /*
        Topological topo = new Topological(digraph);
        if (!topo.hasOrder()) {
            throw new IllegalArgumentException();
        }
        */
        int root = -1;
        for (int v = 0; v < digraph.V(); v++) {
            if (digraph.outdegree(v) == 0) {
                if (root >= 0) {
                    throw new IllegalArgumentException();
                }
                root = v;
            }
        }
        sap = new SAP(digraph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return symbolTable.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return symbolTable.contains(word);
    }

    private List<Integer> list(String word) {
        List<Integer> list = new ArrayList<>();
        for (Synonym s : symbolTable.get(word)) {
            list.add(s.id);
        }
        return list;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        return sap.length(list(nounA), list(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if ((nounA == null) || (nounB == null)) {
            throw new IllegalArgumentException();
        }
        if (!isNoun(nounA) || !(isNoun(nounB))) {
            throw new IllegalArgumentException();
        }
        int ancestor = sap.ancestor(list(nounA), list(nounB));
        if (ancestor < 0) {
            return null;
        }
        return String.join(" ", synonyms[ancestor].synset);
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}