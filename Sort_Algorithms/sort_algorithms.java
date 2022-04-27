/*
Written By: Joe Barchanowicz
Sort Algorithms
This file contains class pair
and the class ArraySorts
*/

import java.math.*;

class ArraySorts{

   /////////////////////////////////////////////////////////
   /////////////    insertion sort    //////////////////////
   /////////////////////////////////////////////////////////
   
   public static void insertionSort(int a[], int LF, int n){
      
      for (int i = LF+1; i <= n; ++i) { 
         int save = a[i]; 
         int j = i - 1;
         
         //shift into place  
         while (j >= 0 && a[j] > save) { 
            a[j + 1] = a[j]; 
            j = j - 1; 
         } 
         a[j + 1] = save;
      }  
   } 

   ///////////////////////////////////////////////////
   // QuickSort1:outside-in partition(random pivot) //
   ///////////////////////////////////////////////////
   
   public static void QuickSort1
    (int a[], int n, int cutoff){
      QuickSort1(a, 0, n-1, cutoff-1);
      insertionSort(a, 0, n-1);  
   }
   
   private static void QuickSort1
    (int a[], int LF, int RT, int cutoff){
   
      while(RT-LF >= cutoff){
         
         //setting intial LF and RT pair
         pair QS1 = new pair(LF,RT);
         
         //choose random pivot
         int pivot = (int)(Math.random()*((RT-LF)+1))+LF;      
         int pivotValue = a[pivot];
         int save;
         
         //sorting values   
         while(LF <= RT){  
            while(RT >= LF && a[LF] < pivotValue){
               LF++;
            }
                             
            while(RT >= LF && a[RT] > pivotValue){
               RT--;
            }
            
            //Swap values
            if(RT >= LF){
               save = a[LF];
               a[LF] = a[RT];
               a[RT] = save;
               LF++;
               RT--;
            }
         }
         
         //saveLF=initial Left
         //saveRT=initial Right
         //midLeft=end of left partition
         //midRight=beginning of Right partition
         int saveLF = QS1.getLeft();
         int saveRT= QS1.getRight();
         
         //setting new pair values
         QS1.setLeft(RT);
         QS1.setRight(LF);
         
         //midLeft=new pair value for left
         //midRight=new pair value for right
         int midLeft = QS1.getLeft();
         int midRight = QS1.getRight();
         
         //recurssive call on smaller partition
         //loop larger 
         if ((midLeft - saveLF) < (saveRT - midRight)){
         
            QuickSort1(a, saveLF, midLeft, cutoff);
            LF = midRight;
            RT = saveRT;
         }
         else{
            QuickSort1(a, midRight, saveRT, cutoff);
            LF=saveLF;
            RT=midLeft;
         }
         
      } 
   }
   
   //////////////////////////////////////////////////////////////
   ///  QuickSort2: left-to-right: 1 partition, random pivot  ///
   //////////////////////////////////////////////////////////////
   
   public static void QuickSort2
    (int a[], int n, int cutoff){
      QuickSort2(a, 0, n-1, cutoff-1);
      insertionSort(a, 0, n-1); 
   }
   
   private static void QuickSort2
    (int a[], int LF, int RT, int cutoff){
   
      while(RT-LF >= cutoff){

         //choose random pivot
         int pivot = (int)(Math.random()*((RT-LF)+1))+LF;
  
         //move pivot to beginning of array
         int save = a[LF];
         a[LF] = a[pivot];
         a[pivot] = save;
            
         //set pointers: 
         //LS = Last Small
         //FU = First Unknown 
         int LS = LF;
         int FU = LF + 1;
         
         //variable to keep track of
         //how many times FU = pivot
         int equal = 0;
         
         while(FU <= RT){
            while(FU <= RT && a[FU] > a[LF]){                 
               FU++;
            }
            while(FU <= RT && a[FU] < a[LF]){
               LS++;
               save = a[FU];
               a[FU] = a[LS];
               a[LS] = save;
               FU++;
            }
            while(FU <= RT && a[FU] == a[LF]){
               equal++;
               //if a[FU] = pivot
               //then every other time 
               //swap values
               if(equal%2==0){
                  LS++;
                  save = a[FU];
                  a[FU] = a[LS];
                  a[LS] = save;
                  FU++;
               }
               else{
                  FU++;
               }
            }              
         }
        
         //move pivot to appropriate place
         save = a[LF];
         int mid = LS;
         a[LF] = a[mid];
         a[mid] = save;
         
         //recurssive call on smaller partition
         //loop larger 
         if((mid-LF)<(RT-mid)){   
            QuickSort2(a, LF, mid-1, cutoff);
            LF = mid+1;
         }
         else{
            QuickSort2(a, mid+1, RT, cutoff);
            RT = mid-1;
         }  
      }
   }
   
