package app.model;

import java.util.Date;
import java.util.List;

/**
 * Created by alexe on 13.05.2016.
 */
public class Contact {
    Long id;
    String firstName;
    String lastName;

    Address address;

    List<String> phones;
    List<String> emails;

    String photoPath;
    Date birthday;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (!firstName.equals(contact.firstName)) return false;
        if (!lastName.equals(contact.lastName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        return result;
    }
}
