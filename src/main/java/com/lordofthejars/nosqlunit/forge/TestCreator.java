package com.lordofthejars.nosqlunit.forge;

import java.util.Map;

public interface TestCreator {

	String createTestClass(DatabaseEnum databaseEnum,
			String mode, Map<Object, Object> parameters);
	
}
