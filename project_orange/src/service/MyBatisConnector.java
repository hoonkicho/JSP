package service;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisConnector {
	private static MyBatisConnector connector = null;
	
	public static MyBatisConnector getInstance() {
		if(connector== null) {
			connector = new MyBatisConnector();
		}
		return connector;
	}
	// 싱글톤
	SqlSessionFactory factory = null;
	public MyBatisConnector() {
		try {
			Reader reader = Resources.getResourceAsReader("config/mybatis/sqlMapConfig.xml");
			factory = new SqlSessionFactoryBuilder().build(reader);
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public SqlSessionFactory getSqlSessionFactory() {
		return factory;
	}
}
