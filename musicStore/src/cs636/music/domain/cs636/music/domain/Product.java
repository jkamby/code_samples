package cs636.music.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.*;

/**
 * One product, i.e., one music CD.
 * Like Murach pg. 649, except:
 * --uses BigDecimal for price, to keep money values precise
 * --is missing some derived attributes we don't really need
 * --has Set<Track> so we can work with Tracks in the service and presentation layers
 * Note that Products and Tracks are invariant objects in this webapp--they never change
 * so it's OK to let the presentation layer use them as is.
 * No setters are actually needed, but can be there for convenience
 */

@Entity
@Table(name="PRODUCT")

public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String code;
	private String description;
	private BigDecimal price;  // using BigDecimal to preserve exact pennies
	private Set<Track> tracks;
	
	public Product(long id, String code, String description, BigDecimal price, Set<Track> tracks) {
		this.id = id;
		this.code = code;
		this.description = description;
		this.price = price;
		this.tracks = tracks;
	}

	public Set<Track> getTracks() {
		return tracks;
	}

	public void setTracks(Set<Track> tracks) {
		this.tracks = tracks;
	}

	public Product() {

	}

	public long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public String getCode() {
		return code;
	}

	public BigDecimal getPrice() {
		return price;
	}
	
	// Since a Product "owns" its tracks, it can use them
	// in its own methods. Here are two examples.
	public Track findTrackbyNumber(int trackNumber){
		for (Track track: tracks){
			if (trackNumber == track.getTrackNumber()) {
				return track;
			}
		}
		return null;
	}
	
	public Track findTrackbyID(int trackID){
		for (Track track: tracks){
			if (trackID == track.getId()) {
				return track;
			}
		}
		return null;
	}

}