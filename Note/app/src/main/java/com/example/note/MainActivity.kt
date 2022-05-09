package com.example.note

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.note.model.Note
import com.example.note.ui.theme.NoteTheme
import com.example.note.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteTheme {
                val noteViewModel = viewModel<NoteViewModel>()
                val textTitle = remember {
                    mutableStateOf("")
                }
                val textDescription = remember {
                    mutableStateOf("")
                }
                val listNote = remember {
                    mutableStateListOf<Note>()
                }
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {
                        HeaderView()

                        Column(
                            modifier = Modifier.padding(top = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            TextField(
                                value = textTitle.value,
                                onValueChange = {
                                    textTitle.value = it
                                },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = MaterialTheme.colors.surface
                                ),
                                label = { Text("Title") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .padding(start = 30.dp, end = 30.dp, bottom = 5.dp),
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                            )
                            TextField(
                                value = textDescription.value,
                                onValueChange = {
                                    textDescription.value = it
                                },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = MaterialTheme.colors.surface
                                ),
                                label = { Text("Add a note") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .padding(start = 30.dp, end = 30.dp, bottom = 10.dp),
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                            )
                            Button(
                                onClick = {
                                    if (textTitle.value != "" && textDescription.value != "") {
                                        val dateFormatter = SimpleDateFormat("E, d MMMM hh:mm a");
                                        val note = Note(title = textTitle.value,
                                            description = textDescription.value,
                                            entryDate = dateFormatter.format(Date()))
                                        textTitle.value = ""
                                        textDescription.value = ""
                                        noteViewModel.addNote(note)
                                    }
                                },
                                modifier = Modifier
                                    .clip(CircleShape)
                            ) {
                                Text(
                                    text = "Lưu",
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                )
                            }
                        }

                        LazyColumn(
                            modifier = Modifier.padding(top = 10.dp)
                        ) {
                            listNote.convertList(noteViewModel.noteList.value.reversed())
                            items(items = listNote) {
                                    item ->
                                        Card(
                                            modifier = Modifier
                                                .padding(horizontal = 20.dp, vertical = 5.dp)
                                                .fillMaxWidth()
                                                .height(80.dp)
                                                .clip(RoundedCornerShape(0.dp, 35.dp, 0.dp, 35.dp)),
                                            backgroundColor = Color.parse("#2a2b2e"),
                                            elevation = 0.dp,
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(5.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Column(
                                                    modifier = Modifier
                                                        .padding(5.dp)
                                                        .width(270.dp)
                                                ) {
                                                    Text(
                                                        text = item.title,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                    Text(
                                                        text = item.description,
                                                        fontSize = 18.sp
                                                    )
                                                    Text(
                                                        text = item.entryDate,
                                                        fontSize = 13.sp
                                                    )
                                                }
                                                Image(
                                                    painterResource(R.drawable.ic_baseline_delete_24),
                                                    contentDescription = "",
                                                    contentScale = ContentScale.Crop,
                                                    modifier = Modifier
                                                        .height(25.dp)
                                                        .width(25.dp)
                                                        .padding(end = 10.dp)
                                                        .clickable {
                                                            listNote.remove(item)
                                                            noteViewModel.removeNote(item)
                                                        }
                                                )
                                            }
                                        }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun Color.Companion.parse(colorString: String): Color =
    Color(color = android.graphics.Color.parseColor(colorString))

@Preview
@Composable
fun HeaderView(text: String = "Note") {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.parse("#2a2b2e")),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h4,
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
                .padding(start = 20.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_notifications_24),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(30.dp)
                .width(30.dp)
                .padding(end = 10.dp)
                .align(alignment = Alignment.CenterVertically),
        )
    }
}

@Composable
fun FormContent() {
    val noteViewModel = viewModel<NoteViewModel>()
    val textTitle = remember {
        mutableStateOf("")
    }
    val textDescription = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = textTitle.value,
            onValueChange = {
                textTitle.value = it
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            ),
            label = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(start = 30.dp, end = 30.dp, bottom = 5.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        )
        TextField(
            value = textDescription.value,
            onValueChange = {
                textDescription.value = it
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            ),
            label = { Text("Add a note") },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(start = 30.dp, end = 30.dp, bottom = 5.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        )
        Button(
            onClick = {
                val dateFormatter = SimpleDateFormat("E, d MMMM hh:mm a");
                val note = Note(title = textTitle.value,
                    description = textDescription.value,
                    entryDate = dateFormatter.format(Date()))
                textTitle.value=""
                textDescription.value = ""
                noteViewModel.addNote(note = note)
            },
            modifier = Modifier
                .clip(CircleShape)
        ) {
            Text(
                text = "Lưu",
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
        }
    }
}

fun <T> SnapshotStateList<T>.convertList(newList: List<T>){
    clear()
    addAll(newList)
}

@Composable
fun ListContent(listNote: SnapshotStateList<Note>) {
//    val noteViewModel = viewModel<NoteViewModel>()
//    val listNote = remember {
//        mutableStateListOf<Note>()
//    }
    LazyColumn(
        modifier = Modifier.padding(top = 10.dp)
    ) {
//        listNote.convertList(noteViewModel.noteList.value.reversed())
        items(items = listNote) {
            item ->
            Card(
                modifier = Modifier
                    .padding(0.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(0.dp, 35.dp, 0.dp, 35.dp)),
                backgroundColor = Color.LightGray,
                elevation = 0.dp
            ) {
                Row(
                    modifier = Modifier.padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .width(250.dp)

                    ) {
                        Text(
                            text = item.title,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = item.description,
                            fontSize = 18.sp
                        )
                        Text(
                            text = item.entryDate,
                            fontSize = 13.sp
                        )
                    }
                    Image(
                        painterResource(R.drawable.ic_baseline_delete_24),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(30.dp)
                            .width(25.dp)
                            .padding(end = 10.dp)
                            .clickable {
                                listNote.remove(item)
//                                noteViewModel.removeNote(item)
                            }
                    )
                }
            }
        }
    }
}
