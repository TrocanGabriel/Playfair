package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Decryption {

	

	    private String KeyWord        = new String();

	    private String Key            = new String();

	    private char   matrix_arr[][] = new char[5][5];

	 

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

	        KeyWord = K_adjust;

	    }

	 

	    public void KeyGen()

	    {

	        boolean flag = true;

	        char current;

	        Key = KeyWord;

	        for (int i = 0; i < 26; i++)

	        {

	            current = (char) (i + 97);

	            if (current == 'j')

	                continue;

	            for (int j = 0; j < KeyWord.length(); j++)

	            {

	                if (current == KeyWord.charAt(j))

	                {

	                    flag = false;

	                    break;

	                }

	            }

	            if (flag)

	                Key = Key + current;

	            flag = true;

	        }

	        System.out.println(Key);

	        matrix();

	    }

	 

	    private void matrix()

	    {

	        int counter = 0;

	        for (int i = 0; i < 5; i++)

	        {

	            for (int j = 0; j < 5; j++)

	            {

	                matrix_arr[i][j] = Key.charAt(counter);

	                System.out.print(matrix_arr[i][j] + " ");

	                counter++;

	            }

	            System.out.println();

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

	 

	    public int[] GetDiminsions(char letter)

	    {

	        int[] key = new int[2];

	        if (letter == 'j')

	            letter = 'i';

	        for (int i = 0; i < 5; i++)

	        {

	            for (int j = 0; j < 5; j++)

	            {

	                if (matrix_arr[i][j] == letter)

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

	            part1 = GetDiminsions(one);

	            part2 = GetDiminsions(two);

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

	            Original.append(matrix_arr[part1[0]][part1[1]]);

	            Original.append(matrix_arr[part2[0]][part2[1]]);

	        }

	        return Original;

	    }

	 

	    public static void main(String[] args)

	    {

	       Decryption descryption = new Decryption();

	        Scanner scanner = null;
	        Scanner scannerDictionary = null;
	        Scanner scannerDictionaryCypher = null;

			try {
				scanner = new Scanner(new File("data"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        try {
				scannerDictionary = new Scanner(new File("dictionary"));
				scannerDictionaryCypher = new Scanner(new File("dictionary"));

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


	        int keylength = Integer.parseInt(scanner.next());
	        String ciphertext = scanner.next();
	        
//	        while(scannerDictionary.hasNext()) {
//	        	String key = scannerDictionary.next();
	        descryption.setKey("charles");
	        descryption.KeyGen();
	        System.out.println("Enter word to encrypt: (Make sure length of message is even)");

	       

	        	if (verify(ciphertext)) {
	        	StringBuilder dec =  descryption.decryptMessage(ciphertext);
	        	for (int i = 1 ; i < dec.length()-1 ; i++) {
	        	    char c = dec.charAt(i);
	        	    char prefix = dec.charAt(i-1);
	        	    char postfix = dec.charAt(i+1);
	        	    if(c == 'x' && prefix == postfix) {
	        	    	dec.deleteCharAt(i);
	        	    }
	        	}
	        	int index = 0;
	        	boolean foundWords = false;

       		for(int i =dec.length()-1; i >= index; i--) {

    			try {
   				scannerDictionaryCypher = new Scanner(new File("dictionary"));

   			} catch (FileNotFoundException e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   			}
    			String sub = dec.substring(index, i);
    			while(scannerDictionaryCypher.hasNext()) {
    				String word = scannerDictionaryCypher.next();
    				if(sub.equals(word)) {
        				System.out.println("word found: " + word);
        				index = i;
        				i = dec.length()-1;
        				System.out.println("index found: " + index);
        			scannerDictionaryCypher.close();
        		break;
    			}
       		}
    			if(index == dec.length()-1) {
				System.out.println("exit while");
				foundWords = true;
    			}

	        	

	        }
       		
	        	}

	        else {
	            System.out.println("Message doesn't meet conditions of Playfair!");
	        }
	        	scanner.close();
	        	
//	    }

}

		private static boolean verify(String ciphertext) {
			
			boolean isPlayfair = true;
			if(ciphertext.length() % 2 == 0) {

			for(int i = 0; i <ciphertext.length(); i=i+2 ) {
        	    char c1 = ciphertext.charAt(i);
        	    char c2 = ciphertext.charAt(i+1);
        	    if(c1 == c2) {
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

}
