package service;

import entities.Tarefa;
import enums.Situacao;
import repository.TarefaRepository;

import java.util.List;

public class TarefaServiceImpl implements TarefaService {

    private final TarefaRepository tarefaRepository = new TarefaRepository();

    @Override
    public Tarefa criar(Tarefa t) {
        validar(t);
        if(t.getSituacao() == null) {
            t.setSituacao(Situacao.EM_ANDAMENTO);
        }
        tarefaRepository.salvar(t);
        return t;
    }

    @Override
    public Tarefa atualizar(Tarefa t) {
        if (t.getId() == null) {
            throw new IllegalArgumentException("ID é obrigatório para atualizar");
        }
        validar(t);
        tarefaRepository.atualizar(t);
        return t;
    }

    @Override
    public void remover(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("ID é obrigatório para remover");
        }
        tarefaRepository.deletar(id);
    }

    @Override
    public Tarefa buscarPorId(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("ID é obrigatório para buscar");
        }
        return tarefaRepository.buscarPorId(id);
    }

    @Override
    public List<Tarefa> listarTodos() {
        return tarefaRepository.listarTodas();
    }

    public void validar(Tarefa t) {
        if (t == null) {
            throw new IllegalArgumentException("Tarefa não pode ser nula");
        }

        if (t.getTitulo() == null || t.getTitulo().isBlank()) {
            throw new IllegalArgumentException("Titulo é obrigatorio");
        }

        if (t.getResponsavel() == null || t.getResponsavel().isBlank()) {
            throw new IllegalArgumentException("Responsavel é obrigatorio");
        }

        if (t.getPrioridade() == null) {
            throw new IllegalArgumentException("Prioridade é obrigatorio");
        }

        if (t.getDeadline() == null) {
            throw new IllegalArgumentException("Deadline é obrigatorio");
        }
    }
}
