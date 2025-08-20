import entities.Tarefa;
import enums.Prioridade;
import enums.Situacao;
import service.TarefaService;
import service.TarefaServiceImpl;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TarefaService tarefaService = new TarefaServiceImpl();

        Tarefa tarefa1 = new Tarefa(
                "Estudar JPA",
                "Ler Documentação Hibernate",
                "Luiz Felipe",
                Prioridade.ALTA,
                LocalDate.of(2025,8,05),
                Situacao.EM_ANDAMENTO
        );

        //Verificando se salvou
        tarefaService.criar(tarefa1);
        System.out.println("Tarefa salva com sucesso!");

        //Verificando a listagem
        List<Tarefa> tarefas = tarefaService.listarTodos();
        System.out.println("Lista de tarefas: ");
        tarefas.forEach(t -> System.out.println(t.getId() +" - "+ t.getTitulo()));

        //Buscar por id
        Tarefa encontrada = tarefaService.buscarPorId(tarefas.get(0).getId());
        System.out.println("Tarefa de ID: " + encontrada.getTitulo());

        //Atualizando tarefa
        encontrada.setSituacao(Situacao.CONCLUIDA);
        tarefaService.atualizar(encontrada);
        System.out.println("Tarefa atualizada para concluida");

        //Remover tarefa
        tarefaService.remover(encontrada.getId());
        System.out.println("Tarefa removida!");
    }
}
