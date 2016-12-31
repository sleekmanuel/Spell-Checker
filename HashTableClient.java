/*
* Data Structures
*
* Brief description of the file: 
* This project is a spell checker program. It read a dictionary name word and saves it
* in a Hash Table. Reads a file to check for errors in it. If an error exists,
* a possible lists of correction is displayed but if the error doesn't exist, it prints
* out an "okay" text.
*/


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class HashTableClient {
	
/*
 * Creates a LinkedList, ArrayList,Array, tHashMap and HashTable of  already implemented methods.
 */
	static SeparateChainingHashTable<String> list = new SeparateChainingHashTable<>();
	static LinkedList<String> ll = new LinkedList<String>();
	static HashMap<String, LinkedList<String>> Correct = new HashMap<String , LinkedList<String>>();
	static Scanner console = new Scanner(System.in);
	static 	ArrayList<String> ar = new ArrayList<String>();
	static char alph[] = {'a','b', 'c', 'd', 'e', 'f','g','h','i', 'j', 'k' , 'l', 'm', 'n', 'n', 'o', 'p', 'q', 'r', 's','t','u','v','w','x','y','z','\''};
	
	/*
	 * 				-------Main Method -----------
	 */
    public static void main(String[] args) throws IOException{ 
    			ReadFile(list); // calls the ReadFile method
    			OpenFile(list); // calls the openFile method
    			Operations(ar); // calls the Operations method
	  }
    
    
    
    
/*
* Loops through each incorrect word in arrayList, applies each possible correction methods and stores each 
* possible correction in a linkedList and then a hashMap with the error word as key and
*  prints out the error word and its corresponding possible corrections.	
*/
@SuppressWarnings("unchecked")
public static void Operations(ArrayList<String> ar){
    	for (int i = 0;i < ar.size() ;i++) {  
     	   String x = ar.get(i); 
     	   if(Correct.containsKey(x)){ // print below if key already exists in HashMap
     		   System.out.println();
     		   System.out.println("The incorrect words below already exist".toUpperCase());;
     	   }else{
     		   
    		   Addchar(x);
    		   RemoveChar(x);
    		   ExchChar(x);
    		   ReplaceChar(x);
    		   LinkedList<String> shList = new LinkedList<String>();
    		   shList = (LinkedList<String>) ll.clone();
    		   Correct.put(x,shList);
    		   ll.clear();
     	   }
    		    System.out.println("\"" + x +"\"" + " IS NOT SPELLED CORRECTLY".toLowerCase());
    			System.out.println("Possible Correction: "+  Correct.get(x));
    			
    			
        }
    }
    

 /*
  *  Method reads the en-US.txt file and sets it up into the SeparateChaninig HashTable
  */
public static void ReadFile(SeparateChainingHashTable<String> HTable) {
String line = null;
try {
        FileReader fileReader = new FileReader("en-US.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
    
      for (int i = 0; i <152000; i++){
          line = bufferedReader.readLine();
          HTable.insert(line.toLowerCase());
        }     
          bufferedReader.close();         
      }
      catch(FileNotFoundException ex) {
          System.out.println("File Not Found ");                
      }
      catch(IOException ex) {
          System.out.println("Error!"); 
      }
}



/*
 * This Method asks the user for a file to spell check. uses the Separate HashTable to compare find 
 * missing word. if word is missing, word in user file is incorrect.
 * The incorrect words are stored in an arrayList
 * If all words in the user file are found in the SeparateChaniningHashTable, text file has correct words
 */
public static ArrayList<String> OpenFile (SeparateChainingHashTable<String> h) {
	
	 	String line = null;
	 	int wrongCount = 0;
	 
	 	boolean success = false;
	 while(!success)
		 try {
			 System.out.println("Please enter name of the file to be corrected (with .txt): ");
	         String fileName = console.nextLine();
	         FileReader inputfile = new FileReader(fileName);
	         BufferedReader bufferedReader = new BufferedReader(inputfile);
        
     /*
      * loops through the text file, reads and eliminates punctuation.
      */
	         while((line = bufferedReader.readLine()) != null) { 
        	String[] split = line.split(" ");
        	for( int i = 0; i < split.length; i++){
        		split[i] = split[i].replaceAll("[.:;,]", "");
        			if(h.contains(split[i].toLowerCase()+"\\")){
        				;
        			}else{
        				wrongCount++;
        				ar.add(split[i]); 		
    	           }  
            } 
        	
	     }   
        // checks if all words are spelled correctly
        if(wrongCount == 0){
        	System.out.println("All words are spelled correctly");
        }
        	bufferedReader.close();    
        	success=true;
      }
      catch(FileNotFoundException ex) {
    	  	System.out.println( "404: File not Found! ");                
      }
      catch(IOException ex) {
    	  	System.out.println("Error!"); 
        }
	 return ar;
}

/*
 * Replace Method: replaces each character in the incorrect string with letters in the alphabet.
 * Returns the the words that match the words in the SeparateChaining HashTable.
 */
private static void ReplaceChar(String word) {
	for(int i = 0; i < word.length(); i++){
		for( int j = 0 ; j < alph.length; j++){
			char[] temp = word.toCharArray();
			temp[i] = alph[j];
			String newWord = new String(temp); 
			if(list.contains(newWord.toLowerCase()+"\\")){
				ll.add(newWord.toLowerCase());
					
				}
		   }
		}

}
/*
 * Exchange Method: Swaps adjacent character of the incorrect word and compares 
 * to SeparateChaining HashTable. Returns Matched words
 */
private static void ExchChar(String word) {

		char[] c = word.toCharArray();
		char temp = c[0];
	for(int i = 0; i < word.length()-1;i++){	
		c[i] = c[i+1];
		c[i+1] = temp;
		String swappedString = new String(c);
		temp = c[i];
		c[i]= c[i+1];
		
	if(list.contains(swappedString.toLowerCase()+"\\")){
		ll.add(swappedString.toLowerCase());
		}
	}

}

/*
 * Add Method: Adds alphabet characters to the incorrect word and returns words that match the 
 * SeparateChaining HashTable
 */
public static void Addchar(String word ){
		for(int i = 0; i< word.length()+1; i++){
			for( int j = 0 ; j < alph.length; j++){
				String newWord = word.substring(0, i) + alph[j] + word.substring(i) ;
					if(list.contains(newWord.toLowerCase()+"\\")){
						ll.add(newWord.toLowerCase());
						}						
			 	}
			
		}
} 
/*
 * Remove Method: Removes characters from the incorrect word, returns "True" comparisons with 
 * SeparateChaining HashTable
 */
public static  void RemoveChar(String word){
	for(int i = 0; i < word.length();i++){		 
			String line = word.substring(0,i) + word.substring(i+1);
		if(list.contains(line.toLowerCase()+"\\")){
			ll.add(line.toLowerCase());
			}
		}

	}


}
