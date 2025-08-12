package com.uvg.todolist_lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.uvg.todolist_lab3.ui.theme.TodoListLab3Theme

data class lista(
    val id: Int,
    val tarea: String,
    val completado: Boolean
)

class ToDoModel : ViewModel() {
    private val mLista = mutableStateListOf<lista>()
    val lista: List<lista> get() = mLista

    fun addLista(tarea: String) {
        val nuevaTarea = lista(id = mLista.size + 1, tarea = tarea, completado = false)
        mLista.add(nuevaTarea)
    }

    fun completarTarea(lista: lista) {
        val index = mLista.indexOf(lista)
        if (index >= 0) {
            mLista[index] = lista.copy(completado = !lista.completado)
        }
    }

    fun eliminarLista(lista: lista) {
        mLista.remove(lista)
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoListLab3Theme {
                val viewModel = remember { ToDoModel() }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    agregarTarea(viewModel = viewModel)
                    Spacer(modifier = Modifier.height(8.dp))
                    Lista(viewModel = viewModel)
                }
                PantallaConFondoLocal()
            }
        }
    }
}

@Composable
fun Lista(viewModel: ToDoModel) {
    val toDoList = viewModel.lista
    LazyColumn {
        items(toDoList) { lista ->
            ListaItem(
                lista = lista,
                onCheckedChange = { viewModel.completarTarea(lista) },
                onDelete = { viewModel.eliminarLista(lista) }
            )
        }
    }
}

@Composable
fun ListaItem(lista: lista, onCheckedChange: (lista) -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onCheckedChange(lista) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = lista.completado,
            onCheckedChange = { onCheckedChange(lista) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = lista.tarea,
            style = MaterialTheme.typography.bodyLarge,
            color = if (lista.completado) Color.Green else Color.Black
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { onDelete() }) {
            Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
        }
    }
}

@Composable
fun agregarTarea(viewModel: ToDoModel) {
    var tarea by remember { mutableStateOf("") }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = tarea,
            onValueChange = { tarea = it },
            label = { Text("Nueva tarea") },
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = {
            if (tarea.isNotBlank()) {
                viewModel.addLista(tarea)
                tarea = ""
            }
        }) {
            Icon(Icons.Filled.Add, contentDescription = "Agregar tarea")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLista() {
    TodoListLab3Theme {
        val vm = ToDoModel().apply { addLista("Tarea de prueba") }
        Lista(viewModel = vm)
fun PantallaConFondoLocal() {
    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
