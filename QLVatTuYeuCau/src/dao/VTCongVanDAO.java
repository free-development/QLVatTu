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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import model.NguoiDung;
import model.TrangThai;
import model.VTCongVan;
import model.VaiTro;
import util.HibernateUtil;



/**
 * @author quoioln
 *
 */
public class VTCongVanDAO {
	private SessionFactory template;  
	private Session session;
	public VTCongVanDAO() {
		template = HibernateUtil.getSessionFactory();
		session = template.openSession();
	}
	public List<VTCongVan> getAllVTCongVan() {
		session.beginTransaction();
		List<VTCongVan> vtCongVanList = (List<VTCongVan>) session.createCriteria(VTCongVan.class).list();
		session.getTransaction().commit();
		return vtCongVanList;
	}
	public void addVTCongVan(VTCongVan vtCongVan){
		session.beginTransaction();
		session.save(vtCongVan);
		session.getTransaction().commit();
	}
	public void addOrUpdateVTCongVan(VTCongVan vtCongVan){
		session.beginTransaction();
		session.saveOrUpdate(vtCongVan);
		session.getTransaction().commit();
	}
	public void updateVTCongVan(VTCongVan vtCongVan){
		session.beginTransaction();
		session.update(vtCongVan);
		session.getTransaction().commit();
	}
	public void deleteVTCongVan(VTCongVan vtCongVan){
		session.beginTransaction();
		session.delete(vtCongVan);
		session.getTransaction().commit();
	}
	public ArrayList<VTCongVan> getVTCongVan(int cvId, String msnv) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(VTCongVan.class);
		Criterion expCv = Restrictions.eq("cvId", cvId);
		Criterion expNd = Restrictions.eq("msnv", msnv);
		cr.add(expCv);
		cr.add(expNd);
		ArrayList<VTCongVan> vtCongVanList = (ArrayList<VTCongVan>) cr.list();
		session.getTransaction().commit();
		return vtCongVanList;
	}
	public int deleteByCvId(int cvId) {
		session.beginTransaction();
		String sql = "delete from VTCongVan where cvId = :cvId";
		Query query = session.createQuery(sql);
		query.setParameter("cvId", cvId);
		int result = query.executeUpdate();
		session.getTransaction().commit();
		return result;
	}
	public int delete(int cvId, String msnv) {
		session.beginTransaction();
		String sql = "delete from VTCongVan where cvId = :cvId and msnv = :msnv";
		Query query = session.createQuery(sql);
		query.setParameter("cvId", cvId);
		query.setParameter("msnv", msnv);
		int result = query.executeUpdate();
		session.getTransaction().commit();
		return result;
	}
	public String getVaiTro(String vtMa) {
		session.beginTransaction();
		String sql = "select vtTen from VaiTro where vtMa = :vtMa";
		Query query = session.createQuery(sql);
		query.setParameter("vtMa", vtMa);
		String vaiTro = (String) query.list().get(0);
		session.getTransaction().commit();
		return vaiTro;
	}
	public HashMap<String, NguoiDung> getNguoiXuLy(int cvId) {
		session.beginTransaction();
//		Criteria cr = session.createCriteria(VTCongVan.class);
		String sql = "SELECT distinct E.msnv FROM VTCongVan E where E.cvId = " + cvId;
		Query query = session.createQuery(sql);
		ArrayList<String> msnvList = (ArrayList<String>) query.list();
		
		HashMap<String, NguoiDung> nguoiDungHash = new HashMap<String, NguoiDung>();
		NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
		if (msnvList.size() > 0) {
			for (String msnv : msnvList) {
				NguoiDung nguoiDung = nguoiDungDAO.getNguoiDung(msnv);
				nguoiDungHash.put(msnv ,nguoiDung);
			}
		}
		session.getTransaction().commit();
		return nguoiDungHash;
	}
	public HashMap<String, VaiTro> toVaiTro(ArrayList<VTCongVan> vtcvList) {
		HashMap<String, VaiTro>  vaiTroList = new HashMap<String, VaiTro> ();
		for (VTCongVan vtCongVan : vtcvList) {
			
			VaiTro vaiTro = new VaiTroDAO().getVaiTro(vtCongVan.getVtMa());
			String vtMa = vaiTro.getVtMa();
			vaiTroList.put(vtMa, vaiTro);
		}
		return vaiTroList;
	}
	public ArrayList<Integer> getVtIdByCvId(final int cvId) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(VTCongVan.class);
		cr.add(Restrictions.eq("cvId", cvId));
		cr.setProjection(Projections.property("vtMa"));
		ArrayList<Integer> vtMaList = (ArrayList<Integer>) cr.list();
		session.getTransaction().commit();
		return vtMaList;
	}
	public ArrayList<VaiTro> getVaiTroByCvId(final int cvId) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(VaiTro.class);
		Criteria cr1 = session.createCriteria(VTCongVan.class);
		cr1.add(Restrictions.eq("cvId", cvId));
		cr1.setProjection(Projections.property("vtMa"));
		ArrayList<Integer> vtMaList = (ArrayList<Integer>) cr1.list();
		if (vtMaList.size() == 0) {
			session.getTransaction().commit();
			return new ArrayList<VaiTro>();
		}
		cr.add(Restrictions.in("vtMa", vtMaList));
		ArrayList<VaiTro> vaiTroList = (ArrayList<VaiTro>) cr.list();
		
		session.getTransaction().commit();
		return vaiTroList;
	}
	public ArrayList<VaiTro> getVaiTro(final int cvId, String msnv) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(VaiTro.class);
		Criteria cr1 = session.createCriteria(VTCongVan.class);
		cr1.add(Restrictions.eq("cvId", cvId));
		cr1.setProjection(Projections.property("vtMa"));
		cr1.add(Restrictions.eq("msnv", msnv));
		ArrayList<Integer> vtMaList = (ArrayList<Integer>) cr1.list();
		
		if (vtMaList.size() == 0) {
			session.getTransaction().commit();
			return new ArrayList<VaiTro>();
		}
		cr.add(Restrictions.in("vtMa", vtMaList));
		ArrayList<VaiTro> vaiTroList = (ArrayList<VaiTro>) cr.list();
		
		session.getTransaction().commit();
		return vaiTroList;
	}
	public ArrayList<String> getNguoiXl(final int cvId) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(NguoiDung.class);
		
