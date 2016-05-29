package app.database;

import app.model.Contact;

import java.util.Set;

/**
 * Created by alexe on 13.05.2016.
 */
public interface DBStore {
    Long add(Contact contact);
    Contact get(Long id);
    Set<Contact> getContacts();
    int remove(Long id);
    int remove (Contact contact);
    Set<Contact> serch (Contact contact);
}
