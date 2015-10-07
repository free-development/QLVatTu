package dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;



import model.CTVatTu;
import model.CongVan;
import model.DonVi;
import model.TrangThai;
import model.NhatKy;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import util.DateUtil;
import util.HibernateUtil;

public class NhatKyDAO {
	
	private SessionFactory template;  
	private Session session;
	public NhatKyDAO() {
		template = HibernateUtil.getSessionFactory();
		session = template.openSession();
	}
	public NhatKy getNhatKy(final int ycId) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(NhatKy.class, "NhatKy");
		cr.add(Restrictions.eq("nkId", ycId));
		ArrayList<NhatKy> nhatKyList = (ArrayList<NhatKy>) cr.list();
		NhatKy nhatKy = null;
		if (nhatKyList.size() > 0)
			nhatKy = nhatKyList.get(0);
		session.getTransaction().commit();
		return nhatKy;
	}
	
	public List<NhatKy> getAllNhatKy() {
		session.beginTransaction();
		List<NhatKy> nhatKyList = (List<NhatKy>) session.createCriteria(NhatKy.class).list();
		session.getTransaction().commit();
		return nhatKyList;
	}
	public void addNhatKy(NhatKy nhatKy){
		session.beginTransaction();
		session.save(nhatKy);
		session.getTransaction().commit();
	}
	
	public void updateNhatKy(NhatKy nhatKy){
		session.beginTransaction();
		session.update(nhatKy);
		session.getTransaction().commit();
	}
	public void deleteNhatKy(int nkId){
		session.beginTransaction();
		// Cach 1 dung giong nhu Statement
		String sql = "detete from NhatKy where nkId = " + nkId ;		
		Query query = session.createQuery(sql);
		query.executeUpdate();
		
		/*
		 Cach 2 dung giong nhu Prepare statement
			 String sql = "update NhatKy set daXoa = 1 where ycId = :ycId";
			Query query = session.createQuery(sql);
			query.setParameter("ycId", 1);
			query.executeUpdate();
			session.getTransaction().commit();
		 */
		
		session.getTransaction().commit();
	}

	public ArrayList<NhatKy> getByMsnv(String msnv) {
			session.beginTransaction();
			Criteria cr = session.createCriteria(NhatKy.class);
			Criterion expMsnv = Restrictions.eq("msnv", msnv);
//			LogicalExpression andExp = Restrictions.and(expCv, xoaYc);
			cr.add(expMsnv);
			cr.addOrder(Order.desc("nkId"));
			ArrayList<NhatKy> nhatKyList = (ArrayList<NhatKy>) cr.list(); 
			session.getTransaction().commit();
			return nhatKyList;
	}
	public long sizeByMsnv(String msnv) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(NhatKy.class);
		Criterion expMsnv = Restrictions.eq("msnv", msnv);
		cr.add(expMsnv);
		long size = (long) cr.setProjection(Projections.rowCount()).uniqueResult();
		session.getTransaction().commit();
		return size;
	}
	
	public ArrayList<NhatKy> getLimitByMsnv(String msnv, int first, int limit) {
		session.beginTransaction();
		
		Criteria cr = session.createCriteria(NhatKy.class);
		Criterion expMsnv = Restrictions.eq("msnv", msnv);
		cr.add(expMsnv);
		cr.addOrder(Order.desc("nkId"));
		cr.setFirstResult(first);
		cr.setMaxResults(limit);
		ArrayList<NhatKy> nhatKyList = (ArrayList<NhatKy>) cr.list();
		session.getTransaction().commit();
		return nhatKyList;
	}
	public void refresh(Object object) {
		session.refresh(object);
	}
	public void addOrUpdateNhatKy(NhatKy NhatKy){
		session.beginTransaction();
		session.saveOrUpdate(NhatKy);
		session.getTransaction().commit();
	}
	
	
	public void close() {
		session.close();
	}
	public void disconnect() {
		session.disconnect();
	}
	public static void main(String[] args) {
//		ArrayList<CongVan> congVanList = new CongVanDAO().getTrangThai("", "", null, null);
//		System.out.println(new NhatKyDAO().size(congVanList));
		NhatKyDAO nhatKyDAO = new NhatKyDAO();
		Date currentDate = DateUtil.convertToSqlDate(new java.util.Date ());
		nhatKyDAO.addNhatKy(new NhatKy("quoioln", "", currentDate, "chia se cong van"));
		nhatKyDAO.disconnect();
		nhatKyDAO.close();
	}
}