//		ArrayList<Integer> vtMaList = getVtIdByCvId(cvId);
		Criteria cr1 = session.createCriteria(VTCongVan.class);
		cr1.add(Restrictions.eq("cvId", cvId));
		cr1.setProjection(Projections.property("msnv"));
		ArrayList<String> msnvList = (ArrayList<String>) cr1.list();
		
		if (msnvList.size() == 0) {
			session.getTransaction().commit();
			return new ArrayList<String>();
		}
		cr.add(Restrictions.in("msnv", msnvList));
		cr.setProjection(Projections.property("hoTen"));
		ArrayList<String> hoTenList = (ArrayList<String>) cr.list();
		
		session.getTransaction().commit();
		return hoTenList;
	}
	public ArrayList<VTCongVan> getByMsnv(String msnv) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(VTCongVan.class);
		cr.add(Restrictions.eq("msnv", msnv));
		ArrayList<VTCongVan> vtCongVanList = (ArrayList<VTCongVan>) cr.list();
		session.getTransaction().commit();
		return vtCongVanList;
	}
	public void close() {
		session.close();
	}
	public void disconnect() {
		session.disconnect();
	}
	public ArrayList<Integer> groupByMsnvLimit(String msnv, int first, int limit) {
		session.beginTransaction();
		String sql = "select distinct(cvId) from VTCongVan where  ttMa != 'DaGQ' and msnv = :msnv order by vtMa desc";
		Query query = session.createQuery(sql);
		query.setParameter("msnv", msnv);
		query.setFirstResult(first);
		query.setMaxResults(limit);
		
		ArrayList<Integer> congVanIdList = (ArrayList<Integer>) query.list();
		session.getTransaction().commit();
		return congVanIdList;
	}
	public ArrayList<ArrayList<VaiTro>> getVaiTroList(String msnv, ArrayList<Integer> cvIdList) {
		ArrayList<ArrayList<VaiTro>> vaiTroList = new ArrayList<ArrayList<VaiTro>>();
		for (Integer cvId : cvIdList) {
			ArrayList<VaiTro> vtCongVanList = getVaiTro(cvId, msnv);
			vaiTroList.add(vtCongVanList);
		}
		return vaiTroList;
	}
	
	public VTCongVan getVTCongVan(String msnv, int cvId, String vtMa) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(VTCongVan.class);
		cr.add(Restrictions.eq("msnv", msnv));
		cr.add(Restrictions.eq("cvId", cvId));
		VTCongVan vtCongVan = (VTCongVan) cr.list().get(0);
		session.getTransaction().commit();
		return vtCongVan;
	}
	public ArrayList<ArrayList<String>> getTrangThai(String msnv, ArrayList<Integer> cvIdList,
			ArrayList<ArrayList<VaiTro>> vaiTroList) {
		Criteria cr = session.createCriteria("VTCongVan");
		ArrayList<ArrayList<String>> trangThaiList = new ArrayList<ArrayList<String>>();
		int count = 0;
		for (Integer cvId : cvIdList) {
			ArrayList<String> trangThaiCongVanList = new ArrayList<String>();
			ArrayList<VaiTro> vtCongVanList = vaiTroList.get(count);
			for (VaiTro vaiTro : vtCongVanList) {
				VTCongVan vtCongVan = getVTCongVan(msnv, cvId, vaiTro.getVtMa());
				trangThaiCongVanList.add(vtCongVan.getTrangThai().getTtTen());
			}
			trangThaiList.add(trangThaiCongVanList);
			count ++;
		}
		return trangThaiList;
	}
	public ArrayList<String> getTtByCvId(int cvId) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(VTCongVan.class, "vtCongVan");
		cr.createAlias("vtCongVan.trangThai", "trangThai");
		cr.add(Restrictions.eq("cvId", cvId));
		cr.setProjection(Projections.property("trangThai.ttMa"));
		ArrayList<String> trangThaiList = (ArrayList<String>) cr.list();
		session.getTransaction().commit();
		return trangThaiList;
	}
	public int checkTtCongVan(int cvId) {
		ArrayList<String> trangThaiList = getTtByCvId(cvId);
		int check = 1;
		for (String ttMa : trangThaiList) {
			if (ttMa.equals("DGQ") || ttMa.equals("CGQ")) {
				check = 0;
				break;
			}
		}
		return check;
	}
	public static void main(String[] args) {
//		ArrayList<VaiTro> cvIdList = new VTCongVanDAO().getVaiTro(17, "b1203954");
		ArrayList<String> trangThaiList = new VTCongVanDAO().getTtByCvId(51);
		System.out.println(trangThaiList);
	}
}
