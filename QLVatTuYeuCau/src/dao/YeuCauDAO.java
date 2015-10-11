package dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;



import model.CTVatTu;
import model.CongVan;
import model.DonVi;
import model.TrangThai;
import model.YeuCau;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import util.DateUtil;
import util.HibernateUtil;

public class YeuCauDAO {
	
	private SessionFactory template;  
	private Session session;
	public YeuCauDAO() {
		template = HibernateUtil.getSessionFactory();
		session = template.openSession();
	}
	public YeuCau getYeuCau(final int ycId) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(YeuCau.class, "yeuCau");
//		cr.createAlias("yeuCau.ctVatTu", "ctVatTu");
//		cr.createAlias("ctVatTu.vatTu", "vatTu");
//		cr.createAlias("vatTu.dvt", "dvt");
		cr.add(Restrictions.eq("ycId", ycId));
		ArrayList<YeuCau> yeuCauList = (ArrayList<YeuCau>) cr.list();
		YeuCau yeuCau = null;
		if (yeuCauList.size() > 0)
			yeuCau = yeuCauList.get(0);
		session.getTransaction().commit();
		return yeuCau;
	}
	public YeuCau getByYcId(final int ycId) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(YeuCau.class);
//		cr.createAlias("yeuCau.ctVatTu", "ctVatTu");
//		cr.createAlias("ctVatTu.vatTu", "vatTu");
//		cr.createAlias("vatTu.dvt", "dvt");
		cr.add(Restrictions.eq("ycId", ycId));
		ArrayList<YeuCau> yeuCauList = (ArrayList<YeuCau>) cr.list();
		YeuCau yeuCau = null;
		if (yeuCauList.size() > 0)
			yeuCau = yeuCauList.get(0);
		session.getTransaction().commit();
		return yeuCau;
	}
	public List<YeuCau> getAllYeuCau() {
		session.beginTransaction();
		List<YeuCau> yeuCauList = (List<YeuCau>) session.createCriteria(YeuCau.class).list();
		session.getTransaction().commit();
		return yeuCauList;
	}
	public void addYeuCau(YeuCau yeuCau){
		session.beginTransaction();
		session.save(yeuCau);
		session.getTransaction().commit();
	}
	
	public void updateYeuCau(YeuCau yeuCau){
		session.beginTransaction();
		session.update(yeuCau);
		session.getTransaction().commit();
		session.clear();
	}
	public void deleteYeuCau(int ycId){
		session.beginTransaction();
		// Cach 1 dung giong nhu Statement
		String sql = "update YeuCau set daXoa = 1 where ycId = " + ycId ;		
		Query query = session.createQuery(sql);
		query.executeUpdate();
		
		/*
		 Cach 2 dung giong nhu Prepare statement
			 String sql = "update YeuCau set daXoa = 1 where ycId = :ycId";
			Query query = session.createQuery(sql);
			query.setParameter("ycId", 1);
			query.executeUpdate();
			session.getTransaction().commit();
		 */
		
		session.getTransaction().commit();
	}

	public ArrayList<YeuCau> getByCvId(int cvId) {
			session.beginTransaction();
			Criteria cr = session.createCriteria(YeuCau.class);
			Criterion expCv = Restrictions.eq("cvId", cvId);
			Criterion xoaYc = Restrictions.eq("daXoa", 0);
//			LogicalExpression andExp = Restrictions.and(expCv, xoaYc);
			cr.add(expCv);
			cr.add(xoaYc);
			ArrayList<YeuCau> yeuCauList = (ArrayList<YeuCau>) cr.list(); 
			session.getTransaction().commit();
			return yeuCauList;
	}
	public long sizeByCongVan(int cvId) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(YeuCau.class);
		Criterion expCv = Restrictions.eq("cvId", cvId);
		Criterion xoaYc = Restrictions.eq("daXoa", 0);
		LogicalExpression andExp = Restrictions.and(expCv, xoaYc);
		cr.add(andExp);
		long size = (long) cr.setProjection(Projections.rowCount()).uniqueResult();
		session.getTransaction().commit();
		return size;
}
	public ArrayList<YeuCau> getVTThieu() {
		session.beginTransaction();
		Criteria cr = session.createCriteria(YeuCau.class);
		Criterion xoaYc = Restrictions.eq("daXoa", 0);
		cr.add(xoaYc);
		ArrayList<YeuCau> yeuCauList = (ArrayList<YeuCau>) cr.list(); 
		session.getTransaction().commit();
		return yeuCauList;
	}
	
	public List<YeuCau> limitByIdCv(int cvId, int first, int limit) {
		/*
		session.beginTransaction();
		Criteria cr = session.createCriteria(YeuCau.class);
		Criterion xoaCd = Restrictions.eq("daXoa", 0);
//		Criterion limitRow = Restrictions.
		cr.add(xoaCd);
		cr.setFirstResult(first);
		cr.setMaxResults(limit);
		ArrayList<YeuCau> yeuCauList = (ArrayList<YeuCau>) cr.list(); 
		session.getTransaction().commit();
		return yeuCauList;
		*/
		session.beginTransaction();
		Criteria cr = session.createCriteria(YeuCau.class, "yeuCau");
//		cr.createAlias("yeuCau.ctVatTu", "ctVatTu");
//		cr.createAlias("ctVatTu.vatTu", "vatTu");
//		cr.createAlias("vatTu.dvt", "dvt");
		Criterion expCv = Restrictions.eq("yeuCau.cvId", cvId);
		Criterion xoaYc = Restrictions.eq("yeuCau.daXoa", 0);
		LogicalExpression andExp = Restrictions.and(expCv, xoaYc);
		cr.add(andExp);
		cr.setFirstResult(first); 
		cr.setMaxResults(limit);
		ArrayList<YeuCau> yeuCauList = (ArrayList<YeuCau>) cr.list(); 
		session.getTransaction().commit();
		return yeuCauList;
	}
	
	public long size (ArrayList<CongVan> congVanList) {
		long size = 0;
		for (CongVan congVan : congVanList) {
			long tempSize = sizeByCongVan(congVan.getCvId());
			size += tempSize;
		}
		return size;
	}
	public ArrayList<YeuCau> getByDaXoa() {
		session.beginTransaction();
		Criteria cr = session.createCriteria(YeuCau.class);
		Criterion xoaYC = Restrictions.eq("daXoa", 0);
		cr.add(xoaYC);
		ArrayList<YeuCau> yeuCauList = (ArrayList<YeuCau>) cr.list(); 
		session.getTransaction().commit();
		return yeuCauList;
	}
	
	public void addOrUpdateYeuCau(YeuCau yeuCau){
		session.beginTransaction();
		session.saveOrUpdate(yeuCau);
		session.getTransaction().commit();
	}
	
	// get so luong yeu by 
	public YeuCau getYeuCau(final int cvId, final int ctvtId) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(YeuCau.class);
