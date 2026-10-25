package com.example.geoquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.geoquiz.ui.theme.GeoQuizTheme

data class Question(
    val text: String,
    val answer: Boolean
)

val questions = listOf(
    Question("Canberra is the capital of Australia.", true),
    Question("The Pacific Ocean is larger than the Atlantic Ocean.", true),
    Question("The Suez Canal connects the Red Sea and the Indian Ocean.", false),
    Question("The source of the Nile River is in Egypt.", false),
    Question("The Amazon River is the longest river in the Americas.", true),
    Question("Lake Baikal is the world's oldest and deepest freshwater lake.", true)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GeoQuizTheme {
                GeoQuizScreen()
            }
        }
    }
}

@Composable
fun GeoQuizScreen() {
    var currentIndex by remember { mutableIntStateOf(0) }
    var correctAnswers by remember { mutableIntStateOf(0) }
    var answered by remember { mutableStateOf(false) }
    var showResult by remember { mutableStateOf(false) }

    val currentQuestion = questions[currentIndex]

    // Показываем результат после ответа на последний вопрос
    if (answered && currentIndex == questions.lastIndex && !showResult) {
        LaunchedEffect(currentIndex, answered) {
            showResult = true
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(Color(0xFF6200EE))
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "GeoQuiz",
                fontSize = 24.sp,
                color = Color.White
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = currentQuestion.text,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (!answered) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            if (currentQuestion.answer) {
                                correctAnswers++
                            }
                            answered = true
                        }
                    ) {
                        Text("TRUE")
                    }

                    Button(
                        onClick = {
                            if (!currentQuestion.answer) {
                                correctAnswers++
                            }
                            answered = true
                        }
                    ) {
                        Text("FALSE")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (answered && currentIndex < questions.lastIndex) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            currentIndex++
                            answered = false
                        }
                    ) {
                        Text("NEXT ❯")
                    }
                }
            }
        }
    }

    if (showResult) {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text("Тест завершён")
            },
            text = {
                Text(
                    "Правильных ответов: $correctAnswers из ${questions.size}"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        currentIndex = 0
                        correctAnswers = 0
                        answered = false
                        showResult = false
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GeoQuizScreenPreview() {
    GeoQuizTheme {
        GeoQuizScreen()
    }
}