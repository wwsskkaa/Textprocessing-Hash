import java.io.*;
import java.util.*;
/*Name:Shuang Wu 
 Concordancie.java: Contains the main method which runs the entire program, has a friendly scanner usage to ask user to input words for locations in the sonnets
Also, I use scanner to input the lexicon file and the sonnet file, but for gatsby file, scanner isn't working the way I want it to run
so I used buffered reader. I write separate methods for reading three files. but the word choosing process is extremely similar between Gatsby file and sonnet file.
Great Gatsby is a long book, so I cut some boring chapters in the middle in order to keep the word amount not exceed sonnets too much.
The shellsort and merge methods are also included in the class, after I created two concordances for sonnet and dictionary file, I put all two sets of words into two arraylist
then I call shellsort on them to sort them alphabetically. After two lists are sorted, I have a merge method which can pull out the unique words in sonnets which are not in modern dictionary.
So here we call those words 'elizabethanisms'. And I input those words into a txt file and return to the user.

10/31/2014
*/
public class Concordancie {
	static int counting = 0;
	static String ROMAN;// sonnet num

	public static void main(String args[]) throws IOException {
		Scanner user = new Scanner(System.in);
		System.out.println("Hello, Qiguang, This is Bonnie:)");
		HASH sonnets = new HASH(0);
		READSONNET(sonnets);
		System.out
				.println("Today I will create a concordance from The Sonnets by Shakespeare.\n\n\n");
		System.out.println("...........Done reading THE SONNETS..............\nThere are "
				+ counting + " words (real words, including repeats) in the file.");
		/*
		 * since what i use for counting was after skipping the first two
		 * lines(title and author) and skipping the last line(useless ending
		 * sentence) and i also exclude all the sonnet numbers so the counting
		 * word here actually only contains the sonnets itself.
		 */
		ArrayList<String> UNIQUEWords = new ArrayList<String>();
		/*
		 * I pass in the 2d array I created in HASH class which stores all the
		 * loc of words. if there is a word, then I add it to the UNIQUEWORDS
		 * arraylist, which will give me a number of how many unique words are
		 * in sonnets
		 */
		for (int i = 0; i < sonnets.TWODTABLE.length; i++)
			if (sonnets.TWODTABLE[i] != null) {
				
				UNIQUEWords.add(sonnets.TWODTABLE[i][0].word);
				
			}
		UNIQUEWords = Shellsort(UNIQUEWords);// O(nlogn) shellsort
		
		System.out.println("The concordance has been created, there are "
				+ UNIQUEWords.size()
				+ " unique words in Shakespeare's sonnets.\n");
		double ratiosonnet = (UNIQUEWords.size() + 0.0) * 100.0//accuracy get the percent
				/ (counting + 0.0);// making sure the accuracy.
		System.out.printf("Which indicates that, only %.2f percent of the words in The Sonnets are unique.",
						ratiosonnet);// print out the percent of unique words insonnets
		System.out.println("\n\nOk, now input a word and I will give you the location of it.\ntype \"stop\" if you want to move on.\n");

		for (String input = user.nextLine(); !input.equalsIgnoreCase("stop"); input = user
				.nextLine()) {
			// if the user input is not stop, then user can keep inputing.
			// otherwise it will go to the next part.
			input = input.toLowerCase();
			Wordie[] entireloc = sonnets.get(new Wordie(input, "", 0, 0));
			// get the entire set of locs for that word input.
			if (entireloc == null)
				// if it is not exist, then this word doesn't exist
				System.out.print("Shakespeare never used that word in Sonnets!");
			else {
				System.out.println("Here are the locations (Sonnet, line, index):");
				for (int j = 0; j < entireloc.length; j++) {
					Wordie w = entireloc[j];
					if (w != null)
					// all the empty spots won't be printed.
					{
						System.out.print("(" + w.roman + ", " + w.line + ", "
								+ w.wordNumber + ") ");
					}
				}
			}

			System.out.println();
		}// end for

		System.out
				.println("\n\n------------------------------------------------------\n\n");

		System.out
				.println("Second step, Let's try comparing Sonnets with my favourite book: The Great Gatsby!");
		System.out
				.println("The book is too long, so I chose some of the most interesting chapters. ");
		HASH gatsby = new HASH(0);
		// put gatsby into a concordance.
		READGATSBY(gatsby, "gatsby.txt");

		System.out.println("\n..........Done reading.......\n There are "
				+ counting + " words in total (include duplicates).");
		ArrayList<String> GWords = new ArrayList<String>();
		for (int i = 0; i < gatsby.TWODTABLE.length; i++)
			if (gatsby.TWODTABLE[i] != null)
				GWords.add(gatsby.TWODTABLE[i][0].word);

		GWords = Shellsort(GWords);
		// I chose Shellsort (O(nlogn)) as my sorting method.

		System.out.println("Done creating Gatsby concordance, found "
				+ GWords.size() + " unique elements.");
		double ratiogatsby = (GWords.size() + 0.0) * 100.0 / (counting + 0.0);// accuracy
		System.out
				.printf("Which indicates that, only %.2f percent of the words in Gatsby book are unique.\n",
						ratiogatsby);
		// prints the % of unique words in the gatsby book

		double finalratio = ratiosonnet / ratiogatsby;
		System.out.printf("\nI get a final ratio of %.2f by dividing the Sonnet's uniqueword ratio by the ratio of gatsby book.\n",
						finalratio);
		/*divide the sonnet uniqueword ratio by the gatsby unique word ratio. The hypothesis is that 
		*average Elizabethan city-dweller, circa 1600, had a working vocabulary at least three times larger than the average American today.
		*So the ratio supposed to be bigger than 1 and even be as large as 3.*/
		if (finalratio > 1) {
			System.out.printf("\nSo After comparing, Sonnet's uniqueword ratio (%.2f) is higher than Fitzgerald's Great Gatsby ratio(%.2f)\n",
							ratiosonnet, ratiogatsby);
			System.out.println("With a final ratio > 1,it indicates that Shakespeare has more volume of words than Fitzgerald who are more modern.");

		} else if (finalratio == 1) {
			System.out.printf("\nSo After comparing, Sonnet's uniqueword ratio (%.2f) is equal to Fitzgerald's Great Gatsby ratio(%.2f)\n",
							ratiosonnet, ratiogatsby);
			System.out.println("With a final ratio = 1,it indicates that Shakespeare has same volume of words with Fitzgerald who are more modern.");
		} else {
			System.out.printf("\nSo After comparing, Sonnet's uniqueword ratio (%.2f) is lower than Fitzgerald's Great Gatsby ratio(%.2f)\n",
							ratiosonnet, ratiogatsby);
			System.out.println("With a final ratio < 1,it indicates that Shakespeare has less volume of words than Fitzgerald who are more modern.");
		}

		ArrayList<String> shakespearean = MERGE(UNIQUEWords, GWords);
		// here i use the concept of merging two lists. As mergesorts works, you
		// basically merge two sorted lists together.

		System.out
				.println("\nDone comparing and retrieving Shakespearean words using The Great Gatsby! ");
		System.out.println("There are about " + shakespearean.size()+ " elizabethanisms (not used in The Great Gatsby).");

		System.out.println("\n\n------------------------------------------------------\n\n");
		System.out.println("Last part: I am going to compare sonnets with linux dictionary\nIt's a huge file so it will take time. Be patient:)");
		Scanner file = new Scanner(new FileReader("linuxwords.txt"));
		HashSet<String> linux = new HashSet<String>();

		while (file.hasNextLine()) {
			linux.add(file.nextLine().toLowerCase());
		}
		/*
		 * will add to HashSet if this word is not pre-existed. since we are
		 * going to compare the lower case of sonnets to this dictionary, then i
		 * will just make every word to lowercase but there are a lot of words
		 * are spelled the same way but with capital letters. so that's why the
		 * actually words in total is different from the actually length of
		 * dict.
		 */

		Object[] dictArray = linux.toArray();
		// change the hashset into a array. Can't directly set it to String[].So will be casted later.

		System.out.println("Done creating dict words list!  There are "
				+ dictArray.length
				+ " words in total(ignoring capital letters).");
		ArrayList<String> dictWords = new ArrayList<String>();

		for (int i = 0; i < dictArray.length; i++) {
			String adding = (String) dictArray[i];
			dictWords.add(adding);
		}

		Collections.sort(dictWords);

		shakespearean = MERGE(UNIQUEWords, dictWords);
		System.out.println("\nDone comparing...");
		System.out
				.println("There are "
						+ shakespearean.size()
						+ " Elizabethanisms that are not used today (not in the dictionary).");
		System.out
				.println("\nI will print out all the Elizabethanisms in a txt file called 'Elizabethanismsoutput.txt'");
		PrintWriter writer = new PrintWriter("Elizabethanismsoutput.txt",
				"UTF-8");
		for (int a = 0; a < shakespearean.size(); a++) {
			writer.println(shakespearean.get(a) + " ");
		}
		writer.close();

		System.out
				.println("\n\nText file created.\n I hope you like my program. Thanks for playing! Have a great day;)");
	}// end main

