import data.Priority
import data.Task
import data.TasksRepository
import data.TasksRepositoryMemory
import org.junit.jupiter.api.*
import kotlin.test.assertContains
import kotlin.test.assertContentEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue

class SimpleTest {

    private lateinit var tasksRepository: TasksRepository

    @BeforeEach
    // добавление значений
    fun createListTaskRepository(){
        tasksRepository = TasksRepositoryMemory()
        (tasksRepository as TasksRepositoryMemory).tasks.run {
            add(Task(3, "2test", Priority.HIGH))
            add(Task(2, "8test", Priority.LOW, false))
            add(Task(4, "10test", Priority.LOW))
            add(Task(1, "3test", Priority.MEDIUM))
        }
        println((tasksRepository as TasksRepositoryMemory).tasks.toList())
    }

    @AfterEach
    // чистка всего листа
    fun deleteAllTaskRepository(){
        tasksRepository = TasksRepositoryMemory()
        (tasksRepository as TasksRepositoryMemory).tasks.clear()
    }

    @DisplayName ("Сhecking adding a task to the list")
    @Test
    fun addTaskInRepository(){
        val task1 = Task(5,"35test", priority = Priority.values()[1])
        tasksRepository.addTask(task1)
        val nextAddTask = (tasksRepository as TasksRepositoryMemory).nextId()
        val task2 = Task(nextAddTask,"2test", Priority.valueOf("HIGH"))
        tasksRepository.addTask(task2)
        println((tasksRepository as TasksRepositoryMemory).tasks.toList())
        assertAll(
            { assertContains(tasksRepository.getTasks(), task1)},
            { assertContains(tasksRepository.getTasks(), task2)}
        )
    }

    @DisplayName ("Checking task deletion to the list")
    @Test
    fun deleteTaskInRepository(){
        val deleteTask = tasksRepository.getTasks().random()
        (tasksRepository as TasksRepositoryMemory).tasks.remove(deleteTask)
        println((tasksRepository as TasksRepositoryMemory).tasks.toList())
        assertFails { assertContains(tasksRepository.getTasks(), deleteTask) }
    }

    @DisplayName ("Сhecking the completed task")
    @Test
    fun completedTaskInRepository(){
        val completedTask = tasksRepository.getTasks().filter { !it.completed }.random()
        completedTask.id?.let { tasksRepository.completeTask(it) }
        println((tasksRepository as TasksRepositoryMemory).tasks.toList())
        assertTrue { completedTask.completed }
    }

//    @DisplayName ("Checking the sorting of the cleaver by name and priority")
//    @Test
//    fun filterNameAndPriority(){
//        val repository = tasksRepository.getTasks()
//
//        assertAll (
//            { assertContentEquals(
//                repository.sortedBy { it.name }, repository, "Repository tasks are not sorted by name")
//            },
//
//            { assertContentEquals(
//                repository.sortedBy { it.priority }, repository, "Repository tasks are not sorted by prioryty")
//            }
//        )
//    }
}
