package com.lordofthejars.nosqlunit.forge;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class WhenJavaTestFileIsCreated {

	private static final String EXPECTED_JAVA_FILE = "package com.lordofthejars.forge;\n" + 
			"\n" + 
			"import static com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb.InMemoryMongoRuleBuilder.newInMemoryMongoDbRule;\n" + 
			"import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;\n" + 
			"\n" + 
			"import static org.junit.Assert.assertThat;\n" + 
			"\n" + 
			"import org.junit.ClassRule;\n" + 
			"import org.junit.Rule;\n" + 
			"import org.junit.Test;\n" + 
			"\n" + 
			"import com.lordofthejars.nosqlunit.annotation.UsingDataSet;\n" + 
			"import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;\n" + 
			"import com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb;\n" + 
			"import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;\n" + 
			"\n" + 
			"\n" + 
			"@UsingDataSet(locations=\"mongo-initial-data.json\", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)\n" + 
			"public class MyTest {\n" + 
			"\n" + 
			"	@ClassRule\n" + 
			"	public static final InMemoryMongoDb IN_MEMORY_MONGO_DB = newInMemoryMongoDbRule().build();\n" + 
			"	\n" + 
			"	@Rule\n" + 
			"	public MongoDbRule embeddedMongoDbRule = newMongoDbRule().defaultEmbeddedMongoDb(\"test\");\n" + 
			"	\n" + 
			"	@Test\n" + 
			"	public void myMethod1 () {\n" + 
			"		\n" + 
			"		\n" + 
			"	}\n" + 
			"	\n" + 
			"	@Test\n" + 
			"	public void myMethod2 () {\n" + 
			"		\n" + 
			"		\n" + 
			"	}\n" + 
			"	\n" + 
			"		\n" + 
			"}\n";
	
	@Test
	public void should_be_created_from_velocity_template() {
		
		JavaTemplate javaTemplate = new VelocityJavaTemplate();
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("package", "com.lordofthejars.forge");
		parameters.put("classname", "MyTest");
		parameters.put("methods", Arrays.asList("myMethod1", "myMethod2"));
		parameters.put("database", "test");
		
		String generatedJavaClass = javaTemplate.create("Test-embedded-template-test.vtl", parameters);
		
		assertThat(generatedJavaClass, is(EXPECTED_JAVA_FILE));
		
	}
	
}
