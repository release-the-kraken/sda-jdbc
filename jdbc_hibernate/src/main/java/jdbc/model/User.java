package jdbc.model;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class User {
    private final long id;
    private final String name;
    private final String password;

}
