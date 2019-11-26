package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String userName;
    private String password;
    private String email;
    private String profilPicURL;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "teachers")
    private List<Student> studentList = new ArrayList<>();


    public Teacher() {
    }

    public Teacher(String name, String userName, String password, String email, String profilPicURL) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.profilPicURL = profilPicURL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilPicURL() {
        return profilPicURL;
    }

    public void setProfilPicURL(String profilPicURL) {
        this.profilPicURL = profilPicURL;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
}
