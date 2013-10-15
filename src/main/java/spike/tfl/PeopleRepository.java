package spike.tfl;

import org.mongodb.morphia.Datastore;

public class PeopleRepository {
    private Datastore store;

    public PeopleRepository(Datastore store) {
        this.store = store;
    }

    public Person findPerson(String name) {
        return store.createQuery(Person.class).field("name").equal(name.toString()).get();
    }
}
