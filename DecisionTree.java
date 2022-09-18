import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

/**
 *  Implements a binary decision tree
 *
 *  @author Anna Wilson
 *  @version Fall 2021
 *
 */
public class DecisionTree extends BinaryTree<String> {

  /** leaf constructor 
  * @param s data to add to leaf
  */
  public DecisionTree(String s) {
    super(s);
  }

  /**branch constructor 
  * @param s data to add to branch
  * @param left leaf
  * @pram no right leaf
  */
  public DecisionTree(String s, DecisionTree yes, DecisionTree no){
    super(s, yes, no);
  }

  /** deep copy constructor 
  * @param tree tree to be copied
  */
  public DecisionTree(DecisionTree tree){
    super(tree.getData());
    this.setLeft(tree.getLeft());
    this.setRight(tree.getRight());
  }

  /** @override */
  public void setLeft(BinaryTree<String> left) {
    if (left instanceof DecisionTree) {
      super.setLeft(left);
    } else {
      throw new UnsupportedOperationException();
    }
  }

  /** @override */
  public DecisionTree getLeft() {
    return (DecisionTree)super.getLeft();
  }

  /** @override */
  public void setRight(BinaryTree<String> right) {
    if (right instanceof DecisionTree) {
      super.setRight(right);
    } else {
      throw new UnsupportedOperationException();
    }
  }

  /** @override */
  public DecisionTree getRight() {
    return (DecisionTree)super.getRight();
  }

  /**
  * follows specified path to a location on a tree
  * @param path is a string represention of the path of yes's and no's down the tree
  * @return the location found by the path
  */
  public DecisionTree followPath(String path){
    char[] pathArray = path.toCharArray();
    DecisionTree answer;
    if(pathArray[0] == 'Y'){
      answer = getLeft();
    }
    else{
      answer = getRight();
    }
    for(int i = 1; i < pathArray.length; i++){
      if(pathArray[i] == 'Y'){
        answer = answer.getLeft();
      }
      else{
        answer = answer.getRight();
      }
    }
    return answer;
  }

  /**
  * adds branches and/or leaves at a specified location
  * @param path string representation of the path to the location
  * @param animals tree that you are adding to
  * @param branch the branch (or leaf) you are adding
  */
  public void addAtLocation(String path, DecisionTree animals, DecisionTree branch){
    char[] pathArray = path.toCharArray();
    DecisionTree location = animals;
    for(int i = 0; i < pathArray.length; i++){
      if(i == pathArray.length - 1){
        if(pathArray[i] == 'Y'){
          location.setLeft(branch);
        }
        else{
          location.setRight(branch);
        }
      }
      else if(pathArray[i] == 'Y'){
        location = location.getLeft();          
      }
      else{
        location = location.getRight();
      }
    }
  }

  /**
  * method that creates a string representation of a tree in inorder
  * @param t the tree you are turning to a string
  */
  public static String inorderStringDT(DecisionTree t) {
      if (t==null) {
        return "";
      } 
      else {
        return "("+inorderString(t.getLeft())+" "+t.getData()+" "+inorderString(t.getRight())+")";
      }
  }

  /**
  * method that creates a string representation of a tree in breadth first order, along with listing the path to reach a node by each node
  * @param bfString will be the string representation we return
  * @param tree is the tree we are making a string
  * @param path will be the path we make each node
  * @return the string representaion
  */
  public String breadthFirstString(String bfString, DecisionTree tree, String path){
    if(path == ""){
      bfString = tree.getData() + "\n";
    }
    if(tree.isLeaf()){      
    }
    else{
      bfString = bfString + path + "Y" + " " + tree.getLeft().getData() + "\n";
      bfString = bfString + path + "N" + " " + tree.getRight().getData() + "\n";
      bfString = breadthFirstString(bfString, tree.getLeft(), path + "Y");
      bfString = breadthFirstString(bfString, tree.getRight(), path + "N");
    }
      return bfString;
  }

  /**
  * writes a string to a file
  * @param tree is the tree to be written
  * @param fileName will be the name of the file we make
  */
  public void writeToFile(String tree, String fileName){
    try{
      FileWriter fileWriter = new FileWriter(fileName);
      PrintWriter out = new PrintWriter(fileWriter);
      out.print(tree);
      out.close();
    }
    catch(IOException e){
      System.out.println("IO Exception");
    }
  }

  /**
  * reads a file of a tree and turns it into an actual tree
  * @param fileName name of the file we are readinf from
  */
  public static DecisionTree readFromFile (String fileName) throws IOException {
      BufferedReader file = new BufferedReader(new FileReader(fileName));
      Scanner sc = new Scanner(file);
      DecisionTree fileTree = new DecisionTree(sc.nextLine());
      while(sc.hasNext()){
        String [] lineArray = sc.nextLine().split(" ");
        String path = lineArray[0];
        String data = "";
        for(int i = 1; i < lineArray.length; i++){
          data = data + lineArray[i] + " ";
        }
        DecisionTree branch = new DecisionTree(data);
        fileTree.addAtLocation(path, fileTree, branch);
      }
      sc.close();
      return fileTree;     
  }   
}