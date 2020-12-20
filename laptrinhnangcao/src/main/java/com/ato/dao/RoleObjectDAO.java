package com.ato.dao;

import com.ato.dao.common.HibernateDAO;
import com.ato.model.bo.RoleObject;
import com.ato.model.dto.RoleObjectDTO;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleObjectDAO extends HibernateDAO<RoleObject, Long> {
    @Autowired
    private SessionFactory sessionFactory;
    public List<RoleObjectDTO> getAllRoleObject(RoleObjectDTO obj) {
        Session session = sessionFactory.openSession();
        StringBuilder sql = new StringBuilder("SELECT id_role idRole,id_object idObject,id_action idAction FROM role_object where id_object = 2 and id_role = 1 ");
        SQLQuery query = session.createSQLQuery(sql.toString());
        query.addScalar("idRole", new LongType());
        query.addScalar("idObject", new LongType());
        query.addScalar("idAction", new LongType());
        query.setResultTransformer(Transformers.aliasToBean(RoleObjectDTO.class));
        query.setParameter("name", obj.getIdRole());
        return query.list();
    }

    public void deleteRoleObject(Long id){
        Session session = sessionFactory.openSession();
        StringBuilder sql = new StringBuilder("" +
                "DELETE FROM `ShopQuanAo`.`role_object` WHERE (`id_role` = :id) ");
        SQLQuery query=  session.createSQLQuery(sql.toString());
        query.setParameter("id", id);
        query.executeUpdate();
    }


}
