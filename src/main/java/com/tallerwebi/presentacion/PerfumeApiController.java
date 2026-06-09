package com.tallerwebi.presentacion;

import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;

@RestController
@RequestMapping("/api")
public class PerfumeApiController {

    @GetMapping(value = "/perfumes", produces = "application/json")
    public String obtenerPerfumesDelCatalogo() {
        try {
            // Buscamos el archivo JSON local en la carpeta de recursos
            File archivo = ResourceUtils.getFile("classpath:perfumes.json");
            // Leemos todo su contenido y lo retornamos directamente
            return new String(Files.readAllBytes(archivo.toPath()));
        } catch (Exception e) {
            return "{\"error\": \"No se pudo cargar el catálogo de perfumes local.\"}";
        }
    }
}