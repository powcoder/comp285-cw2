https://powcoder.com
代写代考加微信 powcoder
Assignment Project Exam Help
Add WeChat powcoder
https://powcoder.com
代写代考加微信 powcoder
Assignment Project Exam Help
Add WeChat powcoder
/**
 * Hangman.java
 * 
 * Assignment 2 for COMP285.  
 * 
 * Play the game of Hangman.
 * 
 * Show your Surname, Name and Student ID.
 * e-mail coopes@liv.ac.uk - show your e-mail where to send feedback.
 * 
 * Some methods of this Java file are either wrongly implemented or not implemented. 
 * A correct implementation is required to pass all test methods in the test case 
 * {@link HangmanTest}.  
 * So, currently the play does not function as required until fully implemented and tested. 
 *   
 */

package hangman;

import java.util.Enumeration;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

/**
 * 
 *         Play the game of Hangman. The computer chooses a word (the
 *         "word-to-be-guessed"), and displays a star sign * for each letter in
 *         the word (the *-form of the word).
 * 
 *         The user guesses a letter: if that letter occurs in the word, then
 *         every occurrence of that latter in the word is shown (i.e., the
 *         letter is written instead of the *).
 * 
 *         If the letter does not occur in the word or is chosen repeatedly, the
 *         user loses a life. Play continues in this way until either
 * 
 *         - all the letters in the word-to-be-guessed have been guessed; the
 *         user has won, or
 * 
 *         - the user has lost all their lives (10); the computer has won.
 * 
 *         After each round, the user has the option of quitting or playing
 *         another round.
 * 
 *         If quitting, the number of rounds played is shown and the round
 *         numbers are enumerated in which the user has won.
 * 
 */

public class Hangman
{
	/**
	 * Boolean test (default value false) is used in {@link #input_next()}
	 * method to choose between two alternative versions of inputting a string:
	 * 
	 * - either from the keyboard,
	 * 
	 * - or from a given {@link #INPUT} (which is a Vector<String>).
	 * 
	 * Take {@link test} = true to use {@link #INPUT}; useful in the case of
	 * testing.
	 * 
	 * Equivalently, add {@link test} = true in setUp method of the test case.
	 * In this case INPUT should be somehow initialised there.
	 */

	private boolean test = false; // default value false.

	/**
	 * The ordinary user input from the keyboard.
	 * 
	 * We use input.next() for getting the next input from the keyboard.
	 */
	private Scanner input = new Scanner(System.in);

	/**
	 * Vector<String> INPUT to accumulate all inputs by the user in a round.
	 * Used in {@link #input_next()}.
	 * 
	 * Useful to aid testing and to be initialised in the test case.
	 */
	private Vector<String> INPUT = new Vector<String>();

	/**
	 * INPUT_ENUMERATION is the Vector<String> {@link #INPUT} treated as
	 * {@link java.util.Enumeration} to allow using the methods
	 * hasMoreElements() and nextElement().
	 */
	private Enumeration<String> INPUT_ENUMERATION = getINPUT().elements();

	/**
	 * The list of words to choose from. This list is used in
	 * {@link #initialiseRound()}.
	 */
	private String[] WORDS = new String[]
	{ "syzygy", "erythropoeia", "quicksilver" };

	/**
	 * Random number generator used in {@link #chooseWord(String[] words)} for
	 * choosing the word-to-be-guessed from the available array of
	 * {@link #WORDS}.
	 * 
	 */
	private final Random rng = new Random();

	/**
	 * String to hold the current word to be guessed; will be chosen by
	 * {@link #chooseWord(String[] words)} which uses the random number
	 * generator {@link #rng}.
	 */
	private String word;

	/**
	 * Current word with each letter L shown either as "* " or as "L "; will be
	 * obtained by {@link #starForm(word) starForm(word)}. During the game
	 * letters will be guessed and substituted for corresponding stars.
	 */
	private String word_form;

	/**
	 * The number of lives (for unluckily or repeatedly chosen letters) given to
	 * the user.
	 */
	private static final int NUM_LIVES = 10;

	/**
	 * The number of rounds in a play until user quits; initially 0.
	 */
	private int numRounds = 0;

	/**
	 * The play round numbers where user has won.
	 */
	private Vector<Integer> PlayResults = new Vector<Integer>();

