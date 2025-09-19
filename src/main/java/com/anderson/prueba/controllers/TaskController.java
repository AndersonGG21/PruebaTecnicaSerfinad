package com.anderson.prueba.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anderson.prueba.models.Task;
import com.anderson.prueba.servicies.TaskService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/tareas")
@Validated
public class TaskController {
    
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Obtiene todas las tareas del sistema.
     * 
     * Este endpoint recupera una lista de todas las tareas disponibles y las devuelve en un
     * formato de respuesta estandarizado. La respuesta incluye un indicador de éxito, los 
     * datos de las tareas y un mensaje descriptivo.
     * 
     * @return ResponseEntity que contiene un Map con la siguiente estructura:
     *         - success (boolean): indica si la operación fue exitosa
     *         - data (ArrayList<Task>): lista de todas las tareas cuando es exitosa
     *         - message (String): mensaje descriptivo sobre el resultado de la operación
     *         - error (String): mensaje de error cuando ocurre una excepción (solo en caso de fallo)
     *         
     *         Devuelve HTTP 200 (OK) en caso de éxito o HTTP 500 (Internal Server Error) en caso de fallo
     * 
     * @throws Exception si hay un error al recuperar las tareas desde la capa de servicio
     */
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getTasks() {
        try {
            ArrayList<Task> tasks = taskService.getTasksList();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", tasks);
            response.put("message", "Tareas obtenidas exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error al obtener las tareas");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Crea una nueva tarea en el sistema.
     * 
     * Este endpoint permite crear una nueva tarea mediante una petición POST.
     * La tarea debe ser enviada en el cuerpo de la petición en formato JSON
     * y será validada automáticamente.
     * 
     * @param task La tarea a crear, debe ser un objeto JSON válido que contenga
     *             todos los campos requeridos en la entidad Task.
     * @return ResponseEntity con un Map que contiene:
     *         - En caso de éxito (HTTP 201):
     *           * success: true
     *           * data: objeto Task creado con su ID generado
     *           * message: "Tarea creada exitosamente"
     *         - En caso de error (HTTP 500):
     *           * success: false
     *           * message: "Error al crear la tarea"
     *           * error: mensaje detallado del error ocurrido
     * 
     * @throws Exception si ocurre algún error durante el proceso de creación
     * 
     */
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> createTask(@Valid @RequestBody Task task) {
        try {
            Task createdTask = taskService.createTask(task);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", createdTask);
            response.put("message", "Tarea creada exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error al crear la tarea");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    

}
