/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_access_layer;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author BritishWaldo
 */
public class DBUtil
{
    private static final EntityManagerFactory entityFactory = Persistence.createEntityManagerFactory("User_Manager_PU");
    
    public static EntityManagerFactory getEntityFactory()
    {
        return DBUtil.entityFactory;
    }
}
