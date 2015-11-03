package Tokenizer;

import java.util.ArrayList;

public class Parser {

	/**
	 * @param args
	 */
	public Token curentToken=null;
	public int tokenNumber=0;
	public ArrayList<Token> tokenList=new ArrayList<Token>();
	public Boolean parseResult=null;
	
	public  boolean parser(ArrayList<Token> tokenListInp)
	{
		Token tokenLast=new Token("$","$");
		tokenListInp.add(tokenLast);
	
		Token tokenLast1=new Token("","");
		tokenListInp.add(tokenLast1);
		
		tokenList=tokenListInp;
		
		
		 parseResult=E();
		 return parseResult;
		
	}
	
	
	//Function for E
	public boolean E()
	{
		if(T()==true)
		{
			if(X()==true)
				return true;
			else
				return false;
		}
		else
		{
			return false;
		}
	}
	
	//Function for T
	public boolean T()
	{
		if(tokenList.get(tokenNumber).getTokenType().equals("LEFT_ROUND_BRACKET"))
		{
			tokenNumber++;
			if(E()==true)
			{
				if(tokenList.get(tokenNumber).getTokenType().equals("RIGHT_ROUND_BRACKET"))
				{
					tokenNumber++;
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
		else if(tokenList.get(tokenNumber).getTokenType().equals("INTEGER_LITERAL")) 
		{
			tokenNumber++;
			if(Y()==true)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	
	
	public boolean X()
	{
		if(tokenList.get(tokenNumber).getTokenType().equals("SYMBOL_PLUS")) 
		{
			tokenNumber++;
			if(E()==true)
			{
				return true;
			}
			else
			{
				return false;
			}
			
		}
		else if((tokenList.get(tokenNumber).getTokenType().equals("$"))||(tokenList.get(tokenNumber).getTokenType().equals("RIGHT_ROUND_BRACKET")))
		{
			if(!tokenList.get(tokenNumber).getTokenType().equals("$"))
			{
			//tokenNumber++;
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean Y()
	{
		if(tokenList.get(tokenNumber).getTokenType().equals("SYMBOL_MULTIPLY")) 
		{
			tokenNumber++;
			if(T()==true)
			{
				return true;
			}
			else
			{
				return false;
			}
			
		}
		else if((tokenList.get(tokenNumber).getTokenType().equals("$"))||(tokenList.get(tokenNumber).getTokenType().equals("RIGHT_ROUND_BRACKET"))
				||(tokenList.get(tokenNumber).getTokenType().equals("SYMBOL_PLUS")))
		{
			if(!tokenList.get(tokenNumber).getTokenType().equals("$"))
			{
			//tokenNumber++;
			}
			return true;
		}
		else
		{
			return false;
		}
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
