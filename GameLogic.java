import java.awt.Color;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.awt.event.KeyEvent;
import java.io.*;
import java.security.GeneralSecurityException;
import java.security.Key;

public class GameLogic{
   
      
   //Name of file containing all the possible "secret words"
   private static final String SECRET_WORDS_FILENAME = "secrets.txt";   
   
   //Name of file containing all the valid guess words
   private static final String VALID_GUESSES_FILENAME = "valids.txt";   
   
   //Use for generating random numbers!
   private static final Random rand = new Random();
   
   //Dimensions of the game grid in the game window
   public static final int MAX_ROWS = 6;
   public static final int MAX_COLS = 5;
   
   //Character codes for the enter and backspace key press
   public static final char ENTER_KEY = KeyEvent.VK_ENTER;
   public static final char BACKSPACE_KEY = KeyEvent.VK_BACK_SPACE;
   
   //The null character value (used to represent an "empty" value for a spot on the game grid)
   public static final char NULL_CHAR = 0;
   
   //Various Color Values
   private static final Color CORRECT_COLOR = new Color(53, 209, 42); //(Green)
   private static final Color WRONG_PLACE_COLOR = new Color(235, 216, 52); //(Yellow)
   private static final Color WRONG_COLOR = Color.DARK_GRAY; //(Dark Gray [obviously])
   private static final Color DEFAULT_KEY_COLOR = new Color(160, 163, 168); //(Light Gray)
   

   
   //A preset, hard-coded secret word to be use when the resepective debug is enabled
   private static final char[] DEBUG_PRESET_SECRET = {'S', 'H', 'I', 'R', 'E'};      
   
   
  
   
   
   
   
   //******************   NON-FINAL GLOBAL VARIABLES   ******************

   
   
   //Array storing all valid guesses read out of the respective file
   private static String[] validGuesses;
   
   //The current row/col where the user left off typing
   private static int currentRow, currentCol;


      
   
   //*******************************************************************
   
   
   
   
   //This function gets called ONCE when the game is very first launched
   //before the user has the opportunity to do anything.
   //
   //Should perform any initialization that needs to happen at the start of the game,
   //and return the randomly chosen "secret word" as a char array
   //
   //If either of the valid guess or secret words files cannot be read, or are
   //missing the word count in the first line, this function returns null.

   public static char[] initializeGame(){

      if (JWordleLauncher.DEBUG_USE_PRESET_SECRET)
         return DEBUG_PRESET_SECRET;  // Since, we are using preset hard-coded word

      else{
         return(readFile());
      }

   }

   public static char[] readFile(){
      try {
         Scanner scanner = new Scanner(new File(SECRET_WORDS_FILENAME));
         if(!scanner.hasNextInt())
            return null;
         int numWords = scanner.nextInt();
         scanner.nextLine(); // consume the rest of the first line
         if (numWords <= 0){
            scanner.close();
            return null;
         }
         int randomIndex = rand.nextInt(numWords);
         for (int i = 0; i < randomIndex; i++) {
            scanner.nextLine(); // skip to the randomly chosen word
         }
         String secretWord = scanner.nextLine().trim();
         scanner.close();
         return secretWord.toCharArray();
      } 
      catch (FileNotFoundException e){
         return null;
      }

   }
   
   //Complete your warmup task (Section 3.1.1 part 2) here by calling the requisite
   //functions out of GameGUI.
   //This function gets called ONCE after the graphics window has been
   //initialized and initializeGame has been called.

 

   public static void warmup(){
      /* 
      GameGUI.setGridChar(0,0,'c');
      GameGUI.setGridColor(0,0, CORRECT_COLOR);
      GameGUI.setGridChar(1,3,'o');
      GameGUI.setGridColor(1,3, WRONG_COLOR);
      GameGUI.setGridChar(3,4,'s');
      GameGUI.setGridColor(3,4,DEFAULT_KEY_COLOR);
      GameGUI.setGridChar(5, 4, 'c');
      GameGUI.setGridColor(5, 4, WRONG_PLACE_COLOR);
      GameGUI.setKeyColor('U', CORRECT_COLOR);
      GameGUI.setKeyColor('C', WRONG_COLOR);

      */
     
     //All of your warmup code will go in here except for the
     //"wiggle" task (3.1.1 part 3)... where will that go?
     
   }
   

   
   //This function gets called everytime the user types a valid key on the
   //keyboard (alphabetic character, enter, or backspace) or clicks one of the
   //keys on the graphical keyboard interface.
   //
   //The key pressed is passed in as a char value.

   //**********************************************  Game starts here  ***********************************************************//

   public static void reactToKey(char key){
      //GameGUI.wiggle(3); //                       It's warmup part 3  (wiggling)
      
      if (key!= ENTER_KEY && key!= BACKSPACE_KEY && currentCol<MAX_COLS){
         GameGUI.setGridChar(currentRow, currentCol, key);
         currentCol++;
      }

      else if (key == BACKSPACE_KEY && currentCol!=0){
         currentCol--;
         GameGUI.setGridChar(currentRow, currentCol, (char)NULL_CHAR);
      }

      if (key == ENTER_KEY && currentCol==5 && currentRow<MAX_ROWS) {
         EnterKeyPressed();
      }
   }


