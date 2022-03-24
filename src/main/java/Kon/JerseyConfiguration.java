package Kon;

import Kon.resources.ConsoleSalesResource;
import Kon.resources.GameRatingsResource;
import Kon.resources.VideogameSalesResource;
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
