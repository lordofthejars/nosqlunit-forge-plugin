package com.lordofthejars.nosqlunit.forge;

public enum DatabaseEnum {

	MONGODB("MongoDB", "nosqlunit-mongodb", "mongo-initial-data.json"), NEO4J("Neo4j", "nosqlunit-neo4j", "neo4j-initial-data.xml"), CASSANDRA("Cassandra",
			"nosqlunit-cassandra", "cassandra-initial-data.json"), HBASE("HBase", "nosqlunit-hbase", "hbase-initial-data.json"), REDIS("Redis", "nosqlunit-redis", "redis-initial-data.json"), INFINISPAN(
			"Infinispan", "nosqlunit-infinispan", "infinispan-initial-data.json"), COUCHDB("CouchDB", "nosqlunit-couchdb", "couchdb-initial-data.json");

	private String name;
	private String artifact;
	private String datasetName;

	private DatabaseEnum(String name, String artifact, String datasetName) {
		this.name = name;
		this.artifact = artifact;
		this.datasetName = datasetName;
	}

	public String getName() {
		return this.name;
	}

	public String getArtifact() {
		return this.artifact;
	}
	
	public String getDatasetName() {
		return this.datasetName;
	}
	
}
