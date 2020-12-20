package com.ato.dao;


import com.ato.dao.common.HibernateDAO;
import com.ato.model.bo.Token;
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
public class TokenDAO extends HibernateDAO<Token, Long> {

    @Autowired
    private SessionFactory sessionFactory;


    public void createUpToken(Token obj){
        Session session = sessionFactory.openSession();
        StringBuilder sql = new StringBuilder(
                "INSERT INTO Authentication.token (usertoken, code_token, status) VALUES (:usertoken, :codeToken, :statusToken) ");
        SQLQuery query= session.createSQLQuery(sql.toString());
        query.setParameter("usertoken", obj.getUsertoken());
        query.setParameter("codeToken", obj.getCodeToken());
        query.setParameter("statusToken", obj.getStatus());
        query.executeUpdate();
    }

    public List<Token> searchToken(Token obj) {
        Session session = sessionFactory.openSession();
        SQLQuery query = session.createSQLQuery(
                "SELECT  usertoken, code_token as codeToken, status FROM Authentication.token where usertoken = :userToken and code_token = :codeToken and status = 1" );
        query.addScalar( "usertoken", new StringType() );
        query.addScalar( "codeToken", new StringType() );
        query.addScalar( "status", new LongType() );
        query.setResultTransformer( Transformers.aliasToBean( Token.class ) );
        query.setParameter( "userToken", obj.getUsertoken() );
        query.setParameter( "codeToken", obj.getCodeToken() );
        return query.list();
    }

}
