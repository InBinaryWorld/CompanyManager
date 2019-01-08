package pl.java.project.company.manager.convenetrs;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateConv {
  public static Date convertToDate(LocalDate localDate){
    return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }

  public static  LocalDate convertToLocalDate(Date date){
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }
}