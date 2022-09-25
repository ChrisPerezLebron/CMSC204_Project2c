/**
 * A static utility class that provides methods for converting from 
 * infix to postfix and from postfix to infix. This class also 
 * provides a method for evaluating postifx expressions 
 * THIS CLASS ONLY WORKS WITH SINGLE DIGIT OPERANDS 
 * @author Christopher Perez Lebron
 *
 */
public class Notation {
	
	/**
	 * converts a infix expression into postfix expression 
	 * using a stack and a queue.
	 * @param infix a string representing a infix algebraic expression
	 * @return a string representing infix's postfix form
	 * @throws InvalidNotationFormatException if the infix expression is 
	 * invalid in anyway. 
	 */
	public static String convertInfixToPostfix(String infix) {
		MyStack<Character> operatorStack = new MyStack<>(infix.length());
		MyQueue<Character> postfixQueue = new MyQueue<>(infix.length());
		char nextCharacter;
		char topOperator;
		int numOperands = 0;
		int numOperators = 0;
		int index = 0;
		while(index < infix.length()) {
			nextCharacter = infix.charAt(index);
			
			/*
			 * it is easier and less code to check if the current 
			 * char is a digit using Character class's isDigit 
			 * method
			 */
			
			if(Character.isDigit(nextCharacter)) {
				postfixQueue.enqueue(nextCharacter); 
				numOperands++;
			}
			else {
				/*
				 * i put the switch case in the else clause so that 
				 * the switch is NOT evaluated if the character is 
				 * a digit.
				 * 
				 * of course, I could of left it outside of the 
				 * else statement and let the default case. However, 
				 * that would cost a few more comparisons than 
				 * preventing the switch case entirely if the 
				 * character is a digit. 
				 */
				switch(nextCharacter) {
					case '^':
						operatorStack.push(nextCharacter);
						numOperators++;
						break;
						
					case '+': case '-': case '*': case '/': case '%': 
						
						while(!operatorStack.isEmpty() 
								&& ( precedenceOf(nextCharacter) <= precedenceOf(operatorStack.top()) )  ) {
							
							postfixQueue.enqueue(operatorStack.top());
							operatorStack.pop(); 
							
						}
						operatorStack.push(nextCharacter);
						numOperators++;
						break;
					
					case '(':
						operatorStack.push(nextCharacter); 
						break;
					
					case ')':
						
						try {
							/*
							 * this throws StackUnderflowException if stack is empty. 
							 * Stack will only be empty if all operators were popped 
							 * and no open parenthesis was found. Thus, we can assume 
							 * that if a StackUnderflowException is thrown then the 
							 * infix expression is invalid
							 */
							topOperator = operatorStack.pop(); 
							while(topOperator != '(') {
								postfixQueue.enqueue(topOperator);
								topOperator = operatorStack.pop();
							}
						
						
							break;
						} catch (StackUnderflowException e) {
							/*
							 * as explained earlier, if the stack throws StackUnderflowException 
							 * then the expression is invalid
							 */
							throw new InvalidNotationFormatException("ERROR: Unbalanced Parentheses"); 
						}
						
					default: 
						break;
						
				}
			}
			index++;
		}
		
		while(!operatorStack.isEmpty()) {
			topOperator = operatorStack.pop();
			/*
			 * this will catch a situation in which there is more open parentheses 
			 * than close parentheses such as "1+2(*3" 
			 */
			if(topOperator == '(')
				throw new InvalidNotationFormatException("ERROR: Unbalanced Parentheses");
			postfixQueue.enqueue(topOperator); 
		}
		
		
		
		if(numOperators > numOperands -1 )
			throw new InvalidNotationFormatException("ERROR: Too many operators");
		
		if(numOperators < numOperands - 1)
			throw new InvalidNotationFormatException("ERROR: not enough operators");
		
		return postfixQueue.toString();
	}
	
	
	/**
	 * this method converts a char type representing an arithmetic 
	 * operator to an integer based precedence level. 
	 * @param operator a char representing a arithmetic operator
	 * @return an integer indicating its precedence level
	 * @throws IllegalArgumentException if a character 
	 * representing anything other than an arithmetic 
	 * operator or open parenthesis is passed
	 */
	private static int precedenceOf(char operator) {
		int precedence;
		
		switch(operator) {
			case '^': 
				precedence = 3; 
				break;
			case '*': case '/':
				precedence = 2;
				break;
			case '+': case '-': case '%':
				precedence = 1;
				break;
			case '(': 
				precedence = 0;
				break;
			default: 
				/* 
				 * this will only run if the char passed 
				 * into this method is not a operator
				 */
				throw new IllegalArgumentException();
		}
		
		return precedence; 
	}
	
