package com.lol.common.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * 抽样实体基类，自动生成id为uuid
 * @author yangli
 *
 * @param <ID>
 */
@MappedSuperclass
public abstract  class BaseUuidEntity<ID extends Serializable> {

	public static final String ID = "id";
	
	public ID id;
	
	@Id
    @GeneratedValue(generator= "uuid")
    @GenericGenerator(name="uuid", strategy="com.lol.common.hibernate.UuidStrategy")
    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
