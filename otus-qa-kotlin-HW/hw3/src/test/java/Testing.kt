import io.kotest.core.extensions.TestCaseExtension
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult

class RepeatOnFailureExtension: TestCaseExtension {

    val 
    override suspend fun intercept(testCase: TestCase, execute: suspend (TestCase) -> TestResult): TestResult {
        TODO("Not yet implemented")
    }

}