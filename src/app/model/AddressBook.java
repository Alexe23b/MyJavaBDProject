package app.model;

import java.util.Collection;
import java.util.Set;

/**
 * Created by alexe on 13.05.2016.
 */
public interface AddressBook {
    boolean add(Contact contact);
    boolean add(Collection<Contact> contact);

    boolean remove(Contact contact);
    boolean edit(Contact contact);

    Set<Contact> getContacts();

    Contact getContact(Long id);

    Set<Contact> search(Contact contact);
 }
