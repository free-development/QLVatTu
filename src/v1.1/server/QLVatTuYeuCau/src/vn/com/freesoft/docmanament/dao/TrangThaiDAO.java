package vn.com.freesoft.docmanament.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import vn.com.freesoft.docmanament.entity.TrangThai;
import vn.com.freesoft.docmanament.util.HibernateUtil;

public class TrangThaiDAO {

	private SessionFactory template;
	private Session session;

	public TrangThaiDAO() {
		template = HibernateUtil.getSessionFactory();
		session = template.openSession();
	}

	public TrangThai getTrangThai(final String ttMa) {
		session.beginTransaction();
		TrangThai trangThai = (TrangThai) session.get(TrangThai.class, ttMa);
		session.getTransaction().commit();
		return trangThai;
	}

	public List<TrangThai> getAllTrangThai() {
		session.beginTransaction();
		List<TrangThai> trangThaiList = (List<TrangThai>) session.createCriteria(TrangThai.class).list();
		session.getTransaction().commit();
		return trangThaiList;
	}

	public void addTrangThai(TrangThai trangThai) {
		session.beginTransaction();
		session.save(trangThai);
		session.getTransaction().commit();
	}

	public void updateTrangThai(TrangThai trangThai) {
		session.beginTransaction();
		session.update(trangThai);
		session.getTransaction().commit();
	}

	public void deleteTrangThai(TrangThai trangThai) {
		session.beginTransaction();
		session.delete(trangThai);
		session.getTransaction().commit();
	}

	public void close() {
		if (session.isOpen())
			session.close();
	}

	public void disconnect() {
		if (session.isConnected())
			session.disconnect();
	}

}
