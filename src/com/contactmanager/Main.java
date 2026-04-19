package com.contactmanager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ContactManager manager = new ContactManager();

        while (true) {
            System.out.println("\n===== Contact Manager =====");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Search Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Name: ");
                    String name = sc.nextLine();

                    System.out.print("Phone: ");
                    String phone = sc.nextLine();

                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    manager.addContact(new Contact(name, phone, email));
                    System.out.println("Contact added!");
                    break;

                case 2:
                    manager.viewContacts();
                    break;

                case 3:
                    System.out.print("Enter name: ");
                    manager.searchContact(sc.nextLine());
                    break;

                case 4:
                    System.out.print("Enter name: ");
                    manager.deleteContact(sc.nextLine());
                    System.out.println("Contact deleted!");
                    break;

                case 5:
                    System.out.println("Exiting...");
                    return;
            }
        }
    }
}