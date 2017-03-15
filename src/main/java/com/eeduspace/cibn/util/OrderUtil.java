package com.eeduspace.cibn.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.eeduspace.uuims.comm.util.base.encrypt.Digest;

public class OrderUtil {

	static Date today = new Date();
	static int orderIndex = 0;

	@SuppressWarnings("deprecation")
	private static String getIndex() {

		Date n = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String currTime = outFormat.format(n);

		if (orderIndex > 0) {
			if (n.getYear() == today.getYear() && n.getMonth() == today.getMonth() && n.getDay() == today.getDay()) {
				orderIndex += 1;
			} else {
				today = n;
				orderIndex = 1;
			}
		} else {
			today = n;
			orderIndex = 1;
		}
		if (orderIndex > 999999) {
			orderIndex = 1;
		}
		String indexString = String.format("%s%06d", currTime, orderIndex);
		return indexString;
	}

	/**
	 * 生成订单号
	 * 
	 * @param preFixString
	 * @return
	 */
	public static synchronized String GetOrderNumber(String preFixString) {
		String orderNumberString = preFixString + getIndex();
		return orderNumberString;
	}

	/**
	 * 获取时间戳
	 * 
	 * @return
	 */
	public static String GetTimestamp() {
		return Long.toString(new Date().getTime() / 1000);
	}

	/**
	 * 生成随机数
	 * 
	 * @return
	 */
	public static String CreateNoncestr() {
		Random random = new Random();
		return Digest.md5Digest(String.valueOf(random.nextInt(10000)));
	}
	
	/**
	 * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额
	 * 
	 * @param amount
	 * @return
	 */
	public static String changeY2F(String amount) {
		String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥
																// 或者$的金额
		int index = currency.indexOf(".");
		int length = currency.length();
		Long amLong = 0l;
		if (index == -1) {
			amLong = Long.valueOf(currency + "00");
		} else if (length - index >= 3) {
			amLong = Long.valueOf((currency.substring(0, index + 3)).replace(
					".", ""));
		} else if (length - index == 2) {
			amLong = Long.valueOf((currency.substring(0, index + 2)).replace(
					".", "") + 0);
		} else {
			amLong = Long.valueOf((currency.substring(0, index + 1)).replace(
					".", "") + "00");
		}
		return amLong.toString();
	}
}
