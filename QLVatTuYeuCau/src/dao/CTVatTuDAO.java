/**
 * 
 */
package dao;

import java.util.ArrayList;
import java.util.HashMap;
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

import model.CTVatTu;
import model.ChatLuong;
import model.NoiSanXuat;
import model.VatTu;
import util.HibernateUtil;

/**
 * @author QUOI
 *
 */
public class CTVatTuDAO {
	
	private SessionFactory template;  
	private Session session;
	public CTVatTuDAO() {
		template = HibernateUtil.getSessionFactory();
		session = template.openSession();
	}
	public CTVatTuDAO(Session session) {
		this.session = session;
	}
	public CTVatTu getCTVatTu(final String ctVatTu) {
		session.beginTransaction();
		
		CTVatTu ctvt = (CTVatTu) session.get(CTVatTu.class, ctVatTu);
		session.getTransaction().commit();
		return ctvt;
	}
	public List<CTVatTu> getAllCTVatTu() {
		session.beginTransaction();
		Criteria cr = session.createCriteria(CTVatTu.class);
		Criterion xoaCd = Restrictions.eq("daXoa", 0);
		cr.add(xoaCd);
		ArrayList<CTVatTu> CTVatTuList = (ArrayList<CTVatTu>) cr.list(); 
		session.getTransaction().commit();
		return CTVatTuList;
	}

	public long size() {
		session.beginTransaction();
		String sql = "select count(ctvtId) from CTVatTu where daXoa = 0";
		Query query =  session.createQuery(sql);
		long size = (long) query.list().get(0);
		session.getTransaction().commit();
		return size;
		
	}
	
	public void addCTVatTu(CTVatTu ctVatTu){
		session.beginTransaction();
		session.save(ctVatTu);
		session.getTransaction().commit();
	}
	public void addOrUpdateCTVatTu(CTVatTu ctVatTu){
		session.beginTransaction();
		session.saveOrUpdate(ctVatTu);
		session.getTransaction().commit();
	}
	public void updateCTVatTu(CTVatTu ctVatTu){
		session.beginTransaction();
		session.update(ctVatTu);
		session.getTransaction().commit();
	}
	public void deleteCTVatTu(String vtMa){
		session.beginTransaction();
		String sql = "update CTVatTu set daXoa = 1 where vtMa = '" + vtMa + "'";		
		Query query = session.createQuery(sql);
		query.executeUpdate();
		session.getTransaction().commit();
	}
	public CTVatTu getCTVatTuById(final int ctvtId) {
		session.beginTransaction();
		
		
		CTVatTu ctVatTu =  (CTVatTu) session.get(CTVatTu.class, ctvtId);
		session.getTransaction().commit();
		return ctVatTu;
	}
	public CTVatTu getCTVatTu(final String vtMa, final String nsxMa, final String clMa) {
		session.beginTransaction();
		
		Criteria cr = session.createCriteria(CTVatTu.class);
		Criterion expNoiSanXuat = Restrictions.eq("noiSanXuat", new NoiSanXuat(nsxMa));
		Criterion expChatLuong = Restrictions.eq("chatLuong", new ChatLuong(clMa));
		Criterion expVatTu = Restrictions.eq("vatTu", new VatTu(vtMa));
		//LogicalExpression andExp = Restrictions.and (expChatLuong, expNoiSanXuat);
//		andExp = Restrictions.and(andExp, expChatLuong);
//		andExp = Restrictions.and(andExp, expVatTu);
		cr.add(expNoiSanXuat);
		cr.add(expChatLuong);
		cr.add(expVatTu);
		ArrayList<CTVatTu> ctvtList = (ArrayList<CTVatTu>) cr.list();
		
		CTVatTu ctVatTu = null;
		if(ctvtList.size() != 0)
			ctVatTu = ctvtList.get(0);
		session.getTransaction().commit();
		return ctVatTu;
	}
	public ArrayList<CTVatTu> getCTVTu(final String vtMa) {
		session.beginTransaction();
		
		Criteria cr = session.createCriteria(CTVatTu.class);
		
		Criterion expVatTu = Restrictions.eq("vatTu", new VatTu(vtMa));
		
		cr.add(expVatTu);
		
		ArrayList<CTVatTu> ctVatTu =  (ArrayList<CTVatTu>) cr.list();
		session.getTransaction().commit();
		return ctVatTu;
	}
	
