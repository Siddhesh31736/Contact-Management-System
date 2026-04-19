package com.contactmanager;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class ContactGUI extends JFrame {

    private ArrayList<Contact> contacts = new ArrayList<>();
    private final String FILE_NAME = "contacts.txt";

    private JTextField nameField, phoneField, emailField, searchField;
    private JTextArea displayArea;

    public ContactGUI() {
        setTitle("Contact Management System");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        loadContacts(); // 🔥 Load on start

        // 🔹 Top Panel
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        panel.add(phoneField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Search:"));
        searchField = new JTextField();
        panel.add(searchField);

        add(panel, BorderLayout.NORTH);

        // 🔹 Display Area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // 🔹 Buttons
        JPanel btnPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JButton addBtn = new JButton("Add");
        JButton viewBtn = new JButton("View");
        JButton searchBtn = new JButton("Search");
        JButton deleteBtn = new JButton("Delete");
        JButton updateBtn = new JButton("Update");

        btnPanel.add(addBtn);
        btnPanel.add(viewBtn);
        btnPanel.add(searchBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(updateBtn);

        add(btnPanel, BorderLayout.SOUTH);

        // 🔥 ADD
        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();

            if (name.isEmpty() || !name.matches("[a-zA-Z ]+")) {
                JOptionPane.showMessageDialog(this, "Invalid Name!");
                return;
            }

            if (!phone.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(this, "Phone must be 10 digits!");
                return;
            }

            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                JOptionPane.showMessageDialog(this, "Invalid Email!");
                return;
            }

            for (Contact c : contacts) {
                if (c.getName().equalsIgnoreCase(name)) {
                    JOptionPane.showMessageDialog(this, "Contact already exists!");
                    return;
                }
            }

            contacts.add(new Contact(name, phone, email));
            saveContacts(); // 🔥 Save after add
            clearFields();
            JOptionPane.showMessageDialog(this, "Contact Added!");
        });

        // 🔥 VIEW
        viewBtn.addActionListener(e -> {
            displayArea.setText("");

            if (contacts.isEmpty()) {
                displayArea.setText("No contacts available!");
                return;
            }

            for (Contact c : contacts) {
                displayArea.append(c + "\n");
            }
        });

        // 🔥 SEARCH
        searchBtn.addActionListener(e -> {
            String search = searchField.getText().trim();

            if (search.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter name to search!");
                return;
            }

            displayArea.setText("");

            for (Contact c : contacts) {
                if (c.getName().equalsIgnoreCase(search)) {
                    displayArea.append("Found: " + c + "\n");
                    return;
                }
            }

            displayArea.setText("Contact not found!");
        });

        // 🔥 DELETE
        deleteBtn.addActionListener(e -> {
            String name = searchField.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter name to delete!");
                return;
            }

            boolean found = false;

            for (int i = 0; i < contacts.size(); i++) {
                if (contacts.get(i).getName().equalsIgnoreCase(name)) {
                    contacts.remove(i);
                    found = true;
                    break;
                }
            }

            if (found) {
                saveContacts(); // 🔥 Save after delete
            }

            JOptionPane.showMessageDialog(this,
                    found ? "Deleted!" : "Contact not found!");
        });

        // 🔥 UPDATE
        updateBtn.addActionListener(e -> {
            String name = searchField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter name to update!");
                return;
            }

            if (!phone.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(this, "Phone must be 10 digits!");
                return;
            }

            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                JOptionPane.showMessageDialog(this, "Invalid Email!");
                return;
            }

            for (Contact c : contacts) {
                if (c.getName().equalsIgnoreCase(name)) {
                    c.setPhone(phone);
                    c.setEmail(email);
                    saveContacts(); // 🔥 Save after update
                    JOptionPane.showMessageDialog(this, "Updated!");
                    return;
                }
            }

            JOptionPane.showMessageDialog(this, "Contact not found!");
        });

        setVisible(true);
    }

    // 💾 SAVE CONTACTS
    private void saveContacts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Contact c : contacts) {
                writer.write(c.getName() + "," + c.getPhone() + "," + c.getEmail());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data!");
        }
    }

    // 📂 LOAD CONTACTS
    private void loadContacts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    contacts.add(new Contact(parts[0], parts[1], parts[2]));
                }
            }

        } catch (IOException e) {
            // File may not exist first time → ignore
        }
    }

    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }

    public static void main(String[] args) {
        new ContactGUI();
    }
}