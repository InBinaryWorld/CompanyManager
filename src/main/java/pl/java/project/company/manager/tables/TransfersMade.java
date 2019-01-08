package pl.java.project.company.manager.tables;

public class TransfersMade {
  private int id;
  private Portfolio portfolio;
  private Person person;
  private int amount;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Portfolio getPortfolio() {
    return portfolio;
  }

  public void setPortfolio(Portfolio portfolio) {
    this.portfolio = portfolio;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }
}
