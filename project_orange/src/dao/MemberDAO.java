package dao;


import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import service.MyBatisConnector;
import vo.MemberVO;


public class MemberDAO {
	private static MemberDAO single = null;
	
	public static MemberDAO getInstance() {
		if(single == null)
			single = new MemberDAO();
		return single;
	}

	SqlSessionFactory factory = null;
	public MemberDAO() {
		factory = MyBatisConnector.getInstance().getSqlSessionFactory();
	}
	
	//회원리스트
	public List<MemberVO> selectList() {
		List<MemberVO> list = null;
		SqlSession sqlSession = factory.openSession();
		list = sqlSession.selectList("join.member_list");
		sqlSession.close();
		return list;
	}

	//회원등록
	public void insert(MemberVO vo) {
		SqlSession sqlSession = factory.openSession();
		sqlSession.insert("join.member_insert", vo);
		sqlSession.commit();
		sqlSession.close();
	}
	
	//아이디 조회
	public List<MemberVO> selectList(String id) {
		List<MemberVO> list = null;
		SqlSession sqlSession = factory.openSession();
		list = sqlSession.selectList("join.member_list_id", id);
		sqlSession.commit();
		sqlSession.close();
		return list;
	}

	//로그인 일치여부 조회
	public MemberVO selectOne(MemberVO vo) {
		MemberVO res_vo = null;
		SqlSession sqlSession = factory.openSession();
		res_vo = sqlSession.selectOne("join.member_list_login",vo);
		sqlSession.commit();
		sqlSession.close();
		return res_vo;
	}
	
	//회원 삭제
	public void deleteList(int idx) {
		SqlSession sqlSession = factory.openSession();
		sqlSession.delete("join.member_delete",idx);
		sqlSession.commit();
		sqlSession.close();	
	}
	
	
	//페이징
	public int selectCount() {
		SqlSession sqlSession = factory.openSession();
		int count =(int)sqlSession.selectOne("join.member_count");
		sqlSession.close();
		return count;
	}
	public List<MemberVO> select(HashMap<String, Integer> map) {
		List<MemberVO> list = null;
		
		SqlSession sqlSession = factory.openSession();
		
		list = sqlSession.selectList("join.member_list_page", map);
		sqlSession.close();
		
		return list;
	}
}