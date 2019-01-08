package pl.java.project.company.manager.tables;

import java.util.Date;
import java.util.Set;

public class Portfolio {
  private int id;
  private int change;
  private int currentState;
  private Date changeDate;
  private String title;
  private Set<TransfersMade> transfersMades;

  public Set<TransfersMade> getTransfersMades() {
    return transfersMades;
  }

  public void setTransfersMades(Set<TransfersMade> transfersMades) {
    this.transfersMades = transfersMades;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getChange() {
    return change;
  }

  public void setChange(int change) {
    this.change = change;
  }

  public int getCurrentState() {
    return currentState;
  }

  public void setCurrentState(int currentState) {
    this.currentState = currentState;
  }

  public Date getChangeDate() {
    return changeDate;
  }

  public void setChangeDate(Date changeDate) {
    this.changeDate = changeDate;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
