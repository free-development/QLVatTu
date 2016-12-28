package dao;

import java.util.ArrayList;
import java.util.List;

import model.ChatLuong;
import model.ChucDanh;
import model.MucDich;
import model.NoiSanXuat;
import model.YeuCau;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import util.HibernateUtil;

public class MucDichDAO {
	
	private SessionFactory template;  
	private Session session;
	public MucDichDAO() {
		template = HibernateUtil.getSessionFactory();
		session = template.openSession();
	}
	public MucDich getMucDich(final String mdMa) {
		session.beginTransaction();
		MucDich mucDich = (MucDich) session.get(MucDich.class, mdMa);
		session.getTransaction().commit();
		return mucDich;
	}
	public List<MucDich> getAllMucDich() {
		session.beginTransaction();
		Criteria cr = session.createCriteria(MucDich.class);
		Criterion xoaMd = Restrictions.eq("daXoa", 0);
		cr.add(xoaMd);
		ArrayList<MucDich> mucDichList = (ArrayList<MucDich>) cr.list(); 
		session.getTransaction().commit();
		return mucDichList;
	}
	public void addMucDich(MucDich mucDich){
		session.beginTransaction();
		session.save(mucDich);
		session.getTransaction().commit();
	}
	public void addOrUpdateMucDich(MucDich mucDich){
		session.beginTransaction();
		session.saveOrUpdate(mucDich);
		session.getTransaction().commit();
	}
	public void updateMucDich(MucDich mucDich){
		session.beginTransaction();
		session.update(mucDich);
		session.getTransaction().commit();
	}
	public void deleteMucDich(String mdMa){
		session.beginTransaction();
		String sql = "update MucDich  set daXoa = 1 where mdMa = '" + mdMa + "'";	
		Query query = session.createQuery(sql);
		query.executeUpdate();
		session.getTransaction().commit();
	}
	public int getMucDich1(final String mdMa)
	{
		session.beginTransaction();
		Criteria cr = session.createCriteria(MucDich.class);
		Criterion expMdMa=Restrictions.eq("mdMa", mdMa);
		cr.add(expMdMa);
		int l = cr.list().size();
		session.getTransaction().commit();
		return l;
		
	}
	public List<MucDich> limit(int first, int limit) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(MucDich.class);
		Criterion xoaCd = Restrictions.eq("daXoa", 0);
//		Criterion limitRow = Restrictions.
		cr.add(xoaCd);
		cr.setFirstResult(first);
		cr.setMaxResults(limit);
		ArrayList<MucDich> mucDichList = (ArrayList<MucDich>) cr.list(); 
		session.getTransaction().commit();
		return mucDichList;
	}
	public long size() {
		session.beginTransaction();
		String sql = "select count(mdMa) from MucDich where daXoa = 0";
		Query query =  session.createQuery(sql);
		long size = (long) query.list().get(0);
		session.getTransaction().commit();
		return size;
		
	}
	public void close() {
		if(session.isOpen())
			session.close();
	}
	public void disconnect() {
		if (session.isConnected())
			session.disconnect();
	}

}
