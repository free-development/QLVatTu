package vn.com.freesoft.docmanament.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import vn.com.freesoft.docmanament.entity.CTVatTu;
import vn.com.freesoft.docmanament.entity.CongVan;
import vn.com.freesoft.docmanament.entity.YeuCau;
import vn.com.freesoft.docmanament.util.HibernateUtil;

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

	public void addYeuCau(YeuCau yeuCau) {
		session.beginTransaction();
		session.save(yeuCau);
		session.getTransaction().commit();
	}

	public void updateYeuCau(YeuCau yeuCau) {
		session.beginTransaction();
		session.update(yeuCau);
		session.getTransaction().commit();
		session.clear();
	}

	public void deleteYeuCau(int ycId) {
		session.beginTransaction();
		// Cach 1 dung giong nhu Statement
		String sql = "update YeuCau set daXoa = 1 where ycId = " + ycId;
		Query query = session.createQuery(sql);
		query.executeUpdate();

		/*
		 * Cach 2 dung giong nhu Prepare statement String sql =
		 * "update YeuCau set daXoa = 1 where ycId = :ycId"; Query query =
		 * session.createQuery(sql); query.setParameter("ycId", 1);
		 * query.executeUpdate(); session.getTransaction().commit();
		 */

		session.getTransaction().commit();
	}

	public ArrayList<YeuCau> getByCvId(int cvId) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(YeuCau.class);
		Criterion expCv = Restrictions.eq("cvId", cvId);
		Criterion xoaYc = Restrictions.eq("daXoa", 0);
		// Criterion soLuong = Restri
		// LogicalExpression andExp = Restrictions.and(expCv, xoaYc);
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
		 * session.beginTransaction(); Criteria cr =
		 * session.createCriteria(YeuCau.class); Criterion xoaCd =
		 * Restrictions.eq("daXoa", 0); // Criterion limitRow = Restrictions.
		 * cr.add(xoaCd); cr.setFirstResult(first); cr.setMaxResults(limit);
		 * ArrayList<YeuCau> yeuCauList = (ArrayList<YeuCau>) cr.list();
		 * session.getTransaction().commit(); return yeuCauList;
		 */
		session.beginTransaction();
		Criteria cr = session.createCriteria(YeuCau.class, "yeuCau");
		// cr.createAlias("yeuCau.ctVatTu", "ctVatTu");
		// cr.createAlias("ctVatTu.vatTu", "vatTu");
		// cr.createAlias("vatTu.dvt", "dvt");
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

	public long size(ArrayList<CongVan> congVanList) {
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

	public void addOrUpdateYeuCau(YeuCau yeuCau) {
		session.beginTransaction();
		session.saveOrUpdate(yeuCau);
		session.getTransaction().commit();
	}

	// get so luong yeu by
	public YeuCau getYeuCau(final int cvId, final int ctvtId) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(YeuCau.class);
		// cr.createAlias("yeuCau.ctVatTu", "ctVatTu");
		// cr.createAlias("ctVatTu.vatTu", "vatTu");
		// cr.createAlias("vatTu.dvt", "dvt");
		Criterion expCv = Restrictions.eq("cvId", cvId);
		Criterion expCtvt = Restrictions.eq("ctvtId", ctvtId);
		// Criterion expDaXoa= Restrictions.eq("daXoa", 0);
		cr.add(expCv);
		cr.add(expCtvt);
		// cr.add(expDaXoa);
		ArrayList<YeuCau> ycList = (ArrayList<YeuCau>) cr.list();
		YeuCau yeuCau = null;
		if (ycList.size() != 0)
			yeuCau = (YeuCau) cr.list().get(0);

		session.getTransaction().commit();
		return yeuCau;
	}

	public int getLastInsert() {
		session.beginTransaction();
		Criteria cr = session.createCriteria(YeuCau.class).setProjection(Projections.max("ycId"));
		Integer idOld = (Integer) cr.list().get(0);
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
			yeuCau = new YeuCau(cvId, ctvtId, soLuong, 0, 0);
			addYeuCau(yeuCau);
			int ycId = getLastInsert() - 1;
			yeuCau = new YeuCauDAO().getByYcId(ycId);
		} else if (yeuCau.getDaXoa() == 0) {
			int soLuongOld = yeuCau.getYcSoLuong();
			soLuong += soLuongOld;
			yeuCau.setYcSoLuong(soLuong);
			yeuCau.setDaXoa(0);
			updateYeuCau(yeuCau);
		} else {
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
		if (yeuCau.getDaXoa() == 0) {
			sl += soLuongCap;
		}
		yeuCau.setCapSoLuong(sl);
		// CTVatTu ctvt = yeuCau.getCtVatTu();
		// ctvt.setSoLuongTon(ctvt.getSoLuongTon() - soLuongCap);
		// yeuCau.setCtVatTu(ctvt);
		updateYeuCau(yeuCau);
		// session.refresh(YeuCau.class);
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
		else if (temp > 0)
			return 1;
		if (temp == 0)
			return 0;
		// if (capSoLuong == ycSoLuong && soLuongTon >= soLuong)
		// return 0;
		return -1;
	}

	public ArrayList<CTVatTu> distinctCtvt(HashMap<String, Object> conditions, String msnv) {
		session.beginTransaction();
		String joinVtCv = "";
		String conditionMsnv = "";
		if (msnv != null) {
			joinVtCv = " join VTCONGVAN d ";
			conditionMsnv = " d.cvId = b.cvId and d.msnv = '" + msnv + "' and ";
		}
		// String msnv = " join VTCONGVAN d ";
		ArrayList<CTVatTu> ctVatTuList = new ArrayList<CTVatTu>();
		String sql = "select c.* from CTVATTU c join YEUCAU a join CONGVAN b " + joinVtCv + " where " + conditionMsnv
				+ " a.cvId = b.cvId and c.ctvtId = a.ctvtId and a.ycSoLuong > a.capSoLuong and a.daXoa = 0 ";
		SQLQuery query;
		ArrayList<Integer> ctvtIdList = new ArrayList<Integer>();
		if (conditions != null) {
			for (String key : conditions.keySet()) {
				if (key.equalsIgnoreCase("leCvNgayNhan"))
					sql += " and b.cvNgayNhan <= :" + key;
				else if (key.equalsIgnoreCase("geCvNgayNhan"))
					sql += " and b.cvNgayNhan >= :" + key;
				else if (key.equalsIgnoreCase("leCvNgayDi"))
					sql += " and b.cvNgayDi <= :" + key;
				else if (key.equalsIgnoreCase("leCvNgayDi"))
					sql += " and b.cvNgayDi >= :" + key;
			}
			query = session.createSQLQuery(sql);

			for (String key : conditions.keySet()) {
				query.setParameter(key, conditions.get(key));
			}
		} else {
			query = session.createSQLQuery(sql);
		}
		query.addEntity("ctVatTu", CTVatTu.class);
		ArrayList<CTVatTu> ctvatTuList = (ArrayList<CTVatTu>) query.list();
		session.getTransaction().commit();
		return ctvatTuList;
	}

	public long sumByCtvtId(int ctvtId) {
		session.beginTransaction();
		String sql = "select sum(ycSoLuong) - sum(capSoLuong) from YeuCau where ctvtId = " + ctvtId;
		Query query = session.createQuery(sql);
		/*
		 * Criteria crYeuCau = session.createCriteria(YeuCau.class);
		 * crYeuCau.add(Restrictions.eq("ctvtId", ctvtId));
		 * crYeuCau.setProjection(Projections.groupProperty("ctvtId"));
		 * crYeuCau.setProjection(Projections.sum("ycSoLuong")); long sumSoLuong
		 * = (long) crYeuCau.uniqueResult();
		 */
		long sumSoLuong = (long) query.uniqueResult();
		session.getTransaction().commit();
		return sumSoLuong;
	}

	public ArrayList<CongVan> getCongVanByCtvtId(int ctvtId) {
		session.beginTransaction();
		String sql = "select distinct a.* from CONGVAN a join YEUCAU b where a.cvId = b.cvId and b.ctvtId = " + ctvtId;
		/*
		 * // get distinct cvId of yeuCau have ctvtId = ctvtId and capsoLuong <
		 * ycSoLuong Criteria crYeuCau = session.createCriteria(YeuCau.class);
		 * crYeuCau.setProjection(Projections.distinct(Projections.property(
		 * "cvId"))); crYeuCau.add(Restrictions.eq("ctvtId", ctvtId));
		 * ArrayList<Integer> cvIdList = (ArrayList<Integer>) crYeuCau.list();
		 * // get cong van list have cvId in cvIdList ArrayList<CongVan>
		 * congVanList = new ArrayList<CongVan>(); if (cvIdList.size() > 0) {
		 * Criteria crCongVan = session.createCriteria(CongVan.class,
		 * "congVan"); crCongVan.createAlias("congVan.trangThai", "trangThai");
		 * crCongVan.add(Restrictions.in("cvId", cvIdList)); congVanList =
		 * (ArrayList<CongVan>) crCongVan.list(); }
		 */
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity("CongVan", CongVan.class);
		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) query.list();
		session.getTransaction().commit();
		return congVanList;
	}

	public void close() {
		session.close();
	}

	public void disconnect() {
		session.disconnect();
	}

}
