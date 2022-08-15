package lispvalidator;

public class LispValidator {

	/**
	 * @param lispCodeStr is the input lisp code (supports multi-line strings) 
	 * @return            true if the code string has proper count and nesting of parentheses
	 */
	public static boolean isValidCodeStr(String lispCodeStr) {
		
		final char QUOTE = '"';
		final char ESCAPE = '\\';
		final char COMMENT = ';';
		final char OPEN_PAREN = '(';
		final char CLOSE_PAREN = ')';
		
		// opening and closing parenthesis count
        int openCount = 0;
        int closeCount = 0;

		// split the string by newline, process one line at a time
    	for (String line : lispCodeStr.split("\n")) {

    		// exit the loop early if the closing parenthesis count becomes greater than the opening count
    		if (closeCount > openCount) {
    			break;
    		}
					
			boolean isQuoted = false;  // true if currently inside a quoted string
	        boolean isEscaped = false; // true if currently at an escaped character, previous character was \
	        
    		// process the line one character at a time, 
	        // exit the loop early if the closing parenthesis count becomes greater than the opening count
	        for (int i = 0; i < line.length() && openCount >= closeCount; i++) {
	        	char c = line.charAt(i);

	        	// if this character is escaped, ignore it and reset the isEscaped flag 
	        	if (isEscaped) {
	        		isEscaped = false;

	        	} else {
	        	
	        		// if this character is quote,
	        		// toggle the quoted flag, this could be the start or end of a quote
	        		if (c == QUOTE) {
	        			isQuoted = !isQuoted;

	        		} else {

		                // if inside a quoted string,
	        			// don't look for parenthesis, but handle escape character
		                if (isQuoted) {
		
		                    if (c == ESCAPE) {
		                    	isEscaped = true;
		                    }

		                // else, not inside of quoted string
		                } else {
		
		                    // if this character starts a comment,
		                	// no need to process the rest of line, exit loop
		                    if (c == COMMENT) {
		                        break;

		                    // all other possibilities are handled, 
		                    // this is a normal character, count any parentheses
		                    } else {
		                    	
		                        if (c == OPEN_PAREN) {
		                        	openCount++;
		                        } else if (c == CLOSE_PAREN) {
		                            closeCount++;
		                        }
		                    }
	                    }
	        		}
	        	}
	        }
    	}   
	
        // To be valid, the lisp code source code must have:
        //   1. equal number of opening / closing parenthesis
        //   2. at least one open/close pair 
        return openCount == closeCount && openCount + closeCount > 0;
	}
	
	public static void main(String[] args) {
		String code = """
				(
				"this is a string with \\" escaped quote and parenthesis ("
				)
				""";
		System.out.println(isValidCodeStr(code));
	}

}
