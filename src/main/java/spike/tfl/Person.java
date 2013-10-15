package spike.tfl;


import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("person")
public class Person {

    @Id
    private ObjectId id;

    private String name;

    private String role;

    public Person() {}

    public Person(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