   // 1. First function called after the user press the ENTER key

   public static void EnterKeyPressed(){
      char[] guess= new char[MAX_COLS];
         for (int i=0; i<MAX_COLS;i++){
            guess[i]=GameGUI.getGridChar(currentRow, i);   
         }
         if(!validGuesses(guess))    // Calls (2) to check valid guess
            return;
         postEnterKeyTask(guess);    // Calls (3) for post ENTER key tasks
         currentRow++;
         currentCol=0;
   }

   // 2. Called by (1) to check if the user entered valid guess

   public static boolean validGuesses(char[] guess){
      if (JWordleLauncher.DEBUG_ALL_GUESSES_VALID)
         return true;
         
      int totalWords=0;
      String guessStr = new String(guess);
      try {
         Scanner scanner = new Scanner(new File(VALID_GUESSES_FILENAME));
         if (scanner.hasNextInt()) {
            totalWords = scanner.nextInt();
         }
        // Looping through the remaining lines of the file and check if the word is in the file
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals(guessStr)){
               return true;  
            }
        }
        GameGUI.wiggle(currentRow);
      }
      catch (FileNotFoundException e){
         return false;
      }
      return false;
   }

   // 3. Called by (1) to perform post ENTER key task
    
   public static void postEnterKeyTask(char[] guess){
      char[] secretword = GameGUI.getSecretWordArr();
      boolean[] matchedIndices = new boolean[secretword.length];
      boolean[] guessedIndices = new boolean[guess.length];
  
      findCorrectLetters(secretword, guess, matchedIndices, guessedIndices);                // Calls 4
      findCorrectLettersAtWrongPlace(secretword, guess, matchedIndices, guessedIndices);    // Calls 5
      findIncorrectLetters(guess, guessedIndices);                                          // Calls 6
  
      checkWinLose(guess);                                                                  // Calls 7
   }
  

   // 4. Checks Correct Letter at Correct position

   private static void findCorrectLetters(char[] secretword, char[] guess, boolean[] matchedIndices, boolean[] guessedIndices) {
      for (int i = 0; i < secretword.length; i++) {
          if (secretword[i] == guess[i]) {
              GameGUI.setGridColor(currentRow, i, CORRECT_COLOR);
              GameGUI.setKeyColor(guess[i], CORRECT_COLOR);
              matchedIndices[i] = true;
              guessedIndices[i] = true;
            }
      }
   }
  
   // 5. Checks Correct Letter at Wrong position

   private static void findCorrectLettersAtWrongPlace(char[] secretword, char[] guess, boolean[] matchedIndices, boolean[] guessedIndices){
      for (int i = 0; i < secretword.length; i++){
         for (int j = 0; j < guess.length; j++){
              if (secretword[i] == guess[j] && !matchedIndices[i] && !guessedIndices[j]){
                  Color gridcol = GameGUI.getGridColor(currentRow, j);
                  Color keycoll = GameGUI.getKeyColor(guess[j]);
                  if (gridcol != CORRECT_COLOR) {
                      GameGUI.setGridColor(currentRow, j, WRONG_PLACE_COLOR);
                  }
                  if (keycoll != CORRECT_COLOR) {
                      GameGUI.setKeyColor(guess[j], WRONG_PLACE_COLOR);
                  }
                  matchedIndices[i] = true;
                  guessedIndices[j] = true;
               }
         }
      }
   }
  
   // 6. Checks inCorrect Letter

   private static void findIncorrectLetters(char[] guess, boolean[] guessedIndices){
      for (int i = 0; i < guess.length; i++){
         if (!guessedIndices[i]){
            Color gridcol = GameGUI.getGridColor(currentRow, i);
            Color keycoll = GameGUI.getKeyColor(guess[i]);
            if (gridcol != CORRECT_COLOR && gridcol != WRONG_PLACE_COLOR){
               GameGUI.setGridColor(currentRow, i, WRONG_COLOR);
            }
            if (keycoll != CORRECT_COLOR && keycoll != WRONG_PLACE_COLOR){
               GameGUI.setKeyColor(guess[i], WRONG_COLOR);
            } 
         }
      }
   }

   // 7. Checks decision
  
   public static void checkWinLose(char[] guess){
      int gridCount=0;
      for(int l=0; l<guess.length; l++){
         if(GameGUI.getGridColor(currentRow, l)==CORRECT_COLOR){
            gridCount++;
         }
         if(gridCount==5)
            GameGUI.gameOver(true);

         else if(currentRow==5){
            GameGUI.gameOver(false);
         }  
      }
   }
}

//*************************************************     END      ********************************************************************//