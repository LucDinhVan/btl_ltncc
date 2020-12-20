package com.ato.dao;

import com.ato.dao.common.HibernateDAO;
import com.ato.model.bo.Product;
import com.ato.model.dto.ProductDTO;
import com.ato.utils.ValidateUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public class ProductsDAO extends HibernateDAO<Product, Long> {
    @Autowired
    private SessionFactory sessionFactory;

    public List<ProductDTO> doSearch(ProductDTO obj) {
        Session session = sessionFactory.openSession();
        StringBuilder sql = new StringBuilder("SELECT product.id id, name, code,  quantity, cost, rate, status,update_time updateTime, relase_date relaseDate," +
                " (select name from object where id = product.id_objects) parenObject, (select id from object where id = product.id_objects) idParen ,  sum(amount) totalProduct" +
                " FROM product " +
                " left join product_size_color pzc on product.id = pzc.id_product  " +
                "  where 1 = 1 ");
        if (StringUtils.isNotEmpty(obj.getCode())) {
            sql.append(" and upper(code) LIKE upper(:code) escape '&'  ");
        }
        if (StringUtils.isNotEmpty(obj.getName())) {
            sql.append(" and upper(name) LIKE upper(:name) escape '&'  ");
        }
        if (obj.getStatus() != null) {
            sql.append(" and status = :status ");
        }
        sql.append(" group by product.id, name, code,  quantity, cost, " +
                " rate, status, updateTime, relaseDate, parenObject ");
        StringBuilder sqlCount = new StringBuilder("SELECT COUNT(*) FROM (");
        sqlCount.append(sql.toString());
        sqlCount.append(")");
        sqlCount.append(" as roles ");
        SQLQuery query = session.createSQLQuery(sql.toString());
        SQLQuery queryCount = session.createSQLQuery(sqlCount.toString());

        query.addScalar("id", new LongType());
        query.addScalar("name", new StringType());
        query.addScalar("code", new StringType());
        query.addScalar("quantity", new IntegerType());
        query.addScalar("cost", new BigDecimalType());
        query.addScalar("rate", new IntegerType());
        query.addScalar("status", new LongType());
        query.addScalar("updateTime", new TimestampType());
        query.addScalar("relaseDate", new TimestampType());
        query.addScalar("parenObject", new StringType());
        query.addScalar("idParen", new LongType());
        query.addScalar("totalProduct", new LongType());
        query.setResultTransformer(Transformers.aliasToBean(ProductDTO.class));
        if (StringUtils.isNotEmpty(obj.getName())) {
            query.setParameter("name", "%" + ValidateUtils.validateKeySearch(obj.getName()) + "%");
            queryCount.setParameter("name", "%" + ValidateUtils.validateKeySearch(obj.getName()) + "%");
        }
        if (StringUtils.isNotEmpty(obj.getCode())) {
            query.setParameter("code", "%" + ValidateUtils.validateKeySearch(obj.getCode()) + "%");
            queryCount.setParameter("code", "%" + ValidateUtils.validateKeySearch(obj.getCode()) + "%");
        }
        if (obj.getStatus() != null) {
            query.setParameter("status", obj.getStatus());
            queryCount.setParameter("status", obj.getStatus());
        }
        if (obj.getPage() != null && obj.getPageSize() != null) {
            query.setFirstResult((obj.getPage().intValue()) * obj.getPageSize().intValue());
            query.setMaxResults(obj.getPageSize().intValue());
            obj.setTotalRecord(((BigInteger) queryCount.uniqueResult()).intValue());
        }
        return query.list();
    }
}
