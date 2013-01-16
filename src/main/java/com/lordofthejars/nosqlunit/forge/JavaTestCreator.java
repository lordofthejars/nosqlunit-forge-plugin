package com.lordofthejars.nosqlunit.forge;

import java.util.Map;

public class JavaTestCreator implements TestCreator {

	private JavaTemplate javaTemplate = new VelocityJavaTemplate();
	
	@Override
	public String createTestClass(DatabaseEnum databaseEnum, String mode,
			Map<Object, Object> parameters) {
		return javaTemplate.create(templateName(databaseEnum, mode), parameters);
	}

	private String templateName(DatabaseEnum databaseEnum, String mode) {
		return databaseEnum.getName() + "-" + mode + "-template-test.vtl";
	}
	
}
