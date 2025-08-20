package service;

import entities.Tarefa;

import java.util.List;

public interface TarefaService {

    Tarefa criar(Tarefa t);
    Tarefa atualizar(Tarefa t);
    void remover(Long id);
    Tarefa buscarPorId(Long id);
    List<Tarefa> listarTodos();

}
