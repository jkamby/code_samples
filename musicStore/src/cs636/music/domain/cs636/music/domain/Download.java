package cs636.music.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import cs636.music.domain.User;

/**
 * Download 
 * Like Murach, pg. 649 except:
 * --instead of productCode, has ref to the specific Track
 * --exposes download id as a property, in case we want to use it.
 * This class has setters, as a convenience for creating objects,
 * but these objects, once created, never change.
 */

@Entity
@Table(name="DOWNLOAD") 

public class Download implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@TableGenerator(name="DownloadIdGen",
			table = "MUSIC_ID_GEN",
			pkColumnName = "GEN_NAME",
			valueColumnName = "GEN_VAL",
			pkColumnValue = "DownloadId_Gen")
				
	@GeneratedValue(generator="DownloadIdGen")
	@Column(unique=true, nullable=false)
	private long id;
	
	@ManyToOne(fetch=FetchType.EAGER) // @Column(name="USER_ID", nullable=false, length=30)
	private User user;
	
	@Column(name="TRACK_ID", nullable=false, length=30)
	private Track track;

	@Temporal(TemporalType.DATE) // @Column(name="DOWNLOAD_DATE", nullable=false, length=30)
	private Date downloadDate;

	public Download() {
		user = null;
		downloadDate = new Date();
	}

	public long getDownloadId() {
		return id;
	}

	public void setDownloadId(long download_id) {
		this.id = download_id;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User u) {
		user = u;
	}
	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	public Date getDownloadDate() {
		return downloadDate;
	}

	public void setDownloadDate(Date download_date) {
		this.downloadDate = download_date;
	}

}
