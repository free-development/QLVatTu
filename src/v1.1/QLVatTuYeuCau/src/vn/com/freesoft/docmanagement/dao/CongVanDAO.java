package vn.com.freesoft.docmanagement.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import vn.com.freesoft.docmanagement.entity.CongVan;
import vn.com.freesoft.docmanagement.entity.DonVi;
import vn.com.freesoft.docmanagement.entity.TrangThai;
import vn.com.freesoft.docmanagement.entity.VTCongVan;
import vn.com.freesoft.docmanagement.util.DateUtil;
import vn.com.freesoft.docmanagement.util.HibernateUtil;
import vn.com.freesoft.docmanagement.util.SqlUtil;

public class CongVanDAO {

	private SessionFactory template;
	private Session session;

	public CongVanDAO() {
		template = HibernateUtil.getSessionFactory();
		session = template.openSession();
	}

	public CongVan getCongVan(final int cvId) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(CongVan.class, "congVan");
		cr.createAlias("congVan.mucDich", "mucDich");
		cr.createAlias("congVan.donVi", "donVi");
		cr.createAlias("congVan.trangThai", "trangThai");
		cr.add(Restrictions.eq("cvId", cvId));
		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) cr.list();
		session.getTransaction().commit();
		if (congVanList.size() > 0)
			return congVanList.get(0);
		else
			return null;
	}

	public List<CongVan> getAllCongVan() {
		session.beginTransaction();
		Criteria cr = session.createCriteria(CongVan.class);
		cr.add(Restrictions.eq("daXoa", 0));
		List<CongVan> congVanList = (List<CongVan>) cr.list();
		session.getTransaction().commit();
		return congVanList;
	}

	public List<CongVan> getAllCongVan(String msnv) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(CongVan.class);
		cr.add(Restrictions.eq("daXoa", 0));
		if (msnv != null) {
			String sql = "select distinct cvId from VTCongVan where msnv = '" + msnv + "'";
			Query query = session.createQuery(sql);
			ArrayList<Integer> cvIdList = (ArrayList<Integer>) query.list();
			cr.add(Restrictions.in("cvId", cvIdList));
		}
		List<CongVan> congVanList = (List<CongVan>) cr.list();
		session.getTransaction().commit();
		return congVanList;
	}

	public List<CongVan> limit(int first, int limit) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(CongVan.class);

		Criterion xoaCd = Restrictions.eq("daXoa", 0);
		// Criterion limitRow = Restrictions.
		cr.add(xoaCd);
		cr.setFirstResult(first);
		cr.setMaxResults(limit);
		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) cr.list();
		session.getTransaction().commit();
		return congVanList;
	}

	public Criteria getCriteria(String msnv) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(CongVan.class, "congVan");
		cr.createAlias("congVan.mucDich", "mucDich");
		cr.createAlias("congVan.donVi", "donVi");
		cr.createAlias("congVan.trangThai", "trangThai");
		if (msnv != null) {
			Criteria crVtCv = session.createCriteria(VTCongVan.class);
			crVtCv.add(Restrictions.eq("msnv", msnv));
			crVtCv.setProjection(Projections.countDistinct("cvId"));

			crVtCv.setProjection(Projections.property("cvId"));

			ArrayList<Integer> cvIdList = (ArrayList<Integer>) crVtCv.list();

			if (cvIdList.size() > 0)
				cr.add(Restrictions.in("cvId", cvIdList));
			else {
				session.getTransaction().commit();
				return null;
			}
		}
		// cr.
		cr.add(Restrictions.eq("daXoa", 0));
		session.getTransaction().commit();
		return cr;
	}

	public long size(String msnv) {

		Criteria cr = getCriteria(msnv);
		long size = 0;
		if (cr != null) {
			// session.beginTransaction();
			cr.setProjection(Projections.countDistinct("cvId"));
			size = (long) cr.list().get(0);
			// session.getTransaction().commit();
		}

		return size;
	}

	public void addCongVan(CongVan congVan) {
		session.beginTransaction();
		session.save(congVan);
		// session.refresh(congVan);
		session.getTransaction().commit();
	}

	public void addOrUpdateCongVan(CongVan congVan) {
		session.beginTransaction();
		session.saveOrUpdate(congVan);
		session.getTransaction().commit();
	}

	public void updateCongVan(CongVan congVan) {
		session.beginTransaction();
		session.update(congVan);
		session.getTransaction().commit();
	}

	public void deleteCongVan(int cvId) {
		session.beginTransaction();
		// session.delete(congVan);
		String hql = "UPDATE CongVan set daXoa = 1 " + "WHERE cvId = :cvId";
		Query query = session.createQuery(hql);
		query.setParameter("cvId", cvId);
		int result = query.executeUpdate();
		session.getTransaction().commit();
	}

	public int getSoDen(final int cvId) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(CongVan.class);
		cr.add(Restrictions.eq("cvId", cvId));
		cr.setProjection(Projections.property("soDen"));
		int soDen = (int) cr.list().get(0);
		session.getTransaction().commit();
		return soDen;
	}

	public int getLastInsert() {
		session.beginTransaction();
		Criteria cr = session.createCriteria(CongVan.class).setProjection(Projections.max("cvId"));// max("ctvtId"));
		Integer idOld = (Integer) cr.list().get(0);
		int id = 0;
		if (idOld != null)
			id += idOld + 1;
		else
			id++;
		session.getTransaction().commit();
		return id;
	}

	public int getSoDenAdd(Date cvNgayNhan) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(CongVan.class);
		cr.setProjection(Projections.max("soDen"));
		// cr.add(Restrictions.sqlRestriction("MONTH(cvNgayNhan) = " +
		// (cvNgayNhan.getMonth() + 1)));
		cr.add(Restrictions.sqlRestriction("YEAR(cvNgayNhan) = " + (cvNgayNhan.getYear() + 1900)));
		// cr.setProjection(Projections.property("soDen"));
		// cr.addOrder(Order.desc("soDen"));
		Integer soDen = (Integer) cr.uniqueResult();
		if (soDen == null)
			soDen = 0;
		soDen++;
		// ArrayList<Integer> temp = (ArrayList<Integer>) cr.list();
		// int soDen = 0;
		// if (temp.size() > 0)
		// soDen = temp.get(0);
		// soDen ++;
		session.getTransaction().commit();
		return soDen;
	}

	public ArrayList<CongVan> getByDate(Date ngaybd, Date ngaykt) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(CongVan.class);
		cr.add(Restrictions.between("cvNgayNhan", ngaybd, ngaykt));
		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) cr.list();
		session.getTransaction().commit();
		return congVanList;
	}

	public ArrayList<CongVan> getTrangThai(Date ngaybd, Date ngaykt) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(CongVan.class, "congVan");
		cr.createAlias("congVan.trangThai", "trangThai");
		// cr.createAlias("", arg1)
		Criterion tt1 = Restrictions.eq("trangThai.ttMa", "DGQ");
		Criterion tt2 = Restrictions.eq("trangThai.ttMa", "CGQ");
		LogicalExpression andExp = Restrictions.or(tt1, tt2);
		// cr.add(tt1);
		cr.add(andExp);
		if ((ngaybd != null || ngaybd != null)) {

			if (ngaybd == null)
				ngaybd = ngaykt;
			else if (ngaykt == null)
				ngaykt = ngaybd;

			Criterion ngay = Restrictions.between("cvNgayNhan", ngaybd, ngaykt);
			// LogicalExpression andNgay = Restrictions.and(andExp, ngay);
			cr.add(ngay);
		}

		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) cr.list();
		session.getTransaction().commit();
		return congVanList;
	}

	public ArrayList<CongVan> getTrangThai(String ngaybd, String ngaykt, String dvMa, String ttMa) {
		session.beginTransaction();
		Date ngayht = DateUtil.convertToSqlDate(new java.util.Date());
		Criteria cr = session.createCriteria(CongVan.class);

		if (ngaybd != "" || ngaykt != "") {
			if (ngaybd == "") {
				ngaybd = ngaykt;
			}
			if (ngaykt == "") {
				ngaykt = ngaybd;
			}
			Criterion ngay = Restrictions.between("cvNgayNhan", DateUtil.parseDate(ngaybd), DateUtil.parseDate(ngaykt));
			cr.add(ngay);
		}

		if (ttMa != null && !"all".equalsIgnoreCase(ttMa)) {
			Criterion crtt = Restrictions.eq("trangThai", new TrangThai(ttMa));
			cr.add(crtt);
		}
		if (dvMa != null) {
			Criterion crdv = Restrictions.eq("donVi", new DonVi(dvMa));
			cr.add(crdv);
		}

		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) cr.list();
		session.getTransaction().commit();
		return congVanList;
	}

	public CongVan getByCvSo(String cvSo) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(CongVan.class);
		cr.add(Restrictions.eq("cvSo", cvSo));
		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) cr.list();
		CongVan congVan = null;
		if (congVanList.size() > 0)
			congVan = congVanList.get(0);
		session.getTransaction().commit();
		return congVan;
	}

	public ArrayList<Integer> getCvIdByMsnv(String msnv) {
		session.beginTransaction();
		String sqlNd = "select distinct(b.cvId) from VTCongVan b";
		if (msnv != null)
			sqlNd += " where msnv = '" + msnv + "'";

		ArrayList<Integer> cvIdList = (ArrayList<Integer>) session.createQuery(sqlNd).list();
		session.getTransaction().commit();
		return cvIdList;
	}

	public ArrayList<Integer> groupByYearLimit(String msnv, HashMap<String, Object> conditions, int limit) {

		ArrayList<Integer> cvIdList = getCvIdByMsnv(msnv);

		session.beginTransaction();
		String sql = "select distinct YEAR(a.cvNgayNhan) from CongVan a where a.daXoa = 0 ";

		if (msnv != null)
			sql += "  and a.cvId in (select distinct(b.cvId) from VTCongVan b where msnv = '" + msnv + "')";

		if (conditions != null) {
			for (String key : conditions.keySet()) {
				int index = key.indexOf(".");
				if (index != -1)
					key = key.substring(index + 1);
				sql += " and " + key + " = :" + key;

			}

		}
		sql += " order by cvNgayNhan DESC";
		Query query = session.createQuery(sql);
		if (conditions != null) {
			for (String key : conditions.keySet()) {

				Object object = conditions.get(key);
				int index = key.indexOf(".");
				if (index != -1)
					key = key.substring(index + 1);
				query.setParameter(key, object);

			}
		}
		query.setMaxResults(limit);
		ArrayList<Integer> yearList = (ArrayList<Integer>) query.list();
		session.getTransaction().commit();
		return yearList;
	}

	public ArrayList<Integer> groupByMonth(final String msnv, HashMap<String, Object> conditions, final int year) {
		ArrayList<Integer> cvIdList = getCvIdByMsnv(msnv);
		session.beginTransaction();
		String sql = "select distinct MONTH(a.cvNgayNhan) from CongVan a where a.daXoa = 0 and YEAR(cvNgayNhan) = :year";
		if (msnv != null)
			sql += "  and a.cvId in (select distinct(b.cvId) from VTCongVan b where msnv = '" + msnv + "')";
		if (conditions != null) {
			for (String key : conditions.keySet()) {
				int index = key.indexOf(".");
				if (index != -1)
					key = key.substring(index + 1);
				sql += " and " + key + " = :" + key;
			}
		}

		sql += " order by cvNgayNhan DESC";
		Query query = session.createQuery(sql);
		query.setParameter("year", year);
		if (conditions != null) {
			for (String key : conditions.keySet()) {
				Object object = conditions.get(key);
				int index = key.indexOf(".");
				if (index != -1)
					key = key.substring(index + 1);
				query.setParameter(key, object);
			}
		}

		ArrayList<Integer> monthList = (ArrayList<Integer>) query.list();
		session.getTransaction().commit();
		return monthList;
	}

	public ArrayList<Integer> groupByDate(final String msnv, HashMap<String, Object> conditions, final int year,
			final int month) {
		ArrayList<Integer> cvIdList = getCvIdByMsnv(msnv);

		session.beginTransaction();
		String sql = "select distinct DAY(a.cvNgayNhan) from CongVan a where a.daXoa = 0 and YEAR(cvNgayNhan) = :year and MONTH(cvNgayNhan) = :month "
				+ "";
		if (msnv != null) {
			sql += "  and a.cvId in (select distinct(b.cvId) from VTCongVan b where msnv = '" + msnv + "')";
		}
		if (conditions != null) {
			for (String key : conditions.keySet()) {
				int index = key.indexOf(".");
				if (index != -1)
					key = key.substring(index + 1);
				sql += " and " + key + " = :" + key;
			}
		}
		sql += " order by cvNgayNhan DESC";
		Query query = session.createQuery(sql);
		query.setParameter("year", year);
		query.setParameter("month", month);
		if (conditions != null) {
			for (String key : conditions.keySet()) {
				Object object = conditions.get(key);
				int index = key.indexOf(".");
				if (index != -1)
					key = key.substring(index + 1);
				query.setParameter(key, object);
			}
		}
		query.setParameter("year", year);
		query.setParameter("month", month);
		ArrayList<Integer> dateList = (ArrayList<Integer>) query.list();
		session.getTransaction().commit();
		return dateList;
	}

	public ArrayList<CongVan> searchByYear(int year) {
		session.beginTransaction();
		String sql = "from CongVan where daXoa = 0 and year(cvNgayNhan) = :year";
		Query query = session.createQuery(sql);
		query.setParameter("year", year);
		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) query.list();
		session.getTransaction().commit();
		return congVanList;
	}

	public ArrayList<CongVan> search(HashMap<String, Object> conditions, HashMap<String, Boolean> orderBy) {
		session.beginTransaction();
		SqlUtil sqlUtil = new SqlUtil();
		sqlUtil.createQuery("CongVan");
		if (conditions != null && conditions.size() > 0) {
			for (String key : conditions.keySet()) {
				Object object = conditions.get(key);
				if (object instanceof Integer)
					sqlUtil.addCondition(key + " = " + object);
				else
					sqlUtil.addCondition(key + " = '" + object + "'");
			}
		}
		if (orderBy.size() > 0 && orderBy != null)
			sqlUtil.orderBy(orderBy);
		Query query = session.createQuery(sqlUtil.getQuery());
		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) query.list();
		session.getTransaction().commit();
		return congVanList;
	}

	public ArrayList<CongVan> searchLimit(String msnv, HashMap<String, Object> conditions,
			HashMap<String, Boolean> orderBy, int first, int limit) {
		session.beginTransaction();
		ArrayList<Integer> cvIdList = new ArrayList<Integer>();
		if (msnv != null) {
			cvIdList = (ArrayList<Integer>) session
					.createQuery("select cvId from VTCongVan where msnv = '" + msnv + "' and daXoa = 0").list();
			if (cvIdList.size() == 0) {
				session.getTransaction().commit();
				return new ArrayList<CongVan>();
			}
		}

		Criteria cr = session.createCriteria(CongVan.class, "congVan");
		cr.createAlias("congVan.mucDich", "mucDich");
		cr.createAlias("congVan.donVi", "donVi");
		cr.createAlias("congVan.trangThai", "trangThai");
		LogicalExpression expression = null;

		if (conditions != null) {
			for (String key : conditions.keySet()) {
				Object object = conditions.get(key);
				// if (object instanceof Integer &&
				// (key.equalsIgnoreCase("year") ||
				// key.equalsIgnoreCase("month") ||
				// key.equalsIgnoreCase("day"))) {
				if (object instanceof LogicalExpression) {
					expression = (LogicalExpression) conditions.get(key);
				} else if (object != null) {
					if (key.equals("cvId") || key.equals("soDen") || key.equals("cvNgayDi") || key.equals("cvNgayNhan"))
						cr.add(Restrictions.eq(key, object));
					else if (key.equals("year"))
						// cr.addQueryHint("year(cvNgayNhan) = " + object);
						cr.add(Restrictions.sqlRestriction("year(cvNgayNhan) = " + object));
					else if (key.equals("gecvNgayNhan"))
						cr.add(Restrictions.ge("cvNgayNhan", object));
					else if (key.equals("lecvNgayNhan"))
						cr.add(Restrictions.le("cvNgayNhan", object));
					else if (key.equals("gecvNgayDi"))
						cr.add(Restrictions.ge("cvNgayDi", object));
					else if (key.equals("lecvNgayDi"))
						cr.add(Restrictions.le("cvNgayDi", object));
					else
						cr.add(Restrictions.like(key, (String) object, MatchMode.START));
					// cr.add(Restrictions.eq(key, conditions.get(key)));

				}
			}
		}
		if (expression != null)
			cr.add(expression);
		cr.add(Restrictions.eq("daXoa", 0));
		if (orderBy != null) {
			for (String key : orderBy.keySet()) {
				if (orderBy.get(key))
					cr.addOrder(Order.desc(key));
				else
					cr.addOrder(Order.asc(key));
			}
		}
		if (cvIdList.size() > 0)
			cr.add(Restrictions.in("cvId", cvIdList));
		cr.setFirstResult(first);
		cr.setMaxResults(limit);
		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) cr.list();
		session.getTransaction().commit();
		return congVanList;
	}

	public long size(String msnv, HashMap<String, Object> conditions) {

		session.beginTransaction();
		ArrayList<Integer> cvIdList = new ArrayList<Integer>();
		if (msnv != null) {
			cvIdList = (ArrayList<Integer>) session
					.createQuery("select cvId from VTCongVan where msnv = '" + msnv + "' and daXoa = 0").list();
			if (cvIdList.size() == 0) {
				session.getTransaction().commit();
				return 0;
			}

		}

		Criteria cr = session.createCriteria(CongVan.class, "congVan");
		cr.createAlias("congVan.mucDich", "mucDich");
		cr.createAlias("congVan.donVi", "donVi");
		cr.createAlias("congVan.trangThai", "trangThai");
		LogicalExpression expression = null;
		if (conditions != null) {
			for (String key : conditions.keySet()) {
				Object object = conditions.get(key);
				if (object instanceof LogicalExpression) {
					expression = (LogicalExpression) conditions.get(key);
				} else if (object != null) {
					if (key.equals("cvId") || key.equals("soDen") || key.equals("cvNgayDi") || key.equals("cvNgayNhan"))
						cr.add(Restrictions.eq(key, object));
					else if (key.equals("year"))
						// cr.addQueryHint("year(cvNgayNhan) = " + object);
						cr.add(Restrictions.sqlRestriction("year(cvNgayNhan) = " + object));
					else if (key.equals("gecvNgayNhan"))
						cr.add(Restrictions.ge("cvNgayNhan", object));
					else if (key.equals("lecvNgayNhan"))
						cr.add(Restrictions.le("cvNgayNhan", object));
					else if (key.equals("gecvNgayDi"))
						cr.add(Restrictions.ge("cvNgayDi", object));
					else if (key.equals("lecvNgayDi"))
						cr.add(Restrictions.le("cvNgayDi", object));
					else
						cr.add(Restrictions.like(key, (String) object, MatchMode.START));
					// cr.add(Restrictions.eq(key, conditions.get(key)));
				}
			}
			if (expression != null)
				cr.add(expression);
		}
		cr.add(Restrictions.eq("daXoa", 0));

		if (cvIdList.size() > 0)
			cr.add(Restrictions.in("cvId", cvIdList));
		cr.setProjection(Projections.count("cvId"));
		Long size = (Long) cr.list().get(0);
		session.getTransaction().commit();
		return size;
	}

	public void close() {
		if (session.isOpen())
			session.close();
	}

	public void disconnect() {
		if (session.isConnected())
			session.disconnect();
	}

	public LogicalExpression addTimeExpression(LogicalExpression expression, HashMap<Integer, HashSet<Integer>> month,
			HashMap<String, HashSet<Integer>> date) {
		String condition = " (";
		boolean checkYear = false;
		for (Integer y : month.keySet()) {

			if (checkYear)
				condition += " or ";
			condition += "( ";
			checkYear = true;
			condition += "YEAR(cvNgayNhan) = " + y;
			HashSet<Integer> monthList = month.get(y);
			if (monthList != null && monthList.size() > 0) {

				condition += " and ";
				condition += "( ";
				boolean checkMonth = false;
				for (Integer m : monthList) {

					if (checkMonth)
						condition += " or ";
					condition += "( ";
					checkMonth = true;
					condition += "MONTH(cvNgayNhan) = " + m;
					HashSet<Integer> dateList = date.get(y + "#" + m);
					if (dateList != null && dateList.size() > 0) {
						condition += " and ";
						Boolean checkDay = false;
						for (Integer d : dateList) {
							if (checkDay)
								condition += " or ";
							condition += "( ";
							checkDay = true;
							condition += " DAY(cvNgayNhan) = " + d;
							condition += ") ";
						}

					}
					condition += ") ";
				}
				condition += ") ";
			}
			condition += ")";
		}
		condition += " )";

		if (condition.length() <= 4)
			return expression;
		Criterion ex = Restrictions.sqlRestriction(condition);
		if (expression == null)
			expression = Restrictions.or(ex, ex);
		else
			expression = Restrictions.or(expression, ex);
		return expression;
	}

	public String addTimeExpressionSql(String expression, HashMap<Integer, HashSet<Integer>> month,
			HashMap<String, HashSet<Integer>> date) {
		String condition = " (";
		boolean checkYear = false;
		for (Integer y : month.keySet()) {

			if (checkYear)
				condition += " or ";
			condition += "( ";
			checkYear = true;

			condition += "YEAR(cvNgayNhan) = " + y;
			HashSet<Integer> monthList = month.get(y);
			if (monthList != null) {

				condition += " and ";
				condition += "( ";
				boolean checkMonth = false;
				for (Integer m : monthList) {

					if (checkMonth)
						condition += " or ";
					condition += "( ";
					checkMonth = true;
					condition += "MONTH(cvNgayNhan) = " + m;
					HashSet<Integer> dateList = date.get(y + "#" + m);
					if (dateList != null) {
						condition += " and ";
						Boolean checkDay = false;
						for (Integer d : dateList) {
							if (checkDay)
								condition += " or ";
							condition += "( ";
							checkDay = true;
							condition += " DAY(cvNgayNhan) = " + d;
							condition += ") ";
						}

					}
					condition += ") ";
				}
				condition += ") ";
			}
			condition += ")";
		}
		condition += " )";

		if (condition.length() == 0)
			return expression;
		else
			expression += " and " + condition;

		return condition;
	}
}
