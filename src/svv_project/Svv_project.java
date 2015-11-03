/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svv_project;

//import Tokenizer.Parser;
import Tokenizer.Token;
import Tokenizer.TokenData;
import Tokenizer.TokenType;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author sreeram
 */
public class Svv_project 
{
    public static void main(String[] args) throws FileNotFoundException, IOException 
    {
        //read input file
        /*FileReader in = new FileReader("input.txt");
        BufferedReader br = new BufferedReader(in);
        String line;
        while ((line=br.readLine()) != null) 
        {
        System.out.println(line);
        }
        in.close();
        */
        ArrayList<String> list=genarateTokens();
        ArrayList<String> variable_list=get_variables(list);
        System.out.println("variable list: "+variable_list);
        ArrayList<String> variable_values=get_values(list);
        System.out.println("variable values: "+variable_values);
        
        get_use(variable_list, list);
    }
    
    public static ArrayList<String> genarateTokens()
	{
		
		//accepting file path from the user
		/*System.out.println("Please enter the complete path of the file which has the code(make sure to use double slashes !!)  :-  ");
		Scanner user_input=new Scanner(System.in);
		String filePath=user_input.next();
		*/
		
		//A SAMPLE PATH is -> D:\\code.txt
		
		//Extracting data from the file in string format
		//File file = new File(filePath);
		File file = new File("input.txt");
		FileInputStream fis = null;
		String dataFromFile=null;
		try {
			fis = new FileInputStream(file);
			 byte[] buffer = new byte[(int)file.length()];
			 new DataInputStream(fis).readFully(buffer);
			 fis.close();
			 dataFromFile =new String(buffer, "UTF-8");
			 
		System.out.println("Code which is read from the file is --> \n"+dataFromFile+"\n");
		}
		catch(IOException e)
		{
			System.out.println("There is some error in retreiving data from the file !!");
			e.printStackTrace();
			System.exit(0);
			
		}
		
		
		ArrayList<Token> tokenList=new ArrayList<Token>();
		
		
		System.out.println("\n Following is the list of tokens \n");
		
		
		String code=dataFromFile;
		//Following Array list will contain all the regular expressions for the Tokens used the our Language
		ArrayList<TokenData> tokenDatas=new ArrayList<TokenData>();
		
		tokenDatas.add(new TokenData(Pattern.compile("^(if)"),TokenType.KEYWORD_IF));
		tokenDatas.add(new TokenData(Pattern.compile("^(then)"),TokenType.KEYWORD_THEN));
		tokenDatas.add(new TokenData(Pattern.compile("^(while)"),TokenType.KEYWORD_WHILE));
		tokenDatas.add(new TokenData(Pattern.compile("^(write)"),TokenType.KEYWORD_WRITE));
		tokenDatas.add(new TokenData(Pattern.compile("^(read)"),TokenType.KEYWORD_READ));
		tokenDatas.add(new TokenData(Pattern.compile("^(return)"),TokenType.KEYWORD_RETURN));
		tokenDatas.add(new TokenData(Pattern.compile("^(else)"),TokenType.KEYWORD_ELSE));
		tokenDatas.add(new TokenData(Pattern.compile("^(forward)"),TokenType.KEYWORD_FORWARD));
		
		
		tokenDatas.add(new TokenData(Pattern.compile("^(int)"),TokenType.INT_DATATYPE));
		tokenDatas.add(new TokenData(Pattern.compile("^(char)"),TokenType.CHAR_DATATYPE));
		tokenDatas.add(new TokenData(Pattern.compile("^(string)"),TokenType.STRING_DATATYPE));
		tokenDatas.add(new TokenData(Pattern.compile("^(bool)"),TokenType.BOOL_DATATYPE));
		
		
		
		tokenDatas.add(new TokenData(Pattern.compile("^([A-Z][a-zA-Z0-9]*)"),TokenType.VARIABLE_IDENTIFIER));
		tokenDatas.add(new TokenData(Pattern.compile("^([a-z][a-zA-Z0-9]*)"),TokenType.FUNCTION_IDENTIFIER));
		
		tokenDatas.add(new TokenData(Pattern.compile("^((-)?[0-9]+)"),TokenType.INTEGER_LITERAL));
		tokenDatas.add(new TokenData(Pattern.compile("^(\".*\")"),TokenType.STRING_LITERAL));
		tokenDatas.add(new TokenData(Pattern.compile("^('.')"),TokenType.CHAR_LITERAL));
		
		tokenDatas.add(new TokenData(Pattern.compile("^(;)"),TokenType.SEMICOLON));
		tokenDatas.add(new TokenData(Pattern.compile("^(,)"),TokenType.COMMA));
		tokenDatas.add(new TokenData(Pattern.compile("^(<)"),TokenType.LEFT_ANGULAR_BRACKET));
		tokenDatas.add(new TokenData(Pattern.compile("^(>)"),TokenType.RIGHT_ANGULAR_BRACKET));
		tokenDatas.add(new TokenData(Pattern.compile("^(\\{)"),TokenType.LEFT_CURLY_BRACKET));
		tokenDatas.add(new TokenData(Pattern.compile("^(\\})"),TokenType.RIGHT_CURLY_BRACKET));
		tokenDatas.add(new TokenData(Pattern.compile("^(\\()"),TokenType.LEFT_ROUND_BRACKET));
		tokenDatas.add(new TokenData(Pattern.compile("^(\\))"),TokenType.RIGHT_ROUND_BRACKET));
		tokenDatas.add(new TokenData(Pattern.compile("^(\\[)"),TokenType.LEFT_SQUARE_BRACKET));
		tokenDatas.add(new TokenData(Pattern.compile("^(\\])"),TokenType.RIGHT_SQUARE_BRACKET));
		tokenDatas.add(new TokenData(Pattern.compile("^(:=)"),TokenType.ASSIGNMENT_OPERATOR));
		tokenDatas.add(new TokenData(Pattern.compile("^(|)"),TokenType.PIPE_SYMBOL));
		tokenDatas.add(new TokenData(Pattern.compile("^(&)"),TokenType.AND_SYMBOL));
		tokenDatas.add(new TokenData(Pattern.compile("^(>)"),TokenType.SYMBOL_MORE_THAN));
		tokenDatas.add(new TokenData(Pattern.compile("^(<)"),TokenType.SYMBOL_LESS_THAN));
		tokenDatas.add(new TokenData(Pattern.compile("^(=)"),TokenType.SYMBOL_EQUAL_TO));
		tokenDatas.add(new TokenData(Pattern.compile("^(!=)"),TokenType.SYMBOL_NOT_EQUAL_TO));
		tokenDatas.add(new TokenData(Pattern.compile("^(\\+)"),TokenType.SYMBOL_PLUS));
		tokenDatas.add(new TokenData(Pattern.compile("^(\\-)"),TokenType.SYMBOL_MINUS));
		tokenDatas.add(new TokenData(Pattern.compile("^(\\*)"),TokenType.SYMBOL_MULTIPLY));
		tokenDatas.add(new TokenData(Pattern.compile("^(\\/)"),TokenType.SYMBOL_DIVISION));
		tokenDatas.add(new TokenData(Pattern.compile("^(%)"),TokenType.SYMBOL_PERCENTAGE));
		tokenDatas.add(new TokenData(Pattern.compile("^(^)"),TokenType.SYMBOL_POWER));
		tokenDatas.add(new TokenData(Pattern.compile("^(->)"),TokenType.ARROW_SYMBOL));
		
		
	
		//erroneousString will contain the part of the code which is erroneous and can not be parsed
		String erroneousString=null;
		ArrayList<String> alltokens=new ArrayList<String>();
		
		//Parse until no string remains to be parsed 
		while(code.isEmpty() ==false)
		{
			if(code.startsWith(" "))
			{
				code=code.trim();//removing blank spaces from code
			}
			
	    
		 //removing \n, \r, \t from  code
		 code = code.replaceAll("\\r|\\n|\\t", "");
			
		   //By the end of trying all the regular expression , the matcherMax object will hold the data of the 
		   //match of maximum length .
			Matcher matcherMax=null;
			int max_len=0;//this will hold the length of the match of maximum length
		    TokenData tokenDataMax=null;//This is token data for the maximum match 
			
		    //Now following loop will try all the regular expressions present in the tokenDatas list .
			for(TokenData data : tokenDatas)
			{
				Matcher matcher=data.getPattern().matcher(code);
				
				if(matcher.find())
				{
					String token=matcher.group().trim();
					int len=token.length();
					if(len>max_len)//if this match is greater than maximum match till now, then set this as the maximum match
					{
						max_len=len;
						matcherMax=matcher;
						tokenDataMax=data;
					}
				}
				
			}//end of iteration over tokenDatas list
			
			//condition when some match is found
			if(matcherMax!=null)
			{
				String token=matcherMax.group().trim();
				System.out.println("Token type- "+tokenDataMax.getType().name()+"  Token is-  "+ token);
				alltokens.add(token);
				//Removing the matched string from the code
				code=matcherMax.replaceFirst("");
	
				//adding this token in the list
				Token tempToken=new Token(tokenDataMax.getType().name(),token);
                tokenList.add(tempToken);				
				}
			else
			{
				//setting the erroneousString in case no match is found
				erroneousString=code;
				break;
			}
			
			
		}//end of while
		
		if(erroneousString!=null)
		{
			System.out.println("Code with error is -> "+ erroneousString);
			System.out.println("\n Parsing aborted !! Please fix this error.");
			Token tempToken=new Token("Error","Error");
			tokenList.add(tempToken);
		}
		
		//Parser parser=new Parser();
		//System.out.println("End result is "+(boolean)parser.parser(tokenList));
		//parser.parser(tokenList);
                System.out.println("TokenList: "+tokenList);
                System.out.println("Alltokens: "+alltokens);
                return alltokens;
              
	}//end of function genarateTokens
	
