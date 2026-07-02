package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Favorito;
import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.RepositorioFavorito;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioFavoritoImpl implements RepositorioFavorito {

  private SessionFactory sessionFactory;

  @Autowired
  public RepositorioFavoritoImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Perfume buscarPerfume(Long id) {
    return this.sessionFactory.getCurrentSession().get(Perfume.class, id);
  }

  @Override
  public void guardarFavorito(Favorito favorito) {
    this.sessionFactory.getCurrentSession().save(favorito);
  }

  @Override
  public List<Perfume> listar(Long idUsuario) {
    return this.sessionFactory.getCurrentSession()
      .createQuery(
        "SELECT f.perfume FROM Favorito f WHERE f.usuario.id = :idUsuario",
        Perfume.class
      )
      .setParameter("idUsuario", idUsuario)
      .list();
  }

  @Override
  public void eliminar(Long idUsuario, Long idPerfume) {
    this.sessionFactory.getCurrentSession()
      .createQuery(
        "DELETE FROM Favorito f WHERE f.usuario.id = :idUsuario AND f.perfume.id = :idPerfume"
      )
      .setParameter("idUsuario", idUsuario)
      .setParameter("idPerfume", idPerfume)
      .executeUpdate();

    this.sessionFactory.getCurrentSession().flush();
    this.sessionFactory.getCurrentSession().clear();
  }

  @Override
  public boolean existe(Long idUsuario, Long idPerfume) {
    Long cantidad =
      this.sessionFactory.getCurrentSession()
        .createQuery(
          "SELECT COUNT(f) FROM Favorito f WHERE f.usuario.id = :idUsuario AND f.perfume.id = :idPerfume",
          Long.class
        )
        .setParameter("idUsuario", idUsuario)
        .setParameter("idPerfume", idPerfume)
        .uniqueResult();

    return cantidad != null && cantidad > 0;
  }
}
