package hu.ait.midterm_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hu.ait.midterm_compose.ui.theme.Midterm_ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Midterm_ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DrawButtons()
                }
            }
        }
    }
}

@Composable
fun DrawButtons() {
    Column {
        Button(modifier = Modifier.fillMaxWidth()) {
            Text(text = "PRESS")
        }
        Button(modifier = Modifier.fillMaxWidth()) {
            Text(text = "PRESS")
        }
        Row (modifier = Modifier.fillMaxWidth())
        {
            Button(modifier = Modifier.fillMaxWidth()) {
                Text(text = "PRESS")
            }
            Spacer(modifier = Modifier.fillMaxSize(0.55f))
            Button(modifier = Modifier.fillMaxWidth()) {
                Text(text = "PRESS")
            }
        }
    }
}

