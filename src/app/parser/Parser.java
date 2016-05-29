package app.parser;

import app.model.Contact;

import java.util.Set;

/**
 * Created by alexe on 13.05.2016.
 */
public interface Parser {
    Set<Contact> parse(String data);
}
