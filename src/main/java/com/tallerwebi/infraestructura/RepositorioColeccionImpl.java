package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Coleccion;
import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.RepositorioColeccion;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioColeccion")
public class RepositorioColeccionImpl implements RepositorioColeccion {

  private SessionFactory sessionFactory;

  @Autowired
  public RepositorioColeccionImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public void guardarPerfume(Perfume perfume) {
    sessionFactory.getCurrentSession().save(perfume);
  }

  @Override
  public void guardarColeccion(Coleccion coleccion) {
    sessionFactory.getCurrentSession().save(coleccion);
  }

  @Override
  public List<Perfume> listar() {
    return sessionFactory.getCurrentSession().createQuery("from Perfume", Perfume.class).list();
  }
}