	public static void READSONNET(HASH TWODTABLE) throws IOException {
		counting = 0;//reset the word counter.
		boolean a = false;//if a is true, then add.
		Scanner file = new Scanner(new File("sonnets.txt"));// read in file
		int line = 0;
		while (file.hasNextLine()) {
			String Linerightnow = file.nextLine();
			Linerightnow = Linerightnow.trim();// get rid of the white spaces
			if ((Linerightnow.equals("THE SONNETS")) && a == false) {// skip the
																		// line
				a = true;//meets THE SONNETS , a equals to true, skip a line and the adding process begins.
				file.nextLine();
				continue;
			}
			if (Linerightnow.equals("End of The Project Gutenberg Etext of Shakespeare's Sonnets"))// skip this line
			{
				a = false;//a equals to false and the adding process ends
				continue;
			}

			if (Linerightnow.matches("[A-Z]+"))
			// if the line only contains capital letters, it will be the sonnet num.
			{
				ROMAN = Linerightnow;
				line = -1;
				// cuz there are still lines between sonnet number and the actual start of the first line in that sonnet, so we want it to be -1 at first.
			}
			Linerightnow = Linerightnow.toLowerCase();

			Linerightnow = Linerightnow.replaceAll("[^a-z'\\- ]", "");// replace all the useless punctuations

			ArrayList<String> changelineintoarraylist = new ArrayList<String>(
					Arrays.asList(Linerightnow.split("\\s+")));

			while (changelineintoarraylist.remove(""))
			// there are empty spaces which appears while those ,!:;.got replaced,has to be removed
			{
				changelineintoarraylist.remove("");
			}
			while (changelineintoarraylist.remove(null))
			// there might be null spots in the arraylist, has to be removed before added.
			{
				changelineintoarraylist.remove(null);
			}
			
			if (a && changelineintoarraylist.size() > 1) {
				counting = counting + changelineintoarraylist.size();//sonnet numbers, the first two lines and the end line doesn't count.
				for (int i = 0; i < changelineintoarraylist.size(); i++) {
					String word = changelineintoarraylist.get(i);
					if (word.matches("[a-z]+(['\\-][a-z]+)?")
							|| word.matches("[']+[a-z]+[']+[a-z]*?")
							|| word.matches("[a-z]+(['-][a-z]+)(['-]*[a-z]*)?"))
					// matching case, if matches then add
					{
						// the middle cases covers the cases such as 'love' or 'scap'd, the last case covers the cases such as all-the-world
						if (word.matches("[']+[a-z]+[']+?"))
						// the case of 'love', have to get rid of the quotes
						{
							word = word.substring(1, word.length() - 1);
						}
						TWODTABLE.add(new Wordie(word, ROMAN, line, i + 1));
					}
					// the reason why i+1 is instead of shown the exact index of the word starting 0, it will start with 1 which is the usual way.
				}

			}

			line++;
			// increment the line everytime gets to the next line.
		}
		file.close();
	}

