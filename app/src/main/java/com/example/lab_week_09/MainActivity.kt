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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme
import com.example.lab_week_09.ui.theme.OnBackgroundTitleText
import com.example.lab_week_09.ui.theme.PrimaryTextButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LAB_WEEK_09Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNav()
                }
            }
        }
    }
}

data class Student(var name: String)

private object Routes {
    const val HOME = "home"
    const val LIST = "list"
}

@Composable
fun AppNav(navController: NavHostController = rememberNavController()) {
    var student by remember { mutableStateOf(Student("")) }
    val students = remember { mutableStateListOf(Student("Tanu"), Student("Tina"), Student("Tono")) }

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                student = student,
                onNameChange = { student = student.copy(name = it) },
                onSubmitClick = {
                    val t = student.name.trim()
                    if (t.isNotEmpty()) {
                        students.add(Student(t))
                        student = Student("")
                        navController.navigate(Routes.LIST)
                    }
                }
            )
        }
        composable(Routes.LIST) {
            StudentListScreen(
                items = students,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    student: Student,
    onNameChange: (String) -> Unit,
    onSubmitClick: () -> Unit,
) {
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Week 9 - Home") }) }
    ) { inner ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OnBackgroundTitleText(text = stringResource(id = R.string.enter_item))

                    TextField(
                        value = student.name,
                        onValueChange = onNameChange,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )

                    PrimaryTextButton(
                        text = stringResource(id = R.string.button_click),
                        onClick = onSubmitClick
                    )
                }
            }
        }
    }
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun StudentListScreen(items: List<Student>, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Student List") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { inner ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
        ) {
            items(items) { s ->
                Column(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = s.name, style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPart3() {
    LAB_WEEK_09Theme { AppNav() }
}