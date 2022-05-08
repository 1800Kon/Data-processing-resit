package Kon;

import Kon.apiResources.ConsoleSalesResource;
import Kon.apiResources.GameRatingsResource;
import Kon.apiResources.VideogameSalesResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfiguration extends ResourceConfig {
    public JerseyConfiguration() {
        register(ConsoleSalesResource.class);
        register(VideogameSalesResource.class);
        register(GameRatingsResource.class);
    }

}
