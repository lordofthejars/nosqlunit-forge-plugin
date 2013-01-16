package com.lordofthejars.nosqlunit.forge;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class WhenJavaTestFromEngineIsCreated {

	private static final String CASSANDRA_EMBEDDED_TEST = "package com.test;\n" + 
			"\n" + 
			"import static com.lordofthejars.nosqlunit.cassandra.CassandraRule.CassandraRuleBuilder.newCassandraRule;\n" + 
			"import static com.lordofthejars.nosqlunit.cassandra.EmbeddedCassandra.EmbeddedCassandraRuleBuilder.newEmbeddedCassandraRule;\n" + 
			"\n" + 
			"import org.junit.ClassRule;\n" + 
			"import org.junit.Rule;\n" + 
			"import org.junit.Test;\n" + 
			"\n" + 
			"import com.lordofthejars.nosqlunit.annotation.UsingDataSet;\n" + 
			"import com.lordofthejars.nosqlunit.cassandra.CassandraRule;\n" + 
			"import com.lordofthejars.nosqlunit.cassandra.EmbeddedCassandra;\n" + 
			"import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;\n" + 
			"\n" + 
			"@UsingDataSet(locations=\"cassandra-initial-data.json\", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)\n" + 
			"public class MyTest {\n" + 
			"\n" + 
			"	@ClassRule\n" + 
			"	public static EmbeddedCassandra EMBEDDED_CASSANDRA = newEmbeddedCassandraRule().build();\n" + 
			"	\n" + 
			"	@Rule\n" + 
			"	public CassandraRule cassandraRule = newCassandraRule().defaultEmbeddedCassandra(\"test\");\n" + 
			"	\n" + 
			"	@Test\n" + 
			"	public void myMethod () {\n" + 
			"		\n" + 
			"	}\n" + 
			"		\n" + 
			"}";
	
	private static final String CASSANDRA_MANAGED_TEST = "package com.test;\n" + 
			"\n" + 
			"import static com.lordofthejars.nosqlunit.cassandra.CassandraRule.CassandraRuleBuilder.newCassandraRule;\n" + 
			"import static com.lordofthejars.nosqlunit.cassandra.ManagedCassandra.ManagedCassandraRuleBuilder.newManagedCassandraRule;\n" + 
			"\n" + 
			"import org.junit.ClassRule;\n" + 
			"import org.junit.Rule;\n" + 
			"import org.junit.Test;\n" + 
			"\n" + 
			"import com.lordofthejars.nosqlunit.annotation.UsingDataSet;\n" + 
			"import com.lordofthejars.nosqlunit.cassandra.CassandraRule;\n" + 
			"import com.lordofthejars.nosqlunit.cassandra.ManagedCassandra;\n" + 
			"import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;\n" + 
			"\n" + 
			"@UsingDataSet(locations=\"cassandra-initial-data.json\", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)\n" + 
			"public class MyTest {\n" + 
			"\n" + 
			"	@ClassRule\n" + 
			"	public static ManagedCassandra MANAGED_CASSANDRA = newManagedCassandraRule().cassandraPath(\"/opt/server\").build();\n" + 
			"	\n" + 
			"	@Rule\n" + 
			"	public CassandraRule cassandraRule = newCassandraRule().defaultManagedCassandra(\"test\");\n" + 
			"	\n" + 
			"	@Test\n" + 
			"	public void myMethod () {\n" + 
			"		\n" + 
			"	}\n" + 
			"		\n" + 
			"}";
	
	private static final String CASSANDRA_REMOTE_TEST = "package com.test;\n" + 
			"\n" + 
			"import static com.lordofthejars.nosqlunit.cassandra.RemoteCassandraConfigurationBuilder.newRemoteCassandraConfiguration;\n" + 
			"import static com.lordofthejars.nosqlunit.cassandra.CassandraRule.CassandraRuleBuilder.newCassandraRule;\n" + 
			"\n" + 
			"import org.junit.Rule;\n" + 
			"import org.junit.Test;\n" + 
			"\n" + 
			"import com.lordofthejars.nosqlunit.annotation.UsingDataSet;\n" + 
			"import com.lordofthejars.nosqlunit.cassandra.CassandraRule;\n" + 
			"import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;\n" + 
			"\n" + 
			"@UsingDataSet(locations=\"cassandra-initial-data.json\", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)\n" + 
			"public class MyTest {\n" + 
			"\n" + 
			"	@Rule\n" + 
			"	public CassandraRule cassandraRule = newCassandraRule().configure(newRemoteCassandraConfiguration().clusterName(\"test\")\n" + 
			"													.host(\"localhost\")\n" + 
			"													.port(10)\n" + 
			"													.build()\n" + 
			"													)\n" + 
			"												.build();\n" + 
			"	\n" + 
			"	@Test\n" + 
			"	public void myMethod () {\n" + 
			"		\n" + 
			"	}\n" + 
			"		\n" + 
			"}";
	
	private static final String COUCHDB_REMOTE_TEST = "package com.test;\n" + 
			"\n" + 
			"import static com.lordofthejars.nosqlunit.couchdb.RemoteCouchDbConfigurationBuilder.newRemoteCouchDbConfiguration;\n" + 
			"import static com.lordofthejars.nosqlunit.couchdb.CouchDbRule.CouchDbRuleBuilder.newCouchDbRule;\n" + 
			"\n" + 
			"import org.junit.Rule;\n" + 
			"import org.junit.Test;\n" + 
			"\n" + 
			"import com.lordofthejars.nosqlunit.annotation.UsingDataSet;\n" + 
			"import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;\n" + 
			"import com.lordofthejars.nosqlunit.couchdb.CouchDbRule;\n" + 
			"\n" + 
			"@UsingDataSet(locations=\"couchdb-initial-data.json\", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)\n" + 
			"public class MyTest {\n" + 
			"	\n" + 
			"	@Rule\n" + 
			"	public CouchDbRule couchDbRule = newCouchDbRule().configure(newRemoteCouchDbConfiguration()\n" + 
			"								.url(\"http://localhost:10\")\n" + 
			"								.databaseName(\"test\")\n" + 
			"								.build()\n" + 
			"								)\n" + 
			"							.build();\n" + 
			"	\n" + 
			"	@Test\n" + 
			"	public void myMethod () {\n" + 
			"		\n" + 
			"	}\n" + 
			"		\n" + 
			"}";
	
	private static final String COUCHDB_MANAGED_TEST = "package com.test;\n" + 
			"\n" + 
			"import static com.lordofthejars.nosqlunit.couchdb.ManagedCouchDb.ManagedCouchDbRuleBuilder.newManagedCouchDbRule;\n" + 
			"import static com.lordofthejars.nosqlunit.couchdb.CouchDbRule.CouchDbRuleBuilder.newCouchDbRule;\n" + 
			"\n" + 
			"import org.junit.ClassRule;\n" + 
			"import org.junit.Rule;\n" + 
			"import org.junit.Test;\n" + 
			"\n" + 
			"import com.lordofthejars.nosqlunit.annotation.UsingDataSet;\n" + 
			"import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;\n" + 
			"import com.lordofthejars.nosqlunit.couchdb.CouchDbRule;\n" + 
			"import com.lordofthejars.nosqlunit.couchdb.ManagedCouchDb;\n" + 
			"\n" + 
			"@UsingDataSet(locations=\"couchdb-initial-data.json\", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)\n" + 
			"public class MyTest {\n" + 
			"\n" + 
			"	@ClassRule\n" + 
			"	public static ManagedCouchDb MANAGED_COUCH_DB = newManagedCouchDbRule().couchDbPath(\"/opt/server\").build(); \n" + 
			"	\n" + 
			"	@Rule\n" + 
			"	public CouchDbRule couchDbRule = newCouchDbRule().defaultManagedMongoDb(\"test\");\n" + 
			"	\n" + 
			"	\n" + 
			"	@Test\n" + 
			"	public void myMethod () {\n" + 
			"		\n" + 
			"	}\n" + 
			"		\n" + 
			"}";
	
	private static final String HBASE_EMBEDDED_TEST = "package com.test;\n" + 
			"\n" + 
			"import static com.lordofthejars.nosqlunit.hbase.EmbeddedHBase.EmbeddedHBaseRuleBuilder.newEmbeddedHBaseRule;\n" + 
			"import static com.lordofthejars.nosqlunit.hbase.HBaseRule.HBaseRuleBuilder.newHBaseRule;\n" + 
			"\n" + 
			"import org.junit.ClassRule;\n" + 
			"import org.junit.Rule;\n" + 
			"import org.junit.Test;\n" + 
			"\n" + 
			"import com.lordofthejars.nosqlunit.annotation.UsingDataSet;\n" + 
			"import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;\n" + 
			"import com.lordofthejars.nosqlunit.hbase.EmbeddedHBase;\n" + 
			"import com.lordofthejars.nosqlunit.hbase.HBaseRule;\n" + 
			"\n" + 
			"@UsingDataSet(locations=\"hbase-initial-data.json\", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)\n" + 
			"public class MyTest {\n" + 
			"\n" + 
			"	@ClassRule\n" + 
			"	public static EmbeddedHBase EMBEDDED_HBASE = newEmbeddedHBaseRule().build();\n" + 
			"	\n" + 
			"	@Rule\n" + 
			"	public HBaseRule hBaseRule = newHBaseRule().defaultEmbeddedHBase();\n" + 
			"	\n" + 
			"	@Test\n" + 
			"	public void myMethod () {\n" + 
			"		\n" + 
			"	}\n" + 
			"		\n" + 
			"}";
	
	private static final String HBASE_MANAGED_TEST = "package com.test;\n" + 
			"\n" + 
			"import static com.lordofthejars.nosqlunit.hbase.ManagedHBase.HBaseRuleBuilder.newManagedHBaseServerRule;\n" + 
			"import static com.lordofthejars.nosqlunit.hbase.HBaseRule.HBaseRuleBuilder.newHBaseRule;\n" + 
			"\n" + 
			"import org.junit.ClassRule;\n" + 
			"import org.junit.Rule;\n" + 
			"import org.junit.Test;\n" + 
			"\n" + 
			"import com.lordofthejars.nosqlunit.annotation.UsingDataSet;\n" + 
			"import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;\n" + 
			"import com.lordofthejars.nosqlunit.hbase.ManagedHBase;\n" + 
			"import com.lordofthejars.nosqlunit.hbase.HBaseRule;\n" + 
			"\n" + 
			"@UsingDataSet(locations=\"hbase-initial-data.json\", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)\n" + 
			"public class MyTest {\n" + 
			"\n" + 
			"	@ClassRule\n" + 
			"	public static ManagedHBase managedHBase = newManagedHBaseServerRule().hBasePath(\"/opt/server\").build();\n" + 
			"	\n" + 
			"	@Rule\n" + 
			"	public HBaseRule hBaseRule = newHBaseRule().defaultManagedHBase();\n" + 
			"	\n" + 
			"	@Test\n" + 
			"	public void myMethod () {\n" + 
			"		\n" + 
			"	}\n" + 
			"		\n" + 
			"}";
	
	private static final String HBASE_REMOTE_TEST = "package com.test;\n" + 
			"\n" + 
			"import static com.lordofthejars.nosqlunit.hbase.RemoteHBaseConfigurationBuilder.newRemoteHBaseConfiguration;\n" + 
			"import static com.lordofthejars.nosqlunit.hbase.HBaseRule.HBaseRuleBuilder.newHBaseRule;\n" + 
			"\n" + 
			"import org.junit.Rule;\n" + 
			"import org.junit.Test;\n" + 
			"\n" + 
			"import com.lordofthejars.nosqlunit.annotation.UsingDataSet;\n" + 
			"import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;\n" + 
			"import com.lordofthejars.nosqlunit.hbase.HBaseRule;\n" + 
			"\n" + 
			"@UsingDataSet(locations=\"hbase-initial-data.json\", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)\n" + 
			"public class MyTest {\n" + 
			"	\n" + 
			"	@Rule\n" + 
			"	public HBaseRule hBaseRule = newHBaseRule().configure(newRemoteHBaseConfiguration()\n" + 
			"							.setProperty(\"hbase.zookeeper.quorum\", \"localhost\")\n" + 
			"							.setProperty(\"hbase.zookeeper.property.clientPort\", \"10\")\n" + 
			"							.build()\n" + 
			"							)\n" + 
			"						.build();\n" + 
			"	\n" + 
			"	@Test\n" + 
			"	public void myMethod () {\n" + 
			"		\n" + 
			"	}\n" + 
			"		\n" + 
			"}";
	
	private static final String INFINISPAN_EMBEDDED_TEST = "package com.test;\n" + 
			"\n" + 
			"import static com.lordofthejars.nosqlunit.infinispan.InfinispanRule.InfinispanRuleBuilder.newInfinispanRule;\n" + 
			"import static com.lordofthejars.nosqlunit.infinispan.EmbeddedInfinispan.EmbeddedInfinispanRuleBuilder.newEmbeddedInfinispanRule;\n" + 
			"\n" + 
			"\n" + 
			"import org.junit.ClassRule;\n" + 
			"import org.junit.Rule;\n" + 
			"import org.junit.Test;\n" + 
			"\n" + 
			"import com.lordofthejars.nosqlunit.annotation.UsingDataSet;\n" + 
			"import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;\n" + 
			"import com.lordofthejars.nosqlunit.infinispan.EmbeddedInfinispan;\n" + 
			"import com.lordofthejars.nosqlunit.infinispan.InfinispanRule;\n" + 
			"\n" + 
			"@UsingDataSet(locations=\"infinispan-initial-data.json\", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)\n" + 
			"public class MyTest {\n" + 
			"\n" + 
			"	@ClassRule\n" + 
			"	public static final EmbeddedInfinispan EMBEDDED_INFINISPAN = newEmbeddedInfinispanRule().build();\n" + 
			"	\n" + 
			"	@Rule\n" + 
			"	public final InfinispanRule infinispanRule = newInfinispanRule().defaultEmbeddedInfinispan();\n" + 
			"	\n" + 
			"	\n" + 
			"	@Test\n" + 
			"	public void myMethod () {\n" + 
			"		\n" + 
			"	}\n" + 
			"		\n" + 
			"}";
	
	private static final String INFINISPAN_MANAGED_TEST = "package com.test;\n" + 
			"\n" + 
			"import static com.lordofthejars.nosqlunit.infinispan.InfinispanRule.InfinispanRuleBuilder.newInfinispanRule;\n" + 
			"import static com.lordofthejars.nosqlunit.infinispan.ManagedInfinispan.ManagedInfinispanRuleBuilder.newManagedInfinispanRule;\n" + 
			"\n" + 
			"import org.junit.ClassRule;\n" + 
			"import org.junit.Rule;\n" + 
			"import org.junit.Test;\n" + 
			"\n" + 
			"import com.lordofthejars.nosqlunit.annotation.UsingDataSet;\n" + 
			"import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;\n" + 
			"import com.lordofthejars.nosqlunit.infinispan.InfinispanRule;\n" + 
			"import com.lordofthejars.nosqlunit.infinispan.ManagedInfinispan;\n" + 
			"\n" + 
			"@UsingDataSet(locations=\"infinispan-initial-data.json\", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)\n" + 
			"public class MyTest {\n" + 
			"\n" + 
			"	@ClassRule\n" + 
			"	public static final ManagedInfinispan MANAGED_INFINISPAN = newManagedInfinispanRule().infinispanPath(\"/opt/server\").build();\n" + 
			"	\n" + 
			"	@Rule\n" + 
			"	public final InfinispanRule infinispanRule = newInfinispanRule().defaultManagedInfinispan();\n" + 
			"	\n" + 
			"	@Test\n" + 
			"	public myMethod () {\n" + 
			"\n" + 
			"	}\n" + 
			"		\n" + 
			"}";
	
	private static final String INFINISPAN_REMOTE_TEST = "package com.test;\n" + 
			"\n" + 
			"import static com.lordofthejars.nosqlunit.infinispan.RemoteInfinispanConfigurationBuilder.newRemoteInfinispanConfiguration;\n" + 
			"import static com.lordofthejars.nosqlunit.infinispan.InfinispanRule.InfinispanRuleBuilder.newInfinispanRule;\n" + 
			"\n" + 
			"import org.junit.Rule;\n" + 
			"import org.junit.Test;\n" + 
			"\n" + 
			"import com.lordofthejars.nosqlunit.annotation.UsingDataSet;\n" + 
			"import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;\n" + 
			"import com.lordofthejars.nosqlunit.infinispan.InfinispanRule;\n" + 
			"\n" + 
			"@UsingDataSet(locations=\"infinispan-initial-data.json\", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)\n" + 
			"public class MyTest {\n" + 
			"\n" + 
			"	@Rule\n" + 
			"	public final InfinispanRule infinispanRule = newInfinispanRule().configure(newRemoteInfinispanConfiguration()\n" + 
			"									.host(\"localhost\")\n" + 
			"									.port(10)\n" + 
			"									.build()\n" + 
			"									)\n" + 
			"								.build();\n" + 
			"	\n" + 
			"	@Test\n" + 
			"	public myMethod () {\n" + 
			"\n" + 
			"	}\n" + 
			"		\n" + 
			"}";
	
	private static final String MONGODB_EMBEDDED_TEST = "package com.test;\n" + 
			"\n" + 
			"import static com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb.InMemoryMongoRuleBuilder.newInMemoryMongoDbRule;\n" + 
			"import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;\n" + 
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
			"\n" + 
			"	@ClassRule\n" + 
			"	public static final InMemoryMongoDb IN_MEMORY_MONGO_DB = newInMemoryMongoDbRule().build();\n" + 
			"	\n" + 
			"	@Rule\n" + 
			"	public MongoDbRule embeddedMongoDbRule = newMongoDbRule().defaultEmbeddedMongoDb(\"test\");\n" + 
			"	\n" + 
			"	@Test\n" + 
			"	public void myMethod () {\n" + 
			"		\n" + 
			"	}\n" + 
			"		\n" + 
			"}";
	
	private static final String MONGODB_MANAGED_TEST = "package com.test;\n" + 
			"\n" + 
			"import static com.lordofthejars.nosqlunit.mongodb.ManagedMongoDb.MongoServerRuleBuilder.newManagedMongoDbRule;\n" + 
			"import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;\n" + 
			"\n" + 
			"import org.junit.ClassRule;\n" + 
			"import org.junit.Rule;\n" + 
			"import org.junit.Test;\n" + 
			"\n" + 
			"import com.lordofthejars.nosqlunit.annotation.UsingDataSet;\n" + 
			"import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;\n" + 
			"import com.lordofthejars.nosqlunit.mongodb.ManagedMongoDb;\n" + 
			"import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;\n" + 
			"\n" + 
			"@UsingDataSet(locations=\"mongo-initial-data.json\", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)\n" + 
			"public class MyTest {\n" + 
			"\n" + 
			"	@ClassRule\n" + 
			"	public static ManagedMongoDb MANAGED_MONGO_DB = newManagedMongoDbRule().mongodPath(\"/opt/server\").build();\n" + 
			"	\n" + 
			"	@Rule\n" + 
			"	public MongoDbRule managedMongoDbRule =  newMongoDbRule().defaultManagedMongoDb(\"test\");\n" + 
			"	\n" + 
			"	@Test\n" + 
			"	public void myMethod () {\n" + 
			"		\n" + 
			"	}\n" + 
			"		\n" + 
			"}";
	
	private static final String MONGODB_REMOTE_TEST = "package com.test;\n" + 
			"\n" + 
			"import static com.lordofthejars.nosqlunit.mongodb.MongoDbConfigurationBuilder.mongoDb;\n" + 
			"import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;\n" + 
			"\n" + 
			"import org.junit.Rule;\n" + 
			"import org.junit.Test;\n" + 
			"\n" + 
			"import com.lordofthejars.nosqlunit.annotation.UsingDataSet;\n" + 
			"import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;\n" + 
			"import com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb;\n" + 
			"import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;\n" + 
			"\n" + 
			"@UsingDataSet(locations=\"mongo-initial-data.json\", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)\n" + 
			"public class MyTest {\n" + 
			"\n" + 
			"	@Rule\n" + 
			"	public MongoDbRule remoteMongoDbRule = newMongoDbRule().configure(mongoDb().host(\"localhost\")\n" + 
			"										   .port(10)\n" + 
			"										   .databaseName(\"test\")\n" + 
			"										   .build()\n" + 
			"										   )\n" + 
			"									   .build();\n" + 
			"					\n" + 
			"	@Test\n" + 
			"	public void myMethod() {\n" + 
			"\n" + 
			"	}\n" + 
			"		\n" + 
			"}";
	
	private static final String NEO4J_EMBEDDED_TEST = "package com.test;\n" + 
			"\n" + 
			"import static com.lordofthejars.nosqlunit.neo4j.InMemoryNeo4j.InMemoryNeo4jRuleBuilder.newInMemoryNeo4j;\n" + 
			"import static com.lordofthejars.nosqlunit.neo4j.Neo4jRule.Neo4jRuleBuilder.newNeo4jRule;\n" + 
			"\n" + 
			"import org.junit.ClassRule;\n" + 
			"import org.junit.Rule;\n" + 
			"import org.junit.Test;\n" + 
			"\n" + 
			"import com.lordofthejars.nosqlunit.annotation.UsingDataSet;\n" + 
			"import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;\n" + 
			"import com.lordofthejars.nosqlunit.neo4j.InMemoryNeo4j;\n" + 
			"import com.lordofthejars.nosqlunit.neo4j.Neo4jRule;\n" + 
			"\n" + 
			"@UsingDataSet(locations=\"neo4j-initial-data.xml\", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)\n" + 
			"public class MyTest {\n" + 
			"\n" + 
			"	@ClassRule\n" + 
			"	public static InMemoryNeo4j inMemoryNeo4j = newInMemoryNeo4j().build();\n" + 
			"	\n" + 
			"	@Rule\n" + 
			"	public Neo4jRule neo4jRule = newNeo4jRule().defaultEmbeddedNeo4j();\n" + 
			"	\n" + 
			"	@Test\n" + 
			"	public void myMethod () {\n" + 
			"		\n" + 
			"	}\n" + 
			"		\n" + 
			"}";
	
	private static final String NEO4J_MANAGED_TEST = "package $package;\n" + 
			"\n" + 
			"import static com.lordofthejars.nosqlunit.neo4j.ManagedWrappingNeoServer.ManagedWrappingNeoServerRuleBuilder.newWrappingNeoServerNeo4jRule;\n" + 
			"import static com.lordofthejars.nosqlunit.neo4j.Neo4jRule.Neo4jRuleBuilder.newNeo4jRule;\n" + 
			"\n" + 
			"import org.junit.ClassRule;\n" + 
			"import org.junit.Rule;\n" + 
			"import org.junit.Test;\n" + 
			"\n" + 
			"import com.lordofthejars.nosqlunit.annotation.UsingDataSet;\n" + 
			"import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;\n" + 
			"import com.lordofthejars.nosqlunit.neo4j.ManagedWrappingNeoServer;\n" + 
			"import com.lordofthejars.nosqlunit.neo4j.Neo4jRule;\n" + 
			"\n" + 
			"@UsingDataSet(locations=\"neo4j-initial-data.xml\", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)\n" + 
			"public class $classname {\n" + 
			"\n" + 
			"	@ClassRule\n" + 
			"	public static ManagedWrappingNeoServer managedWrappingNeoServer = newWrappingNeoServerNeo4jRule().build();\n" + 
			"	\n" + 
			"	@Rule\n" + 
			"	public Neo4jRule neo4jRule = newNeo4jRule().defaultManagedNeo4j();\n" + 
			"	\n" + 
			"	#foreach( $method in $methods )	\n" + 
			"@Test\n" + 
			"	public void $method () {\n" + 
			"\n" + 
			"	}\n" + 
			"	#end\n" + 
			"	\n" + 
			"}";
	
	private static final String NEO4J_REMOTE_TEST = "package com.test;\n" + 
			"\n" + 
			"import static com.lordofthejars.nosqlunit.neo4j.ManagedNeoServerConfigurationBuilder.newManagedNeoServerConfiguration;\n" + 
			"import static com.lordofthejars.nosqlunit.neo4j.Neo4jRule.Neo4jRuleBuilder.newNeo4jRule;\n" + 
			"\n" + 
			"import org.junit.Rule;\n" + 
			"import org.junit.Test;\n" + 
			"\n" + 
			"import com.lordofthejars.nosqlunit.annotation.UsingDataSet;\n" + 
			"import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;\n" + 
			"import com.lordofthejars.nosqlunit.neo4j.Neo4jRule;\n" + 
			"\n" + 
			"@UsingDataSet(locations=\"neo4j-initial-data.xml\", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)\n" + 
			"public class MyTest {\n" + 
			"\n" + 
			"	@Rule\n" + 
			"	public Neo4jRule neo4jRule = newNeo4jRule().configure(newManagedNeoServerConfiguration()\n" + 
			"						   .uri(\"http://localhost:10/test\")\n" + 
			"						   .build()\n" + 
			"					       )\n" + 
			"					   .build();\n" + 
			"	\n" + 
			"	@Test\n" + 
			"	public void myMethod () {\n" + 
			"\n" + 
			"	}\n" + 
			"		\n" + 
			"}";
	
	private static final String REDIS_EMBEDDED_TEST = "package com.test;\n" + 
			"\n" + 
			"import static com.lordofthejars.nosqlunit.redis.EmbeddedRedis.EmbeddedRedisRuleBuilder.newEmbeddedRedisRule;\n" + 
			"import static com.lordofthejars.nosqlunit.redis.RedisRule.RedisRuleBuilder.newRedisRule;\n" + 
			"\n" + 
			"import org.junit.ClassRule;\n" + 
			"import org.junit.Rule;\n" + 
			"import org.junit.Test;\n" + 
			"\n" + 
			"import com.lordofthejars.nosqlunit.annotation.UsingDataSet;\n" + 
			"import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;\n" + 
			"import com.lordofthejars.nosqlunit.redis.EmbeddedRedis;\n" + 
			"import com.lordofthejars.nosqlunit.redis.RedisRule;\n" + 
			"\n" + 
			"@UsingDataSet(locations=\"redis-initial-data.json\", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)\n" + 
			"public class MyTest {\n" + 
			"\n" + 
			"\n" + 
			"	@ClassRule\n" + 
			"	public static EmbeddedRedis embeddedRedis = newEmbeddedRedisRule().build();\n" + 
			"\n" + 
			"	@Rule\n" + 
			"	public RedisRule redisRule = newRedisRule().defaultEmbeddedRedis();\n" + 
			"	\n" + 
			"	@Test\n" + 
			"	public void myMethod () {\n" + 
			"		\n" + 
			"	}\n" + 
			"		\n" + 
			"}";
	
	private static final String REDIS_MANAGED_TEST = "package com.test;\n" + 
			"\n" + 
			"import static com.lordofthejars.nosqlunit.redis.RedisRule.RedisRuleBuilder.newRedisRule;\n" + 
			"import static com.lordofthejars.nosqlunit.redis.ManagedRedis.ManagedRedisRuleBuilder.newManagedRedisRule;\n" + 
			"\n" + 
			"import org.junit.ClassRule;\n" + 
			"import org.junit.Rule;\n" + 
			"import org.junit.Test;\n" + 
			"\n" + 
			"import com.lordofthejars.nosqlunit.annotation.UsingDataSet;\n" + 
			"import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;\n" + 
			"import com.lordofthejars.nosqlunit.redis.ManagedRedis;\n" + 
			"import com.lordofthejars.nosqlunit.redis.RedisRule;\n" + 
			"\n" + 
			"@UsingDataSet(locations=\"redis-initial-data.json\", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)\n" + 
			"public class MyTest {\n" + 
			"\n" + 
			"	@ClassRule\n" + 
			"	public static ManagedRedis managedRedis = newManagedRedisRule().redisPath(\"/opt/server\").build();\n" + 
			"\n" + 
			"	@Rule\n" + 
			"	public RedisRule redisRule = newRedisRule().defaultManagedRedis();\n" + 
			"	\n" + 
			"	@Test\n" + 
			"	public void myMethod () {\n" + 
			"		\n" + 
			"	}\n" + 
			"		\n" + 
			"}";
	
	private static final String REDIS_REMOTE_TEST = "package com.test;\n" + 
			"\n" + 
			"import static com.lordofthejars.nosqlunit.redis.RemoteRedisConfigurationBuilder.newRemoteRedisConfiguration;\n" + 
			"import static com.lordofthejars.nosqlunit.redis.RedisRule.RedisRuleBuilder.newRedisRule;\n" + 
			"\n" + 
			"import org.junit.Rule;\n" + 
			"import org.junit.Test;\n" + 
			"\n" + 
			"import com.lordofthejars.nosqlunit.annotation.UsingDataSet;\n" + 
			"import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;\n" + 
			"import com.lordofthejars.nosqlunit.redis.RedisRule;\n" + 
			"\n" + 
			"@UsingDataSet(locations=\"redis-initial-data.json\", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)\n" + 
			"public class MyTest {\n" + 
			"\n" + 
			"	@Rule\n" + 
			"	public RedisRule redisRule = newRedisRule().configure(newRemoteRedisConfiguration().host(\"localhost\")\n" + 
			"						   .port(10)\n" + 
			"						   .build()\n" + 
			"						   )\n" + 
			"					.build();\n" + 
			"	\n" + 
			"	@Test\n" + 
			"	public void myMethod () {\n" + 
			"		\n" + 
			"	}\n" + 
			"		\n" + 
			"}";
	
	@Test
	public void embedded_cassandra_test_should_be_created() {
		
		TestCreator testCreator = new JavaTestCreator();
		String testClassContent = testCreator.createTestClass(DatabaseEnum.CASSANDRA, "embedded", embeddedParameters());
		
		assertThat(testClassContent, is(CASSANDRA_EMBEDDED_TEST));
		
	}
	
	@Test
	public void managed_cassandra_test_should_be_created() {
		
		TestCreator testCreator = new JavaTestCreator();
		String testClassContent = testCreator.createTestClass(DatabaseEnum.CASSANDRA, "managed", managedParameters());
		
		assertThat(testClassContent, is(CASSANDRA_MANAGED_TEST));
		
	}
	
	@Test
	public void remote_cassandra_test_should_be_created() {
		
		TestCreator testCreator = new JavaTestCreator();
		String testClassContent = testCreator.createTestClass(DatabaseEnum.CASSANDRA, "remote", remoteParameters());
		
		assertThat(testClassContent, is(CASSANDRA_REMOTE_TEST));
		
	}
	
	@Test
	public void remote_couchDB_test_should_be_created() {
		
		TestCreator testCreator = new JavaTestCreator();
		String testClassContent = testCreator.createTestClass(DatabaseEnum.COUCHDB, "remote", remoteParameters());
		
		assertThat(testClassContent, is(COUCHDB_REMOTE_TEST));
		
	}
	
	@Test
	public void managed_couchDB_test_should_be_created() {
		
		TestCreator testCreator = new JavaTestCreator();
		String testClassContent = testCreator.createTestClass(DatabaseEnum.COUCHDB, "managed", managedParameters());
		
		assertThat(testClassContent, is(COUCHDB_MANAGED_TEST));
		
	}
	
	@Test
	public void embedded_hbase_test_should_be_created() {
		
		TestCreator testCreator = new JavaTestCreator();
		String testClassContent = testCreator.createTestClass(DatabaseEnum.HBASE, "embedded", embeddedParameters());
		
		assertThat(testClassContent, is(HBASE_EMBEDDED_TEST));
		
	}
	
	@Test
	public void managed_hbase_test_should_be_created() {
		
		TestCreator testCreator = new JavaTestCreator();
		String testClassContent = testCreator.createTestClass(DatabaseEnum.HBASE, "managed", managedParameters());
		
		assertThat(testClassContent, is(HBASE_MANAGED_TEST));
		
	}
	
	@Test
	public void remote_hbase_test_should_be_created() {
		
		TestCreator testCreator = new JavaTestCreator();
		String testClassContent = testCreator.createTestClass(DatabaseEnum.HBASE, "remote", remoteParameters());
		
		assertThat(testClassContent, is(HBASE_REMOTE_TEST));
		
	}
	
	@Test
	public void embedded_infinispan_test_should_be_created() {
		
		TestCreator testCreator = new JavaTestCreator();
		String testClassContent = testCreator.createTestClass(DatabaseEnum.INFINISPAN, "embedded", embeddedParameters());
		
		assertThat(testClassContent, is(INFINISPAN_EMBEDDED_TEST));
		
	}
	
	@Test
	public void managed_infinispan_test_should_be_created() {
		
		TestCreator testCreator = new JavaTestCreator();
		String testClassContent = testCreator.createTestClass(DatabaseEnum.INFINISPAN, "managed", managedParameters());
		
		assertThat(testClassContent, is(INFINISPAN_MANAGED_TEST));
		
	}
	
	@Test
	public void remote_infinispan_test_should_be_created() {
		
		TestCreator testCreator = new JavaTestCreator();
		String testClassContent = testCreator.createTestClass(DatabaseEnum.INFINISPAN, "remote", remoteParameters());
		
		assertThat(testClassContent, is(INFINISPAN_REMOTE_TEST));
		
	}
	
	@Test
	public void embedded_mongoDB_test_should_be_created() {
		
		TestCreator testCreator = new JavaTestCreator();
		String testClassContent = testCreator.createTestClass(DatabaseEnum.MONGODB, "embedded", embeddedParameters());
		
		assertThat(testClassContent, is(MONGODB_EMBEDDED_TEST));
		
	}
	
	@Test
	public void managed_mongoDB_test_should_be_created() {
		
		TestCreator testCreator = new JavaTestCreator();
		String testClassContent = testCreator.createTestClass(DatabaseEnum.MONGODB, "managed", managedParameters());
		
		assertThat(testClassContent, is(MONGODB_MANAGED_TEST));
		
	}
	
	@Test
	public void remote_mongoDB_test_should_be_created() {
		
		TestCreator testCreator = new JavaTestCreator();
		String testClassContent = testCreator.createTestClass(DatabaseEnum.MONGODB, "remote", remoteParameters());
		
		assertThat(testClassContent, is(MONGODB_REMOTE_TEST));
		
	}
	
	@Test
	public void embedded_neo4j_test_should_be_created() {
		
		TestCreator testCreator = new JavaTestCreator();
		String testClassContent = testCreator.createTestClass(DatabaseEnum.NEO4J, "embedded", embeddedParameters());
		
		assertThat(testClassContent, is(NEO4J_EMBEDDED_TEST));
		
	}
	
	@Test
	public void managed_neo4j_test_should_be_created() {
		
		TestCreator testCreator = new JavaTestCreator();
		String testClassContent = testCreator.createTestClass(DatabaseEnum.NEO4J, "managed", managedParameters());
		
		assertThat(testClassContent, is(NEO4J_MANAGED_TEST));
		
	}
	
	@Test
	public void remote_neo4j_test_should_be_created() {
		
		TestCreator testCreator = new JavaTestCreator();
		String testClassContent = testCreator.createTestClass(DatabaseEnum.NEO4J, "remote", remoteParameters());
		
		assertThat(testClassContent, is(NEO4J_REMOTE_TEST));
		
	}
	
	@Test
	public void embedded_redis_test_should_be_created() {
		
		TestCreator testCreator = new JavaTestCreator();
		String testClassContent = testCreator.createTestClass(DatabaseEnum.REDIS, "embedded", embeddedParameters());
		
		assertThat(testClassContent, is(REDIS_EMBEDDED_TEST));
		
	}
	
	@Test
	public void managed_redis_test_should_be_created() {
		
		TestCreator testCreator = new JavaTestCreator();
		String testClassContent = testCreator.createTestClass(DatabaseEnum.REDIS, "managed", managedParameters());
		
		assertThat(testClassContent, is(REDIS_MANAGED_TEST));
		
	}
	
	@Test
	public void remote_redis_test_should_be_created() {
		
		TestCreator testCreator = new JavaTestCreator();
		String testClassContent = testCreator.createTestClass(DatabaseEnum.REDIS, "remote", remoteParameters());
		
		assertThat(testClassContent, is(REDIS_REMOTE_TEST));
		
	}
	
	private Map<Object, Object> remoteParameters() {
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("package", "com.test");
		parameters.put("classname", "MyTest");
		parameters.put("methods", Arrays.asList("myMethod"));
		parameters.put("database", "test");
		parameters.put("host", "localhost");
		parameters.put("port", 10);
		
		return parameters;
	}
	
	private Map<Object, Object> managedParameters() {
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("package", "com.test");
		parameters.put("classname", "MyTest");
		parameters.put("methods",  Arrays.asList("myMethod"));
		parameters.put("database", "test");
		parameters.put("path", "/opt/server");
		
		return parameters;
	}
	
	private Map<Object, Object> embeddedParameters() {
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("package", "com.test");
		parameters.put("classname", "MyTest");
		parameters.put("methods", Arrays.asList("myMethod"));
		parameters.put("database", "test");
		return parameters;
	}
	
}
