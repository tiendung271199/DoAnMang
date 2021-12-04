package doan.constant;

public class DatabaseConstant {

	// role
	public static final int ROLE_ADMIN = 1;
	public static final int ROLE_MOD = 4; // người điều hành
	public static final int ROLE_USER_1 = 2; // Người cần giúp đỡ
	public static final int ROLE_USER_2 = 3; // Người giúp đỡ

	// censored post
	public static final int POST_CENSORED = 1; // đã kiểm duyệt
	public static final int POST_NON_CENSORED = 0; // chưa kiểm duyệt

	// status post
	public static final int POST_STATUS_1 = 1; // vẫn còn nhận hỗ trợ
	public static final int POST_STATUS_2 = 0; // không còn nhận hỗ trợ

	// status confirm register charity
	public static final int REGISTER_CONFIRM = 1; // đã xác nhận => liên hệ trực tiếp để giúp đỡ
	public static final int REGISTER_NON_CONFIRM = 0; // chưa được xác nhận bởi người đăng bài

}
