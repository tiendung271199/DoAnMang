package doan.constant;

public class RegexConstant {

	public static final String REGEX_PHONE = "(0[3|5|7|8|9])+([0-9]{8})";

	public static final String REGEX_USERNAME = "[A-Za-z0-9]{6,20}";

	public static final String REGEX_PASSWORD = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^\\w\\s])(?=\\S+$).{6,20}";

}
