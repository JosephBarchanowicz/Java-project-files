/*
Written By: Joe Barchanowicz
Knight Board
This file contains class Pair
class KnightBoard and methods to solve KnightBoard
*/

import java.io.*; 
import java.util.*;

class Pair {
   private int row, col;
   
   // Constructor
   public Pair(int row, int col) {
      this.row = row;
      this.col = col;
   } 
   
   // Copy constructor  
   public Pair(Pair p) {
      row = p.row;
      col = p.col;
   }
   
   //Getters and setters
   public void setRow(int row){
      this.row = row;
   }
   public int getRow(){
      return this.row;
   }
   public void setCol(int col){
      this.col=col;
   }
   public int getCol(){
      return this.col;
   }
   
   // override Java's default toString
   public String toString() {
      return "(" + String.valueOf(row) 
              + ", " + String.valueOf(col) + ")"; 
   } 
}//End Pair Class

//The main class for the chess board and methods
//for implementing the algorithm
//for solving KnightBoard
class KnightBoard {
   private int board[][]; 
   private int numRows, numCols; 
   private Pair start = new Pair(numRows, numCols);
   private ArrayList<Pair>move = new ArrayList<Pair>();
   
      
   public KnightBoard(){
      board = new int[8][8];
      for (int row = 0; row < 8; row++){
         for (int col = 0; col < 8; col++){ 
            board[row][col] = 0;
         }
      }  
      
      move = new ArrayList<Pair>();
      start = new Pair(-1, -1);    
   }
   
   public KnightBoard(String fileName) throws IOException {
   
      boolean inputError = false;
      int int1, int2;
      
      StringTokenizer input;
      BufferedReader inFile = new BufferedReader
      (new FileReader(fileName));
         
      //reading the size of the Board
      String lineStart;
      String inputLine = inFile.readLine();
      input = new StringTokenizer(inputLine);
      lineStart = input.nextToken(); // read "board"     
      lineStart = input.nextToken(); // read "size:"     
      numRows = Integer.parseInt(input.nextToken());
      numCols = Integer.parseInt(input.nextToken());
      this.board = new int[numRows][numCols];
      
      //reading which square to start in from file
      inputLine = inFile.readLine();
      String tempArray[]=inputLine.split(" ");
      numRows = Integer.parseInt(tempArray[2]);
      numCols = Integer.parseInt(tempArray[3]);
      start.setRow(numRows);//setting start row
      start.setCol(numCols);//setting start column
      
      //Marking the starting position on board
      board[numRows][numCols] = 1;
      
      //Reading legal moves
      //reading peice movement:
      inputLine = inFile.readLine();
      int i = 0;
      while ((inputLine = inFile.readLine()) != null){
         //Temp legal moves holder
         Pair temp = new Pair(numRows, numCols);
         String testArray1[]=inputLine.split(" ");
         String tempArray1[]=inputLine.split(" ");
         numRows = Integer.parseInt(testArray1[0]);
         numCols = Integer.parseInt(testArray1[1]);
         temp.setRow(numRows);//setting start row
         temp.setCol(numCols);//setting start column
         move.add(temp);
      }//End while loop     
   }//End KnightBoard
   
   //copy constructor
   public KnightBoard(KnightBoard b){
      this.board = b.board;
      this.move = b.move;
      this.start = b.start;       
   }
     
   //printing completed board
   public String toString(){
      String result= " ";
      
      //setting string formatting 
      String less = "%3s";
      String more = "%4s";
      
      //finding board size to set formatting of output
      int row = 0;
      int totalBoardSize = board.length * board[row].length;
      String numChar;
      
      //prints board less than 10X10 (or equal value)
      if (totalBoardSize < 100){
         numChar = less;
      }
      //prints any board greater than or equal to 10X10
      else{
         numChar = more;
      }
         
      for (row = 0; row < board.length; row++) {
         for (int col = 0; col < board[row].length; col++) {
            System.out.printf(numChar, board[row][col]);
         }
         System.out.printf("\n");
      }

      return result;
   }//end to string
   
