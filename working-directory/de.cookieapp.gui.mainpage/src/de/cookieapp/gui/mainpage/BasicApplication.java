package de.cookieapp.gui.mainpage;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;
import org.eclipse.rap.rwt.client.WebClient;


public class BasicApplication implements ApplicationConfiguration {

    public void configure(Application application) {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put(WebClient.PAGE_TITLE, "CookieApp");
        System.out.println("Application initialized");
        application.addEntryPoint("/cookieapp", MainPage.class, properties);
    }

}