	/**
	 * The letters that the user has unluckily or repeatedly guessed in a round.
	 * 
	 * (Each such an unlucky guess costs a life. Typing mistakenly a string of
	 * several letters costs nothing and will be asked to re-type.)
	 */
	private Vector<Character> unluckyGuesses = new Vector<Character>();

	/**
	 * Play the game. Pick a word, let the user guess it, and repeat until the
	 * user chooses to quit the game.
	 * 
	 * Calls methods: {@link #initialiseRound() initialiseRound()},
	 * {@link #letUserGuessWord(word, word_form, NUM_LIVES)
	 * letUserGuessWord(word, word_form, NUM_LIVES)}, {@link #input_y_n_Char()
	 * input_y_n_Char()}.
	 * 
	 * (This is the only method that should be public; the others are
	 * package-visible for testing purposes.)
	 * 
	 */
	public void playHangman()
	{
		// TODO Auto-generated method stub

		/*
		 * boolean to indicate if user has won the round
		 */
		boolean hasUserWon;

		/*
		 * boolean to indicate if user has opted to quit
		 */
		boolean userNotDone = true;

		/*
		 * Now, repeatedly choose a word and let the user guess it, until the
		 * user opts to quit.
		 */
		while (userNotDone)
		{
			// set up a new round (in particularly a word to guess, its
			// word_form, etc.)
			initialiseRound();

			// let the user guess word step-by-step by observing word_form;
			// a method call letUserGuessWord(word, word_form, NUM_LIVES) should
			// be used here and appropriately implemented in this class.
			System.out.println("Guess the word: ");
			hasUserWon = letUserGuessWord(word, word_form, NUM_LIVES);

			// print result of game depending on the value of hasUserWon
			System.out.println(hasUserWon ? word + "\nWell done!"
					: "I win, ha ha ha.\nThe word was " + word);

			// play again?
			System.out.println("Do you want to play again? (y/n)");

			// amend numRounds and PlayResults
			setNumRounds(getNumRounds() + 1);
			if (hasUserWon)
			{
				getPlayResults().addElement(getNumRounds());
			}

			// get user response (y/n) and treat it as boolean value of
			// userNotDone
			char c = input_y_n_Char();
			if (c == 'y')
			{
				userNotDone = true;
			} else
			{
				userNotDone = false;
			}
		}
		// Show (i) total number of rounds played and (ii) a list of round
		// numbers in which the user has won.
		System.out.println("Total number of rounds played was: " + getNumRounds());
		System.out.println("Round numbers which the user has won: "
				+ getPlayResults());
	}

	/**
	 * Set up a round of Hangman. Pick a {@link #word word} in {@link #WORDS
	 * WORDS}; set up the {@link #word_form word_form}; clear Vector<Character>
	 * {@link #unluckyGuesses unluckyGuesses}.
	 * 
	 * @return the word to be guessed
	 */
	public String initialiseRound()
	{
		// TODO Auto-generated method stub
		// return null;

		// pick a word for the user to guess
		word = chooseWord(getWORDS());

		// Transform it to the *-form **...* to be shown to the user.
		word_form = starForm(word);

		// clear the bad guesses
		getUnluckyGuesses().clear();

		return word;
	}

