package com.domain.POJO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="videoFilePOJO")
public class VideoFilePOJO {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	@Column(name="videoPath")
	private String path = "";
	@Column(name="duration")
	private long duration = -1;
	@Column(name="thumbnail")
	private String thumbnail = "";
	@Column(name="height")
	private int height = -1;
	@Column(name="width")
	private int width = -1;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	@Override
	public String toString() {
		return "VideoFilePOJO [id=" + id + ", path=" + path + ", duration="
				+ duration + ", thumbnail=" + thumbnail + ", height=" + height
				+ ", width=" + width + "]";
	}
	
	
}