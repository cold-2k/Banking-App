package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Customer {
    // Data fields

    private static int nextId = 1;
    private final String id;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    private final List<Account> accounts;

    // Constructor

    Customer(String firstName, String lastName, String phoneNumber, String email) {
        this.id = String.format("CUS%06d", nextId++);

        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;

        this.accounts = new ArrayList<>();
    }

    // Methods

    void addAccount(Account account) {
        accounts.add(account);
    }

    void removeAccount(Account account) {
        accounts.remove(account);
    }

    // Utility Methods

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", phoneNumber=" + phoneNumber +
                ", email=" + email +
                ", accounts=" + accounts.size() +
                '}';
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
