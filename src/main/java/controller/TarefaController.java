package controller;

import entities.Tarefa;
import enums.Situacao;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import service.TarefaService;
import service.TarefaServiceImpl;

import java.io.Serializable;
import java.util.List;

@Named("tarefaController")
@ViewScoped
public class TarefaController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private TarefaService tarefaService;

    private List<Tarefa> tarefas;
    private Tarefa tarefaEdicao;

    @PostConstruct
    public void init() {
        carregarLista();
        novo();
    }

    public void carregarLista() {
        tarefas = tarefaService.listarTodos();
    }

    public void novo() {
        tarefaEdicao = new Tarefa();
        tarefaEdicao.setSituacao(Situacao.EM_ANDAMENTO);
    }

    public void editar(Tarefa t) {
        Tarefa copiaTarefa = new Tarefa();

        copiaTarefa.setId(t.getId());
        copiaTarefa.setTitulo(t.getTitulo());
        copiaTarefa.setDescricao(t.getDescricao());
        copiaTarefa.setResponsavel(t.getResponsavel());
        copiaTarefa.setPrioridade(t.getPrioridade());
        copiaTarefa.setDeadline(t.getDeadline());
        copiaTarefa.setSituacao(t.getSituacao());
        tarefaEdicao = copiaTarefa;
    }

    public void salvar() {
        if(tarefaEdicao.getId() == null) {
            tarefaService.criar(tarefaEdicao);
        } else {
            tarefaService.atualizar(tarefaEdicao);
        }
        carregarLista();
        novo();
    }

    public void cancelar() {
        novo();
    }

    public void remover(Long id){
        if(id != null) {
            tarefaService.remover(id);
            carregarLista();
        }
    }

    public void concluir(Tarefa t) {
        if(t != null && t.getSituacao() != Situacao.CONCLUIDA) {
            t.setSituacao(Situacao.CONCLUIDA);
            tarefaService.atualizar(t);
            carregarLista();
        }

    }


    public List<Tarefa> getTarefas() {
        return tarefas;
    }
    public Tarefa getTarefaEdicao() {
        return tarefaEdicao;
    }
    public void setTarefaEdicao(Tarefa tarefaEdicao) {
        this.tarefaEdicao = tarefaEdicao;
    }
}