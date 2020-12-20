package com.ato.dao;

import com.ato.dao.common.HibernateDAO;
import com.ato.model.bo.ObjectAction;
import com.ato.model.dto.ObjectActionDTO;
import com.ato.model.dto.RoleObjectDTO;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.TimestampType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ObjectActionDAO extends HibernateDAO<ObjectAction, Long> {
    @Autowired
    private SessionFactory sessionFactory;

    public List<RoleObjectDTO> getAllObjectAction(ObjectAction obj) {
        Session session = sessionFactory.openSession();
        StringBuilder sql = new StringBuilder("SELECT id, id_objects idObjects,id_action idAction,update_time updateTime FROM object_action where id_objects = :idObjects ");
        SQLQuery query = session.createSQLQuery(sql.toString());
        query.addScalar("id", new LongType());
        query.addScalar("idObjects", new LongType());
        query.addScalar("idAction", new LongType());
        query.addScalar("updateTime", new TimestampType());
        query.setResultTransformer(Transformers.aliasToBean(ObjectAction.class));
        query.setParameter("idObjects", obj.getIdObjects());
        return query.list();
    }

    public void deleleRow(ObjectActionDTO obj) {
        Session session = sessionFactory.openSession();
        StringBuilder sql = new StringBuilder("DELETE from object_action where id_objects = :idObjects and id_action = :idAction");
        SQLQuery query=  session.createSQLQuery(sql.toString());
        query.setParameter("idObjects", obj.getIdObjects());
        query.setParameter("idAction", obj.getIdAction());
        query.executeUpdate();
    }


}
