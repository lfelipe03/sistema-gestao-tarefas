package repository;

import entities.Tarefa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class TarefaRepository {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("tarefasPU");

    public void salvar(Tarefa tarefa) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(tarefa);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }

    public void atualizar(Tarefa tarefa) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(tarefa);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void deletar(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Tarefa tarefa = em.find(Tarefa.class, id);
            if(tarefa != null) {
                em.remove(tarefa);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public Tarefa buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Tarefa tarefa = null;
        try {
            tarefa = em.find(Tarefa.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return tarefa;
    }

    public List<Tarefa> listarTodas() {
        EntityManager em = emf.createEntityManager();
        List<Tarefa> tarefas = null;
        try {
            tarefas = em.createQuery("from Tarefa", Tarefa.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return tarefas;
    }
}
