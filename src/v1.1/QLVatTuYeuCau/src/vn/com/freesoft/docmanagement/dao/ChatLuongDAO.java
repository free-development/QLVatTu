package vn.com.freesoft.docmanagement.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import vn.com.freesoft.docmanagement.entity.ChatLuong;

public class ChatLuongDAO {

	@Autowired
	private Session session;

	public ChatLuong getChatLuong(final String clMa) {
		session.beginTransaction();

		ChatLuong chatLuong = (ChatLuong) session.get(ChatLuong.class, clMa);
		// session.

		session.getTransaction().commit();
		return chatLuong;
	}

	public List<ChatLuong> getAllChatLuong() {
		session.beginTransaction();
		Criteria cr = session.createCriteria(ChatLuong.class);
		Criterion xoaCd = Restrictions.eq("daXoa", 0);
		cr.add(xoaCd);
		ArrayList<ChatLuong> chatLuongList = (ArrayList<ChatLuong>) cr.list();
		session.getTransaction().commit();
		return chatLuongList;
	}

	public List<ChatLuong> limit(int first, int limit) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(ChatLuong.class);
		Criterion xoaCd = Restrictions.eq("daXoa", 0);
		// Criterion limitRow = Restrictions.
		cr.add(xoaCd);
		cr.setFirstResult(first);
		cr.setMaxResults(limit);
		ArrayList<ChatLuong> chatLuongList = (ArrayList<ChatLuong>) cr.list();
		session.getTransaction().commit();
		return chatLuongList;
	}

	public long size() {
		session.beginTransaction();
		String sql = "select count(clMa) from ChatLuong where daXoa = 0";
		Query query = session.createQuery(sql);
		long size = (long) query.list().get(0);
		session.getTransaction().commit();
		return size;

	}

	public void addChatLuong(ChatLuong chatLuong) {
		session.beginTransaction();
		session.save(chatLuong);
		session.getTransaction().commit();
	}

	public void addOrUpdateChatLuong(ChatLuong chatLuong) {
		session.beginTransaction();
		session.saveOrUpdate(chatLuong);
		session.getTransaction().commit();
	}

	public void updateChatLuong(ChatLuong chatLuong) {
		session.beginTransaction();
		session.update(chatLuong);
		session.getTransaction().commit();
	}

	public void deleteChatLuong(String clMa) {
		session.beginTransaction();
		String sql = "update ChatLuong set daXoa = 1 where clMa = '" + clMa + "'";
		Query query = session.createQuery(sql);
		query.executeUpdate();
		session.getTransaction().commit();
	}

	public int getChatLuong1(final String clMa) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(ChatLuong.class);
		Criterion expClMa = Restrictions.eq("clMa", clMa);
		cr.add(expClMa);
		int l = cr.list().size();
		session.getTransaction().commit();
		return l;

	}

	public void close() {
		session.close();
	}

	public void disconnect() {
		session.disconnect();
	}

	public ChatLuong getByNameCl(String clTen) {
		session.beginTransaction();
		String sql = "from ChatLuong where LOWER(clTen) = :clTen";
		Query query = session.createQuery(sql);
		query.setParameter("clTen", clTen.toLowerCase());
		ArrayList<ChatLuong> list = (ArrayList<ChatLuong>) query.list();
		ChatLuong cl = null;
		if (list.size() != 0)
			cl = list.get(0);
		session.getTransaction().commit();
		return cl;
	}
}
