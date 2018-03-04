
package cs636.music.dao;

import static cs636.music.dao.DBConstants.SYS_TABLE;
import static cs636.music.dao.DBConstants.DOWNLOAD_TABLE;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import cs636.music.domain.Download;

/**
 * 
 * Access Download table through this class. 
 * @author Chung-Hsien (Jacky) Yu
 */
public class DownloadDAO {
	
	private DbDAO dbDAO;
    
	public DownloadDAO(DbDAO db) {
		this.dbDAO = db;
	}
	
	/**
	 * Increase download_id by 1 in the system table
	 * @throws SQLException
	 */
	private void advanceDownloadID()
	{
		EntityManager em = dbDAO.getEM();
		Query q = em.createNativeQuery(" update " + SYS_TABLE
					+ " set download_id = download_id + 1");
		q.executeUpdate();
	}
	
	/**
	 * Get the available download id 
	 * @return the download id available 
	 * @throws SQLException
	 */
	private int getNextDownloadID()
	{
		int nextDID = 0;
		EntityManager em = dbDAO.getEM();
		Query q = em.createNativeQuery(" select download_id from " + SYS_TABLE);
		List rList = q.getResultList();
		if(!rList.isEmpty())
			nextDID = (int) rList.get(0/*"download_id"*/);
		advanceDownloadID(); // the id has been taken, so set +1 for next one
		return nextDID;
	}
	
	/**
	 * insert a download history to download table
	 * @param download
	 * @throws SQLException
	 */
	public void insertDownload(Download download) {
		EntityManager em = dbDAO.getEM();
		em.persist(download);
		/*int download_id = getNextDownloadID();
		download.setDownloadId(download_id);
		String sqlString = "insert into "+ DOWNLOAD_TABLE + " values (" +
		download.getDownloadId() + ", " + 
		download.getUser().getId() + ", " + 
		"current_timestamp" +
		", " +  download.getTrack().getId() + ")" ;
		Query q = em.createNativeQuery(sqlString);
		q.executeInsert();*/
	}
	
	
	/**
	 * find all downlaod history by given a download id
	 * @return all downlaod history in a Set
	 * @throws SQLException
	 */
	public Set<Download> findAllDownloads() {
		// Download download=null;
		// Set<Download> downloads = new HashSet<Download>();
		EntityManager em = dbDAO.getEM();
		String sqlString = "select * from "+ DOWNLOAD_TABLE + 
		     " order by download_date";
		TypedQuery<Download> query = (TypedQuery<Download>) em.createQuery(sqlString);
		List<Download> downloads = query.getResultList();
		return new TreeSet<Download>(downloads);
	}
}
