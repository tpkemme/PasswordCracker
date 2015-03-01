import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class PasswordCrack {
	private static ArrayList<String> words;
	private static ArrayList<User> users;
	
	public static void main(String args[]) throws IOException{
		// Initialize characters and 
		if(args.length != 2){
			System.err.println("Please use the correct number of arguments");
			System.exit(0);
		}
		words = new ArrayList<String>();
		users = new ArrayList<User>();
		
		File wordsF = new File(args[0]);
		File passwdF = new File(args[1]);
		
		FileReader wordFr = new FileReader(wordsF);
		BufferedReader wordBr = new BufferedReader(wordFr);
		String line;
		while((line = wordBr.readLine()) != null){
			String word = line;
			words.add(word);
			//System.out.println("User " + u.getAccountName() + " added at " + users.indexOf(u));
		}
		wordBr.close();
		wordFr.close();
		
		FileReader passFr = new FileReader(passwdF);
		BufferedReader passBr = new BufferedReader(passFr);
		String line2;
		while((line2 = passBr.readLine()) != null){
			String[] lineA = line2.split(":");
			User u = initUser(lineA);
			users.add(u);
			//System.out.println("User " + u.getAccountName() + " added at " + users.indexOf(u));
		}
		passBr.close();
		passFr.close();
		
		//First pass
		for(User u: users){
			seedWordList(u);
			
			// Try normal versions of the words
		    for(String word: words){
		    	String encryptedPass = jcrypt.crypt(u.getSalt(), word);
		    	if(encryptedPass.equals(u.getpsswdData())){
		    		System.out.println("THIS IS THE PASSWORD: " + encryptedPass + " for user " + u.getAccountName());
		    	    u.setPssCracked(true);
		    	    break;
		    	}
		    }
		}
		// Mangle once
	    ArrayList<String> mangledWords = new ArrayList<String>();
	    mangledWords = mangleOnce(mangledWords);
	    
	  //Second pass
  		for(User u: users){  			
  			// Try mangled versions of the words
  			if(u.isPssCracked() == false){
  				for(String word: mangledWords){
  		    		String encryptedPass = jcrypt.crypt(u.getSalt(), word);
  		    		if(encryptedPass.equals(u.getpsswdData())){
  		    			System.out.println("THIS IS THE PASSWORD: " + encryptedPass + " for user " + u.getAccountName());
  		    	    	u.setPssCracked(true);
  		    	    	break;
  		    		}
  		    	}
  			}
  		}
  		
  	  //Third Pass
  	    mangledWords = mangleTwice(mangledWords);
  	    for(User u: users){  			
			// Try mangled versions of the words
			if(u.isPssCracked() == false){
				for(String word: mangledWords){
		    		String encryptedPass = jcrypt.crypt(u.getSalt(), word);
		    		if(encryptedPass.equals(u.getpsswdData())){
		    			System.out.println("THIS IS THE PASSWORD: " + encryptedPass + " for user " + u.getAccountName());
		    	    	u.setPssCracked(true);
		    	    	break;
		    		}
		    	}
			}
  	  	}
  	    
  	    for(User u: users){
  	    	if(u.isPssCracked() == false){
  	    		System.out.println("This user's password is secure: " + u.getAccountName());
  	    	}
  	    }
//		System.out.println("The string is: string");
//		String s = "string";
//		System.out.println("The string prepended: " + prepend(s, 33));
//		System.out.println("The string appended: " + append(s, 126));
//		System.out.println("The string with first char deleted: " + delFirstChar(s));
//		System.out.println("The string with last char deleted: " + delLastChar(s));
//		System.out.println("The string reversed: " + reverse(s));
//		System.out.println("The string duplicated: " + duplicate(s));
//		System.out.println("The string reflected: " + reflect(s));
//		System.out.println("The string in uppercase: " + upper(s));
//		System.out.println("The string in lowercase: " + lower(s));
//		System.out.println("The string capitalized: " + capitalize(s));
//		System.out.println("The string ncapitalized: " + ncapitalize(s));
//		System.out.println("The string case toggled: " + toggleCase(s));
	}
	
	public static User initUser(String[] args){
		String accountName = args[0];
		String salt = args[1].substring(0, 2);
		String encPass = args[1].substring(2);
		String[] fullName = args[4].split("\\s+");
		User u = new User(accountName, salt, encPass, fullName);
		//System.out.println(u.toString());
		return u;
	}
	
	public static void seedWordList(User u){
		words.add(u.getAccountName());
		words.add((u.getFullName()[0]));
		words.add((u.getFullName()[1]));
		//System.out.println("Words added: " + u.getAccountName() + ", " + (u.getFullName()[0]) + ", " + (u.getFullName()[1]));
		
	}
	
	// Returns a random int in the range of min to max inclusive
	public static int randInt(int min, int max, Random rand) {
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	public static String prepend(String s, int charCode){
		return (char)charCode + s;
	}
	
	public static String append(String s, int charCode){
		return s + (char)charCode;
	}
	
	public static String delFirstChar(String s){
		return s.substring(1);
	}
	
	public static String delLastChar(String s){
		return s.substring(0, s.length()-1);
	}
	
	public static String reverse(String s){
		String st = "";
		for(int i = 0; i < s.length(); i++){
			st = s.charAt(i) + st;
		}
		return st;
	}
	
	public static String duplicate(String s){
		return s + s;
	}
	
	public static String reflect(String s){
		return s + reverse(s);
	}
	
	public static String upper(String s){
		return s.toUpperCase();
	}
	
	public static String lower(String s){
		return s.toLowerCase();
	}
	
	public static String capitalize(String s){
		String c = s.substring(0,1);
		return c.toUpperCase() + s.substring(1).toLowerCase();
	}
	
	public static String ncapitalize(String s){
		String n = s.substring(1);
		return s.substring(0, 1).toLowerCase() + n.toUpperCase();
	}
	
	public static String toggleCase(String s){
		char[] chars = s.toCharArray();
	    for (int i = 0; i < chars.length; i++)
	    {
	        char c = chars[i];
	        if (Character.isUpperCase(c))
	        {
	            chars[i] = Character.toLowerCase(c);
	        }
	        else if (Character.isLowerCase(c))
	        {
	            chars[i] = Character.toUpperCase(c);
	        }
	    }
	    return new String(chars);
	}
	
	public static ArrayList<String> mangleTwice(ArrayList<String> mangledWords){
		return mangleOnce(mangledWords);
	}
	
	public static ArrayList<String> mangleOnce(ArrayList<String> mangledWords){
		
		ArrayList<String> temp1 = new ArrayList<String>();
		for(Iterator<String> iter = words.iterator(); iter.hasNext(); ){
			String word = iter.next();
			//prepend
			for(int i = 33; i < 127; i++){
				temp1.add(prepend(word, i));
			}
		}
		
		ArrayList<String> temp2 = new ArrayList<String>();
		for(Iterator<String> iter = words.iterator(); iter.hasNext(); ){
			String word = iter.next();
			//append
			for(int i = 33; i < 127; i++){
				temp2.add(append(word, i));
			}
		}
		
		ArrayList<String> temp3 = new ArrayList<String>();
		for(Iterator<String> iter = words.iterator(); iter.hasNext(); ){
			String word = iter.next();
			//delete first char
			temp3.add(delFirstChar(word));
		}
		
		ArrayList<String> temp4 = new ArrayList<String>();
		for(Iterator<String> iter = words.iterator(); iter.hasNext(); ){
			String word = iter.next();
			//delete last char
			temp4.add(delLastChar(word));
		}
		
		ArrayList<String> temp5 = new ArrayList<String>();
		for(Iterator<String> iter = words.iterator(); iter.hasNext(); ){
			String word = iter.next();
			//reversed
			temp5.add(reverse(word));
		}
		
		ArrayList<String> temp6 = new ArrayList<String>();
		for(Iterator<String> iter = words.iterator(); iter.hasNext(); ){
			String word = iter.next();
			//duplicated
			temp6.add(duplicate(word));
		}
		
		ArrayList<String> temp7 = new ArrayList<String>();
		for(Iterator<String> iter = words.iterator(); iter.hasNext(); ){
			String word = iter.next();
			//reflected
			temp7.add(reflect(word));
		}
		
		ArrayList<String> temp8 = new ArrayList<String>();
		for(Iterator<String> iter = words.iterator(); iter.hasNext(); ){
			String word = iter.next();
			//uppercase
			temp8.add(upper(word));
		}
		
		ArrayList<String> temp9 = new ArrayList<String>();
		for(Iterator<String> iter = words.iterator(); iter.hasNext(); ){
			String word = iter.next();
			//lowercase
			temp9.add(lower(word));
		}
		
		ArrayList<String> temp10 = new ArrayList<String>();
		for(Iterator<String> iter = words.iterator(); iter.hasNext(); ){
			String word = iter.next();
			//capitalize
			temp10.add(capitalize(word));
		}
		
		ArrayList<String> temp11 = new ArrayList<String>();
		for(Iterator<String> iter = words.iterator(); iter.hasNext(); ){
			String word = iter.next();
			//ncapitalize
			temp11.add(ncapitalize(word));
		}
		
		ArrayList<String> temp12 = new ArrayList<String>();
		for(Iterator<String> iter = words.iterator(); iter.hasNext(); ){
			String word = iter.next();
			//toggle case
			temp12.add(toggleCase(word));
		}
		
		for(String word : temp1){
			mangledWords.add(word);
		}
		for(String word : temp2){
			mangledWords.add(word);
		}
		for(String word : temp3){
			mangledWords.add(word);
		}
		for(String word : temp4){
			mangledWords.add(word);
		}
		for(String word : temp5){
			mangledWords.add(word);
		}
		for(String word : temp6){
			mangledWords.add(word);
		}
		for(String word : temp7){
			mangledWords.add(word);
		}
		for(String word : temp8){
			mangledWords.add(word);
		}
		for(String word : temp9){
			mangledWords.add(word);
		}
		for(String word : temp10){
			mangledWords.add(word);
		}
		for(String word : temp11){
			mangledWords.add(word);
		}
		for(String word : temp12){
			mangledWords.add(word);
		}
		
		//System.out.println("Size of mangledWords: " + mangledWords.size());
		
		return mangledWords;
	}
}
