package com.example.carpark.web;


import com.example.carpark.service.BaseService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseController<T, V extends BaseService<T>> {
    private final V service;

    public BaseController(V service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> findById(@PathVariable String id) throws NotFoundException {
        return ResponseEntity.ok(this.service.findById(id));
    }

    @GetMapping
    public ResponseEntity<Collection<T>> getAll() {
        return ResponseEntity.ok(this.service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable String id, @Valid @RequestBody T updated) throws NotFoundException {
        return ResponseEntity.ok(this.service.update(id, updated));
    }

    @PostMapping("")
    public void create(@RequestBody T created){
        this.service.create(created);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> delete(@PathVariable(value = "id") String id) {
        this.service.remove(id);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