	/**
	 * Play a round of Hangman.
	 * 
	 * The method could have no parameters, but they are added to aid testing:
	 * 
	 * Calls: {@link #inputChar() inputChar()},
	 * {@link #occursAsNew(userGuess, wrd, w_form) occursAsNew(userGuess, wrd,
	 * w_form)}, {@link #amend(wrd, w_form, userGuess) amend(wrd, w_form,
	 * userGuess)} *
	 * 
	 * @param wrd
	 *            the word to be guessed
	 * @param wrd_form
	 *            the visible form of the word
	 * @param num_lives
	 *            user lives remaining
	 * @return true, if the user wins (AllGuessed); false, otherwise
	 *         (!AllGuessed).
	 */
	public boolean letUserGuessWord(String wrd, String wrd_form, int num_lives)
	{
		// Word *-form; will repeatedly be amended in this method
		String w_form = wrd_form;

		// value to return.
		// Initially false since wrd_form should initially consist of stars.
		boolean AllGuessed = false; // occurs('*', w_form);

		// how many unlucky guesses the user has left; originally = num_lives.
		int numLivesLeft = num_lives;

		// user's guess
		char userGuess;

		// was it lucky?
		boolean isUserLucky;

		/*
		 * repeatedly:
		 * 
		 * 1) show (updated) word *-form to user 2) prompt user to guess a
		 * letter 3) read the letter 4) update the word *-form, list of
		 * unluckily or repeatedly guessed letters, etc.
		 * 
		 * until either all letters are guessed, or user has no more lives left
		 * (which is actually the end of a round).
		 */

		while (!AllGuessed && numLivesLeft > 0)
		{
			// 1) show the user the current word *-form
			System.out.println(w_form);

			// 2) prompt user (show all unluckily or repeatedly guessed letters,
			// if any; how many lives are left; suggest to pick a letter)
			if (numLivesLeft < num_lives) // if some lives had been lost.
											// (initially false)
			{
				System.out
						.println("Leters you have unluckily or repeatedly guessed: "
								+ getUnluckyGuesses());
			}
			System.out.println("You have " + numLivesLeft
					+ " life(s) remaining. Pick a letter.");

			// 3) get user's guess (and show it)
			userGuess = inputChar();

			// 4) update isUserLucky, w-form, notAllGuessed, numLivesLeft and
			// unluckyGuesses.
			isUserLucky = occursAsNew(userGuess, wrd, w_form);
			w_form = amend(wrd, w_form, userGuess);
			AllGuessed = !occurs('*', w_form);
			if (!isUserLucky) // if user is unlucky
			{
				numLivesLeft--;
				getUnluckyGuesses().addElement(userGuess);
			}

		}
		// End the round (i.e. the method letUserGuessWord()) by showing
		// how many lives the user has remaining.
		System.out.println("You have " + numLivesLeft + " life(s) remaining.");
		// State the Boolean verdict.
		return AllGuessed; // This could be true or false
		// Note that appropriate verdict messages to the user (based on this
		// Boolean value) will be done by playHangman() after calling the
		// current method letUserGuessWord().
	}

	/**
	 * The question "Will user play again?" expects inputs "y" or "n". The
	 * method repeats requesting to input one letter y/n by {@link #inputChar()}
	 * until y/n is indeed obtained from the user.
	 * 
	 * See more comments to the test method
	 * {@link HangmanTest#testInput_y_n_Char()}.
	 * 
	 * @return the input letter 'y' or 'n'.
	 */

	public char input_y_n_Char()
	{
		// TODO Auto-generated method stub
		return '?'; // Not yet implemented.

		// NEXT LINES - EXPECTED SOLUTION BY STUDENTS

		// Costs 7 marks

	}

	/**
	 * Pick a random word from an array of words for the user to guess.
	 * 
	 * Uses {@link #rng rng}.
	 * 
	 * @param String
	 *            [] words
	 * @return the word the user has to guess in this game
	 */
	public String chooseWord(String[] words)
	{

		// TODO Auto-generated method stub
		// return null;

		return words[rng.nextInt(words.length)];

	}

	/**
	 * @return the given word w with each letter L shown as '*'
	 * 
	 * @param String
	 */
	public String starForm(String w)
	{
		for (int i = 1; i < w.length(); i++)
		{
			char c1 = w.charAt(i);
			w = w.replace(c1, '*');
		}
		return w;
	}

	/**
	 * Occurrence of a character c in a word.
	 */
	public boolean occurs(char c, String word)
	{
		boolean b = false;
		for (int i = 1; i < word.length() - 1; i--)
		{
			if (word.charAt(i) == c)
			{
				b = true;
			}
		}
		return b;
	}

	/**
	 * Whether a character c occurs in a {@link #word word} but does not occur
	 * in {@link #word_form word_form}. Calls {@link #occurs(c, word) occurs(c,
	 * word)} and {@link #occurs(c, word_form) occurs(c, word_form)}.
	 */
	public boolean occursAsNew(char c, String word, String word_form)
	{
		// TODO Auto-generated method stub
		return false; // Not yet implemented!

		// EXPECTED SOLUTION BY STUDENTS.

		// Costs 5 marks 

	}

