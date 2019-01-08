package pl.java.project.company.manager.tables;

import java.util.Date;
import java.util.Set;

public class Person {
  private String pesel;
  private String name;
  private String surname;
  private Date dateOfBirth;
  private Set<Worker> workers;
  private Set<SalaryHistory> salaryHistories;
  private Set<TransfersMade> transfersMades;

  public Set<TransfersMade> getTransfersMades() {
    return transfersMades;
  }

  public void setTransfersMades(Set<TransfersMade> transfersMades) {
    this.transfersMades = transfersMades;
  }

  public Set<SalaryHistory> getSalaryHistories() {
    return salaryHistories;
  }

  public void setSalaryHistories(Set<SalaryHistory> salaryHistories) {
    this.salaryHistories = salaryHistories;
  }

  public Set<Worker> getWorkers() {
    return workers;
  }

  public void setWorkers(Set<Worker> workers) {
    this.workers = workers;
  }

  public String getPesel() {
    return pesel;
  }

  public void setPesel(String pesel) {
    this.pesel = pesel;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }
}
