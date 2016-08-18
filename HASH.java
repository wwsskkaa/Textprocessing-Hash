import java.util.*;
/*Name:Shuang Wu

HASH.java: this one contains all the steps which used for creating concordance. I used a 2d array to store all the words locs.
So basically, every row stores the entire set of locs for the same word. The word are stored in Wordie type(another class).
I need to use hashing to hash words in, but since it might easily get a collision, I have to use double hashing.
So the size of my 2d array (# of rows) should be a prime number. so I have a nextPrime() method which returns the next prime of current capacity*100
so every time I need to expand the row number, I just call the expand method which creates a new 2d array of nextprime size and rehash all the current ones in
whenever I add a new loc, I expand the size of that row by 1.

10/31/2014
*/
public class HASH {
	int capacity;
	// THE CAPACITY-THE ROW # OF THE 2D ARRAY
	Wordie[][] TWODTABLE;
	// where the locs of sonnet words are stored
	ArrayList<String> keys;

	public HASH(int givenCapacity) {
		if (givenCapacity <= 0) {
			capacity = 101;
		}
		// if not input or input as 0 by user, then it will be assumed it is 101.

		else {
			capacity = givenCapacity;
		}

		keys = new ArrayList<String>();
		// in the constructor, initialize arraylist keys.
		TWODTABLE = new Wordie[capacity][];
		// the 2d array now has the capacity as the # of rows
	}// end constructor

	public int nextPrime() {
		/*
		 * this method returns the next prime number which is bigger or equal to
		 * capacity*100. double hashing requires the array size to be prime so
		 * using prime to expand the array, is not only efficient but also safe.
		 */
		int nextprime = 0;
		int i = capacity * 100;
		while (nextprime == 0) {
			if (checkPrime(i)) {
				nextprime = i;
			}
			i++;
		}

		return nextprime;
	}// end method

	public boolean checkPrime(int n) {
		// a method which checks if the takin in int is prime or not. I looked
		// at the code from 171 notes.
		if (n % 2 == 0)
			return false;
		for (int i = 3; i * i <= n; i += 2)
			if (n % i == 0)
				return false;

		return true;
	}// end method

	public Wordie[] get(Wordie w) {
		int index;
		
		for (index = w.word.compareTo("") % capacity; TWODTABLE[index] != null
				&& !TWODTABLE[index][0].word.equals(w.word); index = (index + 5)
				% capacity);
		/*
		 * word.compareTo("") can return a lot of integers >=< 0, so use that
		 * number to mode capacity will be my first hashing function so if that
		 * index is already occupied and the existing word is not equal to the
		 * insertion word, then it will be a collision. so a double hashing will
		 * be implemented, which i add the index now by 5 and then mod by
		 * capacity again. until the same word appears, then we just return that
		 * index.
		 */
		return TWODTABLE[index];
	}

	public int add(Wordie w) {
		if (keys.size() > TWODTABLE.length / 2)
			// we check if we have enough spaces.
			return expandarray(nextPrime(), w);
		// expand the array using the new size which will be the first prime
		// after currentcapacity*100
		int index;// = w.word.compareTo("") % capacity;

		for (index = w.word.compareTo("") % capacity; TWODTABLE[index] != null
				&& !TWODTABLE[index][0].word.equals(w.word); index = (index + 5)
				% capacity);

		/*
		 * word.compareTo("") can return a lot of integers >=< 0, so use that
		 * number to mode capacity will be my first hashing function so if that
		 * index is already occupied and the existing word is not equal to the
		 * insertion word, then it will be a collision. so a double hashing will
		 * be implemented, which i add the index now by 5 and then mod by
		 * capacity again. the goal here is to find a empty space for the word
		 * to go in or to add the word to existing list.
		 */
		if (TWODTABLE[index] == null) {
			// empty space
			Wordie[] currWordList = new Wordie[1];
			currWordList[0] = (w);
			TWODTABLE[index] = currWordList;
			keys.add(w.word);
			// if it is a new word, then add to the keys list.
		}// end if

		else {
			// existing word
			Wordie[] currWordList = TWODTABLE[index];
			Wordie[] expandedWordList = Arrays.copyOf(currWordList,
					currWordList.length + 1);
			// new loc come in, expand the columns by 1
			expandedWordList[currWordList.length] = w;
			TWODTABLE[index] = expandedWordList;
		}// end else

		return index;
	}// end method

	public int expandarray(int newcapa, Wordie extraword) {
		capacity = newcapa;
		HASH rehashedtable = new HASH(newcapa);
		for (int i = 0; i < TWODTABLE.length; i++) {
			if (TWODTABLE[i] != null) {
				for (Wordie pw : TWODTABLE[i]) {
					if (pw != null) {
						rehashedtable.add(pw);
						// rehashing the exisiting items in the original array into the new array with more spots.
					}
				}
			}
		}
		keys = rehashedtable.keys;
		TWODTABLE = rehashedtable.TWODTABLE;

		return rehashedtable.add(extraword);
	}// end method
}// end class