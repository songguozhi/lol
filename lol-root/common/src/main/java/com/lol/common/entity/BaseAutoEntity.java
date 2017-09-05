package com.lol.common.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 抽象实体基类，数据库自增主键
 * @author yangli
 *
 * @param <ID>
 */
@MappedSuperclass
public abstract class BaseAutoEntity<ID extends Serializable>{

	public static final String ID = "id";
	private ID id;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public ID getId() {
		return id;
	}
	public void setId(ID id) {
		this.id = id;
	}
	
	
}
