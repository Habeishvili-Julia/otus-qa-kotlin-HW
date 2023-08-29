import kotlin.properties.Delegates
import kotlin.random.Random

fun main(){

    val testSteps = Steps()
    val run = RunTest()

    run.runTest(testSteps){testSteps.testAcsses()}
    run.runTest(testSteps){testSteps.testNull()}
    run.runTest(testSteps){testSteps.testNumberPositive()}
    run.runTest(testSteps){testSteps.testFail()}

}

class Steps {

    private val messageError: String = "%s. i =  %s"
    private var number by Delegates.notNull<Int>()
    private var time by Delegates.notNull<Long>()

    fun beforeAll(){
        println("Before all started")
        time = System.currentTimeMillis()
    }

    fun beforePrecondition(){
        println("Precondition started")
        number = Random.nextInt()
        println(number)
    }

    fun afterAll(){
        println("After all started")
    }

    fun afterClear(){
        println("Clear started")
        number = 0
    }

    fun testAcsses() {
        println("The test successful")
        assert(number % 2 == 0) {
            messageError.format("Num is not even", number)
        }
    }

    fun testNull() {
        println("Test not NULL")
        assert(number != null) {
            messageError.format("Num is not even", number)
        }
    }

    fun testNumberPositive() {
        println("Test number > 0")
        assert(number % 2 > 0) {
            messageError.format("Num is not even", number)
        }
    }

    fun testFail() {
        println("Test fail")
        assert(number == null) {
            messageError.format("Num is not even", number)
        }
    }
}