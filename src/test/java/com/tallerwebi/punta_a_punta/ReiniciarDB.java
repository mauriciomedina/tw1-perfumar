package com.tallerwebi.punta_a_punta;

import java.io.IOException;

public class ReiniciarDB {

  public static void limpiarBaseDeDatos() {
    try {
      String dbHost = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
      String dbPort = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT") : "3306";
      String dbName = System.getenv("DB_NAME") != null ? System.getenv("DB_NAME") : "tallerwebi";
      String dbUser = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "user";
      String dbPassword = System.getenv("DB_PASSWORD") != null
        ? System.getenv("DB_PASSWORD")
        : "user";

      // Coleccion, Favorito y Resena tienen FK a Usuario: hay que borrarlas
      // primero o el DELETE FROM Usuario falla por constraint violation.
      String sqlCommands =
        "DELETE FROM Coleccion;\n" +
        "DELETE FROM Favorito;\n" +
        "DELETE FROM Resena;\n" +
        "DELETE FROM Usuario;\n" +
        "ALTER TABLE Usuario AUTO_INCREMENT = 1;\n" +
        "INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);";

      // Se pasa como array de argumentos (no como un único string de shell) para
      // que funcione en cualquier sistema operativo sin depender de /bin/bash.
      ProcessBuilder processBuilder = new ProcessBuilder(
        "docker",
        "exec",
        "tallerwebi-mysql",
        "mysql",
        "-h",
        dbHost,
        "-P",
        dbPort,
        "-u",
        dbUser,
        "-p" + dbPassword,
        dbName,
        "-e",
        sqlCommands
      );
      processBuilder.redirectErrorStream(true);

      Process process = processBuilder.start();
      int exitCode = process.waitFor();

      if (exitCode == 0) {
        System.out.println("Base de datos limpiada exitosamente");
      } else {
        System.err.println("Error al limpiar la base de datos. Exit code: " + exitCode);
      }
    } catch (IOException | InterruptedException e) {
      System.err.println("Error ejecutando script de limpieza: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
