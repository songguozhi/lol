package com.lol.common.query;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;



import com.lol.common.utils.JaxbMapper;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * 外部查询文件解析器，获得查询语句：hql或sql
 * @author yangli
 *
 */
public class QueryParser {
    private static final Logger LOGGER = Logger.getLogger(QueryParser.class);
    private static Map<String, String> allQueryTemplateMap = null;
    //-----------------------------------------辅助方法开始------------------------------------------------------
    private static String getQueryStringTemplate(String queryName) throws Exception{
        String queryTemplate = "";
        if(QueryConstants.QUERY_SQL_HQL_FROM.equals("xml")){
            if(null == allQueryTemplateMap){
                allQueryTemplateMap = getAllQueryTemplateMap();
            }
            queryTemplate = allQueryTemplateMap.get(queryName);
        }
        return queryTemplate;
    }
    //-----------------------------------------辅助方法结束------------------------------------------------------
    
    /**
     * 
     * @param queryName
     * @param paramMap
     * @return
     * @throws Exception
     */
    public static String getQueryString(String queryName, Map<String, ?> paramMap) throws Exception{
        // 过滤掉value是null或""空的数据
        Map<String, Object> mapTemp = new HashMap<String, Object>();
        if((null != paramMap) && (!paramMap.isEmpty())){
            for(Map.Entry<String, ?> entry : paramMap.entrySet()){
                Object value = entry.getValue();
                if((null != value) && (!"".equals((value + "").trim())) &&
                   (!"%null%".equals(value + "")) && (!"%%".equals(value + ""))&&(!"/".equals(value+""))){
                        mapTemp.put(entry.getKey(), value);
                }
            }
        }
        
        Configuration cfg = new Configuration();
        // 字符串模板加载器
        StringTemplateLoader stringTempLoader = new StringTemplateLoader();
        // 获取Hql或Sql模板
        String queryStringTemplate = getQueryStringTemplate(queryName);
        if((null == queryStringTemplate) || StringUtils.isBlank(queryStringTemplate)){
            throw new Exception("查询名称为：" + queryName + "不存在");
        }
        
        LOGGER.info("query name is :" + queryName);
        stringTempLoader.putTemplate(queryName, queryStringTemplate);
        cfg.setTemplateLoader(stringTempLoader);
        cfg.setDefaultEncoding(QueryConstants.ENCODE_UTF8);
        Template template = cfg.getTemplate(queryName);
        
        StringWriter writer = new StringWriter();
        template.process(mapTemp, writer);
        // 获得查询字符串
        String queryString = writer.toString().trim();
        return queryString;
    }
    
    /**
     * 获得外部查询语句的hql和sql模板，放入map中
     * @return 查询语句map集合
     * @throws Exception
     */
    @SuppressWarnings("unused")
    public static Map<String,String> getAllQueryTemplateMap() throws Exception{
        // map: key:queryName, value:hql或sql
        Map<String, String> queryTemplateMap = new HashMap<String, String>();
        
        URL url = QueryParser.class.getResource(QueryConstants.QUERY_ROOT_FILE_PATH);
        InputStream in = new FileInputStream(new File(url.toURI()));
        
        QueryFiles queryFile = JaxbMapper.fromXml(IOUtils.toString(in, 
                QueryConstants.ENCODE_UTF8), QueryFiles.class);
        // 获取query.xml中的所有file
        List<QueryFiles.Query> queryFileList = queryFile.getQuery();
        if(null != queryFileList){
            for(QueryFiles.Query query : queryFileList){
                String filePath = query.getFile();
                // 根据filepath读取所有*.query.xml的内容
                url = QueryParser.class.getResource(QueryConstants.PATH_PREFIX + filePath);
                in = new FileInputStream(new File(url.toURI()));
                if(null == in){
                    throw new Exception("文件：" + filePath + "不存在");
                }
                
                QueryMapping queryMapping = JaxbMapper.fromXml(
                        IOUtils.toString(in, QueryConstants.ENCODE_UTF8), QueryMapping.class);
                // 将*.query.xml中的所有query name和sql或hql语句放入map
                for(QueryMapping.Query mappingQuery : queryMapping.getQuery()){
                    String queryName = mappingQuery.getName();
                    if(queryTemplateMap.containsKey(queryName)){
                        throw new Exception("外部查询语句的query name重复： " + queryName);
                    }
                    String hql = mappingQuery.getHql();
                    String sql = mappingQuery.getSql();
                    
                    if((StringUtils.isNotBlank(hql)) && (StringUtils.isNotBlank(sql))){
                        throw new Exception("外部查询语句hql和sql语句不能同时存在，query name: " + queryName);
                    }
                    
                    if(StringUtils.isNotBlank(hql)){
                        queryTemplateMap.put(queryName, hql);
                    }else if(StringUtils.isNotBlank(sql)){
                        queryTemplateMap.put(queryName, sql);
                    }else{
                        throw new Exception("外部查询语句的hql或sql不存在，query name: " + queryName);
                    }
                }
            }
        }
        IOUtils.closeQuietly(in);
        //TODO 实际中应该将queryTemplateMap赋值给一个静态变量
        
        return queryTemplateMap;
    }
}

