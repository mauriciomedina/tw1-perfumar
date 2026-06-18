package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

public class ControladorLoginTest {

  private ControladorLogin controladorLogin;
  private Usuario usuarioMock;
  private DatosLogin datosLoginMock;
  private HttpServletRequest requestMock;
  private HttpSession sessionMock;
  private ServicioLogin servicioLoginMock;

  @BeforeEach
  public void init() {
    datosLoginMock = new DatosLogin("dami@unlam.com", "123");
    usuarioMock = mock(Usuario.class);
    when(usuarioMock.getEmail()).thenReturn("dami@unlam.com");
    requestMock = mock(HttpServletRequest.class);
    sessionMock = mock(HttpSession.class);
    servicioLoginMock = mock(ServicioLogin.class);
    controladorLogin = new ControladorLogin(servicioLoginMock);
  }

  @Test
  public void loginConUsuarioYPasswordInorrectosDeberiaLlevarALoginNuevamente() {
    // preparacion
    when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(null);

    // ejecucion
    ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);

    // validacion
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
    assertThat(
      modelAndView.getModel().get("error").toString(),
      equalToIgnoringCase("Usuario o clave incorrecta")
    );
    verify(sessionMock, times(0)).setAttribute("ROL", "ADMIN");
  }

  @Test
  public void loginConUsuarioYPasswordCorrectosDeberiaLLevarAHome() {
    // preparacion
    Usuario usuarioEncontradoMock = mock(Usuario.class);
    when(usuarioEncontradoMock.getRol()).thenReturn("ADMIN");

    when(requestMock.getSession()).thenReturn(sessionMock);
    when(servicioLoginMock.consultarUsuario(anyString(), anyString()))
      .thenReturn(usuarioEncontradoMock);

    // ejecucion
    ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);

    // validacion
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
    verify(sessionMock, times(1)).setAttribute("ROL", usuarioEncontradoMock.getRol());
  }

  @Test
  public void registrameSiUsuarioNoExisteDeberiaCrearUsuarioYVolverAlLogin()
    throws UsuarioExistente {
    // ejecucion
    ModelAndView modelAndView = controladorLogin.registrarme(usuarioMock);

    // validacion
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    verify(servicioLoginMock, times(1)).registrar(usuarioMock);
  }

  @Test
  public void registrarmeSiUsuarioExisteDeberiaVolverAFormularioYMostrarError()
    throws UsuarioExistente {
    // preparacion
    doThrow(UsuarioExistente.class).when(servicioLoginMock).registrar(usuarioMock);

    // ejecucion
    ModelAndView modelAndView = controladorLogin.registrarme(usuarioMock);

    // validacion
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
    assertThat(
      modelAndView.getModel().get("error").toString(),
      equalToIgnoringCase("El usuario ya existe")
    );
  }

  @Test
  public void errorEnRegistrarmeDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente {
    // preparacion
    doThrow(RuntimeException.class).when(servicioLoginMock).registrar(usuarioMock);

    // ejecucion
    ModelAndView modelAndView = controladorLogin.registrarme(usuarioMock);

    // validacion
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
    assertThat(
      modelAndView.getModel().get("error").toString(),
      equalToIgnoringCase("Error al registrar el nuevo usuario")
    );
  }

  @Test
  public void irALoginDeberiaRetornarVistaLoginConDatosLogin() {
    // ejecucion
    ModelAndView modelAndView = controladorLogin.irALogin();

    // validacion
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
    assertThat(modelAndView.getModel().get("datosLogin"), instanceOf(DatosLogin.class));
  }

  @Test
  public void nuevoUsuarioDeberiaRetornarVistaNuevoUsuarioConUsuarioVacio() {
    // ejecucion
    ModelAndView modelAndView = controladorLogin.nuevoUsuario();

    // validacion
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
    assertThat(modelAndView.getModel().get("usuario"), instanceOf(Usuario.class));
  }

  @Test
  public void irAHomeDeberiaRetornarVistaHome() {
    // ejecucion
    ModelAndView modelAndView = controladorLogin.irAHome();

    // validacion
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("home"));
  }

  @Test
  public void inicioDeberiaRedirigirALogin() {
    // ejecucion
    ModelAndView modelAndView = controladorLogin.inicio();

    // validacion
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
  }

  @Test
  public void cerrarSesionDeberiaInvalidarLaSesionYRedirigirALogin() {
    // preparacion
    when(requestMock.getSession()).thenReturn(sessionMock);

    // ejecucion
    ModelAndView modelAndView = controladorLogin.cerrarSesion(requestMock);

    // validacion
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    verify(sessionMock).invalidate();
  }

  @Test
  public void irAPerfilDeberiaRetornarVistaPerfilConDatosDelUsuarioDeLaSesion() {
    // preparacion
    when(requestMock.getSession()).thenReturn(sessionMock);
    when(sessionMock.getAttribute("NOMBRE")).thenReturn("Juan");
    when(sessionMock.getAttribute("EMAIL")).thenReturn("juan@test.com");
    when(sessionMock.getAttribute("CIUDAD")).thenReturn("Córdoba");
    when(sessionMock.getAttribute("PAIS")).thenReturn("AR");

    // ejecucion
    ModelAndView modelAndView = controladorLogin.irAPerfil(requestMock);

    // validacion
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("perfil"));
    assertThat(modelAndView.getModel().get("usuario"), instanceOf(Usuario.class));
  }

  @Test
  public void actualizarPerfilDeberiaGuardarCambiosActualizarSesionYRedirigirAPerfil() {
    // preparacion
    Usuario usuario = new Usuario();
    usuario.setNombre("Nuevo Nombre");
    usuario.setEmail("nuevo@test.com");
    usuario.setCiudad("Rosario");
    usuario.setPais("AR");

    when(requestMock.getSession()).thenReturn(sessionMock);
    when(sessionMock.getAttribute("USUARIO_ID")).thenReturn(1L);

    // ejecucion
    ModelAndView modelAndView = controladorLogin.actualizarPerfil(usuario, requestMock);

    // validacion
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/perfil"));
    verify(servicioLoginMock, times(1)).actualizar(any(Usuario.class));
    verify(sessionMock).setAttribute("CIUDAD", "Rosario");
    verify(sessionMock).setAttribute("PAIS", "AR");
    verify(sessionMock).setAttribute("NOMBRE", "Nuevo Nombre");
    verify(sessionMock).setAttribute("EMAIL", "nuevo@test.com");
  }
}
