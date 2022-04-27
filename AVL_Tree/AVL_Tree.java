/*
Written By: Joe Barchanowicz
AVL Tree
This file contains Class StringAVLNode
and Class StringAVLTree
*/ 

class StringAVLNode{
   private String item;
   private int balance;
   private StringAVLNode left, right;
   
   public StringAVLNode(String str){
      item = str;
   }   
   public int getBalance(){
      return this.balance;
   }
   public void setBalance(int bal){
      this.balance=bal;
   }
   public String getItem(){
      return this.item;
   }
   //no setItem
   public StringAVLNode getLeft(){
      return this.left;
   }
   public void setLeft(StringAVLNode pt){
      this.left = pt; 
   }
   public StringAVLNode getRight(){
      return this.right;
   }
   public void setRight(StringAVLNode pt){
      this.right = pt; 
   }

}//End Class StringNode

class StringAVLTree{
   StringAVLNode root;
  
   public StringAVLTree(){
      this.root=root;
   }//End StringAVLTree
   
   //rotate to the right
   private static StringAVLNode 
      rotateRight(StringAVLNode t){
      
      
      //    right rotate
      //
      //         t
      //        / \
      //       x   tC
      //      / \
      //     tA tB
      
      StringAVLNode x = t.getLeft();
      StringAVLNode tB = x.getRight();
      
      //rotation
      x.setRight(t);
      t.setLeft(tB);
   
      return x;
   }//end rotateRight
   
   //rotate to the left
   private static StringAVLNode rotateLeft(StringAVLNode t){
      //     left rotate
      //
      //         t
      //        / \
      //       tA  x
      //          / \
      //         tB tC
      
      StringAVLNode x = t.getRight();
      StringAVLNode tC = x.getLeft();
      
      //rotation
      x.setLeft(t);
      t.setRight(tC);
       
      return x;

   }//end rotateLeft
         
   public int height(){
      return height(root);
   }
   
   private static int height(StringAVLNode t){
      int heightCount = 0;
      if (t == null){      
         heightCount = 0;
      }
      else if (t.getBalance() <= 0){
         heightCount = height(t.getLeft()) + 1;
      }
      else{ 
         heightCount = height(t.getRight()) + 1;
      }

      return heightCount;  
   }//end height
   
   public int nadir(){
      return nadir(root);
   }
   //nadir count has to be O(n) because larger trees
   //with a balanced top will almost be impossible to 
   //find the shortest distance from root to leaf
   private int nadir(StringAVLNode t){
      int nadirCount = 0;
      if (t == null){      
         nadirCount = 0;
      }
      else if (t.getLeft() == null || t.getRight()== null){
         nadirCount = Math.max(nadir(t.getLeft()), 
         nadir(t.getRight())) + 1;
      }
      else{
         nadirCount = Math.min(nadir(t.getLeft()), 
         nadir(t.getRight())) + 1;
      }
      
      /*
      ***here is my O(logn) code that will not*** 
      ***work with larger trees***
      if(t.getBalance() == 0){
         if(t.getRight().getBalance() != 0){
         {
      }
      else if (t.getBalance() <= 0 || t.getLeft() == null){
         if(t.getBalance
         if(t.getRight() == null){
            nadirCount = nadir(t.getLeft()) + 1;
         }
         else{
            nadirCount = nadir(t.getRight()) + 1;
         }
      }
      else{
         nadirCount = nadir(t.getLeft()) + 1;
      }*/
  
      return nadirCount;  
   }//end nadir
   
   public int leafCt(){
      return leafCt(root);
   }
 
   private int leafCt(StringAVLNode t){
      int leafCount = 0;
      if (t == null){
         leafCount = 0; 
      }
      else if (t.getLeft() == null && t.getRight() == null){
         leafCount = 1;   
      }
      else{
         leafCount = leafCt(t.getLeft()) 
         + leafCt(t.getRight());
      }
      return leafCount;
   }
   
   public int balanced(){
      return balanced(root);
   }
 