   //check toString
   public String toString2(){
      String result = new String();
      for (int row = 0; row < board.length; row++) {
         for (int col = 0; col < board[row].length; col++) {
            result = 
               result + String.valueOf(board[row][col]);
         }
      }
      return result;
   }//end toString 2
   
   //checks if move is in bounds and not already taken
   private boolean tryMove(Pair sq){
      boolean checkMove = true;
      if (sq.getRow() < 0 
            || sq.getRow() > board.length - 1 
            || sq.getCol() < 0 
            || sq.getCol() > board[0].length - 1
            || board[sq.getRow()][sq.getCol()] != 0
            || move.size() == 0){
               
               //not a legal move
               checkMove = false;
            }
      //returns true if legal, false if illegal move
      return checkMove; 
      
   }//End try move

   //counts number of legal moves from pair sq
   private int moveCt(Pair sq) {
      int count = 0;
      
      //temp arraylist to hold next move from pair sq 
      Pair nextMoveCounter = new Pair(numRows, numCols);
      
      //for loop that gets moves 
      //and checks if they are legal  
      for (int i = 0; i < move.size(); i++){
         nextMoveCounter.setRow(move.get(i).getRow() 
            + sq.getRow());
         nextMoveCounter.setCol(move.get(i).getCol() 
            + sq.getCol());
         
         //check if move is legal
         if (tryMove(nextMoveCounter) == true){
            
            //counter for legal moves from position pair sq
            count = count + 1;
         }
      }
      
      //returns total number of legal moves from the pair sq
      return count; 
      
   }//End MoveCt
   
   //updating the board
   private void makeMove(Pair sq, int counter) {     
      board[sq.getRow()][sq.getCol()] = counter;          
   }//End make move
  
  //solving knights tour problem
  public void solve() { 
      int bestMove, i, bestMoveCt;
      Pair curSpot = new Pair(numRows, numCols);
      Pair nextMove = new Pair(numRows, numCols); 
      boolean done = false;
      int moveCount = 1;
      
      //empty array list
      if(move.size() == 0){
         //Do nothing
      }      
      else{
   
         bestMove = 5;
         
         //setting curSpot to equal start position
         curSpot = start;
         
         //starting while loop to solve knights tour
         while(done != true){
            bestMoveCt=move.size();
            
            //initally setting i = to the largest 
            //size of move arraylist
            //to see if move is legal the inital value is at
            //its largest
            i = move.size();
            //for loop to check moves
            for (int j = 0; j < move.size(); j++){
            
               //setting the nextMove row and column to
               //position of arraylist move plus curspot        
               nextMove.setRow(move.get(j).getRow() 
                  + curSpot.getRow());
               nextMove.setCol(move.get(j).getCol() 
                  + curSpot.getCol());
               
               
               //checking if next move is legal
               if (tryMove(nextMove) == true){
                  //i is counting the number of moves
                  i = moveCt(nextMove);
                  
                  //setting the bestmove counter variable 
                  if (i <= bestMoveCt){
                     //bestMoveCt holds current bestmove
                     //legal moves number from nextMove
                     bestMoveCt = i;
                     //setting bestmove = to the position 
                     //in the arraylist 
                     //with the best move
                     bestMove = j;
                     
                  }//end if
               }//end if
            }//end for loop
            
            //setting row and col for 
            //nextrow from the best moves
            nextMove.setRow(move.get(bestMove).getRow() 
               + curSpot.getRow());
            nextMove.setCol(move.get(bestMove).getCol() 
               + curSpot.getCol());
            
            //checking if the next move is 
            //really a legal move              
            if (tryMove(nextMove) == true){
               moveCount++;
               makeMove(nextMove, moveCount);
               
               //updating curSpot
               curSpot.setRow(nextMove.getRow());
               curSpot.setCol(nextMove.getCol());
            }
            //there are no more legal moves 
            else{
               done = true;  
            }
         }//end while loop
      }
   }//End Solve

   public static String myName() { 
      return "Joseph Barchanowicz";
   }//End my name
  
}//End Class KnightBoard 