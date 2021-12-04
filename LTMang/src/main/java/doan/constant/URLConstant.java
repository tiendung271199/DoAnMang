package doan.constant;

public class URLConstant {

	public static final String URL_EMPTY = "";
	public static final String URL_INDEX = "";
	public static final String URL_INDEX_2 = "trang-chu";
	public static final String URL_INDEX_PAGINATION = "trang-chu/trang-{page}";
	public static final String URL_SEARCH = "trang-chu/tim-kiem/{keywordURL}";
	public static final String URL_SEARCH_PAGINATION = "trang-chu/tim-kiem/{keywordURL}/trang-{page}";
	public static final String URL_DETAIL = "chi-tiet/{title}-{id}";
	public static final String URL_POST_QUANLY = "tai-khoan/quan-ly-bai-viet";
	public static final String URL_POST_QUANLY_PAGINATION = "tai-khoan/quan-ly-bai-viet/trang-{page}";
	public static final String URL_POST_DETAIL = "tai-khoan/quan-ly-bai-viet/chi-tiet/{title}-{id}";
	public static final String URL_POST_STOP = "tai-khoan/quan-ly-bai-viet/ngung-nhan-ho-tro";
	public static final String URL_POST_ADD = "tai-khoan/dang-bai-viet";
	public static final String URL_POST_UPDATE = "tai-khoan/cap-nhat-bai-viet/{id}";
	public static final String URL_POST_DEL = "tai-khoan/xoa-bai-viet/{id}";

	public static final String URL_POST_REGISTER_CHARITY = "dang-ky-giup-do";
	public static final String URL_POST_CANCEL_REGISTER_CHARITY = "huy-dang-ky-giup-do";
	public static final String URL_POST_CONFIRM_REGISTER_CHARITY = "xac-nhan-dang-ky-giup-do";

	public static final String URL_POST_REGISTER_CHARITY_USER = "tai-khoan/danh-sach-bai-viet-da-dang-ky-giup-do";
	public static final String URL_POST_REGISTER_CHARITY_USER_PAGINATION = "tai-khoan/danh-sach-bai-viet-da-dang-ky-giup-do/trang-{page}";

	public static final String URL_LOGIN = "dang-nhap";
	public static final String URL_LOGOUT = "dang-xuat";
	public static final String URL_PROFILE = "tai-khoan/ho-so-ca-nhan";
	public static final String URL_PROFILE_CHANGE_PASSWORD = "tai-khoan/ho-so-ca-nhan/doi-mat-khau";
	public static final String URL_REGISTER_ACCOUNT = "dang-ky-tai-khoan";
	public static final String URL_INFO_USER = "tai-khoan/thong-tin-nguoi-dung-{id}";
	public static final String URL_INFO_USER_PAGINATION = "tai-khoan/thong-tin-nguoi-dung-{id}/trang-{page}";
	public static final String URL_REVIEWS_USER = "tai-khoan/danh-gia-nguoi-dung/{id}";
	public static final String URL_REVIEWS_XULY = "tai-khoan/xu-ly-danh-gia";

	// ADMIN
	public static final String URL_ADMIN = "admin";
	public static final String URL_ADMIN_LOGIN = "auth/login";

	public static final String URL_ADMIN_POST = "post";
	public static final String URL_ADMIN_POST_PAGINATION = "post/trang-{page}";
	public static final String URL_ADMIN_POST_DETAIL = "post/chi-tiet/{id}";
	public static final String URL_ADMIN_POST_CENSORED = "post/censored";
	public static final String URL_ADMIN_POST_SEARCH = "post/tim-kiem";
	public static final String URL_ADMIN_POST_SEARCH_2 = "post/tim-kiem/title={titleURL}/username={usernameURL}/censored={censoredURL}";
	public static final String URL_ADMIN_POST_SEARCH_PAGINATION_2 = "post/tim-kiem/title={titleURL}/username={usernameURL}/censored={censoredURL}/trang-{page}";

	public static final String URL_ADMIN_USER = "user";
	public static final String URL_ADMIN_USER_PAGINATION = "user/trang-{page}";
	public static final String URL_ADMIN_USER_SEARCH = "user/tim-kiem";
	public static final String URL_ADMIN_USER_SEARCH_2 = "user/tim-kiem/username={usernameURL}/role={roleURL}";
	public static final String URL_ADMIN_USER_SEARCH_PAGINATION_2 = "user/tim-kiem/username={usernameURL}/role={roleURL}/trang-{page}";
	public static final String URL_ADMIN_USER_ADD = "user/add";
	public static final String URL_ADMIN_USER_UPDATE = "user/update/user-{id}";
	public static final String URL_ADMIN_USER_CHANGE_PASSWORD = "user/doi-mat-khau/user-{id}";
	public static final String URL_ADMIN_USER_DEL = "user/del/user-{id}";

	public static final String URL_ADMIN_CHARITY = "charity";
	public static final String URL_ADMIN_CHARITY_PAGINATION = "charity/trang-{page}";

	public static final String URL_ADMIN_REVIEWS = "reviews";
	public static final String URL_ADMIN_REVIEWS_PAGINATION = "reviews/trang-{page}";
	public static final String URL_ADMIN_REVIEWS_DETAIL = "reviews/chi-tiet/{id}";

	// redirect
	public static final String URL_ADMIN_POST_REDIRECT = "admin/post";
	public static final String URL_ADMIN_USER_REDIRECT = "admin/user";
	public static final String URL_ADMIN_CHARITY_REDIRECT = "admin/charity";
	public static final String URL_ADMIN_REVIEWS_REDIRECT = "admin/reviews";

	// AJAX
	public static final String URL_DISTRICT = "address/district";
	public static final String URL_WARD = "address/ward";

	// API
	public static final String URL_USER_LOGIN_API = "api/user/login";
	public static final String URL_QUESTION_API = "api/question/{object}";

	// error
	public static final String URL_ERROR = "error";
	public static final String URL_ERROR_404 = "404";
	public static final String URL_ERROR_403 = "403";
	public static final String URL_ERROR_400 = "400";

}
