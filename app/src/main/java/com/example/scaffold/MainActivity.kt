package com.example.scaffold

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scaffold.ui.theme.ScaffoldTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScaffoldTheme {
                ScaffoldApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldApp() {
    // Create a NavController instance
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Green // Change color as desired
            ) {
                Text(text = "Bottom bar")
            }
        }
    ) { paddingValues ->
        NavHost(navController, startDestination = "mainScreen", Modifier.padding(paddingValues)) {
            composable("mainScreen") {
                MainScreen(navController) // Pass navController here
            }
            composable("infoScreen") {
                InfoScreen(navController)
            }
            composable("settingsScreen") {
                SettingsScreen(navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(title: String, navController: NavController) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Select an option") } // Default text

    TopAppBar(
        title = { Text(title) }, // Use the passed title
        navigationIcon = {
            IconButton(onClick = { /* Handle navigation icon click */ }) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Filled.MoreVert, contentDescription = null)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false } // Dismiss menu when clicked outside
            ) {
                // Create a list of options
                val options = listOf("Info", "Settings")

                options.forEach { label ->
                    DropdownMenuItem(
                        text = { Text(text = label) }, // Using your suggestion
                        onClick = {
                            selectedText = label // Set selected text
                            expanded = false // Close menu after selection
                            when (label) {
                                "Info" -> navController.navigate("infoScreen") // Navigate to Info Screen
                                "Settings" -> navController.navigate("settingsScreen") // Navigate to Settings Screen
                            }
                        }
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenTopBar(title: String, navController: NavController) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null)
            }
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavController) {
    // Define title state
    val title = "Main Screen"
    Scaffold(
        topBar = { MainTopBar(title = title, navController = navController) },
        content = { paddingValues -> // Add paddingValues parameter here
            // Apply padding to the text
            Text(
                text = "Content for Home screen",
                modifier = Modifier.padding(paddingValues) // Apply padding
            )
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InfoScreen(navController: NavController) {

    val title = "Infoooo"
    Scaffold(
        topBar = { ScreenTopBar(title = title, navController) },
        content = { paddingValues ->
        Text(text = "Content for Info screen", modifier = Modifier.padding(paddingValues)) }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(navController: NavController) {
    // Define title state
    val title = "Settings"
    Scaffold(
        topBar = { ScreenTopBar(title = title, navController) },
        content = { paddingValues -> Text(text = "Content for Settings screen",modifier = Modifier.padding(paddingValues)) }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewScaffoldApp() {
    ScaffoldTheme {
        ScaffoldApp()
    }
}
