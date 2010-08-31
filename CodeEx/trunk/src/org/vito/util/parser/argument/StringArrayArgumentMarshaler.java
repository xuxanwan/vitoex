package org.vito.util.parser.argument;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.vito.util.parser.argument.ArgsException.ErrorCode.*;

public class StringArrayArgumentMarshaler implements ArgumentMarshaler {
	private String[] stringArrayValue = new String[0];

	public void set(Iterator<String> currentArgument) throws ArgsException {
		try {
			for (int i = 0; currentArgument.hasNext(); i++) {
				stringArrayValue[i] = currentArgument.next();
			}
		} catch (NoSuchElementException e) {
			throw new ArgsException(MISSING_STRING);
		}
	}

	public static String[] getValue(ArgumentMarshaler am) {
		if (am != null && am instanceof StringArrayArgumentMarshaler)
			return ((StringArrayArgumentMarshaler) am).stringArrayValue;
		else
			return new String[0];
	}
}
