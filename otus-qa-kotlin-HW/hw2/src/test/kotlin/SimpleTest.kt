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
    // добавление значений следует производить через API приложения
    // tasksRepository.addTask() ....
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
//    @Disabled   // Не надо: и так before создает новый пустой tasks
    // чистка всего листа
    fun deleteAllTaskRepository(){
        tasksRepository = TasksRepositoryMemory()
        (tasksRepository as TasksRepositoryMemory).tasks.clear()
    }

    @DisplayName ("Сhecking adding a task to the list")
    @Test
    fun addTaskInRepository(){
        // Не совсем корректно добавлять задачи с заданным id - пример все равно его не учитывает
        // пример преподавателя сам дает очередной id добавляемой задаче
        // этот idTask1 или idTask2 мы получаем в ответ на вызов фунции add, в том числе без указания номера задачи
        // попробуйте убрать все Ваши номера задач и обращайтесь к add без номеров (именованные аргументы)
        val task1 = Task(5,"35test", priority = Priority.values()[1])
        val idTask1 =
          tasksRepository.addTask(task1)
        val nextAddTask = (tasksRepository as TasksRepositoryMemory).nextId()  // ненужно - пример даст номер
        val task2 = Task(nextAddTask,"2test", Priority.valueOf("HIGH"))
        val idTask2 =
          tasksRepository.addTask(task2)
        println((tasksRepository as TasksRepositoryMemory).tasks.toList())
        assertAll(
            { assertContains(tasksRepository.getTasks(), task1)},
            { assertContains(tasksRepository.getTasks(), task2)}
        )
    }

    @DisplayName ("Checking task deletion to the list")
    // тем более что функции remove в примере нет - похвально это доп
    // и что тогда тестируется?? функция Collection Remove?? - она работает
    // задание выдано Вам как тестору на тестировавние приложения TODO
    // Если бы (как и должно быть в TasksRepositoryMemory стоит private  val tasks
    // то этот тест компилироваться не будет - и это обычно так и бывает
    // Тестер при тестированиее приложения Обязан обращаться к приложению только через его API
    // следует воспользоваться tasksRepository.deleteTask(id: Int) вместо tasks.remove(deleteTask)
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
        // Что здесь проверяется? Что у локальной переменной completedTask completed = True??
        // а причем здесь тогда тестируемое приложение
        // задание "Завершить задачу и проверить корректность работы фильтра по завершенным задачам"
        // следует запросить все задачи tasksRepository.getTasks()
        // и проверить что законченная с его id закончена
        assertTrue { completedTask.completed }
    }

    @DisplayName ("Checking the sorting of the cleaver by name and priority")
    @Disabled  // Это задание делать не надо
    @Test
    fun filterNameAndPriority(){
        val repository = tasksRepository.getTasks()

        assertAll (
            { assertContentEquals(
                repository.sortedBy { it.name }, repository, "Repository tasks are not sorted by name")
            },

            { assertContentEquals(
                repository.sortedBy { it.priority }, repository, "Repository tasks are not sorted by prioryty")
            }
        )
    }
}
