package vn.com.freesoft.docmanagement.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import vn.com.freesoft.docmanagement.entity.File;
import vn.com.freesoft.docmanagement.util.HibernateUtil;

public class FileDAO {

	private SessionFactory template;
	private Session session;

	public FileDAO() {
		template = HibernateUtil.getSessionFactory();
		session = template.openSession();
	}

	public File getFile(final int fileId) {
		session.beginTransaction();
		File file = (File) session.get(File.class, fileId);
		session.getTransaction().commit();
		return file;
	}

	public File getByCongVanId(final int cvId) {
		session.beginTransaction();
		Criteria cr = session.createCriteria(File.class);
		cr.add(Restrictions.eq("cvId", cvId));
		File file = null;
		List<File> fileList = cr.list();
		if (fileList.size() > 0)
			file = fileList.get(0);
		session.getTransaction().commit();
		return file;
	}

	public List<File> getAllFile() {
		session.beginTransaction();
		List<File> fileList = (List<File>) session.createCriteria(File.class).list();
		session.getTransaction().commit();
		return fileList;
	}

	public void addFile(File file) {
		session.beginTransaction();
		session.save(file);
		session.getTransaction().commit();
	}

	public void updateFile(File file) {
		session.beginTransaction();
		session.update(file);
		session.getTransaction().commit();
	}

	public void deleteFile(File file) {
		session.beginTransaction();
		session.delete(file);
		session.getTransaction().commit();
	}

	public int getLastInsert() {
		session.beginTransaction();
		Criteria cr = session.createCriteria(File.class).setProjection(Projections.max("fileId"));// max("ctvtId"));
		Integer idOld = (Integer) cr.list().get(0);
		int id = 0;
		if (idOld != null)
			id += idOld + 1;
		else
			id++;

		session.getTransaction().commit();
		return id;
	}

	public void close() {
		if (session.isOpen())
			session.close();
	}

	public void disconnect() {
		if (session.isConnected())
			session.disconnect();
	}

	public void deleteFileByCvID(int id) {

	}

}
