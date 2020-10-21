package com.well.kotesttest

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.TestLintResult
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.android.tools.lint.detector.api.Issue
import org.intellij.lang.annotations.Language
import io.kotest.core.spec.style.FreeSpec


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest : FreeSpec({
    "should suggest style" {
        @Language("XML")
        val fileContent = """
        <LinearLayout
            xmlns:android="http://schemas.android.com/apk/res/android"
            android:id="@+id/layout_test" >
        </LinearLayout>        
        """.trimIndent()

        fileContent.lintAsLayoutFile()
            .expectFixDiffs(
                """
                Fix for res/layout/dummy_layout.xml line 3: Convert to camel case:
                @@ -3 +3
                -     android:id="@+id/layout_test" >
                +     android:id="@+id/layoutTest" >
            """.trimIndent()
            )
    }
})

fun String.lintAsLayoutFile(vararg issues: Issue): TestLintResult =
    TestLintTask.lint().files(LintDetectorTest.xml("res/layout/dummy_layout.xml", this))
        .allowDuplicates()
        .issues(*issues.map { it }.toTypedArray())
        .allowMissingSdk()
        .run()