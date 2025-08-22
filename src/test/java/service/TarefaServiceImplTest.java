package service;

import entities.Tarefa;
import enums.Prioridade;
import enums.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.TarefaRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarefaServiceImplTest {


    @Mock
    private TarefaRepository tarefaRepository;

    @InjectMocks
    private TarefaServiceImpl service;


    private Tarefa tarefaValida() {
        Tarefa t = new Tarefa();
        t.setTitulo("Estudar");
        t.setDescricao("Revisar conteúdo");
        t.setResponsavel("Luiz");
        t.setPrioridade(Prioridade.ALTA);
        t.setDeadline("10/08/2025");
        return t;
    }
    
    @Test
    void criar_DefinirSituacaoEmAndamentoQuandoNulaESalvar() {
        Tarefa t = tarefaValida();
        t.setStatus(null);

        Tarefa r = service.criar(t);

        assertNotNull(r);
        assertEquals(Status.EM_ANDAMENTO, r.getStatus());
        verify(tarefaRepository).salvar(t);
        verifyNoMoreInteractions(tarefaRepository);
    }

    @Test
    void criar_deveLancarExcecaoQuandoTituloInvalido() {
        Tarefa t = new Tarefa();
        t.setTitulo(" ");

        assertThrows(IllegalArgumentException.class, () -> service.criar(t));
        verifyNoInteractions(tarefaRepository);
    }

    @Test
    void atualizar_deveLancarExcecaoQuandoIdNulo() {
        Tarefa t = tarefaValida();
        t.setId(null);

        assertThrows(IllegalArgumentException.class, () -> service.atualizar(t));
        verifyNoInteractions(tarefaRepository);
    }

    @Test
    void atualizar_deveValidarEChamarRepository() {
        Tarefa t = tarefaValida();
        t.setId(1L);

        Tarefa r = service.atualizar(t);

        assertSame(t, r);
        verify(tarefaRepository).atualizar(t);
        verifyNoMoreInteractions(tarefaRepository);
    }

    @Test
    void removerDeveLancarExcecaoQuandoIdNulo() {
        assertThrows(IllegalArgumentException.class, () -> service.remover(null));
        verifyNoInteractions(tarefaRepository);
    }

    @Test
    void removerDeveChamarRepository() {
        service.remover(10L);
        verify(tarefaRepository).deletar(10L);
        verifyNoMoreInteractions(tarefaRepository);
    }

    @Test
    void buscarPorIdDeveLancarExcecaoQuandoIdNulo() {
        assertThrows(IllegalArgumentException.class, () -> service.buscarPorId(null));
        verifyNoInteractions(tarefaRepository);
    }
    @Test
    void buscarPorIdDeveChamarRepository() {
        service.buscarPorId(5L);
        verify(tarefaRepository).buscarPorId(5L);
        verifyNoMoreInteractions(tarefaRepository);
    }

    @Test
    void listarTodosDeveChamarRepositoryERetornarLista() {
        List<Tarefa> tarefaList = Arrays.asList(tarefaValida(), tarefaValida());
        when(tarefaRepository.listarTodas()).thenReturn(tarefaList);

        List<Tarefa> r = service.listarTodos();

        assertEquals(2, r.size());
        assertEquals(tarefaList, r);
        verify(tarefaRepository).listarTodas();
        verifyNoMoreInteractions(tarefaRepository);
    }

    @Test
    void validar() {
        assertThrows(IllegalArgumentException.class, () -> service.validar(null));
    }
}