package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Decryption {

	

	    private String keyword        = new String();
	    private String key            = new String();
	    private char   matrix[][] = new char[5][5];

	 

	    public void setKey(String k)
	    {
	        String K_adjust = new String();
	        boolean flag = false;
	        K_adjust = K_adjust + k.charAt(0);
	        for (int i = 1; i < k.length(); i++)
	        {
	            for (int j = 0; j < K_adjust.length(); j++)
	            {
	                if (k.charAt(i) == K_adjust.charAt(j))
	                {
	                    flag = true;
	                }
	            }

	            if (flag == false)
	                K_adjust = K_adjust + k.charAt(i);
	            flag = false;
	        }
	        keyword = K_adjust;
	    }
	 

	    public void KeyGen()
	    {
	        boolean flag = true;
	        char current;
	        key = keyword;
	        for (int i = 0; i < 26; i++)
	        {
	            current = (char) (i + 97);
	            if (current == 'j')
	                continue;

	            for (int j = 0; j < keyword.length(); j++)
	            {
	                if (current == keyword.charAt(j))
	                {
	                    flag = false;
	                    break;
	                }
	            }

	            if (flag)
	                key = key + current;
	            flag = true;
	        }
	        matrix();
	    }

	 

	    private void matrix()
	    {
	        int counter = 0;
	        for (int i = 0; i < 5; i++)
	        {
	            for (int j = 0; j < 5; j++)
	            {
	                matrix[i][j] = key.charAt(counter);
	                counter++;
	            }
	        }
	    }

	 

	    private String format(String old_text)
	    {
	        int i = 0;
	        int len = 0;
	        String text = new String();
	        len = old_text.length();
	        for (int tmp = 0; tmp < len; tmp++)
	        {
	            if (old_text.charAt(tmp) == 'j')
	            {
	                text = text + 'i';
	            }
	            else
	                text = text + old_text.charAt(tmp);
	        }
	        len = text.length();
	        for (i = 0; i < len; i = i + 2)
	        {
	            if (text.charAt(i + 1) == text.charAt(i))
	            {
	                text = text.substring(0, i + 1) + 'x' + text.substring(i + 1);
	            }
	        }
	        return text;
	    }
	 
	    private String[] Divid2Pairs(String new_string)
	    {
	        String Original = format(new_string);
	        int size = Original.length();
	        if (size % 2 != 0)
	        {
	            size++;
	            Original = Original + 'x';
	        }
	        String x[] = new String[size / 2];
	        int counter = 0;
	        for (int i = 0; i < size / 2; i++)
	        {
	            x[i] = Original.substring(counter, counter + 2);
	            counter = counter + 2;
	        }
	        return x;
	    }

	    public int[] GetDimensions(char letter)
	    {
	        int[] key = new int[2];
	        if (letter == 'j')
	            letter = 'i';
	        for (int i = 0; i < 5; i++)
	        {
	            for (int j = 0; j < 5; j++)
	            {
	                if (matrix[i][j] == letter)
	                {
	                    key[0] = i;
	                    key[1] = j;
	                    break;
	                }
	            }
	        }
	        return key;
	    }

	 


	    public StringBuilder decryptMessage(String Code)

	    {
	        StringBuilder Original = new StringBuilder("");
	        String src_arr[] = Divid2Pairs(Code);
	        char one;
	        char two;
	        int part1[] = new int[2];
	        int part2[] = new int[2];
	        for (int i = 0; i < src_arr.length; i++)
	        {
	            one = src_arr[i].charAt(0);
	            two = src_arr[i].charAt(1);
	            part1 = GetDimensions(one);
	            part2 = GetDimensions(two);
	            if (part1[0] == part2[0])
	            {
	                if (part1[1] > 0)
	                    part1[1]--;
	                else
	                    part1[1] = 4;
	                if (part2[1] > 0)
	                    part2[1]--;
	                else
	                    part2[1] = 4;
	            }

	            else if (part1[1] == part2[1])
	            {
	                if (part1[0] > 0)
	                    part1[0]--;
	                else
	                    part1[0] = 4;
	                if (part2[0] > 0)
	                    part2[0]--;
	                else
	                    part2[0] = 4;
	            }
	            else
	            {
	                int temp = part1[1];
	                part1[1] = part2[1];
	                part2[1] = temp;
	            }

	            Original.append(matrix[part1[0]][part1[1]]);
	            Original.append(matrix[part2[0]][part2[1]]);
	        }

	        return Original;

	    }

private static boolean verify(String ciphertext) {
			
			boolean isPlayfair = true;
			if(ciphertext.length() % 2 == 0) {

			for(int i = 0; i <ciphertext.length(); i=i+2 ) {
        	    char c1 = ciphertext.charAt(i);
        	    char c2 = ciphertext.charAt(i+1);
        	    if(c1 == c2 || (c1 == 'i' && c2 == 'j' || c1 == 'j' && c2 == 'i')) {
        	    	isPlayfair = false;
        	    }   
			}
			for(int i = 0; i <ciphertext.length(); i++ ) {
        	    char c1 = ciphertext.charAt(i);
        	    if(!Character.isAlphabetic(c1)) {
        	    	isPlayfair = false;
        	    }
			
			}
			
		} else {
			isPlayfair = false;
		}
			return isPlayfair;
		}


	    public static void main(String[] args)

	    {

	       Decryption decryption = new Decryption();
	       
	       String correctKey = null;
	       String correctMessage = null;
	        Scanner scanner = null;
	        Scanner scannerDictionary = null;
	        Scanner scannerDictionaryCypher = null;

			try {
				scanner = new Scanner(new File("data"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

	        try {
				scannerDictionary = new Scanner(new File("dictionary"));
				scannerDictionaryCypher = new Scanner(new File("words"));

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}


	        int keylength = Integer.parseInt(scanner.next());
	        String ciphertext = scanner.next();
	        
	        if (verify(ciphertext)) {
	        while(scannerDictionary.hasNext()) {
	        	String keyTest = scannerDictionary.next();
	        	
	        		if (keyTest.length() == keylength) {
		        		System.out.print("key: " + keyTest);
	        decryption.setKey(keyTest);
	        decryption.KeyGen();
	       
	        
	        	StringBuilder dec =  decryption.decryptMessage(ciphertext);
	        	System.out.println(" with plain text: " + dec);
	        	for (int i = 1 ; i < dec.length()-1 ; i++) {
	        	    char c = dec.charAt(i);
	        	    char prefix = dec.charAt(i-1);
	        	    char postfix = dec.charAt(i+1);
	        	    if(c == 'x' && prefix == postfix) {
	        	    	dec.deleteCharAt(i);
	        	    }
	        	}
	        	int index = dec.length();
	        	boolean foundWords = false;

       		for(int i = 0; i <= index; i++) {

    			try {
   				scannerDictionaryCypher = new Scanner(new File("words"));

   			} catch (FileNotFoundException e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   			}
    			String sub = dec.substring(i, index);
    			while(scannerDictionaryCypher.hasNext()) {
    				String word = scannerDictionaryCypher.next();
    				if(sub.equals(word)) {
        				System.out.println("word found: " + word);
        				index = i;
        				i = -1;
        				System.out.println("index found: " + index);
        			scannerDictionaryCypher.close();
        		break;
    			}
       		}
    			if(index == 0) {
		  		correctMessage = dec.toString();
     		 	correctKey = keyTest;
     		 break;
    			}
	      
	        }
       		
	        	}
	        	}
	        }
	        else {
	            System.out.println("Message doesn't meet conditions of Playfair!");
	            
	        }
	        		        		        
	    
	        if(correctKey != null) {
	        	System.out.println("THE CORRECT KEY: " + correctKey + " with message : " +correctMessage);
	        }
	        
	        scannerDictionary.close();
	        scanner.close();
}
	    

		
}
