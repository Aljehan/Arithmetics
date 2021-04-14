


import java.util.Scanner;
import java.lang.Character;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.Arrays;
public class Arithmetics2{

    public static String operandBuilder = "";
    public static boolean isPossible = false;

    public static void main(String[] args){

        String inputNumbers;
        String[] stringSplitter = null;
        int[] numbers = null;
        char operation;
        String targetOp;
        int i, target;
        int length = 0;
        boolean found;

        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            
            inputNumbers = scanner.nextLine();
            // Takes in user input and makes it useable
            targetOp = scanner.nextLine();
            operation = targetOp.charAt(targetOp.length() - 1);
            targetOp = targetOp.substring(0, targetOp.length() - 2);
            operation = Character.toUpperCase(operation);
            target = Integer.parseInt(targetOp);

            // Turns the input numbers into a string, and removes all occurances of spaces
            stringSplitter = null;
            stringSplitter = inputNumbers.split(" ");  
            length = stringSplitter.length;

            // Initializes a new int array with a size that can hold the inputted numbers
            numbers = null;
            numbers = new int[length];

            // Fill the int array with contents from the string array
            for(i = 0; i < length; i++){
                numbers[i] = Integer.parseInt(stringSplitter[i]);
            }           
            
            // If the user specified L operation, run that method. Otherwise, print error
            if(operation == 'L'){
                if ((lMax(numbers.clone(), target) && lMin(numbers.clone(), target))){
                    System.out.println(operation + " " + target + " impossible");
                }else{
                    found = lrOperation(numbers, target,numbers[0], 0,"");
                   if(found==false){
                     System.out.println(operation + " " + target + " impossible");
                    }  
                }
                
            }else if(operation == 'N'){ 
                if(nMax(numbers.clone(), target) && nMin(numbers.clone(), target)){
                    System.out.println(operation + " " + target + " impossible");
                }else{
                    found = nOperation(numbers, target,0,1,0,"");
                    if(found==false){
                        System.out.println(operation + " " + target + " impossible");
                    }
                }
            }else{
               System.err.println("ERROR: Please enter L or N for operation only");
            }
        }
        // Close the scanner, effectively ending the program
        scanner.close();

    }

    /* Recursively finds any possible solution for LHS solving (Chen algorthims)
       @param numbers - An int array which stores the numbers to be used
              target  - The int that the program is trying to find a calculation to hit
              res     - Recursively stores the current sum of previous recursions
              depth   - Keeps track of what depth level the recursion is currently at
              operate   - Keeps track of which operators to use
       @return boolean - Returns true if a possible calculation is found                  */
    public static boolean lrOperation(int[] numbers, int target, int res,int depth,String operate){        
        if(depth==numbers.length-1){
            return lrOperation(numbers, target, res, depth+1, operate+Integer.toString(numbers[depth]));
        }else if(depth==numbers.length){
            if(res==target){
                System.out.println("L "+Integer.toString(target)+" "+operate);
                return true;
            }else{
                return false;
            }
        }else{
            if(res>target){
                return false;
            }
            if(lrOperation(numbers, target, res+numbers[depth+1], depth+1, operate+Integer.toString(numbers[depth])+" + ")||
            lrOperation(numbers, target, res*numbers[depth+1], depth+1, operate+Integer.toString(numbers[depth])+" * ")){
                return true;
            }else{
                return false;
            }
        }
    }
        /* Find any Possible solution for the N algorithms (Chen algorthims)
       @param numbers - An int array which stores the numbers to be used
              target  - The int that the program is trying to find a calculation to hit
              Index     - Recursively inremented the index of the array
              operate   - Keeps track of which operators to use
       @return boolean - Returns true if a possible calculation is found                  */
    public static boolean nOperation(int[] numbers, int target, int res,int buffer,int depth, String operate){
        if(depth==numbers.length-1){
            return nOperation(numbers, target, res+(buffer*numbers[depth]),1, depth+1, operate+Integer.toString(numbers[depth]));
        }else if(depth==numbers.length){            
            if(res==target){
                System.out.println("N "+Integer.toString(target)+" "+operate);
                return true;
            }else{
                return false;
            }
        }else{
            if(res>target){
                return false;
            }
            if(nOperation(numbers, target, res+(buffer*numbers[depth]),1, depth+1, operate+Integer.toString(numbers[depth])+" + ")||
            nOperation(numbers, target, res, buffer*numbers[depth],depth+1, operate+Integer.toString(numbers[depth])+" * ")){
                return true;
            }else{
                return false;
            }
        }
    }


        /* Find the maximum possible value for the L to R 
       @param numbers - An int array which stores the numbers to be used
              target  - The int that the program is trying to find a calculation to hit
       @return boolean - Returns true if the target is smaller thn the max value.   
       */
   public static boolean lMax(int[] numbers, int target){
    int max = 1;
    boolean state = false;

    for(int i = 0 ; i < numbers.length ; i++ ){
        if(numbers[i] == 1 && i == 0){
            continue;
        }else if (numbers[i] == 1){
            max += numbers[i];
        }else{
            max *= numbers[i];
        }
    }
    if (target < max){
        state = true;
    }
    return state;
    }


       /* Find the minimum possible value for the L to R 
       @param numbers - An int array which stores the numbers to be used
              target  - The int that the program is trying to find a calculation to hit
       @return boolean - Returns true if the target is slarger than the min value.   
       */
    public static boolean lMin(int[] numbers, int target){
        int min = 0;
        boolean state = false;
    
        for(int i = 0 ; i < numbers.length ; i++ ){
            if(numbers[i] == 1 && i == 0){
                min = 1;
            }else if (numbers[i] == 1){
                min *= numbers[i];
            }else{
                min += numbers[i];
            }
        }
        if (target > min){
            state = true;
        }
        return state;
        }



               /* Find the minimum possible value for the N 
       @param numbers - An int array which stores the numbers to be used
              target  - The int that the program is trying to find a calculation to hit
       @return boolean - Returns true if the target is slarger than the min value.   
       */
    public static boolean nMin(int[] numbers, int target){
        int min = 0;
        boolean state = false;
        for(int i = 0; i < numbers.length; i++){
            if(numbers[i] == 1){
                continue;
            }
            min += numbers[i];
        }

        if (min > target){
            state = true;
        }

        return state;
    }


                /* Find the maximum possible value for the L to R 
       @param numbers - An int array which stores the numbers to be used
              target  - The int that the program is trying to find a calculation to hit
       @return boolean - Returns true if the target is smaller thn the max value.   
       */
    public static boolean nMax(int[] numbers, int target){
    int max = 1;
    boolean state = false;
    int theOnes = 0;
    int lPos = 0;
    int largest = 0;
    for(int i = 0; i < numbers.length; i++){
        if(numbers[i] > largest){
            largest = numbers[i];
            lPos = i;
        }
        if (numbers[i] == 1){
            theOnes += 1;
        }
    }
    numbers[lPos] += theOnes;
    for(int i = 0; i < numbers.length; i++){
        max *= numbers[i];
    }

    if(target < max){
        state = true;
    }

    return state;
}

}