   //////////////////////////////////////////////////////////
   /////  QuickSort3:left-2-Right 2 pivots, both random  ////
   //////////////////////////////////////////////////////////
   public static void QuickSort3
    (int a[], int n, int cutoff){
      QuickSort3(a, 0, n-1, cutoff-1);
      insertionSort(a, 0, n-1); 
   }


   
   private static void QuickSort3
    (int a[], int LF, int RT, int cutoff){
   
      while(RT-LF >= cutoff){
      
      pair QS3 = new pair(LF,RT);
         
         //generic variable help move array element
         int save;
         
         //select first random pivot 
         int pivot1 = (int)(Math.random()*((RT-LF)+1))+LF;
         
         //save pivot in a[0]
         save = a[LF];
         a[LF] = a[pivot1];
         a[pivot1] = save;
         
         //select second random pivot 
         int pivot2 = (int)(Math.random()*((RT-(LF+1))+1))+(LF+1);
              
         //check pivot2 > pivot1
         //move into correct location
         if(a[pivot2] >= a[LF]){
            save = a[RT];
            a[RT] = a[pivot2];
            a[pivot2] = save;
         }
         
         else{
            //special case: a[LF] > a[RT]
            //and both are pivots
            if(pivot2 == RT && a[LF] > a[RT]){
               save = a[RT];
               int save2 = a[pivot2];
               a[RT] = a[LF];
               a[LF] = save2;
            }
            else{
               save = a[LF];
               a[LF] = a[pivot2];
               a[pivot2] = save;
               save = a[RT];
               a[RT] = a[pivot2];
               a[pivot2] = save;
            }
              
         } 
         
         //setting pointers
         //LS = Last Small
         //FU = First unknown
         //FB = First Big
         int LS = LF;
         int FU = LF+1;
         int FB = RT;
         
         //counter variable for when values = pivot
         int equal = 0;
         
         
         while(FU < FB){
         //Less than small pivot
            while (FU <= FB && a[FU] < a[LF]){
            
               LS++;
                  
               //swap LS with FU
               save = a[FU];
               a[FU] = a[LS];
               a[LS] = save;
         
               FU++;
           
            }
                                   
            //inbetween small and large pivot
            while (FU <= FB && a[FU] > a[LF]  && a[FU] < a[RT]){
               FU++;  
            }
            
            //greater than large pivot
            while(FU < FB && a[FU] > a[RT]){
        
               FB--;
               save = a[FB];
               a[FB] = a[FU];
               a[FU] = save;
         
            }
            
            //a[FU] == one of the pivots
            //every other time swap values
            if(FU < FB && a[FU] == a[LF]){ 
               equal ++;
               if(equal%2==1){
                  LS++;
                  //swap LS with FU
                  save = a[FU];
                  a[FU] = a[LS];
                  a[LS] = save;
         
                  FU++;
               }
               else if(equal%2==0){
                  FU++;
               }
            }
            else if(FU < FB && a[FU] == a[RT]){
               equal ++;
               if(equal%2==1){
                  FB--;
                  save = a[FB];
                  a[FB] = a[FU];
                  a[FU] = save;
               }
               else if(equal%2==0){
                  FU++;
               }
            }
         }
             
         //Move pivots back to correct location
         save = a[LF];
         a[LF] = a[LS];
         a[LS] = save;
         save = a[RT];
         a[RT] = a[FB];
         a[FB] = save;            
         
         //saveLF=initial Left
         //saveRT=initial Right
         //midLeft=end of left partition
         //midRight=beginning of Right partition 
         int saveLF = QS3.getLeft();
         int saveRT = QS3.getRight();  
         QS3.setLeft(LS);
         QS3.setRight(FB);
         int midLeft = QS3.getLeft();
         int midRight = QS3.getRight();
         
         //Left smaller than middle
         //and middle smaller than right
         //loop right partition
         if((midLeft-saveLF) < ((midRight-1)-(midLeft+1)) 
           && (midRight-1)-(midLeft+1)<(saveRT-midRight)){
          
            QuickSort3(a, saveLF, midLeft - 1, cutoff);
            QuickSort3(a, midLeft+1, midRight-1, cutoff);
            LF = midRight;
            RT = saveRT;
         }
         
         //middle smaller than left
         //and right smaller than left
         //loop Left partition
         else if((midRight-1)-(midLeft+1) < (midLeft-saveLF) 
           && (saveRT-midRight)<(midLeft-saveLF)){
            
            QuickSort3(a, midLeft+1, midRight-1, cutoff);
            QuickSort3(a, midRight+1, saveRT, cutoff);
            LF=saveLF;
            RT=midLeft-1;
         }
         
         //right smaller than middle
         //and left smaller than middle
         //loop middle partition
         else{
            QuickSort3(a, midRight+1, saveRT, cutoff);
            QuickSort3(a, saveLF, midLeft - 1, cutoff);
            RT=midRight-1;
            LF=midLeft+1;
         }
         
      }  
   }
   
