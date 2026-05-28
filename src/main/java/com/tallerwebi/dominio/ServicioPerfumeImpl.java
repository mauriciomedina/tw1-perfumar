package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.RepositorioPerfumeImpl;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("servicioPerfume")
@Transactional
public class ServicioPerfumeImpl implements ServicioPerfume {

  private RepositorioPerfume repositorioPerfume;

  @Autowired
  public ServicioPerfumeImpl(RepositorioPerfumeImpl repositorioPerfume) {
    this.repositorioPerfume = repositorioPerfume;
  }

  @Override
  public Perfume consultarPerfume(String nombre) {
    return repositorioPerfume.buscar(nombre);
  }

  @Override
  public void grabar(Perfume perfume) throws Exception {
    Perfume perfumeExistente = repositorioPerfume.buscar(perfume.getNombre());
    if (perfumeExistente != null) {
      throw new Exception();
    }
    repositorioPerfume.guardar(perfume);
  }

  @Override
  public List<Perfume> listar() {
    return this.repositorioPerfume.listar();
  }
}
