import java.util.Scanner;
import java.io.IOException;

/**
* class that plays a 20 questions style animal guessing game
* @author Anna Wilson
* @version Fall 2021
*/
public class AnimalGuess{

  /**
  * runs the methods to play the guessing game
  * @param args will be the name of the file that stores the tree of questions when the user first runs it (so that would be just args[0])
  */
  public static void main(String[] args) throws IOException {

    String fileName = args[0];

    boolean playAgain = true;
    boolean correctInput = false;
    Scanner sc = new Scanner(System.in);
    
   try{
      DecisionTree animals = DecisionTree.readFromFile(fileName);

      String guess = "";
      String path = "";

      // loop every time the user says yes to play another round
      while(playAgain){   
        System.out.println("Think of an animal.");
        System.out.println("I'll try to guess it.");
        path = askQuestions(animals, guess, sc);

        while(correctInput == false){
          String guessedIt = sc.nextLine();
          if(guessedIt.equals("yes") || guessedIt.equals("Yes") || guessedIt.equals("y") || guessedIt.equals("Y") || guessedIt.equals("ye")){
            correctInput = true;
            System.out.println("Yay, I guessed it!");
          }
          else if(guessedIt.equals("no") || guessedIt.equals("No") || guessedIt.equals("n") || guessedIt.equals("N")){
            // path = path + "N";
            correctInput = true;
            System.out.println("I got it wrong.");
            System.out.println("Please help me to learn.");
            System.out.println("What was your animal?");      
            String userAnimal = sc.nextLine();
            System.out.println("Type a yes or no question that would distinguish between " + animals.followPath(path).getData() + " and " + userAnimal);
            String question = sc.nextLine();
            System.out.println("Would you answer yes to this question for the " + userAnimal + "?");
            String isYes = sc.nextLine();

            addToTree(question, isYes, userAnimal, guess, animals, path); 
           
          }
          else{
            System.out.println("Try inputting something like yes or no");
            correctInput = false;
          }
        }
        correctInput = false;

        System.out.println("Play again?");

        String stringTree = "";
        stringTree = animals.breadthFirstString(stringTree, animals, "");
        animals.writeToFile(stringTree, fileName);

        while(correctInput == false){
          String play = sc.nextLine();
          if(play.equals("yes") || play.equals("Yes") || play.equals("y") || play.equals("Y") || play.equals("ye")){
            correctInput = true;
            playAgain = true;// thus we will loop again
          }
          else if(play.equals("no") || play.equals("No") || play.equals("n") || play.equals("N")){
            correctInput = true;
            playAgain = false;
            System.out.println("Okay, bye!");
            
          }
          else{
            System.out.println("Try inputting something like yes or no");
            correctInput = false;
          }
        }   
        correctInput = false;   
      }  
      sc.close();
    }
    catch(IOException e){
      System.out.println("IOException");
    }
  }

  /**
  * method that adds information (branch and/or leaf) to the overall decision tree at specified location
  * 
  * @param question a question user provides to distinguish 2 animals
  * @param isYes string yes or no that tells us if the user's question is true or false for the animal they were thinking of
  * @param userAnimal the animal the user was thinking of
  * @param guessAnimal the animal the program incorrectly guessed
  * @param animals the big decision tree of everything 
  * @param path a string comprised of Y's and N's, denoting which direction to take as yoy go down the tree to reach the specified location
  */
  public static void addToTree(String question, String isYes, String userAnimal, String guessAnimal, DecisionTree animals, String path){

    // left means YES, right means NO
    guessAnimal = animals.followPath(path).getData();
    DecisionTree leftLeaf;
    DecisionTree rightLeaf;


    if(isYes.equals("yes") || isYes.equals("Yes") || isYes.equals("y") || isYes.equals("Y") || isYes.equals("ye")){// if the user's question is true for the user's animal
      leftLeaf = new DecisionTree(userAnimal); 
      rightLeaf = new DecisionTree(guessAnimal);
    }
    else{// else the user's question is NOT true for their animal
      leftLeaf = new DecisionTree(guessAnimal);
      rightLeaf = new DecisionTree(userAnimal);
    }

    // create a branch that holds the question and the yes and no answers
    DecisionTree branch = new DecisionTree(question);
    branch.setLeft(leftLeaf);
    branch.setRight(rightLeaf);

    // add the branch at location specified by path
    animals.addAtLocation(path, animals, branch); 
  }

  /**
  * method that travels through the decision tree, asking the question stored in each node
  * asks for a yes or no response from user for each question
  * this yes or no determines which direction to go as we continue to travel down the tree
  * when the leaves of the tree are reached (aka the actual guesses, not questions, ask "is your animal a [leaf]?"
  *
  * @param animals the big decision tree of questions/answers
  * @param guess will be the animal the computer guesses
  * @param sc scanner to read input
  * @return path the path that tells us which directions to take as we go down the list. We will use this to find the location of where we will add a new branch
  */
  public static String askQuestions(DecisionTree animals, String guess, Scanner sc){
    boolean correctInput2 = false;
    String isYes;

    // string path that represents the path of yes's and no's we will take as we go down the path:
    String path = ""; 

    // ask the first question stored in the tree and get a response
    System.out.println(animals.getData()); 
    while(correctInput2 == false){
      isYes = sc.nextLine();
      if(isYes.equals("yes") || isYes.equals("Yes") || isYes.equals("y") || isYes.equals("Y") || isYes.equals("ye")){
        correctInput2 = true;
        path = path + "Y";     
      }
      else if(isYes.equals("no") || isYes.equals("No") || isYes.equals("n") || isYes.equals("N")){
        correctInput2 = true;
        path = path + "N";   
      } 
      else{
        System.out.println("Try inputting something like yes or no");
        correctInput2 = false;
      }
    }
    correctInput2 = false;
    
    // now ask the rest of the questions, until we hit a leaf on the tree
    while(animals.followPath(path).isBranch()){
      System.out.println(animals.followPath(path).getData());
      while(correctInput2 == false){
        isYes = sc.nextLine();       
        if(isYes.equals("yes") || isYes.equals("Yes") || isYes.equals("y") || isYes.equals("Y") || isYes.equals("ye")){
          correctInput2 = true;
          path = path + "Y";
        }
        else if(isYes.equals("no") || isYes.equals("No") || isYes.equals("n") || isYes.equals("N")){
          correctInput2 = true;
          path = path + "N";
        } 
        else{
          System.out.println("Try inputting something like yes or no");
          correctInput2 = false;
        } 
      }  
      correctInput2 = false;    
    }

    guess = animals.followPath(path).getData();
    System.out.println("Is your animal a " + guess + "?");
      
    return path;
  }
}