   ////////////////////////////////////////////////////////////
   //////   QuickSort4:outside-in(pivot=a[LF])   //////////////
   ////////////////////////////////////////////////////////////
   
   public static void QuickSort4
    (int a[], int n, int cutoff){
      QuickSort4(a, 0, n-1, cutoff-1);
      insertionSort(a, 0, n-1);
   }
   
   
   private static void QuickSort4
    (int a[], int LF, int RT, int cutoff){
      
      while(RT-LF >= cutoff){
         
         //setting intial LF and RT pair
         pair QS4 = new pair(LF,RT);
         
         //Set pivotValue
         int pivot = LF;
         int pivotValue = a[LF];
         int save;
         
         //sorting        
         while(RT >= LF ){
            while(RT >= LF && a[LF] < pivotValue){
               LF++;
            }
            while(RT >= LF && a[RT] > pivotValue){
               RT--;
            }
            
            //Swap values
            if(RT >= LF){
               save = a[LF];
               a[LF] = a[RT];
               a[RT] = save;
               LF++;
               RT--;
            }
         }
         
         //saveLF=initial Left
         //saveRT=initial Right
         int saveLF = QS4.getLeft();
         int saveRT = QS4.getRight();
         
         //updating pair
         QS4.setLeft(RT);
         QS4.setRight(LF);
         
         //midLeft=end of left partition
         //midRight=beginning of Right partition
         int midLeft = QS4.getLeft();
         int midRight = QS4.getRight();

         //recurssive call on smaller partition
         //loop larger 
         if ((midLeft - saveLF) < (saveRT - midRight)){
            QuickSort4(a, saveLF, midLeft, cutoff);
            LF=midRight;
            RT=saveRT;
         }
         else{
            QuickSort4(a, midRight, saveRT, cutoff);
            RT=midLeft;
            LF=saveLF;
         }
                      
      }
   }
   
   ///////////////////////////////////////////////////////////////
   ///  QuickSort5: left-to-right: 1 partition, pivot = a[LF]  ///
   ///////////////////////////////////////////////////////////////
   
   public static void QuickSort5
    (int a[], int n, int cutoff){
      QuickSort5(a, 0, n-1, cutoff-1);
      insertionSort(a, 0, n-1); 
   }
   
   private static void QuickSort5
    (int a[], int LF, int RT, int cutoff){
      
      while(RT-LF >= cutoff){
      
         int pivot = LF;

         //set pointers: 
         //LS = Last Small
         //FU = First Unknown 
         int LS = LF;
         int FU = LF + 1;
         
         //variable to keep track of
         //how many times FU = pivot
         int equal = 0;
         
         //generic varialbe to help move
         //array values
         int save;
         
         //sort
         while(FU <= RT){
            while(FU <= RT && a[FU] > a[LF]){
               FU++;
            }
            while(FU <= RT && a[FU] < a[LF]){
               LS++;
               save = a[FU];
               a[FU] = a[LS];
               a[LS] = save;
               FU++;
            }
            while(FU <= RT && a[FU] == a[LF]){
               equal++;
               if(equal%2==0){
                  LS++;
                  save = a[FU];
                  a[FU] = a[LS];
                  a[LS] = save;
                  FU++;
               }
               else{
                  FU++;
               }
            }
         }
         
         //move pivot to appropriate place
         save = a[LF];
         int mid = LS;
         a[LF] = a[mid];
         a[mid] = save;
         
         //recurssive call on smaller partition
         //loop larger
         if((mid-LF)<(RT-mid)){   
            QuickSort5(a, LF, mid-1, cutoff);
            LF=mid+1;
         }
         else{
            QuickSort5(a, mid+1, RT, cutoff);
            RT=mid-1;
         }
           
      }
   }

      
   //////////////////////////////////////////////////
   // AlmostQS1:outside-in partition(random pivot) //
   //////////////////////////////////////////////////
   
