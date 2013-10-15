package spike.tfl;


import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import spike.tfl.health.TemplateHealthCheck;

import java.net.UnknownHostException;

public class HelloApp extends Service<HelloConfiguration> {

    public static void main(String[] args) throws Exception {
        new HelloApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<HelloConfiguration> bootstrap) {
        bootstrap.setName("hello-world");
    }

    @Override
    public void run(HelloConfiguration configuration,
                    Environment environment) {
        final String template = configuration.getTemplate();
        final String defaultName = configuration.getDefaultName();
        final String defaultRole = configuration.getDefaultRole();

//        Datastore store = getDatastore(configuration.getDatabaseConfiguration());

        Datastore store = getDatastore();
        addPeople(store);
        PeopleRepository repository = new PeopleRepository(store);

        environment.addResource(new HelloController(template, defaultName, defaultRole, repository));
        environment.addHealthCheck(new TemplateHealthCheck(template));
    }

    private Datastore getDatastore() {
        try {
            Mongo mongo = new MongoClient("127.0.0.1", 27017);
            Morphia morphia = new Morphia();
            morphia.map(Person.class);
            return morphia.createDatastore(mongo, "People");
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    private void addPeople(Datastore store) {
        if (store.getCount(Person.class) <= 0) {
            store.save(new Person("Shodhan", "Tech lead"));
            store.save(new Person("Simon", "Developer"));
            store.save(new Person("Laura", "Developer"));
            store.save(new Person("Ste", "Developer"));
            store.save(new Person("Nima", "Developer"));
            store.save(new Person("Torsten", "QA"));
            store.save(new Person("Sneha", "BA"));
            store.save(new Person("Chirdeep", "PM"));
            store.save(new Person("Susie", "Devops"));
        }
    }

}
