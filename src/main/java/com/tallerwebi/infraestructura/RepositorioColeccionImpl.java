package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Coleccion;
import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.RepositorioColeccion;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioColeccionImpl implements RepositorioColeccion {

  private SessionFactory sessionFactory;

  @Autowired
  public RepositorioColeccionImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Perfume buscarPerfume(Long id) {
    return this.sessionFactory.getCurrentSession().get(Perfume.class, id);
  }

  @Override
  public void guardarPerfume(Perfume perfume) {
    this.sessionFactory.getCurrentSession().save(perfume);
  }

  @Override
  public void guardarColeccion(Coleccion coleccion) {
    this.sessionFactory.getCurrentSession().save(coleccion);
  }

  @Override
  public List<Perfume> listar() {
    return this.sessionFactory.getCurrentSession()
      .createQuery("SELECT c.perfume FROM Coleccion c", Perfume.class)
      .list();
  }
}
