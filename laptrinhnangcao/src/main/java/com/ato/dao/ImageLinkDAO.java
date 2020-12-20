package com.ato.dao;

import com.ato.dao.common.HibernateDAO;
import com.ato.model.bo.ImageLink;
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
public class ImageLinkDAO extends HibernateDAO<ImageLink, Long> {
    @Autowired
    private SessionFactory sessionFactory;

}