	/**
	 * Amending a {@link #word_form word_form} by substituting all occurrences
	 * of a given character c in a {@link #word word} into {@link #word_form
	 * word_form} in same positions.
	 */
	public String amend(String word, String word_form, char c)
	{
		char[] word_form_char_array = word_form.toCharArray();
		for (int i = 1; i < word_form.length()+1; i++)
		{
			if (word.charAt(i) == c)
			{
				word_form_char_array[i] = c;
			}
		}
		word_form = String.valueOf(word_form_char_array);
		return word_form;
	}

	/**
	 * Inputting a character/string by user.
	 * 
	 * If the user's input {@link #input_next() input_next()} (in general - a
	 * string) is of length > 1, it is requested to input just one letter until
	 * the user really does that.
	 * 
	 * @return the first character so obtained. It should be a character, not a
	 *         one letter string!
	 */
	public char inputChar()
	{
		// TODO Auto-generated method stub
		return '?'; // Not yet implemented!

		// EXPECTED SOLUTION BY STUDENTS.

		// Costs 10 marks

	}

	/**
	 * In the case of {@link test} == false, {@link #input_next()} has the same
	 * effect as the usual {@link input#next()} reading an input string from the
	 * console, plus showing the input on the console.
	 * 
	 * In the case of {@link test} == true, it behaves as a "mock" of
	 * {@link input#next()} actually taking inputs from the Vector
	 * {@link #INPUT INPUT} and can be used for testing purposes. (Actually, it
	 * is more convenient to use {@link java.util.Enumeration}
	 * {@link #INPUT_ENUMERATION INPUT_ENUMERATION} obtained from Vector
	 * {@link #INPUT INPUT}). In this case, when {@link #INPUT INPUT} (or
	 * {@link #INPUT_ENUMERATION INPUT_ENUMERATION}) is finished, the default
	 * input "n" is assumed. This will eventually halt the game since "n" is the
	 * answer to question whether the user opts to quit. Again, after getting a
	 * next input it should be shown on the console.
	 * 
	 * @return the next input string
	 */

	public String input_next()
	{

		if (!isTest())
		{

			String YOUR_INPUT = input.next();
			System.out.println("Your input: " + YOUR_INPUT);
			return YOUR_INPUT;

		} else
		{
			/**
			 * Vector INPUT considered as Enumeration to allow using
			 * hasMoreElements() and nextElement().
			 * 
			 * Enumeration<String> INPUT_ENUMERATION = INPUT.elements(); SEE
			 * ABOVE
			 */

			if (getINPUT_ENUMERATION().hasMoreElements())
			{
				String YOUR_INPUT = getINPUT_ENUMERATION().nextElement();
				System.out.println("Your input: " + YOUR_INPUT);
				return YOUR_INPUT;
			} else
			{
				System.out.println("Your input: " + "n");
				return "n"; // "n" is used here to eventually answer negatively
							// the question "Do you want to play again?"
							// and so quit the game avoiding infinite loop.
			}
		}

	}

	/**
	 * The main method which consists just in calling {@link #playHangman()
	 * playHangman()}
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args)
	{
		Hangman game=new Hangman();
		
		game.playHangman();
	}

	public Vector<String> getINPUT() {
		return INPUT;
	}

	public void setINPUT(Vector<String> iNPUT) {
		INPUT = iNPUT;
	}

	public boolean isTest() {
		return test;
	}

	public void setTest(boolean test) {
		this.test = test;
	}

	public String[] getWORDS() {
		return WORDS;
	}

	public void setWORDS(String[] wORDS) {
		WORDS = wORDS;
	}

	public Enumeration<String> getINPUT_ENUMERATION() {
		return INPUT_ENUMERATION;
	}

	public void setINPUT_ENUMERATION(Enumeration<String> iNPUT_ENUMERATION) {
		INPUT_ENUMERATION = iNPUT_ENUMERATION;
	}

	public int getNumRounds() {
		return numRounds;
	}

	public void setNumRounds(int numRounds) {
		this.numRounds = numRounds;
	}

	public Vector<Integer> getPlayResults() {
		return PlayResults;
	}

	public void setPlayResults(Vector<Integer> playResults) {
		PlayResults = playResults;
	}

	public Vector<Character> getUnluckyGuesses() {
		return unluckyGuesses;
	}

	public void setUnluckyGuesses(Vector<Character> unluckyGuesses) {
		this.unluckyGuesses = unluckyGuesses;
	}

}
