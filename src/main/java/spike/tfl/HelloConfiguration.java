package spike.tfl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class HelloConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty
    private String template;

    @NotEmpty
    @JsonProperty
    private String defaultName = "Stranger";

    @NotEmpty
    @JsonProperty
    private String defaultRole;

//    @Valid
//    @NotNull
//    @JsonProperty("database")
//    private DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();


    public String getTemplate() {
        return template;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public String getDefaultRole() {
        return defaultRole;
    }

//    public DatabaseConfiguration getDatabaseConfiguration() {
//        return databaseConfiguration;
//    }


}
