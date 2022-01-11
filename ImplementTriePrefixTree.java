import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * LeetCode 208. Implement Trie (Prefix Tree)
 * https://leetcode.com/problems/implement-trie-prefix-tree/
 */
public class ImplementTriePrefixTree {


    /**
     * Trie class.
     * 
     * Runtime: 31 ms, faster than 76.07% of Java online submissions.
     * Memory Usage: 49.6 MB, less than 69.59% of Java online submissions.
     * 
     * 15 / 15 test cases passed.
     * Status: Accepted
     * Runtime: 31 ms
     * Memory Usage: 49.6 MB
     */
    static class Trie {


        // **** class members ****
        public char     ch;
        public String   string;
        public boolean  end;
        public Trie[]   children;


        /**
         * Constructor.
         */
        public Trie() {
            this.ch         = '*';
            this.children   = new Trie[26];
            this.end        = false;
        }


        /**
         * Constructor.
         */
        public Trie(char ch) {
            this.ch         = ch;
            this.children   = new Trie[26];
            this.end        = false;
        }


        /**
         * Constructor.
         */
        public Trie(char ch, String string) {
            this.ch         = ch;
            this.children   = new Trie[26];
            this.end        = false;
            this.string     = string;
        }


        /**
         * toString.
         */
        @Override
        public String toString() {
            return "(ch: " + this.ch +  " end: " + this.end + " string: " + this.string + ")";
        }


        /**
         * Insert the string word into the trie.
         */
        public String insert(String word) {

            // **** sanity checks ****
            if (word.length() == 0) return word;

            // **** initialization ****
            Trie p  = this;
            int i   = 0;
            
            // **** traverse word checking and inserting nodes ****
            for (char ch : word.toCharArray()) {

                // **** traverse the trie adding / finding nodes with this ch ****
                if (p.children[ch - 'a'] != null) {
                    p = p.children[ch - 'a'];
                } else {

                    // **** create node for this ch ****
                    Trie node = new Trie(ch, word.substring(0, i + 1));

                    // **** add node to Trie ****
                    p.children[ch - 'a'] = node;

                    // **** update p ****
                    p = node;
                }

                // **** increment index ****
                i++;
            }

            // **** flag this ch as the end for word ****
            p.end = true;

            // **** return word ****
            return word;
        }
        

        /**
         * Returns true if the string word is in the trie
         * (i.e., was inserted before), and false otherwise.
         */
        public boolean search(String word) {

            // **** sanity checks ****
            if (word.length() == 0 || word.equals(" ")) return false;

            // **** initialization ****
            Trie p = this;

            // **** traverse the trie looking for word ****
            for (char ch : word.toCharArray()) {

                // **** look for ch in children ****
                if (p.children[ch - 'a'] == null) return false;

                // **** keep on traversing the trie ****
                p = p.children[ch - 'a'];
            }

            // **** return if word was found  ****
            return p.end;
        }
        

        /**
         * Returns true if there is a previously inserted string 
         * word that has the prefix prefix, and false otherwise.
         */
        public boolean startsWith(String prefix) {

            // **** sanity checks ****
            if (prefix.length() == 0) return false;

            // **** initialization ****
            Trie p = this;

            // **** traverse the trie looking for the prefix ****
            for (char ch : prefix.toCharArray()) {

                // **** look for ch in p's children ****
                if (p.children[ch - 'a'] == null) return false;

                // **** keep on traversing the trie ****
                p = p.children[ch - 'a'];
            }

            // **** found prefix in trie ****
            return true;
        }


        /**
         * Returns a list of words if there are one or more 
         * previously inserted string words that have the prefix prefix.
         * If no words are found returns an empty list.
         */
        public List<String> anyWords(String prefix) {

            // **** initialization ****
            List<String> words = new ArrayList<>();

            // **** sanity checks ****
            if (prefix.length() == 0 || prefix.equals(" ")) return words;

            // **** to traverse trie ****
            Trie p = this;

            // **** get to the end of the prefix ****
            for (int i = 0; prefix.charAt(0) != '*' && i < prefix.length(); i++) {

                // **** get current character ****
                char ch = prefix.charAt(i);
    
                // **** ****
                if (p.children[ch - 'a'] == null) return words;

                // **** continue search ****
                p = p.children[ch - 'a'];
            }

            // **** search for full words starting here ****
            findWords(p, words);

            // **** return list of possible words ****
            return words;
        }


        /**
         * Find full words in the trie starting at p
         * and add them to the list.
         * Recursive call.
         */
        public void findWords(Trie p, List<String>words) {

            // **** end condition ****
            if (p == null) return;

            // **** traverse trie ****
            for (Trie child : p.children)
                findWords(child, words);

            // ???? ????
            // System.out.println("findWords <<< p: " + p.toString());

            // **** check if full word ****
            if (p.end == true) {

                // ???? ????
                // System.out.println("<<< found full word ==>" + p.string + "<==");

                // **** add string to list ****
                words.add(p.string);
            }
        }

    }


    /**
     * Test scaffold
     * @throws IOException
     * !!! NOT PART OF THE SOLUTION !!!
     */
    public static void main(String[] args) throws IOException {

        // **** initialization ****
        Trie trie = null;

        // **** open buffered reader ****
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // **** read command sequence ****
        String[] cmdArr = Arrays.stream(br.readLine().trim().split(", "))
                            .toArray(size -> new String[size]);

        // **** read argument sequence ****
        String[] argArr = Arrays.stream(br.readLine().trim().split(", "))
                            .toArray(size -> new String[size]);

        // **** close buffered reader ****
        br.close();

        // **** check array lengths ****
        if (cmdArr.length != argArr.length) {
            System.err.println("main <<< cmdArr.length: " + cmdArr.length + 
                                " != argArr.length: " + argArr.length);
            System.exit(-1);
        }
        
        // ???? ????
        System.out.println("main <<< cmdArr: " + Arrays.toString(cmdArr));
        System.out.println("main <<< argArr: " + Arrays.toString(argArr));

        // **** loop once per command ****
        for (int i = 0; i < cmdArr.length; i++) {

            // **** proceed based on command ****
            switch (cmdArr[i]) {
                case "Trie":

                    // **** instantiate Trie ****
                    trie = new Trie();

                    // ???? ????
                    System.out.println("main <<< trie: " + trie.toString());
                break;

                case "insert":
                    System.out.println("main <<< insert(" + argArr[i] + "): " + trie.insert(argArr[i]));
                break;

                case "search":
                    System.out.println("main <<< search(" + argArr[i] + "): " + trie.search(argArr[i]));
                break;

                case "startsWith":
                    System.out.println("main <<< startsWith(" + argArr[i] + "): " + trie.startsWith(argArr[i]));
                break;

                case "anyWords":
                    System.out.println("main <<< anyWords(" + argArr[i] + "): " + trie.anyWords(argArr[i]));
                break;

                default:
                    System.out.println("main <<< unexpected cmdArr[" + i + "]: " + cmdArr[i]);
                break;
            }
        }
    }
}