	public int getLastInsert() {
		session.beginTransaction();
		Criteria cr =  session.createCriteria(CTVatTu.class).setProjection(Projections.max("ctvtId"));// max("ctvtId"));
		Integer idOld =  (Integer) cr.list().get(0);
		int id = 0;
		if (idOld != null)
			id += idOld + 1;
		else
			id++;
		
		session.getTransaction().commit();
		return id;
	}
	public HashMap<Integer, CTVatTu> getHashMap() {
		HashMap<Integer, CTVatTu> ctvtHash = new HashMap<Integer, CTVatTu>();
		ArrayList<CTVatTu> ctvtList = (ArrayList<CTVatTu>) getAllCTVatTu();
		for (CTVatTu ctvt : ctvtList) {
			ctvtHash.put(ctvt.getCtvtId(), ctvt);
		}
		return ctvtHash;
	}

	public ArrayList<CTVatTu> search(String vtMa, String vtTen, String nsx, String chatLuong) {
		session.beginTransaction();
		Criteria cr =  session.createCriteria(CTVatTu.class, "ctVatTu");
		/*
		if (vtTen != "")
			cr.setFetchMode("ctVatTu.", mode)
		*/
		if (vtMa != "")
		{	
			Criterion expVatTu = Restrictions.eq("vatTu", new VatTu(vtMa));
			cr.add(expVatTu);
		}
		if (nsx != "") {
			Criterion expNsx = Restrictions.eq("noiSanXuat", new NoiSanXuat(nsx));
			cr.add(expNsx);
		}
		if (chatLuong != "") {
			Criterion expChatLuong = Restrictions.eq("chatLuong", new ChatLuong(chatLuong));
			cr.add(expChatLuong);
		}
		ArrayList<CTVatTu> ctVatTuList = (ArrayList<CTVatTu>) cr.list();
		session.getTransaction().commit();
		return ctVatTuList;
	}
	public ArrayList<String> startWith(String i) {
		session.beginTransaction();

		String sql = "select vtTen from VatTu where vtTen LIKE :vtTen";
		Query query = session.createQuery(sql);
		query.setParameter("vtTen", i+"%");
		ArrayList<String> list = (ArrayList<String>) query.list();
		
		session.getTransaction().commit();
		return list;
	}

	public ArrayList<CTVatTu> searchVtTen(String i) {
		session.beginTransaction();
		String sql = "from CTVatTu where vtMa in (select * from VatTu where vtTen LIKE :vtTen)";
		Query query = session.createQuery(sql);
		query.setParameter("vtTen", i+"%");
		ArrayList<CTVatTu> list = (ArrayList<CTVatTu>) query.list();
		session.getTransaction().commit();
		return list;
	}
	 public ArrayList<String> startWithMa(String i) {
		session.beginTransaction();
		String sql = "select distinct vtMa from VatTu where vtMa LIKE :vtMa";
		Query query = session.createQuery(sql);
		query.setParameter("vtMa", i+"%");
		ArrayList<String> list = (ArrayList<String>) query.list();
		session.getTransaction().commit();
		return list;
	}
	 public ArrayList<CTVatTu> searchVtMa(String i) {
		session.beginTransaction();
		String sql = "from CTVatTu where vtMa LIKE :vtTen";
		Query query = session.createQuery(sql);
		query.setParameter("vtMa", i+"%");
		ArrayList<CTVatTu> list = (ArrayList<CTVatTu>) query.list();
		session.getTransaction().commit();
		return list;
	}

	public void begin(){
		session.beginTransaction();
	}
	public void commit(){
		session.getTransaction().commit();
	}
	public void rollback() {
		session.getTransaction().rollback();
	}
	
