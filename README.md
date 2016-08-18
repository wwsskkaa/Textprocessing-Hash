
# Text Processing using hashing strategy. 

Copyright (c) | Fall 2014 | Shuang Wu | University of Rochester 

Description:
I implemented hash function using 2d array, try to use my self-implemented hashing to build a concordance of THE SONNETs by Shakespeare, and also of Great Gatsby by Fitzgerald.Shellsort and a merge startegy is also usedti give out all the unique words in sonnet which are not in modern dictionary.

Double Hashing strategy is used for this project: in HASH.java, I used a 2d array to store all the words locs.So basically, every row stores the entire set of locs for the same word. The word are stored in Wordie type(another class).I need to use hashing to hash words in, but since it might easily get a collision, I have to use double hashing.So the size of my 2d array (# of rows) should be a prime number. so I have a nextPrime() method which returns the next prime of current capacity*100 so every time I need to expand the row number, I just call the expand method which creates a new 2d array of nextprime size and rehash all the current ones in whenever I add a new loc, I expand the size of that row by 1.




Included Files:
1) This README

2)SAMPLEOUTPUT.txt containing the sample output of this program.

3) Concordancie.java: Contains the main method which runs the entire program, has a friendly scanner usage to ask user to input words for locations in the sonnets.Also, I use scanner to input the lexicon file and the sonnet file, but for gatsby file, scanner isn't working the way I want it to run,so I used buffered reader. I write separate methods for reading three files. but the word choosing process is extremely similar between Gatsby file and sonnet file.The shellsort and merge methods are also included in the class, after I created two concordances for sonnet and dictionary file, I put all two sets of words into two arraylist then I call shellsort on them to sort them alphabetically. After two lists are sorted, I have a merge method which can pull out the unique words in sonnets which are not in modern dictionary. So here we call those words 'elizabethanisms'. And I input those words into a txt file and return to the user.


4) Wordie.java: Wordie stores a word, a sonnet num and line num and index num for those words in the sonnets another instructor can be used for other articles where there won't be location needed. so it just store a word.

5)HASH.java: this one contains all the steps which used for creating concordance.

6) linuxwords.txt: the modern english dictionary.

7) sonnets.txt: THE SONNETS by Shakespeare (gutenberg website)

8) gatsby.txt: selected chapters from Great Gatsby by Fitzgerald.(australian gutenberg website)

9) Elizabethanismoutput.txt: the output of all the Elizabethanisms.


Compilation Instructions: 

Go to terminal

cd Desktop

javac Concordancie.java

java Concordancie



DO NOT COPY OR USE OUR CODE. THANK YOU.

