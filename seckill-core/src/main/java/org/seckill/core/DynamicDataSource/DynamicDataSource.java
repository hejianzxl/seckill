package org.seckill.core.DynamicDataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		System.out.println("====================determineCurrentLookupKey==============");
		Object dataSource =  DynamicDataSourceHolder.getDataSouce();
		System.out.println("当前dataSource: " + dataSource);
		return dataSource;
	}
}
