package pl.java.project.company.manager.tables;

import java.util.Date;

public class Leave {
  private int id;
  private Worker worker;
  private Date beginDate;
  private Date endDate;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Worker getWorker() {
    return worker;
  }

  public void setWorker(Worker worker) {
    this.worker = worker;
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

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
}
