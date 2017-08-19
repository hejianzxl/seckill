package org.seckill.core.constant;

public class ConstantKey {

	private static final String	PREFIX_HEADER	= "SECKILL";
	
	private static final String	PREFIX_LCOK	= "LCOK";

	private static final String	PREFIX			= "@";

	public static String getKey(String code) {
		return PREFIX_HEADER + code;
	}

	public static String getSeckillKey(long uid, long itemId) {
		return PREFIX_HEADER + PREFIX +  uid + PREFIX + itemId;
	}

	public static String getUserSeckillKey() {
		return PREFIX_HEADER + PREFIX + "QUEUEING";
	}
	
	/**
	 * 用户参与记录
	 * @param uid
	 * @param itemId
	 * @return
	 */
	public static String getTakeInKey(String uid, String itemId,String activityCode) {
		return PREFIX_HEADER + PREFIX + uid + PREFIX + activityCode;
	}
	
	/**
	 * uid、itemId分布式锁 key
	 * @param uid
	 * @param itemId
	 * @return
	 */
	public static String getLock(long uid, String itemId) {
		return  PREFIX_LCOK + PREFIX + uid + PREFIX + itemId;
	}

	public static String getOrderSequence(String userId) {
		return PREFIX_HEADER + PREFIX + userId + PREFIX + "Sequence";
	}

	public static String getUserSeckillKey(String activityCode, String itemId) {
		// TODO Auto-generated method stub
		return null;
	}
}
