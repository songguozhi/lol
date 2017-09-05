package com.lol.common.query;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 外部查询语句根文件，对应query.xml
 * @author yangli
 *
 */
@XmlRootElement
public class QueryFiles {
    
private List<Query> query;
    
    @XmlElement
    public List<Query> getQuery() {
        return query;
    }


    public void setQuery(List<Query> query) {
        this.query = query;
    }


    /**
     * 
     *query.xml根节点的子节点包含的属性
     *
     */
    public static class Query {
        
        private String file ;

        @XmlAttribute
        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }
    }
}

