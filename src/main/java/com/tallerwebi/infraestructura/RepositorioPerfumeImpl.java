package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.RepositorioPerfume;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioPerfume")
public class RepositorioPerfumeImpl implements RepositorioPerfume {

  private SessionFactory sessionFactory;

  @Autowired
  public RepositorioPerfumeImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Perfume buscar(String nombre) {
    return (Perfume) sessionFactory
      .getCurrentSession()
      .createCriteria(Perfume.class)
      .add(Restrictions.eq("nombre", nombre))
      .uniqueResult();
  }

  @Override
  public void guardar(Perfume perfume) {
    sessionFactory.getCurrentSession().save(perfume);
  }

  @Override
  public void modificar(Perfume perfume) {
    sessionFactory.getCurrentSession().update(perfume);
  }

  @Override
  public List<Perfume> listar() {
    return sessionFactory.getCurrentSession().createQuery("from Perfume", Perfume.class).list();
  }
}
