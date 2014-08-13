package com.domain.DAO;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;




public class BaseDAO<T>{
    
	private Class<T> clazz;
	
	public void PropertyImplementationBinder(Class<T> clazz){
		this.clazz = clazz;
	}
	
	private Class<T> getInterfaceClass() {
		return clazz;
	}
	
    @Resource(name="sessionFactory")
    protected SessionFactory sessionFactory;

    
    public void setSessionFactory(SessionFactory sessionFactory) {
           this.sessionFactory = sessionFactory;
    }
   
    protected Session getSession(){
           Session session = sessionFactory.openSession();
           session.beginTransaction();
           return session;
    }     
    
    public void addObject(T objectPOJO) {
		Session session = getSession();	
    	
    	session.save(objectPOJO);
    	session.getTransaction().commit();
	}
    
    @SuppressWarnings("unchecked")
	public T getObjWithLikeCondition(String key, Object value){
		Session session = getSession();
        Object uniqueResult = session.createCriteria(getInterfaceClass())
  			.add(Restrictions.like(key, value)).uniqueResult();
        session.getTransaction().commit();
        session.close();
        return (T) uniqueResult;
	}
    
    
    @SuppressWarnings("unchecked")
	public List<T> getAll(){
		Session session = getSession();
        List<T> list = session.createCriteria(getInterfaceClass()).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();

        session.getTransaction().commit();
        session.close();
        return list;
	}
   
    public void update(T object) {
    	Session session = getSession();	
        session.update(object);
        session.getTransaction().commit();
        session.close();
    }

}
