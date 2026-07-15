const CHAT_STORAGE_KEY = "perfumar_chat_historial";
const CHAT_MAX_HISTORIAL = 20;
const CHAT_API_URL = "/spring/api/gemini/preguntar";

function chatEscaparHtml(texto) {
  const contenedor = document.createElement("div");
  contenedor.textContent = texto;
  return contenedor.innerHTML;
}

function chatFormatearTexto(texto) {
  return chatEscaparHtml(texto)
    .replace(/\*\*(.+?)\*\*/g, "<strong>$1</strong>")
    .replace(
      /\[([^\][]+)\]\(\/spring\/especificacion\?id=(\d+)\)/g,
      "<a href=\"/spring/especificacion?id=$2\" class=\"pa-chat-link\">$1</a>"
    )
    .replace(/\n/g, "<br>");
}

function chatLeerHistorial() {
  try {
    const guardado = localStorage.getItem(CHAT_STORAGE_KEY);
    return guardado ? JSON.parse(guardado) : [];
  } catch (error) {
    console.error("[Chat]", error);
    return [];
  }
}

function chatGuardarHistorial(historial) {
  const recortado = historial.slice(-CHAT_MAX_HISTORIAL);
  localStorage.setItem(CHAT_STORAGE_KEY, JSON.stringify(recortado));
  return recortado;
}

class ChatComponent extends HTMLElement {
  constructor() {
    super();
    this.enviando = false;
  }

  connectedCallback() {
    this.innerHTML = `
      <style>
        .pa-chat-launcher {
          position: fixed;
          bottom: 24px;
          right: 24px;
          width: 56px;
          height: 56px;
          border-radius: 50%;
          background: var(--primary-black, #000);
          color: #fff;
          border: none;
          display: flex;
          align-items: center;
          justify-content: center;
          cursor: pointer;
          box-shadow: 0 4px 16px rgba(0, 0, 0, 0.25);
          z-index: 1050;
        }
        .pa-chat-launcher .material-symbols-outlined { font-size: 26px; }
        .pa-chat-panel {
          position: fixed;
          bottom: 92px;
          right: 24px;
          width: 340px;
          max-width: calc(100vw - 32px);
          height: 460px;
          max-height: calc(100vh - 140px);
          background: #fff;
          border-radius: 16px;
          box-shadow: 0 8px 30px rgba(0, 0, 0, 0.2);
          display: none;
          flex-direction: column;
          overflow: hidden;
          z-index: 1050;
          font-family: var(--font-body, sans-serif);
        }
        .pa-chat-panel.pa-chat-abierto { display: flex; }
        .pa-chat-header {
          background: var(--primary-black, #000);
          color: #fff;
          padding: 14px 16px;
          display: flex;
          align-items: center;
          justify-content: space-between;
        }
        .pa-chat-header-titulo {
          font-family: var(--font-display, serif);
          font-size: 16px;
        }
        .pa-chat-header-acciones button {
          background: none;
          border: none;
          color: #fff;
          opacity: 0.8;
          cursor: pointer;
          padding: 2px 4px;
        }
        .pa-chat-header-acciones button:hover { opacity: 1; }
        .pa-chat-mensajes {
          flex: 1;
          overflow-y: auto;
          padding: 14px;
          display: flex;
          flex-direction: column;
          gap: 10px;
          background: #f9f9f9;
        }
        .pa-chat-burbuja {
          max-width: 85%;
          padding: 9px 12px;
          border-radius: 12px;
          font-size: 13.5px;
          line-height: 1.4;
          word-wrap: break-word;
        }
        .pa-chat-burbuja.pa-chat-user {
          align-self: flex-end;
          background: var(--primary-black, #000);
          color: #fff;
          border-bottom-right-radius: 3px;
        }
        .pa-chat-burbuja.pa-chat-model {
          align-self: flex-start;
          background: #eee;
          color: #1a1c1c;
          border-bottom-left-radius: 3px;
        }
        .pa-chat-burbuja.pa-chat-error {
          align-self: flex-start;
          background: #fde8e8;
          color: #8a1f1f;
        }
        .pa-chat-link {
          color: inherit;
          font-weight: 600;
          text-decoration: underline;
        }
        .pa-chat-link:hover { opacity: 0.8; }
        .pa-chat-form {
          display: flex;
          gap: 8px;
          padding: 12px;
          border-top: 1px solid #eee;
        }
        .pa-chat-input {
          flex: 1;
          border: 1px solid #ddd;
          border-radius: 20px;
          padding: 8px 14px;
          font-size: 13.5px;
          outline: none;
        }
        .pa-chat-input:focus { border-color: #000; }
        .pa-chat-enviar {
          background: var(--primary-black, #000);
          color: #fff;
          border: none;
          border-radius: 50%;
          width: 36px;
          height: 36px;
          display: flex;
          align-items: center;
          justify-content: center;
          cursor: pointer;
          flex-shrink: 0;
        }
        .pa-chat-enviar:disabled { opacity: 0.5; cursor: not-allowed; }
      </style>

      <button class="pa-chat-launcher" id="pa-chat-launcher" aria-label="Abrir chat con la IA">
        <span class="material-symbols-outlined">forum</span>
      </button>

      <div class="pa-chat-panel" id="pa-chat-panel">
        <div class="pa-chat-header">
          <span class="pa-chat-header-titulo">Asistente PerfumAR</span>
          <div class="pa-chat-header-acciones">
            <button id="pa-chat-limpiar" title="Limpiar conversación" aria-label="Limpiar conversación">
              <span class="material-symbols-outlined" style="font-size: 18px;">delete_sweep</span>
            </button>
            <button id="pa-chat-cerrar" title="Cerrar" aria-label="Cerrar chat">
              <span class="material-symbols-outlined" style="font-size: 18px;">close</span>
            </button>
          </div>
        </div>
        <div class="pa-chat-mensajes" id="pa-chat-mensajes"></div>
        <form class="pa-chat-form" id="pa-chat-form">
          <input class="pa-chat-input" id="pa-chat-input" type="text" placeholder="Contanos qué buscás..." autocomplete="off" />
          <button class="pa-chat-enviar" id="pa-chat-boton-enviar" type="submit" aria-label="Enviar">
            <span class="material-symbols-outlined" style="font-size: 18px;">send</span>
          </button>
        </form>
      </div>
    `;

    this.panel = this.querySelector("#pa-chat-panel");
    this.contenedorMensajes = this.querySelector("#pa-chat-mensajes");
    this.input = this.querySelector("#pa-chat-input");
    this.botonEnviar = this.querySelector("#pa-chat-boton-enviar");

    this.querySelector("#pa-chat-launcher").addEventListener("click", () => this.alternarPanel());
    this.querySelector("#pa-chat-cerrar").addEventListener("click", () => this.cerrarPanel());
    this.querySelector("#pa-chat-limpiar").addEventListener("click", () => this.limpiarConversacion());
    this.querySelector("#pa-chat-form").addEventListener("submit", (evento) => {
      evento.preventDefault();
      this.enviarMensaje();
    });

    this.renderizarHistorialGuardado();
  }