	public static void READGATSBY(HASH TWODTABLE, String path)
			throws IOException {
		boolean a = false;
		BufferedReader file = new BufferedReader(new FileReader(new File(path)));
		counting = 0;
		String Linerightnow = file.readLine();
		while (Linerightnow != null) {

			Linerightnow = Linerightnow.trim();
			// get rid of the white spaces
			if ((Linerightnow.equals("Chapter 1")) && a == false) {
				// start the counting and adding while skip this line.
				a = true;
				file.readLine();
				continue;
			}

			if (Linerightnow.equals("THE END"))
			// loop breaks when it reaches the end.
			{
				a = false;
				break;
			}

			if (Linerightnow.contains("Chapter"))
			// when contains upper case Chapter, skip that line.
			{
				Linerightnow = file.readLine();
			}
			Linerightnow = Linerightnow.toLowerCase();

			Linerightnow = Linerightnow.replaceAll("[^a-z'\\- ]", "");
			// replace all the 'useless' punctuations.

			ArrayList<String> changelineintoarraylist = new ArrayList<String>(
					Arrays.asList(Linerightnow.split("\\s+|--+")));//if there is word such as listen----

			while (changelineintoarraylist.remove(""))
			// there are empty spaces which appears while those ,!:;.got replaced,has to be removed
			{
				changelineintoarraylist.remove("");
			}
			while (changelineintoarraylist.remove(null))
			// there might be null spots in the arraylist, has to be removed before added.
			{
				changelineintoarraylist.remove(null);
			}

			if (a && changelineintoarraylist.size() > 1) {

				counting = counting + changelineintoarraylist.size();
				for (int i = 0; i < changelineintoarraylist.size(); i++) {
					String word = changelineintoarraylist.get(i);

					if (word.matches("[a-z]+(['\\-][a-z]+)?")
							|| word.matches("[']+[a-z]+[']+?")
							|| word.matches("[a-z]+(['-][a-z]+)(['-]*[a-z]*)?"))
					// matching case, if matches then add
					{
						// since we have to compare gatsby with sonnets, the word chosen process should be fairly similar
						if (word.matches("[']+[a-z]+[']+?"))
						// the case of 'love', have to get rid of the quotes
						{
							word = word.substring(1, word.length() - 1);
						}
						TWODTABLE.add(new Wordie(word));
					}
				}
			}
			Linerightnow = file.readLine();
		}

		file.close();
	}

