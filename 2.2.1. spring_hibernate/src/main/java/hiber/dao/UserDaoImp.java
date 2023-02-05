package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUser(String model, int series) {
      Session session = sessionFactory.openSession();
      session.getTransaction().begin();
      TypedQuery<Car> query = session.createQuery("from Car where model = :model and series = :series");
      query.setParameter("model", model);
      query.setParameter("series", series);
      Long x = query.getSingleResult().getId();
      TypedQuery query2 = session.createQuery("from User where car_id = :car_id", User.class);
      query2.setParameter("car_id", x);
      User user = (User) query2.getSingleResult();
      session.getTransaction().commit();
      return user;
   }

}
