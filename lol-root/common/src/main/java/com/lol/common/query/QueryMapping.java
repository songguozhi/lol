package com.lol.common.query;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * <p>describing：外部查询语句映射类，外部查询语句包含文件，对应*.query.xml</p>
 * @author yangli
 *
 */
@XmlRootElement
public class QueryMapping {
    /**
     * 每条查询的list集合
     */
    private List<Query> query;

    @XmlElement
    public List<Query> getQuery() {
        return query;
    }

    public void setQuery(List<Query> query) {
        this.query = query;
    }



    /**
     * 每一个查询，作为内部类存在
     *
     */
    public static class Query{
        /**
         * 查询名称，必须唯一
         */
        private String name;
        
        /**
         * hql语句，与sql语句互斥，只能存在一种
         */
        private String hql;
        
        /**
         * sql语句，与hql语句互斥，只能存在一种
         */
        private String sql;

        @XmlAttribute
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @XmlElement
        public String getHql() {
            return hql;
        }

        public void setHql(String hql) {
            this.hql = hql;
        }

        @XmlElement
        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }
    }
}
    
