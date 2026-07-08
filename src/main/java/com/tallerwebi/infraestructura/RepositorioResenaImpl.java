package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.RepositorioResena;
import com.tallerwebi.dominio.Resena;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioResenaImpl implements RepositorioResena {

  private SessionFactory sessionFactory;

  @Autowired
  public RepositorioResenaImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Perfume buscarPerfume(Long id) {
    return this.sessionFactory.getCurrentSession().get(Perfume.class, id);
  }

  @Override
  public void guardar(Resena resena) {
    this.sessionFactory.getCurrentSession().save(resena);
  }

  @Override
  public List<Resena> listarPorPerfume(Long idPerfume) {
    return this.sessionFactory.getCurrentSession()
      .createQuery(
        "FROM Resena r WHERE r.perfume.id = :idPerfume ORDER BY r.fecha DESC",
        Resena.class
      )
      .setParameter("idPerfume", idPerfume)
      .list();
  }

  @Override
  public Double promedioDePuntuacion(Long idPerfume) {
    return this.sessionFactory.getCurrentSession()
      .createQuery(
        "SELECT AVG(r.puntuacion) FROM Resena r WHERE r.perfume.id = :idPerfume",
        Double.class
      )
      .setParameter("idPerfume", idPerfume)
      .uniqueResult();
  }
}
