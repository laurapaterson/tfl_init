package spike.tfl;

import com.google.common.base.Optional;
import com.yammer.metrics.annotation.Timed;
import spike.tfl.core.Greeting;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloController {
    private final String template;
    private final String defaultName;
    private String defaultRole;
    private PeopleRepository repository;
    private final AtomicLong counter;

    public HelloController(String template, String defaultName, String defaultRole, PeopleRepository repository) {
        this.template = template;
        this.defaultName = defaultName;
        this.defaultRole = defaultRole;
        this.repository = repository;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public Greeting ohHello(@QueryParam("name") Optional<String> name) {
        String content = null;
        if (name.isPresent()) {
            content = getTeamGreeting(name.get(), getRoleFor(name.get()));
        } else {
            content = getTeamGreeting(name.or(defaultName), defaultRole);
        }

        return new Greeting(counter.incrementAndGet(), content);
    }

    private String getRoleFor(String name) {
        Person person = repository.findPerson(name);
        if (person != null) {
            return person.getRole();
        }
        return defaultRole;
    }

    private String getTeamGreeting(String name, String role) {
        return String.format(template, name, role);
    }
}