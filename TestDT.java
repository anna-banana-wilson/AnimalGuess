import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class TestDT {
  public static void main(String[] args) {
//     DecisionTree tree = new DecisionTree("Is it a Mammal?");
//     DecisionTree branch1 = new DecisionTree("Does it lay eggs?");
//     DecisionTree branch2 = new DecisionTree("Is it a reptile?");
//     tree.setLeft(branch1);
//     tree.setRight(branch2);




// DecisionTree leaf3 = new DecisionTree("leaf3");
// DecisionTree leaf4 = new DecisionTree("leaf4");
// DecisionTree branch = new DecisionTree("branch", leaf3, leaf4); 
// branch1.setLeft(branch);

// // tree.addAtLocation("YY", tree, branch);
// // System.out.println(tree.getLeft().getLeft().getRight().getData());

// System.out.println("TREE: "+DecisionTree.inorderStringDT(tree));

// DecisionTree location = tree.getLeft().getLeft();
// location.setData("boop");
// System.out.println(tree.getLeft().getLeft().getData());

    // branch1.setLeft(new DecisionTree("Platypus"));
    // branch1.setRight(new DecisionTree("Cat"));
    // branch2.setLeft(new DecisionTree("Lizard"));
    // branch2.setRight(new DecisionTree("Frog"));
    
    // System.out.println(tree.getData());

    // System.out.println(tree.followPath("YN"));

    // DecisionTree copy = new DecisionTree(tree);
    // System.out.println(copy.followPath("YN"));



    // DecisionTree fish = new DecisionTree("shark");
    // DecisionTree firefly = new DecisionTree("firefly");




    DecisionTree yes3 = new DecisionTree("lizard");
    DecisionTree no4 = new DecisionTree("shark");

    DecisionTree elephant = new DecisionTree("elephant");
    DecisionTree bunny = new DecisionTree("bunny");

   

    DecisionTree yes1 = new DecisionTree("cat");
    DecisionTree no2 = new DecisionTree("is it big?", elephant, bunny);

    DecisionTree yes = new DecisionTree("does it like fish?", yes1, no2);
    DecisionTree no = new DecisionTree("is it a reptile?", yes3, no4);
    
    DecisionTree tree = new DecisionTree("is it a mammal?", yes, no);

    // System.out.println(test.inorderStringDT(test));
    String stringTree = "";
    //test.printBreadthFirst(test, "");  
    stringTree = tree.breadthFirstString(stringTree, tree, "");
    // tree.readToFile("hi.txt", stringTree);

    try{
      DecisionTree.readFromFile("AnimalTree.txt");
    }
    catch(IOException e){
      System.out.println("IOException");
    }

  }
}