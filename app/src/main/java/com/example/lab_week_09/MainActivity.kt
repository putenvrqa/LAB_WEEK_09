package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LAB_WEEK_09Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home()   // Part 2: tidak kirim list statis
                }
            }
        }
    }
}

data class Student(
    var name: String
)

@Composable
fun Home() {
    var student by remember { mutableStateOf(Student("")) }

    val students = remember {
        mutableStateListOf(
            Student("Tanu"),
            Student("Tina"),
            Student("Tono")
        )
    }

    val onSubmit = {
        val trimmed = student.name.trim()
        if (trimmed.isNotEmpty()) {
            students.add(Student(trimmed))
            student = Student("")
        }
    }

    HomeContent(
        student = student,
        onNameChange = { student = student.copy(name = it) },
        onSubmitClick = onSubmit,
        items = students
    )
}

@Composable
fun HomeContent(
    student: Student,
    onNameChange: (String) -> Unit,
    onSubmitClick: () -> Unit,
    items: List<Student>,
) {
    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.enter_item))

                TextField(
                    value = student.name,
                    onValueChange = onNameChange,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )

                Button(
                    onClick = onSubmitClick,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(text = stringResource(id = R.string.button_click))
                }
            }
        }

        items(items) { s ->
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = s.name)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomePart2() {
    LAB_WEEK_09Theme { Home() }
}