package io.namoosori.travelclub.web.util.helper;

import org.springframework.util.StringUtils;


public class StringUtil extends StringUtils {
	//
	public static boolean isEmpty(String str) {
		//
		return str == null || str.length()==0;
	}
	public static boolean isNullOrEmpty(String str) {
		if (str == null) {
			return true;
		} else if (str.trim().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

}
