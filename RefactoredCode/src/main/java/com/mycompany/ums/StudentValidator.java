package com.mycompany.ums;

import java.util.Objects;

/**
 * Student entity with immutable fields.
 */
public class StudentValidator {
    private String id = "";
    private String name = "";
    private String email = "";
    private String phone = "";

    public void Student(String id, String name, String email, String phone) {
        this.id = Objects.requireNonNull(id, "ID cannot be null");
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.email = Objects.requireNonNull(email, "Email cannot be null");
        this.phone = Objects.requireNonNull(phone, "Phone cannot be null");
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    @Override
    public String toString() {
        return "StudentValidator [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + "]";
    }

    // Other student related methods can be here
}

/**
 * Interface for validating student details.
 * (ISP: Small focused interface)
 */
interface StudentIdValidator {
    void validateId(String id);
}

interface StudentNameValidator {
    void validateName(String name);
}

interface StudentEmailValidator {
    void validateEmail(String email);
}

interface StudentPhoneValidator {
    void validatePhone(String phone);
}

/**
 * Composite interface for full student validation.
 */


/**
 * Concrete implementation of StudentValidator.
 * (DIP: High-level module depends on abstraction)
 * (OCP: If new validations are needed, extend this class without changing client code)
 */