//		cr.createAlias("yeuCau.ctVatTu", "ctVatTu");
//		cr.createAlias("ctVatTu.vatTu", "vatTu");
//		cr.createAlias("vatTu.dvt", "dvt");
		Criterion expCv = Restrictions.eq("cvId", cvId);
		Criterion expCtvt = Restrictions.eq("ctvtId", ctvtId);
//		Criterion expDaXoa= Restrictions.eq("daXoa", 0);
		cr.add(expCv);
		cr.add(expCtvt);
//		cr.add(expDaXoa);
		ArrayList<YeuCau> ycList  = (ArrayList<YeuCau>) cr.list();
		YeuCau yeuCau = null;
		if (ycList.size() != 0)
			yeuCau = (YeuCau) cr.list().get(0);
		
		session.getTransaction().commit();
		return yeuCau;
	}
	
	
	
	public int getLastInsert() {
		session.beginTransaction();
		Criteria cr =  session.createCriteria(YeuCau.class).setProjection(Projections.max("ycId"));
		Integer idOld =  (Integer) cr.list().get(0);
		int id = 0;
		if (idOld != null)
			id += idOld + 1;
		else
			id++;
		
		session.getTransaction().commit();
		return id;
	}
	
	public YeuCau addSoLuong(final int cvId, final int ctvtId, int soLuong) {
		YeuCau yeuCau = getYeuCau(cvId, ctvtId);
		if (yeuCau == null) {
			yeuCau = new YeuCau(cvId, ctvtId, soLuong, 0,0);
			addYeuCau(yeuCau);
			int ycId = getLastInsert()-1;
			yeuCau = new YeuCauDAO().getByYcId(ycId);
		}
		else if (yeuCau.getDaXoa() == 0){
			int soLuongOld = yeuCau.getYcSoLuong();
			soLuong += soLuongOld;
			yeuCau.setYcSoLuong(soLuong);
			yeuCau.setDaXoa(0);
			updateYeuCau(yeuCau);
		}
		else {
			yeuCau.setYcSoLuong(soLuong);
			yeuCau.setDaXoa(0);
			yeuCau.setCapSoLuong(0);
			updateYeuCau(yeuCau);
		}
		return yeuCau;
	}
	// function cap vat tu
	public YeuCau capVatTu(YeuCau yeuCau, final int soLuongCap) {
		int sl = yeuCau.getCapSoLuong();
		if(yeuCau.getDaXoa() == 0) {
			sl += soLuongCap;
		}
		yeuCau.setCapSoLuong(sl);
//		CTVatTu ctvt = yeuCau.getCtVatTu();
//		ctvt.setSoLuongTon(ctvt.getSoLuongTon() - soLuongCap);
//		yeuCau.setCtVatTu(ctvt);
		updateYeuCau(yeuCau);
//		session.refresh(YeuCau.class);
		return yeuCau;
	}
	public void refresh(Object object) {
		session.refresh(object);
	}
	// check before update so luong yeu cau
	public boolean checkUpdateSoLuong(final int ycId, int soLuong) {
		YeuCau yeuCau = getYeuCau(ycId);
		return (yeuCau.getCapSoLuong() <= soLuong);
	}
	// check before update so luong yeu cau
	public int checkCapSoLuong(final int ycId, int soLuong) {
		YeuCau yeuCau = getYeuCau(ycId);
		int capSoLuong = yeuCau.getCapSoLuong() + soLuong;
		int ycSoLuong = yeuCau.getYcSoLuong();
		int temp = ycSoLuong - capSoLuong;
		CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
		CTVatTu ctVatTu = ctVatTuDAO.getCTVatTu(yeuCau.getCtvtId());
		int soLuongTon = ctVatTu.getSoLuongTon();
		if (soLuongTon < soLuong)
			return -2;
		if (temp >= 0)
			return 1;
		
		return -1;
	}
	
	public void close() {
		session.close();
	}
	public void disconnect() {
		session.disconnect();
	}
	public static void main(String[] args) {
//		ArrayList<CongVan> congVanList = new CongVanDAO().getTrangThai("", "", null, null);
//		System.out.println(new YeuCauDAO().size(congVanList));
//		YeuCauDAO yeuCauDAO = new YeuCauDAO();
//		YeuCau yeuCau = yeuCauDAO.getYeuCau(2,1);
//		System.out.println(yeuCau.getCtVatTu().getVatTu().getDvt().getDvtTen());
	}
}
