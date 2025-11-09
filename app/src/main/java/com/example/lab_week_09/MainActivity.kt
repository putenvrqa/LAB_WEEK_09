package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.navigation.NavType
import androidx.navigation.navArgument
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
    const val DETAIL = "detail/{name}"
}

@Composable
fun AppNav(navController: NavHostController = rememberNavController()) {
    var student by remember { mutableStateOf(Student("")) }
    val students = remember { mutableStateListOf(Student("Tanu"), Student("Tina"), Student("Tono")) }

    NavHost(navController = navController, startDestination = Routes.HOME) {

        composable(Routes.HOME) {
            HomeScreen(
                student = student,
                items = students,
                onNameChange = { student = student.copy(name = it) },
                onSubmitClick = {
                    val t = student.name.trim()
                    if (t.isNotEmpty()) {
                        students.add(Student(t))
                        student = Student("")
                    }
                },
                onFinishClick = { navController.navigate(Routes.LIST) }
            )
        }

        composable(Routes.LIST) {
            StudentListScreen(
                items = students,
                onItemClick = { s -> navController.navigate("detail/${s.name}") },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            val nameArg = backStackEntry.arguments?.getString("name").orEmpty()
            StudentDetailScreen(
                name = nameArg,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun HomeScreen(
    student: Student,
    items: List<Student>,
    onNameChange: (String) -> Unit,
    onSubmitClick: () -> Unit,
    onFinishClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            OnBackgroundTitleText(text = stringResource(id = R.string.enter_item))

            Spacer(Modifier.height(8.dp))

            TextField(
                value = student.name,
                onValueChange = onNameChange,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PrimaryTextButton(
                    text = stringResource(id = R.string.button_click),
                    onClick = onSubmitClick
                )
                PrimaryTextButton(
                    text = "Finish",
                    onClick = onFinishClick
                )
            }

            Spacer(Modifier.height(12.dp))
        }

        items(items) { s ->
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) { Text(text = s.name, style = MaterialTheme.typography.bodyLarge) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentListScreen(
    items: List<Student>,
    onItemClick: (Student) -> Unit,
    onBack: () -> Unit
) {
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
                        .fillMaxWidth()
                        .clickable { onItemClick(s) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = s.name, style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDetailScreen(name: String, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .padding(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Name:", style = MaterialTheme.typography.titleSmall)
            Text(text = name, style = MaterialTheme.typography.headlineSmall)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPart4() {
    LAB_WEEK_09Theme { AppNav() }
}