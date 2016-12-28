package vn.com.freesoft.docmanament.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import vn.com.freesoft.docmanament.entity.CTNguoiDung;
import vn.com.freesoft.docmanament.entity.NguoiDung;
import vn.com.freesoft.docmanament.util.HibernateUtil;
import vn.com.freesoft.docmanament.util.StringUtil;

public class CTNguoiDungDAO {

	private SessionFactory template;
	private Session session;

	public CTNguoiDungDAO() {
		template = HibernateUtil.getSessionFactory();
		session = template.openSession();
	}

	public CTNguoiDung getCTNguoiDung(final String msnv) {
		session.beginTransaction();
		CTNguoiDung ctNguoiDung = (CTNguoiDung) session.get(CTNguoiDung.class, msnv);
		session.getTransaction().commit();
		return ctNguoiDung;
	}

	public int login(final String msnv, final String matkhau) {

		CTNguoiDung ctNguoiDung = getCTNguoiDung(msnv);
		if (ctNguoiDung == null) {
			return -1;
		} else if (!matkhau.equals(ctNguoiDung.getMatKhau())) {
			return 0;
		} else if (ctNguoiDung.getKhoa() == 1)
			return -2;
		return 1;
	}

	// public boolean login(final String msnv, final String matkhau) {
	// CTNguoiDung ctNguoiDung = getCTNguoiDung(msnv);
	// if (ctNguoiDung == null || !matkhau.equals(ctNguoiDung.getMatKhau()))
	// return false;
	// return true;
	// }
	public List<CTNguoiDung> getAllCTNguoiDung() {
		session.beginTransaction();
		List<CTNguoiDung> ctNguoiDungList = (List<CTNguoiDung>) session.createCriteria(CTNguoiDung.class).list();
		session.getTransaction().commit();
		return ctNguoiDungList;
	}

	public void addCTNguoiDung(CTNguoiDung ctNguoiDung) {
		session.beginTransaction();
		session.save(ctNguoiDung);
		session.getTransaction().commit();
	}

	public void updateCTNguoiDung(CTNguoiDung ctNguoiDung) {
		session.beginTransaction();
		session.update(ctNguoiDung);
		session.getTransaction().commit();
	}

	public void deleteCTNguoiDung(CTNguoiDung ctNguoiDung) {
		session.beginTransaction();
		session.delete(ctNguoiDung);
		session.getTransaction().commit();
	}

	public int lockNguoiDung(String msnv) {
		session.beginTransaction();
		String sql = "update CTNguoiDung set khoa = 1 where msnv = '" + msnv + "'";
		Query query = session.createQuery(sql);
		int count = query.executeUpdate();
		session.getTransaction().commit();
		return count;
	}

	// public List<CTNguoiDung> limit(int first, int limit) {
	// session.beginTransaction();
	// Criteria cr = session.createCriteria(CTNguoiDung.class);
	// Criterion khoa = Restrictions.eq("khoa", 0);
	//// Criterion limitRow = Restrictions.
	// cr.add(khoa);
	// cr.setFirstResult(first);
	// cr.setMaxResults(limit);
	// ArrayList<CTNguoiDung> ctnguoiDungList = (ArrayList<CTNguoiDung>)
	// cr.list();
	// session.getTransaction().commit();
	// return ctnguoiDungList;
	// }
	//
	// public long size() {
	// session.beginTransaction();
	// String sql = "select count(msnv) from CTNguoiDung where khoa = 0";
	// Query query = session.createQuery(sql);
	// long size = (long) query.list().get(0);
	// session.getTransaction().commit();
	// return size;
	//
	// }
	public void close() {
		if (session.isOpen())
			session.close();
	}

	public void disconnect() {
		if (session.isConnected())
			session.disconnect();
	}

	public List<NguoiDung> limit(ArrayList<String> ignoreList, int first, int limit) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(CTNguoiDung.class);
		Criterion khoaNd = Restrictions.eq("khoa", 0);
		// Criterion limitRow = Restrictions.
		cr.add(khoaNd);
		cr.setFirstResult(first);
		cr.setMaxResults(limit);
		cr.setProjection(Projections.property("msnv"));
		ArrayList<String> ctnguoiDungList = (ArrayList<String>) cr.list();
		if (ctnguoiDungList.size() == 0) {
			ArrayList<NguoiDung> nguoiDungList = new ArrayList<NguoiDung>();
			session.getTransaction().commit();
			return nguoiDungList;
		} else {
			Criteria crNguoiDung = session.createCriteria(NguoiDung.class);
			crNguoiDung.add(Restrictions.in("msnv", ctnguoiDungList));
			// new update 24.10.2015
			// crNguoiDung.add(Restrictions.not(Restrictions.in("msnv")));
			ArrayList<NguoiDung> nguoiDungList = (ArrayList<NguoiDung>) crNguoiDung.list();
			session.getTransaction().commit();
			return nguoiDungList;
		}
	}

	public long size() {
		session.beginTransaction();
		String sql = "select count(msnv) from CTNguoiDung where khoa = 0";
		Query query = session.createQuery(sql);
		long size = (long) query.list().get(0);
		session.getTransaction().commit();
		return size;
	}

	public List<NguoiDung> limitReset(int first, int limit) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(CTNguoiDung.class);
		Criterion resetNd = Restrictions.eq("khoa", 1);
		cr.add(resetNd);
		cr.setFirstResult(first);
		cr.setMaxResults(limit);
		cr.setProjection(Projections.property("msnv"));
		ArrayList<String> ctnguoiDungList = (ArrayList<String>) cr.list();
		if (ctnguoiDungList.size() == 0) {
			ArrayList<NguoiDung> nguoiDungList = new ArrayList<NguoiDung>();
			session.getTransaction().commit();
			return nguoiDungList;
		} else {
			Criteria crNguoiDung = session.createCriteria(NguoiDung.class);
			crNguoiDung.add(Restrictions.in("msnv", ctnguoiDungList));
			ArrayList<NguoiDung> nguoiDungList = (ArrayList<NguoiDung>) crNguoiDung.list();
			session.getTransaction().commit();
			return nguoiDungList;
		}

	}

	public long sizeReset() {
		session.beginTransaction();
		String sql = "select count(msnv) from CTNguoiDung where khoa = 1";
		Query query = session.createQuery(sql);
		long size = (long) query.list().get(0);
		session.getTransaction().commit();
		return size;

	}

	public String resetMK(String msnv) {
		session.beginTransaction();
		StringUtil random = new StringUtil();
		int k = 8;
		String mk = random.randomCharacter(k);
		CTNguoiDungDAO ctnguoiDungDAO = new CTNguoiDungDAO();
		CTNguoiDung ctNd = new CTNguoiDung(msnv, mk, 0);
		ctNd.setMatKhau(StringUtil.encryptMD5(mk));
		ctnguoiDungDAO.updateCTNguoiDung(ctNd);
		session.getTransaction().commit();
		return mk;
	}

}