	public void addCTVatTuRoll(CTVatTu ctVatTu){
		session.save(ctVatTu);
	}
	public void updateCTVatTuRoll(CTVatTu ctVatTu){
		session.update(ctVatTu);
	}
	public long sizeOfSearchCtvtMa(final String vtMa) {
		session.beginTransaction();

		String sql = "select count(a.vatTu.vtMa) from CTVatTu a join a.vatTu b  where a.vatTu.vtMa LIKE :vtMa and a.vatTu.vtMa = b.vtMa";
		Query query = session.createQuery(sql);
		query.setParameter("vtMa", vtMa+"%");
		long size = (long) query.list().get(0);
		session.getTransaction().commit();
		return size;
	}
	public ArrayList<CTVatTu> searchByCtvtMaLimit(String vtMa, int first, int limit) {
		session.beginTransaction();

		//String sql = "select a.ctvtId, a.vatTu, a.noiSanXuat, a.chatLuong, a.dinhMuc, a.soLuongTon, a.daXoa from CTVatTu a join a.vatTu b  where a.vatTu.vtMa LIKE :vtMa and a.vatTu.vtMa = b.vtMa";
//		Query query = session.createQuery(sql);
//		query.setParameter("vtMa", vtMa+"%");
		Criteria cr = session.createCriteria(CTVatTu.class, "ctVatTu");
		cr.createAlias("ctVatTu.noiSanXuat", "noiSanXuat");
		cr.createAlias("ctVatTu.chatLuong", "chatLuong");
		cr.createAlias("ctVatTu.vatTu", "vatTu");
		cr.createAlias("vatTu.dvt", "dvt");
		cr.add(Restrictions.like("vatTu.vtMa", vtMa, MatchMode.START));
		cr.addOrder(Order.asc("vatTu.vtMa"));
		cr.setFirstResult(first);
		cr.setMaxResults(limit);
		ArrayList<CTVatTu> list = (ArrayList<CTVatTu>) cr.list();
		
		session.getTransaction().commit();
		return list;
	}
	public long sizeOfSearchCtvtTen(String vtTen) {
		session.beginTransaction();

		String sql = "select count(b.vtTen) from CTVatTu a join a.vatTu b  where a.vatTu.vtTen LIKE :vtTen and a.vatTu.vtTen = b.vtTen order by b.vtMa";
		Query query = session.createQuery(sql);
		query.setParameter("vtTen", vtTen+"%");
		long size = (long) query.list().get(0);
		session.getTransaction().commit();
		return size;
	}
	public ArrayList<CTVatTu> searchByCtvtTenLimit(String vtTen, int first, int limit) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(CTVatTu.class, "ctVatTu");
		cr.createAlias("ctVatTu.noiSanXuat", "noiSanXuat");
		cr.createAlias("ctVatTu.chatLuong", "chatLuong");
		cr.createAlias("ctVatTu.vatTu", "vatTu");
		cr.createAlias("vatTu.dvt", "dvt");
		cr.add(Restrictions.like("vatTu.vtTen", vtTen+"%"));
		cr.addOrder(Order.asc("vatTu.vtMa"));
		cr.setFirstResult(first);
		cr.setMaxResults(limit);
		ArrayList<CTVatTu> list = (ArrayList<CTVatTu>) cr.list();
		session.getTransaction().commit();
		return list;
	}
	public ArrayList<CTVatTu> limit(int first, int limit) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(CTVatTu.class, "ctVatTu");
		cr.createAlias("ctVatTu.noiSanXuat", "noiSanXuat");
		cr.createAlias("ctVatTu.chatLuong", "chatLuong");
		cr.createAlias("ctVatTu.vatTu", "vatTu");
		cr.createAlias("vatTu.dvt", "dvt");
		cr.addOrder(Order.asc("vatTu.vtMa"));
		cr.setFirstResult(first);
		cr.setMaxResults(limit);
		ArrayList<CTVatTu> list = (ArrayList<CTVatTu>) cr.list();
		
		session.getTransaction().commit();
		return list;
	}
	public void close() {
		if(session.isOpen())
			session.close();
	}
	public void disconnect() {
		if (session.isConnected())
			session.disconnect();
	}

	public static void main(String[] args) {
		
		CTVatTuDAO ct = new CTVatTuDAO();
		System.out.println(ct.searchByCtvtMaLimit("1", 0, 10000000).size());
	}
	public ArrayList<CTVatTu> getCtVatTuListAlert(int first, int limit) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(CTVatTu.class, "ctVatTu");
		cr.createAlias("ctVatTu.noiSanXuat", "noiSanXuat");
		cr.createAlias("ctVatTu.chatLuong", "chatLuong");
		cr.createAlias("ctVatTu.vatTu", "vatTu");
		cr.createAlias("vatTu.dvt", "dvt");
//		cr.add(Restrictions.eq("dinhMuc", ))
		cr.add(Restrictions.sqlRestriction("soLuongTon < dinhMuc"));
		cr.setFirstResult(first);
		cr.setMaxResults(limit);
		ArrayList<CTVatTu> ctvatTuList =  (ArrayList<CTVatTu>) cr.list();
		session.getTransaction().commit();
		return ctvatTuList;
	}
}
