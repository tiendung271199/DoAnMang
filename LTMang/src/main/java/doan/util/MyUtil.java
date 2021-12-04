package doan.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import doan.constant.GlobalConstant;

public class MyUtil {

	public static String getIpAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return GlobalConstant.EMPTY;
	}

}
