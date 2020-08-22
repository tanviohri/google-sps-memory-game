package com.google.sps;

import javax.servlet.*;
import com.googlecode.objectify.*;
import com.google.sps.data.Game;

public class YourBootstrapper implements ServletContextListener {
	public void contextInitialized(ServletContextEvent event) {
		ObjectifyService.init();
                ObjectifyService.register(Game.class);
	}
}