  alternarPanel() {
    this.panel.classList.toggle("pa-chat-abierto");
    if (this.panel.classList.contains("pa-chat-abierto")) {
      this.input.focus();
      this.scrollAlFinal();
    }
  }

  cerrarPanel() {
    this.panel.classList.remove("pa-chat-abierto");
  }

  limpiarConversacion() {
    localStorage.removeItem(CHAT_STORAGE_KEY);
    this.contenedorMensajes.innerHTML = "";
  }

  renderizarHistorialGuardado() {
    const historial = chatLeerHistorial();
    historial.forEach((turno) => this.agregarBurbuja(turno.rol, turno.texto));
    this.scrollAlFinal();
  }

  agregarBurbuja(rol, texto, claseExtra) {
    const burbuja = document.createElement("div");
    const clase = claseExtra || (rol === "user" ? "pa-chat-user" : "pa-chat-model");
    burbuja.className = `pa-chat-burbuja ${clase}`;
    burbuja.innerHTML = chatFormatearTexto(texto);
    this.contenedorMensajes.appendChild(burbuja);
    return burbuja;
  }

  scrollAlFinal() {
    this.contenedorMensajes.scrollTop = this.contenedorMensajes.scrollHeight;
  }

  async enviarMensaje() {
    const mensaje = this.input.value.trim();
    if (mensaje === "" || this.enviando) return;

    const historialPrevio = chatLeerHistorial();

    this.agregarBurbuja("user", mensaje);
    chatGuardarHistorial([...historialPrevio, { rol: "user", texto: mensaje }]);
    this.input.value = "";
    this.scrollAlFinal();

    this.enviando = true;
    this.botonEnviar.disabled = true;
    const burbujaCargando = this.agregarBurbuja("model", "Escribiendo...", "pa-chat-model");
    this.scrollAlFinal();

    try {
      const respuesta = await fetch(CHAT_API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ pregunta: mensaje, historial: historialPrevio }),
      });

      const datos = await respuesta.json().catch(() => null);

      burbujaCargando.remove();

      if (!respuesta.ok || !datos || !datos.respuesta) {
        this.agregarBurbuja(
          "model",
          "No pude procesar tu consulta en este momento. Probá de nuevo en unos instantes.",
          "pa-chat-error"
        );
      } else {
        this.agregarBurbuja("model", datos.respuesta);
        const historialActualizado = chatLeerHistorial();
        chatGuardarHistorial([
          ...historialActualizado,
          { rol: "model", texto: datos.respuesta },
        ]);
      }
    } catch (error) {
      console.error("[Chat]", error);
      burbujaCargando.remove();
      this.agregarBurbuja(
        "model",
        "Hubo un problema de conexión. Probá de nuevo en unos instantes.",
        "pa-chat-error"
      );
    } finally {
      this.enviando = false;
      this.botonEnviar.disabled = false;
      this.scrollAlFinal();
    }
  }
}

customElements.define("app-chat", ChatComponent);