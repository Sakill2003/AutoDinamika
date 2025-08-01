package autodinamika.api.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seller_ID;

    private String first_Name;
    private String last_Name;
    private LocalDate birthday_Date;
    private String phone;
    private String email;
    private String address;

    private Double commissions = 0.0;

    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    // Getters and Setters

    public Long getSeller_ID() {
        return seller_ID;
    }

    public void setSeller_ID(Long seller_ID) {
        this.seller_ID = seller_ID;
    }

    public String getFirst_Name() {
        return first_Name;
    }

    public void setFirst_Name(String first_Name) {
        this.first_Name = first_Name;
    }

    public String getLast_Name() {
        return last_Name;
    }

    public void setLast_Name(String last_Name) {
        this.last_Name = last_Name;
    }

    public LocalDate getBirthday_Date() {
        return birthday_Date;
    }

    public void setBirthday_Date(LocalDate birthday_Date) {
        this.birthday_Date = birthday_Date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getCommissions() {
        return commissions;
    }

    public void setCommissions(Double commissions) {
        this.commissions = commissions;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