   private int balanced(StringAVLNode t){
      int balancedCount;
      if (t == null){
         balancedCount = 0; 
      }
      //Case 1: balanced but has children
      else if (t.getBalance() == 0 && t.getLeft() != null 
      && t.getRight() != null){
         balancedCount = balanced(t.getLeft()) 
         + balanced(t.getRight()) + 1; 
      }
      
      //Case 2:leaf   
      else if (t.getBalance() == 0 && t.getLeft() 
      == null && t.getRight() == null){
         balancedCount = 1;
      }
      
      //Case 3: Not Balanced 
      else{
         balancedCount = balanced(t.getLeft()) 
         + balanced(t.getRight()); 
      }
      return balancedCount;
   }//end balanced

   public String successor(String str){
      successor(str, root);
      return str; 
   }
   
   private String successor(String str, StringAVLNode t){
      if (t == null){
         str = null;
      }
      //Case 1: found match
      else if(str.compareToIgnoreCase(t.getItem()) == 0){
      
         if(t.getRight() != null){
            
            //go right, then as far left as possible
            t = t.getRight();
            str = successor(str, t.getLeft());
            
         }
         else{
            //System.out.println(" ");
         }   
      }
      //Case 2: str less than t
      else if(str.compareToIgnoreCase(t.getItem()) < 0){
         
         successor(str, t.getLeft()); 
      }
      //Case 3: str greater than t
      else{
         successor(str, t.getRight());
      }
      return str;
   }
   
   public void insert(String str){
      root = insert(str, root);
   }
   
