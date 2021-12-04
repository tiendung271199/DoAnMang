package doan.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

import doan.model.User;

public class StringUtil {

	// rút gọn chuỗi
	public static String setStringCompact(String str, int n) {
		StringBuilder sb = new StringBuilder(str);
		if (sb.length() > n) {
			sb.delete(n, sb.length()).append("...");
		}
		return sb.toString();
	}

	public static String setStringCompact2(String str, int n) {
		String[] arr = delSpace(str).split("\\s");
		StringBuilder sb = new StringBuilder();
		if (arr.length > n) {
			for (int i = 0; i < n; i++) {
				sb.append(arr[i]);
				if (i != n - 1) {
					sb.append(" ");
				} else {
					sb.append("...");
				}
			}
			return sb.toString();
		}
		return str;
	}

	// xoá khoảng trắng trong chuỗi
	public static String delSpace(String str) {
		return str.trim().replaceAll("\\s+", " ");
	}

	// định dạng số điện thoại
	public static String beautifulPhone(String phone) {
		return phone.replaceFirst("(\\d{4})(\\d{3})(\\d+)", "$1 $2 $3");
	}

	// thay thế ký tự khoảng trắng thành ký tự "-"
	public static String spaceToDash(String str) {
		return str.replaceAll("\\s", "-");
	}

	// thay thế ký tự "-" thành ký tự khoảng trắng
	public static String dashToSpace(String str) {
		return str.replaceAll("-", " ");
	}

	// thay thế ký tự khoảng trắng thành ký tự "+"
	public static String spaceToPlus(String str) {
		return str.replaceAll("\\s", "+");
	}

	// thay thế ký tự "+" thành ký tự khoảng trắng
	public static String plusToSpace(String str) {
		return str.replaceAll("+", " ");
	}

	// xu ly form user
	public static User dataProcessingUser(User user) {
		user.setUsername(delSpace(user.getUsername().trim()));
		user.setPassword(delSpace(user.getPassword().trim()));
		user.setFullname(delSpace(user.getFullname().trim()));
		user.setEmail(delSpace(user.getEmail().trim()));
		user.setPhone(delSpace(user.getPhone().trim()));
		return user;
	}

	public static String makeSlug(String title) {
		String slug = Normalizer.normalize(title, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		slug = pattern.matcher(slug).replaceAll("");
		slug = slug.toLowerCase();
		slug = slug.replaceAll("đ", "d");
		slug = slug.replaceAll("([^0-9a-z-\\s])", "");
		slug = slug.replaceAll("[\\s]", "-");
		slug = slug.replaceAll("(-+)", "-");
		slug = slug.replaceAll("^-+", "");
		slug = slug.replaceAll("-+$", "");
		return slug;
	}

}
