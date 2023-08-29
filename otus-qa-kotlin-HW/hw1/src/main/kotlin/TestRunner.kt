import kotlin.reflect.full.declaredFunctions

interface TestRunner {
    fun <T> runTest(steps: T, test: () -> Unit)
}

class RunTest: TestRunner {

    override fun <T> runTest(steps: T, test: () -> Unit) {

        val thisSteps = when (steps) {
            null -> throw NullPointerException("Just massege")
            else -> steps
        }

        val beforeStep = thisSteps::class.declaredFunctions.filter { kFunction ->
            kFunction.name.contains("before")
        }.forEach {
            it.call(thisSteps)
        }

        try {
            test.invoke()
        } catch (e: AssertionError) {
            e.printStackTrace()
        }

        val afterStep = thisSteps::class.declaredFunctions.filter { kFunction ->
            kFunction.name.contains("after")
        }.forEach {
            it.call(thisSteps)
        }

    }
}