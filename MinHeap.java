//State the package that this file belongs to
package ca.ucalgary.cpsc331;

//Import packages and libraries that will be used by this class.
import java.lang.Math;

public class MinHeap implements PriorityQueue{

    private final int CAPACITY = 31;//define the max capacity of the heap array
    public int numOfValidElements;//keeps count of currently present valid elements in the hear array    
    public int[] minHeapArray;//holds the elements of the heap

    //This default constructor initializes an empty heap.
    public MinHeap(){
        this.minHeapArray = new int[CAPACITY];//holds int values of the heap
        this.numOfValidElements = 0;//there are zero valid elements when the heap is just created.
    }

    //This method returns the index of a parent for node i.
    private int parentIndex(int i){
        int parent = Math.floorDiv(i-1, 2);//math library is used explicitly to indicate that floor is used
        return parent;
    }

    //This method returns the index of the left child for node i.
    private int leftIndex(int i){
        int left = i*2+1;
        return left; 
    }

    //This method returns the index of the right child for node i.
    private int rightIndex(int i){
        int right = (2*i+1)+1;
        return right;
    }
       
    //This method maintains the min heap property for element i
    private void minHeapify(int i){

        int leftIndex = leftIndex(i);
        int rightIndex = rightIndex(i);
        int smallestIndex;//this variable holds the index of the larger index element

        //set smallestInd to index that is smallest when comparing values of i and its children
        if (leftIndex<=this.numOfValidElements-1 && this.minHeapArray[leftIndex]<minHeapArray[i])
            smallestIndex = leftIndex;
        else 
            smallestIndex = i;
        if (rightIndex <= this.numOfValidElements-1 && minHeapArray[rightIndex]<minHeapArray[smallestIndex])
            smallestIndex = rightIndex;
        
        //if element at i is not the smallest when compared to its children, then excahnge with its smallest child value
        if (smallestIndex!=i){
            int temp = minHeapArray[i];
            minHeapArray[i] = minHeapArray[smallestIndex];
            minHeapArray[smallestIndex] = temp;
            //now ensure that the child node with the new exchanged value also holds the min heap property
            minHeapify(smallestIndex);
        }
    }

    //This method returns true if the minHeapArray is empty, and false if the minHeapArray is not empty.
    public boolean empty(){
        if (this.numOfValidElements == 0)
            return true;//returns true and exits the method
        return false;
    }

    //This method returns true if the minHeapArray is full, and false if the minHeapArray is not full.
    public boolean full() {
        if (this.numOfValidElements == this.CAPACITY)
            return true;
        return false;
    }

    //This method inserts the element key into the minHeapArray
    public void insert(int key) {

            //check if we have room in our min heap array for another element to be added
            if (this.numOfValidElements+1 > this.CAPACITY){
                throw new RuntimeException("Heap overflow encountered. Heap is full and no more elements can be inserted.");
            }
            //increment the number of valid elements
            this.numOfValidElements++;
            //set i to the index of the last valid element
            int i = this.numOfValidElements-1;

            //insert the key value into the last valid element location
            this.minHeapArray[i] = key;

            //if the element we are inserting is smaller than its parent then exchange them and perform the same swap on the exchanged parent iteratively.
            while (i>0 && this.minHeapArray[parentIndex(i)]>this.minHeapArray[i]){
                int temp = this.minHeapArray[i];
                this.minHeapArray[i] = this.minHeapArray[parentIndex(i)];
                this.minHeapArray[parentIndex(i)] = temp;
                i = parentIndex(i);
            }     
    }

    //This method removes and returns the element of minHeapArray with the smallest value and ensures that min heap property is maintained on the remaining elements.
    public int extractMin() {
        
        //check if any elements are present in the heap array
        if (this.numOfValidElements < 1){
            throw new RuntimeException("Heap underflow encountered. Heap is empty and there is no min value to extract.");
        }
        
        //the value at index 0 is the minimum in a maintained min heap array, extract that value.
        int min = min();
        
        //maintain the min-heap property on the remaining elements in the heap
        this.minHeapArray[0] = this.minHeapArray[this.numOfValidElements-1];//place the last element of the heap array into the first element

        //remove the last element from the array by decrementing the valid elements counter
        this.numOfValidElements--;

        //Maintain the min heap property on the new first element
        minHeapify(0);
        //return the smallest element that was removed
        return min;
    }
    
    //This method returns the element of minHeapArray with the smallest value, which is at index 0.
    public int min() {
        
        //check if any elements are present in the heap array
        if (this.numOfValidElements < 1){
            throw new RuntimeException("Heap underflow encountered. Heap is empty and there is no min value.");
        }

        return this.minHeapArray[0];
    }

    //This function prints out the values that are contained inside the heap array and formats the output to contain the elements at each height on a separate row.
    public String toString(){
        
        if (this.numOfValidElements == 0)//only print the size if the heap array is empty
            return "size = 0\n";
        else{//otherwise print each element in the required format.
            String s = "size = ";//start building the return string
            s = s + this.numOfValidElements+"\n";
            s = s + this.minHeapArray[0]+"\n";

            //height of a heap is calculated by h=floor(lgn), where n is the number of elements in the heap array
            int h = (int)(Math.log(this.numOfValidElements)/Math.log(2));
            //declare these variables outside the for loop so they can be used to calculate current row values based on previous row values.
            int firstElemIndex = 0;
            int lastElemIndex = 0;
            for (int i = 1; i<=h; i++){
                //update the index of first and last elements at height i of the min-heap
                firstElemIndex = lastElemIndex+1;
                lastElemIndex = firstElemIndex*2;
                //print elements that are at h=i
                for(int j = firstElemIndex; j<=lastElemIndex; j++){
                    //print element and a new line charecter after it if it is the last element in a level.
                    if (j<this.numOfValidElements && (j==lastElemIndex || j==this.numOfValidElements-1))
                        s = s + this.minHeapArray[j]+"\n";    
                    //print element and a space after it if it is not the last element in a level.
                    else if (j<this.numOfValidElements && j<lastElemIndex)
                         s = s + this.minHeapArray[j]+" ";
                }
            }
            return s;
        }  
    }

}