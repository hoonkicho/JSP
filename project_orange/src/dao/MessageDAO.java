package dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import service.MyBatisConnector;
import vo.MessageVO;

public class MessageDAO {
private static MessageDAO single = null;
	
	public static MessageDAO getInstance() {
		if(single == null)
			single = new MessageDAO();
		return single;
	}
	SqlSessionFactory factory = null;
	public MessageDAO() {
		factory = MyBatisConnector.getInstance().getSqlSessionFactory();
	}
	
	//메세지 삽입
	public void insert(MessageVO vo) {
		SqlSession sqlSession = factory.openSession();
		sqlSession.insert("message.message_insert", vo);
		sqlSession.commit();
		sqlSession.close();
	}
	
	
	public MessageVO selectOne(MessageVO vo) {
		MessageVO res_vo = null;
		SqlSession sqlSession = factory.openSession();
		res_vo = sqlSession.selectOne("message.message_one", vo);
		sqlSession.close();
		return res_vo;
	}
	
	public List<MessageVO> select(String receivedId) {
		List<MessageVO> list = null;
		SqlSession sqlSession = factory.openSession();
		list = sqlSession.selectList("message.message_list",receivedId);
		sqlSession.close();
		return list;
	}
	
	//댓글
	public int deleteList(int idx) {
		int res = 0;
		SqlSession sqlSession = factory.openSession();
		res = sqlSession.delete("message.message_delete",idx);
		sqlSession.commit();
		sqlSession.close();	
		
		return res;
	}
	
	//페이징
	public int myselectCount(String id) {
		SqlSession sqlSession = factory.openSession();
		int count =(int)sqlSession.selectOne("message.message_count",id);
		sqlSession.close();
		return count;
	}
	public List<MessageVO> myselect(HashMap<String, String> map) {
		List<MessageVO> list = null;
		
		SqlSession sqlSession = factory.openSession();
		
		list = sqlSession.selectList("message.message_list_page", map);
		sqlSession.close();
		
		return list;
	}
}
