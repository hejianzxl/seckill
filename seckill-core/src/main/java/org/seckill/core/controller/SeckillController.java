package org.seckill.core.controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.seckill.core.service.JedisService;
import org.seckill.core.service.SeckillService;
import org.seckill.core.utils.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class SeckillController {
	@Autowired
	private SeckillService seckillService;
	
	@RequestMapping("/seck")
	public void seckill(String uid,String itemId,String activityCode) {
		seckillService.seckill(uid, itemId, System.currentTimeMillis(), activityCode);
	}
	
	@RequestMapping("/seck2")
	public boolean seckill2(String uid,String itemId,String activityCode) {
		return seckillService.seckill2(uid, itemId, System.currentTimeMillis(), activityCode);
	}
	
	@RequestMapping("/test")
	public Object test() {
		Connection conn;
		try {
			conn = SpringContextHolder.getBean(DataSource.class).getConnection();
			System.out.println(conn.getMetaData().getURL());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ok";
	}
}
