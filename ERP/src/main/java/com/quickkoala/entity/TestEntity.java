package com.quickkoala.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member_ship")
public class TestEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private int uidx;
	
	@Column(nullable = false, unique=true, length=30)
	private String uid;	
	
	@Column(nullable = false)
	private String uname;
	
	@Column(nullable = false)
	private String upass;
	
	@Column(nullable = false, length=11)
	private String utel;
	
	@Column(nullable = false)
	private String uemail;
	
	@Column(nullable = false, length=5)
	private String upost;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String uaddr1;
	
	@Column(columnDefinition = "TEXT")
	private String uaddr2;
	
	@Column(columnDefinition = "TEXT")
	private String uimg;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime today;

}
