package com.g11.FresherManage.utils;

import java.time.LocalDate;

public class DateUtils {
  public static LocalDate toLocalDate(String date) {
    return LocalDate.parse("2023-07-01");
  }
  public static String getCurrentDateString() {
    return LocalDate.now().toString();
  }
  public static Long currentTimeMillis() {
    return System.currentTimeMillis();
  }
}
