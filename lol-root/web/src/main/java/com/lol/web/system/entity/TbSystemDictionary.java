package com.lol.web.system.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统字典表
 * @author yangli
 */
@Entity
@Table(name = "tb_system_dictionary")
public class TbSystemDictionary implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 901378967062941099L;
	public static final String WORD_ID = "wordId";
	public static final String WORD_CN = "wordCn";
	public static final String WORD_ENGLISH = "wordEnglish";
	public static final String WORD_DETAIL = "wordDetail";
	public static final String CREATETIME = "createTime";
	public static final String UPDATETIME = "updateTime";
	
	/**主键**/
	private Integer wordId;
	/**中文词条名**/
	private String wordCn;
	/**英文词条名**/
	private String wordEnglish;
	/**词条描述**/
	private String wordDetail;
	/**创建时间**/
	private Timestamp createTime;
	/**更新时间**/
	private Timestamp updateTime;

	// Constructors

	/** default constructor */
	public TbSystemDictionary() {
	}

	/** full constructor */
	public TbSystemDictionary(String wordCn, String wordEnglish,
			String wordDetail, Timestamp createTime, Timestamp updateTime) {
		this.wordCn = wordCn;
		this.wordEnglish = wordEnglish;
		this.wordDetail = wordDetail;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "word_id", unique = true, nullable = false)
	public Integer getWordId() {
		return this.wordId;
	}

	public void setWordId(Integer wordId) {
		this.wordId = wordId;
	}

	@Column(name = "word_cn", nullable = false, length = 100)
	public String getWordCn() {
		return this.wordCn;
	}

	public void setWordCn(String wordCn) {
		this.wordCn = wordCn;
	}

	@Column(name = "word_english", nullable = false, length = 100)
	public String getWordEnglish() {
		return this.wordEnglish;
	}

	public void setWordEnglish(String wordEnglish) {
		this.wordEnglish = wordEnglish;
	}

	@Column(name = "word_detail", length = 200)
	public String getWordDetail() {
		return this.wordDetail;
	}

	public void setWordDetail(String wordDetail) {
		this.wordDetail = wordDetail;
	}

	@Column(name = "create_time", nullable = false, length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "update_time", nullable = false, length = 19)
	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}