	public static ArrayList<String> Shellsort(ArrayList<String> a) {
		// here i looked at the moresorting lab from csc 172.
		int j;
		int k;
		for (k = 1; Math.pow(2, k) - 1 <= a.size() / 2; ++k)
			;
		--k;
		for (int gap = (int) Math.pow(2, k) - 1; gap > 0; --k, gap = (int) Math
				.pow(2, k) - 1) {
			for (int i = gap; i < a.size(); i++) {
				String tmp = a.get(i);
				for (j = i; j >= gap && tmp.compareTo(a.get(j - gap)) < 0; j -= gap) {
					a.set(j, a.get(j - gap));

				}
				a.set(j, tmp);
			}
		}
		return a;
	}

	public static ArrayList<String> MERGE(ArrayList<String> a,
			ArrayList<String> b) {
		/*
		 * Here I use the idea of the mergesort algorithm, where i need the
		 * merge two sorted list together. first i am comparing the first
		 * element from both array, if the original list first item is small
		 * than the comparison list, it means that the comparison list doesn't have this item
		 * then the origin item got to be moved to the new merged array. 
		 * then comparing the first items from both array
		 * again, do the same thing. if two items are the same, then just
		 * increment the index without doing anything else. therefore in the
		 * end, all the words that only in original lists will be in the unused
		 * arraylist.
		 */
		ArrayList<String> unused = new ArrayList<String>();
		int aindex = 0, bindex = 0;
		while (aindex < a.size()) {
			// if it runs through original list, then it stops
			if (bindex >= b.size()) {
				unused.add(a.get(aindex++));
			}

			else {
				int comparison = a.get(aindex).compareTo(b.get(bindex));
				if (comparison < 0)
					unused.add(a.get(aindex++));

				else if (comparison > 0)
					bindex++;

				else {
					bindex++;
					aindex++;
				}
			}// end else
		}// end while

		return unused;
	}// end method

}// end class