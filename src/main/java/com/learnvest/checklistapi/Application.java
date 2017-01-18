package com.learnvest.checklistapi;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author Jerome Simmonds
 *
 */
@ApplicationPath(ChecklistApiConstants.APPLICATION_PATH)
public class Application extends ResourceConfig {

	public Application() {
        packages("com.learnvest.checklistapi.exception",
			"com.learnvest.checklistapi.resource");
	}
}
