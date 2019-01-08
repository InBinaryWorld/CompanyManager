package pl.java.project.company.manager.tables;

import java.util.Date;

public class SalaryHistory {
  private int id;
  private int oldSalary;
  private int newSalary;
  private Person person;
  private Date changeDate;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getOldSalary() {
    return oldSalary;
  }

  public void setOldSalary(int oldSalary) {
    this.oldSalary = oldSalary;
  }

  public int getNewSalary() {
    return newSalary;
  }

  public void setNewSalary(int newSalary) {
    this.newSalary = newSalary;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public Date getChangeDate() {
    return changeDate;
  }

  public void setChangeDate(Date changeDate) {
    this.changeDate = changeDate;
  }
}
