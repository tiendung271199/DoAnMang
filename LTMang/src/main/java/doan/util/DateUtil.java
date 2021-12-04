package doan.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String formatDate(Timestamp date) {
		String[] array = date.toString().split("\\s")[0].split("-");
		StringBuilder sb = new StringBuilder();
		sb.append(array[2]).append("/").append(array[1]).append("/").append(array[0]);
		return sb.toString();
	}

	public static String findDatePresent() {
		return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	}

	// lấy ngày (7 ngày trước) so với ngày hiện tại
	public static String findDatePast(String date, int n) {
		if (n == 0) {
			return date;
		}
		String[] arrDate = date.split("\\/");
		return findDatePast(findDateBefore(Integer.parseInt(arrDate[0]), Integer.parseInt(arrDate[1]),
				Integer.parseInt(arrDate[2])), n - 1);
	}

	public static String convertFormatDate(String date) {
		String[] arrDate = date.split("\\/");
		StringBuilder sb = new StringBuilder();
		sb.append(arrDate[2]).append("-").append(arrDate[1]).append("-").append(arrDate[0]);
		return sb.toString();
	}

	public static String findDateBefore(int day, int month, int year) {
		StringBuilder sb = new StringBuilder();
		day--;
		if (day == 0) {
			month--;
			if (month == 0) {
				month = 12;
				year--;
			}
			day = timSoNgayTrongThang(month, year);
		}
		sb.append(formatDate(day)).append("/").append(formatDate(month)).append("/").append(year);
		return sb.toString();
	}

	public static String findDateAfter(int day, int month, int year) {
		StringBuilder sb = new StringBuilder();
		day++;
		if (day > timSoNgayTrongThang(month, year)) {
			day = 1;
			month++;
			if (month > 12) {
				month = 1;
				year++;
			}
		}
		sb.append(formatDate(day)).append("/").append(formatDate(month)).append("/").append(year);
		return sb.toString();
	}

	public static int timSoNgayTrongThang(int month, int year) {
		int day = 0;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			day = 30;
			break;
		case 2:
			if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
				day = 29;
			} else {
				day = 28;
			}
			break;
		}
		return day;
	}

	public static String getDateTime() {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
	}

	// check thời gian đã cách nhau 1 phút chưa => true: tăng views
	public static boolean checkDateTime(String date) {
		String datePresent = getDateTime();
		int dayDate = getDay(date);
		int monthDate = getMonth(date);
		int yearDate = getYear(date);
		int hourDate = getHour(date);
		int minuteDate = getMinute(date);
		int secondDate = getSecond(date);
		int dayDatePresent = getDay(datePresent);
		int monthDatePresent = getMonth(datePresent);
		int yearDatePresent = getYear(datePresent);
		int hourDatePresent = getHour(datePresent);
		int minuteDatePresent = getMinute(datePresent);
		int secondDatePresent = getSecond(datePresent);
		if (yearDatePresent == yearDate) {
			if (monthDatePresent == monthDate) {
				if (dayDatePresent == dayDate) {
					if (hourDatePresent == hourDate) {
						if (minuteDatePresent == minuteDate) {
							return false;
						} else {
							if (minuteDatePresent - minuteDate == 1) {
								if (secondDatePresent < secondDate) {
									return false;
								}
							}
						}
					}
				}
			}
		}
		return true;
	}

	public static int getDay(String date) {
		return Integer.parseInt(date.split("\\s")[0].split("\\/")[0]);
	}

	public static int getMonth(String date) {
		return Integer.parseInt(date.split("\\s")[0].split("\\/")[1]);
	}

	public static int getYear(String date) {
		return Integer.parseInt(date.split("\\s")[0].split("\\/")[2]);
	}

	public static int getHour(String date) {
		return Integer.parseInt(date.split("\\s")[1].split("\\:")[0]);
	}

	public static int getMinute(String date) {
		return Integer.parseInt(date.split("\\s")[1].split("\\:")[1]);
	}

	public static int getSecond(String date) {
		return Integer.parseInt(date.split("\\s")[1].split("\\:")[2]);
	}

	public static String formatDate(int n) {
		String kq = Integer.toString(n);
		if (kq.length() == 1) {
			kq = "0" + kq;
		}
		return kq;
	}

}
