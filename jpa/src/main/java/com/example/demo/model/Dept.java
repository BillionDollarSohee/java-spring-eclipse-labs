package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "DEPT")
public class Dept {
	@Id
	private int deptno;
	private String dname;
	private String loc;
}
