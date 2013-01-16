package com.lordofthejars.nosqlunit.forge;

import java.util.List;

import org.jboss.forge.project.dependencies.Dependency;

public class DependencyUtil {
	
	private DependencyUtil() {
		super();
	}

	public static Dependency getLatestNonSnapshotVersion(List<Dependency> dependencies) {
		if (dependencies == null) {
			return null;
		}
		for (int i = dependencies.size() - 1; i >= 0; i--) {
			Dependency dep = dependencies.get(i);
			if (!dep.getVersion().endsWith("SNAPSHOT")) {
				return dep;
			}
		}
		return dependencies.get(dependencies.size() - 1);
	}

}