    public static ArrayList<String> get_variables(ArrayList alltokens)
    {
          
                ArrayList<String> datatype_list=new ArrayList<>(Arrays.asList("int","float","double","boolean","String"));
                System.out.println("data type list: "+datatype_list);
                ArrayList<String> variables=new ArrayList<String>();
                for(int i=0;i<alltokens.size();i++)
                {
                    if(datatype_list.contains(alltokens.get(i)))
                    {
                        System.out.println("data type found: "+alltokens.get(i));
                        System.out.println("variable : "+alltokens.get(i+1));
                        variables.add((String) alltokens.get(i+1));
                        if(alltokens.get(i+2).equals("="))
                        {
                            System.out.println("value of variable: "+alltokens.get(i+3));
                            //var_values.add((String) alltokens.get(i+3));
                        }
                    }
                }
        return variables;
    }
    
    public static ArrayList<String> get_values(ArrayList alltokens)
    {
                int temp;
                ArrayList<String> datatype_list=new ArrayList<>(Arrays.asList("int","float","double","boolean","String"));
                System.out.println("data type list: "+datatype_list);
                ArrayList<String> var_values=new ArrayList<String>();
                for(int i=0;i<alltokens.size();i++)
                {
                    if(datatype_list.contains(alltokens.get(i)))
                    {
                        if(alltokens.get(i+2).equals("="))
                        {
                            System.out.println("value of variable: "+alltokens.get(i+3));
                            var_values.add((String) alltokens.get(i+3));
                        }
                        else
                        {
                            var_values.add("null");
                        }
                    }
                }
                
        return var_values;
    }

    public static void get_use(ArrayList variable_list, ArrayList alltokens)
    {
        
        for(int i=0;i<alltokens.size();i++)
        {
            if(alltokens.get(i).equals("="))
            {
                System.out.println("in if");
            /*    int j=i;
                while(alltokens.get(j++)!=";")
                {
                    System.out.println("in while");
                    if(variable_list.contains(alltokens.get(j)))
                    {
                        System.out.println("def: "+alltokens.get(j));
                    }
                }
            */
                String s=";";
                if(alltokens.get(i).equals(s))
                {
                    System.out.println("contains ;");
                }
            }
        }
    }
}    

