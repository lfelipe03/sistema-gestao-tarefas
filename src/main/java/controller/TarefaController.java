package controller;

import entities.Tarefa;
import enums.Status;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import service.TarefaService;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named("tarefaController")
@ViewScoped
public class TarefaController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private TarefaService tarefaService;

    private List<Tarefa> tarefas;
    private Tarefa tarefaEdicao;

    private Long filtroNumero;
    private String filtroTituloDescricao;
    private String filtroResponsavel;
    private Status filtroStatus;

    @PostConstruct
    public void init() {
        filtroStatus = Status.EM_ANDAMENTO;
        buscar();
        novo();
    }

    public void buscar() {
        List<Tarefa> base = tarefaService.listarTodos();

        tarefas = base.stream()
                .filter(t -> filtroNumero == null || (t.getId() != null && t.getId().equals(filtroNumero)))
                .filter(t -> isBlank(filtroTituloDescricao) ||
                        containsIgnoreCase(t.getTitulo(), filtroTituloDescricao) ||
                        containsIgnoreCase(t.getDescricao(), filtroTituloDescricao))
                .filter(t -> isBlank(filtroResponsavel) || containsIgnoreCase(t.getResponsavel(), filtroResponsavel))
                .filter(t -> filtroStatus == null || t.getStatus() == filtroStatus)
                .collect(Collectors.toList());
    }

    public void limparFiltros() {
        filtroNumero = null;
        filtroTituloDescricao = null;
        filtroResponsavel = null;
        filtroStatus = null;
        buscar();
    }

    public void novo() {
        tarefaEdicao = new Tarefa();
        tarefaEdicao.setStatus(Status.EM_ANDAMENTO);
    }

    public void editar(Tarefa t) {
        Tarefa copiaTarefa = new Tarefa();
        copiaTarefa.setTitulo(t.getTitulo());
        copiaTarefa.setDescricao(t.getDescricao());
        copiaTarefa.setResponsavel(t.getResponsavel());
        copiaTarefa.setPrioridade(t.getPrioridade());
        copiaTarefa.setDeadline(t.getDeadline());
        copiaTarefa.setStatus(t.getStatus());
        tarefaEdicao = copiaTarefa;
    }

    public void salvar() {
        if (tarefaEdicao.getId() == null) {
            tarefaService.criar(tarefaEdicao);
        } else {
            tarefaService.atualizar(tarefaEdicao);
        }
        buscar();
        novo();
    }

    public void cancelar() {
        novo();
    }

    public void remover(Long id) {
        if (id != null) {
            tarefaService.remover(id);
            buscar();
        }
    }

    public void concluir(Tarefa t) {
        if (t != null && t.getStatus() != Status.CONCLUIDA) {
            t.setStatus(Status.CONCLUIDA);
            tarefaService.atualizar(t);
            buscar();
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
    private static boolean containsIgnoreCase(String a, String b) {
        return a != null && b != null && a.toLowerCase().contains(b.toLowerCase());
    }


    public List<Tarefa> getTarefas() { return tarefas; }
    public Tarefa getTarefaEdicao() { return tarefaEdicao; }
    public void setTarefaEdicao(Tarefa tarefaEdicao) { this.tarefaEdicao = tarefaEdicao; }

    public Long getFiltroNumero() { return filtroNumero; }
    public void setFiltroNumero(Long filtroNumero) { this.filtroNumero = filtroNumero; }

    public String getFiltroTituloDescricao() { return filtroTituloDescricao; }
    public void setFiltroTituloDescricao(String filtroTituloDescricao) { this.filtroTituloDescricao = filtroTituloDescricao; }

    public String getFiltroResponsavel() { return filtroResponsavel; }
    public void setFiltroResponsavel(String filtroResponsavel) { this.filtroResponsavel = filtroResponsavel; }

    public Status getFiltroStatus() { return filtroStatus; }
    public void setFiltroStatus(Status filtroStatus) { this.filtroStatus = filtroStatus; }
}
