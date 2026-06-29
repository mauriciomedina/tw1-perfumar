package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Local;
import com.tallerwebi.dominio.RepositorioLocal;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioLocal")
public class RepositorioLocalImpl implements RepositorioLocal {

  private SessionFactory sessionFactory;

  //  conexión a la base de datos
  @Autowired
  public RepositorioLocalImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public List<Local> listarTodos() {
    // Ejecutamos la consulta rápida para traer todos los registros de la tabla Local
    // (un 'SELECT * FROM Local')
    return this.sessionFactory.getCurrentSession().createCriteria(Local.class).list();
  }
}
