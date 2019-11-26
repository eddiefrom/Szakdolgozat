package models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDate bornDate;
    private Integer age;
    private int classNumber;
    private String profilPicURL;
    private Double allPoints;
    private Double easyPoints;
    private Double mediumPoints;
    private Double hardPoints;
    private Double trueFalseExercise;
    private Double fromMindExercise;
    private Double mainExercise;
    private int shortestTime;
    private int largestTime;
    private int competitionTime;
    private int practiceTime;
    @ManyToMany()
    private List<Teacher> teachers = new ArrayList<>();
    @ManyToOne
    private Parent parent;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "studentList")
    private List<Worksheet> worksheetList = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "student", orphanRemoval = true)
    private List<CompetitionResults> competitionResults = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "studentP", orphanRemoval = true)
    private List<PracticeResults> practiceResults = new ArrayList<>();

    public Student() {
    }

    public Student(String name, String email, String password, LocalDate bornDate, int classNumber,
                   String profilPicURL, Parent parent) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.bornDate = bornDate;
        this.age = Period.between(bornDate, LocalDate.now()).getYears();
        this.classNumber = classNumber;
        this.profilPicURL = profilPicURL;
        this.parent = parent;
    }

    public Student(String name, String email, String password, LocalDate bornDate, int classNumber, String profilPicURL, Double allPoints, Double easyPoints, Double mediumPoints, Double hardPoints, Double trueFalseExercise, Double fromMindExercise, Double mainExercise,
                   int shortestTime, int largestTime, int competitionTime, int practiceTime, Parent parent) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.bornDate = bornDate;
        this.age = Period.between(bornDate, LocalDate.now()).getYears();
        this.classNumber = classNumber;
        this.profilPicURL = profilPicURL;
        this.allPoints = allPoints;
        this.easyPoints = easyPoints;
        this.mediumPoints = mediumPoints;
        this.hardPoints = hardPoints;
        this.trueFalseExercise = trueFalseExercise;
        this.fromMindExercise = fromMindExercise;
        this.mainExercise = mainExercise;
        this.shortestTime = shortestTime;
        this.largestTime = largestTime;
        this.competitionTime = competitionTime;
        this.practiceTime = practiceTime;
        this.parent = parent;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBornDate() {
        return bornDate;
    }

    public void setBornDate(LocalDate bornDate) {
        this.bornDate = bornDate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public int getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }

    public String getProfilPicURL() {
        return profilPicURL;
    }

    public void setProfilPicURL(String profilPicURL) {
        this.profilPicURL = profilPicURL;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }
    public List<Worksheet> getWorksheetList() {
        return worksheetList;
    }

    public void setWorksheetList(List<Worksheet> worksheetList) {
        this.worksheetList = worksheetList;
    }
   public List<CompetitionResults> getCompetitionResults() {
        return competitionResults;
    }

    public void setCompetitionResults(List<CompetitionResults> competitionResults) {
        this.competitionResults = competitionResults;
    }

    public List<PracticeResults> getPracticeResults() {
        return practiceResults;
    }

    public void setPracticeResults(List<PracticeResults> practiceResults) {
        this.practiceResults = practiceResults;
    }

    public Double getAllPoints() {
        return allPoints;
    }

    public void setAllPoints(Double allPoints) {
        this.allPoints = allPoints;
    }

    public void addToAllPoints(Double p)
    {
        this.allPoints += p;
    }

    public void minusFromAllPoints(Double p)
    {
        this.allPoints -= p;
    }

    public int getCompetitionTime() {
        return competitionTime;
    }

    public void setCompetitionTime(int competitionTime) {
        this.competitionTime = competitionTime;
    }

    public void addCompTime(int d)
    {
        this.competitionTime += d;
    }

    public int getPracticeTime() {
        return practiceTime;
    }

    public void addPractTime(int d)
    {
        this.practiceTime += d;
    }

    public void minusMediumPoints(Double mediumPoints) {
        this.mediumPoints -= mediumPoints;
    }

    public void addTrueFalseExercise(Double trueFalseExercise) {
        this.trueFalseExercise += trueFalseExercise;
    }

    public void minusTrueFalseExercise(Double trueFalseExercise) {
        this.trueFalseExercise -= trueFalseExercise;
    }

    public void minusMainExercise(Double d)
    {
        this.mainExercise -= d;
    }

    public void addMainExercise(Double d)
    {
        this.mainExercise += d;
    }

    public void addMediumPoints(Double mediumPoints) {
        this.mediumPoints += mediumPoints;
    }

    public void addHardPoints(Double hardPoints) {
        this.hardPoints += hardPoints;
    }

    public void minusHardPoints(Double hardPoints) {
        this.hardPoints -= hardPoints;
    }

    public void addEasyPoints(Double easyPoints) {
        this.easyPoints += easyPoints;
    }

    public void minusEasyPoints(Double easyPoints) {
        this.easyPoints -= easyPoints;
    }

    public void setPracticeTime(int practiceTime) {
        this.practiceTime = practiceTime;
    }

    public Double getEasyPoints() {
        return easyPoints;
    }

    public void setEasyPoints(Double easyPoints) {
        this.easyPoints = easyPoints;
    }

    public Double getMediumPoints() {
        return mediumPoints;
    }

    public void setMediumPoints(Double mediumPoints) {
        this.mediumPoints = mediumPoints;
    }

    public Double getHardPoints() {
        return hardPoints;
    }

    public void setHardPoints(Double hardPoints) {
        this.hardPoints = hardPoints;
    }

    public Double getTrueFalseExercise() {
        return trueFalseExercise;
    }

    public void setTrueFalseExercise(Double trueFalseExercise) {
        this.trueFalseExercise = trueFalseExercise;
    }

    public Double getFromMindExercise() {
        return fromMindExercise;
    }

    public void setFromMindExercise(Double fromMindExercise) {
        this.fromMindExercise = fromMindExercise;
    }

    public Double getMainExercise() {
        return mainExercise;
    }

    public void setMainExercise(Double mainExercise) {
        this.mainExercise = mainExercise;
    }

    public int getShortestTime() {
        return shortestTime;
    }

    public void setShortestTime(int shortestTime) {
        this.shortestTime = shortestTime;
    }

    public int getLargestTime() {
        return largestTime;
    }

    public void setLargestTime(int largestTime) {
        this.largestTime = largestTime;
    }

   public void addWorksheet(Worksheet w)
   {
       worksheetList.add(w);
       w.getStudentList().add(this);
   }

   public void removeWorksheet(Worksheet w)
   {
       worksheetList.remove(w);
       w.getStudentList().remove(this);
   }

   public void addCompResult(CompetitionResults competitionResults)
    {
        this.competitionResults.add(competitionResults);
        competitionResults.setStudent(this);
    }

    public void addPractResult(PracticeResults practiceResults)
    {
        this.practiceResults.add(practiceResults);
        practiceResults.setStudentP(this);
    }

   public void removeCompResult(CompetitionResults competitionResults)
   {
       this.competitionResults.remove(competitionResults);
       competitionResults.setStudent(null);
   }

    public void removePractResult(PracticeResults practiceResults)
    {
        this.practiceResults.remove(practiceResults);
        practiceResults.setStudentP(null);
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
}