   public static void AlmostQS1(int a[], int n, int cutoff){
      QuickSort1(a, 0, n-1, cutoff-1);
      //insertionSort(a, 0, n-1);
   }
   
   
   /////////////////////////////////////////////////
   //  AlmostQS2: left-to-right: 1 random pivot  ///
   /////////////////////////////////////////////////
   
   public static void AlmostQS2(int a[], int n, int cutoff){
      QuickSort2(a, 0, n-1, cutoff-1);
      //insertionSort(a, 0, n-1); 
   }


   /////////////////////////////////////////////////////////
   /////  AlmostQS3:left-2-Right 2 pivots, both random  ////
   /////////////////////////////////////////////////////////
   
   public static void AlmostQS3(int a[], int n, int cutoff){
      QuickSort3(a, 0, n-1, cutoff-1);
      //insertionSort(a, 0, n-1); 
   }

   /////////////////////////////////////////////////
   /////  HeapSort: bottom-up build heap  //////////
   /////////////////////////////////////////////////
   
   public static void HeapSortBU(int a[], int n){
   
      //Build Heap Bottom up
      for (int i = n / 2 - 1; i >= 0; i--){ 
         HeapBuildBU(a, n, i);
      } 
      
      //Sort Heap
      for (int i = n - 1; i >= 0; i--){ 
         // Move current a[0] to a[n-1] 
         int save = a[0]; 
         a[0] = a[i]; 
         a[i] = save; 
  
         // call HeapBuildBU on the reduced heap 
         HeapBuildBU(a, i, 0); 
      } 
   }
   
   private static void HeapBuildBU(int a[], int n, int i){
 
      int parent = i; // Initialize parent as root 
      int leftChild = 2*i + 1; // left = 2*i + 1 
      int rightChild = 2*i + 2; // right = 2*i + 2 
  
      // If left child is larger than parent 
      if (leftChild < n && a[leftChild] > a[parent]){ 
         parent = leftChild; 
      }
  
      // If right child is larger than parent so far 
      if (rightChild < n && a[rightChild] > a[parent]){ 
         parent = rightChild; 
      }
  
      //If parent is not root
      //trickle down
 
      if (parent != i){ 
      
         int save = a[i];  
         a[i] = a[parent];
         a[parent] = save;
         
         // Recursively build heap 
         //from the affected sub-tree 
         HeapBuildBU(a, n, parent); 
      }           
   }
   
   //////////////////////////////////////////////
   ///////  HeapSort: Top-Down Build  ///////////
   //////////////////////////////////////////////
   
   public static void HeapSortTD(int a[], int n){
      
      //Build Heap topDown 
      HeapBuildTD(a, n-1);
      
      //sort heap
      for (int i = n - 1; i >= 0; i--) { 
         // Move current a[0] to a[n-1] 
         int save = a[0]; 
         a[0] = a[i]; 
         a[i] = save; 
  
         // call HeapBuildBU on the reduced heap 
         HeapBuildBU(a, i, 0); 
      }  
   }
   
   private static void HeapBuildTD(int a[], int n){
   
      for (int i = 1; i <= n; ++i){

         int save = a[i];

         if(a[i] > a[(i - 1)/2]){
            int j = i;
                     
            //trickle up
            while(save > a[(j - 1)/2] && j > 0){
               
               a[j] = a[(j - 1)/2];
               j = (j - 1)/2;   
            }
            a[j] = save;
        } 
      }       
   } 
   
   public static String myName() { 
      return "Joseph Barchanowicz";
   }//End my name
        
}

class pair{
   public int left, right;
   
   public pair(int left, int right){
      this.left = left;
      this.right = right;
   }
   
   //Getters and setters
   public void setLeft(int left){
      this.left = left;
   }
   public int getLeft(){
      return this.left;
   }
   
   public void setRight(int right){
      this.right = right;
   }
   public int getRight(){
      return this.right;
   }
}

        