	/**
	 * Evaluates a postfix expression and returns it's value as a double
	 * @param postfixExpr a string representing a postfix expression
	 * @return a double representing the postfix expression's value
	 * @throws InvalidNotationFormatException if the postfix expression is 
	 * invalid in anyway. 
	 */
	public static double evaluatePostfixExpression(String postfixExpr) {
		MyStack<Double> valueStack = new MyStack<>(postfixExpr.length());
		char nextCharacter; 
		Double operandTwo;
		Double operandOne;
		Double result; 
		Double charValue;
		int index = 0;
		while(index < postfixExpr.length()) {
			nextCharacter = postfixExpr.charAt(index); 
			
			if(Character.isDigit(nextCharacter)) {
				//convert nextChar into a double and store in a Double wrapper obj
				charValue = (double) Character.digit(nextCharacter, 10); 
				valueStack.push(charValue);
			}
			else {
				
				/*
				 * reasoning for wrapping switch in an 
				 * else clause is the same as reasoning 
				 * discussed in InfixToPostfix method
				 * 
				 * for reference this is the reasoning I 
				 * provided earlier: 
				 * 
					 * i put the switch case in the else clause so that 
					 * the switch is NOT evaluated if the character is 
					 * a digit.
					 * 
					 * of course, I could of left it outside of the 
					 * else statement and let the default case. However, 
					 * that would cost a few more comparisons than 
					 * preventing the switch case entirely if the 
					 * character is a digit. 
				 * 
				 * 
				 */
				switch(nextCharacter) {
					case '+': case '-': case '*': case '/': case '^': case '%':
						
						/*
						 * if StackUnderflowException is thrown then there aren't 
						 * enough operands. Therefore, the expression is invalid
						 */
						try {
							operandTwo = valueStack.pop();
							operandOne = valueStack.pop();
						} catch(StackUnderflowException e) {
							throw new InvalidNotationFormatException("ERROR: too few operands");
						}
						result = calculate(operandOne, nextCharacter, operandTwo); 
						
						valueStack.push(result); 
						break;
						
					case '(': case ')':
						/*
						 * no parenthesis should be present in the postfix expression
						 */
						throw new InvalidNotationFormatException("ERROR: postfix should not have any parentheses");
					default:
						break;
				}
			}
			
			index++; 
			
		}
		
		result = valueStack.pop(); 
		
		//if you have extra operands then the expression was invalid 
		if(!valueStack.isEmpty()) {
			throw new InvalidNotationFormatException("ERROR: Too many operands");
		}
		
		return result;
		
	}
	
	
	/**
	 * takes in a double representing the left operand of an operation, 
	 * a char representing an arithmetic operator, a double representing 
	 * the right operator, and returns the value of the expression 
	 * (operand1 operator operand2) 
	 * (2*9) 
	 * @param operand1 a double representing the left operand in the operation
	 * @param operator a char representing an arithmetic operator
	 * @param operand2 a double representing the right operand in the operation
	 * @return a double indicating the value of the expression
	 * @throws IllegalArgumentException if a character representing anything 
	 * other than an arithmetic operator is passed in for the operator parameter 
	 */
	private static double calculate(double operand1, char operator, double operand2) {
		double result; 
		switch (operator) {
			case '+': 
				result = operand1 + operand2; 
				break;
			case '-': 
				result = operand1 - operand2; 
				break;
			case '*': 
				result = operand1 * operand2; 
				break;
			case '/': 
				result = operand1 / operand2; 
				break;
			case '^': 
				result = Math.pow(operand1, operand2); 
				break;
			case '%':
				result = operand1 % operand2;
				break;
			default:
				/*
				 * this only executes if the operator parameter 
				 * is NOT an actual operator
				 */
				throw new IllegalArgumentException();
		}
		
		return result;
	}
	
	
	
	/**
	 * converts a postfix expression into an infix expression
	 * @param postfix a string representing a postfix expression
	 * @return a string representing the postfix expression's 
	 * infix representation
	 * @throws InvalidNotationFormatException if the postfix expression is 
	 * invalid in anyway. 
	 */
	public static String convertPostfixToInfix(String postfix) {
		MyStack<String> operandStack = new MyStack<>(postfix.length()); 
		String operand2;
		String operand1; 
		String combinedOperand;
		char currentCharacter; 
		
		int index = 0;
		while(index < postfix.length()) {
			
			
			currentCharacter = postfix.charAt(index);
			if(Character.isDigit(currentCharacter))
				operandStack.push("" + currentCharacter);
			else {
				switch(currentCharacter) {
					case '*': case '/': case '+': 
					case '-': case '^': case '%':
						
						/*
						 * if StackUnderflowException is thrown then the 
						 * expression was invalid
						 */
						try {
							operand2 = operandStack.pop();
							operand1 = operandStack.pop();
						} catch(StackUnderflowException e) {
							throw new InvalidNotationFormatException("ERROR: Input is invalid");
						}
						combinedOperand = "(" + operand1 + 
								currentCharacter + operand2 + ")";
						operandStack.push(combinedOperand);
						break;
					case '(': case ')':
						/*
						 * postfix should not have any parenthesis
						 */
						throw new InvalidNotationFormatException("ERROR: postfix should not have any parentheses");
				}
			}
			index++;
		}
		
		String result = operandStack.pop(); 
		
		/*
		 * if the stack is not empty then then we were left with 2 or 
		 * more operands which signifies there was not enough 
		 * operators. Thus, notation is invalid
		 */
		if(!operandStack.isEmpty())
			throw new InvalidNotationFormatException("ERROR: Input is invalid");
		
		return result;
	}

}
