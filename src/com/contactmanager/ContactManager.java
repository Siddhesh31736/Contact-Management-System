package com.contactmanager;

import java.util.ArrayList;

public class ContactManager {
    private ArrayList<Contact> contacts = new ArrayList<>();

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found!");
            return;
        }
        for (Contact c : contacts) {
            System.out.println(c);
        }
    }

    public void searchContact(String name) {
        for (Contact c : contacts) {
            if (c.getName().equalsIgnoreCase(name)) {
                System.out.println("Found: " + c);
                return;
            }
        }
        System.out.println("Contact not found!");
    }

    public void deleteContact(String name) {
        contacts.removeIf(c -> c.getName().equalsIgnoreCase(name));
    }
}