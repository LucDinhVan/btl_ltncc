package com.ato.dao;

import com.ato.dao.common.HibernateDAO;
import com.ato.model.bo.ProductSizeColor;
import com.ato.model.dto.ProductSizeColorDTO;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductSizeColorDAO extends HibernateDAO<ProductSizeColor, Long> {
    @Autowired
    private SessionFactory sessionFactory;

    public List<ProductSizeColorDTO> doSearch(Long id) {
        Session session = sessionFactory.openSession();
        StringBuilder sql = new StringBuilder("select product_size_color.id, sizes.name nameSize , color.name nameColor, product_size_color.amount from product_size_color \n" +
                "left join sizes on sizes.id = product_size_color.id_size\n" +
                "left join color on color.id = product_size_color.id_color\n" +
                "where id_product = :id");
        SQLQuery query = session.createSQLQuery(sql.toString());
        query.addScalar("id", new LongType());
        query.addScalar("nameSize", new StringType());
        query.addScalar("nameColor", new StringType());
        query.addScalar("amount", new LongType());
        query.setResultTransformer(Transformers.aliasToBean(ProductSizeColorDTO.class));
        query.setParameter("id", id);
        return query.list();
    }

}
