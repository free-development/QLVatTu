package dao;


import java.util.ArrayList;
import java.util.List;

import model.DonViTinh;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import util.HibernateUtil;

public class DonViTinhDAO {
	
	private SessionFactory template;  
	private Session session;
	public DonViTinhDAO() {
		template = HibernateUtil.getSessionFactory();
		session = template.openSession();
	}
	// trong day phai la kieu int
	public DonViTinh getDonViTinh(final int dvtId) {
		session.beginTransaction();
		
		DonViTinh DonViTinh = (DonViTinh) session.get(DonViTinh.class, dvtId);
//		session.
		
		session.getTransaction().commit();
		return DonViTinh;
	}
	public DonViTinh getDonViTinhByTen(final String dvtTen) {
		session.beginTransaction();
		/*
		String sql = "from DonViTinh where LOWER(dvtTen) = :dvtTen";
		Query query = session.createQuery(sql);
		query.setParameter("dvtTen", dvtTen.toLowerCase());
		
		
		ArrayList<DonViTinh> list = (ArrayList<DonViTinh>) query.list();
		*/
		Criteria cr = session.createCriteria(DonViTinh.class);
		cr.add(Restrictions.eq("dvtTen", dvtTen));
		ArrayList<DonViTinh> list = (ArrayList<DonViTinh>) cr.list();
		DonViTinh dvt = null;
		if(list.size() != 0)
			dvt = list.get(0);
		session.getTransaction().commit();
		return dvt;
	}
	public List<DonViTinh> getAllDonViTinh() {
		session.beginTransaction();
		Criteria cr = session.createCriteria(DonViTinh.class);
		Criterion xoaCd = Restrictions.eq("daXoa", 0);
		cr.add(xoaCd);
		ArrayList<DonViTinh> DonViTinhList = (ArrayList<DonViTinh>) cr.list(); 
		session.getTransaction().commit();
		return DonViTinhList;
	}
	
	public List<DonViTinh> limit(int first, int limit) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(DonViTinh.class);
		Criterion xoaCd = Restrictions.eq("daXoa", 0);
//		Criterion limitRow = Restrictions.
		cr.add(xoaCd);
		cr.setFirstResult(first);
		cr.setMaxResults(limit);
		ArrayList<DonViTinh> DonViTinhList = (ArrayList<DonViTinh>) cr.list(); 
		session.getTransaction().commit();
		return DonViTinhList;
	}
	public long size() {
		session.beginTransaction();
		String sql = "select count(dvtId) from DonViTinh where daXoa = 0";
		Query query =  session.createQuery(sql);
		long size = (long) query.list().get(0);
		session.getTransaction().commit();
		return size;
		
	}
	
	public void addDonViTinh(DonViTinh dvt){
		session.beginTransaction();
		session.save(dvt);
		session.getTransaction().commit();
	}
	public void updateDonViTinh(DonViTinh dvt){
		session.beginTransaction();
		session.update(dvt);
		session.getTransaction().commit();
	}
	public void addOrUpdateDonViTinh(DonViTinh dvt){
		session.beginTransaction();
		session.saveOrUpdate(dvt);
		session.getTransaction().commit();
	}
	public int lastInsertId() {
		session.beginTransaction();
		String sql = "select MAX(dvtId) from DonViTinh";
		int dvtId = (int) session.createQuery(sql).list().get(0); 
		session.getTransaction().commit();
		return dvtId;
	}
	public void deleteDonViTinh(int dvtId){
		session.beginTransaction();
		String sql = "update DonViTinh set daXoa = 1 where dvtId = " + dvtId;		
		Query query = session.createQuery(sql);
		query.executeUpdate();
		session.getTransaction().commit();
	}
	public void deleteDonViTinhTen(String dvtTen){
		session.beginTransaction();
		String sql = "update DonViTinh set daXoa = 1 where dvtTen = '" + dvtTen+ "'";		
		Query query = session.createQuery(sql);
		query.executeUpdate();
		session.getTransaction().commit();
	}
	public int getDonViTinhDAO(final int dvtId) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(DonViTinh.class);
		Criterion expdvtId = Restrictions.eq("dvtId", dvtId);
		cr.add(expdvtId);
		int l =  cr.list().size();
		session.getTransaction().commit();
		return l;
	}
	public void close() {
		if(session.isOpen())
			session.close();
	}
	public void disconnect() {
		if (session.isConnected())
			session.disconnect();
	}
	public int getLastDvtId() {
		session.beginTransaction();
		Criteria cr =  session.createCriteria(DonViTinh.class).setProjection(Projections.max("dvtId"));// max("ctvtId"));
		Integer id =  (Integer) cr.list().get(0);
		session.getTransaction().commit();
		return id;
	}
}
