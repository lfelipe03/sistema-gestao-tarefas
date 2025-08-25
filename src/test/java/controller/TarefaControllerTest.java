package controller;

import entities.Tarefa;
import enums.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.TarefaService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarefaControllerTest {

    @Mock
    private TarefaService tarefaService;

    @InjectMocks
    private TarefaController controller;

    private Tarefa tarefaValida() {
        Tarefa t = new Tarefa();
        t.setId(1L);
        t.setTitulo("Estudar");
        t.setDescricao("Revisar conteúdo");
        t.setResponsavel("Luiz");
        t.setStatus(Status.EM_ANDAMENTO);
        return t;
    }

    @Test
    void initDeveCarregarLista() {
        when(tarefaService.listarTodos()).thenReturn(Collections.emptyList());

        controller.init();

        assertNotNull(controller.getTarefas());
        assertNotNull(controller.getTarefaEdicao());
        assertEquals(Status.EM_ANDAMENTO, controller.getTarefaEdicao().getStatus());
        verify(tarefaService, times(1)).listarTodos();
        verifyNoMoreInteractions(tarefaService);
    }

    @Test
    void salvarDeveCriarQuandoIdNulo() {
        when(tarefaService.listarTodos()).thenReturn(Collections.emptyList());

        Tarefa nova = tarefaValida();
        nova.setId(null);
        controller.setTarefaEdicao(nova);

        controller.salvar();

        verify(tarefaService).criar(nova);
        verify(tarefaService).listarTodos();
        verifyNoMoreInteractions(tarefaService);
    }

    @Test
    void salvarDeveAtualizarQuandoIdNaoNulo() {
        when(tarefaService.listarTodos()).thenReturn(Collections.emptyList());

        Tarefa existente = tarefaValida();
        controller.setTarefaEdicao(existente);

        controller.salvar();

        verify(tarefaService).atualizar(existente);
        verify(tarefaService).listarTodos();
        verifyNoMoreInteractions(tarefaService);
    }

    @Test
    void removerDeveChamarServiceQuandoIdNaoNulo() {
        when(tarefaService.listarTodos()).thenReturn(Collections.emptyList());

        controller.remover(1L);

        verify(tarefaService).remover(1L);
        verify(tarefaService).listarTodos();
        verifyNoMoreInteractions(tarefaService);
    }

    @Test
    void removerNaoFazNadaQuandoIdNulo() {
        controller.remover(null);
        verifyNoInteractions(tarefaService);
    }

    @Test
    void concluirDeveAtualizarSituacaoQuandoNaoConcluida() {
        when(tarefaService.listarTodos()).thenReturn(Collections.emptyList());

        Tarefa t = tarefaValida();
        t.setStatus(Status.EM_ANDAMENTO);

        controller.concluir(t);

        assertEquals(Status.CONCLUIDA, t.getStatus());
        verify(tarefaService).atualizar(t);
        verify(tarefaService).listarTodos();
        verifyNoMoreInteractions(tarefaService);
    }

    @Test
    void concluirNaoFazNadaSeTarefaJaConcluida() {
        Tarefa t = tarefaValida();
        t.setStatus(Status.CONCLUIDA);

        controller.concluir(t);

        verifyNoInteractions(tarefaService);
    }

    @Test
    void editarDeveCopiarPropriedades() {
        Tarefa t = tarefaValida();
        t.setDescricao("Original");

        controller.editar(t);
        Tarefa copia = controller.getTarefaEdicao();

        assertNotSame(t, copia);
        assertEquals(t.getDescricao(), copia.getDescricao());
    }
}
