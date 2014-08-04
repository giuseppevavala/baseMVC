package com.domain.POJO;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="digitalItemPOJO")
public class DigitalItemPOJO {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	@Column(name="titolo")
	private String titolo = "";
	@OneToMany (fetch=FetchType.EAGER)
    @JoinColumn(name="videoFile")
	private List<VideoFilePOJO> videoFile;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public List<VideoFilePOJO> getVideoFile() {
		return videoFile;
	}
	public void setVideoFile(List<VideoFilePOJO> videoFile) {
		this.videoFile = videoFile;
	}
	@Override
	public String toString() {
		return "DigitalItemPOJO [id=" + id + ", titolo=" + titolo
				+ ", videoFile=" + videoFile + "]";
	}
	
	
	
}