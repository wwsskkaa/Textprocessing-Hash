/*Name:Shuang Wu
 Wordie.java: Wordie stores a word, a sonnet num and line num and index num for those words in the sonnets
 another instructor can be used for other articles where there won't be location needed. so it just store a word.
 10/31/2014
 */
public class Wordie {
	public String word, roman;
	public int line, wordNumber;

	public Wordie(String Word, String Sonnetnum, int Linenum, int Indexnum) {
		word = Word;
		roman = Sonnetnum;
		line = Linenum;
		wordNumber = Indexnum;
	}

	// I have two constructors so the sonnets can call the first one and others
	// can call the one below.
	public Wordie(String Word) {
		word = Word;

	}// end constructor
}// end class