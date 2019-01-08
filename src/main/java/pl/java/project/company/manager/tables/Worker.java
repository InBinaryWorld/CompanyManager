package pl.java.project.company.manager.tables;

import java.security.PrivateKey;
import java.util.Date;
import java.util.Set;

public class Worker {
  private int id;
  private  Person person;
  private String profession;
  private int salary;
  private Date beginDate;
  private Date endDate;
  private Set <Leave> leaves;

  public Set<Leave> getLeaves() {
    return leaves;
  }

  public void setLeaves(Set<Leave> leaves) {
    this.leaves = leaves;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public String getProfession() {
    return profession;
  }

  public void setProfession(String profession) {
    this.profession = profession;
  }

  public int getSalary() {
    return salary;
  }

  public void setSalary(int salary) {
    this.salary = salary;
  }

  public Date getBeginDate() {
    return beginDate;
  }

  public void setBeginDate(Date beginDate) {
    this.beginDate = beginDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDAte) {
    this.endDate = endDAte;
  }

  @Override
  public String toString() {
    return person.getPesel();
  }
}
