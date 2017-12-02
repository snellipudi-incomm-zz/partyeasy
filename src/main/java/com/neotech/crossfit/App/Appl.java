package com.neotech.crossfit.App;

/**
 * Created by vinna on 11/5/2017.
 */

import org.hibernate.Session;
import com.neotech.crossfit.persistence.HibernateUtil;
import com.neotech.crossfit.request.LoginRequest;

    public class Appl
    {
       public void saveLoginDetails( )
        {
            System.out.println("Maven + Hibernate + MySQL");
            Session session = HibernateUtil.getSessionFactory().openSession();

            session.beginTransaction();
            LoginRequest logon = new LoginRequest();

            logon.setUserName("test01");
            logon.setPassword("Demo1234");
            logon.setLogin_ID(11);

            session.save(logon);
            session.getTransaction().commit();
        }
    }