   private StringAVLNode insert
   (String str, StringAVLNode t){
      if (t == null){ 
			t = new StringAVLNode(str); 
      }
      
      //Insert: Case 1(Left)
      else if (str.compareToIgnoreCase(t.getItem()) < 0){
         
         int oldBalance = 44;
         if(t.getLeft() != null){
            oldBalance = t.getLeft().getBalance();
            t.setLeft(insert(str, t.getLeft()));
            int newBalance = t.getLeft().getBalance();
            
            //oldBalance = 0 and newBalance not zero
            //the tree height has changed and 
            //have to update the balance of the tree
            //and check to see if it is within AVL balance 
            //parameters
            if(oldBalance == 0 && newBalance != 0){
               t.setBalance(t.getBalance()-1);
               if(t.getBalance() == -2){
                  //Rotate Case 1: single right rotate
                  //t balance = -2 new balance = -1
                  if (newBalance == -1){
                     t = rotateRight(t);
                     //update balance factor of the two 
                     //nodes that were rotated in a
                     //single rotate the two nodes
                     //that rotated are balanced to 
                     //zero
                     t.setBalance(t.getBalance()+1);
                     t.getRight().setBalance
                      (t.getRight().getBalance()+2);
                  }
                  //Rotate Case 2: 
                  //Double rotate (right then left)
                  else if (newBalance == 1){
                     //    case 1:
                     //       x
                     //      / 
                     //     y  
                     //      \
                     //       z
                        
                     if (t.getLeft().getLeft()
                         == null){
                         t.setLeft(rotateLeft(t.getLeft()));
                        //update balance after the 
                        //first rotation
                        t.getLeft().setBalance(t.getLeft()
                         .getBalance()-1);
                        t.getLeft().getLeft().setBalance
                         (t.getLeft().getLeft()
                         .getBalance()-1);
                        //update balance after the 
                        //second rotation
                        t = rotateRight(t);
                        t.setBalance(t.getBalance()+1); 
                        t.getRight().setBalance(t.getRight()
                         .getBalance()+2);
                         }
                     
                     //    case 1:
                     //       x
                     //      / \
                     //     y  tC
                     //    / \
                     //   tA  z
                     //      /
                     //     tB
                     else if(t.getLeft().getRight()
                         .getBalance()==-1){
                        t.setLeft(rotateLeft(t.getLeft()));
                        //update balance after the 
                        //first rotation
                        t.getLeft().setBalance(t.getLeft()
                         .getBalance()-1);
                        t.getLeft().getLeft().setBalance
                         (t.getLeft().getLeft()
                         .getBalance()-1);
                        //update balance after the 
                        //second rotation
                        t = rotateRight(t);
                        t.setBalance(t.getBalance()+2); 
                        t.getRight().setBalance(t.getRight()
                         .getBalance()+3);
                     } 

                     //    case 2:
                     //       x
                     //      / \
                     //     y  tC
                     //    / \
                     //   tA  z
                     //        \
                     //        tB

                     else{
                        t.setLeft(rotateLeft(t.getLeft()));
                        //update balance after the first 
                        //rotation
                        t.getLeft().setBalance(t.getLeft()
                         .getBalance()-2);
                        t.getLeft().getLeft().setBalance
                        (t.getLeft().getLeft().getBalance()
                         -2);
                        //update balance after the second 
                        //rotation
                        t = rotateRight(t);
                        t.setBalance(t.getBalance()+1); 
                        t.getRight().setBalance(t.getRight()
                         .getBalance()+2);
                     } 
                  }
               }  
            }
         }
         else{
            t.setLeft(insert(str, t.getLeft()));
            t.setBalance(t.getBalance() - 1);
           
         }
      }//end left insert
        
      //Insert: Case 2(Right)
      else if (str.compareToIgnoreCase(t.getItem()) > 0){
         int oldBalance = 444;
         if(t.getRight() != null){
            oldBalance = t.getRight().getBalance();
            t.setRight(insert(str, t.getRight()));
            int newBalance = t.getRight().getBalance();
            
            //Rotate Case 1: single left rotate
            if(oldBalance == 0 && newBalance != 0){
               t.setBalance(t.getBalance()+1);
               if(t.getBalance() == 2){
                  if (newBalance == 1){
                     t = rotateLeft(t);
                     t.setBalance(t.getBalance()-1);
                     t.getLeft().setBalance(t.getLeft()
                     .getBalance()-2);
                  }
                  else if (newBalance == -1){
                     
                     //    case 1:
                     //       x
                     //      / \
                     //     tA  y
                     //        / \
                     //       z  tC
                     //        \
                     //        tB

                    //Rotate case 2:  
                    //right child of right child with 
                    //right child of left child  
                    if(t.getRight().getLeft()
                     .getBalance()==1){
                        t.setRight(rotateRight
                         (t.getRight()));
                        //update balance after the first 
                        //rotation
                        t.getRight().setBalance
                        (t.getRight().getBalance()+1);
                        t.getRight().getRight().setBalance
                         (t.getRight().getRight()
                         .getBalance()+1);
                        //update balance after the second 
                        //rotation
                        t = rotateLeft(t);
                        t.setBalance(t.getBalance()-2); 
                        t.getLeft().setBalance(t.getLeft()
                         .getBalance()-3);
                     } 
                     
                     //    case 2:
                     //       x
                     //        \
                     //         y
                     //        / 
                     //       z  
                     //       
                     //     

                     else if(t.getRight().getRight()
                      == null){
                        t.setRight(rotateRight
                         (t.getRight()));
                        //update balance after the first 
                        //rotation
                        t.getRight().setBalance
                        (t.getRight().getBalance()+1);
                        t.getRight().getRight().setBalance
                         (t.getRight().getRight()
                         .getBalance()+1);
                        //update balance after the second 
                        //rotation
                        t = rotateLeft(t);
                        t.setBalance(t.getBalance()-1); 
                        t.getLeft().setBalance(t.getLeft()
                         .getBalance()-2);
                     } 



                     //    case 3:
                     //       x
                     //      / \
                     //     tA  y
                     //        / \
                     //       z  tC
                     //      / 
                     //     tB

                     else{
                        t.setRight(rotateRight
                         (t.getRight()));
                        //update balance after the first 
                        //rotation
                        t.getRight().setBalance
                         (t.getRight().getBalance()+2);
                        t.getRight().getRight().setBalance
                        (t.getRight().getRight()
                        .getBalance()+2);
                        //update balance after the second 
                        //rotation
                        t = rotateLeft(t);
                        t.setBalance(t.getBalance()-1); 
                        t.getLeft().setBalance(t.getLeft()
                         .getBalance()-2);
                     } 
                  }
               }
            }
         }
       
         else{
            t.setRight(insert(str, t.getRight()));
            t.setBalance(t.getBalance() + 1);
         }

      }//end right insert
      
      //Insert: Case 3(Already exists)
      else {
         //str already exists in AVL tree, do nothing
      }
 
      return t;
   }
             
   public static String myName(){
      return "Joseph Barchanowicz";
   }
}//End Class StringAVLTree

