package dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import service.MyBatisConnector;

import vo.PlaylistVO;

public class PlaylistDAO {
	private static PlaylistDAO single = null;
	
	public static PlaylistDAO getInstance() {
		if(single == null)
			single = new PlaylistDAO();
		return single;
	}
	
	SqlSessionFactory factory = null;
	public PlaylistDAO() {
		factory = MyBatisConnector.getInstance().getSqlSessionFactory();
	}
	
	// 플레이리스트 존재여부
	public PlaylistVO selectOne(PlaylistVO vo) {
		PlaylistVO res_vo = null;
		SqlSession sqlSession = factory.openSession();
		res_vo = sqlSession.selectOne("playlist.playlist_one", vo);
		sqlSession.close();
		return res_vo;
	}
	
	// 플레이리스트 삽입
	public void insert(PlaylistVO vo) {
		SqlSession sqlSession = factory.openSession();
		sqlSession.insert("playlist.playlist_insert",vo);
		sqlSession.commit();
		sqlSession.close();
	}
	
	// 해당 회원의 플레이리스트
	public List<PlaylistVO> select(int memberidx) {
		List<PlaylistVO> list = null;
		
		SqlSession sqlSession = factory.openSession();
		list = sqlSession.selectList("playlist.playlist_list",memberidx);
		sqlSession.close();
		
		return list;
	}

	// 플레이리스트 삭제
	public void deleteList(int resultidx) {
		SqlSession sqlSession = factory.openSession();
		sqlSession.delete("playlist.playlist_delete",resultidx);
		sqlSession.commit();
		sqlSession.close();	
	}
	
	
	// 페이징
	public int myselectCount(int idx) {
		SqlSession sqlSession = factory.openSession();
		int count =(int)sqlSession.selectOne("playlist.myplaylist_count",idx);
		sqlSession.close();
		return count;
	}
	public List<PlaylistVO> myselect(HashMap<String, Integer> map) {
		List<PlaylistVO> list = null;
		
		SqlSession sqlSession = factory.openSession();
		
		list = sqlSession.selectList("playlist.myplaylist_list_page", map);
		sqlSession.close();
		
		return list;
	}
}
